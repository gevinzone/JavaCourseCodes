package com.gevinzone.homework0501;

import com.gevinzone.homework0501.bo.Klass;
import com.gevinzone.homework0501.bo.School;
import com.gevinzone.homework0501.bo.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@Slf4j
@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class Homework0501Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Homework0501Application.class, args);
//        ApplicationContext context2 = new ClassPathXmlApplicationContext("applicationContext.xml");

        log.debug("beans form ConfigurableApplicationContext...");
        Student student01 = (Student) context.getBean("student01");
        Student student02 = (Student) context.getBean("student02");
        Klass class1 = (Klass) context.getBean("class1");

        log.info(student01.toString());
        log.info(student02.toString());
        log.info(class1.toString());

        log.debug("beans from ClassPathXmlApplicationContext...");
        Student student03 = (Student) context.getBean("student03");
        Student student04 = (Student) context.getBean("student04");
        Klass class2 = (Klass) context.getBean("class2");

        log.info(class2.toString());
        for (Student student : class2.getStudents()) {
            log.info(student.toString());
        }
        log.info(student03.toString());
        log.info(student04.toString());

        School school = context.getBean(School.class);
        log.info(school.toString());

//        log.info("   context.getBeanDefinitionNames() ===>> "+ String.join(",", context.getBeanDefinitionNames()));
        Student s101 = (Student) context.getBean("student101");
        log.info(s101.toString());
        Student s102 = (Student) context.getBean("student102");
        log.info(s102.toString());
    }

}
