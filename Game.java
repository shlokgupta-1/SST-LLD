package SnakeAndLadder;

import SnakeAndLadder.enums.*;
import SnakeAndLadder.models.*;

import java.util.*;

public class Game {
    private Board board;
    private Queue<Player> players;
    private Dice dice;

    public Game(int n, List<String> names, Difficulty difficulty) {
        this.board = new Board(n, difficulty);
        this.players = new LinkedList<>();
        for (String name : names) players.add(new Player(name));
        this.dice = new Dice();
    }

    public void play() {
        while (true) {
            Player p = players.poll();

            if (p.isFinished()) continue;

            int roll = dice.roll();
            System.out.println(p.getName() + " rolled " + roll);

            int next = p.getPosition() + roll;

            if (next > board.getSize()) {
                players.add(p);
                continue;
            }

            next = board.getNextPosition(next);
            p.setPosition(next);

            if (next == board.getSize()) {
                System.out.println(p.getName() + " finished!");
                p.setFinished(true);
                break;  // Game ends when first player reaches the end
                // DO NOT add back to queue → effectively removed
            } else {
                players.add(p);
            }

            long activePlayers = players.stream().filter(player -> !player.isFinished()).count();
            if (activePlayers <= 1) break;
        }

        System.out.println("Game Over");
    }
}
