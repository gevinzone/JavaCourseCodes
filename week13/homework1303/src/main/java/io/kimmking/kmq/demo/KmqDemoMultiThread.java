package io.kimmking.kmq.demo;

import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.core.KmqProducer;

import java.util.concurrent.atomic.AtomicLong;

public class KmqDemoMultiThread {
    public static void main(String[] args) throws Exception {
        String topic = "kk.test";
        KmqBroker broker = new KmqBroker();
        broker.createTopic(topic);
        final boolean[] flag = new boolean[1];
        flag[0] = true;

        int consumerCount = 2, producerCounts = 2;
        startConsumer(broker, topic, flag, consumerCount);
        runProducer(broker, topic, flag, producerCounts);
    }

    private static void startConsumer(KmqBroker broker, String topic, boolean[] flag, int threads) {
        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                KmqConsumer consumer = broker.createConsumer();
                consumer.subscribe(topic);
                while (flag[0]) {
                    KmqMessage<Order> message = consumer.poll(100);
                    if (null != message) {
                        System.out.println(message.getBody());
                    }
                }
                System.out.println("程序退出。");
            }).start();
        }
    }

    private static void runProducer(KmqBroker broker, String topic, boolean[] flag, int threads) throws Exception {
        AtomicLong idGen = new AtomicLong(1000L);
        for (int k = 0; k < threads; k++) {
            new Thread(() -> {
                KmqProducer producer = broker.createProducer();
                for (int i = 0; i < 1000; i++) {
                    Order order = new Order(idGen.incrementAndGet(), System.currentTimeMillis(), "USD2CNY", 6.51d);
                    producer.send(topic, new KmqMessage(null, order));
                }
            }).start();
        }
        Thread.sleep(500);
        System.out.println("点击任何键，发送一条消息；点击q或e，退出程序。");
        KmqProducer producer = broker.createProducer();
        while (true) {
            char c = (char) System.in.read();
            if(c > 20) {
                System.out.println(c);
                producer.send(topic, new KmqMessage(null, new Order(100000L + c, System.currentTimeMillis(), "USD2CNY", 6.52d)));
            }

            if( c == 'q' || c == 'e') break;
        }

        flag[0] = false;
    }
}
