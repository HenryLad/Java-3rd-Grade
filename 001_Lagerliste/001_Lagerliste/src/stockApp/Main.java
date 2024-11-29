package stockApp;

import date.Date;
import stock.MovingAvgStockList;
import stock.StockListImpl;
import stock.ValuedStockMovement;

public class Main {

    public static void main(String[] args){
        StockListImpl stockList = new MovingAvgStockList();


        // Einlagern, Auslagern & Analysieren

        stockList.store(new ValuedStockMovement(
                new Date(2024,1,2),
                100,
                10
        ));
        stockList.store(new ValuedStockMovement(
                new Date(2024,1,2),
                100,
                11
        ));
        System.out.println(stockList.getStockStatus());



    }
}
