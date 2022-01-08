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

public class HackathonSignUpActivity extends AppCompatActivity {

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
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        paymentButton = findViewById(R.id.signup);
        backBtn = findViewById(R.id.backBtn);

        FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                mPost = snapshot.getValue(Post.class);
                Post post = snapshot.getValue(Post.class);
                Picasso.get().load(post.getImageurl()).into(image);
                title.setText(post.getTitle());
                desc.setText(post.getDescription());
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

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HackathonSignUpActivity.this)
                        .setTitle("Payment")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        String currentTime = Calendar.getInstance().getTime().toString();
                                        String eventName = mPost.getTitle();
                                        String uNumber = Long.toString(Calendar.getInstance().getTimeInMillis());
                                        String refNumber = firebaseUser.getUid() + uNumber;
                                        OrderHistory orderHistory1 = new OrderHistory(eventName, currentTime, mPost.getPublisher());
                                        FirebaseDatabase.getInstance().getReference("OrderHistory").child(firebaseUser.getUid()).child(refNumber).setValue(orderHistory1);
                                        startActivity(new Intent(HackathonSignUpActivity.this, HackathonHistoryActivity.class));

                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });}}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }
}