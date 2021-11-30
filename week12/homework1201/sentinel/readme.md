# Redis Sentinel

关键配置项：

```
sentinel monitor mymaster 127.0.0.1 6380 2
sentinel down-after-milliseconds mymaster 10000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1
```

## 1. 启动redis主从数据库

启动主从三个redis: 6379, 6380, 6381，6379为主，另外两个为从


## 2. 启动redis sentinel

```
redis-sentinel sentinel0.conf
redis-sentinel sentinel1.conf
```

这时，可以看到sentinel在监控redis主从数据库：

```
23451:X 29 Nov 2021 20:33:52.819 # Sentinel ID is 8d992c54df8f8677b0b345825f61fb733c73d14c
23451:X 29 Nov 2021 20:33:52.819 # +monitor master mymaster 127.0.0.1 6379 quorum 2
23451:X 29 Nov 2021 20:33:52.820 * +slave slave 127.0.0.1:6381 127.0.0.1 6381 @ mymaster 127.0.0.1 6379
23451:X 29 Nov 2021 20:33:52.821 * +slave slave 127.0.0.1:6380 127.0.0.1 6380 @ mymaster 127.0.0.1 6379
23451:X 29 Nov 2021 20:34:19.341 * +sentinel sentinel 8d992c54df8f8677b0b345825f61fb733c73d14d 127.0.0.1 26380 @ mymaster 127.0.0.1 6379
```


## 3. 监控主从库

### 3.1 从库down

停止从库6380，会监控到从库少了一个，但主库不变：

```
23451:X 29 Nov 2021 20:43:52.505 # +sdown slave 127.0.0.1:6380 127.0.0.1 6380 @ mymaster 127.0.0.1 6379
```

当6380重新启动后，sentinel会监控到并自动将其配置为从库

```
23451:X 29 Nov 2021 20:48:03.391 * +reboot slave 127.0.0.1:6380 127.0.0.1 6380 @ mymaster 127.0.0.1 6379
23451:X 29 Nov 2021 20:48:03.446 # -sdown slave 127.0.0.1:6380 127.0.0.1 6380 @ mymaster 127.0.0.1 6379
```

### 3.2 主库down

停止主库6379，会从新选主，并把6379标记为从库，然后标记从库6379 down：

```
23470:X 29 Nov 2021 20:53:18.869 # +sdown master mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:18.953 # +odown master mymaster 127.0.0.1 6379 #quorum 2/2
23470:X 29 Nov 2021 20:53:18.953 # +new-epoch 1
23470:X 29 Nov 2021 20:53:18.953 # +try-failover master mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:18.955 # +vote-for-leader 8d992c54df8f8677b0b345825f61fb733c73d14d 1
23470:X 29 Nov 2021 20:53:18.957 # 8d992c54df8f8677b0b345825f61fb733c73d14c voted for 8d992c54df8f8677b0b345825f61fb733c73d14d 1
23470:X 29 Nov 2021 20:53:19.027 # +elected-leader master mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:19.027 # +failover-state-select-slave master mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:19.080 # +selected-slave slave 127.0.0.1:6380 127.0.0.1 6380 @ mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:19.080 * +failover-state-send-slaveof-noone slave 127.0.0.1:6380 127.0.0.1 6380 @ mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:19.148 * +failover-state-wait-promotion slave 127.0.0.1:6380 127.0.0.1 6380 @ mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:20.025 # +promoted-slave slave 127.0.0.1:6380 127.0.0.1 6380 @ mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:20.025 # +failover-state-reconf-slaves master mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:20.106 * +slave-reconf-sent slave 127.0.0.1:6381 127.0.0.1 6381 @ mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:20.905 * +slave-reconf-inprog slave 127.0.0.1:6381 127.0.0.1 6381 @ mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:20.905 * +slave-reconf-done slave 127.0.0.1:6381 127.0.0.1 6381 @ mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:20.971 # +failover-end master mymaster 127.0.0.1 6379
23470:X 29 Nov 2021 20:53:20.971 # +switch-master mymaster 127.0.0.1 6379 127.0.0.1 6380
23470:X 29 Nov 2021 20:53:20.972 * +slave slave 127.0.0.1:6381 127.0.0.1 6381 @ mymaster 127.0.0.1 6380
23470:X 29 Nov 2021 20:53:20.972 * +slave slave 127.0.0.1:6379 127.0.0.1 6379 @ mymaster 127.0.0.1 6380
23470:X 29 Nov 2021 20:53:31.029 # +sdown slave 127.0.0.1:6379 127.0.0.1 6379 @ mymaster 127.0.0.1 6380
```

当6379再次上线后，会成为从库：

```
23470:X 29 Nov 2021 20:57:37.557 # -sdown slave 127.0.0.1:6379 127.0.0.1 6379 @ mymaster 127.0.0.1 6380
```


