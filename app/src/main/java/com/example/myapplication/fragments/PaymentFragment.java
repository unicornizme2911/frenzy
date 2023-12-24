package com.example.myapplication.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.authentication.RegisterActivity;
import com.example.myapplication.entities.Invoice;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.Ticket;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.InvoiceModel;
import com.example.myapplication.models.TicketModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.collection.LLRBNode;

import java.io.FileReader;
import java.util.ArrayList;

public class PaymentFragment extends Fragment {
    private static final String TAG = "PaymentFragment";

    private final  PaymentFragment paymentFragment = this;
    private Movie movie;
    private String time, date, theater,phuongthuc = "";
    private User user;
    private TicketModel ticketModel = new TicketModel();
    private InvoiceModel invoiceModel = new InvoiceModel();
    private ArrayList<String> seats = new ArrayList<>();
    private ArrayList<String> ticketsID = new ArrayList<>();
    public PaymentFragment(Movie movie, User user, String time, String date, String theater, ArrayList<String> seats){
        this.movie = movie;
        this.time = time;
        this.date = date;
        this.theater = theater;
        this.seats = seats;
        this.user = user;
    }
    private ImageView iv_poster,back;
    private TextView tv_title,tv_date,tv_time,tv_theater,tv_seats,tv_total,tv_quantity,tv_total2;
    private MaterialButton thanhtoan;
    private long tongtien =0;
    private LinearLayout momo,zalo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        init(view);
        Log.e(TAG, "onCreateView: "+user );
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new DetailUserFragment(user));
            }
        });
        momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zalo.setBackgroundColor(Color.BLACK);
                momo.setBackgroundColor(Color.RED);
                phuongthuc = "Momo";
            }
        });
        zalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phuongthuc = "ZaloPay";
                momo.setBackgroundColor(Color.BLACK);
                zalo.setBackgroundColor(Color.RED);
            }
        });
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phuongthuc.equalsIgnoreCase("")){
                    Toast.makeText(view.getContext(),"Select a payment method",Toast.LENGTH_LONG).show();
                }else{
                    createTickets();
                    createInvoic();
                }
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
    private  void createInvoic(){
        invoiceModel.createInvoice(user.getUuid(), ticketsID, "0", phuongthuc, "Done", new InvoiceModel.InvoiceCallbacks() {
            @Override
            public void onSuccess(Invoice invoice) {
                Log.d(TAG, "onSuccess Creaate Invoice: "+invoice);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    private void createTickets(){
        for (String aSeat:seats) {
            ticketModel.createTicket(user.getUuid(), movie.getId(), theater, aSeat, date, time, new TicketModel.TicketCallbacks() {
                @Override
                public void onSuccess(Ticket ticket) {
                    ticketsID.add(ticket.getId());
                    Log.d(TAG, "onSuccess CreateTicket: "+ticket);
                }

                @Override
                public void onFailed(Exception e) {

                }
            });

        }
    }
    private void init(View view){
        momo = view.findViewById(R.id.LN_Momo);
        zalo = view.findViewById(R.id.LN_Zalo);
        back = view.findViewById(R.id.iv_back_thanhtoan);
        iv_poster = view.findViewById(R.id.iv_poster_follow);
        tv_title = view.findViewById(R.id.tv_title);
        tv_date = view.findViewById(R.id.tv_date_watch);
        tv_time = view.findViewById(R.id.tv_time_watch);
        tv_theater = view.findViewById(R.id.tv_name_cinema);
        tv_seats = view.findViewById(R.id.tv_seat);
        tv_total = view.findViewById(R.id.tv_total_money);
        tv_total2 = view.findViewById(R.id.tv_total_money2);
        tv_quantity = view.findViewById(R.id.tv_number_seat);
        thanhtoan = view.findViewById(R.id.btn_thanhtoan);
        Glide.with(paymentFragment.getContext())
                .load(Uri.parse(movie.getImage()))
                .into(iv_poster);
        tv_title.setText(movie.getName());
        tv_date.setText(date);
        tv_time.setText(time);
        tv_theater.setText(theater);
        tv_seats.setText(seats.toString());
        tv_quantity.setText(String.valueOf(seats.size()));
        for(String seat:seats){
            checkSeat(seat);
        }
        tv_total.setText(String.valueOf(tongtien));
        tv_total2.setText(String.valueOf(tongtien));
    }
    private void checkSeat(String aSeat){
        long moneyforaSeat = 0;
        String col = aSeat.substring(0,1);
        String cow = aSeat.substring(1,2);
        if(col.equalsIgnoreCase("C") || col.equalsIgnoreCase("D") || col.equalsIgnoreCase("B")){
            if(cow.equalsIgnoreCase("4") || cow.equalsIgnoreCase("5") || cow.equalsIgnoreCase("6") || cow.equalsIgnoreCase("3")){
                moneyforaSeat = 90000;
            }else{
                moneyforaSeat = 45000;
            }
        }else {
            moneyforaSeat = 45000;
        }
        tongtien += moneyforaSeat;
    }
}
