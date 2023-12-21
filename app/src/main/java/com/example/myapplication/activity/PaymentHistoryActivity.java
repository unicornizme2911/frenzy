package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.User;
import com.example.myapplication.fragments.DetailUserFragment;
import com.example.myapplication.fragments.HomeFragment;


public class PaymentHistoryActivity extends AppCompatActivity {
    private static final String TAG = "PaymentHistoryAdapter";

    private User user;
    private PaymentHistoryActivity(){

    }
    private PaymentHistoryActivity(User user){
        this.user = user;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        TextView imageView = findViewById(R.id.tv_toolbar_title);
        ImageView imageView1 = findViewById(R.id.iv_back_history_payment);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new DetailUserFragment(user))
                        .commit();
            }
        });
    }
    private void goHome(){
        Intent sw = new Intent(this, MainActivity.class);
        startActivity(sw);
    }
}
