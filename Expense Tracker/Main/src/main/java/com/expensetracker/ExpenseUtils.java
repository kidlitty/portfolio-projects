package com.expensetracker;

import java.util.ArrayList;

public class ExpenseUtils {
    public static void renumberExpenseIds (ArrayList<Expense> expenses) {
        for (int i = 0; i < expenses.size(); i++) {
            expenses.get(i).setId(i + 1);
        }
    }
}
