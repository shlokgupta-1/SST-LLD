package models;

import enums.ScreenType;

import java.util.List;
import java.util.UUID;

public class Screen {
    private final String id;
    private final String name;
    private final ScreenType screenType;
    private final int totalSeats;
    private final List<Seat> seats;

    public Screen(String name, ScreenType screenType, List<Seat> seats) {
        this.id         = UUID.randomUUID().toString();
        this.name       = name;
        this.screenType = screenType;
        this.seats      = seats;
        this.totalSeats = seats.size();
    }

    public String getId()           { return id; }
    public String getName()         { return name; }
    public ScreenType getScreenType() { return screenType; }
    public int getTotalSeats()      { return totalSeats; }
    public List<Seat> getSeats()    { return seats; }

    @Override
    public String toString() {
        return "Screen{id=" + id + ", name=" + name + ", type=" + screenType
                + ", seats=" + totalSeats + "}";
    }
}
