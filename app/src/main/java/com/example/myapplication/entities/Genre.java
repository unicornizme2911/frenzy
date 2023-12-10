package com.example.myapplication.entities;

import java.util.HashMap;
import java.util.*;
import com.example.myapplication.entities.Movie;

public class Genre{
    private String id;
    private String name;
    private String description;
    private ArrayList<Movie> movies;
    public Genre() {}

    public Genre(String id, String name, String description, ArrayList<Movie> movies) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.movies = movies;
    }

    public Genre(String id, String name) {
        this(id, name, "", new ArrayList<Movie>());
    }

    public Genre(HashMap<String, Object> genreMap){
        this(
            genreMap.get("id").toString(),
            genreMap.get("name").toString(),
            genreMap.get("description").toString(),
            (ArrayList<Movie>)genreMap.get("movies")
        );
    }

    public Map<String, Object> toMap(){
        Map<String, Object> genreMap = new HashMap<>();
        genreMap.put("id", this.id);
        genreMap.put("name", this.name);
        genreMap.put("description", this.description);
        genreMap.put("movies", this.movies);
        return genreMap;
    }

    public ArrayList<Movie> getMovies(){
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies){
        this.movies = movies;
    }

    public String getUuid() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setUuid(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return "Genre{" +
                "id = " + this.id +
                ", name = " + this.name +
                ", description = " + this.description +"";
    }
}