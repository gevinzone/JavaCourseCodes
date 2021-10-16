package com.gevin.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RawSqlBatch {
    public void batchInsert() throws SQLException {
        int batchSize = 5;
        List<Student> students = Student.createStudents(batchSize);
        String sql = "INSERT INTO student (`name`, klass) VALUES (?, ?)";
        try(Connection connection = Db.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                for (Student student : students) {
                    ps.setString(1, student.name);
                    ps.setInt(2, student.classId);
                    ps.addBatch();
                }
                int[] ns = ps.executeBatch();
                for (int n : ns) {
                    System.out.println(n + " record inserted");
                }
            }
        }

    }

    private static class Student {
        private final String name;
        private final int classId;

        public Student(String name, int classId) {
            this.name = name;
            this.classId = classId;
        }

        public static List<Student> createStudents(int batchSize) {
            List<Student> list = new ArrayList<>(batchSize * 2);
            for (int i = 0; i < batchSize; i++) {
                list.add(new Student("batch_student_" + i + 1, 2));
            }
            return list;
        }
    }

    public static void main(String[] args) throws SQLException {
        RawSqlBatch instance = new RawSqlBatch();
        instance.batchInsert();
    }
}

