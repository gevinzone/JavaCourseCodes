package com.gevin.jdbc;

import java.sql.*;

public class RawSql {
    private void printStudentInfo(ResultSet resultSet) throws SQLException {
        System.out.println("Student Info:");
        System.out.printf("id: %d, name: %s, class: %d, create_time: %s %n",
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getDate(4).toString()
        );
    }
    public void getStudents() {
        String sql = "select id, name, klass, create_time from student";
        try (Connection connection = Db.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    System.out.println("=============");
                    printStudentInfo(resultSet);
                }
                System.out.println("=============");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void getStudent() {
        String sql = "SELECT id, `name`, klass, create_time FROM student WHERE id = ?";
        try (Connection connection = Db.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setObject(1, 1);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    return;
                }
                printStudentInfo(resultSet);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int insertStudent() {
        String sql = "INSERT INTO student (`name`, klass) VALUES (?, ?)";
        try (Connection connection = Db.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setObject(1, "new student 001");
                preparedStatement.setObject(2, 2);
                int n = preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println(resultSet.getInt(1));
                }
                return n;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public int updateStudent() {
        String sql = "UPDATE student SET klass = ? WHERE `name`= ?";
        int n = 0;
        try (Connection connection = Db.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setObject(1, 1);
                ps.setObject(2, "new student 001");
                n = ps.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return n;
    }

    public int deleteStudent() {
        String sql = "DELETE FROM student WHERE `name`= ?";
        try (Connection connection = Db.getConnection()){
           try (PreparedStatement ps = connection.prepareStatement(sql)) {
               ps.setObject(1, "new student 001");
               return ps.executeUpdate();
           }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public static void main(String ... args) throws SQLException {
        RawSql instance = new RawSql();
        instance.getStudents();
        instance.getStudent();
        int result = 0;
        result = instance.insertStudent();
        result = instance.updateStudent();
        result = instance.deleteStudent();

        System.out.println(result);
    }
}
