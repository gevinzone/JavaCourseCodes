package com.gevinzone.homework0701.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;

@Component
public class Basic {
    @Autowired
    private final DataSource dataSource;

    public Basic(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int insertUser() {
        String sql = "INSERT INTO `user` (username, `password`, salt, nickname, id_number, create_time, update_time)\n" +
                "VALUES (?,?,?,?,?,?,?)";
        Date now = new Date();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setObject(1, "a");
                preparedStatement.setObject(2, "a");
                preparedStatement.setObject(3, "salt");
                preparedStatement.setObject(4, "abc.ab");
                preparedStatement.setObject(5, "342156812409876519");
                preparedStatement.setObject(6, now);
                preparedStatement.setObject(7, now);
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
}
