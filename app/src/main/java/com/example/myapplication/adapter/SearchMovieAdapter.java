package com.example.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.Theater;
import com.example.myapplication.entities.User;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class  SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.MyViewHolder> {
    private static final String TAG = "ListMovieAdapter";
    private ArrayList<Movie> dataMovie = new ArrayList<>();
    private User user;
    private Theater theater;
    private final android.content.Context context;
    public void setData(ArrayList<Movie> movies) {
        this.dataMovie = movies;
        notifyDataSetChanged();
    }
    public interface OnClickListener{
        void OnClick(Movie movie);
    }
    private OnClickListener listener;

    public void OnSetClickListener(OnClickListener  listener) {
        this.listener = listener;
    }
    public SearchMovieAdapter(android.content.Context context) {
        this.context = context;
    }

    public SearchMovieAdapter(android.content.Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.dataMovie = movies;
    }

    @NonNull
    @Override
    public SearchMovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchMovieAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieAdapter.MyViewHolder holder, int position) {
        Movie movie = dataMovie.get(position);
        Glide.with(context)
                .load(Uri.parse(movie.getImage()))
                .into(holder.ig_poster);
        holder.title.setText(movie.getName());
        holder.dayStart.setText(movie.getStartingDate());
        holder.time.setText(String.valueOf(movie.getDuration()));
        holder.chooseTheaterAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataMovie.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ig_poster;
        private TextView title,dayStart,time;
        private MaterialButton chooseTheaterAgain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title_follw);
            ig_poster = itemView.findViewById(R.id.iv_poster_follow);
            dayStart = itemView.findViewById(R.id.tv_release_date_follow);
            time = itemView.findViewById(R.id.tv_time_follw);
            chooseTheaterAgain = itemView.findViewById(R.id.btn_xemchitiet);
        }
    }
}
