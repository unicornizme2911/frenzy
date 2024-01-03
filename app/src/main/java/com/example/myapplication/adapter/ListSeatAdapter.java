package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.entities.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListSeatAdapter extends RecyclerView.Adapter<ListSeatAdapter.MyViewHolder>{
    private ArrayList<String> seatData = new ArrayList<>();
    private ArrayList<String> seatIsExits = new ArrayList<>();
    private static final String TAG = "ListSeatAdapter";


    private Context context;
//    private ArrayList<String> seatName = new ArrayList<>();
    public interface OnBookingListener{
        void OnBooking(String seatName);
    }
    private ListSeatAdapter.OnBookingListener listener;

    public void OnSetClickBookListener(ListSeatAdapter.OnBookingListener listener) {
        this.listener = listener;
    }
    public ListSeatAdapter(Context context, ArrayList<String> seat,ArrayList<String> seatIsExits){
        this.context = context;
        this.seatData = seat;
        this.seatIsExits = seatIsExits;
    }
    public int i =0;

    @NonNull
    @Override
    public ListSeatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new ListSeatAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String seat= seatData.get(position);
        String[] seatList = seat.split(":");
        holder.rbseat.setText(seatList[0]);
        if(seatList[1].equalsIgnoreCase("Couple")){
            holder.rbseat.setBackgroundResource(R.drawable.button_seat_couple);
        }
        if(seatList[1].equalsIgnoreCase("VIP")){
            holder.rbseat.setBackgroundResource(R.drawable.button_seat_vip);
        }
        Log.e(TAG, "onBindViewHolder: "+seatIsExits);
        for(String seatEqual:seatIsExits){
            if(seatEqual.equalsIgnoreCase(seatList[0])){
                Log.e(TAG, "onBindViewHolder Equal: "+seatEqual );
                holder.rbseat.setClickable(false);
                holder.rbseat.setBackgroundColor(Color.GRAY);
                holder.rbseat.setBackgroundResource(R.drawable.rounded_corner);
            }
        }
    }


    @Override
    public int getItemCount() {
        return seatData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final CheckBox rbseat;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rbseat = itemView.findViewById(R.id.rbt_seat);
            toggleChecked();
        }
        public void toggleChecked(){
            rbseat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    String seat= seatData.get(position);
                    Log.d(TAG, "onClick Seat: "+seat);
                    String[] seatList = seat.split(":");

                    // toggle the check state of the clicked item
                    rbseat.setSelected(!rbseat.isSelected());
                    Log.e(TAG, "onClick: button has clicked " + rbseat.isSelected());
                    listener.OnBooking(seat);
                    Log.d(TAG, "onClick:  -----------------------------------");
                }
            });
        }
    }
}
