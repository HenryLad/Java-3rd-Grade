package stock;

import date.Date;

public class ValuedStockMovement extends stock implements Cloneable{
    double pricePerUnit;

    public ValuedStockMovement(Date date, double quantity, double pricePerUnit) {
        super(date, quantity); // Aufrufung des Konstrutkers der Super Klasse muss in der ersten Zeile
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public String toString() {
        return  String.format("%s %7.2f EH je EUR %7.2f      EURO %10.2f ",
                date.toString(),
                quantity,
                pricePerUnit,
                quantity*pricePerUnit
                );
    }
    @Override
    public ValuedStockMovement clone() {
        return new ValuedStockMovement(date.clone(), quantity, pricePerUnit);
    }

    public double getValue(){
        return quantity*pricePerUnit;
    }


}
