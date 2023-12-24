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
        search = view.findViewById(R.id.et_search);
        downlist();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e(TAG, "onTextChanged: "+charSequence.toString() );
                String searchText = charSequence.toString();
                movieModel.getAllMovie(new MovieModel.MoviesCallbacks() {
                    @Override
                    public void onSuccess(ArrayList<Movie> moviesData) {
                        try {
                            movies.clear();
                            for (Movie movie : moviesData) {
                                if (movieMatchesCriteria(movie, searchText)) {
                                    Log.e(TAG, "onSuccess: "+movie.getName()+searchText );
                                    movies.add(movie);
                                }
                            }
                            searchMovieAdapter.setData(movies);
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

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
    private boolean movieMatchesCriteria(Movie movie, String searchText) {
        Log.e(TAG, "movieMatchesCriteria: "+movie.getName() + searchText );
        return movie.getName().contains(searchText) || movie.getGenres().contains(searchText);
    }

    private void searchMovie(String text){
        Log.e(TAG, "searchMovie: "+text );
        search(text);
    }
    private void search(String searchText){

    }
}
