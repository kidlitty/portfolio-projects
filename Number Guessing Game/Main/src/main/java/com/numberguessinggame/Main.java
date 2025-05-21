package com.numberguessinggame;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean isRunning = true;

        System.out.println("***********************************");
        System.out.println("Welcome To The Number Guessing Game");
        System.out.println("***********************************");


            while (isRunning) {
                Menu.displayMenu();
                System.out.print("Enter your choice: ");
                int userChoice = getMenuChoice(scanner);

                switch (userChoice) {
                    case 1 -> playRounds(scanner, random, 10, "Easy Mode (10 chances/round)");
                    case 2 -> playRounds(scanner, random, 5, "Medium Mode (5 chances/round)");
                    case 3 -> playRounds(scanner, random, 3, "Hard Mode (3 chances/round)");
                    case 4 -> {
                        System.out.println("Thank you for using the program! Bye.");
                        isRunning = false;
                    }
                }
            }
        scanner.close();
    }
    private static void playRounds (Scanner scanner, Random random, int chancesPerRound, String modeMessage) {
        System.out.println("\n" + modeMessage);

        int rounds = getNumberOfRounds(scanner);
        int wins = 0;

        for (int i = 1; i <= rounds; i++) {
            System.out.println("\n--- Round " + i + " of " + rounds + " ---");
            int computerGuess = random.nextInt(1, 101);
            boolean won = Menu.playGame(scanner, computerGuess, chancesPerRound);
            if (won) {
                wins++;
            }
        }

        displayRoundResults(rounds, wins);
    }
    private static int getMenuChoice (Scanner scanner) {
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
            int guess = scanner.nextInt();
            if (guess >= 1 && guess <= 4) {
                return guess;
            }
            System.out.println("Please enter a number between 1 and 4");
        }
    }
    private static int getNumberOfRounds (Scanner scanner) {
        System.out.print("Enter the number of rounds you want to play: ");
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
            int rounds = scanner.nextInt();
            if (rounds >= 1) return rounds;
            System.out.println("Please enter a number greater than 0.");
        }
    }
    private static void displayRoundResults(int rounds, int wins) {
        System.out.println("\n=== Game Results ===");
        System.out.println("Total Rounds: " + rounds);
        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + (rounds - wins));
        System.out.println("====================\n");
    }
}