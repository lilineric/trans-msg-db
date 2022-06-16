# trans-msg-db
## 介绍
本地消息表是用来实现分布式事务的一种方案，原理和实现都非常简单，不用像 TCC 一样需要业务实现 Try、Confirm/Cancel 接口，依赖本地数据库事务和 MQ 消息不断重试保证最终一致性。
但是需要将本地消息表和业务表放在一个数据库里面，并且不支持回滚。

适用于对资源隔离性没有要求的异步场景。

## 快速开始
实例参考 trans-msg-db-sample，运行需要先执行 init.sql。
### Spring Boot 项目
引入依赖
```xml
<dependency>
    <groupId>com.cellulam</groupId>
    <artifactId>trans-msg-db-spring-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
生产方添加注解:
```java
@Transactional
@TransMsgProducer
public void produce() {
    ...
}
```
消费方添加注解:
```java
@TransMsgConsumer
public void consume() {
    ...
}
```
### trans-msg.yml 配置
```yaml
trans-msg:
  kafka:
    bootstrap-servers: 127.0.0.1:9092
  dynamic:
    message:
      order:
        success:
        - member
```
### 生产方调用 TransMessageSender.send
```java
@Service
public class OrderService {

    @Autowired
    private TransMessageSender transMessageSender;

    @Transactional
    public void placeOrder() {
        // Business process

        transMessageSender.send("success", orderDTO);
    }
}
```

### 消费方实现 TransMessageProcessor 接口（可以直接集成 AbstractTransMessageProcessor 抽象方法）
```java
@Component
public class OrderSuccessProcessor extends AbstractTransMessageProcessor<OrderDto> {
    @Override
    public String getProducer() {
        return "order";
    }

    @Override
    public String getTransType() {
        return "success";
    }

    @Override
    public TransProcessResult process(OrderDto body) {
        return TransProcessResult.SUCCESS;
    }
}
```

## 流程
### 订单业务
我们简化一下订单流程，假设下单成功后需要给用户赠送积分及优惠券：
![下单流程](https://github.com/lilineric/trans-msg-db/blob/main/img/grant-points.png)
我们一般在下单成功后推送订单成功的消息到 MQ ，而 Member 服务和 Coupon 服务则订阅这条消息进行处理，而 MQ 的 ACK 机制（如果你使用的 MQ 支持 ACK）可以保证消费端消费失败进行重试。

但问题在于因为网络原因推送 MQ 失败了怎么办？

当然，我们可以将推送 MQ 消息和下单业务放在一个事务里面，如果 MQ 消息推送失败了，则将下单进行回滚。但是 MQ 和数据库并不在一个网络环境中，这样会很大程度地增加事务失败的概率。

如果只要求原子性，并不在意事务的成功率呢？

然而事实上，即便是通过事务，也是没有办法保证原子性的。

推送 MQ 时返回失败或者成功，这是很清晰的语义，事务根据推送结果进行回滚或者提交就可以了，但是如果返回的是超时异常呢？这个时候是没有办法知道到底是成功还是失败的，可能失败了，也有可能最后执行成功了，只是超出了设置的超时时间。

本地消息表的分布式方案就是通过本地事务来保证业务操作和推送 MQ 的原子性的。

### 本地消息表
我们在业务数据库中建消息表，保证消息表和业务表在同一个数据库中，将订单操作和插入消息表放在同一个事务中，这样就可以依赖数据库本地事务来保证原子性。接着通过一个后台服务定时轮询消息表，将消息取出来重新投递到 MQ ，消费端消费完成后发消息删除消息表中的记录。这里因为重试机制导致很有可能出现重复消息，因此消费端是必须要保证幂等的。
![本地消息表](https://github.com/lilineric/trans-msg-db/blob/main/img/grant-points-trans.png)

上面说了将 推送 MQ 和下单业务放在一个事务中，会增加事务失败的概率，为什么使用本地消息表就不会呢（或者说影响极小）？

因为消息表和业务表是在同一个数据库中的，也是同样的网络环境，如果数据库或网络出了问题，那么即便不在同一个事务下单操作也会失败。下单的 DB 操作成功而插入消息表的操作失败，这种可能性极低。

## 设计
服务以 Jar 包方式提供，通过生产端和消费端两个注解拦截器来进行事务的注册和提交，除了需要在业务数据库中新建消息表，在代码层对业务基本没有侵入。
在 trans-msg-db 的设计中，数据库操作、MQ 消息推送和订阅、事务注册配置等功能都是以插件的形式提供，通过 SPI（Service Provider Interface） 注入的方式完成的。这意味这替换掉默认的插件实现，可以支持任意数据库、MQ。同样的，事务注册的配置信息既可以使用配置文件，也可以存放在数据库中，更推荐的方式是将配置放在配置中心（比如 Apollo、Nacos 等）。
![流程](https://github.com/lilineric/trans-msg-db/blob/main/img/trans-msg-db.png)
所有后台服务都和业务服务运行在同一个 Java 进程中。
