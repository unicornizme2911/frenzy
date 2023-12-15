package com.example.myapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.example.myapplication.fragments.DetailUserFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private Toolbar mToolbarMain;
    private ImageView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Log.d(TAG,"Helo may cung");
        ImageView imageView = findViewById(R.id.iconLogin);
        imageView.setOnClickListener(view -> new DetailUserFragment());
    }

    private void init(){
        Log.d(TAG, "onCreate");
        mToolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
    }


}