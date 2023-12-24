package com.example.myapplication.models;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.authentication.ForgotPasswordActivity;
import com.example.myapplication.authentication.OTPVerifyActivity;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.utlis.EmailUtils;
import com.example.myapplication.utlis.PasswordUtils;
import com.example.myapplication.utlis.PhoneUtils;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserModel extends Model{
    public static final String TAG = "UserModel";
    private static final String USER_COLLECTION = "users";
    private PhoneAuthOptions phoneAuthOptions;
    private final MovieModel movieModel = new MovieModel();
    public interface LoginCallbacks{
        void onSuccess(User user);
        void onFailed(Exception e);
    }
    public interface RegisterCallbacks{
        void onSuccess(User user);
        void onFailed(Exception e);
    }
    public interface UserCallbacks{
        void onSuccess(User user);
        void onFailed(Exception e);
    }
    public interface CheckExistsCallbacks{
        void onExists();
        void onNotFound();
    }
    public interface SuggestionsCallbacks{
        void onSuccess(ArrayList<Movie> movies);
        void onFailed(Exception e);
    }
    public UserModel() {
        super();
    }
    public UserModel(DatabaseReference database){
        super(database);
    }

    public void login(String value, String password, LoginCallbacks callbacks){
        Log.e(TAG, "login: " + value + " " + password );
        Query query = database.child(USER_COLLECTION);
        query.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                User user = null;
                boolean found = false;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    JSONObject userObject = new JSONObject((Map) dataSnapshot.getValue());
                    HashMap<String, Object> userMap = new HashMap<>();
                    Iterator<String> keys = userObject.keys();
                    while( keys.hasNext()){
                        String key = keys.next();
                        try {
                            if (key.equals("movieIds") || key.equals("invoiceIds")) {
                                JSONArray jsonArray = userObject.getJSONArray(key);
                                List<String> listIds = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    listIds.add(jsonArray.getString(i));
                                }
                                userMap.put(key, listIds);
                            } else {
                                userMap.put(key, userObject.get(key));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    user = new User(userMap);
                    if(user != null){
                        try {
                            boolean isMatch = PasswordUtils.verifyPassword(password, user.getPassword());
                            if(isMatch && (user.getEmail().equals(value)|| user.getPhone().equals(value))){
                                callbacks.onSuccess(user);
                                found = true;
                                break;
                            }
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (Exception e) {
                            callbacks.onFailed(e);
                            e.printStackTrace();
                        }
                    }
                }
                if(!found){
                    callbacks.onFailed(new Exception("User not found"));
                }else {
                    callbacks.onSuccess(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: " + error.toString());
            }
        });
    }
    public void register(String email, String phone, String password, String fullname, String address, String birthday, String gender, RegisterCallbacks callbacks) {
        try{
            String uuid = UUID.randomUUID().toString();
            String hashedPassword = PasswordUtils.hashPassword(password);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createDate = sdf.format(new Date());
            if(!EmailUtils.isValid(email)){
                callbacks.onFailed(new Exception("Email is not valid"));
                return;
            }
            if(phone.substring(0,3).equals("+84")){
                phone = "0" + phone.substring(3);
            }
            if(!PhoneUtils.isValid(phone)){
                callbacks.onFailed(new Exception("Phone is not valid"));
                return;
            }
            Uri avatar = Uri.parse("android.resource://com.example.myapplication/drawable/avatar");
            List<String> invoiceIds = new ArrayList<>();
            List<String> movieIds = new ArrayList<>();
            User user = new User(uuid, email, hashedPassword, phone, address, gender, birthday, fullname, avatar, createDate, movieIds, invoiceIds);
            database.child(USER_COLLECTION).child(uuid).setValue(user.toMap())
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            callbacks.onSuccess(user);
                        }else{
                            callbacks.onFailed(task.getException());
                        }
                    })
                    .addOnFailureListener(e -> {
                        callbacks.onFailed(e);
                    });
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public void changePassword(String newPassword, String oldPassword, String phone, LoginCallbacks callbacks){
        try{
            if(PasswordUtils.verifyPassword(oldPassword, newPassword)){
                Log.w(TAG, "changePassword: " + "New password must be different from old password");
                callbacks.onFailed(new Exception("New password must be different from old password"));
                return;
            }
            String hashedPassword = PasswordUtils.hashPassword(newPassword);
            String hashedOldPassword = PasswordUtils.hashPassword(oldPassword);
            database.child(USER_COLLECTION).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String phoneUser = (String) dataSnapshot.child("phone").getValue();
                        String passwordUser = (String) dataSnapshot.child("password").getValue();
                        if(phoneUser != null){
                            if(phoneUser.equals(phone) && passwordUser.equals(hashedOldPassword)){
                                database.child(USER_COLLECTION).child(dataSnapshot.getKey()).child("password").setValue(hashedPassword);
                                callbacks.onSuccess(null);
                                break;
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "onCancelled: " + error.toString());
                }
            });
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String getCurrentUserUid(){
        return mAuth.getCurrentUser().getUid();
    }
    public void checkUserIsExists(String value, CheckExistsCallbacks callbacks){
        database.child(USER_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isFound = false;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String phoneUser = (String) dataSnapshot.child("phone").getValue();
                    String emailUser = (String) dataSnapshot.child("email").getValue();
                    if(phoneUser != null && emailUser != null){
                        if(phoneUser.equals(value) || emailUser.equals(value)){
                            isFound = true;
                            callbacks.onExists();
                            break;
                        }
                    }else if (!isFound){
                        callbacks.onNotFound();
                    }
                }if (!isFound) {
                    callbacks.onNotFound();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onNotFound();
            }
        });
    }

    public void forgotPassword(String value, String newPassword, LoginCallbacks callbacks){
        mAuth.sendPasswordResetEmail(value)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callbacks.onSuccess(null);
                    } else {
                        callbacks.onFailed(task.getException());
                    }
                });
    }
    public void forgotPassword(String phone, String newPass, String newPassConfirm, ForgotPasswordActivity activity){
        if(!newPass.equals(newPassConfirm)){
            Log.w(TAG, "forgotPassword: " + "Password and confirm password must be the same");
            return;
        }
        phoneAuthOptions = PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phone)
                .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Log.e(TAG, "onVerificationCompleted: verification completed");
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }
                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        Intent intent = new Intent(activity, OTPVerifyActivity.class);
                        intent.putExtra("phone", phone);
                        intent.putExtra("newPass", newPass);
                        intent.putExtra("verificationId", verificationId);
                        intent.putExtra("actionOption", OTPVerifyActivity.FORGOT_PASSWORD);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }
    public void getUser(String uuid, UserCallbacks callbacks){
        database.child(USER_COLLECTION).child(uuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    callbacks.onFailed(new Exception("User not found"));
                    return;
                }
                JSONObject userObject = new JSONObject((Map) snapshot.getValue());
                HashMap<String, Object> userMap = new HashMap<>();
                Iterator<String> keys = userObject.keys();
                while( keys.hasNext()){
                    String key = keys.next();
                    try {
                        if (key.equals("movieIds") || key.equals("invoiceIds")) {
                            JSONArray jsonArray = userObject.getJSONArray(key);
                            List<String> listIds = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                listIds.add(jsonArray.getString(i));
                            }
                            userMap.put(key, listIds);
                        } else {
                            userMap.put(key, userObject.get(key));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                User user = new User(userMap);
                callbacks.onSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void addMovieToFavorite(String uuid, String movieId, UserCallbacks callbacks){
        database.child(USER_COLLECTION).child(uuid).child("movieIds").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> movieIds = (ArrayList<String>) snapshot.getValue();
                if(movieIds == null){
                    movieIds = new ArrayList<>();
                }
                if(movieIds.contains(movieId)){
                    callbacks.onFailed(new Exception("Movie already in favorite list"));
                    return;
                }
                movieIds.add(movieId);
                database.child(USER_COLLECTION).child(uuid).child("movieIds").setValue(movieIds).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.e(TAG, "onDataChange: " + "add movie to favorite success");
                        callbacks.onSuccess(null);
                    }else{
                        callbacks.onFailed(task.getException());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void removeMovieFromFavorite(String uuid, String movieId, UserCallbacks callbacks){
        database.child(USER_COLLECTION).child(uuid).child("movieIds").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> movieIds = (ArrayList<String>) snapshot.getValue();
                if(movieIds == null){
                    movieIds = new ArrayList<>();
                }
                if(!movieIds.contains(movieId)){
                    callbacks.onFailed(new Exception("Movie not in favorite list"));
                    return;
                }
                movieIds.remove(movieId);
                database.child(USER_COLLECTION).child(uuid).child("movieIds").setValue(movieIds).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.e(TAG, "onDataChange: " + "remove movie from favorite success");
                        callbacks.onSuccess(null);
                    }else{
                        callbacks.onFailed(task.getException());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void suggestMoviesByGenre(String uuid, SuggestionsCallbacks callbacks) {
        database.child(USER_COLLECTION).child(uuid).child("movieIds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> favoriteMovieIds = (ArrayList<String>) snapshot.getValue();
                if(favoriteMovieIds == null){
                    favoriteMovieIds = new ArrayList<>();
                }
                HashMap<String, Integer> genreCountMap = new HashMap<>();

                for (String movieId : favoriteMovieIds) {
                    movieModel.getGenresOfMovie(movieId, new MovieModel.GenreOfMovieCallbacks() {
                        @Override
                        public void onSuccess(ArrayList<String> genres) {
                            for(String genre : genres){
                                int count = genreCountMap.getOrDefault(genre, 0) + 1;
                                genreCountMap.put(genre, count);
                            }
                            String mostWatchedGenre = "";
                            int maxCount = 0;
                            for (Map.Entry<String, Integer> entry : genreCountMap.entrySet()) {
                                if (entry.getValue() > maxCount) {
                                    mostWatchedGenre = entry.getKey();
                                    maxCount = entry.getValue();
                                }
                            }
                            movieModel.getMovieByGenre(mostWatchedGenre, new MovieModel.MoviesCallbacks() {
                                @Override
                                public void onSuccess(ArrayList<Movie> movie) {
                                    callbacks.onSuccess(movie);
                                }
                                @Override
                                public void onFailed(Exception e) {
                                    callbacks.onFailed(e);
                                }
                            });
                        }
                        @Override
                        public void onFailed(Exception e) {
                            Log.e(TAG, "onFailed: " + e.getMessage());
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void updateUser(String uuid, Map<String, Object> newData, UserCallbacks callbacks){
        database.child(USER_COLLECTION).child(uuid).updateChildren(newData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.e(TAG, "onComplete: " + "update user success");
                callbacks.onSuccess(null);
            } else {
                callbacks.onFailed(task.getException());
            }
        });
    }
    public void changeAvatar(String uuid, String avatar, UserCallbacks callbacks){
        database.child(USER_COLLECTION).child(uuid).child("avatar").setValue(avatar).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.e(TAG, "onComplete: " + "change avatar success");
                callbacks.onSuccess(null);
            } else {
                callbacks.onFailed(task.getException());
            }
        });
    }
}
