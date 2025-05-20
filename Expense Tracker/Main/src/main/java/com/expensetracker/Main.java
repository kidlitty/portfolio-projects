package com.expensetracker;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static int expenseId = 1;
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("en", "ZA"));
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        ArrayList<Expense> expenses = new ArrayList<>(ExpenseFileHandler.loadExpenses());
        expenseId = expenses.size() + 1;

        System.out.println("******************************");
        System.out.println("Welcome To The Expense Tracker");
        System.out.println("******************************" + "\n");
        System.out.println("Type help for usage");

        while (isRunning) {
            System.out.print("\n" + "Enter your command: ");
            String userChoice = scanner.nextLine().toLowerCase().trim();

            switch (userChoice) {
                case "add" -> addExpense(expenses, scanner);
                case "delete" -> deleteExpense(expenses, scanner);
                case "update" -> updateExpense(expenses, scanner);
                case "list" -> listTotalExpense(expenses, scanner);
                case "summary" -> viewMonthlySummary(expenses, scanner);
                case "save" -> saveExpenses(expenses);
                case "help" -> displayOptions();
                case "exit" -> {
                    saveExpenses(expenses);
                    System.out.println("Thank you for using the program, Bye!");
                    isRunning = false;
                }
                default -> {
                    System.out.println("Invalid command, please try again");
                    System.out.println("Type help for usage");
                }
            }
        }

        scanner.close();
    }
    public static void displayOptions() {
        System.out.println("Usage Commands:");
        System.out.println("add - Add Expense");
        System.out.println("delete - Delete Expense");
        System.out.println("update Update Expense");
        System.out.println("list - Display Expenses And Total");
        System.out.println("summary - View Monthly Summary");
        System.out.println("save - Save Expenses To A File");
        System.out.println("help - Help");
        System.out.println("exit - Exit Program");
    }
    private static void printExpenses(ArrayList<Expense> expenses) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found!");
            return;
        }
        expenses.forEach(System.out::println);
    }
    public static void addExpense(ArrayList<Expense> expenses, Scanner scanner) {
        System.out.println("\n" + "- Add Expense -" + "\n");

        String expenseTitle = "";
        while (expenseTitle.isEmpty()) {
            System.out.print("Enter the title of the expense: ");
            expenseTitle = scanner.nextLine().trim();
            if (expenseTitle.isEmpty()) {
                System.out.println("Title cannot be empty.");
            }
        }

        double expenseAmount = 0;
        boolean validAmount = false;
        while (!validAmount) {
            try {
                System.out.print("Enter the amount of expense: ");
                String userInput = scanner.nextLine();
                expenseAmount = Double.parseDouble(userInput);
                if (expenseAmount < 1) {
                    System.out.println("Amount must be greater than 0.");
                } else {
                    validAmount = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a number.");
            }
        }

        LocalDateTime currentDate = LocalDateTime.now();
        Expense newExpense = new Expense(expenseId, expenseTitle, expenseAmount, currentDate);
        expenseId++;

        expenses.add(newExpense);

        System.out.println("Expense Added!");
        System.out.printf("Total: R%.2f\n", calculateTotal(expenses));
    }
    private static double calculateTotal(ArrayList<Expense> expenses) {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }
    public static void deleteExpense (ArrayList<Expense> expenses, Scanner scanner) {
        System.out.println("\n" + "- Delete Expenses -" + "\n");

        printExpenses(expenses);

        System.out.print("\n" + "Enter the ID you wish to delete (or type all to delete everything): ");
        String userChoice = scanner.nextLine().trim().toLowerCase();

        if (userChoice.equals("all")) {
            System.out.print("Are you sure you wish to delete everything? (yes/no): ");
            String userInput = scanner.nextLine().trim().toLowerCase();
            if (userInput.equals("yes")) {
                expenses.clear();
                expenseId = 1;
                System.out.println("All expenses have been cleared");
            } else {
                System.out.println("Bulk deleted cancelled");
            }
            return;
        }

        try {
            int expenseToDelete = Integer.parseInt(userChoice);
            boolean isRemoved = expenses.removeIf(expense -> expense.getId() == expenseToDelete);

            if (isRemoved) {
                System.out.println("Expense with ID " + expenseToDelete + " has been deleted.");
                ExpenseUtils.renumberExpenseIds(expenses);
                expenseId = expenses.size() + 1;
            } else {
                System.out.println("No expense found with ID " + expenseToDelete + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a valid ID or 'all'.");
        }

    }

    public static void updateExpense (ArrayList<Expense> expenses, Scanner scanner) {
        System.out.println("\n" + "- Update Expenses -" + "\n");
        if (expenses.isEmpty()) {
            System.out.println("No expenses to update");
            return;
        }

       printExpenses(expenses);

        System.out.println("Enter the ID of the expense you want to update: ");
        int userId;
        try {
            userId = scanner.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid ID. Please enter a number");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();

        Expense expenseToUpdate = null;
        for (Expense expense : expenses) {
            if (expense.getId() == userId) {
                expenseToUpdate = expense;
                break;
            }
        }

        if (expenseToUpdate == null) {
            System.out.println("Expense with ID " + userId + " does not exist!");
            return;
        }

        System.out.println("\n" + "Current Expense Details: ");
        System.out.println(expenseToUpdate);

        System.out.println("\n" + "Enter the new title (press Enter to keep current): ");
        String newTitle = scanner.nextLine().trim();
        if (!newTitle.isEmpty()) {
            expenseToUpdate.setTitle(newTitle);
        }

        System.out.println("\n" + "Enter the new amount (press Enter to keep current): ");
        String userAmount = scanner.nextLine().trim();
        if (!userAmount.isEmpty()) {
            try {
                double newAmount = Double.parseDouble(userAmount);
                if (newAmount < 1) {
                    System.out.println("Amount must be greater than 0. No changes made,");
                } else {
                    expenseToUpdate.setAmount(newAmount);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. No changes made");
            }
        }

        System.out.println("Expense updated successfully");
    }
    public static void listTotalExpense (ArrayList<Expense> expenses, Scanner scanner) {
        System.out.println("\n" + "- List Expenses -" + "\n");

        if (expenses.isEmpty()) {
            System.out.println("No expenses found!");
            return;
        }

        printExpenses(expenses);

        System.out.println("\n" + "Total expenses: " + expenses.size());
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        System.out.printf("Total amount: %s\n", CURRENCY_FORMAT.format(total));
    }
    public static void viewMonthlySummary (ArrayList<Expense> expenses, Scanner scanner) {
        System.out.println("\n" + "- Monthly Summary -" + "\n");

        int year;
        int month;
        try {
            System.out.print("Enter year (e.g 2024): ");
            year = scanner.nextInt();
            System.out.print("Enter month (1-12): ");
            month = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numbers only.");
            scanner.nextLine();
            return;
        }

        if (month < 1 || month > 12) {
            System.out.println("Invalid month. Must be between 1 and 12.");
            return;
        }

        if (year < 2000 || year > LocalDateTime.now().getYear() + 1) {
            System.out.println("Invalid year. Please enter a valid year.");
            return;
        }

        System.out.println("\n" + "Expenses for " + month + "/" + year + ":");
        double total = 0.0;
        int count = 0;
        boolean found = false;

        for (Expense expense : expenses) {
            LocalDateTime date = expense.getDate();
            if (date.getYear() == year && date.getMonthValue() == month) {
                System.out.println(expense);
                total += expense.getAmount();
                count++;
                found = true;
            }
        }

        if (!found) {
            System.out.println("No expenses found for the selected period.");
        } else {
            System.out.println("\n" + "Total expenses: " + count);
            System.out.printf("Total amount: %s\n", CURRENCY_FORMAT.format(total));
        }
    }
    private static void saveExpenses(ArrayList<Expense> expenses) {
        ExpenseFileHandler.saveExpenses(expenses);
    }

}