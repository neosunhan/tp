package jarvis.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TimePeriod {

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm");
    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimePeriod(LocalDateTime start, LocalDateTime end) {
        assert start.compareTo(end) < 0: "Starting time of lesson must be before ending time of lesson";
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return start.format(DATE_TIME_FORMAT) + " - " + end.format(DATE_TIME_FORMAT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimePeriod)) {
            return false;
        }

        TimePeriod otherTimePeriod = (TimePeriod) other;
        return otherTimePeriod.getStart().equals(getStart())
                && otherTimePeriod.getEnd().equals(getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
