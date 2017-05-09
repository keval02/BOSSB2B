package com.nivida.bossb2b;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by NWSPL-17 on 2/16/2017.
 */

public class InfoImageViewAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;


    Activity activity;
    String imageInfoPath = "";



    public InfoImageViewAdapter(Context mContext, Activity activity, String imageInfoPath) {
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        this.imageInfoPath = imageInfoPath;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        TouchImageView img = new TouchImageView(container.getContext());
        img.setBackgroundColor(Color.parseColor("#FFFFFF"));

        Log.e("ImagePath","" +imageInfoPath);

        if(imageInfoPath.startsWith("http")){
            Picasso.with(mContext).load(imageInfoPath)
                    .placeholder(R.drawable.boss_logo_final)
                    .into(img);
        }




        //C.loadImage(mContext,new File(imageList.get(position)),R.drawable.ic_temp_logo,img);

        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        // TODO Auto-generated method stub

        super.startUpdate(container);


    }
}
