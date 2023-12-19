package com.example.myapplication.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.models.MovieModel;
import com.example.myapplication.models.UserModel;
import com.example.myapplication.utlis.EmailUtils;
import com.example.myapplication.utlis.PhoneUtils;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private final UserModel userModel = new UserModel();

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
                Log.d("Movie", "success");
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Movie", "fail");
            }
        });
        btnDangky = findViewById(R.id.btn_to_register);
        btnDangNhap = findViewById(R.id.btn_login);
        username = findViewById(R.id.et_username_lg);
        password = findViewById(R.id.et_password_lg);
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
                Log.d("login User", txt_username);
                if(TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_username)){
                    Toast.makeText(LoginActivity.this,"Account dose not exits",Toast.LENGTH_LONG).show();
                }else{
                    if(EmailUtils.isValid(txt_username)){
                        Log.d("login User","email");
                        userModel.login(txt_username, txt_password, new UserModel.LoginCallbacks() {
                            @Override
                            public void onSuccess(User user) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, new HomeFragment(user.getUuid()))
                                        .commit();
                            }
                            @Override
                            public void onFailed(Exception e) {
                                Log.d("pw",txt_password);
                                Log.d("User Login","fail");
                            }
                        });
                    } else if(PhoneUtils.isValid(txt_username)){
                        Log.d("login User","phone");
                        userModel.loginWithPhone(txt_username, txt_password, new UserModel.LoginCallbacks() {
                            @Override
                            public void onSuccess(User user) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, new HomeFragment(user.getUuid()))
                                        .commit();
                            }
                            @Override
                            public void onFailed(Exception e) {
                                Log.d("pw",txt_password);
                                Log.d("User Login","fail");
                            }
                        });
                    } else {
                        Log.d("Login user", "fail");
                        return;
                    }
                }
            }
        });
    }
    private void swapRegister(){
        Intent sw = new Intent(this, RegisterActivity.class);
        startActivity(sw);
    }


}