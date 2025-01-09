package supermarkt;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class Supermarkt {
    public static void main(String[] args) throws InterruptedException {
        Queue<Kunde> queue = new LinkedList<>();
        CountDownLatch latch = new CountDownLatch(2); // We have 2 Kasse threads

        KundenGen kundenGen = new KundenGen(queue);
        Kasse kasse1 = new Kasse("Kasse 1", queue);
        Kasse kasse2 = new Kasse("Kasse 2", queue);

        kundenGen.start();
        kasse1.start();
        kasse2.start();

        // Simulation für 10 Sekunden laufen lassen
        Thread.sleep(5000);
        kundenGen.stoppen();
        kundenGen.join();

        // Kassen schließen und abwarten, bis alle Kunden abgearbeitet sind
        kasse1.schliessen();
        kasse2.schliessen();

        System.out.printf("Endsaldo von %s: %.2f Euro.%n", kasse1.getName(), kasse1.getSaldo());
        System.out.printf("Endsaldo von %s: %.2f Euro.%n", kasse2.getName(), kasse2.getSaldo());
    }
}