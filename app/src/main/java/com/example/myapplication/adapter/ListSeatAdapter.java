package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class ListSeatAdapter extends RecyclerView.Adapter<ListSeatAdapter.MyViewHolder>{
    private ArrayList<String> seatData = new ArrayList<>();
    private Context context;
//    private ArrayList<String> seatName = new ArrayList<>();
    public interface OnBookingListener{
        void OnBooking(String seatName);
    }
    private ListSeatAdapter.OnBookingListener listener;

    public void OnSetClickBookListener(ListSeatAdapter.OnBookingListener listener) {
        this.listener = listener;
    }
    public ListSeatAdapter(Context context, ArrayList<String> seat){
        this.context = context;
        this.seatData = seat;
    }
    @NonNull
    @Override
    public ListSeatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new ListSeatAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String seat= seatData.get(position);
        holder.rbseat.setText(seat);
        holder.rbseat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnBooking(seat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seatData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private RadioButton rbseat;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rbseat = itemView.findViewById(R.id.rbt_seat);
        }
    }
}
