package com.nivida.bossb2b;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.nivida.bossb2b.Bean.BeanVendorName;


public class Mapview extends AppCompatActivity {


    private WebView webView;
   // ImageView im_menu,im_back,im_logout,im_history,im_pending_meeting;

    String startloc,endloc,start1,start2;

    AppPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);


        Intent intent = getIntent();
        String lat = intent.getExtras().getString("lat");
        String lonng = intent.getExtras().getString("lonng");

        Log.e("lat",""+lat);
        Log.e("lonng",""+lonng);

        pref=new AppPref(getApplicationContext());

        /*android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(mActionBar.DISPLAY_SHOW_CUSTOM);
*/

        BeanVendorName loc=new BeanVendorName();

        startloc=loc.getStart_latitude();
        Log.e("startloc",""+startloc);
        endloc=loc.getStart_longitude();
        Log.e("endloc",""+endloc);



        String start = "http://maps.google.com/maps?q=loc:"+""+lat+","+lonng;
        Log.e("start",""+start);
        // String endloc="http://maps.google.com/maps?q=loc:"+""+d_dendlatitude+","+d_dendlongitude;

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(start);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_back);
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


    }
    public void onBackPressed() {
        //  Intent in3=new Intent(Mapview.this,Dsr_main_page.class);
        // startActivity(in3);

        // Intent i = new Intent(Mapview.this,Dsr_main_page.class);
        // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //  i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // startActivity(i);
        finish();
    }
}
