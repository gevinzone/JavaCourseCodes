package com.gevinzone.homework0501.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Klass {
    private String name;
    private List<Student> students = new ArrayList<>(10);

    public void addStudent(Student student) {
        students.add(student);
    }
}
