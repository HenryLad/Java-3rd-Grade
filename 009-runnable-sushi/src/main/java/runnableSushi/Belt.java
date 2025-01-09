package runnableSushi;

import java.util.concurrent.locks.ReentrantLock;

public class Belt {
    private final Food[] positions;
    private final ReentrantLock lock = new ReentrantLock();

    public Belt(int size) {
        this.positions = new Food[size];
    }

    public boolean isValidPosition(int pos) {
        return pos >= 0 && pos < positions.length;
    }

    public boolean isFreePosition(int pos) {
        lock.lock();
        try {
            return isValidPosition(pos) && positions[pos] == null;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            for (Food food : positions) {
                if (food != null) return false;
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean add(Food food, int pos) {
        lock.lock();
        try {
            if (isFreePosition(pos)) {
                positions[pos] = food;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public Food remove(int pos) {
        lock.lock();
        try {
            if (isValidPosition(pos) && positions[pos] != null) {
                Food food = positions[pos];
                positions[pos] = null;
                return food;
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void move() {
        lock.lock();
        try {
            Food last = positions[positions.length - 1];
            for (int i = positions.length - 1; i > 0; i--) {
                positions[i] = positions[i - 1];
            }
            positions[0] = last;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        lock.lock();
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < positions.length; i++) {
                sb.append(String.format("-(%d:%s)", i, positions[i] != null ? positions[i] : "..."));
            }
            return sb.toString();
        } finally {
            lock.unlock();
        }
    }
}
