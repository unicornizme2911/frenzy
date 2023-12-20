package com.example.myapplication.models;

import androidx.annotation.NonNull;

import com.example.myapplication.entities.Theater;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TheaterModel extends Model{
    public static final String TAG = "TheaterModel";
    public static final String THEATER_COLLECTION = "theaters";
    public interface TheaterCallbacks{
        void onSuccess(Theater theater);
        void onFailed(Exception e);
    }
    public interface TheatersCallbacks{
        void onSuccess(ArrayList<Theater> theaters);
        void onFailed(Exception e);
    }
    public TheaterModel() {
        super();
    }
    public TheaterModel(DatabaseReference database){
        super(database);
    }

    public void getTheaterById(String id, TheaterCallbacks callback){
        database.child(THEATER_COLLECTION).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    callback.onFailed(new Exception("Theater not found"));
                    return;
                }
                Theater theater = snapshot.getValue(Theater.class);
                callback.onSuccess(theater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailed(new Exception(error.getMessage()));
            }
        });
    }
    public void getTheaterByCity(String city, TheatersCallbacks callback){
        database.child(THEATER_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Theater> theaters = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Theater theater = dataSnapshot.getValue(Theater.class);
                    if(theater.getCity().equals(city)){
                        theaters.add(theater);
                    }
                }
                callback.onSuccess(theaters);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailed(new Exception(error.getMessage()));
            }
        });
    }
    public void getTheaterByDistrict(String district, TheatersCallbacks callback){
        database.child(THEATER_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Theater> theaters = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Theater theater = dataSnapshot.getValue(Theater.class);
                    if(theater.getDistrict().equals(district)){
                        theaters.add(theater);
                    }
                }
                callback.onSuccess(theaters);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailed(new Exception(error.getMessage()));
            }
        });
    }
    public void getTheaterByCityAndDistrict(String city, String district, TheatersCallbacks callback){
        database.child(THEATER_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Theater> theaters = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Theater theater = dataSnapshot.getValue(Theater.class);
                    if(theater.getCity().equals(city) && theater.getDistrict().equals(district)){
                        theaters.add(theater);
                    }
                }
                callback.onSuccess(theaters);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailed(new Exception(error.getMessage()));
            }
        });
    }
    public void getAllTheaters(TheatersCallbacks callback){
        database.child(THEATER_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Theater> theaters = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Theater theater = dataSnapshot.getValue(Theater.class);
                    theaters.add(theater);
                }
                callback.onSuccess(theaters);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailed(new Exception(error.getMessage()));
            }
        });
    }
}
