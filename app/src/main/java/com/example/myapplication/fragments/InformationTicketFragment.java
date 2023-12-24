package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;

import java.util.ArrayList;

public class InformationTicketFragment extends Fragment {
    private Movie movie;
    private WebView webView;
    private User user;
    private String time, date, theater,phuongthuc = "";
    private ArrayList<String> seats = new ArrayList<>();
    private TextView tv_title,tv_date,tv_time,tv_theater,tv_seats,tv_user_name;
    private long total = 0;

    public InformationTicketFragment(Movie movie, User user, String time, String date, String theater, ArrayList<String> seats){
        this.movie = movie;
        this.time = time;
        this.date = date;
        this.theater = theater;
        this.seats = seats;
        this.user = user;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);
        init(view);
        return view;
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void init(View view) {
        tv_title = view.findViewById(R.id.tv_title_tk);
        tv_date = view.findViewById(R.id.tv_date_tk);
        tv_time = view.findViewById(R.id.tv_time_tk);
        tv_theater = view.findViewById(R.id.tv_name_tk);
        tv_seats = view.findViewById(R.id.tv_seat_tk);
        tv_user_name = view.findViewById(R.id.tv_name_user_tk);
        tv_date.setText(date);
        tv_time.setText(time);
        tv_title.setText(movie.getName());
        tv_theater.setText(theater);
        tv_seats.setText(seats.toString());
        tv_user_name.setText(user.getFullname());
    }
}
