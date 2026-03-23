package SnakeAndLadder.models;
import SnakeAndLadder.enums.Difficulty;

import java.util.*;

public class Board {
    private int size;
    private Map<Integer, Integer> map = new HashMap<>();

    public Board(int n, Difficulty difficulty) {
        this.size = n * n;
        generateEntities(n, difficulty);
    }

    private void generateEntities(int n, Difficulty difficulty) {
        Random rand = new Random();
        Set<Integer> used = new HashSet<>();

        int snakes = n;
        int ladders = n;

        if (difficulty == Difficulty.HARD) {
            snakes = n + n/2;
        }

        while (snakes > 0) {
            int start = rand.nextInt(size - 1) + 2;
            int end = rand.nextInt(start - 1) + 1;
            if (!used.contains(start) && !used.contains(end)) {
                map.put(start, end);
                used.add(start);
                used.add(end);
                snakes--;
            }
        }

        while (ladders > 0) {
            int start = rand.nextInt(size - 2) + 1;
            int end = rand.nextInt(size - start) + start + 1;
            if (!used.contains(start) && !used.contains(end)) {
                map.put(start, end);
                used.add(start);
                used.add(end);
                ladders--;
            }
        }
    }

    public int getSize() { return size; }

    public int getNextPosition(int pos) {
        return map.getOrDefault(pos, pos);
}
}