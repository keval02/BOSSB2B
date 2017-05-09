package com.nivida.bossb2b;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nivida.bossb2b.Bean.Bean_Categeory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhruvil Bhosle on 22-11-2016.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {

    List<Bean_Categeory> bean_categeories = new ArrayList<>();
    Context context;
    OnViewClick onViewClick;


    public CategoryListAdapter(Context context, List<Bean_Categeory> bean_categeories,OnViewClick onViewClick) {
        this.context=context;
        this.bean_categeories = bean_categeories;
        this.onViewClick=onViewClick;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView categeory;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            categeory = (TextView) view.findViewById(R.id.txt_category);
            img = (ImageView) view.findViewById(R.id.img_category);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_list, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Bean_Categeory bean_categeory = bean_categeories.get(position);
        holder.categeory.setText(bean_categeory.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClick.onClick(position);
            }
        });

        Picasso.with(context)
                .load(bean_categeory.getApp_image())
                .into(holder.img);


    }


    @Override
    public int getItemCount() {

        return bean_categeories.size();
    }

    public interface OnViewClick{
        void onClick(int position);
    }
}
