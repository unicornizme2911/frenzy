package com.example.myapplication.models;

import androidx.annotation.NonNull;

import com.example.myapplication.entities.Invoice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvoiceModel extends Model{
    public static final String TAG = "InvoiceModel";
    public static final String INVOICE_COLLECTION = "invoices";
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

    public void createInvoice(String userId, List<String> ticketId, String promotionId, String paymentMethod, String paymentStatus, double totalPrice, InvoiceCallbacks callback){
        String id = UUID.randomUUID().toString();
        Invoice invoice = new Invoice(id, userId, ticketId, promotionId, paymentMethod, paymentStatus, totalPrice);
        database.child(INVOICE_COLLECTION).child(id).setValue(invoice).addOnSuccessListener(aVoid -> {
            callback.onSuccess(invoice);
        }).addOnFailureListener(e -> {
            callback.onFailed(e);
        });
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
