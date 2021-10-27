package com.gevinzone.homework0701.jdbc;

import com.gevinzone.homework0701.Homework0701Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class CmdRunner {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Homework0701Application.class, args);

//        Basic basic = context.getBean(Basic.class);
//        basic.insertUser();
        BatchInsert batchInsert = context.getBean(BatchInsert.class);
        int count = 1_000_000;
        String prefix = "A-";
        long start=System.currentTimeMillis();
        batchInsert.insertUsers(count, prefix);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start)/1000 + " s");
    }
}
