package com.example.hackgowhere.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackgowhere.Model.OrderHistory;
import com.example.hackgowhere.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    Context context;

    ArrayList<OrderHistory> list;

    DatabaseReference ref;

    public OrderHistoryAdapter(Context context, ArrayList<OrderHistory> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public OrderHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_history_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderHistoryAdapter.MyViewHolder holder, int position) {


        final OrderHistory orderHistory = list.get(position);

        ref = FirebaseDatabase.getInstance().getReference("Users").child(orderHistory.getPublisherId());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String username = "" + snapshot.child("username").getValue();
                //holder.tutor.setText(username);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        holder.ref.setText(orderHistory.getEventName());
//        holder.price.setText("" + orderHistory.getPostPrice());
//        holder.postTitle.setText(orderHistory.getPostTitle());
        holder.date.setText(orderHistory.getDate());
//        holder.status.setText(orderHistory.getStatus());
//        holder.type.setText(orderHistory.getType());

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ConfirmCompletionActivity.class);
//                intent.putExtra("refNumber", orderHistory.getRefNumber());
//                intent.putExtra("publisherId", orderHistory.getPublisherId());
//                intent.putExtra("price", orderHistory.getPostPrice());
//                intent.putExtra("postId", orderHistory.getPostId());
//
//                context.startActivity(intent);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ref, price, postTitle, status, tutor, date, type;
        CardView cardView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ref = itemView.findViewById(R.id.order_history_item_ref);
            date = itemView.findViewById(R.id.order_history_item_date);

        }
    }
}