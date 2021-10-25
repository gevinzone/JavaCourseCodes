package com.gevinzone.config;

import com.gevinzone.bo.Klass;
import com.gevinzone.bo.School;
import com.gevinzone.bo.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.Resource;

@Configuration
@ImportResource("classpath:applicationContext.xml")
public class SchoolConfiguration {
    @Resource
    Student student301;
    @Resource
    Student student302;
    @Resource
    Student student303;
    @Resource
    Student student304;
    @Resource
    Student student201;
    @Resource
    Student student202;

    @Bean(name = "student1")
    public Student getStudent1() {
        return new Student(1, "Student1");
    }

    @Bean(name = "student2")
    public Student getStudent2() {
        return new Student(2, "Student2");
    }

    @Bean(name = "class1")
    public Klass getClass1(Student student1, Student student2) {
        Klass klass = new Klass("class1", null);
        klass.addStudent(student1);
        klass.addStudent(student2);
        return klass;
    }

    @Bean(name = "school1")
    public School getSchool() {
        return new School();
    }

    @Bean(name = "class5")
    public Klass getClass5() {
        Klass klass = new Klass("class5", null);
        klass.addStudent(student301);
        klass.addStudent(student302);
        klass.addStudent(student303);
        klass.addStudent(student304);
        klass.addStudent(student201);
        klass.addStudent(student202);
        return klass;
    }
}
