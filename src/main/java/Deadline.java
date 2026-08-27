class Deadline extends Task {
    private String deadline;

    public Deadline(String desc, String deadline) {
        super(desc);
        this.deadline = deadline;
    }

    public String getDead() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return "[Deadline]" + super.toString() + " (by: " + deadline + ")";
    }
}
