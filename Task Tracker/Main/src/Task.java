public class Task {

    private int id;
    private String title;
    private String description;
    private Status status;

    Task (int id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Status getStatus() { return status; }

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
    public void setDescription (String description) {
        this.description = description;
    }
    public void setStatus (Status status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return ("[" + id + "]" +"\n"+
                title.toUpperCase() + "\n" +
                description + "\n" +
                "Status: " + status);
    }
}
