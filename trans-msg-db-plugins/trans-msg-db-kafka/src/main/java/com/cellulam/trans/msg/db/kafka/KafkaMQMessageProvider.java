package com.cellulam.trans.msg.db.kafka;

import com.cellulam.trans.msg.db.kafka.builder.TopicBuilder;
import com.cellulam.trans.msg.db.kafka.config.PropertiesLoader;
import com.cellulam.trans.msg.db.kafka.config.TransKafkaProperties;
import com.cellulam.trans.msg.db.kafka.exceptions.MessageRoleException;
import com.cellulam.trans.msg.db.spi.MessageProviderSPI;
import com.cellulam.trans.msg.db.spi.contract.MessageProcessor;
import com.cellulam.trans.msg.db.spi.contract.MessageSender;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.trans.db.facade.Constants;
import com.trans.db.facade.ConsumerRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * @author eric.li
 * @date 2022-06-15 14:26
 */
@Slf4j
public class KafkaMQMessageProvider implements MessageProviderSPI {
    private MessageSender producerMsgSender;
    private MessageSender consumerMsgSender;

    private MessageProcessor producerProcessor;
    private MessageProcessor consumerProcessor;

    private String appName = null;
    private List<ConsumerRegister> consumerRegisters = null;
    private List<String> transTypes = null;

    private boolean isProducer = false;
    private boolean isConsumer = false;

    private String topicPrefix = null;
    private String ackTopicPrefix = null;


    private final TransKafkaProperties properties;

    public KafkaMQMessageProvider() {
        this.properties = PropertiesLoader.loadFromResource(this.getClass().getClassLoader()
                .getResourceAsStream(Constants.YAML_RESOURCE_NAME));
    }

    @Override
    public void init(String appName,
                     List<ConsumerRegister> consumerRegisters,
                     List<String> transTypes) {
        this.appName = appName;

        this.fillProperties();

        this.topicPrefix = TopicBuilder.buildTopicPrefix();
        this.ackTopicPrefix = TopicBuilder.buildAckTopicPrefix();

        if(CollectionUtils.isNotEmpty(consumerRegisters)) {
            this.consumerRegisters = consumerRegisters;
            this.isConsumer = true;
        }

        if(CollectionUtils.isNotEmpty(transTypes)) {
            this.transTypes = transTypes;
            this.isProducer = true;
            this.createTopic();
        }

        this.producerMsgSender = new KafkaMQSender(topicPrefix, properties);
        this.consumerMsgSender = new KafkaMQSender(ackTopicPrefix, properties);
    }

    private void createTopic() {
        if(!this.properties.isAutoCreateTopic()) {
            return;
        }
        Set<String> topics = Sets.newHashSet();
        for (String transType : this.transTypes) {
            topics.add(TopicBuilder.buildTopic(topicPrefix, this.appName, transType));
            topics.add(TopicBuilder.buildTopic(ackTopicPrefix, this.appName, transType));
        }
        new KafkaAdmin(this.properties).createTopic(topics);
    }

    private void fillProperties() {
        if (StringUtils.isEmpty(this.properties.getGroupId())) {
            this.properties.setGroupId(this.appName);
        }
    }

    @Override
    public void registerMessageConsumerProcessor(MessageProcessor processor) {
        this.consumerProcessor = processor;
    }

    @Override
    public void registerMessageProducerProcessor(MessageProcessor processor) {
        this.producerProcessor = processor;
    }

    @Override
    public MessageSender getProducerMsgSender() {
        if(!isProducer) {
            throw new MessageRoleException("It is not producer");
        }
        return this.producerMsgSender;
    }

    @Override
    public MessageSender getConsumerMsgSender() {
        if(!isConsumer) {
            throw new MessageRoleException("It is not consumer");
        }
        return this.consumerMsgSender;
    }

    @Override
    public void start() {
        this.startConsumerReceiver();
        this.startAckReceiver();
    }

    private void startAckReceiver() {
        if (!isProducer) {
            log.info("It is not trans producer, no need to register ACK receiver");
            return;
        }

        List<String> topics = Lists.newArrayList();
        for (String transType : this.transTypes) {
            topics.add(TopicBuilder.buildTopic(this.ackTopicPrefix,
                    this.appName,
                    transType));
        }
        new KafkaMQReceiver(topics, properties,
                StringUtils.isEmpty(properties.getGroupInstanceId()) ? null : properties.getGroupInstanceId() + ".ack",
                this.producerProcessor).start();

    }

    private void startConsumerReceiver() {
        if (!isConsumer) {
            log.info("It is not trans consumer, no need to register consumer receiver");
            return;
        }
        List<String> topics = Lists.newArrayList();
        for (ConsumerRegister consumerRegister : this.consumerRegisters) {
            topics.add(TopicBuilder.buildTopic(TopicBuilder.buildTopicPrefix(),
                    consumerRegister.getProducer(),
                    consumerRegister.getTransType()));
        }
        new KafkaMQReceiver(topics, properties, properties.getGroupInstanceId(), this.consumerProcessor)
                .start();
    }

    @Override
    public String getType() {
        return "kafka";
    }
}
