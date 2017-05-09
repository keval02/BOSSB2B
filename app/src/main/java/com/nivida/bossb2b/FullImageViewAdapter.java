package com.nivida.bossb2b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

class FullImageViewAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;

    ArrayList<String> imageList;
    Activity activity;
    String imageInfoPath = "";
    boolean fromOut=false;

    public FullImageViewAdapter()
    {

    }




    public FullImageViewAdapter(Context context, ArrayList<String> imageList, Activity activity) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        this.imageList = imageList;
    }

    public FullImageViewAdapter(Context applicationContext, ArrayList<String> imageList, Activity product_detail, boolean fromOut) {
        mContext = applicationContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = product_detail;
        this.imageList = imageList;
        this.fromOut=fromOut;
    }

    @Override
    public int getCount() {
        return imageList.size();


    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
    	
        return view == ((View) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
    	
    	TouchImageView img = new TouchImageView(container.getContext());
        img.setBackgroundColor(Color.parseColor("#FFFFFF"));

        Log.e("ImagePath",imageList.get(position));

        if(imageList.get(position).startsWith("http")){
            Picasso.with(mContext).load(imageList.get(position))
                    .placeholder(R.drawable.boss_logo_final)
                    .error(R.drawable.noimagefound)
                    .into(img);
        }
        else {
            Picasso.with(mContext).load(new File(imageList.get(position)))
                    .placeholder(R.drawable.boss_logo_final)
                    .error(R.drawable.noimagefound)
                    .into(img);
        }



        //C.loadImage(mContext,new File(imageList.get(position)),R.drawable.ic_temp_logo,img);

        container.addView(img, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);


        return img;
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    
    @Override
    public void startUpdate(ViewGroup container) {
    	// TODO Auto-generated method stub
    	
    	super.startUpdate(container);
    	 
    	
    }
    
    
}