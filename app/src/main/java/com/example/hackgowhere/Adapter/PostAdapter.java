package com.example.hackgowhere.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackgowhere.Activity.ReviewActivity;
import com.example.hackgowhere.Fragment.PostDetailFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.hackgowhere.Model.Post;
import com.example.hackgowhere.Model.User;
import com.example.hackgowhere.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Viewholder> {

    private Context mContext;
    private List<Post> mPosts;
    private String postId;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {

        final Post post = mPosts.get(position);
        postId = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("postid", "none");
        if (post != null) {
            Picasso.get().load(post.getImageurl()).into(holder.postImage);
            holder.description.setText(post.getDescription());
            holder.title.setText(post.getTitle());
            holder.category.setText("Category: " + post.getCategory());
            holder.difficulty.setText("Difficulty: " + post.getDifficulty());

            FirebaseDatabase.getInstance().getReference().child("Users").child(post.getPublisher()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    Picasso.get().load(user.getProfile_picture()).placeholder(R.mipmap.ic_launcher).into(holder.imageProfile);
                    holder.username.setText(user.getUsername());//user.getUsername());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");

            holder.postImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().putString("postid", post.getPostid()).apply();

                    ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new PostDetailFragment()).commit();
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public ImageView imageProfile;
        public ImageView postImage;
        public ImageView like;
        public ImageView comment;
        public ImageView save;
        public ImageView more;

        public TextView username;
        TextView title;
        TextView description;
        TextView category, difficulty;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.image_profile);
            postImage = itemView.findViewById(R.id.post_image);
            username = itemView.findViewById(R.id.username);
            title = itemView.findViewById(R.id.title);
            category = itemView.findViewById(R.id.category);
            difficulty = itemView.findViewById(R.id.difficulty);
            description = itemView.findViewById(R.id.description);

        }
    }



}

