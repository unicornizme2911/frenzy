package com.example.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.entities.Invoice;
import com.example.myapplication.entities.Movie;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.InvoiceModel;

import java.util.ArrayList;

public class HistoryPaymentAdapter extends  RecyclerView.Adapter<HistoryPaymentAdapter.HistoryViewHolder> {
    private User user;
    private InvoiceModel invoiceModel = new InvoiceModel();
    private Context context;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<String> invoiceId;

    public HistoryPaymentAdapter(Context context, User user){
        this.context = context;
        this.user = user;
    }
    public void setData(ArrayList<Movie> movies,ArrayList<String> invoice){
        this.movies = movies;
        this.invoiceId = invoice;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryPaymentAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_history, parent, false);
        return new HistoryPaymentAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryPaymentAdapter.HistoryViewHolder holder, int position) {
        Movie movie = movies.get(position);
        String invoice = invoiceId.get(position);
        Glide.with(context)
                .load(Uri.parse(movie.getImage()))
                .into(holder.iv_poster);
        holder.tv_title.setText(movie.getName());
        holder.tv_invoiceId.setText(invoice);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title,tv_date,tv_time,tv_theater,tv_invoiceId;
        private ImageView iv_poster;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster = itemView.findViewById(R.id.iv_poster_history);
            tv_title = itemView.findViewById(R.id.tv_title_history);
            tv_invoiceId = itemView.findViewById(R.id.tv_idInvoice);
        }
    }
}
