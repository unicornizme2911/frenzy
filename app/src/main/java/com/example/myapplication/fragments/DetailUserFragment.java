package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.PaymentHistoryActivity;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.UserModel;


public class DetailUserFragment extends Fragment {
    private User user;
    private TextView name;
    private final UserModel userModel = new UserModel();
    public DetailUserFragment(User user){
        this.user = user;
    }
    private static final String TAG = "DetailUserFragment";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_user2, container, false);
        init(view);
        Listener(view);
        name = view.findViewById(R.id.tv_name);
        fillData();
        Intent intent = new Intent();
        intent.setClass(getActivity(), PaymentHistoryActivity.class);
        view.findViewById(R.id.LN_history_payment).setOnClickListener(view1 ->
                changeFragment(new PaymentHistoryFragment(user)));
        return view;
    }
    private void fillData(){
        userModel.getUser(user.getUuid(), new UserModel.UserCallbacks() {
            @Override
            public void onSuccess(User user) {
                name.setText(user.getFullname().toString());
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private void Listener(View view){
        view.findViewById(R.id.iv_close).setOnClickListener(view1 -> changeFragment(new HomeFragment(user)));
        view.findViewById(R.id.iv_home).setOnClickListener(view1 -> changeFragment(new HomeFragment(user)));
    }

    private void init(View view) {

    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}