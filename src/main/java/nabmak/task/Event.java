package nabmak.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;
    private static final DateTimeFormatter DATE_FORMAT = 
        DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Event(String desc, LocalDateTime start, LocalDateTime end) {
        super(desc);
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[Event]" + super.toString() + " (from: " 
            + start.format(DATE_FORMAT) + " to: " 
            + end.format(DATE_FORMAT) + ")";
    }
}
