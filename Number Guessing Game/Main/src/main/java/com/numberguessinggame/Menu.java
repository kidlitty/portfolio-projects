package com.numberguessinggame;

import java.util.Scanner;

public abstract class Menu {

    public static void displayMenu () {
        System.out.println("\n"+"Im thinking of a number between 1 and 100."+"\n");

        System.out.println("Please select the difficulty level");
        System.out.println("1. Easy (10 Chances)");
        System.out.println("2. Medium (5 Chances)");
        System.out.println("3. Hard (3 Chances)");
        System.out.println("4. Exit");
    }
    public static boolean playGame (Scanner scanner, int computerGuess, int chances) {
        int attempts = 0;
        System.out.println("You have " + chances + " chances.");

        while (chances > 0) {
            System.out.print("Enter your guess: ");
            int userGuess = getValidGuess(scanner);

            attempts++;
            if (userGuess == computerGuess) {
                System.out.println("Correct! The number is " + computerGuess);
                System.out.println("Congrats! You guessed the number in " + attempts + " attempts.");
                return true;
            }
            else {
                giveHint(computerGuess, userGuess);
                chances--;
                System.out.println("Chances left: " + chances);
            }
        }
        System.out.println("Game Over! The number was " + computerGuess);
        return false;
    }
    private static void giveHint(int target, int guess) {
        System.out.println("Incorrect! The number is " + (target < guess ? "less" : "greater") + " than " + guess);
    }
    public static int getValidGuess(Scanner scanner) {
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
            int guess = scanner.nextInt();
            if (guess >= 1 && guess <= 100) {
                return guess;
            }
            System.out.println("Please enter a number between 1 and 100");
        }
    }
    public static void easy (Scanner scanner, int computerGuess) {
        System.out.println("\n"+"Easy Mode (10 chances)");
        playGame(scanner, computerGuess, 10);
    }
    public static void medium (Scanner scanner, int computerGuess) {
        System.out.println("\n"+"Medium Mode (5 chances)");
        playGame(scanner, computerGuess, 5);
    }
    public static void hard (Scanner scanner, int computerGuess) {
        System.out.println("\n"+"Hard Mode (3 chances)");
        playGame(scanner, computerGuess, 3);
    }
}
