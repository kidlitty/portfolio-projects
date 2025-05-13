package com.githubtracker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubEvent {
    private String id;
    private String type;
    private LocalDateTime createdAt;
    private Repo repo;
    private Actor actor;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Repo {
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Actor {
        private String login;

        public String getLogin() { return login; }
        public void setLogin(String login) { this.login = login; }

    }

    @JsonProperty("created_at")
    public void setCreatedAt (LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Repo getRepo() { return repo; }
    public void setRepo(Repo repo) { this.repo = repo;}

    public Actor getActor() { return actor;}
    public void setActor(Actor actor) { this.actor = actor; }

    public LocalDateTime getCreatedAt() { return createdAt; }

}
