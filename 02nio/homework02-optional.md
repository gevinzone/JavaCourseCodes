

运行课上的例子，以及 Netty 的例子，分析相关现象



# （一）单线程VS多线程

虚拟机参数设置为 -Xmx512m， -Xms512m时，用wrk压测8801，8802和8803三个服务，测试结果如下：

```bash
➜  ~ wrk -c 40 -d30s http://localhost:8801
Running 30s test @ http://localhost:8801
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.79ms   18.31ms 297.15ms   98.82%
    Req/Sec     2.21k   624.17     3.68k    83.87%
  123890 requests in 30.05s, 16.54MB read
  Socket errors: connect 0, read 502857, write 44, timeout 0
Requests/sec:   4123.44
Transfer/sec:    563.64KB
```

```bash
➜  ~ wrk -c 40 -d30s http://localhost:8802
Running 30s test @ http://localhost:8802
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.79ms    3.97ms 165.98ms   99.52%
    Req/Sec   176.18     81.23   820.00     74.39%
  10279 requests in 30.02s, 6.16MB read
  Socket errors: connect 0, read 306902, write 66, timeout 0
Requests/sec:    342.41
Transfer/sec:    210.05KB
```

```bash
➜  ~ wrk -c 40 -d30s http://localhost:8803
Running 30s test @ http://localhost:8803
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.68ms   17.09ms 306.85ms   98.79%
    Req/Sec     1.63k   729.83     3.41k    66.55%
  91705 requests in 30.03s, 13.63MB read
  Socket errors: connect 0, read 478224, write 31, timeout 0
Requests/sec:   3054.27
Transfer/sec:    464.94KB
```



解释：

1. 8801虽然是同步阻塞，但服务性能表现最好。这是由于我们的服务逻辑比较简单，单线程足以应对，而使用多线程时，由于增加了线程切换带来的开销，性能反而会下降；但单线程本身存在性能瓶颈，由于服务器本身只能排队串行处理请求，若某个请求开销比较大，会阻塞服务器对其他请求对处理，造成性能下降
2. 8802虽然异步非阻塞，但服务器性能表现最差。这是由于该服务器每接受到一个请求，就会创建一个新线程去响应该请求，Java中的线程使用的是操作系统层级的线程，创建和销毁线程的开销比较大，故表现最差
3. 8803服务器使用了线程池，理论上性能表现最好，一方面它通过多线程使服务器具备了并发处理请求的能力，避免了单线程的问题，另一方面，线程池可以重复利用已创建的线程，避免了重复创建线程带来的性能损失
4. 可以修改代码来验证第三点，比如在请求响应的代码中，增加`TimeUnit.NANOSECONDS.sleep(10)`,8801会因阻塞性能下降，8803表现会更好，验证结果如下：

```bash
➜  ~ wrk -c 40 -d30s http://localhost:8801
Running 30s test @ http://localhost:8801
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    62.42ms    2.37ms  73.87ms   89.22%
    Req/Sec   318.63     16.00   373.00     73.17%
  19063 requests in 30.07s, 1.49MB read
  Socket errors: connect 0, read 19164, write 8, timeout 0
Requests/sec:    634.01
Transfer/sec:     50.83KB
```



```bash
➜  ~ wrk -c 40 -d30s http://localhost:8802
Running 30s test @ http://localhost:8802
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.96ms   10.89ms 192.15ms   98.56%
    Req/Sec     2.95k   829.42     7.24k    79.79%
  172630 requests in 30.05s, 22.76MB read
  Socket errors: connect 0, read 323749, write 7842, timeout 0
Requests/sec:   5744.74
Transfer/sec:    775.48KB
```



```bash
➜  ~ wrk -c 40 -d30s http://localhost:8803
Running 30s test @ http://localhost:8803
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.47ms   20.22ms 254.60ms   97.80%
    Req/Sec     4.98k     1.45k    7.63k    77.40%
  283198 requests in 30.03s, 31.69MB read
  Socket errors: connect 0, read 404755, write 20540, timeout 0
Requests/sec:   9431.05
Transfer/sec:      1.06MB
```



若阻塞时间更长，使其远大于创建线程的时间时，8802相比于8803的线程池，有更多的线程能并发执行，比8803有更好的性能，但由于线程资源相对稀缺，如果系统中还有其他业务逻辑，这种方案依然有可能降低系统整体的性能。

使用`TimeUnit.MILLISECONDS.sleep(20);`，验证如下：



```bash
➜  ~ wrk -c 40 -d30s http://localhost:8801
Running 30s test @ http://localhost:8801
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   919.36ms   84.99ms 999.25ms   97.51%
    Req/Sec    21.40      6.75    50.00     63.00%
  1287 requests in 30.09s, 103.13KB read
  Socket errors: connect 0, read 1285, write 2, timeout 0
Requests/sec:     42.77
Transfer/sec:      3.43KB
```



```bash
➜  ~ wrk -c 40 -d30s http://localhost:8802
Running 30s test @ http://localhost:8802
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    24.30ms    2.99ms  91.28ms   88.12%
    Req/Sec   795.38     70.80     1.01k    78.43%
  47635 requests in 30.09s, 3.73MB read
  Socket errors: connect 0, read 45416, write 2217, timeout 0
Requests/sec:   1583.15
Transfer/sec:    126.86KB
```



```bash
➜  ~ wrk -c 40 -d30s http://localhost:8803
Running 30s test @ http://localhost:8803
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    29.46ms    7.23ms 108.52ms   73.44%
    Req/Sec   662.10     58.49   818.00     81.38%
  39576 requests in 30.03s, 3.10MB read
  Socket errors: connect 0, read 36754, write 2822, timeout 0
Requests/sec:   1317.93
Transfer/sec:    105.61KB
```



-----------

# （二）多路复用和Netty

Netty 使用的是多路复用IO，可以由一个线程同时监听多个请求，然后异步响应请求，从而进一步提高了吞吐量。

虚拟机参数设置为 -Xmx512m， -Xms512m时，用wrk压测8808服务，将有更好的性能：



```bash
➜  ~ wrk -c 40 -d30s http://localhost:8808
Running 30s test @ http://localhost:8808
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   831.61us    7.07ms 176.57ms   99.11%
    Req/Sec    51.54k     8.41k   79.79k    86.81%
  3072203 requests in 30.10s, 313.50MB read
Requests/sec: 102059.98
Transfer/sec:     10.41MB
```



`TimeUnit.NANOSECONDS.sleep(10)`:

```
➜  ~ wrk -c 40 -d30s http://localhost:8808
Running 30s test @ http://localhost:8808
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.04ms    5.64ms 147.87ms   99.55%
    Req/Sec     5.31k   312.35     6.19k    89.13%
  316945 requests in 30.07s, 32.34MB read
Requests/sec:  10540.29
Transfer/sec:      1.08MB
```



当handler中有比较长时间的阻塞时，netty的性能会急剧下降，此时，应将阻塞的业务逻辑放到单独的线程池中单独处理

`TimeUnit.MILLISECONDS.sleep(20)`:

```bash
➜  ~ wrk -c 40 -d30s http://localhost:8808/test1
Running 30s test @ http://localhost:8808/test1
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    58.09ms   21.27ms 214.65ms   74.84%
    Req/Sec   346.76     41.98   430.00     73.39%
  20689 requests in 30.05s, 2.13MB read
Requests/sec:    688.55
Transfer/sec:     72.62KB
```



