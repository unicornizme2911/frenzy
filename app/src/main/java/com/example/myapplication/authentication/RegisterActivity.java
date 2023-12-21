package com.example.myapplication.authentication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.User;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.models.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText fullname;
    private final UserModel userModel = new UserModel();
    private EditText phone;
    private EditText email;
    private Context context;


    private EditText password;
    private EditText khuvuc;
    private EditText birthday;
    private Button registerUser;
    private RadioButton fmale;
    private RadioButton male;
    private RadioGroup gender;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        ImageButton bd = findViewById(R.id.ig_bd);
        context = this;
        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Kiểm tra xem RadioButton nào được chọn
                if (checkedId == R.id.radioF) {
                    fmale.setText("Female");
                } else if (checkedId == R.id.radioM) {
                    fmale.setText("Male");
                }
            }
        });
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_fullname = fullname.getText().toString();
                String txt_phone = phone.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_khuvuc = khuvuc.getText().toString();
                String txt_bd = birthday.getText().toString();
                String txt_gender = fmale.getText().toString();
                if(TextUtils.isEmpty(txt_fullname) || TextUtils.isEmpty(txt_phone)|| TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_khuvuc)){
                    Toast.makeText(RegisterActivity.this,"Empty credentials",Toast.LENGTH_LONG).show();
                } else if(txt_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password to short", Toast.LENGTH_LONG).show();
                } else
                {
//                    userModel.register(txt_email, txt_phone, txt_password,txt_fullname ,txt_khuvuc,txt_bd, txt_gender, new UserModel.RegisterCallbacks() {
//                        @Override
//                        public void onSuccess(User user) {
//                            Toast.makeText(RegisterActivity.this,"Successful account registration",Toast.LENGTH_LONG).show();
////                            getSupportFragmentManager().beginTransaction()
////                                    .replace(R.id.fragment_container, new HomeFragment(user))
////                                    .commit();
//                            swapLogin();
//                        }
//                        @Override
//                        public void onFailed(Exception e) {
//                            Snackbar.make(view, "Register fail: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
//
//                        }
//                    });
                    userModel.checkUserIsExists(txt_phone, new UserModel.CheckExistsCallbacks() {
                        @Override
                        public void onExists() {
                            Toast.makeText(RegisterActivity.this, "User is exist", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNotFound() {
                            userModel.register(txt_email, txt_phone, txt_password,txt_fullname ,txt_khuvuc,txt_bd,txt_gender, new UserModel.RegisterCallbacks() {
                                @Override
                                public void onSuccess(User user) {
                                    Toast.makeText(RegisterActivity.this,"Successful account registration",Toast.LENGTH_LONG).show();
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.fragment_container, new HomeFragment(user))
                                            .commit();
                                }
                                @Override
                                public void onFailed(Exception e) {
                                    Snackbar.make(view, "Register fail", Snackbar.LENGTH_LONG).show();

                                }
                            });
                        }
                    });
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
        birthday = findViewById(R.id.et_bd);
        gender = findViewById(R.id.radioGrp);
        fmale = findViewById(R.id.radioF);
        registerUser = findViewById(R.id.btn_to_register);
    }
    private void swapLogin(){
        Intent sw = new Intent(this, LoginActivity.class);
        startActivity(sw);
    }

    private void showDatePickerDialog() {
        // Lấy ngày và thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        // Tạo đối tượng DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.YEAR, year);
                        birthday.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));

                    }

                },
                dayOfMonth, month, year);
        // Hiển thị DatePickerDialog
        datePickerDialog.show();

    }

}
