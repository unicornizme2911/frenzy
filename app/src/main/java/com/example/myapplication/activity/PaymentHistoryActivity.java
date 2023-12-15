package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


public class PaymentHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        TextView imageView = findViewById(R.id.tv_toolbar_title);
        ImageView imageView1 = findViewById(R.id.iv_back_history_payment);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });
    }
    private void goHome(){
        Intent sw = new Intent(this, MainActivity.class);
        startActivity(sw);
    }
}
