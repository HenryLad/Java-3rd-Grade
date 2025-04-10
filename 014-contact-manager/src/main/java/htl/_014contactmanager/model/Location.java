package htl._014contactmanager.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for Location records stored in the database
 */
public class Location {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty zipCode = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    
    public Location() {
    }
    
    public Location(int id, String zipCode, String city) {
        this.id.set(id);
        this.zipCode.set(zipCode);
        this.city.set(city);
    }
    
    public int getId() {
        return id.get();
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    public String getZipCode() {
        return zipCode.get();
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode.set(zipCode);
    }
    
    public StringProperty zipCodeProperty() {
        return zipCode;
    }
    
    public String getCity() {
        return city.get();
    }
    
    public void setCity(String city) {
        this.city.set(city);
    }
    
    public StringProperty cityProperty() {
        return city;
    }
    
    @Override
    public String toString() {
        return zipCode.get() + " " + city.get();
    }
}