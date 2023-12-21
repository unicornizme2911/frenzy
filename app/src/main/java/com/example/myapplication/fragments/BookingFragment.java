package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.entities.Movie;

public class BookingFragment extends Fragment {
    private static final String TAG = "BookingFragment";
    private Movie movie = new Movie();
    String time,date,theater;
    public BookingFragment(Movie movie, String time, String date, String theater){
        this.movie = movie;
        this.time = time;
        this.date = date;
        this.theater = theater;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticketing_person_bottom_sheet, container, false);

        return view;
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
