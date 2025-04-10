package htl._014contactmanager.database;

import htl._014contactmanager.model.ContactType;
import htl._014contactmanager.model.Location;
import htl._014contactmanager.model.contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private Connection connection;
    private LocationRepository locationRepository;
    private boolean hasContactTypeColumn = false;
    private boolean hasLocationIdColumn = false;

    public ContactRepository() {
        connection = Database.getInstance().getConnection();
        locationRepository = new LocationRepository();
        checkTableColumns();
    }
    
    private void checkTableColumns() {
        try {
            // Check if CONTACT_TYPE column exists
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'CONTACTS' AND COLUMN_NAME = 'CONTACT_TYPE'");
            ResultSet rs = stmt.executeQuery();
            hasContactTypeColumn = rs.next();
            rs.close();
            stmt.close();
            
            // Check if LOCATION_ID column exists
            stmt = connection.prepareStatement(
                "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'CONTACTS' AND COLUMN_NAME = 'LOCATION_ID'");
            rs = stmt.executeQuery();
            hasLocationIdColumn = rs.next();
            rs.close();
            stmt.close();
            
            // If columns don't exist, try to add them
            if (!hasContactTypeColumn) {
                try {
                    connection.createStatement().execute(
                        "ALTER TABLE CONTACTS ADD COLUMN CONTACT_TYPE VARCHAR(20) DEFAULT 'NONE'");
                    hasContactTypeColumn = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if (!hasLocationIdColumn) {
                try {
                    connection.createStatement().execute(
                        "ALTER TABLE CONTACTS ADD COLUMN LOCATION_ID INT");
                    connection.createStatement().execute(
                        "ALTER TABLE CONTACTS ADD CONSTRAINT FK_CONTACT_LOCATION " +
                        "FOREIGN KEY (LOCATION_ID) REFERENCES LOCATIONS(ID)");
                    hasLocationIdColumn = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<contact> getAllContacts() {
        List<contact> contactList = new ArrayList<>();
        String sql = "SELECT * FROM CONTACTS";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                // Get the contact type from string
                ContactType type = ContactType.NONE;
                
                if (hasContactTypeColumn) {
                    try {
                        String typeStr = resultSet.getString("CONTACT_TYPE");
                        if (typeStr != null && !typeStr.isEmpty()) {
                            type = ContactType.valueOf(typeStr);
                        }
                    } catch (IllegalArgumentException | SQLException e) {
                        // Invalid type or column doesn't exist, use NONE as default
                    }
                }
                
                // Get location if available
                Location location = null;
                if (hasLocationIdColumn) {
                    try {
                        int locationId = resultSet.getInt("LOCATION_ID");
                        if (!resultSet.wasNull() && locationId > 0) {
                            location = locationRepository.findById(locationId);
                        }
                    } catch (SQLException e) {
                        // Column doesn't exist or other error
                    }
                }
                
                contact c = new contact(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("PHONE"),
                        resultSet.getString("ADDRESS"),
                        type,
                        location
                );
                contactList.add(c);
            }
            return contactList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void addContact(String name, String phone, String address, ContactType type, Location location) {
        String sql;
        if (hasContactTypeColumn && hasLocationIdColumn) {
            sql = "INSERT INTO CONTACTS (NAME, ADDRESS, PHONE, CONTACT_TYPE, LOCATION_ID) VALUES(?,?,?,?,?)";
        } else if (hasContactTypeColumn) {
            sql = "INSERT INTO CONTACTS (NAME, ADDRESS, PHONE, CONTACT_TYPE) VALUES(?,?,?,?)";
        } else if (hasLocationIdColumn) {
            sql = "INSERT INTO CONTACTS (NAME, ADDRESS, PHONE, LOCATION_ID) VALUES(?,?,?,?)";
        } else {
            sql = "INSERT INTO CONTACTS (NAME, ADDRESS, PHONE) VALUES(?,?,?)";
        }
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);
            
            int paramIndex = 4;
            if (hasContactTypeColumn) {
                pstmt.setString(paramIndex++, type != null ? type.name() : ContactType.NONE.name());
            }
            
            if (hasLocationIdColumn && paramIndex <= pstmt.getParameterMetaData().getParameterCount()) {
                if (location != null) {
                    pstmt.setInt(paramIndex, location.getId());
                } else {
                    pstmt.setNull(paramIndex, java.sql.Types.INTEGER);
                }
            }
            
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addContact(String name, String phone, String address) {
        addContact(name, phone, address, ContactType.NONE, null);
    }

    public void updateContact(contact contact) {
        String sql = "UPDATE CONTACTS SET NAME = ?, PHONE = ?, ADDRESS = ?";
        
        if (hasContactTypeColumn) {
            sql += ", CONTACT_TYPE = ?";
        }
        
        if (hasLocationIdColumn) {
            sql += ", LOCATION_ID = ?";
        }
        
        sql += " WHERE ID = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getNumber());
            pstmt.setString(3, contact.getAdress());
            
            int paramIndex = 4;
            
            if (hasContactTypeColumn) {
                pstmt.setString(paramIndex++, contact.getType() != null ? 
                    contact.getType().name() : ContactType.NONE.name());
            }
            
            if (hasLocationIdColumn) {
                if (contact.getLocation() != null) {
                    pstmt.setInt(paramIndex++, contact.getLocation().getId());
                } else {
                    pstmt.setNull(paramIndex++, java.sql.Types.INTEGER);
                }
            }
            
            pstmt.setInt(paramIndex, contact.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteContact(int id) {
        String sql = "DELETE FROM CONTACTS WHERE ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
