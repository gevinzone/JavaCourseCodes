package com.gevinzone.homework0701.mybatis;

import com.gevinzone.homework0701.Homework0701Application;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

@MapperScan("com.gevinzone.homework0701.mybatis.mapper")
public class MyBatisRunner {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Homework0701Application.class, args);
        MyBatisBatchInsert myBatisBatchInsert = context.getBean(MyBatisBatchInsert.class);
        int total = 1_000_000;
        String prefix = "M-";
        long start=System.currentTimeMillis();
        myBatisBatchInsert.batchInsertUser(total, prefix);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start)/1000 + " s");
    }
}
