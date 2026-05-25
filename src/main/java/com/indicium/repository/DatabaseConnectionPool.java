package com.indicium.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionPool {
    private static HikariDataSource dataSource;

    static {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("database.properties")) {
            props.load(in);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            
            String user = props.getProperty("db.user");
            if (user != null && !user.trim().isEmpty()) {
                config.setUsername(user);
            }
            
            String pass = props.getProperty("db.password");
            if (pass != null && !pass.trim().isEmpty()) {
                config.setPassword(pass);
            }

            // Connection Pool optimizations
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);
            
            // HikariCP performance optimizations
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            System.err.println("CRITICAL: database.properties file not found!");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("CRITICAL: Failed to initialize database connection pool!");
            e.printStackTrace();
        }
    }

    private DatabaseConnectionPool() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("Database connection pool is not initialized.");
        }
        return dataSource.getConnection();
    }
}
