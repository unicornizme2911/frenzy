package com.example.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ListMovieFollowAdapter;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.authentication.RegisterActivity;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.MovieModel;
import com.example.myapplication.models.UserModel;

import java.util.ArrayList;

public class MovieFollowFragment extends Fragment {
    private User user;
    private static final String TAG = "MovieFollowFragment";
    private final MovieFollowFragment movieFollowFragment = this;

    private TextView name, logout;
    private final UserModel userModel = new UserModel();
    private final MovieModel movieModel = new MovieModel();


    public MovieFollowFragment(User user ) {
        this.user = user;
    }
    private RecyclerView listMovieFollow;
    private ListMovieFollowAdapter listMovieAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_follow, container, false);
        view.findViewById(R.id.iv_back).setOnClickListener(view1 -> changeFragment(new DetailUserFragment(user)));
        init(view);
        downlist();
        return view;
    }
    private void init(View view){
        listMovieFollow = view.findViewById(R.id.rv_list_movie_follow);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listMovieAdapter = new ListMovieFollowAdapter(movieFollowFragment.getContext(),user);
        listMovieFollow.setLayoutManager(layoutManager);
        listMovieFollow.setAdapter(listMovieAdapter);
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void downlist(){
        ArrayList<Movie> moviesData= new ArrayList<>();
        Log.d(TAG, "downlist: "+user.getMovieIds());

        movieModel.getAllMovie(new MovieModel.MoviesCallbacks() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                if(user.getMovieIds() == null){
                }else{
                    for(String idMovie:user.getMovieIds()){
                        for(int i =0; i< movies.size();i++){
                            if(idMovie.equals(movies.get(i).getId())){
                                moviesData.add(movies.get(i));
                            }
                        }
                    }
                    if(!moviesData.isEmpty()){
                        listMovieAdapter.setData(moviesData,user.getUuid());
                    }
                }

            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }
}
