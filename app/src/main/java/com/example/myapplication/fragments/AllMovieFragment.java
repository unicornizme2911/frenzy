package com.example.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AllMovieAdapter;
import com.example.myapplication.adapter.ListMovieFollowAdapter;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.Theater;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.MovieModel;
import com.example.myapplication.models.UserModel;

import java.util.ArrayList;

public class AllMovieFragment extends Fragment {
    private User user;
    private Theater theater;
    private static final String TAG = "MovieFollowFragment";
    private final AllMovieFragment allMovieFragment = this;
    private RecyclerView listMovie;

    private TextView name, logout;
    private final UserModel userModel = new UserModel();
    private final MovieModel movieModel = new MovieModel();

    public AllMovieFragment(User user, Theater theater ) {
        this.user = user;
        this.theater = theater;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_follow, container, false);
        view.findViewById(R.id.iv_back).setOnClickListener(view1 -> changeFragment(new TheaterFragment(user)));
        init(view);
        downlist();
        return view;
    }
    private void init(View view){
        listMovie = view.findViewById(R.id.rv_list_movie_follow);
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
                AllMovieAdapter listMovieAdapter = new AllMovieAdapter(allMovieFragment.getContext(),movies);
                listMovie.setLayoutManager(layoutManager);
                listMovie.setAdapter(listMovieAdapter);
                listMovieAdapter.OnSetClickListener(new AllMovieAdapter.OnClickListener() {
                    @Override
                    public void OnClick(Movie movie) {
                        changeFragment(new PremiereFragment(movie,user));
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }
}
