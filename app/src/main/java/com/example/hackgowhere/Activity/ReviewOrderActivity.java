package com.example.hackgowhere.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackgowhere.Model.OrderHistory;
import com.example.hackgowhere.Model.Post;
import com.example.hackgowhere.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class ReviewOrderActivity extends AppCompatActivity {

    private String postId, userId;
    ImageView image, backBtn;
    TextView title, desc, price, creditBalance;
    Button paymentButton;
    private Context mContext;
    private Post mPost;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
        postId = getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("postid", "none");
        userId = getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("id", "none");
        image = findViewById(R.id.post_image);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.orderDetails);
        price = findViewById(R.id.price);
        creditBalance = findViewById(R.id.creditBalance);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        paymentButton = findViewById(R.id.paymentButton);
        backBtn = findViewById(R.id.backBtn);

        FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                mPost = snapshot.getValue(Post.class);
                Post post = snapshot.getValue(Post.class);
                Picasso.get().load(post.getImageurl()).into(image);
                title.setText(post.getTitle());
                desc.setText(post.getDescription());
                price.setText("$" + post.getWebsite());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int balance = Integer.parseInt("" + snapshot.child("balance").getValue());
                creditBalance.setText("$" + String.valueOf(String.valueOf(balance)));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}