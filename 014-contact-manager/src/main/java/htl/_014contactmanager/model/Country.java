package htl._014contactmanager.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for Country records stored in the database
 */
public class Country {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty code = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    
    public Country() {
    }
    
    public Country(int id, String code, String name) {
        this.id.set(id);
        this.code.set(code);
        this.name.set(name);
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
    
    public String getCode() {
        return code.get();
    }
    
    public void setCode(String code) {
        this.code.set(code);
    }
    
    public StringProperty codeProperty() {
        return code;
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    @Override
    public String toString() {
        // Only display the code as requested
        return code.get();
    }
}