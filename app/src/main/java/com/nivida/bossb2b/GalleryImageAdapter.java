package com.nivida.bossb2b;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;



/**
 * Created by Nivida new on 12-Aug-16.
 */
public class GalleryImageAdapter extends BaseAdapter
{
    private Context context;
    ArrayList<String> array_image_id;


    public GalleryImageAdapter(Context context, ArrayList<String> array_image_id)
    {
        this.context = context;
        this.array_image_id=array_image_id;

    }

    public int getCount() {
        return array_image_id.size();
    }

    public Object getItem(int position) {
        return array_image_id.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup)
    {
        // TODO Auto-generated method stub
        ImageView img = new ImageView(context);

        //imageloader.DisplayImage(GlobalVariable.link+"files/"+array_image_id.get(index), R.drawable.ic_temp_logo, img);
        img.setLayoutParams(new Gallery.LayoutParams(200, 200));
        img.setScaleType(ImageView.ScaleType.FIT_XY);

        return img;
    }

}