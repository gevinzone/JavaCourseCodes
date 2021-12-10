-------------------

# 说明

homework1301 由两部分组成：

1. 本markdown 文件，和kafka-config文件夹，是如何启动集群Kafka及性能测试的成果
2. spring-kafka-demo，是spring boot使用kafka的demo

-------------------

# 启动

启动Kafka 自带的zookeepr：

```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

启动三个Kafka实例：

```bash
bin/kafka-server-start.sh config/kafka9001.properties 
bin/kafka-server-start.sh config/kafka9002.properties
bin/kafka-server-start.sh config/kafka9003.properties
```

# 性能测试

生产者

```bash
bin/kafka-producer-perf-test.sh --topic test32 --num-records 100000 --record-size 1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9002
```

```
10002 records sent, 2000.0 records/sec (1.91 MB/sec), 3.8 ms avg latency, 286.0 ms max latency.
10004 records sent, 2000.8 records/sec (1.91 MB/sec), 0.6 ms avg latency, 4.0 ms max latency.
10002 records sent, 2000.4 records/sec (1.91 MB/sec), 1.0 ms avg latency, 44.0 ms max latency.
9954 records sent, 1988.0 records/sec (1.90 MB/sec), 0.6 ms avg latency, 4.0 ms max latency.
10062 records sent, 2011.6 records/sec (1.92 MB/sec), 1.0 ms avg latency, 45.0 ms max latency.
10002 records sent, 2000.4 records/sec (1.91 MB/sec), 0.8 ms avg latency, 44.0 ms max latency.
10004 records sent, 2000.0 records/sec (1.91 MB/sec), 0.8 ms avg latency, 35.0 ms max latency.
10004 records sent, 2000.0 records/sec (1.91 MB/sec), 1.0 ms avg latency, 51.0 ms max latency.
10005 records sent, 2000.2 records/sec (1.91 MB/sec), 0.7 ms avg latency, 4.0 ms max latency.
9917 records sent, 1983.4 records/sec (1.89 MB/sec), 0.7 ms avg latency, 51.0 ms max latency.
100000 records sent, 1998.441216 records/sec (1.91 MB/sec), 1.12 ms avg latency, 286.00 ms max latency, 1 ms 50th, 1 ms 95th, 25 ms 99th, 58 ms 99.9th.
```


```bash
bin/kafka-producer-perf-test.sh --topic test32 --num-records 100000 --record-size 1000 --throughput 20000 --producer-props bootstrap.servers=localhost:9002
```

```
83297 records sent, 16656.1 records/sec (15.88 MB/sec), 517.4 ms avg latency, 1191.0 ms max latency.
100000 records sent, 15845.349390 records/sec (15.11 MB/sec), 638.18 ms avg latency, 1324.00 ms max latency, 717 ms 50th, 1280 ms 95th, 1308 ms 99th, 1322 ms 99.9th.
```


消费者

```bash
bin/kafka-consumer-perf-test.sh --bootstrap-server localhost:9002 --topic test32 --fetch-size 1048576 --messages 100000 --threads 1
```

start.time, end.time, data.consumed.in.MB, MB.sec, data.consumed.in.nMsg, nMsg.sec, rebalance.time.ms, fetch.time.ms, fetch.MB.sec, fetch.nMsg.sec
2021-12-08 18:27:35:498, 2021-12-08 18:27:36:695, 95.7613, 80.0011, 100416, 83889.7243, 1638959255866, -1638959254669, -0.0000, -0.0001