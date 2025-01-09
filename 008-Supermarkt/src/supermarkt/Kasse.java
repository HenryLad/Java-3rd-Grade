package supermarkt;

import java.util.Queue;

class Kasse extends Thread {
   private double saldo = 0.0;
   private final Queue<Kunde> queue;
   private volatile boolean offen = true;

   public Kasse(String name, Queue<Kunde> queue) {
       super(name);
       this.queue = queue;
   }

   public double getSaldo() {
       return saldo;
   }

   public void schliessen() {
       offen = false;
       synchronized (queue) {
           queue.notifyAll();
       }
   }

   @Override
   public void run() {
       try {
           while (offen || !queue.isEmpty()) {
               Kunde kunde;
               synchronized (queue) {
                   while (queue.isEmpty() && offen) {
                       queue.wait();
                   }
                   kunde = queue.poll();
               }
               if (kunde != null) {
                   Thread.sleep((long) (Math.random() * 1000)); // Simuliert die Zeit des Bezahlens
                   saldo += kunde.getWarenwert();
                   System.out.printf("%s hat Kunde %d mit %.2f Euro abgerechnet.\n",
                           getName(), kunde.getId(), kunde.getWarenwert());
               }
           }
       } catch (InterruptedException ignore) {
           Thread.currentThread().interrupt();
       }
       System.out.printf("%s hat einen Saldo von %.2f Euro.%n", getName(), getSaldo());
   }
}