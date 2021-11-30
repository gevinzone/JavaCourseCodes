# 1. 启动redis实例

由于要在同一台机器上起多个redis实例，所以要确保每个实例有不同的配置，关键配置项：


```
port 6379
pidfile "/var/run/redis_6379.pid"
dir "/Users/gevin/data/redis/redis6379"
```

启动redis 服务：

```
redis-server redis6379.conf
redis-server redis6380.conf
```

# 2. 设置主从

## 2.1 在客户端动态配置

通过如下命令可以从客户端访问redis实例

```
redis-cli -p 6379
redis-cli -p 6380
```

把6380配置为6379的从节点，可以从客户端进入6380后，执行该命令：

```
slaveof 127.0.0.1 6379
```

此时，从master写入数据后，slave里也能读到：

```
127.0.0.1:6379> set key1 value1
OK
127.0.0.1:6379> set key2 value2
OK
127.0.0.1:6379> keys *
1) "key2"
2) "port"
3) "key1"
```


```
127.0.0.1:6380> keys *
1) "key2"
2) "key1"
3) "port"
127.0.0.1:6380> get key1
"value1"
127.0.0.1:6380> get key2
"value2"

```

## 2.2 直接把从库信息写到配置文件

按如下配置，启动新的redis实例，可以直接成为6379的从节点

新增配置文件redis6381，配置内容与6380类似，然后最后加一行：

```
replicaof 127.0.0.1 6379
```

这时启动该redis，直接作为slave从6379上同步数据(也可设置为6380，从6380上同步数据)：

```
22339:S 29 Nov 2021 19:40:31.548 # Server initialized
22339:S 29 Nov 2021 19:40:31.549 * Ready to accept connections
22339:S 29 Nov 2021 19:40:31.549 * Connecting to MASTER 127.0.0.1:6379
22339:S 29 Nov 2021 19:40:31.549 * MASTER <-> REPLICA sync started
22339:S 29 Nov 2021 19:40:31.549 * Non blocking connect for SYNC fired the event.
22339:S 29 Nov 2021 19:40:31.549 * Master replied to PING, replication can continue...
22339:S 29 Nov 2021 19:40:31.549 * Partial resynchronization not possible (no cached master)
22339:S 29 Nov 2021 19:40:31.550 * Full resync from master: 16344c453a912a1dcbc9c912ea17a5ead2d2b3ae:919
22339:S 29 Nov 2021 19:40:31.649 * MASTER <-> REPLICA sync: receiving 216 bytes from master to disk
22339:S 29 Nov 2021 19:40:31.649 * MASTER <-> REPLICA sync: Flushing old data
22339:S 29 Nov 2021 19:40:31.649 * MASTER <-> REPLICA sync: Loading DB in memory
22339:S 29 Nov 2021 19:40:31.650 * Loading RDB produced by version 6.2.6
22339:S 29 Nov 2021 19:40:31.650 * RDB age 0 seconds
22339:S 29 Nov 2021 19:40:31.650 * RDB memory usage when created 2.10 Mb
22339:S 29 Nov 2021 19:40:31.650 # Done loading RDB, keys loaded: 3, keys expired: 0.
22339:S 29 Nov 2021 19:40:31.650 * MASTER <-> REPLICA sync: Finished with success
```
