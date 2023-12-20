package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.models.MovieModel;

public class PremiereFragment extends Fragment {
    private RecyclerView listPremiere;
    private PremiereFragment premiereFragment;
    private MovieModel movieModel;
    private Movie movie;
    private String userId;
    public PremiereFragment(Movie movie, String userId){
        this.userId = userId;
        this.movie=movie;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_see_premiere, container, false);
        view.findViewById(R.id.iv_back).setOnClickListener(view1 -> changeFragment(new MovieDetailFragment(movie,userId)));

        return view;
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
