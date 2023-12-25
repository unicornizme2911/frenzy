package com.example.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HistoryPaymentAdapter;
import com.example.myapplication.adapter.ListMovieFollowAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.UserModel;

import java.util.ArrayList;

public class PaymentHistoryFragment extends Fragment {
    private User user;
    private static final String TAG = "PaymentHistoryFragment";
    private final PaymentHistoryFragment paymentHistoryFragment = this;
    private UserModel userModel = new UserModel();
    private RecyclerView rv_payhis;
    private HistoryPaymentAdapter historyPaymentAdapter;


    public PaymentHistoryFragment(User user){
        this.user = user;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_payment_history, container, false);
        view.findViewById(R.id.iv_back_history_payment).setOnClickListener(view1 -> changeFragment(new HomeFragment(user)));
        rv_payhis = view.findViewById(R.id.rv_list_history);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        historyPaymentAdapter = new HistoryPaymentAdapter(paymentHistoryFragment.getContext(),user);
        rv_payhis.setLayoutManager(layoutManager);
        rv_payhis.setAdapter(historyPaymentAdapter);
        init(view);
        return view;
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void init(View view){


        userModel.historyMovie(user.getUuid(), new UserModel.HistoryMovieCallbacks() {

            @Override
            public void onSuccess(ArrayList<Movie> movies, String ticket, ArrayList<String> invoice) {
                Log.e(TAG, "onSuccess: "+ticket +""+invoice );
                historyPaymentAdapter.setData(movies,invoice);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
