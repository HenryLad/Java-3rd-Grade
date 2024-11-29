package supermarkt;

import java.util.Queue;

public class KundenGen extends Thread {

   private Queue<Kunde> queue;

   public KundenGen(Queue<Kunde> queue) {
      this.queue = queue;
   }

   @Override
   public void run() {
      Kunde k;
      try {
         while (!isInterrupted()) {
            k = Kunde.einkaufen();

            synchronized (queue) {
               queue.offer(k);
               queue.notifyAll();
            }
            Thread.sleep((long) (Math.random() * 100.00));

         }
      } catch (InterruptedException ignore) {

      }

      System.out.println("Kunden generierung beendet");
   }
}
