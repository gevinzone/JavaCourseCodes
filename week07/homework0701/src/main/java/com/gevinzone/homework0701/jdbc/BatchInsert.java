package com.gevinzone.homework0701.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

@Component
public class BatchInsert {
    @Autowired
    private DataSource dataSource;

    public void insertUsers(int count, String prefix) {
        String sql = "INSERT INTO `user` (username, `password`, salt, nickname, id_number, create_time, update_time)\n" +
                "VALUES (?,?,?,?,?,?,?)";
        Date now = new Date();
        if (prefix == null) {
            prefix = "default";
        }
//        int i = 1_000_000;
        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                for (int i = 1; i <= count; i++) {
                    statement.setObject(1, prefix + "user" + i);
                    statement.setObject(2, prefix + "user" + i);
                    statement.setObject(3, "salt");
                    statement.setObject(4, "abc.ab");
                    statement.setObject(5, "342156812409876519");
                    statement.setObject(6, now);
                    statement.setObject(7, now);
                    statement.addBatch();
                    if (i % 50000 == 0 || i == count) {
                        statement.executeBatch();
                        statement.clearBatch();
                    }
                }
//                statement.executeBatch();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

}
