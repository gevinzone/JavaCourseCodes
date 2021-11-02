package com.gevin.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbPool {

    private final DataSource dataSource;

    public DbPool() throws IOException {
        Properties properties = this.getDbProperties();
        String url = properties.getProperty("datasource.url");
        String username = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");

        dataSource = initHikariConfig(url, username, password);

    }
    private Properties getDbProperties() throws IOException {
        Properties properties = new Properties();

        try (InputStream dbStream = Db.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (dbStream == null) {
                throw new IOException("database.properties is not found");
            }
            properties.load(dbStream);
            return properties;
        }
    }

    private DataSource initHikariConfig(String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("connectionTimeout", "1000"); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "60000"); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "10"); // 最大连接数：10
        return new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
