package com.example.myapplication.adapter.home;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Movie;
import com.google.firebase.database.core.Context;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
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
