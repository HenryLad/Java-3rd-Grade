package htl._014contactmanager.database;

import htl._014contactmanager.model.Country;
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
    private CountryRepository countryRepository;
    private boolean hasCountryIdColumn = false;

    public LocationRepository() {
        connection = Database.getInstance().getConnection();
        countryRepository = new CountryRepository();
        checkTableColumns();
    }
    
    private void checkTableColumns() {
        try {
            // Check if COUNTRY_ID column exists
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'LOCATIONS' AND COLUMN_NAME = 'COUNTRY_ID'");
            ResultSet rs = stmt.executeQuery();
            hasCountryIdColumn = rs.next();
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                Country country = null;
                
                // Get country if column exists
                if (hasCountryIdColumn) {
                    try {
                        int countryId = resultSet.getInt("COUNTRY_ID");
                        if (!resultSet.wasNull()) {
                            country = countryRepository.findById(countryId);
                        }
                    } catch (Exception e) {
                        // Ignore column error
                    }
                }
                
                Location location = new Location(
                    resultSet.getInt("ID"),
                    resultSet.getString("ZIPCODE"),
                    resultSet.getString("CITY"),
                    country
                );
                
                locationList.add(location);
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
                    Country country = null;
                    
                    // Get country if column exists
                    if (hasCountryIdColumn) {
                        try {
                            int countryId = resultSet.getInt("COUNTRY_ID");
                            if (!resultSet.wasNull()) {
                                country = countryRepository.findById(countryId);
                            }
                        } catch (Exception e) {
                            // Ignore column error
                        }
                    }
                    
                    return new Location(
                        resultSet.getInt("ID"),
                        resultSet.getString("ZIPCODE"),
                        resultSet.getString("CITY"),
                        country
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}