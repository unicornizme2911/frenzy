package com.example.myapplication.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.entities.Invoice;
import com.example.myapplication.entities.Promotion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class InvoiceModel extends Model{
    public static final String TAG = "InvoiceModel";
    public static final String INVOICE_COLLECTION = "invoices";
    public static final String TICKET_COLLECTION = "tickets";
    public static final String PROMOTION_COLLECTION = "promotions";
    public static final String USER_COLLECTION = "users";
    public interface InvoiceCallbacks{
        void onSuccess(Invoice invoice);
        void onFailed(Exception e);
    }
    public interface InvoicesCallbacks{
        void onSuccess(ArrayList<Invoice> invoices);
        void onFailed(Exception e);
    }
    public InvoiceModel() {
        super();
    }
    public InvoiceModel(DatabaseReference database){
        super(database);
    }

    public void createInvoice(String userId, List<String> ticketId, String promotionId, String paymentMethod, String paymentStatus, InvoiceCallbacks callback){
        String id = UUID.randomUUID().toString().toUpperCase().substring(0,8);
        final double[] total = {0};
        final int[] ticketCount = {ticketId.size()};
        AtomicInteger processedTickets = new AtomicInteger(0);
        for(String idTicket : ticketId){
            database.child(TICKET_COLLECTION).child(idTicket).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        double ticketPrice = Double.parseDouble(snapshot.child("ticketPrice").getValue().toString());
                        total[0] += ticketPrice;

                        int count = processedTickets.incrementAndGet();
                        if (count == ticketCount[0]) {
                            Log.e(TAG, "ticket total: " + total[0]);
                            database.child(PROMOTION_COLLECTION).child(promotionId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.getValue() != null){
                                        double percent = Double.parseDouble(snapshot.child("percent").getValue().toString());
                                        if(percent != 0){
                                            total[0] = total[0] * (1 - percent/100);
                                            Log.e(TAG, "promotion: " + total[0]);
                                        }
                                    }
                                    double taxes = 0.08;
                                    total[0] += total[0] * taxes;
                                    Invoice invoice = new Invoice(id, userId, ticketId, promotionId, paymentMethod, paymentStatus, total[0], taxes);
                                    database.child(INVOICE_COLLECTION).child(id).setValue(invoice.toMap()).addOnSuccessListener(aVoid -> {
                                        database.child(USER_COLLECTION).child(userId).child("invoiceIds").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                ArrayList<String> invoiceIds = (ArrayList<String>) snapshot.getValue();
                                                if(invoiceIds == null){
                                                    invoiceIds = new ArrayList<>();
                                                }
                                                invoiceIds.add(id);
                                                database.child(USER_COLLECTION).child(userId).child("invoiceIds").setValue(invoiceIds);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e(TAG, "onCancelled: " + error.getMessage());
                                                return;
                                            }
                                        });
                                        callback.onSuccess(invoice);
                                    }).addOnFailureListener(e -> {
                                        callback.onFailed(e);
                                    });
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    callback.onFailed(new Exception(error.getMessage()));
                                }
                            });
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onFailed(new Exception(error.getMessage()));
                }
            });
        }
    }
    public void getAllInvoice(String userId, InvoicesCallbacks callback){
        database.child(INVOICE_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Invoice> invoices = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Invoice invoice = dataSnapshot.getValue(Invoice.class);
                    if(invoice.getUserId().equals(userId)){
                        invoices.add(invoice);
                    }
                }
                callback.onSuccess(invoices);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailed(new Exception(error.getMessage()));
            }
        });
    }
    public void getInvoice(String invoiceId, InvoiceCallbacks callback){
        database.child(INVOICE_COLLECTION).child(invoiceId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    callback.onFailed(new Exception("Invoice not found"));
                    return;
                }
                Invoice invoice = snapshot.getValue(Invoice.class);
                callback.onSuccess(invoice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailed(new Exception(error.getMessage()));
            }
        });
    }
}
