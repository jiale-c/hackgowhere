package com.example.hackgowhere.Activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hackgowhere.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class EditPostActivity extends AppCompatActivity {

    private Uri imageUri;
    private String imageUrl, categoryName, postId, prevCategory, prevWebsite;

    private ImageView close;
    private ImageView imageAdded;
    private TextView post;
    private EditText description;
    private EditText title;
    private EditText category, website;
    private Spinner dropdown;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        close = findViewById(R.id.close);
        imageAdded = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        description = findViewById(R.id.description);
        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        website = findViewById(R.id.website);
        postId = getIntent().getStringExtra("postId");
        prevCategory = getIntent().getStringExtra("category");
        prevWebsite = getIntent().getStringExtra("website");
        categoryName = getIntent().getStringExtra("categoryName");
        imageUrl = getIntent().getStringExtra("imageUrl");
        //prevDifficulty = getIntent().getIntExtra("difficulty");
        dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"Beginner", "Intermediate", "Advanced", "Free for all"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        FirebaseDatabase.getInstance().getReference("Posts").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String editTitle = "" + snapshot.child("title").getValue();
                String editDescription= "" + snapshot.child("description").getValue();
                Picasso.get().load(imageUrl).into(imageAdded);
                title.setText(editTitle);
                description.setText(editDescription);
                category.setText(prevCategory);
                website.setText(prevWebsite);
                //difficulty.setSelection(prevDifficulty);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void upload() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

                String newDesc = description.getText().toString().trim();
                String newTitle = title.getText().toString().trim();
                String newCategory = category.getText().toString().trim();
                String newWebsite = website.getText().toString().trim();
                String newDifficulty = dropdown.getSelectedItem().toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child(postId);

        ref.child("description").setValue(newDesc);
        ref.child("title").setValue(newTitle);
        ref.child("category").setValue(newCategory);
        ref.child("difficulty").setValue(newDifficulty);
        ref.child("website").setValue(newWebsite);

        pd.dismiss();
        finish();

    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUriContent();
            imageAdded.setImageURI(imageUri);
        } else {
            finish();
        }
    }*/


    @Override
    protected void onStart() {
        super.onStart();
    }
}