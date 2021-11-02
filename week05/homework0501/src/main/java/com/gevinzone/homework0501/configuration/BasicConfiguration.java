package com.gevinzone.homework0501.configuration;

import com.gevinzone.homework0501.bo.Klass;
import com.gevinzone.homework0501.bo.School;
import com.gevinzone.homework0501.bo.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class BasicConfiguration {
    @Bean(name = "student01")
    public Student getStudent01() {
        return new Student(1, "Student01");
    }

    @Bean(name = "student02")
    public Student getStudent02() {
        return new Student(2, "Student02");
    }

    @Bean(name = "class1")
    public Klass getKlass1(Student student01, Student student02) {
        Klass klass = new Klass();
        klass.setName("Class1");
        klass.addStudent(student01);
        klass.addStudent(student02);
        return klass;
    }

    @Bean(name = "class3")
    public Klass getKlass3(Student student01, Student student02) {
        return new Klass("Class3", Arrays.asList(student01, student02));
    }

    @Bean
    public School getSchool() {
        return new School();
    }

}
