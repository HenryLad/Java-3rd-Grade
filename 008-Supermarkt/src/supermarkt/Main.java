package supermarkt;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws Exception {
        Queue<Kunde> queue = new LinkedList<Kunde>();

        KundenGen kundenGen = new KundenGen(queue);
        kundenGen.start();

        Kasse kasse1 = new Kasse(1, queue, "Kasse 1");
        Kasse kasse2 = new Kasse(2, queue, "Kasse 2");
        Kasse kasse3 = new Kasse(3, queue, "Kasse 3");
        Kasse kasse4 = new Kasse(4, queue, "Kasse 4");
        Kasse kasse5 = new Kasse(5, queue, "Kasse 5");
        Kasse kasse6 = new Kasse(6, queue, "Kasse 6");
        kasse1.start();
        kasse2.start();
        kasse3.start();
        kasse4.start();
        kasse5.start();
        kasse6.start();

        Thread.sleep(5000);

        kundenGen.interrupt();
        kasse1.interrupt();
        kasse2.interrupt();
        kasse3.interrupt();
        kasse4.interrupt();
        kasse5.interrupt();
        kasse6.interrupt();




    }   
}
