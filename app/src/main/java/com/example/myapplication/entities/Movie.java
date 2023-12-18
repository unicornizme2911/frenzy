package com.example.myapplication.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie implements Serializable {
    private String id;
    private String name;
    private int duration;
    private List<String> genres;
    private List<String> actors;
    private String startingDate;
    private String endingDate;
    private String trailer;
    private String image;
    private String sumary;
    private double rating;
    public Movie() {
        this.id = "";
        this.name = "";
        this.duration = 0;
        this.genres = Arrays.asList();
        this.actors = Arrays.asList();
        this.startingDate = "";
        this.endingDate = "";
        this.trailer = "";
        this.image = "";
        this.sumary = "";
        this.rating = 0;
    }

    public Movie(Movie movie){
        this(
            movie.getId(),
            movie.getName(),
            movie.getDuration(),
            movie.getGenres(),
            movie.getActors(),
            movie.getStartingDate(),
            movie.getEndingDate(),
            movie.getThumbnail(),
            movie.getImage(),
            movie.getSumary(),
            movie.getRating()
        );
    }

    public Movie(String id, String name, int duration, List<String> genres, List<String> actors, String startingDate, String endingDate, String trailer, String image, String sumary, double rating) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genres = genres;
        this.actors = actors;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.trailer = trailer;
        this.image = image;
        this.sumary = sumary;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Movie(HashMap<String, Object> movieMap){
        this(
            (String) movieMap.get("id"),
            (String) movieMap.get("name"),
            (int) movieMap.get("duration"),
            (List<String>) movieMap.get("genres"),
            (List<String>) movieMap.get("actors"),
            (String) movieMap.get("startingDate"),
            (String) movieMap.get("endingDate"),
            (String) movieMap.get("trailer"),
            (String) movieMap.get("image"),
            (String) movieMap.get("sumary"),
            (double) movieMap.get("rating")
        );
    }

    public Map<String, Object> toMap(){
        Map<String, Object> movieMap = new HashMap<>();
        movieMap.put("id", this.id);
        movieMap.put("name", this.name);
        movieMap.put("duration", this.duration);
        movieMap.put("genres", this.genres);
        movieMap.put("actors", this.actors);
        movieMap.put("startingDate", this.startingDate);
        movieMap.put("endingDate", this.endingDate);
        movieMap.put("trailer", this.trailer);
        movieMap.put("image", this.image);
        movieMap.put("sumary", this.sumary);
        movieMap.put("rating", this.rating);
        return movieMap;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", genres=" + genres +
                ", actors=" + actors +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                ", trailer='" + trailer + '\'' +
                ", image=" + image +
                ", sumary='" + sumary + '\'' +
                ", rating=" + rating +
                '}';
    }
}