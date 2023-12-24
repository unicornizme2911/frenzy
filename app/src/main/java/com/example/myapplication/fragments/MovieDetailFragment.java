package com.example.myapplication.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.MovieModel;
import com.example.myapplication.models.UserModel;
import com.google.android.material.button.MaterialButton;

public class MovieDetailFragment extends Fragment {
    private static final String TAG = "MovieDetailFragment";
    private TextView dayMovie,timeMovie,summary,nameType,rate,nameActor,trailer,nameMovie;
    private ImageView poster,poster2,back;
    private ListMovieAdapter listMovieAdapter;
    private final UserModel userModel = new UserModel();
    private final MovieModel movieModel = new MovieModel();
    private MaterialButton seePremiere,follow;
    private User user;
    private Movie movie;
    public MovieDetailFragment(Movie movie, User user){
        this.user = user;
        this.movie = movie;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail_info, container, false);
        timeMovie = view.findViewById(R.id.tv_timeMovie);
        dayMovie = view.findViewById(R.id.tv_dayMovie);
        rate = view.findViewById(R.id.tv_rate);
        poster2 = view.findViewById(R.id.Wv_trailer);
        poster = view.findViewById(R.id.iv_poster);
        summary = view.findViewById(R.id.tv_summary);
        nameType = view.findViewById(R.id.tv_name_type);
        nameActor = view.findViewById(R.id.tv_name_actor);
        seePremiere = view.findViewById(R.id.btn_xemxuatchiet);
        trailer = view.findViewById(R.id.tv_link);
        back = view.findViewById(R.id.iv_back);
        nameMovie = view.findViewById(R.id.tv_nameMovie);
        follow = view.findViewById(R.id.btn_theodoi);
        movieModel.getMovie(movie.getId(), new MovieModel.MovieCallbacks() {
            @Override
            public void onSuccess(Movie movie2) {
                Glide.with(getContext())
                        .load(Uri.parse(movie.getImage()))
                        .into(poster);
                Glide.with(getContext())
                        .load(Uri.parse(movie.getImage()))
                        .into(poster2);
                dayMovie.setText(movie.getStartingDate());
                timeMovie.setText(String.valueOf(movie.getDuration()));
                summary.setText(movie.getSumary());
                nameType.setText(movie.getGenresString());
                nameActor.setText(movie.getActorsString());
                rate.setText(String.valueOf(movie.getRating()));
                nameMovie.setText(movie.getName());
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeFragment(new HomeFragment(user));

                    }
                });


                trailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeFragment(new TrailerFragment(movie,user));
                    }
                });
                seePremiere.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeFragment(new PremiereFragment(movie,user));
                    }
                });
                if(user.getMovieIds() == null){
                }else{
                    for(String movieFollow:user.getMovieIds()){
                        if(movie.getId().equalsIgnoreCase(movieFollow)){
                            follow.setText("Đã theo dỗi");
                            follow.setClickable(false);
                            break;
                        }else{
                            follow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    follow.setText("Đã theo dỗi");
                                    userModel.addMovieToFavorite(user.getUuid(), movie.getId(), new UserModel.UserCallbacks() {
                                        @Override
                                        public void onSuccess(User userloi) {
                                            Log.e(TAG, "onSuccess: "+movie );
                                            user.getMovieIds().add(movie.getId());
                                        }

                                        @Override
                                        public void onFailed(Exception e) {

                                        }
                                    });
                                }
                            });
                        }
                    }
                }


            }

            @Override
            public void onFailed(Exception e) {

            }
        });
        return view;
    }
    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .commit();


    }


}
