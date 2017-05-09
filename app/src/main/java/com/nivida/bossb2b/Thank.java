package com.nivida.bossb2b;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.nivida.bossb2b.Bean.BeanVendor;
import com.nivida.bossb2b.Bean.BeanVendorName;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Thank extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private static final int CAMERA_REQUEST = 1888;
    public static final int CAMERA_DIRECT = 2;
    Button end_btn, btn_con;

    ListView lists, meeting_list;

    private String imgPath = "";


    Uri mCapturedImageURI;


    String imagepath2 = "";


    String imagepath1 = "";
    String comments, comment2;
    ProgressActivity loadingView;
    Button route_start, route_end, vendor, new_vendor, btn_start_meeting, btn_end_meeting, btn_place_order;
    LinearLayout lv_vendor, view_list, order_show, layout_cemara, new_layout_cemara;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double currentLatitude;
    private double currentLongitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    RadioGroup rg, rg_new;
    ImageView im_cemara_icon, im_cemara, im_direct_cemara, im_cemara_direct_icon;

    RadioButton radio2, rb_radio2, radio1, radio3, rb_radio1, rb_radio3;
    AppPref prefs;

    String json;
    EditText edit_comment;
    TextView start_time, last_time, txt_vendor_name, vendor_time, txt_start_meeting_time, txt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank);

        prefs = new AppPref(getApplicationContext());
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null


        start_time = (TextView) findViewById(R.id.start_time);
        last_time = (TextView) findViewById(R.id.last_time);
        txt_vendor_name = (TextView) findViewById(R.id.txt_vendor_name);
        vendor_time = (TextView) findViewById(R.id.vendor_time);
        txt_date = (TextView) findViewById(R.id.txt_date);
        edit_comment = (EditText) findViewById(R.id.edit_comment);

        im_cemara_direct_icon = (ImageView) findViewById(R.id.im_cemara);
        im_cemara_icon = (ImageView) findViewById(R.id.img_camera);
        im_cemara = (ImageView) findViewById(R.id.im_cemara);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        layout_cemara = (LinearLayout) findViewById(R.id.layout_cemara);


        lv_vendor = (LinearLayout) findViewById(R.id.lv_vendor);
        view_list = (LinearLayout) findViewById(R.id.view_list);
        order_show = (LinearLayout) findViewById(R.id.order_show);
        new_layout_cemara = (LinearLayout) findViewById(R.id.layout_direct_cemara);

        lists = (ListView) findViewById(R.id.lists);
        meeting_list = (ListView) findViewById(R.id.meeting_list);
        rg = (RadioGroup) findViewById(R.id.rg);

        route_start = (Button) findViewById(R.id.route_start);
        route_end = (Button) findViewById(R.id.route_end);
        vendor = (Button) findViewById(R.id.vendor);
        new_vendor = (Button) findViewById(R.id.new_vendor);
        btn_start_meeting = (Button) findViewById(R.id.btn_start_meeting);
        btn_end_meeting = (Button) findViewById(R.id.btn_end_meeting);

        txt_start_meeting_time = (TextView) findViewById(R.id.txt_start_meeting_time);
        txt_vendor_name = (TextView) findViewById(R.id.txt_vendor_name);


        end_btn = (Button) findViewById(R.id.btnmet2);
        btn_con = (Button) findViewById(R.id.btncon);


        btn_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Thank.this, PlaceOrderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });


        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Thank.this, HomeActivity.class);
                startActivity(i);
                finish();
                /*if (Internet.isConnectingToInternet(getApplicationContext())) {


                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        buildAlertMessageNoGps();
                    }
                    else if ((!radio2.isChecked() && !radio1.isChecked() && !radio3.isChecked())) {

                        Toast.makeText(Thank.this, "Please Select Any Meeting Type First", Toast.LENGTH_SHORT).show();


                    }

                    else {
                        Log.e("currentLatitude", "" + currentLatitude);
                        Log.e("currentLongitude", "" + currentLongitude);


                        *//*start_time.setVisibility(View.VISIBLE);*//*

                        *//*lv_vendor.setVisibility(View.VISIBLE);
                        view_list.setVisibility(View.VISIBLE);
                        btn_start_meeting.setVisibility(View.GONE);
                        txt_vendor_name.setVisibility(View.VISIBLE);
                        meeting_list.setVisibility(View.VISIBLE);

                        txt_start_meeting_time.setVisibility(View.INVISIBLE);
                        order_show.setVisibility(View.GONE);
                        route_end.setEnabled(true);
                        route_start.setEnabled(true);
                        new_vendor.setEnabled(false);
*//*

                        String root_date = GetCurrentDateTime();
                        comments = edit_comment.getText().toString().trim();

                        String end_map = "http://maps.google.com/maps?q=loc:" + "" + currentLatitude + "," + currentLongitude;
                        Log.e("start_map", "" + end_map);
                        Log.e("root_date", "" + root_date);




                        int meetings_type1 = 0;

                        if (rg.getCheckedRadioButtonId() == R.id.radio1) {


                            meetings_type1 = 3;
                        } else if (rg.getCheckedRadioButtonId() == R.id.radio2) {
                            meetings_type1 = 2;

                        } else if (rg.getCheckedRadioButtonId() == R.id.radio3) {
                            meetings_type1 = 1;

                        }

                        new end_meeting(root_date, comments, meetings_type1, end_map).execute();



                    }

                } else {
                    Internet.noInternet(getApplicationContext());
                }*/
            }


        });


        im_cemara_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                takeimage(CAMERA_REQUEST);

            }
        });

        im_cemara_direct_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeimage(CAMERA_DIRECT);
            }
        });


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


    }


    private void takeimage(int requestCode) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        startActivityForResult(cameraIntent, requestCode);
    }

    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }

    public String getImagePath() {

        return imgPath;
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            String selectedImagePath = getImagePath();

            Bitmap photo = decodeFile(selectedImagePath);


            layout_cemara.setVisibility(View.VISIBLE);
            im_cemara.setImageBitmap(photo);
            //layout_imageview_1.setVisibility(View.GONE);
            // im_cemara_icon.setVisibility(View.VISIBLE);
            imagepath1 = "";
            //Uri tempUri = getImageUri(getApplicationContext(), photo);
            if (im_cemara.getDrawable() == null) {
                imagepath1 = "";
                Log.e("1111", "11111");
            } else {
                Log.e("222222", "22222");
                imagepath1 = selectedImagePath;
                resizeImage(selectedImagePath);
            }
            Log.e("image path", imagepath1);
        } else if (requestCode == CAMERA_DIRECT && resultCode == RESULT_OK) {
            String selectedImagePath = getImagePath();

            Bitmap photo = decodeFile(selectedImagePath);


            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            new_layout_cemara.setVisibility(View.VISIBLE);
            im_direct_cemara.setVisibility(View.VISIBLE);
            im_direct_cemara.setImageBitmap(photo);
            //layout_imageview_1.setVisibility(View.GONE);
            // im_cemara_icon.setVisibility(View.VISIBLE);
            imagepath2 = "";
            //Uri tempUri = getImageUri(getApplicationContext(), photo);
            if (im_direct_cemara.getDrawable() == null) {
                imagepath2 = "";
                Log.e("3333", "3333");
            } else {
                Log.e("4444", "4444");
                imagepath2 = selectedImagePath;
                resizeImage(selectedImagePath);
            }
            Log.e("image path2", imagepath2);
        }

    }

    public void resizeImage(String path) {
        Bitmap image2 = decodeFile(path);
        File file = new File(path);
        try {
            double xFactor = 0;
            double width = Double.valueOf(image2.getWidth());
            Log.v("WIDTH", String.valueOf(width));
            double height = Double.valueOf(image2.getHeight());
            Log.v("height", String.valueOf(height));
            if (width > height) {
                xFactor = 841 / width;
            } else {
                xFactor = 595 / width;
            }


            Log.v("Nheight", String.valueOf(width * xFactor));
            Log.v("Nweight", String.valueOf(height * xFactor));
            int Nheight = (int) ((xFactor * height));
            int NWidth = (int) (xFactor * width);

            Bitmap bm = Bitmap.createScaledBitmap(image2, NWidth, Nheight, true);
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 40, ostream);
            ostream.close();
        } catch (Exception e) {

        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public class end_meeting extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String root_date;
        String comments;
        String end_map;
        int meetings_type1;

        public end_meeting(String root_date, String comments, int meetings_type1, String end_map) {
            this.root_date = root_date;
            this.comments = comments;
            this.end_map = end_map;
            this.meetings_type1 = meetings_type1;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(Thank.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {


            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                parameters.add(new BasicNameValuePair("meeting_id", prefs.getMeetings_id()));
                Log.e("meetings_id", "" + prefs.getMeetings_id());

                parameters.add(new BasicNameValuePair("end_time", root_date));
                Log.e("end_time", "" + root_date);
                if (!comments.equalsIgnoreCase(""))
                    parameters.add(new BasicNameValuePair("comments", comments));
                Log.e("comments", "" + comments);

                parameters.add(new BasicNameValuePair("meeting_type", String.valueOf(meetings_type1)));
                Log.e("meeting_type1", "" + meetings_type1);

                parameters.add(new BasicNameValuePair("end_latitude", String.valueOf(currentLatitude)));
                Log.e("end_latitude", "" + String.valueOf(currentLatitude));

                parameters.add(new BasicNameValuePair("end_longitude", String.valueOf(currentLongitude)));
                Log.e("end_longitude", "" + String.valueOf(currentLongitude));

                if (!imagepath1.equalsIgnoreCase("")) {
                    parameters.add(new BasicNameValuePair("attachments1", imagepath1));
                    Log.e("attachments1", "" + imagepath1);
                }

                parameters.add(new BasicNameValuePair("end_location", end_map));
                Log.e("end_location", "" + end_map);

                Log.e("params", parameters.toString());


                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.START_MEETING, ServiceHandler.POST, parameters, 1);

                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: " + e.toString());

            }
            return json;


        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            loadingView.dismiss();

            if (json == null || json.isEmpty()) {
                Toast.makeText(Thank.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {


                Log.e("json", json);


                try {


                    JSONObject object = new JSONObject(json);
                    boolean status = object.getBoolean("status");

                    if (status) {

                        prefs = new AppPref(Thank.this);
                        prefs.setVisiable("1");
                        Intent i = new Intent(Thank.this, HomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        String Message = object.getString("message");
                        Toast.makeText(Thank.this, "" + Message, Toast.LENGTH_LONG).show();
                        loadingView.dismiss();


                    } else {

                        String Message = object.getString("message");
                        Toast.makeText(Thank.this, "" + Message, Toast.LENGTH_LONG).show();
                        loadingView.dismiss();


                    }

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());

                }

            }

        }


    }


    @Override
    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");
        super.onResume();

        //Now lets connect to the API
        mGoogleApiClient.connect();

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
    }


    private String GetCurrentDateTime() {


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        // Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();

        return formattedDate;

    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public void onBackPressed() {
        Intent i = new Intent(Thank.this, PlaceOrderActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }


}
