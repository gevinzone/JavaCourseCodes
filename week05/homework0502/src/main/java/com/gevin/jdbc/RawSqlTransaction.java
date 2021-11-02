package com.gevin.jdbc;

import java.sql.*;

public class RawSqlTransaction {
    public void useTransaction() throws SQLException {
        Connection connection = Db.getConnection();
        try {
            connection.setAutoCommit(false);
            String studentX = "new student x";
            insertStudent(connection, studentX, insertKlass(connection));
            connection.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    private int insertKlass(Connection connection) throws SQLException {
        String sql = "INSERT INTO Klass (`name`) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, "classX");
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1);
        }
    }

    private void insertStudent(Connection connection, String name, int classId) throws SQLException {
        String sql = "INSERT INTO student (`name`, klass) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, name);
            ps.setObject(2, classId);
            ps.executeUpdate();
        }
    }

    public static void main(String[] args) throws SQLException {
        RawSqlTransaction instance = new RawSqlTransaction();
        instance.useTransaction();
    }
}
