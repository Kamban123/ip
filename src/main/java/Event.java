public class Event extends Task {
    private String start;
    private String end;

    public Event(String desc, String start, String end) {
        super(desc);
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return this.start;
    }

    public String getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[Event]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
