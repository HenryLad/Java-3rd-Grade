package supermarkt;

import java.util.Queue;


class KundenGen extends Thread {
   private final Queue<Kunde> queue;
   private volatile boolean aktiv = true;

   public KundenGen(Queue<Kunde> queue) {
       this.queue = queue;
   }

   public void stoppen() {
       aktiv = false;
   }

   @Override
   public void run() {
       try {
           while (aktiv) {
               Thread.sleep((long) (Math.random() * 500)); // Simuliert den Zeitraum zwischen eintreffenden Kunden
               Kunde neuerKunde = Kunde.einkaufen();
               synchronized (queue) {
                   queue.offer(neuerKunde);
                   queue.notifyAll();
               }
           }
       } catch (InterruptedException ignore) {
           Thread.currentThread().interrupt();
       }
   }
}