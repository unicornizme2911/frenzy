package com.example.myapplication.models;

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
                ticketPrice[0] = jsonObject.optDouble("ticketPrice");
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
        Ticket ticket = new Ticket(id, userId, movieId, theaterId, seatNumber, ticketPrice[0], date, time);
        DatabaseReference query = database.child(TICKET_COLLECTION).child(id);
        query.setValue(ticket).addOnSuccessListener(aVoid -> {
            callback.onSuccess(ticket);
        }).addOnFailureListener(e -> {
            callback.onFailed(e);
        });
        query.child("seat").setValue(seat);
    }
    public ArrayList<String> getSeatIsBooked(String theaterId, String movieId, String date, String time){
        ArrayList<String> seatIsBooked = new ArrayList<>();
        database.child(TICKET_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Ticket ticket = dataSnapshot.getValue(Ticket.class);
                    if(ticket.getTheaterId().equals(theaterId) && ticket.getMovieId().equals(movieId) && ticket.getBookingDate().equals(date) && ticket.getShowTime().equals(time)){
                        seatIsBooked.add(ticket.getSeat());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return seatIsBooked;
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
            int[] vipRow = {4,5,6,7};
            int[] vipCol = {3,4,5,6,7,8,9};
            return Arrays.stream(vipRow).anyMatch(r -> r == row) && Arrays.stream(vipCol).anyMatch(c -> c == col);
        }
    }
}
