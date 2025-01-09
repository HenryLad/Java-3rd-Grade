package runnableSushi;

public class Food {
    private final String id;
    private final FoodType type;

    public Food(String id, FoodType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public FoodType getType() {
        return type;
    }

    @Override
    public String toString() {
        return id;
    }
}
