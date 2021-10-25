package com.gevinzone;

import com.gevinzone.bo.Klass;
import com.gevinzone.bo.School;
import com.gevinzone.bo.Student;
import com.gevinzone.config.SchoolConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class AnnotationApplication {
    public static void main(String ... args ) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SchoolConfiguration.class);
        Student student1 = context.getBean("student1", Student.class);
        Student student2 = context.getBean("student2", Student.class);
        Klass class1 = context.getBean("class1", Klass.class);

        System.out.println(student1);
        System.out.println(student2);
        System.out.println(class1);

        Student student201 = context.getBean("student201", Student.class);
        Student student202 = context.getBean("student202", Student.class);
        Klass class2 = context.getBean("class2", Klass.class);

        System.out.println(student201);
        System.out.println(student202);
        System.out.println(class2);

        School school = context.getBean("school1", School.class);
        System.out.println(school);

        Klass class4 = context.getBean("class4", Klass.class);
        System.out.println(class4);
        Klass class5 = context.getBean("class5", Klass.class);
        System.out.println(class5);
    }
}
