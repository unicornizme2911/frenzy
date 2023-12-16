package com.example.myapplication.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Promotion {
    private String code;
    private Double percent;
    private String fromDate;
    private String toDate;

    public Promotion() {
    }

    public Promotion(String code, Double percent, String fromDate, String toDate) {
        this.code = code;
        this.percent = percent;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public boolean isValid(){
        return !isExpired() && !isNotStarted();
    }

    public double getDiscount(double price){
        return price * percent;
    }

    public boolean isExpired(){
        Date currentDate = new Date();
        Date toDate = new Date(this.toDate);
        return currentDate.after(toDate);
    }

    public boolean isNotStarted(){
        Date currentDate = new Date();
        Date fromDate = new Date(this.fromDate);
        return currentDate.before(fromDate);
    }

    public Promotion(HashMap<String, Object> promotionMap){
        this(
                promotionMap.get("code").toString(),
                Double.parseDouble(promotionMap.get("percent").toString()),
                promotionMap.get("fromDate").toString(),
                promotionMap.get("toDate").toString()
        );
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("percent", percent);
        result.put("fromDate", fromDate);
        result.put("toDate", toDate);
        return result;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "code='" + code + '\'' +
                ", percent=" + percent +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
