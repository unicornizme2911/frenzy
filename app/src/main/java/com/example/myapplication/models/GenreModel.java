package com.example.myapplication.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.entities.Genre;
import com.example.myapplication.entities.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class GenreModel extends Model{
    public static final String TAG = "GenresModel";
    public static final String GENRE_COLLECTION = "genres";
    public interface GenresCallbacks{
        void onSuccess(ArrayList<Genre> genres);
        void onFailed(Exception e);
    }
    public interface GenreCallbacks{
        void onSuccess(Genre genre);
        void onFailed(Exception e);
    }
    public GenreModel() {
        super();
    }
    public GenreModel(DatabaseReference database){
        super(database);
    }
    public void getGenres(GenresCallbacks callbacks){
        database.child(GENRE_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Genre> genres = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Genre genre = new Genre();
                    JSONObject jsonObject = new JSONObject((Map) dataSnapshot.getValue());
                    genre.setId(dataSnapshot.getKey());
                    genre.setName(jsonObject.optString("name"));
                    genres.add(genre);
                }
                callbacks.onSuccess(genres);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void getGenre(String genreId, GenreCallbacks callbacks){
        database.child(GENRE_COLLECTION).child(genreId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Genre genre = new Genre();
                JSONObject jsonObject = new JSONObject((Map) snapshot.getValue());
                genre.setId(snapshot.getKey());
                genre.setName(jsonObject.optString("name"));
                Log.e(TAG, "onDataChange: " + genre.toString());
                callbacks.onSuccess(genre);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void createGenre(String name, String description, ArrayList<Movie> movies, GenreCallbacks callbacks){
        String genreId = database.child(GENRE_COLLECTION).push().getKey();
        Genre genre = new Genre(genreId, name);
        Map<String, Object> genreMap = genre.toMap();
        database.child(GENRE_COLLECTION).child(genreId).setValue(genre).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.e(TAG, "createGenre: " + genre.toString());
                callbacks.onSuccess(genre);
            } else {
                Log.e(TAG, "createGenre: " + task.getException().getMessage());
                callbacks.onFailed(task.getException());
            }
        });
    }
}
