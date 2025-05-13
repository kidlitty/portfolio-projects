# 📊 GitHub Activity Tracker CLI

A simple command-line tool to fetch and display recent public activity for any GitHub user using the GitHub Events API.

---

## 🚀 Features

- Fetches recent GitHub events for a user
- Clean, readable CLI output
- Supports GitHub API token (to avoid rate limits)
- Built with Java 22, uses Jackson for JSON parsing

---

## 📦 Installation

1. **Clone the repository:**

```bash
git clone https://github.com/your-username/github-activity-tracker.git
cd github-activity-tracker
```

2. **Build the project using Maven:**

```commandline
mvn clean package
```

3. **(OPTIONAL) Set a GitHub API token to avoid rate limiting:**

```bash
export GITHUB_TOKEN=your_token_here
```
```commandline (windows)
set GITHUB_TOKEN=your_token_here
```

## 🧪 Usage

**Run the program with:**
```commandline
java -jar target/github-activity-tracker-1.0-SNAPSHOT-jar-with-dependencies.jar 
```

Then follow the prompt to enter a GitHub username.

## ⚙️ Requirements

Java 22+

Maven

## 🛠 Tech Stack

Java 22

Jackson (databind, jsr310)

Maven

GitHub REST API v3

## 🔒 API Rate Limits

GitHub’s public API is rate-limited to 60 requests/hour per IP without a token. To increase your rate limit (up to 5,000 requests/hour), generate a personal access token and export it like this:
```commandline
set GITHUB_TOKEN=your_token
```
```bash
export GITHUB_TOKEN=your_token
```

## 🧑‍💻 Author

Uphile Ntuli
https://github.com/kidlitty