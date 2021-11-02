# 单例的写法



*************

「核心设计」

把构造函数设置为私有，不允许外部创建类的实例，通过静态方法`getInstance()`获取唯一实例。

**************

## 1. 饿汉式



设计类的私有静态实例`instance`，在类加载的时候，`instance` 就已经创建并初始化好了，所以，`instance `实例的创建过程是线程安全的。

通过静态方法`getInstance()`返回`instance`时，不存在竞态条件，也是线程安全的。

优点：简单，线程安全，性能高

缺点：不支持延迟加载，如果实例占用资源多或初始化耗时长，提前初始化实例是资源

```java

public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private static final IdGenerator instance = new IdGenerator();
  private IdGenerator() {}
  public static IdGenerator getInstance() {
    return instance;
  }
  public long getId() { 
    return id.incrementAndGet();
  }
}
```



## 2. 懒汉式



不事先初始化`instance`，在`getInstance()`方法中增加一步，判断如果`instance==null`，则先创建实例。

优点：简单，支持懒加载

缺点：为保证线程安全，`getInstance()`需要上锁同步，并发度低，当单例被频繁用到时，会成为性能瓶颈

```java

public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private static IdGenerator instance;
  private IdGenerator() {}
  public static synchronized IdGenerator getInstance() {
    if (instance == null) {
      instance = new IdGenerator();
    }
    return instance;
  }
  public long getId() { 
    return id.incrementAndGet();
  }
}
```



## 3. 双重检测



为提高懒汉式的并发能力，减小锁的颗粒度。

优点：既支持懒加载，又支持高并发

缺点：代码逻辑复杂，容易写错

```java

public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private static IdGenerator instance;
  private IdGenerator() {}
  public static IdGenerator getInstance() {
    if (instance == null) {
      synchronized(IdGenerator.class) { // 此处为类级别的锁
        if (instance == null) {
          instance = new IdGenerator();
        }
      }
    }
    return instance;
  }
  public long getId() { 
    return id.incrementAndGet();
  }
}
```



说明：

1. 同步块内外两次检查`instance==null`，是为了防止出现并发时，几个线程相继获得锁进入同步块后，分别创建instance实例，多次赋值
2. 本代码在多线程条件下存在竞态条件，需要考虑`instance`变量的可见性和赋值的原子性，故代码逻辑复杂，易出错：
   1. `instance`要对全部线程可见，这个通过`synchronized`或`volatile`关键字保证，本代码基于前者
   2. 赋值的原子性，即`instance = new IdGenerator()`这一行，在旧版本的java（java7及以下），本语句编译后不是原子的，若发生指令重排，其他线程可能拿到错误的`instance`对象
3. 基于第2-2点，旧版本的java中，为保证并发时的正确性，`instance`的声明语句需要`volatile`关键字避免指令重排，即要改为`private volatile static IdGenerator instance;`

## 4. 使用静态内部类



使用静态内部类，能达到“双重检测”一样的效果，且实现更简单，不易出错。

优点：高并发、线程安全、支持懒加载、简单

缺点：暂无

```java

public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private IdGenerator() {}

  private static class SingletonHolder{
    private static final IdGenerator instance = new IdGenerator();
  }
  
  public static IdGenerator getInstance() {
    return SingletonHolder.instance;
  }
 
  public long getId() { 
    return id.incrementAndGet();
  }
}
```



说明：

1. `SingletonHolder` 是一个静态内部类，当外部类 `IdGenerator `被加载的时候，并不会创建 `SingletonHolder` 实例对象；只有当调用 `getInstance()` 方法时，`SingletonHolder` 才会被加载，这个时候才会创建 `instance`
2. instance 的唯一性、创建过程的线程安全性，都由 JVM 来保证
3. 现在若使用Intelij Idea 写单例，写一个“双重检测”的单例实现后，idea会自动提示重构为“使用内部静态类”的方式



## 5. 其他方法



对于Java语言而言，还可以通过枚举类型实现单例，它是通过 Java 枚举类型本身的特性，保证了实例创建的线程安全性和实例的唯一性：

```java

public enum IdGenerator {
  INSTANCE;
  private AtomicLong id = new AtomicLong(0);
 
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

