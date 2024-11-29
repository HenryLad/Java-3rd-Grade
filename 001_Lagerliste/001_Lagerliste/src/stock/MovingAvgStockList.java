package stock;

public class MovingAvgStockList extends StockListImpl {

    // Aktueller Lagerbestand
    private  ValuedStockMovement stock;

    public MovingAvgStockList() {
        stock = null;
    }



    @Override
    public void store(ValuedStockMovement valuedStockMovement) {
        if(stock == null) {
            // WICHTIG: Im Lager wird ein neues Objekt
            //abgelegt, damit es keine Weschelwirkung
            // mit dem übergeordneten Programmsystem gibt,
            // da wir vorhaben, eigenschaften des Objekts (z.b. Menge)
            // zu verändern

            stock = valuedStockMovement.clone();
        }
        else {
            double totalQuantity = stock.quantity + valuedStockMovement.quantity;
            double totalValue = stock.quantity * stock.pricePerUnit
                    +  valuedStockMovement.quantity * valuedStockMovement.pricePerUnit;
            double avgPricePerUnit = totalQuantity / totalValue;

            stock.quantity = totalQuantity;
            stock.pricePerUnit = avgPricePerUnit;
            stock.date = valuedStockMovement.date.clone();

        }
        ingoings.put(valuedStockMovement.clone());
    }



    @Override
    public void remove(stock stock) {
        if(stock == null) {
            return;
        }

        ValuedStockMovement outgoing = new ValuedStockMovement(stock.date.clone(),0 ,this.stock.pricePerUnit);

        if(stock.quantity <= this.stock.quantity) {
            outgoing.quantity = stock.quantity;
            stock = null;
        }else {
            outgoing.quantity = stock.quantity;
            stock.quantity -= this.stock.quantity;
        }

        outgoings.put(outgoing);


    }

    @Override
    public String getStockStatus() {
        if(stock == null) {
            return "\n";
        }
        return stock.toString() + "\n";
    }




}
