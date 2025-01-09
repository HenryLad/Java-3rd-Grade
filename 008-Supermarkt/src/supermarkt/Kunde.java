package supermarkt;

import java.util.concurrent.atomic.AtomicInteger;
class Kunde {
   private static final AtomicInteger idGenerator = new AtomicInteger(1);
   private final int id;
   private final double warenwert;

   private Kunde(double warenwert) {
       this.id = idGenerator.getAndIncrement();
       this.warenwert = warenwert;
   }

   public static Kunde einkaufen() {
       double warenwert = Math.random() * 100; // Einkaufswert zwischen 0 und 100 Euro
       Kunde kunde = new Kunde(warenwert);
       System.out.printf("Kunde %d erstellt mit Warenwert %.2f Euro.%n", kunde.id, kunde.warenwert);
       return kunde;
   }

   public double getWarenwert() {
       return warenwert;
   }

   public int getId() {
       return id;
   }
} 