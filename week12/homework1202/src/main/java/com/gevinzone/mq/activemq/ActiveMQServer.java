package com.gevinzone.mq.activemq;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;

import java.net.URI;

public class ActiveMQServer {
    public static void main(String[] args) throws Exception {
        // 尝试用java代码启动一个ActiveMQ broker server
        // 然后用前面的测试demo代码，连接这个嵌入式的server
        startBroker();

    }

    private static void startBroker() throws Exception {
        BrokerService broker = new BrokerService();
//        broker.addConnector("tcp://localhost:61616");
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI("tcp://localhost:61616"));
        broker.addConnector(connector);
        broker.start();

        broker.waitUntilStopped();
    }


}
