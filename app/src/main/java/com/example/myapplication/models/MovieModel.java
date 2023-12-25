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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

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
    public interface ShowTimesCallbacks{
        void onSuccess(ArrayList<String> showTimes);
        void onFailed(Exception e);
    }
    public interface GenreOfMovieCallbacks{
        void onSuccess(ArrayList<String> genres);
        void onFailed(Exception e);
    }
    public MovieModel() {
        super();
    }

    public MovieModel(DatabaseReference database){
        super(database);
    }

    public void getMovie(String id, MovieCallbacks callbacks){
        database.child(MOVIE_COLLECTION).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
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
                List<String> temp_showTimes = Arrays.asList(jsonObject.optString("showTime").split("[,{}]"));
                List<String> showTimes = new ArrayList<>();
                for(int i = 0; i < temp_showTimes.size(); i++){
                    if(temp_showTimes.get(i).equals("")) continue;
                    String[] parts = temp_showTimes.get(i).replace("\"", "").split(":");
                    String replacedText = parts[0] + ":" + parts[1] + " ~ " + parts[2] + ":" + parts[3];
                    showTimes.add(replacedText);
                }
                Movie movie = new Movie(id, name, duration, genres, artists, showTimes, startingDate, endingDate, trailer, image, sumary, rating, price);
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
                    List<String> temp_showTimes = Arrays.asList(jsonObject.optString("showTime").split("[,{}]"));
                    List<String> showTimes = new ArrayList<>();
                    for(int i = 0; i < temp_showTimes.size(); i++){
                        if(temp_showTimes.get(i).equals("")) continue;
                        String[] parts = temp_showTimes.get(i).replace("\"", "").split(":");
                        String replacedText = parts[0] + ":" + parts[1] + " ~ " + parts[2] + ":" + parts[3];
                        showTimes.add(replacedText);
                    }
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
                    movie.setShowTimes(showTimes);
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
    public void getMovieByGenre(String genreId, MoviesCallbacks callbacks){
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
                    List<String> temp_showTimes = Arrays.asList(jsonObject.optString("showTime").split("[,{}]"));
                    List<String> showTimes = new ArrayList<>();
                    for(int i = 0; i < temp_showTimes.size(); i++){
                        if(temp_showTimes.get(i).equals("")) continue;
                        String[] parts = temp_showTimes.get(i).replace("\"", "").split(":");
                        String replacedText = parts[0] + ":" + parts[1] + " ~ " + parts[2] + ":" + parts[3];
                        showTimes.add(replacedText);
                    }
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
                    movie.setShowTimes(showTimes);
                    for(Genre genre : genres){
                        if(genre.getId().equals(genreId)){
                            movies.add(movie);
                            break;
                        }
                    }
                }
                callbacks.onSuccess(movies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void movieWithTime(String type, MoviesCallbacks callbacks){
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(currentDate);
        database.child(MOVIE_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Movie> movies = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Movie movie = new Movie();
                    JSONObject jsonObject = new JSONObject((Map) dataSnapshot.getValue());
                    String id = dataSnapshot.getKey();
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
                    List<String> temp_showTimes = Arrays.asList(jsonObject.optString("showTime").split("[,{}]"));
                    List<String> showTimes = new ArrayList<>();
                    for(int i = 0; i < temp_showTimes.size(); i++){
                        if(temp_showTimes.get(i).equals("")) continue;
                        String[] parts = temp_showTimes.get(i).replace("\"", "").split(":");
                        String replacedText = parts[0] + ":" + parts[1] + " ~ " + parts[2] + ":" + parts[3];
                        showTimes.add(replacedText);
                    }
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
                    movie.setShowTimes(showTimes);
                    try{
                        Date starting = sdf.parse(startingDate);
                        Date ending = sdf.parse(endingDate);
                        Log.e(TAG, "onDataChange: " + starting + " " + ending + " " + currentDate);
                        if(type.equals("now")){
                            if(starting.compareTo(currentDate) <= 0 && ending.compareTo(currentDate) >= 0){
                                movies.add(movie);
                            }
                        }else if(type.equals("coming")){
                            if(starting.compareTo(currentDate) > 0){
                                movies.add(movie);
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                callbacks.onSuccess(movies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void getGenresOfMovie(String movieId, GenreOfMovieCallbacks callbacks){
        database.child(MOVIE_COLLECTION).child(movieId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    return;
                }
                ArrayList<String> genres = new ArrayList<>();
                JSONObject jsonObject = new JSONObject((Map) snapshot.getValue());
                String genreIds = jsonObject.optString("genres");
                Gson gson = new Gson();
                Map<String, Map<String, String>> genreMap = gson.fromJson(genreIds, HashMap.class);
                for (Map.Entry<String, Map<String, String>> entry : genreMap.entrySet()) {
                    String genre_id = entry.getKey();
                    genres.add(genre_id);
                }
                callbacks.onSuccess(genres);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: " + error.getMessage());
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void getTimeOfMovie(String movieId, ShowTimesCallbacks callbacks){
        database.child(MOVIE_COLLECTION).child(movieId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    callbacks.onFailed(new Exception("Movie not found"));
                    return;
                }
                JSONObject jsonObject = new JSONObject((Map) snapshot.getValue());
                List<String> temp_showTimes = Arrays.asList(jsonObject.optString("showTime").split("[,{}]"));
                ArrayList<String> showTimes = new ArrayList<>();
                for(int i = 0; i < temp_showTimes.size(); i++){
                    if(temp_showTimes.get(i).equals("")) continue;
                    String[] parts = temp_showTimes.get(i).replace("\"", "").split(":");
                    String replacedText = parts[0] + ":" + parts[1] + " ~ " + parts[2] + ":" + parts[3];
                    showTimes.add(replacedText);
                }
                callbacks.onSuccess(showTimes);
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
                            List<String> temp_showTimes = Arrays.asList(jsonObject.optString("showTime").split("[,{}]"));
                            List<String> showTimes = new ArrayList<>();
                            for(int i = 0; i < temp_showTimes.size(); i++){
                                if(temp_showTimes.get(i).equals("")) continue;
                                String[] parts = temp_showTimes.get(i).replace("\"", "").split(":");
                                String replacedText = parts[0] + ":" + parts[1] + " ~ " + parts[2] + ":" + parts[3];
                                showTimes.add(replacedText);
                            }
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
                            movie.setShowTimes(showTimes);
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
    public void createMovie(String name, int duration, List<String> genreIds, List<String> actorIds, List<String> showTimes, String start, String end,
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
        Movie movie = new Movie(id, name, duration, genres, actorIds, showTimes, start, end, trailer, image, sumary, rating, price);
        database.child(MOVIE_COLLECTION).child(id).setValue(movie.toMap()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callbacks.onSuccess(movie);
            } else {
                callbacks.onFailed(task.getException());
            }
        });
    }
}