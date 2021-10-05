查看线程状态：
```
jstack -l 9891
```

查看线程内对象占有的内存：
```
jmap -histo 9891
```

------------

-Xmx512m -Xms512m

```
wrk -c 40 -d30s http://localhost:8801
Running 30s test @ http://localhost:8801
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.29ms   13.81ms 289.35ms   99.03%
    Req/Sec     1.91k   596.67     3.99k    80.64%
  108974 requests in 30.07s, 14.76MB read
  Socket errors: connect 0, read 459216, write 33, timeout 0
Requests/sec:   3624.15
Transfer/sec:    502.81KB
```

```
wrk -c 40 -d30s http://localhost:8802
Running 30s test @ http://localhost:8802
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.89ms    4.59ms 158.91ms   99.17%
    Req/Sec   174.26     76.26   838.00     77.08%
  10324 requests in 30.08s, 5.99MB read
  Socket errors: connect 0, read 300768, write 78, timeout 0
Requests/sec:    343.18
Transfer/sec:    204.01KB
```

```
wrk -c 40 -d30s http://localhost:8803
Running 30s test @ http://localhost:8803
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.20ms   13.41ms 285.73ms   99.11%
    Req/Sec     1.36k   741.41     4.61k    61.83%
  76781 requests in 30.06s, 12.37MB read
  Socket errors: connect 0, read 461218, write 19, timeout 0
Requests/sec:   2554.00
Transfer/sec:    421.32KB
```

-----------

-Xmx2g -Xms2g

no sleep

```
wrk -c 40 -d30s http://localhost:8801
Running 30s test @ http://localhost:8801
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.01ms   18.21ms 288.28ms   98.64%
    Req/Sec     1.89k   773.48     3.16k    74.73%
  106657 requests in 30.02s, 14.47MB read
  Socket errors: connect 0, read 454592, write 24, timeout 0
Requests/sec:   3552.41
Transfer/sec:    493.63KB
```

```
wrk -c 40 -d30s http://localhost:8803
Running 30s test @ http://localhost:8803
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.21ms   13.35ms 279.80ms   99.07%
    Req/Sec     1.31k   730.81     3.22k    60.39%
  74241 requests in 30.09s, 12.17MB read
  Socket errors: connect 0, read 459023, write 30, timeout 0
Requests/sec:   2467.59
Transfer/sec:    414.31KB
```

```
wrk -c 40 -d30s http://localhost:8808
Running 30s test @ http://localhost:8808
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   419.25us    1.63ms  70.03ms   98.99%
    Req/Sec    50.92k     7.66k   59.32k    88.83%
  3040488 requests in 30.01s, 310.26MB read
Requests/sec: 101322.22
Transfer/sec:     10.34MB
```

sleep 20ms

```
wrk -c 40 -d30s http://localhost:8801
Running 30s test @ http://localhost:8801
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   913.93ms   83.97ms   1.00s    97.45%
    Req/Sec    21.87      8.72    50.00     75.22%
  1296 requests in 30.09s, 103.78KB read
  Socket errors: connect 0, read 1278, write 18, timeout 0
Requests/sec:     43.07
Transfer/sec:      3.45KB
```

线程数：Runtime.getRuntime().availableProcessors() * 2

```
wrk -c 40 -d30s http://localhost:8803
Running 30s test @ http://localhost:8803
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    57.94ms   11.04ms 126.06ms   52.03%
    Req/Sec   340.56     40.67   430.00     69.87%
  20381 requests in 30.06s, 1.59MB read
  Socket errors: connect 0, read 18969, write 1411, timeout 0
Requests/sec:    677.99
Transfer/sec:     54.33KB
```

线程数：Runtime.getRuntime().availableProcessors() * 4

```
wrk -c 40 -d30s http://localhost:8803
Running 30s test @ http://localhost:8803
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    28.68ms    6.61ms  99.64ms   73.40%
    Req/Sec   676.98     53.93   838.00     79.19%
  40466 requests in 30.03s, 3.17MB read
  Socket errors: connect 0, read 37264, write 3215, timeout 0
Requests/sec:   1347.69
Transfer/sec:    108.01KB
```

```
wrk -c 40 -d30s http://localhost:8808/test
Running 30s test @ http://localhost:8808/test
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    58.21ms   20.53ms 125.40ms   74.81%
    Req/Sec   344.16     36.16   424.00     70.10%
  20572 requests in 30.02s, 2.12MB read
Requests/sec:    685.38
Transfer/sec:     72.29KB
```