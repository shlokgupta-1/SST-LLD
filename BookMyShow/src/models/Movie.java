package models;

import enums.Genre;
import enums.Language;

import java.util.List;
import java.util.UUID;

public class Movie {
    private final String id;
    private String title;
    private String description;
    private int durationMinutes;
    private Language language;
    private List<Genre> genres;
    private double rating;

    public Movie(String title, String description, int durationMinutes,
                 Language language, List<Genre> genres) {
        this.id              = UUID.randomUUID().toString();
        this.title           = title;
        this.description     = description;
        this.durationMinutes = durationMinutes;
        this.language        = language;
        this.genres          = genres;
        this.rating          = 0.0;
    }

    public String getId()              { return id; }
    public String getTitle()           { return title; }
    public String getDescription()     { return description; }
    public int getDurationMinutes()    { return durationMinutes; }
    public Language getLanguage()      { return language; }
    public List<Genre> getGenres()     { return genres; }
    public double getRating()          { return rating; }

    public void setTitle(String title)             { this.title = title; }
    public void setLanguage(Language language)     { this.language = language; }
    public void setRating(double rating)           { this.rating = rating; }

    @Override
    public String toString() {
        return "Movie{id=" + id + ", title=" + title + ", language=" + language
                + ", genres=" + genres + ", rating=" + rating + "}";
    }
}
