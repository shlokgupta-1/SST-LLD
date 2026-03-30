package models;

import java.util.List;
import java.util.UUID;

public class Theatre {
    private final String id;
    private String name;
    private String address;
    private final City city;
    private List<Screen> screens;

    public Theatre(String name, String address, City city, List<Screen> screens) {
        this.id      = UUID.randomUUID().toString();
        this.name    = name;
        this.address = address;
        this.city    = city;
        this.screens = screens;
    }

    public String getId()          { return id; }
    public String getName()        { return name; }
    public String getAddress()     { return address; }
    public City getCity()          { return city; }
    public List<Screen> getScreens() { return screens; }

    public void addScreen(Screen screen) { screens.add(screen); }

    @Override
    public String toString() {
        return "Theatre{id=" + id + ", name=" + name + ", city=" + city.getName() + "}";
    }
}
