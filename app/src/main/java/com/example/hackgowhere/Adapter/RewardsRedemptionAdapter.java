package com.example.hackgowhere.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackgowhere.Activity.RedeemRewardsActivity;
import com.example.hackgowhere.Model.RewardsRedemptionModel;
import com.example.hackgowhere.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RewardsRedemptionAdapter extends RecyclerView.Adapter<RewardsRedemptionAdapter.MyViewHolder> {

    Context context;

    ArrayList<RewardsRedemptionModel> list;

    public RewardsRedemptionAdapter(Context context, ArrayList<RewardsRedemptionModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public RewardsRedemptionAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rewards_redemption_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        final RewardsRedemptionModel rewardsRedemptionModel = list.get(position);
        holder.title.setText(rewardsRedemptionModel.getTitle());
        holder.points.setText(rewardsRedemptionModel.getPoints());
        Picasso.get().load(rewardsRedemptionModel.getPicture()).into(holder.picture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RedeemRewardsActivity.class);
                intent.putExtra("title", rewardsRedemptionModel.getTitle());
                intent.putExtra("points", rewardsRedemptionModel.getPoints());
                intent.putExtra("picture", rewardsRedemptionModel.getPicture());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, points;
        ImageView picture;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.rewards_redemption_item_reward);
            points = itemView.findViewById(R.id.rewards_redemption_item_points);
            picture = itemView.findViewById(R.id.rewards_redemption_item_picture);

        }
    }
}
