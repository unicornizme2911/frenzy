package com.example.myapplication.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.home.ListMovieAdapter;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.models.MovieModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PremiereFragment extends Fragment {
    private static final String TAG = "PremiereFragment";
    private RecyclerView listPremiere;
    private PremiereFragment premiereFragment;
    private MovieModel movieModel;
    private Movie movie;
    private TextView dateMovie,nameMovie;
    private ImageButton date;
    private String userId;
    private MaterialButton choghe;
    private int dayStart,monthStart,yearStart,dayEnd,monthEnd,yearEnd;
    public PremiereFragment(Movie movie, String userId){
        this.userId = userId;
        this.movie=movie;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_see_premiere, container, false);
        view.findViewById(R.id.iv_back).setOnClickListener(view1 -> changeFragment(new MovieDetailFragment(movie,userId)));
        nameMovie = view.findViewById(R.id.tv_toolbar_title);
        nameMovie.setText(movie.getName());
        date = view.findViewById(R.id.ib_calendar);
        dateMovie = view.findViewById(R.id.tv_showdate);
        choghe = view.findViewById(R.id.btn_chonghe);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                Log.d(TAG, "onClick: "+"Hello");
                showDatePickerDialog(view);
            }
        });
        return view;
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    public interface OnTheaterSelectedListener {
        void onTheaterSelected(String theater);
    }
    public interface OnTimeSelectedListener {
        void onTimeSelected(String Time);
    }
//    private ListMovieAdapter.OnUpdateListener listener;
//
//    public void OnSetDetailListener(ListMovieAdapter.OnUpdateListener listener) {
//        this.listener = listener;
//    }
    private void getTheater(View view, OnTheaterSelectedListener listener,int check) {
        Log.d(TAG, "getTheater: "+check);
        if(check == 1){

            ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.wrong, android.R.layout.simple_spinner_item);
            staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if(!staticAdapter.equals(null)){
                Spinner spnTheater = new Spinner(getContext());
                spnTheater= view.findViewById(R.id.spinnerTheater);
                spnTheater.setAdapter(staticAdapter);
                spnTheater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String theaterSelected = parent.getItemAtPosition(position).toString();
                        listener.onTheaterSelected(theaterSelected);

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        }else{
            ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.theater_array, android.R.layout.simple_spinner_item);
            staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if(!staticAdapter.equals(null)){
                Spinner spnTheater = new Spinner(getContext());
                spnTheater= view.findViewById(R.id.spinnerTheater);
                spnTheater.setAdapter(staticAdapter);
                spnTheater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String theaterSelected = parent.getItemAtPosition(position).toString();
                        listener.onTheaterSelected(theaterSelected);

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        }
    }
    private void getTimeOfMovie(View view, OnTimeSelectedListener listener, Movie movie,int check) {
        Log.d(TAG, "getTimeOfMovie: "+check);
        if(check == 1){
            List<String> time = movie.getShowTimes();
            List<String> timeofmovie = new ArrayList<>();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, timeofmovie);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spnTimeMovie = view.findViewById(R.id.spinnerTimeOfMovie);
            spnTimeMovie.setAdapter(adapter);
            spnTimeMovie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String timeSelected = parent.getItemAtPosition(position).toString();
                    listener.onTimeSelected(timeSelected);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }else{
            List<String> time = movie.getShowTimes();
            List<String> timeofmovie = new ArrayList<>();
            for ( String aTime:time) {
                timeofmovie.add(aTime);

            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, timeofmovie);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spnTimeMovie = view.findViewById(R.id.spinnerTimeOfMovie);
            spnTimeMovie.setAdapter(adapter);
            spnTimeMovie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String timeSelected = parent.getItemAtPosition(position).toString();
                    listener.onTimeSelected(timeSelected);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

    }
    private void showDatePickerDialog(View view) {
        // Lấy ngày và thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        // Tạo đối tượng DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1;
                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.YEAR, year);
                        dateMovie.setText(String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year));
                        setStart();
                        setEnd();
                        int checkdate = 1;
                        String[] theaterSelected = {""};
                        getTheater(view, theater -> {
                            theaterSelected[0] = theater;
                        },checkdate);
                        Log.e(TAG, "onCreateView: " + theaterSelected[0]);

                        String[] timeSelected = {""};
                        getTimeOfMovie(view, time -> {
                            timeSelected[0] = time;
                            Log.e(TAG, "onCreateView: " + theaterSelected[0]);
                        },movie,checkdate);


                        Log.d(TAG, "onDateSet: "+checkdate);
                        if(year >= yearStart){
                            Log.d(TAG, "onDateSet: year start");
                            if(month >= monthStart){
                                Log.d(TAG, "onDateSet: month start");
                                if(day >= dayStart){
                                    Log.d(TAG, "onDateSet: day start");
                                    if(year <= yearEnd){
                                        Log.d(TAG, "onDateSet: year end");
                                        if(month <= monthEnd){
                                            Log.d(TAG, "onDateSet: month end");
                                            if(day <= dayEnd){
                                                choghe.setVisibility(View.VISIBLE);
                                                Log.d(TAG, "onDateSet: day end");
                                                checkdate=0;
                                                getTheater(view, theater -> {
                                                    theaterSelected[0] = theater;
                                                    Log.e(TAG, "onCreateView: " + theaterSelected[0]);

                                                },checkdate);

                                                getTimeOfMovie(view, time -> {
                                                    timeSelected[0] = time;
                                                    Log.e(TAG, "onCreateView: " + timeSelected[0]);
                                                },movie,checkdate);
                                                choghe.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        changeFragment(new BookingFragment(movie,timeSelected[0],dateMovie.getText().toString(),theaterSelected[0]));
                                                    }
                                                });
                                            }else{
                                                Snackbar.make(view, "Ngày này phim không chiếu", Snackbar.LENGTH_LONG).show();
                                            }
                                        }else{
                                            Snackbar.make(view, "Ngày này phim không chiếu", Snackbar.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Snackbar.make(view, "Ngày này phim không chiếu", Snackbar.LENGTH_LONG).show();
                                    }
                                }else{
                                    Snackbar.make(view, "Ngày này phim không chiếu", Snackbar.LENGTH_LONG).show();
                                }
                            }else{
                                Snackbar.make(view, "Ngày này phim không chiếu", Snackbar.LENGTH_LONG).show();
                            }
                        }else{
                            Snackbar.make(view, "Ngày này phim không chiếu", Snackbar.LENGTH_LONG).show();
                        }
                    }
                },
                dayOfMonth, month, year);
        // Hiển thị DatePickerDialog
        datePickerDialog.show();

    }
    public void setStart(){
        String dateStart = movie.getStartingDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Chuyển đổi chuỗi thành đối tượng Date
        Date date = null;
        try {
            date = dateFormat.parse(dateStart);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Định dạng để lấy ngày, tháng, năm từ đối tượng Date
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        dayStart = Integer.valueOf(dayFormat.format(date));
        monthStart = Integer.valueOf(monthFormat.format(date));
        yearStart = Integer.valueOf(yearFormat.format(date));
    }
    public void setEnd(){
        String dateStart = movie.getEndingDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Chuyển đổi chuỗi thành đối tượng Date
        Date date = null;
        try {
            date = dateFormat.parse(dateStart);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Định dạng để lấy ngày, tháng, năm từ đối tượng Date
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        dayEnd = Integer.valueOf(dayFormat.format(date));
        monthEnd = Integer.valueOf(monthFormat.format(date));
        yearEnd = Integer.valueOf(yearFormat.format(date));
    }
}
