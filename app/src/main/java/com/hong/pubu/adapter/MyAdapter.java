package com.hong.pubu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hong.pubu.Food;
import com.hong.pubu.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHodler> {
    private List<Food> foods;
    private Context context;
    public MyAdapter(Context context,List<Food> foods){
        this.foods = foods;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHodler(LayoutInflater.from(context).inflate(R.layout.item_food,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodler holder, int position) {
        Log.i("TAG","我这里被执行了");
        Glide.with(context).load("http://119.29.15.40:6451/"+foods.get(position).getFoodimage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return foods==null?0:foods.size();
    }

    static class MyViewHodler extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_food);
        }
    }
    public void update(List<Food> foods){
        this.foods.addAll(foods);
        notifyDataSetChanged();
    }
}
