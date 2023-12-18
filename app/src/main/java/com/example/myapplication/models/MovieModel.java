package com.example.myapplication.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.entities.Genre;
import com.example.myapplication.entities.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MovieModel extends Model{
    private static final String TAG = "MovieModel";
    private static final String MOVIE_COLLECTION = "movies";
    private GenreModel genreModel = new GenreModel();

    public interface MovieCallbacks{
        void onSuccess(Movie movie);
        void onFailed(Exception e);
    }
    public interface MoviesCallbacks{
        void onSuccess(ArrayList<Movie> movies);
        void onFailed(Exception e);
    }
    public MovieModel() {
        super();
    }

    public MovieModel(DatabaseReference database){
        super(database);
    }

    public void getMovie(String id, MovieCallbacks callbacks){
        database.child(MOVIE_COLLECTION).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    callbacks.onFailed(new Exception("Movie not found"));
                    return;
                }
                JSONObject jsonObject = new JSONObject((Map) snapshot.getValue());
                String id = snapshot.getKey();
                String name = jsonObject.optString("name");
                int duration = jsonObject.optInt("duration");
                String genreIds = jsonObject.optString("genres");
                List<Genre> genres = new ArrayList<>();
                Gson gson = new Gson();
                Map<String, Map<String, String>> genreMap = gson.fromJson(genreIds, HashMap.class);
                for (Map.Entry<String, Map<String, String>> entry : genreMap.entrySet()) {
                    String genre_id = entry.getKey();
                    String genre_name = entry.getValue().get("name");
                    Genre genre = new Genre(genre_id, genre_name);
                    genres.add(genre);
                }
                List<String> temp_artists = Arrays.asList(jsonObject.optString("artists").split("[,\\[\\]\"]"));
                List<String> artists = new ArrayList<>();
                for (int i = 0; i < temp_artists.size(); i++) {
                    if(!temp_artists.get(i).equals("")){
                        artists.add(temp_artists.get(i));
                    }
                }
                String startingDate = jsonObject.optString("startingDate");
                String endingDate = jsonObject.optString("endingDate");
                String trailer = jsonObject.optString("trailer");
                String image = jsonObject.optString("image");
                String sumary = jsonObject.optString("sumary");
                double rating = jsonObject.optDouble("rating");
                double price = jsonObject.optDouble("price");
                Movie movie = new Movie(id, name, duration, genres, artists, startingDate, endingDate, trailer, image, sumary, rating, price);
                Log.e(TAG, "getMovie: " + movie.toString());
                callbacks.onSuccess(movie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void getAllMovie(MoviesCallbacks callbacks){
        database.child(MOVIE_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Movie> movies = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Movie movie = new Movie();
                    JSONObject jsonObject = new JSONObject((Map) snapshot.getValue());
                    String id = snapshot.getKey();
                    String name = jsonObject.optString("name");
                    int duration = jsonObject.optInt("duration");
                    String genreIds = jsonObject.optString("genres");
                    List<Genre> genres = new ArrayList<>();
                    Gson gson = new Gson();
                    Map<String, Map<String, String>> genreMap = gson.fromJson(genreIds, HashMap.class);
                    for (Map.Entry<String, Map<String, String>> entry : genreMap.entrySet()) {
                        String genre_id = entry.getKey();
                        String genre_name = entry.getValue().get("name");
                        Genre genre = new Genre(genre_id, genre_name);
                        genres.add(genre);
                    }
                    List<String> temp_artists = Arrays.asList(jsonObject.optString("artists").split("[,\\[\\]\"]"));
                    List<String> artists = new ArrayList<>();
                    for (int i = 0; i < temp_artists.size(); i++) {
                        if(!temp_artists.get(i).equals("")){
                            artists.add(temp_artists.get(i));
                        }
                    }
                    String startingDate = jsonObject.optString("startingDate");
                    String endingDate = jsonObject.optString("endingDate");
                    String trailer = jsonObject.optString("trailer");
                    String image = jsonObject.optString("image");
                    String sumary = jsonObject.optString("sumary");
                    double rating = jsonObject.optDouble("rating");
                    double price = jsonObject.optDouble("price");
                    movie.setId(id);
                    movie.setName(name);
                    movie.setDuration(duration);
                    movie.setGenres(genres);
                    movie.setArtists(artists);
                    movie.setStartingDate(startingDate);
                    movie.setEndingDate(endingDate);
                    movie.setTrailer(trailer);
                    movie.setImage(image);
                    movie.setSumary(sumary);
                    movie.setRating(rating);
                    movie.setPrice(price);
                    movies.add(movie);
                }
                callbacks.onSuccess(movies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void search(String keyword, MoviesCallbacks callbacks){
        database.child(MOVIE_COLLECTION)
                .orderByChild("name")
                .startAt(keyword)
                .endAt(keyword + "\uf8ff").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Movie> movies = new ArrayList<>();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Movie movie = new Movie();
                            JSONObject jsonObject = new JSONObject((Map) snapshot.getValue());
                            String id = snapshot.getKey();
                            String name = jsonObject.optString("name");
                            int duration = jsonObject.optInt("duration");
                            String genreIds = jsonObject.optString("genres");
                            List<Genre> genres = new ArrayList<>();
                            Gson gson = new Gson();
                            Map<String, Map<String, String>> genreMap = gson.fromJson(genreIds, HashMap.class);
                            for (Map.Entry<String, Map<String, String>> entry : genreMap.entrySet()) {
                                String genre_id = entry.getKey();
                                String genre_name = entry.getValue().get("name");
                                Genre genre = new Genre(genre_id, genre_name);
                                genres.add(genre);
                            }
                            List<String> temp_artists = Arrays.asList(jsonObject.optString("artists").split("[,\\[\\]\"]"));
                            List<String> artists = new ArrayList<>();
                            for (int i = 0; i < temp_artists.size(); i++) {
                                if(!temp_artists.get(i).equals("")){
                                    artists.add(temp_artists.get(i));
                                }
                            }
                            String startingDate = jsonObject.optString("startingDate");
                            String endingDate = jsonObject.optString("endingDate");
                            String trailer = jsonObject.optString("trailer");
                            String image = jsonObject.optString("image");
                            String sumary = jsonObject.optString("sumary");
                            double rating = jsonObject.optDouble("rating");
                            double price = jsonObject.optDouble("price");
                            movie.setId(id);
                            movie.setName(name);
                            movie.setDuration(duration);
                            movie.setGenres(genres);
                            movie.setArtists(artists);
                            movie.setStartingDate(startingDate);
                            movie.setEndingDate(endingDate);
                            movie.setTrailer(trailer);
                            movie.setImage(image);
                            movie.setSumary(sumary);
                            movie.setRating(rating);
                            movie.setPrice(price);
                            movies.add(movie);
                        }
                        callbacks.onSuccess(movies);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callbacks.onFailed(error.toException());
                    }
                });
    }
    public void createMovie(String name, int duration, List<String> genreIds, List<String> actorIds, String start, String end,
                            String trailer, String image, String sumary, double rating, double price, MovieCallbacks callbacks){
        String id = UUID.randomUUID().toString();
        List<Genre> genres = new ArrayList<>();
        for(String genreId : genreIds){
            genreModel.getGenre(genreId, new GenreModel.GenreCallbacks() {
                @Override
                public void onSuccess(Genre genre) {
                    genres.add(genre);
                }

                @Override
                public void onFailed(Exception e) {
                    Log.e(TAG, "onFailed: " + e.getMessage());
                }
            });
        }
        Movie movie = new Movie(id, name, duration, genres, actorIds, start, end, trailer, image, sumary, rating, price);
        database.child(MOVIE_COLLECTION).child(id).setValue(movie.toMap()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callbacks.onSuccess(movie);
            } else {
                callbacks.onFailed(task.getException());
            }
        });
    }
}