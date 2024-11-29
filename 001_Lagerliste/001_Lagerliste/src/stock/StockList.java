package stock;

public interface StockList {
    void store(ValuedStockMovement valuedStockMovement);
    void remove(stock stock);

    String getStockStatus();
    String getStockIngoings();
    String getStockOutgoings();


}
