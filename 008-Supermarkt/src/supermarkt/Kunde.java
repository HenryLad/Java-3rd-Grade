package supermarkt;


public class Kunde {
   private int id;
   private double warenwert;

   private static int nextId = 1;

   private Kunde(){
      this.id = nextId++;
      this.warenwert = Math.random() * 100.00;
   }

   public static Kunde einkaufen(){
      Kunde kunde = new Kunde();
      System.out.println("Einkauf von " + kunde.toString());
      return kunde;
   }

   public String toString(){
      return String.format("Kunde %d mit Warenwert %.2f", id, warenwert);
   }  

   public int getId() {
      return id;
   }

   public double getWarenwert() {
      return warenwert;
   }

}