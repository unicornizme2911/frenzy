package com.example.myapplication.fragments;

import android.health.connect.datatypes.units.Length;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.adapter.home.ListTheatersAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.Theater;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.MovieModel;
import com.example.myapplication.models.TheaterModel;
import com.example.myapplication.models.UserModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private String id,city;
    private final HomeFragment homeFragment = this;
    private final MovieModel movieModel = new MovieModel();
    private final TheaterModel theaterModel = new TheaterModel();
    private final UserModel userModel = new UserModel();

    private ArrayList<Movie> movies= new ArrayList<>();

    private ArrayList<Theater> theaters = new ArrayList<>();
    private RecyclerView rvListmovie, rvListCinema, rvRandomMovie;
    private EditText address;
    private ImageView find;
    private User user;
    private Button phimsapchieu,phimdangchieu;
    private LinearLayout search;
    public HomeFragment(User user){
        this.user = user;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        view.findViewById(R.id.iconLogin).setOnClickListener(view1 -> changeFragment(new DetailUserFragment(user)));
        address = view.findViewById(R.id.et_address);
        find = view.findViewById(R.id.ig_find);
        search = view.findViewById(R.id.LN_search);
        view.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new SearchMovieFragment(user));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new SearchMovieFragment(user));
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+address.getText().toString());
                city = address.getText().toString();
                getTheaterByAddress();
            }
        });
        init(view);
        downloadMovie("now");
        RandomMovie();
        phimsapchieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadMovie("coming");

            }
        });
        phimdangchieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadMovie("now");
            }
        });
        return view;
    }
    public void init(View view){
        rvListmovie = view.findViewById(R.id.rv_home_movielist_container);
        rvListCinema = view.findViewById(R.id.rv_theaters_near_you);
        rvRandomMovie = view.findViewById(R.id.rv_home_phimrandom);
        phimdangchieu = view.findViewById(R.id.btn_home_phimdangchieu);
        phimsapchieu = view.findViewById(R.id.btn_home_phimsapchieu);
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void getTheaterByAddress(){
        theaterModel.getTheaterByCity(city, new TheaterModel.TheatersCallbacks() {
            @Override
            public void onSuccess(ArrayList<Theater> theatersData) {
                Log.d(TAG, "onSuccess: "+ city);
                Log.d(TAG, "onSuccess: "+ theatersData.toString());
                theaters=theatersData;
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                ListTheatersAdapter listTheatersAdapter = new ListTheatersAdapter(homeFragment.getContext(),theaters);
                rvListCinema.setLayoutManager(layoutManager);
                rvListCinema.setAdapter(listTheatersAdapter);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    private void downloadMovie(String type){
        Log.e(TAG, "downloadMovie: "+type );
        movieModel.movieWithTime(type, new MovieModel.MoviesCallbacks() {
            @Override
            public void onSuccess(ArrayList<Movie> moviesData) {
                Log.d(TAG, "onSuccess: "+moviesData);
                movies = moviesData;
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                ListMovieAdapter listMovieAdapter = new ListMovieAdapter(homeFragment.getContext(),movies);
                rvListmovie.setLayoutManager(layoutManager);
                rvListmovie.setAdapter(listMovieAdapter);
                listMovieAdapter.OnSetDetailListener(new ListMovieAdapter.OnUpdateListener() {
                    @Override
                    public void OnUpdate(Movie movie) {
                        showMovieFragment(new MovieDetailFragment(movie,user));
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    private void RandomMovie(){
        userModel.suggestMoviesByGenre(user.getUuid(), new UserModel.SuggestionsCallbacks() {
            @Override
            public void onSuccess(ArrayList<Movie> moviesData) {
                movies = moviesData;
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                ListMovieAdapter listMovieAdapter = new ListMovieAdapter(homeFragment.getContext(),movies);
                rvRandomMovie.setLayoutManager(layoutManager);
                rvRandomMovie.setAdapter(listMovieAdapter);
                listMovieAdapter.OnSetDetailListener(new ListMovieAdapter.OnUpdateListener() {
                    @Override
                    public void OnUpdate(Movie movie) {
                        showMovieFragment(new MovieDetailFragment(movie,user));
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    private void showMovieFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
