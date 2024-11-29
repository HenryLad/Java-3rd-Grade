package supermarkt;

import java.util.Queue;

public class Kasse extends Thread {
   private double saldo = 0.0;
   private Queue<Kunde> queue;

   public Kasse(int id, Queue<Kunde> queue, String name) {
      super(name);
      this.queue = queue;
   }

   public double getSaldo() {
      return saldo;
   }
   
   @Override
   public void run() {
      try {
         while (!queue.isEmpty() || !isInterrupted()) {
            synchronized (queue) {
               Kunde lastelement = queue.poll();
               if (queue.isEmpty() || lastelement == null) {
                  queue.wait();
               } else {
                  this.saldo += lastelement.getWarenwert();
               }
            }
         }

      } catch (InterruptedException ignore) {

      }
      System.out.println(getName() + " hat einen Saldo von " + getSaldo());
   }


}
