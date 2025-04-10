package htl._014contactmanager.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class contact {

    private IntegerProperty id = new SimpleIntegerProperty();// id
    private StringProperty name = new SimpleStringProperty();
    private StringProperty number = new SimpleStringProperty();
    private StringProperty adress = new SimpleStringProperty();
    private ObjectProperty<ContactType> type = new SimpleObjectProperty<>(ContactType.NONE);
    private ObjectProperty<Location> location = new SimpleObjectProperty<>();

    public contact(){
    }

    public contact(int id, String name, String number, String adress){
        this.id.set(id);
        this.name.set(name);
        this.number.set(number);
        this.adress.set(adress);
        this.type.set(ContactType.NONE);
    }
    
    public contact(int id, String name, String number, String adress, ContactType type){
        this.id.set(id);
        this.name.set(name);
        this.number.set(number);
        this.adress.set(adress);
        this.type.set(type != null ? type : ContactType.NONE);
    }
    
    public contact(int id, String name, String number, String adress, ContactType type, Location location){
        this.id.set(id);
        this.name.set(name);
        this.number.set(number);
        this.adress.set(adress);
        this.type.set(type != null ? type : ContactType.NONE);
        this.location.set(location);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public String getAdress() {
        return adress.get();
    }

    public StringProperty adressProperty() {
        return adress;
    }
    
    public ContactType getType() {
        return type.get();
    }
    
    public void setType(ContactType type) {
        this.type.set(type);
    }
    
    public ObjectProperty<ContactType> typeProperty() {
        return type;
    }
    
    public Location getLocation() {
        return location.get();
    }
    
    public void setLocation(Location location) {
        this.location.set(location);
    }
    
    public ObjectProperty<Location> locationProperty() {
        return location;
    }

    @Override
    public String toString(){
        return "%d: %s (%s,%s)".formatted(getId(), getName(), getNumber(), getAdress());
    }

    public void setAdress(String text) {
        this.adress.set(text);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setNumber(String number) {
        this.number.set(number);
    }
}
