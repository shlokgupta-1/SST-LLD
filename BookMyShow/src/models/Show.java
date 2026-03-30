package models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Show {
    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final Theatre theatre;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive;

    public Show(Movie movie, Screen screen, Theatre theatre,
                LocalDateTime startTime, LocalDateTime endTime) {
        this.id        = UUID.randomUUID().toString();
        this.movie     = movie;
        this.screen    = screen;
        this.theatre   = theatre;
        this.startTime = startTime;
        this.endTime   = endTime;
        this.isActive  = true;
    }

    public String getId()            { return id; }
    public Movie getMovie()          { return movie; }
    public Screen getScreen()        { return screen; }
    public Theatre getTheatre()      { return theatre; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime()   { return endTime; }
    public boolean isActive()        { return isActive; }

    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime)     { this.endTime = endTime; }
    public void setActive(boolean active)              { this.isActive = active; }

    @Override
    public String toString() {
        return "Show{id=" + id + ", movie=" + movie.getTitle()
                + ", theatre=" + theatre.getName()
                + ", screen=" + screen.getName()
                + ", time=" + startTime + "}";
    }
}
