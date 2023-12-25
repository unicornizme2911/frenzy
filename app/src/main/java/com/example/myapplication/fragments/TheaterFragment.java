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
import com.example.myapplication.adapter.home.ListTheatersAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.Theater;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.TheaterModel;

import java.util.ArrayList;

public class TheaterFragment extends Fragment {
    private RecyclerView rvListTheaters;

    private static final String TAG = "TheaterFragment";
    private final TheaterFragment theaterFragment = this;

    private final TheaterModel theaterModel = new TheaterModel();
    private ArrayList<Theater> theaters = new ArrayList<>();
    private User user;
    private RecyclerView listTheaters;
    public  TheaterFragment(User user){
        this.user = user;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_theater, container, false);
        view.findViewById(R.id.iv_back).setOnClickListener(view1 -> changeFragment(new DetailUserFragment(user)));
        rvListTheaters = view.findViewById(R.id.rv_list_theater );
        getTheater();
        return view;
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void getTheater(){
        theaterModel.getAllTheaters(new TheaterModel.TheatersCallbacks() {
            @Override
            public void onSuccess(ArrayList<Theater> theaters) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                ListTheatersAdapter listTheatersAdapter = new ListTheatersAdapter(theaterFragment.getContext(),theaters);
                rvListTheaters.setLayoutManager(layoutManager);
                rvListTheaters.setAdapter(listTheatersAdapter);
                listTheatersAdapter.OnSetTheatersListener(new ListTheatersAdapter.OnTheaterListener() {
                    @Override
                    public void OnTheater(Theater theater) {
                        changeFragment(new AllMovieFragment(user));
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
