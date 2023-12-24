package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Invoice;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.InvoiceModel;

import java.util.ArrayList;

public class HistoryPaymentAdapter extends  RecyclerView.Adapter<HistoryPaymentAdapter.HistoryViewHolder> {
    private User user;
    private InvoiceModel invoiceModel = new InvoiceModel();
    private HistoryPaymentAdapter(User user){
        this.user = user;
    }
    @NonNull
    @Override
    public HistoryPaymentAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_payment_history, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryPaymentAdapter.HistoryViewHolder holder, int position) {
        String invoiceId = user.getInvoiceIds().get(position);
        invoiceModel.getAllInvoice(user.getUuid(), new InvoiceModel.InvoicesCallbacks() {
            @Override
            public void onSuccess(ArrayList<Invoice> invoices) {
                for(Invoice invoice: invoices){

                }
            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return user.getInvoiceIds().size();
    }
    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title,tv_date,tv_time,tv_theater,tv_seats,tv_total;
        private ImageView iv_poster;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster = itemView.findViewById(R.id.iv_poster_follow);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date_watch);
            tv_time = itemView.findViewById(R.id.tv_time_watch);
            tv_theater = itemView.findViewById(R.id.tv_name_cinema);
            tv_seats = itemView.findViewById(R.id.tv_seat);
            tv_total = itemView.findViewById(R.id.tv_total_money);
        }
    }
}
