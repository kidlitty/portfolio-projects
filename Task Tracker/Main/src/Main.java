import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Path JSON_FILE = Paths.get("tasks.json");
    private static int taskId = 1;

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            if (Files.notExists(JSON_FILE)) {
                Files.write(JSON_FILE, "[]".getBytes(StandardCharsets.UTF_8));
                return tasks;
            }
            String content = new String(Files.readAllBytes(JSON_FILE), StandardCharsets.UTF_8).trim();
            if (content.equals("[]") || content.isEmpty()) {
                return tasks;
            }

            String body = content.substring(1, content.length() - 1);
            String [] items = body.split("\\},\\s*\\{");
            for (String raw : items) {
                String obj = raw;
                if (!obj.startsWith("{")) {
                    obj = "{" + obj;
                }
                if (!obj.endsWith("}")) {
                    obj = obj + "}";
                }

                int id = Integer.parseInt(getJsonValue(obj, "id"));
                String title = getJsonValue(obj, "title");
                String desc = getJsonValue(obj, "description");
                String stat = getJsonValue(obj, "status");
                Status status = Status.valueOf(stat);
                tasks.add(new Task(id, title, desc, status));

                if (id >= taskId) {
                    taskId = id + 1;
                }
            }
        }
        catch (IOException e) {
            System.err.println("Could not read tasks.json: " + e.getMessage());
        }
        return tasks;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        ArrayList<Task> tasks = loadTasks();

        // Task Tracker CLI Java Program
        System.out.println("*******************************");
        System.out.println("Welcome To Task Tracker Program");
        System.out.println("*******************************");
        System.out.println("Type help for usage");

        while (isRunning) {

            System.out.print("\n" + "Enter Your Command: ");
            String userCommand = scanner.nextLine().toLowerCase();

            switch (userCommand) {
                case "add" -> addTask(tasks, scanner);
                case "update" -> updateTask(tasks, scanner);
                case "delete" -> deleteTasks(tasks, scanner);
                case "list" -> listTasks(tasks);
                case "list complete" -> completeTasks(tasks);
                case "list pending" -> pendingTasks(tasks);
                case "list progress" -> inProgressTasks(tasks);
                case "save" -> saveTasks(tasks);
                case "help" -> usage();
                case "exit" -> {
                    isRunning = false;
                }
                default -> {
                    System.out.println("Invalid Command Please Try Again");
                    usage();
                }
            }
        }
        saveTasks(tasks);

        System.out.println("Thank you for using the program. Bye!");

        scanner.close();
    }
    // Prints program usage information to the user
    public static void usage() {
        System.out.println("\n" + "Usage Commands: ");
        System.out.println("add - Add Task");
        System.out.println("update - Update Existing Tasks");
        System.out.println("delete - Delete Tasks");
        System.out.println("list - List All Existing Tasks");
        System.out.println("list complete - List All Complete Tasks");
        System.out.println("list pending - List All Pending Tasks");
        System.out.println("list progress - List All Task In Progress");
        System.out.println("save - Save All Your Tasks");
        System.out.println("exit - Exit Program");
    }
    private static void saveTasks (ArrayList<Task> tasks) {
        try {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                sb.append("  {\n")
                        .append("   \"id\":").append(t.getId()).append(",\n")
                        .append("   \"title\":\"").append(escapeJson(t.getTitle())).append("\",\n")
                        .append("   \"description\":\"").append(escapeJson(t.getDescription())).append("\",\n")
                        .append("   \"status\":\"").append(t.getStatus().name()).append("\"\n")
                        .append("  }");
                if (i < tasks.size() - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            }
            sb.append("]");
            Files.write(JSON_FILE, sb.toString().getBytes(StandardCharsets.UTF_8));

            System.out.println("Your Tasks Have Been Saved!");
        }
        catch (IOException e) {
            System.err.println("Could not write tasks.json: " + e.getMessage());
        }
    }
    private static String getJsonValue(String json, String key) {
        Pattern p = Pattern.compile("\"" + key + "\":(?:(\\d+)|\"([^\"]*)\")");
        Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1) != null ? m.group(1) : m.group(2);
        }
        return "";
    }
    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
    // Lets the user add their task and description
    public static void addTask (ArrayList<Task> tasks, Scanner scanner) {
        System.out.println("\n" + "- Add Task -");
        System.out.println("Enter the title: ");
        String taskTitle = scanner.nextLine().trim();
        while (taskTitle.isEmpty()) {
            System.out.println("Title cannot be empty. Enter title:");
            taskTitle = scanner.nextLine().trim();
        }
        System.out.println("Enter a description: ");
        String taskDescription = scanner.nextLine().trim();
        while (taskDescription.isEmpty()) {
            System.out.println("Description cannot be empty. Enter description:");
            taskDescription = scanner.nextLine().trim();
        }

        Task newTask = new Task(taskId, taskTitle, taskDescription, Status.PENDING);

        tasks.add(newTask);

        taskId++;

        System.out.println("Task added!");
    }
    // Lets the user update their task, including title, description and status
    public static void updateTask (ArrayList<Task> tasks, Scanner scanner) {
        System.out.println("\n" + "- Update Task -");
        if (tasks.isEmpty()) {
            System.out.println("No tasks to update!");
            return;
        }

        for (Task task : tasks) {
            System.out.println("[" + task.getId() + "]" + " " + task.getTitle().toUpperCase() + " " + task.getStatus());
        }

        System.out.print("Enter the ID of the task to update: ");
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

        Task taskToUpdate = null;
        for (Task task : tasks) {
            if (task.getId() == userId) {
                taskToUpdate = task;
                break;
            }
        }

        if (taskToUpdate == null) {
            System.out.println("Task with ID " + userId + " does not exist.");
            return;
        }

        System.out.println("\n" + "Current task details: ");
        System.out.println(taskToUpdate);

        System.out.println("\n" + "Enter the new title (press Enter to keep current): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.isEmpty()) {
            taskToUpdate.setTitle(newTitle);
        }

        System.out.println("Enter the new description (press Enter to keep current): ");
        String newDescription = scanner.nextLine();
        if (!newDescription.isEmpty()) {
            taskToUpdate.setDescription(newDescription);
        }

        boolean statusPending = true;
        while (statusPending) {
            System.out.println("Update task status: ");
            Status[] statuses = Status.values();
            for (int i = 0; i < statuses.length; i++) {
                System.out.println((i + 1) + ". " + statuses[i]);
            }
            System.out.print("Enter your choice (1-" + statuses.length + "): ");

            int userStatus;
            try {
                userStatus = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice! Please enter a number");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();

            switch (userStatus) {
                case 1 -> {
                    taskToUpdate.setStatus(Status.PENDING);
                    statusPending = false;
                }
                case 2 -> {
                    taskToUpdate.setStatus(Status.IN_PROGRESS);
                    statusPending = false;
                }
                case 3 -> {
                    taskToUpdate.setStatus(Status.COMPLETE);
                    statusPending = false;
                }
                default -> System.out.println("Invalid input. Please choose (1-3)");
            }
        }

        System.out.println("Task updated successfully!");

    }
    // List tasks in a list
    public static void listTasks (ArrayList<Task> tasks) {
        System.out.println("\n" + "- List All Tasks -");
        if (tasks.isEmpty()) {
            System.out.println("No Tasks Found.");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task);
        }
    }
    // Display all complete tasks
    public static void completeTasks (ArrayList<Task> tasks) {
        System.out.println("\n" + "- List All Complete Tasks -");

        boolean found = false;
        for (Task task : tasks) {
            if (task.getStatus() == Status.COMPLETE) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No Complete Tasks Found");
        }
    }
    // Display all pending tasks
    public static void pendingTasks (ArrayList<Task> tasks) {
        System.out.println("\n" + "- List All Pending Tasks -");

        boolean found = false;
        for (Task task : tasks) {
            if (task.getStatus() == Status.PENDING) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No Pending Tasks Found");
        }
    }
    // Display all in progress tasks
    public static void inProgressTasks (ArrayList<Task> tasks) {
        System.out.println("\n" + "- List All In-Progress Tasks -");

        boolean found = false;
        for (Task task : tasks) {
            if (task.getStatus() == Status.IN_PROGRESS) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No In-Progress Tasks Found");
        }
    }
    // Lets the user delete a task of their choosing
    public static void deleteTasks (ArrayList<Task> tasks, Scanner scanner) {
        System.out.println("\n" + "- Delete Tasks -");
        if (tasks.isEmpty()) {
            System.out.println("No task to delete");
            return;
        }

        for (Task task : tasks) {
            System.out.println("[" + task.getId() + "]" + " " + task.getTitle().toUpperCase() + " " + task.getStatus());
        }

        System.out.print("Enter the ID of the task to delete (or type 'all' to delete all tasks): ");
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.equals("all")) {
            System.out.print("Are you sure you want to delete ALL tasks? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("yes")) {
                tasks.clear();
                taskId = 1;
                System.out.println("All tasks have been deleted.");
            } else {
                System.out.println("Bulk delete canceled");
            }
            return;
        }
        int taskToDelete;
        try {
            taskToDelete = Integer.parseInt(input);
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid ID. Please enter a valid ID or 'all'");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();

        boolean isRemoved = tasks.removeIf(task -> task.getId() == taskToDelete);

        if (isRemoved) {
            for (int i = 0; i < tasks.size(); i++) {
                tasks.get(i).setId(i + 1);
            }
            taskId = tasks.size() + 1;
            System.out.println("Task with ID " + taskToDelete + " deleted successfully!");
        }
        else {
            System.out.println("Task with ID " + taskToDelete + " not found.");
        }
    }
}
