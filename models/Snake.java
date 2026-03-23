package SnakeAndLadder.models;

public class Snake extends BoardEntity {
    public Snake(int start, int end) {
        super(start, end);
        if (start <= end) throw new IllegalArgumentException("Snake invalid");
    }
}