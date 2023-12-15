package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.PaymentHistoryActivity;


public class DetailUserFragment extends Fragment {

    private static final String TAG = "FragHome2";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_user2, container, false);
        init(view);
        Listener(view);
        return view;
    }

    private void Listener(View view){
        view.findViewById(R.id.iv_close).setOnClickListener(view1 -> changeFragment(new HomeFragment()));
        view.findViewById(R.id.iv_home).setOnClickListener(view1 -> changeFragment(new HomeFragment()));
        Intent intent = new Intent();
        intent.setClass(getActivity(), PaymentHistoryActivity.class);
        view.findViewById(R.id.LN_history_payment).setOnClickListener(view1 ->
                getActivity().startActivity(intent));

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