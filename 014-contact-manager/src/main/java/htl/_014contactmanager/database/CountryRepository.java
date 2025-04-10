package htl._014contactmanager.database;

import htl._014contactmanager.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for accessing and manipulating Country data in the database
 */
public class CountryRepository {

    private Connection connection;

    public CountryRepository() {
        connection = Database.getInstance().getConnection();
    }

    /**
     * Retrieves all countries from the database
     * 
     * @return List of all countries
     */
    public List<Country> getAllCountries() {
        List<Country> countryList = new ArrayList<>();
        String sql = "SELECT * FROM COUNTRIES ORDER BY CODE";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                countryList.add(new Country(
                        resultSet.getInt("ID"),
                        resultSet.getString("CODE"),
                        resultSet.getString("NAME")
                ));
            }
            return countryList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryList;
    }

    /**
     * Find a country by its ID
     * 
     * @param id The country ID to search for
     * @return The country with the given ID or null if not found
     */
    public Country findById(int id) {
        String sql = "SELECT * FROM COUNTRIES WHERE ID = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Country(
                        resultSet.getInt("ID"),
                        resultSet.getString("CODE"),
                        resultSet.getString("NAME")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}