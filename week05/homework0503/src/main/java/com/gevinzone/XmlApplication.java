package com.gevinzone;

import com.gevinzone.bo.Klass;
import com.gevinzone.bo.School;
import com.gevinzone.bo.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student201 = context.getBean("student201", Student.class);
        Student student202 = context.getBean("student202", Student.class);
        Klass class2 = context.getBean("class2", Klass.class);

        System.out.println(student201);
        System.out.println(student202);
        System.out.println(class2);

        Klass class3 = context.getBean("class3", Klass.class);
        System.out.println(class3);

        School school2 = context.getBean("school2", School.class);
        System.out.println(school2);

        Klass class4 = context.getBean("class4", Klass.class);
        System.out.println(class4);
        Klass class5 = context.getBean("class5", Klass.class);
        System.out.println(class5);
        Klass class6 = context.getBean("class6", Klass.class);
        System.out.println(class6);
    }
}
