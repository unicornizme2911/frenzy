package com.example.myapplication.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.authentication.ForgotPasswordActivity;
import com.example.myapplication.authentication.LoginActivity;
import com.example.myapplication.authentication.RegisterActivity;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.UserModel;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfileFragment extends Fragment {
    EditText fullname, address, birthday;
    ImageButton bd;
    MaterialButton submit, changePassword;
    private RadioButton male, female;
    private RadioGroup gender;
    private String btn_gender;
    private User user;
    private Context context;
    private final UserModel userModel = new UserModel();
    public UpdateProfileFragment(User user){
        this.user = user;
    }
    private static final String TAG = "UpdateProfileFragment";
    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_user, container, false);
        init(view);
        fillData();
        imageView = view.findViewById(R.id.iv_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new DetailUserFragment(user));
            }
        });
        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioF) {
                    btn_gender= "Female";
                } else if (checkedId == R.id.radioM) {
                    btn_gender = "Male";
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullnameStr = fullname.getText().toString();
                String addressStr = address.getText().toString();
                String birthdayStr = birthday.getText().toString();
                String genderStr = btn_gender;
                if(TextUtils.isEmpty(fullnameStr) || TextUtils.isEmpty(addressStr)) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                Map<String, Object> map = new HashMap<>();
                map.put("fullname", fullnameStr);
                map.put("address", addressStr);
                map.put("birthday", birthdayStr);
                map.put("gender", genderStr);
                userModel.updateUser(user.getUuid(), map, new UserModel.UserCallbacks() {
                    @Override
                    public void onSuccess(User user) {
                        Toast.makeText(getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                        changeFragment(new DetailUserFragment(user));
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return view;
    }
    private void fillData(){
        userModel.getUser(user.getUuid(), new UserModel.UserCallbacks() {
            @Override
            public void onSuccess(User user) {
                fullname.setText(user.getFullname().toString());
                address.setText(user.getAddress().toString());
                birthday.setText(user.getBirthday().toString());
                if (user.getGender().equalsIgnoreCase("male")){
                    gender.check(R.id.radioM);
                } else {
                    gender.check(R.id.radioF);
                }
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    public void init(View view){
        fullname = view.findViewById(R.id.et_fullname);
        address = view.findViewById(R.id.et_address);
        birthday = view.findViewById(R.id.et_bd);
        bd = view.findViewById(R.id.ig_bd);
        gender = view.findViewById(R.id.radioGrp);
        submit = view.findViewById(R.id.btn_submit);
        changePassword = view.findViewById(R.id.btn_changePassword);
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.YEAR, year);
                        birthday.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                    }
                },
                dayOfMonth, month, year);
        datePickerDialog.show();
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
