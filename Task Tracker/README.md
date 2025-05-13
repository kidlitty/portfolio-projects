# 🚀 Task Tracker CLI

A command-line task manager with persistent storage, status tracking, and intuitive commands. Perfect for developers who love terminal productivity! 💻✨

## 📦 Features

| Feature               | Description                                                                 |
|-----------------------|-----------------------------------------------------------------------------|
| **📝 Add Tasks**       | Create tasks with titles, descriptions, and default "Pending" status        |
| **🔄 Update Tasks**    | Modify existing tasks (title, description, status)                          |
| **🗑️ Delete Tasks**    | Remove individual tasks or clear all tasks at once                          |
| **📋 List Filtering**  | View tasks by status: `Pending`, `In-Progress`, or `Complete`               |
| **💾 Persistent Storage** | Automatic saving to `tasks.json` with manual save option                  |
| **🔍 Input Validation**  | Robust error handling for invalid commands and inputs                     |

## 🛠️ Installation

1. **Prerequisites**:  
   Ensure you have [Java JDK 17+] installed

2. **Clone & Run**:
   ```bash
   git clone https://github.com/yourusername/task-tracker-cli.git
   cd task-tracker-cli
   
   javac Main.java
   java Main

3. **First Run**:
A tasks.json file will be automatically created in your project directory.

## 🎮 Usage 
```commandline
# Add new task
Enter Your Command: add

# Update task ID 1
Enter Your Command: update
[1] FINISH README Pending

# List all in-progress tasks
Enter Your Command: list progress

# Delete all tasks (with confirmation)
Enter Your Command: delete
> all
> yes
```
### 🔧 All Commands
| Command           | Description                                  | Example Usage               |
|-------------------|----------------------------------------------|-----------------------------|
| `add`             | Create a new task                            | `add`                       |
| `update <id>`     | Modify an existing task                      | `update 1`                  |
| `delete <id\|all>`| Delete a single task or all tasks            | `delete 3` or `delete all`  |
| `list`            | Show all tasks                               | `list`                      |
| `list complete`   | Filter tasks with "Complete" status          | `list complete`             |
| `list pending`    | Filter tasks with "Pending" status           | `list pending`              |
| `list progress`   | Filter tasks with "In-Progress" status       | `list progress`             |
| `save`            | Force immediate save to disk                 | `save`                      |
| `help`            | Display command help                         | `help`                      |
| `exit`            | Quit the program (auto-saves)                | `exit`                      |

## 🗂️ Project Structure
```commandline
task-tracker-cli/
├── Main.java          # CLI interface and core logic
├── Task.java          # Task model (id, title, description, status)
├── Status.java        # Enum: PENDING, IN_PROGRESS, COMPLETE
└── tasks.json         # Auto-generated task storage
```

## 💾 Data Persistence
Tasks are stored in JSON format with automatic loading/saving:
```json
[
{
"id": 1,
"title": "Create GitHub Repo",
"description": "Initialize project repository",
"status": "COMPLETE"
}
]
```
## 🚨 Notes
    🔄 ID Management: Deleting tasks resets remaining IDs to maintain sequence

    ✅ Validation: Empty titles/descriptions are blocked

    ⚠️ Bulk Delete: Requires confirmation before deleting all tasks

👤 Author: Uphile Ntuli
💡 Contributions: PRs welcome! Please open an issue first to discuss changes.