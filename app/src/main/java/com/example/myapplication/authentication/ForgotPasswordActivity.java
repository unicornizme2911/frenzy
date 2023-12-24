package com.example.myapplication.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.models.UserModel;
import com.example.myapplication.utlis.PasswordUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgotPasswordActivity";
    private static final String COUNTRY_CODE = "+84";
    TextInputEditText edtPhoneNumber, edtNewPassword, edtOldPassword;
    Button btnSubmit, btnCancel;
    PhoneAuthProvider provider;
    PhoneAuthOptions options;
    FirebaseAuth mAuth;
    UserModel userModel;

    String phoneNumber = "", newPassword = "", oldPassword = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleForgotPassword();
            }
        });

    }

    private void init() {
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        mAuth = FirebaseAuth.getInstance();
        userModel = new UserModel();
    }

    private void handleForgotPassword() {
        String phoneNumber = edtPhoneNumber.getText().toString();
        String newPassword = edtNewPassword.getText().toString();
        String oldPassword = edtOldPassword.getText().toString();

        if (phoneNumber.isEmpty()) {
            edtPhoneNumber.setError("Phone number is required");
            edtPhoneNumber.requestFocus();
            return;
        }

        if (newPassword.isEmpty()) {
            edtNewPassword.setError("New password is required");
            edtNewPassword.requestFocus();
            return;
        }

        if (oldPassword.isEmpty()) {
            edtOldPassword.setError("Confirm new password is required");
            edtOldPassword.requestFocus();
            return;
        }
        options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+84" + phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(ForgotPasswordActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        Log.d(TAG, "onCodeSent: code sent" + s);
                        Intent otpIntent = new Intent(ForgotPasswordActivity.this, OTPVerifyActivity.class);
                        otpIntent.putExtra("verificationId", s);
                        otpIntent.putExtra("phone", phoneNumber.trim());
                        otpIntent.putExtra("oldPassword", oldPassword.trim());
                        otpIntent.putExtra("recentToken", forceResendingToken);
                        String newPass = newPassword.trim();
                        Log.d(TAG, "onCodeSent: new pass" + newPass);
                        try {
                            otpIntent.putExtra("newPassword", newPass);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(ForgotPasswordActivity.this, "Password invalid", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        otpIntent.putExtra("recentToken", forceResendingToken);
                        otpIntent.putExtra("actionOption", OTPVerifyActivity.FORGOT_PASSWORD);
                        startActivity(otpIntent);
                        finish();
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}
