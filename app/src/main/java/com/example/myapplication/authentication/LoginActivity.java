package com.example.myapplication.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.models.MovieModel;

public class LoginActivity extends AppCompatActivity {

    private Button btnDangky;
    private Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MovieModel movieModel = new MovieModel();
        movieModel.getMovie("0XP3WQZ7", new MovieModel.MovieCallbacks() {
            @Override
            public void onSuccess(Movie movie) {
                Log.e("Movie", movie.toString());
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
        btnDangky = findViewById(R.id.btn_to_register);
        btnDangNhap = findViewById(R.id.btn_login);
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapRegister();
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new HomeFragment())
                            .commit();
                }
            }
        });
    }
    private void swapHome(){
        Intent goHome = new Intent(this, MainActivity.class);
        startActivity(goHome);
    }
    private void swapRegister(){
        Intent sw = new Intent(this, RegisterActivity.class);
        startActivity(sw);
    }


}