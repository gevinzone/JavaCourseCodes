package com.gevinzone.mq.activemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

@Slf4j
public class ActiveMQTopicApplication {
    private static final String mqUrl = "tcp://127.0.0.1:61616";
    public static void main(String[] args) {
//        Destination topicDestination = new ActiveMQTopic("gevin.topic");
//        testTopicDestination(topicDestination);
        Destination queueDestination = new ActiveMQQueue("gevin.queue");
//        testQueueDestination(queueDestination);
//        testMultiThreadProductionLine(3, queueDestination, false);
        testMultiThreadProductionLine(3, queueDestination, true);

    }

    private static void testDestination(Destination destination, boolean isMultiConsumer) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(mqUrl);
        try {
            ActiveMQConnection conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            ProductionLine.consume(session, destination);
            if (isMultiConsumer) {
                // 让更多消费者消费消息
                ProductionLine.consume(session, destination);
                ProductionLine.consume(session, destination);
            }
            ProductionLine.produce(session, destination);
            Thread.sleep(2000);
            session.close();
            conn.close();
        } catch (JMSException | InterruptedException e) {
            log.info(e.getMessage());
        }
    }

    private static void testTopicDestination(Destination topicDestination) {
        testDestination(topicDestination, true);
    }
    private static void testQueueDestination(Destination queueDestination) {
        testDestination(queueDestination, false);
        // 即使用多个消费者，一个消息也只能被消费一次
        // 由于在同一个session中，consumer间不存在并发问题，同一时间只有一个consumer收到消息
        testDestination(queueDestination, true);
    }

    private static void testMultiThreadProductionLine(int threads, Destination destination, boolean isMultiConsumerPerThread) {
        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                testDestination(destination, isMultiConsumerPerThread);
            }).start();
        }
    }


}
