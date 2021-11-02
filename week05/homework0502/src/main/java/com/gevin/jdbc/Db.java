package com.gevin.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Db {
    private static String url;
    private static String username;
    private static String password;

    static {
        init();
    }

    private static void init() {
        Properties properties = new Properties();

        InputStream dbStream = Db.class.getClassLoader().getResourceAsStream("database.properties");
        if (dbStream == null) {
            System.out.println("database.properties not found");
            return;
        }

        try {
            properties.load(dbStream);
            String driver = properties.getProperty("datasource.driver");
            url = properties.getProperty("datasource.url");
            username = properties.getProperty("datasource.username");
            password = properties.getProperty("datasource.password");
            Class.forName(driver);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                dbStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
