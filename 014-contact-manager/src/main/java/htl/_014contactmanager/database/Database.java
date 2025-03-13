package htl._014contactmanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;

    private static final String URL = "jdbc:h2:tcp://localhost:9092/./contactDb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Connection connection;

    private Database() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Database getInstance() {
        synchronized (Database.class) {
            if (instance == null) {
                instance = new Database();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void initialize() {
        String createContactTable = "CREATE TABLE IF NOT EXISTS CONTACTS (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(255) NOT NULL," +
                "ADDRESS VARCHAR(255) NOT NULL," +
                "PHONE VARCHAR(255) NOT NULL)";

        try {
            connection.createStatement().execute(createContactTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
