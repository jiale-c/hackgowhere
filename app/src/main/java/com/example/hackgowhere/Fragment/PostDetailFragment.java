package com.example.hackgowhere.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hackgowhere.Activity.EditPostActivity;
import com.example.hackgowhere.Activity.HackathonSignUpActivity;
import com.example.hackgowhere.Adapter.PostAdapter;
import com.example.hackgowhere.Model.Post;
import com.example.hackgowhere.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PostDetailFragment extends Fragment {

    private String postId, categoryName, imageUrl, category, website, difficulty;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private Button buyButton, removeButton, webButton, editButton;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        postId = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("postid", "none");
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);
        buyButton = view.findViewById(R.id.buyButton);
        removeButton = view.findViewById(R.id.removeButton);
        webButton = view.findViewById(R.id.chatButton);
        editButton = view.findViewById(R.id.editButton);
        firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");

        FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                postList.add(dataSnapshot.getValue(Post.class));

                postAdapter.notifyDataSetChanged();

                if (firebaseAuth.getUid().equals(dataSnapshot.child("publisher").getValue())) {

                    buyButton.setVisibility(View.INVISIBLE);
                    webButton.setVisibility(View.INVISIBLE);
                }
                else {
                    removeButton.setVisibility(View.INVISIBLE);
                    editButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Posts").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        categoryName = (String) snapshot.child("categoryName").getValue();
                        category = (String) snapshot.child("category").getValue();
                        website = (String) snapshot.child("website").getValue();
                        imageUrl = (String) snapshot.child("imageurl").getValue();
                        difficulty = (String) snapshot.child("difficulty").getValue();
                        Intent intent = new Intent(getActivity(), EditPostActivity.class).putExtra("postId", postId);
                        intent.putExtra("categoryName", categoryName);
                        intent.putExtra("imageUrl", imageUrl);
                        intent.putExtra("category", category);
                        intent.putExtra("website", website);
                        intent.putExtra("difficulty", difficulty);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Remove Post")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).removeValue();
                        getActivity().getSupportFragmentManager().popBackStack();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HackathonSignUpActivity.class));

            }
        });


        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        String web = (String) snapshot.child("website").getValue().toString();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
                        startActivity(browserIntent);

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        return view;
    }

}