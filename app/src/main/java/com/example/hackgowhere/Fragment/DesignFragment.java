package com.example.hackgowhere.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackgowhere.Activity.PostDesignActivity;
import com.example.hackgowhere.Adapter.SimplePostAdapter;
import com.example.hackgowhere.Model.Post;
import com.example.hackgowhere.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DesignFragment extends Fragment {
    private RecyclerView recyclerViewPosts;
    private SimplePostAdapter simplePostAdapter;
    private List<Post> postList;
    private TextView categoryName;
    ImageView newPost;
    private List<String> followingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_individual_category, container, false);

        recyclerViewPosts = view.findViewById(R.id.recycler_view_posts);
        recyclerViewPosts.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        categoryName = view.findViewById(R.id.categoryName);
        categoryName.setText("Design");
        recyclerViewPosts.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        newPost = view.findViewById(R.id.new_post);
        simplePostAdapter = new SimplePostAdapter(getContext(), postList);
        recyclerViewPosts.setAdapter(simplePostAdapter);

        followingList = new ArrayList<>();

        readPosts();

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostDesignActivity.class));
            }
        });

        return view;
    }

    private void readPosts() {

        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    if (post.getCategoryName().equals("Design")) {
                        postList.add(post);
                    }
                }
                simplePostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
