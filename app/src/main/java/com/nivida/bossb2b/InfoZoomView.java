package com.nivida.bossb2b;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InfoZoomView extends AppCompatActivity {

    String imageInfoPath = "";

    TouchImageView img_fullZoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_zoom_view);


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
        imageInfoPath = intent.getStringExtra("imageInfoPath");
        Log.e("imageInfoPath" , "-->" +imageInfoPath);

        img_fullZoom = (TouchImageView) findViewById(R.id.img_fullZoom);



        Picasso.with(getApplicationContext())
                .load(imageInfoPath)
                .placeholder(R.drawable.bossimageloading)
                .error(R.drawable.noimagefound)
                .into(img_fullZoom);



    }

    @Override
    public void onBackPressed() {
        finish();

    }
}
