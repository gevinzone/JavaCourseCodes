# 作业要求

（选做）使用 [GCLogAnalysis.java](./GCLogAnalysis.java) 自己演练一遍 串行/并行/CMS/G1 的案例。
（选做）使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。
（必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结

# 总结


对`GCLogAnalysis` 这个java程序的执行，查看垃圾回收情况，可以分别用以下命令，查看不同垃圾回收算法下的状态：


```bash
java -XX:+PrintGCDetails GCLogAnalysis
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx1g -Xms1g GCLogAnalysis
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.demo.log -Xmx1g -Xms1g GCLogAnalysis
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseSerialGC -Xmx1g -Xms1g GCLogAnalysis
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseParallelGC -Xmx1g -Xms1g GCLogAnalysis
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseConcMarkSweepGC -Xmx1g -Xms1g GCLogAnalysis
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseG1GC -Xmx1g -Xms1g GCLogAnalysis
java -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+UseG1GC -Xmx1g -Xms1g GCLogAnalysis
```

通过命令运行，进一步理解了以下内容：

1. java进程的内存结构，分为栈、堆、非堆、JVM自身和Direct Memory，其中，栈内主要存放的是线程内局部变量、操作数栈和返回值等数据，而进程内的大部分可共享的变量，存储于堆中
2. 堆内存，为便于垃圾回收，分成了年轻代和老年代两部分，新建的数据，存放于年轻代中，如果年轻代中的数据经过多次垃圾回收依然存在，将在接下来的垃圾回收中转移到老年代去；一次Young GC后，young区内存的减少量，与java进程内存的总体减少量之差，就是从Young区转移到Old区的数据
3. GC包括young GC，old GC，和full GC
4. young GC及年轻代的GC，young区比较小，处理也比较快，所以会把young GC又称之为minor GC
5. 通常在多轮young GC后，会触发full GC，full GC 包括了young区GC、old区GC和非堆的GC，old区很少单独触发GC，非堆的GC，对静态变量的GC比较容易，但对已加载类的GC判断比较麻烦，非堆GC非必须实现
6. java8 默认并行GC，自适应参数是打开的
7. 若java虚拟机堆内存太小，使得对象创建所占的内存比虚拟机能提供的内存（包含垃圾回收对内存的释放）大时，会出现OOM异常
8. 若堆内存设置比较大，则GC的频率会变低，但每次GC时需要释放的内存也会变大，使用并行GC等算法时，GC导致的STW时间会变长


## GC算法

比较简单粗暴的垃圾标记，是采用引用计数的方式，但若存在循环依赖的情况，该方法就失效了，会造成内存泄露甚至内存溢出，因此现在的GC均不采用这种算法。

现在GC算法的主要逻辑，是使用标记-清除、标记-复制，或标记-整理算法。

这些算法中，标记对象，不采用引用计数的方式，而是通过可达性分析，即从GC Root开始，利用dfs或bfs等算法，标记可达对象，然后再用上述三种方法清除不可达对象（反回来亦可）。

（1）标记-清除

标记后，直接删除标记为垃圾的对象

该算法的主要问题是，gc后，可能造成较多的内存碎片，程序执行过程中，若创建较大的对象，可能由于无法找到合适的连续内存，不得不提前触发下一轮GC

（2）标记-复制

这种算法主要用于年轻代，把年轻代分为Eden，Survivor0（S0） 和Survivor1（S1）三个区，S0和S1两区一定有一个为空，这里假设S0为空，标记后，Eden区和S0区需要留存的对象均复制到S1区，然后删除Eden区和S0区全部数据

该算法主要问题是，会有一部分内存浪费，故通常Eden、S0和S1三区的比例设为8:1:1

（3）标记-整理

该算法主要用于老年代，逻辑与标记-清除算法类似，但不直接清除垃圾，而是先把存活对象移动到内存的一端，然后再清除边界以外的内存。

该算法的主要问题是，由于存活对象依然应用与程序中，对象移动操作必须暂停用户程序才能进行，会造成STW


## GC的实现













