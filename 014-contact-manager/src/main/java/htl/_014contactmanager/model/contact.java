package htl._014contactmanager.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class contact {


    private IntegerProperty id = new SimpleIntegerProperty();// id
    private StringProperty name = new SimpleStringProperty();
    private StringProperty number = new SimpleStringProperty();
    private StringProperty adress = new SimpleStringProperty();

    public contact(){

    }

    public contact(int id, String name, String number, String adress){
        this.id.set(id);
        this.name.set(name);
        this.number.set(number);
        this.adress.set(adress);
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
