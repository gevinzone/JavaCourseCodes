## 总结


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
4. young GC即年轻代的GC，young区比较小，处理也比较快，所以会把young GC又称之为minor GC
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

## 垃圾回收器



### 串行GC



单线程的GC，使用分别使用Serial/Serial Old进行Young区和Old区的垃圾回收，Young区使用复制算法，Old区使用标记整理算法



另，ParNew收集器是Serial收集器的多线程并行版本

### 并行GC



并行GC的目标是达到一个可控制的吞吐量(Throughput)。

吞吐量是处理器用于运行用户代码的时间与处理器总消耗时间的比值，即：运行用户代码时间/（运行用户代码时间 + GC时间）

并行GC，在young区使用Parallel Scavenge GC，old区使用Parallel Old GC



### CMS GC



CMS(Concurrent Mark Sweep) GC是一种以获取最短回收停顿时间为目标的收集器

运作的整个过程分为四个步骤，包括:

1. 初始标记(CMS initial mark)
2. 并发标记(CMS concurrent mark)
3. 重新标记(CMS remark)
4. 并发清除(CMS concurrent sweep)

其中初始标记、重新标记这两个步骤需要“Stop The World”

CM S是一款基于“标记-清除”算法实现的收集器，GC结束时会有大量空间碎片产生。空间碎片过多时，将会给大对象分配带来很大麻烦，往往会出现老年代还有很多剩余空间，但就是无法找到足够大的连续空间来分配当前对象，而不得不提前触发一次Full GC的情况。

### G1 GC

G1是一款主要面向服务端应用的垃圾收集器，开创了收集器面向局部收集的设计思路和基于Region的内存布局形式。

G1收集器的 运作过程大致可划分为以下四个步骤:

1. 初始标记(Initial Marking)：仅仅只是标记一下GC Roots能直接关联到的对象，并且修改TAMS 指针的值，让下一阶段用户线程并发运行时，能正确地在可用的Region中分配新对象。这个阶段需要停顿线程，但耗时很短，而且是借用进行Minor GC的时候同步完成的，所以G1收集器在这个阶段实际并没有额外的停顿。
2. 并发标记(Concurrent Marking)：从GC Root开始对堆中对象进行可达性分析，递归扫描整个堆里的对象图，找出要回收的对象，这阶段耗时较长，但可与用户程序并发执行。当对象图扫描完成以后，还要重新处理SATB记录下的在并发时有引用变动的对象。
3. 最终标记(Final M arking)：对用户线程做另一个短暂的暂停，用于处理并发阶段结束后仍遗留下来的最后那少量的SATB记录。
4. 筛选回收(Live Data Counting and Evacuation)：负责更新Region的统计数据，对各个Region的回收价值和成本进行排序，根据用户所期望的停顿时间来制定回收计划，可以自由选择任意多个Region 构成回收集，然后把决定回收的那一部分Region的存活对象复制到空的Region中，再清理掉整个旧 Region的全部空间。这里的操作涉及存活对象的移动，是必须暂停用户线程，由多条收集器线程并行 完成的。

从上述阶段的描述可以看出，G1收集器除了并发标记外，其余阶段也是要完全暂停用户线程的， 换言之，它并非纯粹地追求低延迟，官方给它设定的目标是在延迟可控的情况下获得尽可能高的吞吐量

### Shenandoah GC

Shenandoah是一款只有 OpenJDK才会包含，而OracleJDK里反而不存在的收集器，Shenandoah不仅要进行并发的垃圾标记，还要并发地进行对象清理后的整理动作。

Shenandoah收集器的工作过程大致可以划分为以下九个阶段：

1. 初始标记(Initial M arking)：与G1一样，首先标记与GC Roots直接关联的对象，这个阶段仍是“Stop The World”的，但停顿时间与堆大小无关，只与GC Roots的数量相关。
2. 并发标记(Concurrent Marking)：与G1一样，遍历对象图，标记出全部可达的对象，这个阶段是与用户线程一起并发的，时间长短取决于堆中存活对象的数量以及对象图的结构复杂程度。
3. 最终标记(Final M arking)：与G1一样，处理剩余的SATB扫描，并在这个阶段统计出回收价值最高的Region，将这些Region构成一组回收集(Collection Set)。最终标记阶段也会有一小段短暂的停顿。
4. 并发清理(Concurrent Cleanup)：这个阶段用于清理那些整个区域内连一个存活对象都没有找到 的Region(这类Region被称为Immediate Garbage Region)。
5. 并发回收(Concurrent Evacuation)：并发回收阶段是Shenandoah与之前HotSpot中其他收集器的核心差异。在这个阶段，Shenandoah要把回收集里面的存活对象先复制一份到其他未被使用的Region之中。并发回收阶段运行的时间长短取决于回收集的大小。
6. 初始引用更新(Initial Update Reference)：并发回收阶段复制对象结束后，还需要把堆中所有指向旧对象的引用修正到复制后的新地址，这个操作称为引用更新。引用更新的初始化阶段实际上并未做什么具体的处理，设立这个阶段只是为了建立一个线程集合点，确保所有并发回收阶段中进行的收集器线程都已完成分配给它们的对象移动任务而已。初始引用更新时间很短，会产生一个非常短暂的停顿。
7. 并发引用更新(Concurrent Update Reference)：真正开始进行引用更新操作，这个阶段是与用户线程一起并发的，时间长短取决于内存中涉及的引用数量的多少。并发引用更新与并发标记不同，它不再需要沿着对象图来搜索，只需要按照内存物理地址的顺序，线性地搜索出引用类型，把旧值改为新值即可。
8. 最终引用更新(Final Update Reference)：解决了堆中的引用更新后，还要修正存在于GC Roots 中的引用。这个阶段是Shenandoah的最后一次停顿，停顿时间只与GC Roots的数量相关。
9. 并发清理(Concurrent Cleanup)：经过并发回收和引用更新之后，整个回收集中所有的Region已再无存活对象，这些Region都变成Immediate Garbage Regions了，最后再调用一次并发清理过程来回收这些Region的内存空间，供以后新对象分配使用。



### ZGC

ZGC和Shenandoah的目标是高度相似的，都希望在尽可能对吞吐量影响不太大的前提下，实现在任意堆内存大小下都可以把垃圾收集的停顿时间限制在十毫秒以内的低延迟。

ZGC的运作过程大致可划分为以下四个大的阶段。全部四个阶段都是可以并发执行的，仅是两个阶段中间会存在短暂的停顿小阶段：

1. 并发标记(Concurrent Mark)：与G1、Shenandoah一样，并发标记是遍历对象图做可达性分析的阶段 ， 前后 也要经过类似于 G1、 Shenandoah的初始标记、最终标记的短暂停顿，而且这些停顿阶段所做的事情在目标上也是相类似的。与G1、Shenandoah不同的是，ZGC 的标记是在指针上而不是在对象上进行的，标记阶段会更新染色指针中的Marked 0、Marked 1标志位。
2. 并发预备重分配(Concurrent Prepare for Relocate)：这个阶段需要根据特定的查询条件统计得出本次收集过程要清理哪些Region，将这些Region组成重分配集(Relocation Set)。
3. 并发重分配(Concurrent Relocate)：重分配是ZGC执行过程中的核心阶段，这个过程要把重分 配集中的存活对象复制到新的Region上，并为重分配集中的每个Region维护一个转发表(Forward Table)，记录从旧对象到新对象的转向关系。
4. 并发重映射(Concurrent Remap)：重映射所做的就是修正整个堆中指向重分配集中旧对象的所有引用













