package com.example.myapplication.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.models.MovieModel;
import com.example.myapplication.models.TheaterModel;
import com.example.myapplication.models.TicketModel;
import com.example.myapplication.models.UserModel;
import com.example.myapplication.utlis.EmailUtils;
import com.example.myapplication.utlis.PhoneUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText username;
    private EditText password;
    private TextView forgotPassword;
    private final UserModel userModel = new UserModel();

    private Button btnDangky;
    private Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: "+ "login");
        btnDangky = findViewById(R.id.btn_to_register);
        btnDangNhap = findViewById(R.id.btn_login);
        username = findViewById(R.id.et_username_lg);
        password = findViewById(R.id.et_password_lg);
        forgotPassword = findViewById(R.id.tv_forgot_password);
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapRegister();
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = username.getText().toString();
                String txt_password = password.getText().toString();
                Log.d(TAG, txt_username);
                if(TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_username)){
                    Toast.makeText(LoginActivity.this,"Account dose not exits",Toast.LENGTH_LONG).show();
                }else{
                    userModel.login(txt_username, txt_password, new UserModel.LoginCallbacks() {
                        @Override
                        public void onSuccess(User user) {
                            Log.d("User Login","success");
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new HomeFragment(user))
                                    .commit();
                        }
                        @Override
                        public void onFailed(Exception e) {
                            Snackbar.make(view, "Account dose not exits" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
//                finish();
            }
        });
    }
    private void swapRegister(){
        Intent sw = new Intent(this, RegisterActivity.class);
        startActivity(sw);
    }


}