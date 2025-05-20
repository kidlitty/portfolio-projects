package com.expensetracker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Expense {

    private int id;
    private String title;
    private double amount;
    private LocalDateTime date;

    @JsonCreator
    public Expense (@JsonProperty("id") int id,
                    @JsonProperty("title") String title,
                    @JsonProperty("amount") double amount,
                    @JsonProperty("date") LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null!");
        }
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.date = date;
    }

    public int getId () { return id; }
    public String getTitle () { return title; }
    public  double getAmount () { return amount; }
    public LocalDateTime getDate () { return date; }

    void setId (int id) {
        if (id < 1) {
            throw new IllegalArgumentException("ID must be positive!");
        }
        this.id = id;
    }
    public void setTitle (String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty!");
        }
        this.title = title.trim();
    }
    public void setAmount (double amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Amount must be greater than zero!");
        }
        this.amount = amount;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String returnString = ("[" + id + "]" + "\n" +
                title.toUpperCase() + "\n" +
                "R%.2f" + "\n" +
                "Added on: %s");
        return String.format(returnString, amount, date.format(formatter));
    }
}
