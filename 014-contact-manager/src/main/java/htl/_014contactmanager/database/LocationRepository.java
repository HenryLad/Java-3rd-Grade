package htl._014contactmanager.database;

import htl._014contactmanager.model.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for accessing and manipulating Location data in the database
 */
public class LocationRepository {

    private Connection connection;

    public LocationRepository() {
        connection = Database.getInstance().getConnection();
    }

    /**
     * Retrieves all locations from the database
     * 
     * @return List of all locations
     */
    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<>();
        String sql = "SELECT * FROM LOCATIONS ORDER BY ZIPCODE";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                locationList.add(new Location(
                        resultSet.getInt("ID"),
                        resultSet.getString("ZIPCODE"),
                        resultSet.getString("CITY")
                ));
            }
            return locationList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationList;
    }

    /**
     * Find a location by its ID
     * 
     * @param id The location ID to search for
     * @return The location with the given ID or null if not found
     */
    public Location findById(int id) {
        String sql = "SELECT * FROM LOCATIONS WHERE ID = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Location(
                        resultSet.getInt("ID"),
                        resultSet.getString("ZIPCODE"),
                        resultSet.getString("CITY")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}