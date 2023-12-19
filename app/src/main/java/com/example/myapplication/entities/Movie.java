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
    private List<Genre> genres;
    private List<String> artists;
    private String startingDate;
    private String endingDate;
    private String trailer;
    private String image;
    private String sumary;
    private double price;
    private double rating;
    private List<String> showTimes;
    public Movie() {
        this.id = "";
        this.name = "";
        this.duration = 0;
        this.genres = Arrays.asList();
        this.artists = Arrays.asList();
        this.showTimes = Arrays.asList();
        this.startingDate = "";
        this.endingDate = "";
        this.trailer = "";
        this.price = 0;
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
            movie.getArtists(),
            movie.getShowTimes(),
            movie.getStartingDate(),
            movie.getEndingDate(),
            movie.getTrailer(),
            movie.getImage(),
            movie.getSumary(),
            movie.getRating(),
            movie.getPrice()
        );
    }

    public Movie(String id, String name, int duration, List<Genre> genres, List<String> artists, List<String> showTimes, String startingDate, String endingDate, String trailer, String image, String sumary, double rating, double price) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genres = genres;
        this.artists = artists;
        this.showTimes = showTimes;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.trailer = trailer;
        this.image = image;
        this.sumary = sumary;
        this.rating = rating;
        this.price = price;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
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

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<String> showTimes) {
        this.showTimes = showTimes;
    }

    public Movie(HashMap<String, Object> movieMap){
        this(
            (String) movieMap.get("id"),
            (String) movieMap.get("name"),
            (int) movieMap.get("duration"),
            (List<Genre>) movieMap.get("genres"),
            (List<String>) movieMap.get("artists"),
            (List<String>) movieMap.get("showTimes"),
            (String) movieMap.get("startingDate"),
            (String) movieMap.get("endingDate"),
            (String) movieMap.get("trailer"),
            (String) movieMap.get("image"),
            (String) movieMap.get("sumary"),
            (double) movieMap.get("rating"),
            (double) movieMap.get("price")
        );
    }

    public Map<String, Object> toMap(){
        Map<String, Object> movieMap = new HashMap<>();
        movieMap.put("id", this.id);
        movieMap.put("name", this.name);
        movieMap.put("duration", this.duration);
        movieMap.put("genres", this.genres);
        movieMap.put("artists", this.artists);
        movieMap.put("showTimes", this.showTimes);
        movieMap.put("startingDate", this.startingDate);
        movieMap.put("endingDate", this.endingDate);
        movieMap.put("trailer", this.trailer);
        movieMap.put("image", this.image);
        movieMap.put("sumary", this.sumary);
        movieMap.put("rating", this.rating);
        movieMap.put("price", this.price);
        return movieMap;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", genres=" + getGenresString() +
                ", artists=" + getActorsString() +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                ", showTimes=" + getShowTimesString() +
                ", trailer='" + trailer + '\'' +
                ", image=" + image +
                ", sumary='" + sumary + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                '}';
    }

    public String getGenresString(){
        String genresString = "[";
        for(Genre genre : genres){
            genresString += genre.getName() + ", ";
        }
        return genresString.substring(0, genresString.length() - 2) + "]";
    }

    public String getActorsString(){
        String artistsString = "[";
        for(String actor : artists){
            artistsString += actor + ", ";
        }
        return artistsString.substring(0, artistsString.length() - 2) + "]";
    }
    public String getShowTimesString(){
        String showTimesString = "[";
        for(String showTime : showTimes){
            showTimesString += showTime + ", ";
        }
        return showTimesString.substring(0, showTimesString.length() - 2) + "]";
    }
}