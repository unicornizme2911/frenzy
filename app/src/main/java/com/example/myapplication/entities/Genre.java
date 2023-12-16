package com.example.myapplication.entities;

import java.util.HashMap;
import java.util.*;
import com.example.myapplication.entities.Movie;

public class Genre{
    private String id;
    private String name;
    public Genre() {}

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(HashMap<String, Object> genreMap){
        this(
            genreMap.get("id").toString(),
            genreMap.get("name").toString()
        );
    }

    public Map<String, Object> toMap(){
        Map<String, Object> genreMap = new HashMap<>();
        genreMap.put("id", this.id);
        genreMap.put("name", this.name);
        return genreMap;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "Genre{" +
                "id = " + this.id +
                ", name = " + this.name + "";
    }
}