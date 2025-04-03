package htl._014contactmanager.database;

import htl._014contactmanager.model.contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private Connection connection;

    public ContactRepository() {
        connection = Database.getInstance().getConnection();
    }

    public List<contact> getAllContacts() {
        List<contact> contactList = new ArrayList<>();
        String sql = "SELECT * FROM CONTACTS";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                contactList.add(new contact(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("PHONE"),
                        resultSet.getString("ADDRESS")
                ));
            }
            return contactList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addContact(String name, String phone, String address) {
        String sql = "INSERT INTO CONTACTS (NAME, ADDRESS, PHONE) VALUES(?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateContact(contact contact) {
        String sql = "UPDATE CONTACTS SET NAME = ?, PHONE = ?, ADDRESS = ? WHERE ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getNumber());
            pstmt.setString(3, contact.getAdress());
            pstmt.setInt(4, contact.getId());
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
