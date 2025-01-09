package runnableSushi;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Consumer implements Runnable {
    private final String name;
    private final ConsumerType type;
    private final Belt belt;
    private final int position;
    private boolean running = true;
    private final List<Food> consumedFoods = new ArrayList<>();

    public Consumer(String name, ConsumerType type, Belt belt, int position) {
        this.name = name;
        this.type = type;
        this.belt = belt;
        this.position = position;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        System.out.println("Consumer " + name + " starts consuming at position " + position + " ...");
        Random random = new Random();

        while (running) {
            try {
                if (type == ConsumerType.GUEST) {
                    Thread.sleep(1000 + random.nextInt(4000));
                }
                Food food = belt.remove(position);
                if (food != null) {
                    consumedFoods.add(food);
                    System.out.println("*** " + name + " consumed " + food + " at position " + position);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Consumer " + name + " stopped.");
        System.out.print(name + " took: ");
        for (Food food : consumedFoods) {
            System.out.print(food + " | ");
        }
        System.out.println();
    }
}
