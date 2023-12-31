package com.example.myapplication.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Model {
    public static final String TAG = "Model";
    protected FirebaseAuth mAuth;
    protected DatabaseReference database;
    protected FirebaseStorage storage;
    protected StorageReference storageRef;

    public Model() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        database.keepSynced(true);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    public Model(DatabaseReference database){
        mAuth = FirebaseAuth.getInstance();
        this.database = database;
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public DatabaseReference getDatabase() {
        return database;
    }
}
