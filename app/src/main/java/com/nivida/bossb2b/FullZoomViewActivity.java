package com.nivida.bossb2b;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class FullZoomViewActivity extends AppCompatActivity {


    ViewPager fullImageView;
    FullImageViewAdapter fullImageViewAdapter;
    ArrayList<String> imagePaths = new ArrayList<>();

    String imageInfoPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_zoom_view);

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

        Intent intent = getIntent();
        imagePaths = intent.getStringArrayListExtra("imagePaths");
        imageInfoPath = intent.getStringExtra("imageInfoPath");
        int position = intent.getIntExtra("ImagePosition", 0);
        Log.e("position selected", "" + position);


        fullImageView = (ViewPager) findViewById(R.id.fullImagePager);

        fullImageViewAdapter = new FullImageViewAdapter(getApplicationContext(), imagePaths, this);

        fullImageView.setAdapter(fullImageViewAdapter);

        fullImageView.setCurrentItem(position, true);
        Log.e("position selected", "" + position);
    }

    @Override
    public void onBackPressed() {
        finish();

    }
}
