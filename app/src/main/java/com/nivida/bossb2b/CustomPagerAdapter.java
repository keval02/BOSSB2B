package com.nivida.bossb2b;

import java.util.ArrayList;


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

class CustomPagerAdapter extends PagerAdapter {
	 
    Context mContext;
    LayoutInflater mLayoutInflater;

    ArrayList<String> array_image;
    Activity activity;
    boolean fromOut=false;
    public CustomPagerAdapter()
    {
    	
    }
    public CustomPagerAdapter(Context context,ArrayList<String> array_image,Activity activity) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        this.array_image = array_image;
    }

    public CustomPagerAdapter(Context applicationContext, ArrayList<String> str1, Activity product_detail, boolean fromOut) {
        mContext = applicationContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = product_detail;
        this.array_image = str1;
        this.fromOut=fromOut;
    }

    @Override
    public int getCount() {
        return array_image.size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
    	
        return view == ((View) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
       // View itemView = mLayoutInflater.inflate(R.layout.screen_popup, container, false);
        
    	/*RelativeLayout inflater = (RelativeLayout) .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(mContext, R.layout.screen_popup, (ViewGroup) activity.findViewById(R.id.popup_element));*/
		
		/*LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.screen_popup, (ViewPager) activity.findViewById(R.id.popup_element));*/
    	/*LayoutInflater inflater = (LayoutInflater) container.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View layout = inflater.inflate(R.layout.screen_popup, null);

		((ViewPager) container).addView(layout, 0);
        TouchImageView imageView = (TouchImageView) layout.findViewById(R.id.imageView1);
        imageloader.DisplayImage(array_image.get(position), R.drawable.noimage, imageView);
        Log.e("", "Image url :- "+array_image.get(position));
        
       
 
        return layout;*/
    	
    	
    	TouchImageView img = new TouchImageView(container.getContext());
        img.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //img.setImageResource(images[position]);
    	Log.e(array_image.get(position)+"", ""+GlobalVariable.link+"files/"+array_image.get(position));

        C.loadImage(mContext,GlobalVariable.link+"files/"+array_image.get(position),R.drawable.noimagefound,img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fromOut){
                    Intent i = new Intent(mContext, Event_Gallery_photo_view_Activity.class);
                    //app.setproduct_id(pid);
                    //Log.e("img_full", "" + pid);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("position", position);
                    //Log.e("pos selected", "" + selected_image_position);
                    mContext.startActivity(i);
                    activity.finish();
                }

            }
        });


        container.addView(img, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        
       
        
        
        
      /* img.setOnTouchListener(new OnSwipeTouchListener(activity) {
            public void onSwipeTop() {
                Toast.makeText(activity, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(activity, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(activity, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(activity, "bottom", Toast.LENGTH_SHORT).show();
            }

        });*/

        return img;
    }
 
  /*  @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	((ViewPager) container).removeView((View) object); 
    }*/
    
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