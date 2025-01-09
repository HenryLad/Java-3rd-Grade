package runnableSushi;
public class RunnableSushi {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Runnable Sushi opens ...");
        Belt belt = new Belt(12);

        // Create producers
        Producer sushiChef = new Producer("S", FoodType.SUSHI, belt, 2);
        Producer appetizerChef = new Producer("A", FoodType.APPETIZER, belt, 1);

        Thread sushiChefThread = new Thread(sushiChef);
        Thread appetizerChefThread = new Thread(appetizerChef);

        // Create consumers
        Consumer ann = new Consumer("Ann", ConsumerType.GUEST, belt, 3);
        Consumer bob = new Consumer("Bob", ConsumerType.GUEST, belt, 5);
        Consumer joe = new Consumer("Joe", ConsumerType.GUEST, belt, 7);
        Consumer cleaner = new Consumer("Cleaner", ConsumerType.CLEANER, belt, 0);

        Thread annThread = new Thread(ann);
        Thread bobThread = new Thread(bob);
        Thread joeThread = new Thread(joe);
        Thread cleanerThread = new Thread(cleaner);

        // Start belt
        Thread beltThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    belt.move();
                    System.out.println(belt);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        beltThread.start();
        sushiChefThread.start();
        appetizerChefThread.start();
        annThread.start();
        bobThread.start();
        joeThread.start();

        // Simulate restaurant running for some time
        Thread.sleep(20000);

        // Stop all threads
        sushiChef.stop();
        appetizerChef.stop();
        ann.stop();
        bob.stop();
        joe.stop();
        sushiChefThread.join();
        appetizerChefThread.join();
        annThread.join();
        bobThread.join();
        joeThread.join();

        // Start cleaner
        System.out.println("Start clean up");
        cleanerThread.start();
        Thread.sleep(5000);
        cleaner.stop();
        cleanerThread.join();

        // Stop belt
        beltThread.interrupt();
        beltThread.join();
        System.out.println("Belt stopped");
        System.out.println("Runnable Sushi closes");
    }
}
