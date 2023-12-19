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
import com.example.myapplication.entities.User;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.models.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText fullname;
    private final UserModel userModel = new UserModel();
    private EditText phone;
    private EditText email;
    private EditText password;
    private EditText khuvuc;

    private Button registerUser;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_fullname = fullname.getText().toString();
                String txt_phone = phone.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_khuvuc = khuvuc.getText().toString();
                Log.d("Register User",txt_phone);
                if(TextUtils.isEmpty(txt_fullname) || TextUtils.isEmpty(txt_phone)|| TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_khuvuc)){
                    Toast.makeText(RegisterActivity.this,"Empty credentials",Toast.LENGTH_LONG).show();
                } else if(txt_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password to short", Toast.LENGTH_LONG).show();
                } else
                {
//                    userModel.register(txt_email, txt_phone, txt_password, txt_khuvuc, new UserModel.RegisterCallbacks() {
//                        @Override
//                        public void onSuccess(User user) {
//                            Toast.makeText(RegisterActivity.this,"Successful account registration",Toast.LENGTH_LONG).show();
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.fragment_container, new HomeFragment(user.getUuid()))
//                                    .commit();
//                        }
//                        @Override
//                        public void onFailed(Exception e) {
//                            Snackbar.make(view, "Register fail", Snackbar.LENGTH_LONG).show();
//
//                        }
//                    });
//                    userModel.checkUserIsExists(txt_phone, new UserModel.CheckExistsCallbacks() {
//                        @Override
//                        public void onExists() {
//                            Toast.makeText(RegisterActivity.this, "User is exist", Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onNotFound() {
//                            userModel.register(txt_email, txt_phone, txt_password, txt_khuvuc, new UserModel.RegisterCallbacks() {
//                                @Override
//                                public void onSuccess(User user) {
//                                    Log.d("user", user.toString());
//                                }
//                                @Override
//                                public void onFailed(Exception e) {
//                                    Snackbar.make(view, "Register fail", Snackbar.LENGTH_LONG).show();
//
//                                }
//                            });
//                        }
//                    });
                }
            }
        });
    }
    private void init(){
        fullname = findViewById(R.id.et_fullname);
        phone = findViewById(R.id.et_phone);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        khuvuc = findViewById(R.id.et_khuvuc);
        registerUser = findViewById(R.id.btn_to_register);
    }
    private void swapLogin(){
        Intent sw = new Intent(this, LoginActivity.class);
        startActivity(sw);
    }

}
