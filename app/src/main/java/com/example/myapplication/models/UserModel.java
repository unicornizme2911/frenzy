package com.example.myapplication.models;

import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.entities.User;
import com.example.myapplication.utlis.EmailUtils;
import com.example.myapplication.utlis.PasswordUtils;
import com.example.myapplication.utlis.PhoneUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class UserModel extends Model{
    public static final String TAG = "UserModel";
    private static final String USER_COLLECTION = "users";

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
    public UserModel() {
        super();
    }
    public UserModel(DatabaseReference database){
        super(database);
    }

    public void login(String email, String password, LoginCallbacks callbacks){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();
                        database.child(USER_COLLECTION).child(uid).get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                User user = new User(task1.getResult().getValue(User.class));
                                callbacks.onSuccess(user);
                            } else {
                                callbacks.onFailed(task1.getException());
                            }
                        });
                    } else {
                        callbacks.onFailed(task.getException());
                    }
                });
    }
    public void loginWithPhone(String phone, String password, LoginCallbacks callbacks){
        Log.e(TAG, "loginWithPhone: " + phone + " " + password );
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
                            userMap.put(key, userObject.get(key));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    user = new User(userMap);
                    if(user != null){
                        try {
                            boolean isMatch = PasswordUtils.verifyPassword(password, user.getPassword());
                            if(isMatch && user.getPhone().equals(phone)){
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
    public void register(String email, String phone, String password, String birthday, String gender, String address, Uri avatar, RegisterCallbacks callbacks) {
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
            User user = new User(uuid, email, gender, hashedPassword, phone, address, birthday, "user", avatar, createDate);
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
    public void changePassword(String newPassword, String oldPassword, String email, LoginCallbacks callbacks){
        try{
            if(PasswordUtils.verifyPassword(oldPassword, newPassword)){
                callbacks.onFailed(new Exception("New password must be different from old password"));
                return;
            }
            String hashedPassword = PasswordUtils.hashPassword(newPassword);
            mAuth.signInWithEmailAndPassword(email, oldPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String uid = mAuth.getCurrentUser().getUid();
                            database.child(USER_COLLECTION).child(uid).child("password").setValue(hashedPassword)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            callbacks.onSuccess(null);
                                        } else {
                                            callbacks.onFailed(task1.getException());
                                        }
                                    });
                        } else {
                            callbacks.onFailed(task.getException());
                        }
                    });
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String getCurrentUserUid(){
        return mAuth.getCurrentUser().getUid();
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
    public void getUser(String uuid, UserCallbacks callbacks){
        database.child(USER_COLLECTION).child(uuid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = new User(task.getResult().getValue(User.class));
                callbacks.onSuccess(user);
            } else {
                callbacks.onFailed(task.getException());
            }
        });
    }
    public void updateUser(String uuid, Map<String, Object> newData, UserCallbacks callbacks){
        database.child(USER_COLLECTION).child(uuid).updateChildren(newData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callbacks.onSuccess(null);
            } else {
                callbacks.onFailed(task.getException());
            }
        });
    }
}
