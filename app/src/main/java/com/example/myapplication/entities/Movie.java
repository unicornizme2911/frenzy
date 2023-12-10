package com.example.myapplication.entities;

import android.net.Uri;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Movie implements Serializable {
    private String id;
    private String name;
    private int duration;
    private String[] genres;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private String thumbnail;
    private Uri[] images;
    private String sumary;
    private double rating;

    public Movie() {
    }

    public Movie(String id, String name, int duration, String[] genres, LocalDate startingDate, LocalDate endingDate, String thumbnail, Uri[] images, String sumary, double rating) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genres = genres;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.thumbnail = thumbnail;
        this.images = images;
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

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Uri[] getImages() {
        return images;
    }

    public void setImages(Uri[] images) {
        this.images = images;
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

    public Movie(HashMap<String, Object> movieMap){
        this(
            movieMap.get("id").toString(),
            movieMap.get("name").toString(),
            Integer.parseInt(movieMap.get("duration").toString()),
            (String[])movieMap.get("genres"),
            LocalDate.parse(movieMap.get("startingDate").toString()),
            LocalDate.parse(movieMap.get("endingDate").toString()),
            movieMap.get("thumbnail").toString(),
            (Uri[])movieMap.get("images"),
            movieMap.get("sumary").toString(),
            Double.parseDouble(movieMap.get("rating").toString())
        );
    }

    public Map<String, Object> toMap(){
        Map<String, Object> movieMap = new HashMap<>();
        movieMap.put("id", this.id);
        movieMap.put("name", this.name);
        movieMap.put("duration", this.duration);
        movieMap.put("genres", this.genres);
        movieMap.put("startingDate", this.startingDate);
        movieMap.put("endingDate", this.endingDate);
        movieMap.put("thumbnail", this.thumbnail);
        movieMap.put("images", this.images);
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
                ", genres=" + Arrays.toString(genres) +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                ", thumbnail='" + thumbnail + '\'' +
                ", images=" + Arrays.toString(images) +
                ", sumary='" + sumary + '\'' +
                ", rating=" + rating +
                '}';
    }
}