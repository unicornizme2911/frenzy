package com.example.myapplication.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.UserModel;
import com.example.myapplication.utlis.PasswordUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTPVerifyActivity extends AppCompatActivity {
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    Button btnVerify;
    TextView tvTimer, tvRecent, tvPhoneNumber;
    String verificationId = "";
    String phone = "";
    String fullname = "";
    String address = "";
    String gender = "";
    String birthday = "";
    String email = "";
    private String newPassword = "";
    private String oldPassword = "";
    String password = "";
    UserModel userModel;
    FirebaseAuth mAuth;
    DatabaseReference database;
    PhoneAuthOptions options;
    private boolean isRecent = false;
    private final int recentTimer = 60;
    private int otpSelection = 0;
    private int actionOption = 0;
    private static final String COUNTRY_CODE = "+84";

    private static final String TAG = "OTPVerifyActivity";

    public static final int REGISTRATION = 1;
    public static final int FORGOT_PASSWORD = 2;
    public static final int RE_LOGIN = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);

        getDataIntent();
        init();
        countTimer();

        tvRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecent) {
                    handleRecentVertification();
                    countTimer();
                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp1.getText().toString().trim().isEmpty() ||
                        otp2.getText().toString().trim().isEmpty() ||
                        otp3.getText().toString().trim().isEmpty() ||
                        otp4.getText().toString().trim().isEmpty() ||
                        otp5.getText().toString().trim().isEmpty() ||
                        otp6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OTPVerifyActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code = otp1.getText().toString() +
                        otp2.getText().toString() +
                        otp3.getText().toString() +
                        otp4.getText().toString() +
                        otp5.getText().toString() +
                        otp6.getText().toString();

                if (verificationId != null) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                    Log.e(TAG, "onClick: action Option: " + actionOption);
                    switch (actionOption) {
                        case REGISTRATION:
                            signInWithPhoneAuthCredential(credential);
                            break;
                        case FORGOT_PASSWORD:
                            resetPasswordAuthCredential(credential);
                            break;
                        default:
                            Toast.makeText(OTPVerifyActivity.this, "Something went wrong when try authentication", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
    }

    private void init() {
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);

        mAuth = FirebaseAuth.getInstance();
        options = PhoneAuthOptions.newBuilder()
                .setActivity(this)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(mCallbacks)
                .setPhoneNumber(phone)
                .build();

        btnVerify = findViewById(R.id.btnVerify);
        tvTimer = findViewById(R.id.tvTimer);
        tvRecent = findViewById(R.id.tvRecent);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);

        tvPhoneNumber.setText(COUNTRY_CODE + " " + phone);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        userModel = new UserModel(database);

        otp1.addTextChangedListener(textWatcher);
        otp2.addTextChangedListener(textWatcher);
        otp3.addTextChangedListener(textWatcher);
        otp4.addTextChangedListener(textWatcher);
        otp5.addTextChangedListener(textWatcher);
        otp6.addTextChangedListener(textWatcher);
    }

    private void showKeyboard(EditText editText) {
        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        verificationId = intent.getStringExtra("verificationId");
        phone = intent.getStringExtra("phone");
        fullname = intent.getStringExtra("fullname");
        password = intent.getStringExtra("password");
        gender = intent.getStringExtra("gender");
        birthday = intent.getStringExtra("birthday");
        address = intent.getStringExtra("address");
        email = intent.getStringExtra("email");
        actionOption = intent.getIntExtra("actionOption", 0);
        newPassword = intent.getStringExtra("newPassword");
        oldPassword = intent.getStringExtra("oldPassword");
    }

    private void countTimer() {
        isRecent = false;
        new CountDownTimer(recentTimer * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("00:" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
                isRecent = true;
                btnVerify.setEnabled(false);
            }
        }.start();
    }

    private void handleRecentVertification() {
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential: success login with phone number");

                            FirebaseUser user = task.getResult().getUser();
                            // Create a new user with a first and last name
                            userModel.register(email, phone, password, fullname, address, birthday, gender, new UserModel.RegisterCallbacks() {
                                @Override
                                public void onSuccess(User user) {
                                    Log.e(TAG, "onSuccess: " + user.toString());
                                    Toast.makeText(OTPVerifyActivity.this,"Successful account registration",Toast.LENGTH_LONG).show();
                                    Intent sw = new Intent(OTPVerifyActivity.this, LoginActivity.class);
                                    startActivity(sw);
                                }

                                @Override
                                public void onFailed(Exception e) {
                                    Log.e(TAG, "onFailed: " + e.getMessage());
//                                    Snackbar.make(view, "Register fail", Snackbar.LENGTH_LONG).show();
                                }
                            });
                            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("uuid", user.getUid());
                            editor.apply();
                            Intent intent = new Intent(OTPVerifyActivity.this, MainActivity.class);
                            intent.putExtra("fullname", fullname);
                            startActivity(intent);
                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTPVerifyActivity.this, "Some thing went wrong ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void resetPasswordAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential: success to reset password");

                            FirebaseUser user = task.getResult().getUser();
                            // Create a new user with a first and last name
                            try {
                                userModel.changePassword(newPassword, oldPassword, phone, new UserModel.LoginCallbacks() {
                                    @Override
                                    public void onSuccess(User user) {
                                        Toast.makeText(OTPVerifyActivity.this, "Successful change password", Toast.LENGTH_LONG).show();
                                        Intent sw = new Intent(OTPVerifyActivity.this, LoginActivity.class);
                                        startActivity(sw);
                                    }
                                    @Override
                                    public void onFailed(Exception e) {
                                        Log.e(TAG, "onFailed: " + e.getMessage());
                                        Snackbar.make(findViewById(android.R.id.content), "Change password fail", Snackbar.LENGTH_LONG).show();
                                    }
                                });
                                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("uuid", user.getUid());
                                editor.apply();
                                Intent intent = new Intent(OTPVerifyActivity.this, MainActivity.class);
                                intent.putExtra("fullname", fullname);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(OTPVerifyActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                                return;
                            }

//                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTPVerifyActivity.this, "Some thing went wrong ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }



    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                switch (otpSelection) {
                    case 0:
                        otpSelection = 1;
                        showKeyboard(otp2);
                        break;
                    case 1:
                        otpSelection = 2;
                        showKeyboard(otp3);
                        break;
                    case 2:
                        otpSelection = 3;
                        showKeyboard(otp4);
                        break;
                    case 3:
                        otpSelection = 4;
                        showKeyboard(otp5);
                        break;
                    case 4:
                        otpSelection = 5;
                        showKeyboard(otp6);
                        break;
                    case 5:
                        otpSelection = 6;
                        showKeyboard(otp6);
                        break;
                }
            }
        }
    };
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTPVerifyActivity.this, "Something went wrong when recent OTP", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            Log.e(TAG, "onKeyUp: otpSelection " + otpSelection);
            switch (otpSelection) {
                case 6:
                    otpSelection = 5;
                    showKeyboard(otp5);
                    break;
                case 5:
                    otpSelection = 4;
                    showKeyboard(otp4);
                    break;
                case 4:
                    otpSelection = 3;
                    showKeyboard(otp3);
                    break;
                case 3:
                    otpSelection = 2;
                    showKeyboard(otp2);
                    break;
                case 2:
                    otpSelection = 1;
                    showKeyboard(otp1);
                    break;
                case 1:
                    otpSelection = 0;
                    showKeyboard(otp1);
                    break;
                default:
                    return true;
            }

        }
        return super.onKeyUp(keyCode, event);
    }
}
