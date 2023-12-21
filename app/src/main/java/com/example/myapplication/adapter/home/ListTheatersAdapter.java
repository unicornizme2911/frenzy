package com.example.myapplication.adapter.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Theater;

import java.util.ArrayList;

public class ListTheatersAdapter extends RecyclerView.Adapter<ListTheatersAdapter.MyViewHolder> {
    private final Context context;
    private ArrayList<Theater> theaters;
    private static final String TAG = "ListTheatersAdapter";
    public ListTheatersAdapter(Context context, ArrayList<Theater> theaters) {
        this.context = context;
        this.theaters = theaters;
    }



    private TextView nameTheater;
    @NonNull
    @Override
    public ListTheatersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTheatersAdapter.MyViewHolder holder, int position) {
        Theater theater = theaters.get(position);
        Log.d(TAG, "onBindViewHolder: "+theater);
        holder.theater.setText(theater.getName());
    }



    @Override
    public int getItemCount() {
        return theaters.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView theater;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            theater = itemView.findViewById(R.id.tv_cinema);
        }
    }
}
