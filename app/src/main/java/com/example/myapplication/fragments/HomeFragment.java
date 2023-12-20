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
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.models.MovieModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private String id;
    private final HomeFragment homeFragment = this;
    private final MovieModel movieModel = new MovieModel();
    private ArrayList<Movie> movies= new ArrayList<>();
    private RecyclerView rvListmovie, rvListCinema, rvRandomMovie;
    public HomeFragment(String id){
        this.id = id;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        view.findViewById(R.id.iconLogin).setOnClickListener(view1 -> changeFragment(new DetailUserFragment(id)));
        ListMovieAdapter movieListAdapter = new ListMovieAdapter(homeFragment.getContext(), movies);
        init(view);
        downloadMovie();
        return view;
    }
    public void init(View view){
        rvListmovie = view.findViewById(R.id.rv_home_movielist_container);
        rvListCinema = view.findViewById(R.id.rv_theaters_near_you);
        rvRandomMovie = view.findViewById(R.id.rv_home_phimrandom);
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void downloadMovie(){
        movieModel.getAllMovie(new MovieModel.MoviesCallbacks() {
            @Override
            public void onSuccess(ArrayList<Movie> moviesData) {
                movies = moviesData;
                Log.d("get movie at home", movies.toString());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvListmovie.setLayoutManager(layoutManager);
                ListMovieAdapter listMovieAdapter = new ListMovieAdapter(homeFragment.getContext(),movies);
                rvListmovie.setAdapter(listMovieAdapter);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
