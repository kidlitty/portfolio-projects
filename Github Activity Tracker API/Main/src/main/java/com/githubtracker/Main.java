package com.githubtracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("**************************************");
        System.out.println("Welcome To The GitHub Activity Tracker");
        System.out.println("**************************************" + "\n");

        System.out.println("Enter The Github Username: ");
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) {
            System.err.println("Username cannot be empty");
            return;
        }

        try {
            List<GitHubEvent> events = GitHubClient.fetchEvents(username);
            displayEvents(events, username);
        } catch (Exception e) {
            System.err.println("Failed to fetch activity. Reason: " + e.getMessage());
        }

        scanner.close();
    }


    private static void displayEvents(List<GitHubEvent> events, String username) {
        System.out.println("\nRecent Github Activity for @" + username);
        System.out.println("===========================================");


        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }

        for (GitHubEvent event : events) {
            System.out.printf(
                    "[%s] %s performed %s on %s%n",
                    event.getCreatedAt().toString(),
                    event.getActor().getLogin(),
                    event.getType(),
                    event.getRepo().getName()
            );
        }

        System.out.println("\nTotal events found: " + events.size());
    }
}