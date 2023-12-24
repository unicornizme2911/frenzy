package com.example.myapplication.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoice {
    private String id;
    private String userId;
    private List<String> ticketId;
    private String promotionId;
    private String paymentMethod;
    private String paymentDate;
    private double totalPrice;
    private double taxes;
    public Invoice() {
        super();
    }

    public Invoice(String id, String userId, List<String> ticketId, String promotionId, String paymentMethod, String paymentDate, double totalPrice, double taxes) {
        this.id = id;
        this.userId = userId;
        this.ticketId = ticketId;
        this.promotionId = promotionId;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.totalPrice = totalPrice;
        this.taxes = taxes;
    }

    public Invoice(String userId, List<String> ticketId, String promotionId, String paymentMethod, String paymentDate, double totalPrice, double taxes) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.promotionId = promotionId;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.totalPrice = totalPrice;
        this.taxes = taxes;
    }

    public Invoice(HashMap<String, Object> invoiceMap){
        this(
                invoiceMap.get("id").toString(),
                invoiceMap.get("userId").toString(),
                (List<String>) invoiceMap.get("ticketId"),
                invoiceMap.get("promotionId").toString(),
                invoiceMap.get("paymentMethod").toString(),
                invoiceMap.get("paymentDate").toString(),
                Double.parseDouble(invoiceMap.get("totalPrice").toString()),
                Double.parseDouble(invoiceMap.get("taxes").toString())
        );
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("userId", userId);
        result.put("ticketId", ticketId);
        result.put("promotionId", promotionId);
        result.put("paymentMethod", paymentMethod);
        result.put("paymentDate", paymentDate);
        result.put("totalPrice", totalPrice);
        result.put("taxes", taxes);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getTicketId() {
        return ticketId;
    }

    public void setTicketId(List<String> ticketId) {
        this.ticketId = ticketId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }
    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", ticketId=" + ticketId +
                ", promotionId='" + promotionId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", totalPrice=" + totalPrice +
                ", taxes=" + taxes +
                '}';
    }
}
