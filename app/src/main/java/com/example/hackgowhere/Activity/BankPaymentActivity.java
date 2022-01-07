package com.example.hackgowhere.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hackgowhere.R;

public class BankPaymentActivity extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_payment);

        next = findViewById(R.id.button_banktransfer_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankPaymentActivity.this, PaymentConfirmationActivity.class);
                intent.putExtra("paymentType", "Bank Wire Transfer");
                intent.putExtra("currency", "SGD");
                intent.putExtra("status", "Pending");
                startActivity(intent);
            }
        });
    }
}