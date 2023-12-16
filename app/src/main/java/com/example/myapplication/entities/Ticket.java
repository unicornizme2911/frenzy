package com.example.myapplication.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Ticket implements Serializable {
    private String id;
    private String movieId;
    private String theaterId;
    private String userId;
    private String seat;
    private double ticketPrice;
    private String bookingDate;
    private String showTime;

    public Ticket() {
    }
    public Ticket(String id, String movieId, String theaterId, String userId, String seat, double ticketPrice, String bookingDate, String showTime) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.userId = userId;
        this.seat = seat;
        this.ticketPrice = ticketPrice;
        this.bookingDate = bookingDate;
        this.showTime = showTime;
    }
    public Ticket(String movieId, String theaterId, String userId, String seat, double ticketPrice, String bookingDate, String showTime) {
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.userId = userId;
        this.seat = seat;
        this.ticketPrice = ticketPrice;
        this.bookingDate = bookingDate;
        this.showTime = showTime;
    }
    public Ticket(HashMap<String, Object> ticketMap){
        this(
                ticketMap.get("id").toString(),
                ticketMap.get("movieId").toString(),
                ticketMap.get("theaterId").toString(),
                ticketMap.get("userId").toString(),
                ticketMap.get("seat").toString(),
                Double.parseDouble(ticketMap.get("ticketPrice").toString()),
                ticketMap.get("bookingDate").toString(),
                ticketMap.get("showTime").toString()
        );
    }
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("movieId", movieId);
        result.put("theaterId", theaterId);
        result.put("userId", userId);
        result.put("seat", seat);
        result.put("ticketPrice", ticketPrice);
        result.put("bookingDate", bookingDate);
        result.put("showTime", showTime);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(String theaterId) {
        this.theaterId = theaterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", movieId='" + movieId + '\'' +
                ", theaterId='" + theaterId + '\'' +
                ", seat='" + seat + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", bookingDate=" + bookingDate +
                ", showTime=" + showTime +
                '}';
    }
}
