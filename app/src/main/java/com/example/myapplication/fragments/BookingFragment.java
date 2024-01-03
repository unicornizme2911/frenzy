package com.example.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ListSeatAdapter;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.authentication.LoginActivity;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.Ticket;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.InvoiceModel;
import com.example.myapplication.models.TicketModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingFragment extends Fragment {
    private static final String TAG = "BookingFragment";
    private Movie movie = new Movie();
    private final BookingFragment bookingFragment = this;

    private TextView title,dateMovie,timeMovie;
    private String time,date,theater;
    private MaterialButton book;
    private User user;
    private ArrayList<String> seat = new ArrayList<>();
    private ArrayList<String> seatPick = new ArrayList<>();
    private TicketModel ticketModel = new TicketModel();
    private List<String> tickets = new ArrayList<>();
    private InvoiceModel invoiceModel = new InvoiceModel();
    private ArrayList<String> seatIsExit = new ArrayList<>();
    private ListSeatAdapter seatAdapter = new ListSeatAdapter(bookingFragment.getContext(),seat,seatIsExit);

    public BookingFragment(Movie movie, User user, String time, String date, String theater){
        this.user = user;
        this.movie = movie;
        this.time = time;
        this.date = date;
        this.theater = theater;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticketing_person_bottom_sheet, container, false);
        title = view.findViewById(R.id.tv_movie_title);
        dateMovie = view.findViewById(R.id.tv_date);
        timeMovie = view.findViewById(R.id.tv_runningTime);
        book = view.findViewById(R.id.btn_Datve);
        init(view);
        getSeat(view);
        ticketModel.getSeatIsBooked(theater, movie.getId(), date, time, new TicketModel.SeatCallbacks() {
            @Override
            public void onSuccess(ArrayList<String> seats) {
                for(String seat : seats){
                    try{
                        Gson gson = new Gson();
                        Type type = gson.fromJson(seat, Map.class).getClass();
                        Map<String, Object> map = gson.fromJson(seat, type);
                        seatIsExit.add(map.get("seatName").toString());
                        Log.d(TAG, "onSuccess: " + map.get("seatName").toString());
                    }catch (IllegalStateException | JsonSyntaxException exception){

                    }

                }
                Log.e(TAG, "onCreateView: "+seatIsExit);
                RecyclerView recyclerView = view.findViewById(R.id.rv_list_seat);
                recyclerView.setAdapter(seatAdapter);
                listener();
            }
            @Override
            public void onFailed(Exception e) {
                Log.d(TAG, "onFailed: " + e.getMessage());
            }
        });
        return view;
    }
    private void listener(){
        seatAdapter.OnSetClickBookListener(new ListSeatAdapter.OnBookingListener() {
            @Override
            public void OnBooking(String seatName) {
                ArrayList<String> result = seatPick;
                int i = 0;
                if(seatPick.isEmpty()){
                    seatPick.add(seatName);
                }else{
                    for(String seat:seatPick){
                        if(seatName.equalsIgnoreCase(seat)){
                            result.remove(seatName);
                            i=0;
                            break;
                        }else{
                            i=1;
                        }
                    }
                    seatPick = result;
                    if(i==1){
                        seatPick.add(seatName);
                    }
                    Log.d(TAG, "OnBooking: "+seatPick);
                }

            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> coupleSeat = new ArrayList<>();
                ArrayList<String> seats = new ArrayList<>();
                for(String seat: seatPick){
                    String[] seatList = seat.split(":");
                    seats.add(seatList[0]);
                }
                Log.e(TAG, "onClick Book: "+seatPick );
                for(String seat: seatPick){
                    String[] seatList = seat.split(":");
                    if(seatList[1].equalsIgnoreCase("Couple")){
                        coupleSeat.add(seatList[0]);
                    }

                }
                Collections.sort(coupleSeat);
                if(coupleSeat.size() %2 ==1){
                    Toast.makeText(getContext(),"Book an even number of tickets",Toast.LENGTH_LONG).show();
                }else{
                    int exist = 0;
                    for(int i=0;i<coupleSeat.size();i+=2){
                        int seat1 = Integer.parseInt(coupleSeat.get(i).substring(1));
                        int seat2 = Integer.parseInt(coupleSeat.get(i+1).substring(1));

                        if(seat1 % 2 == 1 && seat2 % 2 == 0 && seat2 - seat1 == 1 ){
                            continue;
                        }else{
                            Toast.makeText(getContext(),"Please book couple tickets next to each other",Toast.LENGTH_LONG).show();
                            exist = 1;
                            break;
                        }
                    }
                    if(exist == 0){
                        changeFragment(new PaymentFragment(movie,user,time,date,theater,seats));
                    }
                }
            }
        });

    }
    private void init(View view){
        title.setText(movie.getName());
        dateMovie.setText(time);
        timeMovie.setText(date);
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void getSeat(View view){
        String cp = "Couple";
        String sg = "Single";
        String vip= "VIP";
        for(int i =1;i<9;i++) {
            seat.add("A" + String.valueOf(i)+":"+sg);
        }
        for(int i =1;i<9;i++) {
            if( i >2 && i<7 ){
                seat.add("B" + String.valueOf(i)+":"+vip);
            }else{
                seat.add("B" + String.valueOf(i)+":"+sg);
            }
        }
        for(int i =1;i<9;i++) {
            if( i >2 && i<7 ){
                seat.add("C" + String.valueOf(i)+":"+vip);
            }else{
                seat.add("C" + String.valueOf(i)+":"+sg);
            }        }
        for(int i =1;i<9;i++) {
            if( i >2 && i<7 ){
                seat.add("D" + String.valueOf(i)+":"+vip);
            }else{
                seat.add("D" + String.valueOf(i)+":"+sg);
            }        }
        for(int i =1;i<9;i++) {
            seat.add("E" + String.valueOf(i) + ":"+cp);
        }
    }
}
