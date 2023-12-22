package com.example.myapplication.adapter.home;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Movie;

import java.util.ArrayList;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.MyViewHolder> {
    private static final String TAG = "ListMovieAdapter";
    private ArrayList<Movie> dataMovie = new ArrayList<>();
    private final android.content.Context context;
    public interface OnUpdateListener{
        void OnUpdate(Movie movie);
    }
    private OnUpdateListener listener;

    public void OnSetDetailListener(OnUpdateListener  listener) {
        this.listener = listener;
    }

    public ListMovieAdapter(android.content.Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.dataMovie = movies;
    }

    @NonNull
    @Override
    public ListMovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_movie, parent, false);
        return new ListMovieAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMovieAdapter.MyViewHolder holder, int position) {
        Movie movie = dataMovie.get(position);
        Glide.with(context)
                .load(Uri.parse(movie.getImage()))
                .into(holder.ig_poster);
        holder.ig_poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnUpdate(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataMovie.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ig_poster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ig_poster =itemView.findViewById(R.id.ig_poster_list);

        }
    }
}
