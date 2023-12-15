package com.example.myapplication.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.fragments.HomeFragment;

public class LoginActivity extends AppCompatActivity {

    private Button btnDangky;
    private Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
