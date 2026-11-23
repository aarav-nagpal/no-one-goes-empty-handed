package com.kbc.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Thread-safe Singleton DatabaseManager that manages database configurations
 * and provides a JDBC connection.
 */
public class DatabaseManager {

    private static volatile DatabaseManager instance;
    private final Properties properties = new Properties();
    private Connection connection;

    private DatabaseManager() {
        loadProperties();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Modern JDBC drivers are automatically registered via ServiceLoader,
            // but Class.forName is retained for explicit compatibility.
            System.err.println("Notice: MySQL Driver class registration: " + e.getMessage());
        }
    }

    /**
     * Returns the singleton instance of DatabaseManager using Double-Checked Locking.
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    /**
     * Loads database configuration properties from classpath or filesystem.
     */
    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                File file = new File("src/main/resources/config.properties");
                if (file.exists()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        properties.load(fis);
                    }
                } else {
                    File rootFile = new File("config.properties");
                    if (rootFile.exists()) {
                        try (FileInputStream fis = new FileInputStream(rootFile)) {
                            properties.load(fis);
                        }
                    } else {
                        System.err.println("Warning: config.properties could not be located.");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading config.properties: " + e.getMessage());
        }
    }

    /**
     * Returns an active java.sql.Connection. Reopens connection if closed or null.
     */
    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    /**
     * Retrieves the loaded configuration properties.
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Safely closes the active JDBC connection if open.
     */
    public synchronized void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
