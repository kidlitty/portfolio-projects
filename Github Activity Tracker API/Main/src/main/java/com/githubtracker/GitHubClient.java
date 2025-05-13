package com.githubtracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GitHubClient {
    private static final String GITHUB_API = "https://api.github.com/users/%s/events";
    private static final String TOKEN = System.getenv("GITHUB_TOKEN") != null ? System.getenv("GITHUB_TOKEN") : "";
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static List<GitHubEvent> fetchEvents(String username) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = String.format(GITHUB_API, username);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/vnd.github.v3+json")
                .header("User-Agent", "GitHub-Activity-Tracker");

        if (TOKEN != null && !TOKEN.isBlank()) {
            requestBuilder.header("Authorization", "token " + TOKEN);
        }

        HttpRequest request = requestBuilder.GET().build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("API Error (" + response.statusCode() + "): " + response.body());
        }

        return mapper.readValue(
                response.body(),
                mapper.getTypeFactory().constructCollectionType(List.class, GitHubEvent.class)
        );

    }
}
