package stock;

import date.Date;

public class stock implements Cloneable {

    Date date;
    double quantity;

    public stock(Date date, double quantity) {
        this.date = date;
        this.quantity = quantity;
    }

    @Override
    public stock clone(){
        return new stock(date.clone(), quantity);
    }
    


}
