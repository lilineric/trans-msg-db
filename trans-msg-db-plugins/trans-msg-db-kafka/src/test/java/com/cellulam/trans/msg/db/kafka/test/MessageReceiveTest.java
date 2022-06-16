package com.cellulam.trans.msg.db.kafka.test;

import com.cellulam.trans.msg.db.kafka.KafkaMQReceiver;
import com.cellulam.trans.msg.db.kafka.KafkaMQSender;
import com.cellulam.trans.msg.db.kafka.builder.TopicBuilder;
import com.cellulam.trans.msg.db.kafka.config.PropertiesLoader;
import com.cellulam.trans.msg.db.kafka.config.TransKafkaProperties;
import com.google.common.collect.Lists;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * @author eric.li
 * @date 2022-06-16 10:22
 */
public class MessageReceiveTest {
    private final Object lock = new Object();
    private String message = null;

    @Test
    public void test() throws Exception {
        String prefix = "t3";
        List<String> topics = Lists.newArrayList(TopicBuilder.buildTopic(prefix,"test-sender", "test"));
        TransKafkaProperties properties = PropertiesLoader.loadFromResource(this.getClass().getClassLoader().getResourceAsStream("trans-msg.yml"));
        properties.setGroupId("test3");

        Properties kafkaProperties = new Properties();
        kafkaProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        AdminClient adminClient = KafkaAdminClient.create(kafkaProperties);
        adminClient.deleteConsumerGroups(Lists.newArrayList("test3"));


        KafkaMQReceiver receiver = new KafkaMQReceiver(topics,
                properties, properties.getGroupInstanceId(), x -> {
            synchronized (lock) {
                message = x;
            }
            return true;
        });
        receiver.start();

        String msg = "test message " + System.currentTimeMillis();
        KafkaMQSender sender = new KafkaMQSender(prefix, properties);
        sender.send("test-sender", "test", msg);

        waitNotNull(() -> message != null);

        Assert.assertEquals(msg, message);
    }

    private void waitNotNull(Callable<Boolean> callable) throws Exception {
        long start = System.currentTimeMillis();
        while (!callable.call() && System.currentTimeMillis() - start < (30 * 1000)) {
            Thread.sleep(1000);
        }
    }

}
