package com.gevin.jdbc;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;

public class RawSqlWithPool {
    private final DataSource dataSource;
    public RawSqlWithPool(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int insertSchool(String schoolName) throws SQLException {
        String sql = "INSERT into school (`name`) VALUES (?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, schoolName);
                ps.executeUpdate();
                ResultSet resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
                return 0;
            }
        }
    }

    public static void main(String[] args ) throws IOException, SQLException {
        DbPool pool = new DbPool();
        DataSource dataSource = pool.getDataSource();
        RawSqlWithPool instance = new RawSqlWithPool(dataSource);
        int id = instance.insertSchool("schoolX");
        System.out.println(id);
    }
}
