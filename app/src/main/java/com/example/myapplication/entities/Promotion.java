package com.example.myapplication.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Promotion {
    private String code;
    private Double percent;
    private LocalDate fromDate;
    private LocalDate toDate;

    public Promotion() {
    }

    public Promotion(String code, Double percent, LocalDate fromDate, LocalDate toDate) {
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

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public boolean isValid(){
        LocalDate now = LocalDate.now();
        return now.isAfter(fromDate) && now.isBefore(toDate);
    }

    public double getDiscount(double price){
        return price * percent;
    }

    public boolean isExpired(){
        LocalDate now = LocalDate.now();
        return now.isAfter(toDate);
    }

    public boolean isNotStarted(){
        LocalDate now = LocalDate.now();
        return now.isBefore(fromDate);
    }

    public boolean isNotValid(){
        return isExpired() || isNotStarted();
    }

    public Map<String, Object> toMap(){
        Map<String, Object> promotionMap = new HashMap<>();
        promotionMap.put("code", code);
        promotionMap.put("percent", percent);
        promotionMap.put("fromDate", fromDate);
        promotionMap.put("toDate", toDate);
        return promotionMap;
    }

    public Promotion(HashMap<String, Object> promotionMap){
        this(
            promotionMap.get("code").toString(),
            Double.parseDouble(promotionMap.get("percent").toString()),
            LocalDate.parse(promotionMap.get("fromDate").toString()),
            LocalDate.parse(promotionMap.get("toDate").toString())
        );
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
