package com.example.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ListSeatAdapter;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.Ticket;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.InvoiceModel;
import com.example.myapplication.models.TicketModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
                    Gson gson = new Gson();
                    Type type = gson.fromJson(seat, Map.class).getClass();
                    Map<String, Object> map = gson.fromJson(seat, type);
                    seatIsExit.add(map.get("seatName").toString());
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
                Log.e(TAG, "OnBooking: "+seatName );
                seatPick.add(seatName);
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick Book: "+seatPick );
                changeFragment(new PaymentFragment(movie,user,time,date,theater,seatPick));
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
        for(int i =1;i<9;i++) {
            seat.add("A" + String.valueOf(i));
        }
        for(int i =1;i<9;i++) {
            seat.add("B" + String.valueOf(i));
        }
        for(int i =1;i<9;i++) {
            seat.add("C" + String.valueOf(i));
        }
        for(int i =1;i<9;i++) {
            seat.add("D" + String.valueOf(i));
        }
        for(int i =1;i<9;i++) {
            seat.add("E" + String.valueOf(i));
        }
    }
}
