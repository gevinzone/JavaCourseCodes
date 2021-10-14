package com.gevinzone.homework0501.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class Klass {
    private String name;
    @Resource(name = "student01")
    private Student squadLeader;
    private List<Student> students = new ArrayList<>(10);

    public  Klass(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }
}
