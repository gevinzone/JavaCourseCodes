# 目标

创建一个三个节点的redis 集群，每个节点一主一从


# 配置

## 1. 启动6个redis实例

这里启动的redis实例，需要在配置文件中启动集群模式，并配置相关参数，本文直接使用[redis官方参考文档](https://redis.io/topics/cluster-tutorial) 中提供的最简化配置：

```
port 7001
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
```

注意，这里的`cluster-config-file nodes.conf`，不是面向用户的配置，而是redis自动生成，用于自动持久化redis 集群的变动信息

分别创建7001-7006 6个文件夹，将上述配置信息，修改端口号后，保存为`redis.conf`至每个文件夹下，然后用`redis-server `命令启动6个集群实例实例，会显示每个实例均启动集群模式，等待连接：

```
                _._
           _.-``__ ''-._
      _.-``    `.  `_.  ''-._           Redis 6.2.6 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._
 (    '      ,       .-`  | `,    )     Running in cluster mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 7001
 |    `-._   `._    /     _.-'    |     PID: 27565
  `-._    `-._  `-./  _.-'    _.-'
 |`-._`-._    `-.__.-'    _.-'_.-'|
 |    `-._`-._        _.-'_.-'    |           https://redis.io
  `-._    `-._`-.__.-'_.-'    _.-'
 |`-._`-._    `-.__.-'    _.-'_.-'|
 |    `-._`-._        _.-'_.-'    |
  `-._    `-._`-.__.-'_.-'    _.-'
      `-._    `-.__.-'    _.-'
          `-._        _.-'
              `-.__.-'

27565:M 30 Nov 2021 11:00:20.263 # Server initialized
27565:M 30 Nov 2021 11:00:20.263 * Ready to accept connections
```

## 2. 创建集群


redis 5 及以上版本，创建集群的命令已经包含到`redis-cli`命令中，而redis3或4，需要使用`redis-trib`命令。

我们创建一个3个节点的集群，每个节点均一主一从，命令如下：

```
redis-cli --cluster create 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 127.0.0.1:7006 --cluster-replicas 1
```

执行该命令，redis开始创建集群，集群配置如下：

```
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 127.0.0.1:7005 to 127.0.0.1:7001
Adding replica 127.0.0.1:7006 to 127.0.0.1:7002
Adding replica 127.0.0.1:7004 to 127.0.0.1:7003
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: 9af2e293fbb8340617b8b4ff8b432e51b92bd7a1 127.0.0.1:7001
   slots:[0-5460] (5461 slots) master
M: ba9b64a18c79348aa97450b2aab7cfa7ecd66065 127.0.0.1:7002
   slots:[5461-10922] (5462 slots) master
M: ae741b3e62a1f4e53007563a54749b7c333eb28c 127.0.0.1:7003
   slots:[10923-16383] (5461 slots) master
S: 9d7312a47253ed89802a3b385ebd457c03fa6e2c 127.0.0.1:7004
   replicates ae741b3e62a1f4e53007563a54749b7c333eb28c
S: 09b7fa338ebc319846de2fdc1890c58ba416636e 127.0.0.1:7005
   replicates 9af2e293fbb8340617b8b4ff8b432e51b92bd7a1
S: 131b09fbc6358afecdc9e5dce7582484722967f5 127.0.0.1:7006
   replicates ba9b64a18c79348aa97450b2aab7cfa7ecd66065
Can I set the above configuration? (type 'yes' to accept): 
```

输入yes后，该配置生效:

```
>>> Performing Cluster Check (using node 127.0.0.1:7001)
M: 9af2e293fbb8340617b8b4ff8b432e51b92bd7a1 127.0.0.1:7001
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
M: ae741b3e62a1f4e53007563a54749b7c333eb28c 127.0.0.1:7003
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 9d7312a47253ed89802a3b385ebd457c03fa6e2c 127.0.0.1:7004
   slots: (0 slots) slave
   replicates ae741b3e62a1f4e53007563a54749b7c333eb28c
S: 09b7fa338ebc319846de2fdc1890c58ba416636e 127.0.0.1:7005
   slots: (0 slots) slave
   replicates 9af2e293fbb8340617b8b4ff8b432e51b92bd7a1
M: ba9b64a18c79348aa97450b2aab7cfa7ecd66065 127.0.0.1:7002
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
S: 131b09fbc6358afecdc9e5dce7582484722967f5 127.0.0.1:7006
   slots: (0 slots) slave
   replicates ba9b64a18c79348aa97450b2aab7cfa7ecd66065
```

节点实例上配置已经更新更新，如：

7001主节点：

```
27565:M 30 Nov 2021 11:04:41.474 # IP address for this node updated to 127.0.0.1
27565:M 30 Nov 2021 11:04:43.452 * Replica 127.0.0.1:7005 asks for synchronization
27565:M 30 Nov 2021 11:04:43.452 * Partial resynchronization not accepted: Replication ID mismatch (Replica asked for '7434a8fd9fb617f984e7f1b2adda182eb2975e36', my replication IDs are '4e5aa7e537f650cec3637e0948b787c474069b0f' and '0000000000000000000000000000000000000000')
27565:M 30 Nov 2021 11:04:43.452 * Replication backlog created, my new replication IDs are '03b9bae1fc0797db29dca285eae32f0b867b1777' and '0000000000000000000000000000000000000000'
27565:M 30 Nov 2021 11:04:43.452 * Starting BGSAVE for SYNC with target: disk
27565:M 30 Nov 2021 11:04:43.453 * Background saving started by pid 27675
27675:C 30 Nov 2021 11:04:43.455 * DB saved on disk
27565:M 30 Nov 2021 11:04:43.495 * Background saving terminated with success
27565:M 30 Nov 2021 11:04:43.495 * Synchronization with replica 127.0.0.1:7005 succeeded
27565:M 30 Nov 2021 11:04:46.428 # Cluster state changed: ok
```

其对应的从节点为7005:

```
27580:M 30 Nov 2021 11:04:41.434 # configEpoch set to 5 via CLUSTER SET-CONFIG-EPOCH
27580:M 30 Nov 2021 11:04:41.575 # IP address for this node updated to 127.0.0.1
27580:S 30 Nov 2021 11:04:43.451 * Before turning into a replica, using my own master parameters to synthesize a cached master: I may be able to synchronize with the new master with just a partial transfer.
27580:S 30 Nov 2021 11:04:43.451 * Connecting to MASTER 127.0.0.1:7001
27580:S 30 Nov 2021 11:04:43.451 * MASTER <-> REPLICA sync started
27580:S 30 Nov 2021 11:04:43.452 # Cluster state changed: ok
27580:S 30 Nov 2021 11:04:43.452 * Non blocking connect for SYNC fired the event.
27580:S 30 Nov 2021 11:04:43.452 * Master replied to PING, replication can continue...
27580:S 30 Nov 2021 11:04:43.452 * Trying a partial resynchronization (request 7434a8fd9fb617f984e7f1b2adda182eb2975e36:1).
27580:S 30 Nov 2021 11:04:43.454 * Full resync from master: 03b9bae1fc0797db29dca285eae32f0b867b1777:0
27580:S 30 Nov 2021 11:04:43.454 * Discarding previously cached master state.
27580:S 30 Nov 2021 11:04:43.495 * MASTER <-> REPLICA sync: receiving 175 bytes from master to disk
27580:S 30 Nov 2021 11:04:43.495 * MASTER <-> REPLICA sync: Flushing old data
27580:S 30 Nov 2021 11:04:43.495 * MASTER <-> REPLICA sync: Loading DB in memory
27580:S 30 Nov 2021 11:04:43.496 * Loading RDB produced by version 6.2.6
27580:S 30 Nov 2021 11:04:43.496 * RDB age 0 seconds
27580:S 30 Nov 2021 11:04:43.496 * RDB memory usage when created 2.74 Mb
27580:S 30 Nov 2021 11:04:43.496 # Done loading RDB, keys loaded: 0, keys expired: 0.
27580:S 30 Nov 2021 11:04:43.496 * MASTER <-> REPLICA sync: Finished with success
27580:S 30 Nov 2021 11:04:43.496 * Background append only file rewriting started by pid 27679
27580:S 30 Nov 2021 11:04:43.519 * AOF rewrite child asks to stop sending diffs.
27679:C 30 Nov 2021 11:04:43.519 * Parent agreed to stop sending diffs. Finalizing AOF...
27679:C 30 Nov 2021 11:04:43.519 * Concatenating 0.00 MB of AOF diff received from parent.
27679:C 30 Nov 2021 11:04:43.519 * SYNC append only file rewrite performed
27580:S 30 Nov 2021 11:04:43.596 * Background AOF rewrite terminated with success
27580:S 30 Nov 2021 11:04:43.596 * Residual parent diff successfully flushed to the rewritten AOF (0.00 MB)
27580:S 30 Nov 2021 11:04:43.596 * Background AOF rewrite finished successfully
```


# 集群使用

通过命令`redis-cli -c -p 7001` 与集群进行交互，如：

```
➜  ~ redis-cli -c -p 7001
127.0.0.1:7001> set k1 v1
-> Redirected to slot [12706] located at 127.0.0.1:7003
OK
127.0.0.1:7003> get k1
"v1"
127.0.0.1:7003> set k2 v2
-> Redirected to slot [449] located at 127.0.0.1:7001
OK
127.0.0.1:7001>
```

# 重新分片

可以通过该命令查看redis集群的详情：

```
redis-cli -p 7001 cluster nodes
```

```
ae741b3e62a1f4e53007563a54749b7c333eb28c 127.0.0.1:7003@17003 master - 0 1638250862535 3 connected 10923-16383
9af2e293fbb8340617b8b4ff8b432e51b92bd7a1 127.0.0.1:7001@17001 myself,master - 0 1638250862000 1 connected 0-5460
9d7312a47253ed89802a3b385ebd457c03fa6e2c 127.0.0.1:7004@17004 slave ae741b3e62a1f4e53007563a54749b7c333eb28c 0 1638250863038 3 connected
09b7fa338ebc319846de2fdc1890c58ba416636e 127.0.0.1:7005@17005 slave 9af2e293fbb8340617b8b4ff8b432e51b92bd7a1 0 1638250862026 1 connected
ba9b64a18c79348aa97450b2aab7cfa7ecd66065 127.0.0.1:7002@17002 master - 0 1638250863038 2 connected 5461-10922
131b09fbc6358afecdc9e5dce7582484722967f5 127.0.0.1:7006@17006 slave ba9b64a18c79348aa97450b2aab7cfa7ecd66065 0 1638250864049 2 connected
```

交互式重新分片：

```
redis-cli --cluster reshard 127.0.0.1:7000
```

结果：

```
➜  ~ redis-cli -p 7001 cluster nodes
ae741b3e62a1f4e53007563a54749b7c333eb28c 127.0.0.1:7003@17003 master - 0 1638251217515 3 connected 11922-16383
9af2e293fbb8340617b8b4ff8b432e51b92bd7a1 127.0.0.1:7001@17001 myself,master - 0 1638251218000 7 connected 0-6461 10923-11921
9d7312a47253ed89802a3b385ebd457c03fa6e2c 127.0.0.1:7004@17004 slave ae741b3e62a1f4e53007563a54749b7c333eb28c 0 1638251218629 3 connected
09b7fa338ebc319846de2fdc1890c58ba416636e 127.0.0.1:7005@17005 slave 9af2e293fbb8340617b8b4ff8b432e51b92bd7a1 0 1638251217515 7 connected
ba9b64a18c79348aa97450b2aab7cfa7ecd66065 127.0.0.1:7002@17002 master - 0 1638251218000 2 connected 6462-10922
131b09fbc6358afecdc9e5dce7582484722967f5 127.0.0.1:7006@17006 slave ba9b64a18c79348aa97450b2aab7cfa7ecd66065 0 1638251217616 2 connected
```


另外，分配可以通过这个命令直接执行：

```
redis-cli --cluster reshard <host>:<port> --cluster-from <node-id> --cluster-to <node-id> --cluster-slots <number of slots> --cluster-yes
```


# 测试fail over

当前redis集群，7001，7002和7003 是master节点，7004-7006是slave节点

## 1. slave节点down

7004 是7003的slave节点，7004down，此时leader每个节点都会收到消息，7004节点down，不影响集群使用，当7004再次被拉起后，leader通知每个节点7004又正常使用

## 2. master节点down

7003 是master节点，7003down时，7004 首先会与他的master失联，当leader确认7003不可达后，会重新对该节点选主，7004胜出成为新的master节点，集群正常工作；当7003重新拉起后，leader通知每个节点7003可达，并把7003设置为7004的slave节点

# 新增节点

新增节点，需要先已cluster模式启动一个新的redis服务，配置参考上文

## 新增主节点

我们创建一个新阶段7007，把它作为主节点加入到集群中，用如下命令：

```
redis-cli --cluster add-node 127.0.0.1:7007 127.0.0.1:7001
```

```
>>> Adding node 127.0.0.1:7007 to cluster 127.0.0.1:7001
>>> Performing Cluster Check (using node 127.0.0.1:7001)
M: 9af2e293fbb8340617b8b4ff8b432e51b92bd7a1 127.0.0.1:7001
   slots:[0-6461],[10923-11921] (7461 slots) master
   1 additional replica(s)
S: ae741b3e62a1f4e53007563a54749b7c333eb28c 127.0.0.1:7003
   slots: (0 slots) slave
   replicates 9d7312a47253ed89802a3b385ebd457c03fa6e2c
M: 9d7312a47253ed89802a3b385ebd457c03fa6e2c 127.0.0.1:7004
   slots:[11922-16383] (4462 slots) master
   1 additional replica(s)
S: 09b7fa338ebc319846de2fdc1890c58ba416636e 127.0.0.1:7005
   slots: (0 slots) slave
   replicates 9af2e293fbb8340617b8b4ff8b432e51b92bd7a1
M: ba9b64a18c79348aa97450b2aab7cfa7ecd66065 127.0.0.1:7002
   slots:[6462-10922] (4461 slots) master
   1 additional replica(s)
S: 131b09fbc6358afecdc9e5dce7582484722967f5 127.0.0.1:7006
   slots: (0 slots) slave
   replicates ba9b64a18c79348aa97450b2aab7cfa7ecd66065
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
>>> Send CLUSTER MEET to node 127.0.0.1:7007 to make it join the cluster.
[OK] New node added correctly.
```

## 新增从节点

新增从节点通常有2种方式：

（1）加入集群，由集群分配主节点

```
redis-cli --cluster add-node 127.0.0.1:7007 127.0.0.1:7001
```

```
...

[OK] All 16384 slots covered.
Automatically selected master 127.0.0.1:7007
>>> Send CLUSTER MEET to node 127.0.0.1:7008 to make it join the cluster.
Waiting for the cluster to join

>>> Configure node as replica of 127.0.0.1:7007.
[OK] New node added correctly.
```

（2）加入集群时显示声明主节点

```
redis-cli --cluster add-node 127.0.0.1:7009 127.0.0.1:7001 --cluster-slave --cluster-master-id ba9b64a18c79348aa97450b2aab7cfa7ecd66065
```

直接把新增的节点作为7002的slave节点

# 删除节点

通过该命令删除节点：

```
redis-cli --cluster del-node 127.0.0.1:7000 `<node-id>`
```

若删除从节点，可以直接删除，若删除主节点，首先要保证该master节点数据为空，若master节点不为空，可以通过`reshard`命令把数据移走，再移除


