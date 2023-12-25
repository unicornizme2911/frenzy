package com.example.myapplication.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SearchMovieAdapter;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.MovieModel;

import java.util.ArrayList;

public class SearchMovieFragment extends Fragment {
    private MovieModel movieModel = new MovieModel();
    private User user;
    private ArrayList<Movie> movies = new ArrayList<>();
    private static final String TAG = "SearchMovieFragment";
    private final SearchMovieFragment searchMovieFragment = this;
    private RecyclerView listMovie;
    private SearchMovieAdapter searchMovieAdapter;
    public SearchMovieFragment(User user){
        this.user = user;
    }
    private EditText search;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        listMovie = view.findViewById(R.id.rv_list_movie_search);
        view.findViewById(R.id.iv_back).setOnClickListener(view1 -> changeFragment(new HomeFragment(user)));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listMovie.setAdapter(searchMovieAdapter);
        listMovie.setLayoutManager(layoutManager);
        searchMovieAdapter = new SearchMovieAdapter(getContext());
        listMovie.setAdapter(searchMovieAdapter);
        search = view.findViewById(R.id.et_search);
        downlist();
        search();
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
            private ArrayList<Movie> movies2 = new ArrayList<>();

            @Override
            public void onSuccess(ArrayList<Movie> movies) {

                try{
                    for(Movie movie:movies){
                        if(!movie.getName().equals("") || !movie.getGenres().equals("")){
                            movies2.add(movie);
                        }
                    }
                    movies2.clear();
                    for(Movie student:movies2){
                        movies.add(student);
                    }
                    searchMovieAdapter.setData(movies);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    private boolean movieMatchesCriteria(Movie movie, String searchText) {
        Log.e(TAG, "movieMatchesCriteria: "+movie.getName() + searchText );
        return movie.getName().contains(searchText) || movie.getGenres().contains(searchText);
    }

    private void searchMovie(String text){
        Log.e(TAG, "searchMovie: "+text );
        getSearch(text);
    }
    private void  getSearch(String ext){
        movieModel.getAllMovie(new MovieModel.MoviesCallbacks() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                try {
                    ArrayList<Movie> movies1 = new ArrayList<>();

                    Log.e(TAG, "onSuccess: "+movies );
                    for (Movie movie : movies) {
                        if (movieMatchesCriteria(movie, ext)) {
                            movies1.add(movie);
                        }
                    }
                    searchMovieAdapter.setData(movies1);
                    searchMovieAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    private void search(){
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString();
                searchMovie(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
