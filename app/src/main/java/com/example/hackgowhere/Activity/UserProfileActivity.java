package com.example.hackgowhere.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackgowhere.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class UserProfileActivity extends AppCompatActivity {

    private TextView tv_db_first_name, tv_db_last_name, tv_db_phone, tv_db_email, tv_db_school, tv_db_country, tv_db_course, tv_db_year;
    private Button edit, back;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ImageView profile_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tv_db_first_name = findViewById(R.id.textView_user_profile_db_first_name);
        tv_db_last_name = findViewById(R.id.textView_user_profile_db_last_name);
        tv_db_phone = findViewById(R.id.textView_user_profile_db_phone);
        tv_db_email = findViewById(R.id.textView_user_profile_db_email);
        tv_db_school = findViewById(R.id.textView_user_profile_db_school);
        tv_db_course = findViewById(R.id.textView_user_profile_db_course);
        tv_db_year = findViewById(R.id.textView_user_profile_db_year);
        tv_db_country = findViewById(R.id.textView_user_profile_db_country);
        edit = findViewById(R.id.button_user_profile_edit);
        back = findViewById(R.id.button_user_profile_back);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String UID  = (String) user.getUid();
        ref = FirebaseDatabase.getInstance().getReference("Users");
        profile_picture = findViewById(R.id.imageView_user_profile_profile_picture);


        ref.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tv_db_first_name.setText(snapshot.child("first_name").getValue(String.class));
                        tv_db_last_name.setText(snapshot.child("last_name").getValue(String.class));
                        tv_db_phone.setText(snapshot.child("phone").getValue(String.class));
                        tv_db_email.setText(snapshot.child("email").getValue(String.class));
                        tv_db_school.setText(snapshot.child("school").getValue(String.class));
                        tv_db_country.setText(snapshot.child("country").getValue(String.class));
                        tv_db_course.setText(snapshot.child("course").getValue(String.class));
                        tv_db_year.setText(snapshot.child("year").getValue(String.class));
                        Picasso.get().load(snapshot.child("profile_picture").getValue(String.class)).into(profile_picture);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, EditUserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}