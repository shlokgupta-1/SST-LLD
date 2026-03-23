package SnakeAndLadder.models;

public class Player {
    private String name;
    private int position;
    private boolean finished;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.finished = false;
    }

    public String getName() { return name; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public boolean isFinished() { return finished; }
    public void setFinished(boolean f) { this.finished = f; }
}