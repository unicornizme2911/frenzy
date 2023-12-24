package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SearchMovieAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.MovieModel;

import java.util.ArrayList;

public class SearchMovieFragment extends Fragment {
    private MovieModel movieModel = new MovieModel();
    private User user;
    private static final String TAG = "SearchMovieFragment";
    private final SearchMovieFragment searchMovieFragment = this;
    private RecyclerView listMovie;
    public SearchMovieFragment(User user){
        this.user = user;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        listMovie = view.findViewById(R.id.rv_list_movie_search);
        view.findViewById(R.id.iv_back).setOnClickListener(view1 -> changeFragment(new HomeFragment(user)));
        downlist();
        return view;
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void downlist(){
        movieModel.getAllMovie(new MovieModel.MoviesCallbacks() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                SearchMovieAdapter listMovieAdapter = new SearchMovieAdapter(searchMovieFragment.getContext(),movies);
                listMovie.setLayoutManager(layoutManager);
                listMovie.setAdapter(listMovieAdapter);
                listMovieAdapter.OnSetClickListener(new SearchMovieAdapter.OnClickListener() {
                    @Override
                    public void OnClick(Movie movie) {
                        changeFragment(new MovieDetailFragment(movie,user));
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
