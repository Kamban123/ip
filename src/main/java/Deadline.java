import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Deadline extends Task {
    private LocalDateTime deadline;
    private static final DateTimeFormatter DATE_FORMAT = 
        DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Deadline(String desc, LocalDateTime deadline) {
        super(desc);
        this.deadline = deadline;
    }

    public LocalDateTime getDead() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return "[Deadline]" + super.toString() + " (by: " + deadline.format(DATE_FORMAT) + ")";
    }
}
