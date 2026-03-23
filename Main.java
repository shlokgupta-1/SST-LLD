package SnakeAndLadder;

import SnakeAndLadder.enums.Difficulty;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        System.out.print("Enter number of players: ");
        int x = sc.nextInt();

        sc.nextLine();
        List<String> players = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            System.out.print("Enter player name: ");
            players.add(sc.nextLine());
        }

        System.out.print("Difficulty (EASY/HARD): ");
        Difficulty d = Difficulty.valueOf(sc.nextLine().toUpperCase());

        Game game = new Game(n, players, d);
        game.play();
    }
}
