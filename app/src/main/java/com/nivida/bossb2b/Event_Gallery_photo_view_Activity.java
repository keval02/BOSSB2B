package com.nivida.bossb2b;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.nivida.bossb2b.Bean.Bean_ProductImage;


public class Event_Gallery_photo_view_Activity extends AppCompatActivity {

	ViewPager mViewPager;
	CustomPagerAdapter mCustomPagerAdapter;
	ArrayList<Bean_ProductImage> array_image = new ArrayList<Bean_ProductImage>();
	ArrayList<String> array_image_id = new ArrayList<String>();
	DatabaseHandler db;
	AppPrefs appPrefs;
	String pid;

    Gallery gallery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event__gallery_photo_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
       setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        Intent intent=getIntent();
		int position=intent.getIntExtra("position",0);

        appPrefs= new AppPrefs(Event_Gallery_photo_view_Activity.this);
		pid=appPrefs.getproduct_id().toString();

		db = new DatabaseHandler(Event_Gallery_photo_view_Activity.this);
		array_image = db.Get_ProductImage();

        Log.e("Size of Images",array_image.size()+"");

        gallery=(Gallery) findViewById(R.id.gallery);
        gallery.setSpacing(1);






        //ViewGroup.MarginLayoutParams mlp=(ViewGroup.MarginLayoutParams) gallery.getLayoutParams();

        //gallery.setGravity(Gravity.START);
        //mlp.setMargins(-200, 0, 0, 0);

		Log.e("bean_productImages_size",""+array_image.size());
		if (array_image.size() != 0) {

			for (int i = 0; i < array_image.size(); i++) {

				Log.e("pid", "" + array_image.get(i).getPro_id() + ":" + pid);
				if (array_image.get(i).getPro_id().equalsIgnoreCase(pid)) {
					Log.e("bean_productImages_loop", "" + i);
					array_image_id.add(array_image.get(i).getPro_Images());

				}
			}
		}
		mCustomPagerAdapter = new CustomPagerAdapter(Event_Gallery_photo_view_Activity.this,array_image_id,Event_Gallery_photo_view_Activity.this);
        final ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.pager_gallery);
        mViewPager.setAdapter(mCustomPagerAdapter);
        Log.e("position selected",""+position);
        mViewPager.setCurrentItem(position);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int mCurrentFragmentPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                boolean isGoingToRightPage = position == mCurrentFragmentPosition;
                if(isGoingToRightPage)
                {
                    // user is going to the right page
                }
                else
                {
                    // user is going to the left page
                }
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentFragmentPosition = position;
                gallery.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        GalleryImageAdapter galleryImageAdapter=new GalleryImageAdapter(this,array_image_id);
        gallery.setAdapter(galleryImageAdapter);
        gallery.setSelection(position);


        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewPager.setCurrentItem(position);
                gallery.setSelection(position);
                Log.e("selected pos",position+"");
                Log.e("Gallery pos",""+ gallery.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
		

        
	}

    @Override
    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC","Called");
        super.onResume();
    }
	
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub.
        db= new DatabaseHandler(Event_Gallery_photo_view_Activity.this);
        db.Delete_ALL_table();
        db.close();
		Intent iGo = new Intent(Event_Gallery_photo_view_Activity.this,Product_Detail.class);
    	iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(iGo);
		finish();
	}
}
