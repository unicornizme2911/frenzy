package com.example.myapplication.models;

import androidx.annotation.NonNull;

import com.example.myapplication.entities.Promotion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PromotionModel extends Model{
    private static final String PROMOTION_COLLECTION = "promotions";
    private static final String TAG = "PromotionModel";
    public interface PromotionCallbacks{
        void onSuccess(Promotion promotion);
        void onFailed(Exception e);
    }
    public interface PromotionsCallbacks{
        void onSuccess(List<Promotion> promotions);
        void onFailed(Exception e);
    }
    public PromotionModel() {
        super();
    }
    public PromotionModel(DatabaseReference database){
        super(database);
    }
    public void getPromotion(String code, PromotionCallbacks callbacks){
        database.child(PROMOTION_COLLECTION).child(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    callbacks.onFailed(new Exception("Promotion not found"));
                    return;
                }
                JSONObject jsonObject = new JSONObject((Map) snapshot.getValue());
                String id = snapshot.getKey();
                String code = jsonObject.optString("code");
                Double percent = jsonObject.optDouble("percent");
                String fromDate = jsonObject.optString("fromDate");
                String toDate = jsonObject.optString("toDate");
                Promotion promotion = new Promotion(code, percent, fromDate, toDate);
                callbacks.onSuccess(promotion);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
    public void getPromotions(PromotionsCallbacks callbacks){
        database.child(PROMOTION_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Promotion> promotions = new ArrayList<Promotion>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Promotion promotion = new Promotion();
                    JSONObject jsonObject = new JSONObject((Map) dataSnapshot.getValue());
                    String id = dataSnapshot.getKey();
                    String code = jsonObject.optString("code");
                    Double percent = jsonObject.optDouble("percent");
                    String fromDate = jsonObject.optString("fromDate");
                    String toDate = jsonObject.optString("toDate");
                    promotion.setCode(code);
                    promotion.setPercent(percent);
                    promotion.setFromDate(fromDate);
                    promotion.setToDate(toDate);
                    promotions.add(promotion);
                }
                callbacks.onSuccess(promotions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callbacks.onFailed(error.toException());
            }
        });
    }
}
