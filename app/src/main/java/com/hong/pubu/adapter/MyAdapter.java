package com.hong.pubu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hong.pubu.Food;
import com.hong.pubu.R;

import java.util.ArrayList;
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
        holder.set(position);

        Glide.with(context)
                .load("http://119.29.15.40:6451/"+foods.get(position).getFoodimage())
                .asBitmap()
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                       int maxHeight = resource.getHeight()/150;
                       Log.i("TAG","height:"+maxHeight);
                    }
                });
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
        public void set(int position){
            //需要Item高度不同才能出现瀑布流的效果，此处简单粗暴地设置一下高度
            if (position % 2 == 0) {
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250));
            } else {
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 350));
            }
        }
    }
    public void update(List<Food> foods){
        //this.foods.addAll(foods);
        for(Food food:foods){
            this.foods.add(food);
            notifyItemInserted(this.foods.size()-1);
        }
    }

    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position, List<Food> list) {
        this.foods.addAll(position,list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        this.foods.remove(position);
        notifyItemRemoved(position);
    }
}
