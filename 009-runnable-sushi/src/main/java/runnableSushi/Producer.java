package runnableSushi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Producer implements Runnable {
    private final String name;
    private final FoodType type;
    private final Belt belt;
    private final int position;
    private boolean running = true;
    private final List<Food> producedFoods = new ArrayList<>();
    private int counter = 1;

    public Producer(String name, FoodType type, Belt belt, int position) {
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
        System.out.println("Producer " + name + " starts producing at position " + position + " ...");
        Random random = new Random();

        while (running) {
            try {
                Thread.sleep(1000 + random.nextInt(1000));
                Food food = new Food(name + "-" + counter++, type);
                while (!belt.add(food, position)) {
                    Thread.sleep(500);
                }
                producedFoods.add(food);
                System.out.println("*** " + name + " placed " + food + " at position " + position);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Producer " + name + " stopped");
        System.out.print("Producer " + name + " produced: ");
        for (Food food : producedFoods) {
            System.out.print(food + " | ");
        }
        System.out.println();
    }
}
