package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.home.ListTheatersAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.UserModel;
import com.google.android.material.button.MaterialButton;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ListMovieFollowAdapter extends RecyclerView.Adapter<ListMovieFollowAdapter.MyViewHolder> {
    private static final String TAG = "ListMovieFollowAdapter";
    private ArrayList<Movie> listMovieId;
    private Context context;
    private final UserModel userModel = new UserModel();
    private String userId;
    private User user;

    public ListMovieFollowAdapter(Context context, User user){
        this.user = user;
        this.context = context;
        this.listMovieId = new ArrayList<>();
    }
    public void setData(ArrayList<Movie> movies,String userId){
        this.listMovieId = movies;
        this.userId = userId;
        notifyDataSetChanged();
    }

    public ListMovieFollowAdapter(Context context,ArrayList<Movie> movieId, String userId){
        this.listMovieId = movieId;
        this.context = context;
        this.userId = userId;
    }
    @NonNull
    @Override
    public ListMovieFollowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_follow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+listMovieId);
        Movie movie = listMovieId.get(position);
        Glide.with(context)
                .load(Uri.parse(movie.getImage()))
                .into(holder.ig_poster);
        holder.title.setText(movie.getName());
        holder.dayStart.setText(movie.getStartingDate());
        holder.time.setText(String.valueOf(movie.getDuration()));
        holder.cancelFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userModel.removeMovieFromFavorite(userId, movie.getId(), new UserModel.UserCallbacks() {
                    @Override
                    public void onSuccess(User user2) {
                        listMovieId.remove(movie);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void onFailed(Exception e) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovieId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ig_poster;
        private TextView title,dayStart,time;
        private MaterialButton cancelFl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title_follw);
            ig_poster = itemView.findViewById(R.id.iv_poster_follow);
            dayStart = itemView.findViewById(R.id.tv_release_date_follow);
            time = itemView.findViewById(R.id.tv_time_follw);
            cancelFl = itemView.findViewById(R.id.btn_cancel);
        }
    }
}
