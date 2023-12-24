package com.example.myapplication.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.entities.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class TicketModel extends Model{
    public static final String TAG = "TicketModel";
    public static final String TICKET_COLLECTION = "tickets";
    public interface TicketCallbacks{
        void onSuccess(Ticket ticket);
        void onFailed(Exception e);
    }
    public interface TicketsCallbacks{
        void onSuccess(ArrayList<Ticket> tickets);
        void onFailed(Exception e);
    }
    public interface SeatCallbacks{
        void onSuccess(ArrayList<String> seats);
        void onFailed(Exception e);
    }
    public TicketModel() {
        super();
    }
    public TicketModel(DatabaseReference database){
        super(database);
    }

    public void createTicket(String userId, String movieId, String theaterId, String seatNumber, String date, String time, TicketCallbacks callback){
        String id = UUID.randomUUID().toString().toUpperCase().substring(0,8);
        final double[] ticketPrice = {0};
        database.child("movies").child(movieId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    callback.onFailed(new Exception("Movie not found"));
                    return;
                }
                JSONObject jsonObject = new JSONObject((Map) snapshot.getValue());
                ticketPrice[0] = jsonObject.optDouble("price");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailed(new Exception(error.getMessage()));
            }
        });
        int row = seatNumber.substring(0,1).charAt(0) - 'A';
        int col = Integer.parseInt(seatNumber.substring(1));
        Seat seat = new Seat(seatNumber, row, col);
        if(seat.isVip(row, col)){
            ticketPrice[0] += 45000;
        }
        Ticket ticket = new Ticket(id, movieId, theaterId, userId, seatNumber, ticketPrice[0], date, time);
        DatabaseReference query = database.child(TICKET_COLLECTION).child(id);
        query.setValue(ticket).addOnSuccessListener(aVoid -> {
            callback.onSuccess(ticket);
        }).addOnFailureListener(e -> {
            callback.onFailed(e);
        });
        query.child("seat").setValue(seat);
    }
    public void getSeatIsBooked(String theaterId, String movieId, String date, String time, SeatCallbacks callback){
        ArrayList<String> seatIsBooked = new ArrayList<>();
        database.child(TICKET_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    JSONObject jsonObject = new JSONObject((Map) dataSnapshot.getValue());
                    String theater = jsonObject.optString("theaterId");
                    String movie = jsonObject.optString("movieId");
                    String bookingDate = jsonObject.optString("bookingDate");
                    String showTime = jsonObject.optString("showTime");
                    String seat = jsonObject.optString("seat");
                    if(theater.equals(theaterId) && movie.equals(movieId) && bookingDate.equals(date) && showTime.equals(time)){
                        seatIsBooked.add(seat);
                    }
                }
                callback.onSuccess(seatIsBooked);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: "+ error.getMessage());
                callback.onFailed(new Exception(error.getMessage()));
            }
        });
    }

    public class Seat{
        public String seatName;
        public int row;
        public int col;

        public Seat(String seatName, int row, int col){
            this.seatName = seatName;
            this.row = row;
            this.col = col;
        }
        public boolean isVip(int row, int col){
            int[] vipRow = {2,3,4};
            int[] vipCol = {3,4,5,6};
            return Arrays.stream(vipRow).anyMatch(r -> r == row) && Arrays.stream(vipCol).anyMatch(c -> c == col);
        }
    }
}
