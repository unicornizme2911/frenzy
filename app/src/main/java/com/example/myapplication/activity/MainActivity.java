package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.example.myapplication.authentication.LoginActivity;
import com.example.myapplication.authentication.RegisterActivity;
import com.example.myapplication.fragments.DetailUserFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.d(TAG, "onCreate: "+ String.valueOf(i));
        if (savedInstanceState == null && i ==1) {
            i++;
            Intent sw = new Intent(this, LoginActivity.class);
            startActivity(sw);
        }

    }




}