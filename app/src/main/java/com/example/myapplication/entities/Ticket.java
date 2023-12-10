package com.example.myapplication.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Ticket implements Serializable {
    private String movieName;
    private String theaterName;
    private String seat;
    private double ticketPrice;
    private LocalDate bookingDate;
    private LocalDate showTime;

    public Ticket() {
    }

    public Ticket(String movieName, String theaterName, String seat, double ticketPrice, LocalDate bookingDate, LocalDate showTime) {
        this.movieName = movieName;
        this.theaterName = theaterName;
        this.seat = seat;
        this.ticketPrice = ticketPrice;
        this.bookingDate = bookingDate;
        this.showTime = showTime;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
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

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDate showTime) {
        this.showTime = showTime;
    }
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("movieName", movieName);
        result.put("theaterName", theaterName);
        result.put("seat", seat);
        result.put("ticketPrice", ticketPrice);
        result.put("bookingDate", bookingDate);
        result.put("showTime", showTime);
        return result;
    }
    public Ticket(HashMap<String, Object> ticketMap){
        this(
                ticketMap.get("movieName").toString(),
                ticketMap.get("theaterName").toString(),
                ticketMap.get("seat").toString(),
                Double.parseDouble(ticketMap.get("ticketPrice").toString()),
                LocalDate.parse(ticketMap.get("bookingDate").toString()),
                LocalDate.parse(ticketMap.get("showTime").toString())
        );
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "movieName='" + movieName + '\'' +
                ", theaterName='" + theaterName + '\'' +
                ", seat='" + seat + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", bookingDate=" + bookingDate +
                ", showTime=" + showTime +
                '}';
    }
}
