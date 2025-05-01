public enum Status {
    PENDING("Pending"),
    IN_PROGRESS("In-Progress"),
    COMPLETE("Complete");

    private final String displayName;

    Status (String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
