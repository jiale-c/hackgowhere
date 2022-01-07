package com.example.hackgowhere.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackgowhere.Model.RewardsRedemptionHistory;
import com.example.hackgowhere.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RewardsHistoryAdapter extends RecyclerView.Adapter<RewardsHistoryAdapter.MyViewHolder> {

    Context context;

    ArrayList<RewardsRedemptionHistory> list;

    public RewardsHistoryAdapter(Context context, ArrayList<RewardsRedemptionHistory> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public RewardsHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rewards_history_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        final RewardsRedemptionHistory rewardsRedemptionHistory = list.get(position);
        holder.title.setText(rewardsRedemptionHistory.getTitle());
        holder.points.setText(rewardsRedemptionHistory.getPoints());
        holder.refNo.setText(rewardsRedemptionHistory.getRefNo());
        holder.date.setText(rewardsRedemptionHistory.getDate());
        holder.status.setText(rewardsRedemptionHistory.getStatus());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView refNo, title, points, status, date;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            refNo = itemView.findViewById(R.id.rewards_history_item_ref);
            title = itemView.findViewById(R.id.reward_history_item_title);
            points = itemView.findViewById(R.id.reward_history_item_points);
            status = itemView.findViewById(R.id.reward_history_item_status);
            date = itemView.findViewById(R.id.reward_history_item_date);

        }
    }
}
