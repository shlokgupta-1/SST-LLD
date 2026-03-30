package models;

import java.util.UUID;

public class City {
    private final String id;
    private final String name;
    private final String state;

    public City(String name, String state) {
        this.id    = UUID.randomUUID().toString();
        this.name  = name;
        this.state = state;
    }

    public String getId()    { return id; }
    public String getName()  { return name; }
    public String getState() { return state; }

    @Override
    public String toString() {
        return "City{name=" + name + ", state=" + state + "}";
    }
}
