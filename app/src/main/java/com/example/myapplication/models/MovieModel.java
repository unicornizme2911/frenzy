package com.example.myapplication.models;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.entities.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MovieModel extends Model{
    private static final String TAG = "MovieModel";
    private static final String MOVIE_COLLECTION = "movies";

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
                List<String> genres = Arrays.asList(jsonObject.optString("genres").split(","));
                String startingDate = jsonObject.optString("startingDate");
                String endingDate = jsonObject.optString("endingDate");
                String thumbnail = jsonObject.optString("thumbnail");
                String image = jsonObject.optString("image");
                String sumary = jsonObject.optString("sumary");
                double rating = jsonObject.optDouble("rating");
                Movie movie = new Movie(id, name, duration, genres, startingDate, endingDate, thumbnail, image, sumary, rating);
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
                    JSONObject jsonObject = new JSONObject((Map) dataSnapshot.getValue());
                    String id = dataSnapshot.getKey();
                    String name = jsonObject.optString("name");
                    int duration = jsonObject.optInt("duration");
                    List<String> genres = Arrays.asList(jsonObject.optString("genres").split(","));
                    String startingDate = jsonObject.optString("startingDate");
                    String endingDate = jsonObject.optString("endingDate");
                    String thumbnail = jsonObject.optString("thumbnail");
                    String image = jsonObject.optString("image");
                    String sumary = jsonObject.optString("sumary");
                    double rating = jsonObject.optDouble("rating");
                    movie.setId(id);
                    movie.setName(name);
                    movie.setDuration(duration);
                    movie.setGenres(genres);
                    movie.setStartingDate(startingDate);
                    movie.setEndingDate(endingDate);
                    movie.setThumbnail(thumbnail);
                    movie.setImage(image);
                    movie.setSumary(sumary);
                    movie.setRating(rating);
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
                            JSONObject jsonObject = new JSONObject((Map) dataSnapshot.getValue());
                            String id = dataSnapshot.getKey();
                            String name = jsonObject.optString("name");
                            int duration = jsonObject.optInt("duration");
                            List<String> genres = Arrays.asList(jsonObject.optString("genres").split(","));
                            String startingDate = jsonObject.optString("startingDate");
                            String endingDate = jsonObject.optString("endingDate");
                            String thumbnail = jsonObject.optString("thumbnail");
                            String image = jsonObject.optString("image");
                            String sumary = jsonObject.optString("sumary");
                            double rating = jsonObject.optDouble("rating");
                            movie.setId(id);
                            movie.setName(name);
                            movie.setDuration(duration);
                            movie.setGenres(genres);
                            movie.setStartingDate(startingDate);
                            movie.setEndingDate(endingDate);
                            movie.setThumbnail(thumbnail);
                            movie.setImage(image);
                            movie.setSumary(sumary);
                            movie.setRating(rating);
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
    public void createMovie(String name, int duration, List<String> genresId, String start, String end,
                            String thumbnail, String image, String sumary, double rating, MovieCallbacks callbacks){
        String id = UUID.randomUUID().toString();
        Movie movie = new Movie(id, name, duration, genresId, start, end, thumbnail, image, sumary, rating);
        for(String genre: genresId){
            database.child(GenresModel.GENRES_COLLECTION).child(genre).child("movies").child(id).setValue(movie.toMap()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callbacks.onSuccess(movie);
                } else {
                    callbacks.onFailed(task.getException());
                }
            });
        }
    }
}
