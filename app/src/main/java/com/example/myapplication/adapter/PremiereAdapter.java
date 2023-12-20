package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.entities.Movie;

import java.util.ArrayList;

public class PremiereAdapter extends RecyclerView.Adapter<PremiereAdapter.PremiereViewHolder> {
    private static final String TAG = "PremiereAdapter";
    private ArrayList<Movie> dataMovie = new ArrayList<>();
    private Movie Movie = new Movie();
    private final android.content.Context contex;

    public PremiereAdapter(Context contex, ArrayList<Movie> movies) {
        this.contex = contex;
        this.dataMovie = movies;
    }

    @NonNull
    @Override
    public PremiereAdapter.PremiereViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PremiereViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataMovie.size();
    }
    public static class PremiereViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTime, tvCinema;
        public PremiereViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime =itemView.findViewById(R.id.tv_premiereMovie);
            tvCinema = itemView.findViewById(R.id.tv_cinema);
        }
    }
}
