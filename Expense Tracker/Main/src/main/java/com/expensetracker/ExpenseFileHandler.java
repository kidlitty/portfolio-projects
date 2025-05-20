package com.expensetracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExpenseFileHandler {
    private static final String FILE_PATH = "expenses.json";

    public static void saveExpenses (List<Expense> expenses) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(new File(FILE_PATH), expenses);
            System.out.println("Expenses saved successfully to " + FILE_PATH);
        }
        catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    public static List<Expense> loadExpenses() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            List<Expense> expenses = mapper.readValue(file, new com.fasterxml.jackson.core.type.TypeReference<List<Expense>>() {});
            ExpenseUtils.renumberExpenseIds((ArrayList<Expense>) expenses);
            return new ArrayList<>(expenses);
        }
        catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
