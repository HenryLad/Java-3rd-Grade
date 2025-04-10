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
        // Create Contacts table with added contactType column
        String createContactTable = "CREATE TABLE IF NOT EXISTS CONTACTS (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(255) NOT NULL," +
                "ADDRESS VARCHAR(255) NOT NULL," +
                "PHONE VARCHAR(255) NOT NULL," +
                "CONTACT_TYPE VARCHAR(20) DEFAULT 'NONE')";
                
        // Create Location table
        String createLocationTable = "CREATE TABLE IF NOT EXISTS LOCATIONS (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "ZIPCODE VARCHAR(10) NOT NULL," +
                "CITY VARCHAR(255) NOT NULL)";
                
        // Modify CONTACTS table to add location reference - separate into two statements
        String addLocationColumn = "ALTER TABLE CONTACTS ADD COLUMN IF NOT EXISTS LOCATION_ID INT";
        String addForeignKeyConstraint = "ALTER TABLE CONTACTS ADD CONSTRAINT IF NOT EXISTS FK_CONTACT_LOCATION " +
                "FOREIGN KEY (LOCATION_ID) REFERENCES LOCATIONS(ID)";
                
        // Insert initial locations
        String insertLocations = "MERGE INTO LOCATIONS (ID, ZIPCODE, CITY) KEY(ID) VALUES " +
                "(1, '4020', 'Linz'), " +
                "(2, '4060', 'Leonding'), " +
                "(3, '4050', 'Traun'), " +
                "(4, '4030', 'Linz-Land'), " +
                "(5, '4040', 'Linz-Urfahr')";

        try {
            connection.createStatement().execute(createContactTable);
            connection.createStatement().execute(createLocationTable);
            
            // Execute the separated ALTER TABLE statements
            connection.createStatement().execute(addLocationColumn);
            connection.createStatement().execute(addForeignKeyConstraint);
            
            connection.createStatement().execute(insertLocations);
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
