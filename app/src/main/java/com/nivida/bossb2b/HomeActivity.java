package com.nivida.bossb2b;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nivida.bossb2b.Bean.BeanVendor;
import com.nivida.bossb2b.Bean.BeanVendorName;
import com.nivida.bossb2b.Model.APIServices;
import com.nivida.bossb2b.Model.ListData;
import com.nivida.bossb2b.Model.ServiceGenerator;
import com.nivida.bossb2b.Model.VendorDataList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, NavigationView.OnNavigationItemSelectedListener, Callback<VendorDataList> {


    private static final int CAMERA_REQUEST = 1888;
    public static final int CAMERA_DIRECT = 2;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;


    private static final int TIME_DELAY = 2000;
    private static long back_presses;

    NavigationView myNavigationview;
    Menu menus;


    DrawerLayout drawer;

    Toolbar toolbar;
    DrawerAdapter drawerAdapter;
    private String imgPath = "";
    RadioButton radio2, rb_radio2, radio1, radio3, rb_radio1, rb_radio3;

    NestedScrollView nestedScrollView;

    String imagepath1 = "";
    String imagepath2 = "";
    String imagepath3 = "";


    String CompanyName = "";
    String comments;


    int pos;
    int pos1;


    ActionBarDrawerToggle actionBarDrawerToggle;

    List<BeanVendor> vendornames = new ArrayList<BeanVendor>();
    List<BeanVendorName> v_names = new ArrayList<BeanVendorName>();


    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double currentLatitude;
    private double currentLongitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    AppPref prefs;
    DatabaseHandler db1;

    ListData dataBase;

    APIServices apiServices;


    ProgressActivity loadingView;
    String json = new String();

    Button route_start, route_end, vendor, new_vendor, btn_start_meeting, btn_end_meeting, btn_place_order;

    TextView start_time, last_time, txt_vendor_name, vendor_time, txt_start_meeting_time, txt_date;

    ImageView tool_grid;

    LinearLayout lv_search, lv_vendor, order_show, list_hide, lv_time, view_list, lv_date, layout_cemara, new_order_show, new_layout_cemara;
    ListView lists, meeting_list;
    RadioGroup rg;
    ImageView im_cemara_icon, im_direct_cemara, im_cemara_direct_icon;
    ListAdapter listAdapter;
    VendorListAdapter vendorListAdapter;
    String topDate;
    EditText edit_comment, txt_search_by_company, txt_search_by_person;
    Database db;
    ScrollView mainScroll;

    ImageView img_refersh;

    String firebase_token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prefs = new AppPref(getApplicationContext());
        prefs.setLogin("1");

        apiServices = ServiceGenerator.getServiceClass();

        loadingView = new ProgressActivity(HomeActivity.this, "");
        loadingView.setCancelable(false);

        toolbar = (Toolbar) findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);

        db = new Database(getApplicationContext());
        db1 = new DatabaseHandler(getApplicationContext());

        dataBase = new ListData(getApplicationContext());

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        rb_radio2 = (RadioButton) findViewById(R.id.rb_radio2);
        rb_radio1 = (RadioButton) findViewById(R.id.rb_radio1);
        rb_radio3 = (RadioButton) findViewById(R.id.rb_radio3);
        mainScroll = (ScrollView) findViewById(R.id.mainScroll);

        img_refersh = (ImageView) findViewById(R.id.img_refersh);

        btn_place_order = (Button) findViewById(R.id.btn_place_order);


        myNavigationview = (NavigationView) findViewById(R.id.nav_drawer);
        myNavigationview.setNavigationItemSelectedListener(this);

        drawerAdapter = new DrawerAdapter(getApplicationContext());

        menus = myNavigationview.getMenu();

        MenuItem dashboardmenu = menus.findItem(R.id.daseboard);

        if (prefs.getRole_id().equalsIgnoreCase(C.DIS_SALES_ROLE) || prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
            dashboardmenu.setVisible(false);
        }

        Log.e("CurrentLattitude", "" + currentLatitude);
        Log.e("CurrentLongitude", "" + currentLongitude);

        edit_comment = (EditText) findViewById(R.id.edit_comment);

        im_cemara_direct_icon = (ImageView) findViewById(R.id.im_cemara);
        im_cemara_icon = (ImageView) findViewById(R.id.img_camera);

        tool_grid = (ImageView) findViewById(R.id.tool_grid);


        if (!prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
            tool_grid.setVisibility(View.GONE);
        }


        im_direct_cemara = (ImageView) findViewById(R.id.im_direct_cemara);
        im_cemara_direct_icon = (ImageView) findViewById(R.id.im_cemara_direct_icon);


        route_start = (Button) findViewById(R.id.route_start);
        route_end = (Button) findViewById(R.id.route_end);
        vendor = (Button) findViewById(R.id.vendor);
        new_vendor = (Button) findViewById(R.id.new_vendor);

        if (!prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
            vendor.setText("Retailer");
        }

        if (!prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
            new_vendor.setText("New Retailer");
        }


        btn_start_meeting = (Button) findViewById(R.id.btn_start_meeting);
        btn_end_meeting = (Button) findViewById(R.id.btn_end_meeting);

        start_time = (TextView) findViewById(R.id.start_time);
        last_time = (TextView) findViewById(R.id.last_time);
        txt_vendor_name = (TextView) findViewById(R.id.txt_vendor_name);
        vendor_time = (TextView) findViewById(R.id.vendor_time);
        txt_date = (TextView) findViewById(R.id.txt_date);


        txt_start_meeting_time = (TextView) findViewById(R.id.txt_start_meeting_time);


        lv_vendor = (LinearLayout) findViewById(R.id.lv_vendor);

        order_show = (LinearLayout) findViewById(R.id.order_show);
        list_hide = (LinearLayout) findViewById(R.id.list_hide);
        lists = (ListView) findViewById(R.id.lists);

        lv_time = (LinearLayout) findViewById(R.id.lv_time);
        view_list = (LinearLayout) findViewById(R.id.view_list);
        lv_date = (LinearLayout) findViewById(R.id.lv_date);
        layout_cemara = (LinearLayout) findViewById(R.id.layout_cemara);
        new_order_show = (LinearLayout) findViewById(R.id.new_order_show);
        new_layout_cemara = (LinearLayout) findViewById(R.id.layout_direct_cemara);

        route_end.setEnabled(false);
        route_end.setTextColor(Color.parseColor("#e1e1e1"));


        lv_search = (LinearLayout) findViewById(R.id.lv_search);
        txt_search_by_person = (EditText) findViewById(R.id.txt_searchby_person);
        txt_search_by_company = (EditText) findViewById(R.id.txt_searchby_company);


        meeting_list = (ListView) findViewById(R.id.meeting_list);
        rg = (RadioGroup) findViewById(R.id.rg);


        final Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        topDate = df.format(c.getTime());


        txt_date.setText("" + topDate);


        listAdapter = new ListAdapter(getApplicationContext(), vendornames);
        lists.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        String NewAddedVendor = "";
        Intent intent = getIntent();

        NewAddedVendor = intent.getStringExtra("NewCompanyName");
        prefs.setNew_vendor_name(NewAddedVendor);
        tool_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Allowancee.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


        if (checkPlayServices()) {

            startFusedLocation();
            registerRequestUpdate(this);


        }


        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment = edit_comment.getText().toString().trim();
                prefs.setComment(comment);


                if (!imagepath1.isEmpty()) {
                    prefs.setImagePath1(imagepath1);
                }
                if (!imagepath2.isEmpty()) {
                    prefs.setImagePath2(imagepath2);

                }
                if (!imagepath3.isEmpty()) {
                    prefs.setImagePathe3(imagepath3);
                }

                Log.e("Imagepath1", "-->" + imagepath1);
                Log.e("Imagepath2", "-->" + imagepath2);
                Log.e("Imagepath3", "-->" + imagepath3);

                Log.e("Imagepath11", "-->" + prefs.getImagePath1());
                Log.e("Imagepath22", "-->" + prefs.getImagePath2());
                Log.e("Imagepath33", "-->" + prefs.getImagePathe3());

                Log.e("count", "-->" + layout_cemara.getChildCount());


                Intent intent = new Intent(HomeActivity.this, PlaceOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("CompanyName", CompanyName);
                startActivity(intent);


            }
        });

        im_cemara_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int count = layout_cemara.getChildCount();

                if (count <= 2) {
                    takeimage(CAMERA_REQUEST);
                } else {
                    Toast.makeText(HomeActivity.this, "You Can Upload Max 3 photos", Toast.LENGTH_SHORT).show();
                }


            }
        });

        im_cemara_direct_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeimage(CAMERA_DIRECT);
            }
        });

        route_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebase_token = FirebaseInstanceId.getInstance().getToken();
                if (Internet.isConnectingToInternet(getApplicationContext())) {

                    int off = 0;
                    try {
                        off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (off == 0) {
                        displayLocationSettingRequest(getApplicationContext());
                    } else {


                        displayLocationSettingRequest(getApplicationContext());
                        route_end.setGravity(Gravity.CENTER);
                        Log.e("currentLatitude", "" + currentLatitude);
                        Log.e("currentLongitude", "" + currentLongitude);

                        vendor.setVisibility(View.VISIBLE);
                        route_start.setGravity(Gravity.CENTER);
                        route_end.setBackgroundColor(Color.parseColor("#ffffff"));
                        route_start.setBackgroundColor(Color.parseColor("#7BC552"));
                        vendor.setBackgroundColor(Color.parseColor("#ffffff"));
                        route_end.setTextColor(Color.parseColor("#e1e1e1"));
                        lv_time.setVisibility(View.VISIBLE);
                        start_time.setVisibility(View.VISIBLE);
                        lv_vendor.setVisibility(View.VISIBLE);
                        last_time.setVisibility(View.GONE);
                        route_start.setEnabled(false);
                        route_end.setEnabled(false);
                        vendor.setEnabled(true);
                        new_vendor.setVisibility(View.VISIBLE);
                        new_vendor.setEnabled(true);
                        route_start.setGravity(Gravity.START | Gravity.CENTER);


                        if (v_names.size() == 0) {


                            view_list.setVisibility(View.GONE);
                            meeting_list.setVisibility(View.GONE);


                            Log.e("list", "gone");
                        } else {
                            view_list.setVisibility(View.VISIBLE);
                            meeting_list.setVisibility(View.VISIBLE);
                        }

                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mma");
                        final String currentDateTimeString = sdf.format(d);
                        String[] spilt_formate = currentDateTimeString.split(" ");
                        String spilt_date = spilt_formate[0];
                        String spilt_time = spilt_formate[1];


                        String root_date = GetCurrentDateTime();


                        start_time.setText("" + spilt_time);

                        prefs.setRoute_start_time(currentDateTimeString);


                        String start_location = "http://maps.google.com/maps?q=loc:" + "" + currentLatitude + "," + currentLongitude;
                        Log.e("start_map", "" + start_location);

                        prefs.setRoute_startedLattitude((float) currentLatitude);
                        prefs.setRoute_startedLongitude((float) currentLongitude);

                        Log.e("started", "" + prefs.getRoute_startedLattitude());
                        Log.e("started", "" + prefs.getRoute_startedLongitude());

                        new root_start(root_date, start_location, currentLongitude, currentLatitude).execute();

                    }
                } else {
                    Internet.noInternet(getApplicationContext());
                }


            }
        });

        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("ServiceCalled", "-->" + prefs.isOnceclicked());
                new_vendor.setBackgroundColor(Color.parseColor("#ffffff"));
                vendor.setBackgroundColor(Color.parseColor("#7BC552"));
                start_time.setVisibility(View.VISIBLE);
                list_hide.setVisibility(View.VISIBLE);
                img_refersh.setVisibility(View.VISIBLE);

                vendor.setEnabled(true);


                String start_location = "http://maps.google.com/maps?q=loc:" + "" + currentLatitude + "," + currentLongitude;
                Log.e("start_location", "" + start_location);
                Log.e("Selected_role_id", prefs.getSelectedUserRole());

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String date = simpleDateFormat.format(calendar.getTime());

                prefs.setCurrent_date(date);


                if (prefs.isOnceclicked() == false) {
                    if (Internet.isConnectingToInternet(getApplicationContext())) {



                        if (prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {

                            loadingView.show();
                            Call<VendorDataList> dataListCall = apiServices.companySalesPerson(prefs.getUser_id());
                            dataListCall.enqueue(HomeActivity.this);

                        } else {

                            loadingView.show();
                            Call<VendorDataList> dataListCall = apiServices.distSalesPersonList(prefs.getUser_id());
                            dataListCall.enqueue(HomeActivity.this);

                        }
                    } else {
                        Internet.noInternet(getApplicationContext());
                    }
                } else {


                    lists.setVisibility(View.VISIBLE);
                    vendornames = dataBase.getAllData();
                    listAdapter = new ListAdapter(getApplicationContext(), vendornames);
                    lists.setAdapter(listAdapter);
                    lv_search.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();


                }

            }


        });
        txt_search_by_company.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String name = txt_search_by_company.getText().toString().trim();


                    List<BeanVendor> retailerPersonListForSearch = new ArrayList<BeanVendor>();

                    for (int i = 0; i < vendornames.size(); i++) {

                        if (vendornames.get(i).getCompany_name().toLowerCase().startsWith(name.toLowerCase()) /*|| vendornames.get(i).getFirst_name().toLowerCase().startsWith(name.toLowerCase())*/) {

                            retailerPersonListForSearch.add(vendornames.get(i));
                            Log.e("list333", "33333");
                        }

                    }

                    if (retailerPersonListForSearch.size() <= 0)
                        Toast.makeText(HomeActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();

                    listAdapter.updateData(retailerPersonListForSearch);


                    return true;


                }


                return false;
            }
        });

        txt_search_by_company.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    txt_search_by_company.scrollTo(0, mainScroll.getTop());
                }
            }
        });

        txt_search_by_company.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String name = txt_search_by_company.getText().toString().trim();


                List<BeanVendor> retailerPersonListForSearch = new ArrayList<BeanVendor>();

                for (int i = 0; i < vendornames.size(); i++) {

                    if (vendornames.get(i).getCompany_name().toLowerCase().startsWith(name.toLowerCase()) /*|| vendornames.get(i).getFirst_name().toLowerCase().startsWith(name.toLowerCase())*/) {

                        retailerPersonListForSearch.add(vendornames.get(i));

                        Log.e("list333", "33333");
                    }
                }

                if (retailerPersonListForSearch.size() <= 0)
                    Toast.makeText(HomeActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();

                listAdapter.updateData(retailerPersonListForSearch);


            }


        });


        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                List<BeanVendor> currentLists = listAdapter.getCurrentRetailers();
                txt_search_by_company.setText("");
                String itemValue = currentLists.get(position).getCompany_name();
                prefs.setSelectedVendorName(itemValue);
                lv_vendor.setVisibility(View.VISIBLE);
                listAdapter.notifyDataSetChanged();
                list_hide.setVisibility(View.GONE);
                btn_start_meeting.setVisibility(View.VISIBLE);
                start_time.setVisibility(View.VISIBLE);
                edit_comment.setText("");
                lv_search.setVisibility(View.GONE);
                vendor.setEnabled(false);
                prefs.setVendor_id(currentLists.get(position).getVendor_id());
                Log.e("vendor_id", currentLists.get(position).getVendor_id());
                prefs.setSelectedUserRole(currentLists.get(position).getRole_id());
                vendor.setEnabled(true);
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                final String currentDateTimeString = sdf.format(d);

                String root_date = GetCurrentDateTime();
                Log.e("root_date", "" + root_date);
                vendor_time.setText("" + currentDateTimeString);


                prefs.setVendorSelectiontime(currentDateTimeString);
                vendor.setText(itemValue + "\n" + prefs.getVendorSelectiontime());
                prefs.setOnSelectedFirmName(itemValue);
                Log.e("OnItemSelectedFirmName", itemValue);
                new_vendor.setBackgroundColor(Color.parseColor("#ffffff"));

                img_refersh.setVisibility(View.GONE);

                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(txt_search_by_company.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });
        btn_start_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Internet.isConnectingToInternet(getApplicationContext())) {
                    int off = 0;
                    try {
                        off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (off == 0) {
                        displayLocationSettingRequest(getApplicationContext());
                    } else {
                        displayLocationSettingRequest(getApplicationContext());
                        Log.e("currentLatitude", "" + currentLatitude);
                        Log.e("currentLongitude", "" + currentLongitude);
                        btn_start_meeting.setGravity(Gravity.START | Gravity.CENTER);
                        new_vendor.setBackgroundColor(Color.parseColor("#ffffff"));
                        new_vendor.setEnabled(false);
                        new_vendor.setTextColor(Color.parseColor("#e1e1e1"));
                        btn_start_meeting.setBackgroundColor(Color.parseColor("#7BC552"));
                        route_end.setTextColor(Color.parseColor("#e1e1e1"));
                        start_time.setVisibility(View.VISIBLE);
                        order_show.setVisibility(View.VISIBLE);
                        btn_start_meeting.setEnabled(false);
                        txt_start_meeting_time.setVisibility(View.VISIBLE);
                        edit_comment.setText("");

                        img_refersh.setVisibility(View.GONE);

                        route_end.setEnabled(false);
                        vendor.setEnabled(false);

                        vendor.setBackgroundColor(Color.parseColor("#7BC552"));


                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                        final String currentDateTimeString = sdf.format(d);

                        String root_date = GetCurrentDateTime();
                        Log.e("root_date", "" + root_date);


                        txt_start_meeting_time.setText("" + currentDateTimeString);


                        String start_map = "http://maps.google.com/maps?q=loc:" + "" + currentLatitude + "," + currentLongitude;
                        Log.e("start_Location", "" + start_map);

                        prefs.setRoute_startLat(currentLatitude);
                        prefs.setRoute_startLong(currentLongitude);

                        prefs.setMeeting_startedLattitude((float) currentLatitude);
                        prefs.setMeeting_startedLongitude((float) currentLongitude);

                        new start_meeting(root_date, start_map, currentLongitude, currentLatitude, currentDateTimeString).execute();
                    }
                } else {
                    Internet.noInternet(getApplicationContext());
                }


            }
        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                pos = rg.indexOfChild(findViewById(checkedId));
                pos1 = rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));

                switch (pos) {
                    case R.id.radio1:
                        break;
                    case R.id.radio2:
                        break;
                    case R.id.radio3:
                        break;
                    default:
                        break;
                }


            }
        });


        btn_end_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Internet.isConnectingToInternet(getApplicationContext())) {
                    int off = 0;
                    try {
                        off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (off == 0) {
                        displayLocationSettingRequest(getApplicationContext());
                    } else {
                        displayLocationSettingRequest(getApplicationContext());
                        if ((!radio2.isChecked() && !radio1.isChecked() && !radio3.isChecked())) {

                            Toast.makeText(HomeActivity.this, "Please Select Any Meeting Type First", Toast.LENGTH_SHORT).show();


                        } else {
                            Log.e("currentLatitude", "" + currentLatitude);
                            Log.e("currentLongitude", "" + currentLongitude);


                            start_time.setVisibility(View.VISIBLE);

                            lv_vendor.setVisibility(View.VISIBLE);
                            view_list.setVisibility(View.VISIBLE);
                            btn_start_meeting.setVisibility(View.GONE);
                            meeting_list.setVisibility(View.VISIBLE);
                            view_list.setVisibility(View.VISIBLE);
                            txt_start_meeting_time.setVisibility(View.INVISIBLE);
                            order_show.setVisibility(View.GONE);
                            route_end.setEnabled(true);
                            route_start.setEnabled(true);
                            new_vendor.setEnabled(false);


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
                            prefs.setNewvendorAdd(false);


                            new end_meeting(root_date, comments, meetings_type1, end_map, currentLongitude, currentLatitude).execute();
                            db.removeFromCart(null);
                        }
                    }

                } else {
                    Internet.noInternet(getApplicationContext());
                }
            }
        });


        route_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Internet.isConnectingToInternet(getApplicationContext())) {

                    int off = 0;
                    try {
                        off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (off == 0) {
                        displayLocationSettingRequest(getApplicationContext());
                    } else {


                        displayLocationSettingRequest(getApplicationContext());
                        Log.e("currentLatitude", "" + currentLatitude);
                        Log.e("currentLongitude", "" + currentLongitude);
                        route_start.setGravity(Gravity.CENTER);
                        route_end.setGravity(Gravity.START | Gravity.CENTER);
                        start_time.setVisibility(View.VISIBLE);
                        last_time.setVisibility(View.VISIBLE);
                        route_end.setBackgroundColor(Color.parseColor("#7BC552"));
                        route_start.setBackgroundColor(Color.parseColor("#ffffff"));
                        lv_vendor.setVisibility(View.GONE);
                        txt_start_meeting_time.setVisibility(View.GONE);
                        txt_vendor_name.setVisibility(View.GONE);
                        vendor_time.setVisibility(View.GONE);
                        view_list.setVisibility(View.GONE);
                        meeting_list.setVisibility(View.GONE);
                        view_list.setVisibility(View.GONE);
                        route_start.setEnabled(true);

                        lists.setVisibility(View.GONE);
                        route_end.setEnabled(false);
                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                        final String currentDateTimeString = sdf.format(d);
                        last_time.setText("" + currentDateTimeString);
                        txt_search_by_company.setVisibility(View.GONE);
                        String root_date = GetCurrentDateTime();

                        String end_location = "http://maps.google.com/maps?q=loc:" + "" + currentLatitude + "," + currentLongitude;
                        Log.e("end_map", "" + end_location);

                        new root_end(root_date, end_location, currentLongitude, currentLatitude).execute();

                    }
                } else {
                    Internet.noInternet(getApplicationContext());

                }


            }
        });


        img_refersh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Internet.isConnectingToInternet(getApplicationContext())) {

                    if (prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
                        loadingView.show();
                        Call<VendorDataList> dataListCall = apiServices.companySalesPerson(prefs.getUser_id());
                        dataListCall.enqueue(HomeActivity.this);

                    } else {

                        loadingView.show();
                        Call<VendorDataList> dataListCall = apiServices.distSalesPersonList(prefs.getUser_id());
                        dataListCall.enqueue(HomeActivity.this);
                    }
                } else {
                    Internet.noInternet(getApplicationContext());
                }


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


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {


            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


        Log.e("prefrence", "" + prefs.getVisiable().toString() + " " + prefs.isMeetingStarted());

        if (!prefs.getVisiable().equalsIgnoreCase("1")) {
            route_end.setEnabled(true);

        }

        if (prefs.getVisiable().equalsIgnoreCase("1")) {

            if (!prefs.isRouteEnded()) {

                route_end.setEnabled(true);
                route_end.setTextColor(Color.parseColor("#000000"));

            }

            new_vendor.setVisibility(View.VISIBLE);
            route_start.setBackgroundColor(Color.parseColor("#7BC552"));
            route_start.setGravity(Gravity.START | Gravity.CENTER);
            lv_vendor.setVisibility(View.VISIBLE);
            vendor.setVisibility(View.VISIBLE);
            route_start.setEnabled(false);
            vendor.setEnabled(true);
            new_vendor.setEnabled(true);
            start_time.setVisibility(View.VISIBLE);
            new_vendor.setEnabled(true);


            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mma");
            final String currentDateTimeString = sdf.format(d);
            String[] split_dateformat = currentDateTimeString.split(" ");
            String current_date = split_dateformat[0];
            String current_time = split_dateformat[1];


            final String route_started_date = prefs.getRoute_start_time();
            String[] route_started_date_format = route_started_date.split(" ");
            String split_route_date = route_started_date_format[0];
            String split_route_time = route_started_date_format[1];
            start_time.setText(split_route_time);


            if (prefs.isMeetingStarted()) {

                String Comment = prefs.getComment();
                if (Comment.isEmpty()) {
                    edit_comment.setText("");
                } else {
                    edit_comment.setText(Comment);
                }

                btn_start_meeting.setGravity(Gravity.START | Gravity.CENTER);
                route_start.setGravity(Gravity.START | Gravity.CENTER);
                btn_start_meeting.setVisibility(View.VISIBLE);
                btn_start_meeting.setBackgroundColor(Color.parseColor("#7BC552"));
                route_end.setTextColor(Color.parseColor("#e1e1e1"));
                start_time.setVisibility(View.VISIBLE);
                order_show.setVisibility(View.VISIBLE);
                btn_start_meeting.setEnabled(false);
                txt_start_meeting_time.setVisibility(View.VISIBLE);

                new_vendor.setEnabled(false);
                new_vendor.setTextColor(Color.parseColor("#e1e1e1"));
                vendor.setBackgroundColor(Color.parseColor("#7BC552"));
                vendor.setText(prefs.getSelectedVendorName() + "\n" + prefs.getVendorSelectiontime());
                route_end.setEnabled(false);
                vendor.setEnabled(false);
                img_refersh.setVisibility(View.GONE);
                txt_start_meeting_time.setText(prefs.getMeetingStartTime());

                String image1 = prefs.getImagePath1();
                String image2 = prefs.getImagePath2();
                String image3 = prefs.getImagePathe3();

                Log.e("Image111", image1);
                Log.e("Image222", image2);
                Log.e("Image333", image3);
                if (!image1.isEmpty()) {

                    resizeImage(image1);

                }
                if (!image2.isEmpty()) {

                    resizeImage(image2);
                }
                if (!image3.isEmpty()) {

                    resizeImage(image3);

                }

                if (prefs.isPlaceorderclicked()) {

                    radio3.setChecked(true);
                }
            }
            Log.e("time visiable", "visiable");

        }
        new_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vendor.setBackgroundColor(Color.parseColor("#ffffff"));
                vendor.setEnabled(true);
                new_vendor.setBackgroundColor(Color.parseColor("#7BC552"));
                vendor.setText("Retailer/Distributor");
                if (!prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
                    vendor.setText("Retailer");
                }

                Intent intent = new Intent(HomeActivity.this, AddNewVendor.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        new meeting_check().execute();

    }

    private void takeimage(int requestCode) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        /*if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

            try {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, createImageFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {*/
        try {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // }

        startActivityForResult(cameraIntent, requestCode);
    }


    public Uri setImageUri() throws IOException {
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");

        Uri imgUri = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

            imgUri = Uri.fromFile(file);
        } else {

            imgUri = FileProvider.getUriForFile(HomeActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);

        }


        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        /*File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");*/
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri photoURI = FileProvider.getUriForFile(HomeActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                file);

        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                file

        );

        Log.e("IMAGEPROVIDER1", "---->" + image);
        // Log.e("IMAGEPROVIDERs" , "---->"  + file);
        // Save a file: path for use with ACTION_VIEW intents
        this.imgPath = /*"file:" +*/ file.getAbsolutePath();
        return image;
    }


    public String getImagePath() {

        Log.e("inIMagePath", "--->" + imgPath);
        return imgPath;
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 400;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            String selectedImagePath = getImagePath();

            Log.e("imagePaths", "---->" + selectedImagePath);


            Bitmap photo = decodeFile(selectedImagePath);


            if (layout_cemara.getChildCount() == 0) {
                imagepath1 = "";
                imagepath1 = selectedImagePath;


                resizeImage(selectedImagePath);
                Log.e("imagepath1", imagepath1);

            } else if (layout_cemara.getChildCount() == 1) {
                imagepath2 = "";

                imagepath2 = selectedImagePath;
                resizeImage(selectedImagePath);
                Log.e("image path2", imagepath2);
            } else if (layout_cemara.getChildCount() == 2) {

                imagepath3 = "";

                imagepath3 = selectedImagePath;
                resizeImage(selectedImagePath);
                Log.e("image path3", imagepath3);

            }
        }
    }

    public void resizeImage(final String path) {
        Log.e("imagePathssss", "---->" + path);
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


            final View view = getLayoutInflater().inflate(R.layout.layout_subimage_add, null);
            ImageView img_attachment = (ImageView) view.findViewById(R.id.img_attachment);
            ImageView img_remove = (ImageView) view.findViewById(R.id.img_remove);
            final TextView txt_imagePath = (TextView) view.findViewById(R.id.txt_imagePath);

            img_attachment.setImageBitmap(bm);
            txt_imagePath.setText(path);

            img_attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFullImageView(path);
                }
            });


            img_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (prefs.getImagePath1().equalsIgnoreCase(txt_imagePath.getText().toString())) {
                        prefs.setImagePath1("");
                    } else if (prefs.getImagePath2().equalsIgnoreCase(txt_imagePath.getText().toString())) {
                        prefs.setImagePath2("");
                    } else if (prefs.getImagePathe3().equalsIgnoreCase(txt_imagePath.getText().toString())) {
                        prefs.setImagePathe3("");
                    }

                    layout_cemara.removeView(view);

                }
            });

            layout_cemara.addView(view);

            ostream.close();
        } catch (Exception e) {

        }
    }

    private void showFullImageView(String imgPath) {

        ArrayList<String> imagePaths = new ArrayList<>();


        int selectedPosition = 0;
        for (int i = 0; i < layout_cemara.getChildCount(); i++) {
            View view = layout_cemara.getChildAt(i);
            String path = ((TextView) view.findViewById(R.id.txt_imagePath)).getText().toString();

            if (path.equalsIgnoreCase(imgPath))
                selectedPosition = i;

            imagePaths.add(path);
        }

        Intent intent = new Intent(getApplicationContext(), FullZoomViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putStringArrayListExtra("imagePaths", imagePaths);
        intent.putExtra("ImagePosition", selectedPosition);
        startActivity(intent);
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

    @Override
    protected void onResume() {

        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");
        super.onResume();


        //Now lets connect to the API
        mGoogleApiClient.connect();


        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mma");
        final String currentDateTimeString = sdf.format(d);
        String[] split_dateformat = currentDateTimeString.split(" ");
        String current_date = split_dateformat[0];
        String current_time = split_dateformat[1];

        final String route_started_date = prefs.getRoute_start_time();
        String[] route_started_date_format = route_started_date.split(" ");
        String split_route_date = route_started_date_format[0];
        //  String split_route_time = route_started_date_format[1];
        String root_date = GetCurrentDateTime();

        if (prefs.isRouteStarted() && current_date.compareTo(split_route_date) > 0) {

            if (prefs.isMeetingStarted()) {


                String end_map = "http://maps.google.com/maps?q=loc:" + "" + prefs.meeting_startedLattitude + "," + prefs.meeting_startedLongitude;
                new endResume_meeting(root_date, "", 2, end_map, prefs.meeting_startedLongitude, prefs.meeting_startedLattitude).execute();


            }
            if (!prefs.isMeetingStarted()) {


                Log.e("lat", "" + prefs.getRoute_startedLattitude());
                Log.e("long", "" + prefs.getRoute_startedLongitude());

                String end_location = "http://maps.google.com/maps?q=loc:" + "" + prefs.getRoute_startedLattitude() + "," + prefs.getRoute_startedLongitude();

                new root_onresume_end(root_date, end_location, prefs.getRoute_startedLattitude(), prefs.getRoute_startedLongitude()).execute();
            }

        } else {

            Date d1 = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            final String currentDateTimeString1 = sdf1.format(d1);
            txt_date.setText(currentDateTimeString1);


        }


    }

    @Override
    protected void onStart() {

        super.onStart();
    }


    @Override
    protected void onPause() {

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");


    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
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


    private String GetCurrentDateTime() {


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;

    }

    public void logout() {

        if ((order_show.getVisibility() == View.VISIBLE) || (new_order_show.getVisibility() == View.VISIBLE)) {
            drawer.closeDrawer(GravityCompat.START);
            Toast.makeText(this, "Please End Meeting First", Toast.LENGTH_SHORT).show();
        } else {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
            builder1.setMessage("Are you sure to logout?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            prefs.setSales_has_user_selected(false);
                            prefs = new AppPref(HomeActivity.this);
                            prefs.setLoggedIn(false);
                            prefs.setVisiable("");
                            prefs.setMeetingStarted(false);
                            prefs.setOnceclicked(false);
                            String root_date = GetCurrentDateTime();
                            dataBase.deleteList();

                            String end_location = "http://maps.google.com/maps?q=loc:" + "" + currentLatitude + "," + currentLongitude;
                            Log.e("end_map", "" + end_location);

                            new root_logout_end(root_date, end_location, currentLongitude, currentLatitude).execute();

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            db1.Delete_user_table();
                            db.removeFromCart(null);
                            prefs.clearData(getApplicationContext());

                            dialog.cancel();

                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            drawer.closeDrawer(GravityCompat.START);
                            dialog.cancel();

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

        if ((order_show.getVisibility() == View.VISIBLE) || (new_order_show.getVisibility() == View.VISIBLE)) {
            Toast.makeText(this, "Please End Meeting First", Toast.LENGTH_SHORT).show();
        } else {

            if (back_presses + TIME_DELAY > System.currentTimeMillis()) {
                prefs = new AppPref(HomeActivity.this);
                prefs.setLoged_in_for_vendor(true);

                prefs = new AppPref(HomeActivity.this);
                prefs.setLogin("1");
                if (prefs.isMeetingStarted()) {
                    if (prefs.isNewvendorAdd()) {


                        prefs.setNewvendorAdd(true);
                    }


                } else {

                    prefs.setNewvendorAdd(false);

                }

                prefs.setNewvendorAdd(false);
                ColorDrawable buttonColor = (ColorDrawable) route_start.getBackground();
                int colorId = buttonColor.getColor();
                if (colorId == Color.parseColor("#7BC552")) {
                    prefs.setVisiable("1");
                } else {
                    prefs.setVisiable("");

                }

                finish();

            } else {
                Toast.makeText(getBaseContext(), "Press Back Once again to Exit", Toast.LENGTH_SHORT).show();
            }

            back_presses = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.daseboard:
                if ((order_show.getVisibility() == View.VISIBLE) || (new_order_show.getVisibility() == View.VISIBLE)) {
                    drawer.closeDrawer(GravityCompat.START);
                    Toast.makeText(this, "Please End Meeting First", Toast.LENGTH_SHORT).show();
                } else {
                    Intent b = new Intent(HomeActivity.this, Company_SalesPerson_Activity.class);
                    b.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(b);
                    drawer.closeDrawer(GravityCompat.START);


                }
                break;


            case R.id.myaccount:

                Intent c = new Intent(HomeActivity.this, User_Profile.class);
                c.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c);
                drawer.closeDrawer(GravityCompat.START);

                break;

            case R.id.contactus:

                Intent intent = new Intent(HomeActivity.this, Contact.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

                break;

            case R.id.history:

                Intent h = new Intent(HomeActivity.this, All_History.class);
                h.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(h);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.retailer:

                Intent j = new Intent(HomeActivity.this, RetailerReport.class);
                j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(j);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.logout:
                logout();
                break;
            default:
        }


        return false;
    }

    @Override
    public void onResponse(Call<VendorDataList> call, Response<VendorDataList> response) {

        VendorDataList dataList = response.body();
        vendornames.clear();
        dataBase.deleteList();
        loadingView.dismiss();


        if (dataList.isStatus()) {

            Toast.makeText(this, dataList.getMessage(), Toast.LENGTH_SHORT).show();
            vendornames.addAll(dataList.getVendorArrayList());
            lv_search.setVisibility(View.VISIBLE);
            new AddVendorToDatabase(vendornames).execute();
            prefs.setOnceclicked(true);

        } else {
            Toast.makeText(this, dataList.getMessage(), Toast.LENGTH_SHORT).show();
        }

        listAdapter.notifyDataSetChanged();

    }

    private class AddVendorToDatabase extends AsyncTask<Void, Void, Void> {

        List<BeanVendor> vendorList = new ArrayList<>();

        public AddVendorToDatabase(List<BeanVendor> vendorList) {
            this.vendorList = vendorList;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                dataBase.addAllVendors(vendorList);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    @Override
    public void onFailure(Call<VendorDataList> call, Throwable t) {
        loadingView.show();
        Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }


    public class root_start extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String root_date;
        String start_location;
        double currentLongitude;
        double currentLatitude;


        public root_start(String root_date, String start_location, double currentLongitude, double currentLatitude) {


            this.root_date = root_date;
            this.currentLongitude = currentLongitude;
            this.currentLatitude = currentLatitude;
            this.start_location = start_location;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(HomeActivity.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("start_time", root_date));
                Log.e("root_date", "" + root_date);
                parameters.add(new BasicNameValuePair("start_latitude", String.valueOf(currentLatitude)));
                Log.e("start_latitude", "" + String.valueOf(currentLatitude));
                parameters.add(new BasicNameValuePair("start_longitude", String.valueOf(currentLongitude)));
                Log.e("start_longitude", "" + String.valueOf(currentLongitude));
                parameters.add(new BasicNameValuePair("start_location", start_location));
                parameters.add(new BasicNameValuePair("device_id", firebase_token));
                parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));


                Log.e("parameters", "---->" + parameters);


                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.ROUTE_START, ServiceHandler.POST, parameters);

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
                Toast.makeText(HomeActivity.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {


                Log.e("json", json);


                try {


                    JSONObject object = new JSONObject(json);
                    boolean status = object.getBoolean("status");

                    if (status) {


                        String route_id = object.getString("route_id");
                        prefs.setVisiable("1");
                        prefs.setRouteEnded(true);
                        prefs.setRoute_id(object.getString("route_id"));
                        prefs.setRouteStarted(true);


                        Log.e("route_id", "" + route_id);

                        loadingView.dismiss();


                    } else {

                    }

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());

                }
            }

        }


    }

    public class start_meeting extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String root_date;
        String start_map;
        double currentLongitude;
        double currentLatitude;
        String currentTime = "";

        public start_meeting(String root_date, String start_map, double currentLongitude, double currentLatitude) {
            this.root_date = root_date;
            this.start_map = start_map;
            this.currentLongitude = currentLongitude;
            this.currentLatitude = currentLatitude;
        }

        public start_meeting(String root_date, String start_map, double currentLongitude, double currentLatitude, String currentTime) {
            this.root_date = root_date;
            this.start_map = start_map;
            this.currentLongitude = currentLongitude;
            this.currentLatitude = currentLatitude;
            this.currentTime = currentTime;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(HomeActivity.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {


            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
                Log.e("user_id", "" + prefs.getUser_id());
                parameters.add(new BasicNameValuePair("vendor_id", prefs.getVendor_id()));
                Log.e("vendor_id", "" + prefs.getVendor_id());
                parameters.add(new BasicNameValuePair("route_id", prefs.getRoute_id()));
                Log.e("route_id", "" + prefs.getRoute_id());
                parameters.add(new BasicNameValuePair("start_time", root_date));
                Log.e("meeting_start_time", "" + root_date);
                parameters.add(new BasicNameValuePair("start_latitude", String.valueOf(currentLatitude)));
                Log.e("start_latitude", "" + String.valueOf(currentLatitude));
                parameters.add(new BasicNameValuePair("start_longitude", String.valueOf(currentLongitude)));
                Log.e("start_longitude", "" + String.valueOf(currentLongitude));
                parameters.add(new BasicNameValuePair("start_location", start_map));
                Log.e("start_location", "" + start_map);

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.START_MEETING, ServiceHandler.POST, parameters);
                Log.e("json", json);
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
                Toast.makeText(HomeActivity.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {


                Log.e("json", json);


                try {


                    JSONObject object = new JSONObject(json);
                    boolean status = object.getBoolean("status");

                    if (status) {


                        String meeting_id = object.getString("meeting_id");
                        prefs.setMeetings_id(object.getString("meeting_id"));
                        prefs.setMeetingStartTime(currentTime);
                        prefs.setMeetingStarted(true);
                        Log.e("meeting_id", "" + meeting_id);

                        loadingView.dismiss();


                    } else {


                        Toast.makeText(getApplicationContext(), object.getString("Please Check the user id or password"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());

                }
            }

        }


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
        double currentLongitude;
        double currentLatitude;
        int count = layout_cemara.getChildCount();

        public end_meeting(String root_date, String comments, int meetings_type1, String end_map, double currentLongitude, double currentLatitude) {
            this.root_date = root_date;
            this.comments = comments;
            this.end_map = end_map;
            this.meetings_type1 = meetings_type1;
            this.currentLongitude = currentLongitude;
            this.currentLatitude = currentLatitude;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(HomeActivity.this, "");

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
                prefs.setMeeting_type(String.valueOf(meetings_type1));
                Log.e("meeting_type1", prefs.getMeeting_type());

                parameters.add(new BasicNameValuePair("end_latitude", String.valueOf(currentLatitude)));
                Log.e("end_latitude", "" + String.valueOf(currentLatitude));

                parameters.add(new BasicNameValuePair("end_longitude", String.valueOf(currentLongitude)));
                Log.e("end_longitude", "" + String.valueOf(currentLongitude));

                if (count == 1) {

                    if (imagepath1.isEmpty()) {

                        parameters.add(new BasicNameValuePair("attachments1", prefs.getImagePath1()));
                        Log.e("attachments1", "" + prefs.getImagePath1());

                    } else if (!imagepath1.isEmpty()) {

                        parameters.add(new BasicNameValuePair("attachments1", imagepath1));
                        Log.e("attachments1", "" + imagepath1);
                    }


                } else if (count == 2) {
                    if (imagepath1.isEmpty()) {

                        parameters.add(new BasicNameValuePair("attachments1", prefs.getImagePath1()));
                        Log.e("attachments1", "" + prefs.getImagePath1());

                    } else if (!imagepath1.isEmpty()) {

                        parameters.add(new BasicNameValuePair("attachments1", imagepath1));
                        Log.e("attachments1", "" + imagepath1);
                    }


                    if (imagepath2.isEmpty()) {

                        parameters.add(new BasicNameValuePair("attachments2", prefs.getImagePath2()));
                        Log.e("attachments2", "" + prefs.getImagePath2());
                    } else if (!imagepath2.isEmpty()) {

                        parameters.add(new BasicNameValuePair("attachments2", imagepath2));
                        Log.e("attachments2", "" + imagepath2);

                    }


                } else if (count == 3) {

                    if (imagepath1.isEmpty()) {

                        parameters.add(new BasicNameValuePair("attachments1", prefs.getImagePath1()));
                        Log.e("attachments1", "" + prefs.getImagePath1());

                    } else if (!imagepath1.isEmpty()) {

                        parameters.add(new BasicNameValuePair("attachments1", imagepath1));
                        Log.e("attachments1", "" + imagepath1);
                    }


                    if (imagepath2.isEmpty()) {

                        parameters.add(new BasicNameValuePair("attachments2", prefs.getImagePath2()));
                        Log.e("attachments2", "" + prefs.getImagePath2());
                    } else if (!imagepath2.isEmpty()) {

                        parameters.add(new BasicNameValuePair("attachments2", imagepath2));
                        Log.e("attachments2", "" + imagepath2);

                    }


                    if (imagepath3.isEmpty()) {
                        parameters.add(new BasicNameValuePair("attachments3", prefs.getImagePathe3()));
                        Log.e("attachments3", "" + prefs.getImagePathe3());
                    } else if (!imagepath3.isEmpty()) {
                        parameters.add(new BasicNameValuePair("attachments3", imagepath3));
                        Log.e("attachments3", "" + imagepath3);
                    }


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
                Toast.makeText(HomeActivity.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {


                Log.e("json", json);


                try {


                    JSONObject object = new JSONObject(json);
                    boolean status = object.getBoolean("status");

                    if (status) {

                        prefs = new AppPref(HomeActivity.this);
                        prefs.setMeetingStarted(false);
                        prefs.setPlaceorderclicked(false);
                        prefs.setVisiable("1");
                        prefs.setRouteEnded(false);
                        prefs.setComment("");

                        String image1 = prefs.getImagePath1();
                        String image2 = prefs.getImagePath2();
                        String image3 = prefs.getImagePathe3();
                        if (!image1.isEmpty()) {

                            prefs.setImagePath1("");

                        }
                        if (!image2.isEmpty()) {

                            prefs.setImagePath2("");
                        }
                        if (!image3.isEmpty()) {

                            prefs.setImagePathe3("");

                        }


                        Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        String Message = object.getString("message");
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                        loadingView.dismiss();


                    } else {

                        String Message = object.getString("message");
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                        loadingView.dismiss();


                    }

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());

                }

            }

        }


    }

    public class root_end extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String root_date;
        double currentLongitude;
        double currentLatitude;
        String end_location;

        public root_end(String root_date, String end_location, double currentLongitude, double currentLatitude) {
            this.root_date = root_date;
            this.end_location = end_location;
            this.currentLongitude = currentLongitude;
            this.currentLatitude = currentLatitude;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(HomeActivity.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("id", prefs.getId()));
                Log.e("id", "" + prefs.getId());
                parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
                Log.e("user_id", "" + prefs.getUser_id());
                parameters.add(new BasicNameValuePair("id", prefs.getRoute_id()));
                Log.e("end_route_id", "" + prefs.getRoute_id());
                parameters.add(new BasicNameValuePair("end_time", root_date));
                Log.e("end_end_time", "" + root_date);
                parameters.add(new BasicNameValuePair("end_latitude", String.valueOf(currentLatitude)));
                Log.e("end_end_latitude", "" + String.valueOf(currentLatitude));
                parameters.add(new BasicNameValuePair("end_longitude", String.valueOf(currentLongitude)));
                Log.e("end_end_longitude", "" + String.valueOf(currentLongitude));
                parameters.add(new BasicNameValuePair("end_location", end_location));
                Log.e("end_location", "" + end_location);


                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.ROUTE_END, ServiceHandler.POST, parameters);

                return json;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: " + e.toString());

            }

            return json;

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                System.out.println(result_1);
                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(HomeActivity.this, "SERVER ERROR", Toast.LENGTH_LONG).show();
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    Log.e("status", "" + date);
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                        loadingView.dismiss();
                    } else {
                        Log.e("status", "" + date);
                        String Message = jObj.getString("message");
                        prefs.setVisiable("0");
                        prefs.setRouteEnded(true);
                        prefs.setRouteStarted(false);
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();

                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("excePtioN", j.getMessage());
            }

        }

    }

    public class root_logout_end extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String root_date;
        double currentLongitude;
        double currentLatitude;
        String end_location;

        public root_logout_end(String root_date, String end_location, double currentLongitude, double currentLatitude) {
            this.root_date = root_date;
            this.end_location = end_location;
            this.currentLongitude = currentLongitude;
            this.currentLatitude = currentLatitude;

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("id", prefs.getId()));
                Log.e("id", "" + prefs.getId());
                parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
                Log.e("user_id", "" + prefs.getUser_id());
                parameters.add(new BasicNameValuePair("id", prefs.getRoute_id()));
                Log.e("end_route_id", "" + prefs.getRoute_id());
                parameters.add(new BasicNameValuePair("end_time", root_date));
                Log.e("end_end_time", "" + root_date);
                parameters.add(new BasicNameValuePair("end_latitude", String.valueOf(currentLatitude)));
                Log.e("end_end_latitude", "" + String.valueOf(currentLatitude));
                parameters.add(new BasicNameValuePair("end_longitude", String.valueOf(currentLongitude)));
                Log.e("end_end_longitude", "" + String.valueOf(currentLongitude));
                parameters.add(new BasicNameValuePair("end_location", end_location));
                Log.e("end_location", "" + end_location);

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.ROUTE_END, ServiceHandler.POST, parameters);

                return json;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: " + e.toString());

            }

            return json;

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                System.out.println(result_1);
                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(HomeActivity.this, "SERVER ERROR", Toast.LENGTH_LONG).show();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    Log.e("status", "" + date);
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("status", "" + date);
                        String Message = jObj.getString("message");
                        prefs.setVisiable("0");


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("excePtioN", j.getMessage());
            }

        }

    }

    public class root_onresume_end extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String root_date;
        double currentLongitude;
        double currentLatitude;
        String end_location;

        public root_onresume_end(String root_date, String end_location, double currentLongitude, double currentLatitude) {
            this.root_date = root_date;
            this.end_location = end_location;
            this.currentLongitude = currentLongitude;
            this.currentLatitude = currentLatitude;

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("id", prefs.getId()));
                Log.e("id", "" + prefs.getId());
                parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
                Log.e("user_id", "" + prefs.getUser_id());
                parameters.add(new BasicNameValuePair("id", prefs.getRoute_id()));
                Log.e("end_route_id", "" + prefs.getRoute_id());
                parameters.add(new BasicNameValuePair("end_time", root_date));
                Log.e("end_end_time", "" + root_date);
                parameters.add(new BasicNameValuePair("end_latitude", String.valueOf(currentLatitude)));
                Log.e("end_end_latitude", "" + String.valueOf(currentLatitude));
                parameters.add(new BasicNameValuePair("end_longitude", String.valueOf(currentLongitude)));
                Log.e("end_end_longitude", "" + String.valueOf(currentLongitude));
                parameters.add(new BasicNameValuePair("end_location", end_location));
                Log.e("end_location", "" + end_location);

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.ROUTE_END, ServiceHandler.POST, parameters);

                return json;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: " + e.toString());

            }

            return json;

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                System.out.println(result_1);
                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(HomeActivity.this, "SERVER ERROR", Toast.LENGTH_LONG).show();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    Log.e("status", "" + date);
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("status", "" + date);
                        String Message = jObj.getString("message");
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                        prefs.setVisiable("0");
                        prefs.setOnceclicked(false);
                        prefs.setRouteStarted(false);
                        prefs.setComment("");

                        vendor.setVisibility(View.GONE);
                        new_vendor.setVisibility(View.GONE);
                        start_time.setText("");
                        route_start.setGravity(Gravity.CENTER);
                        route_end.setEnabled(false);
                        route_end.setTextColor(Color.parseColor("#e1e1e1"));
                        route_start.setEnabled(true);
                        route_start.setBackgroundColor(Color.parseColor("#ffffff"));
                        last_time.setText("");
                        route_end.setEnabled(false);
                        route_end.setGravity(Gravity.CENTER);
                        route_end.setBackgroundColor(Color.parseColor("#ffffff"));
                        route_end.setTextColor(Color.parseColor("#e1e1e1"));
                        btn_start_meeting.setVisibility(View.GONE);
                        lists.setVisibility(View.GONE);
                        txt_search_by_person.setVisibility(View.GONE);
                        txt_search_by_company.setVisibility(View.GONE);


                        Date d2 = new Date();
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                        final String currentDateTimeString2 = sdf2.format(d2);

                        txt_date.setText(currentDateTimeString2);

                        Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("excePtioN", j.getMessage());
            }

        }

    }

    public class meeting_check extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(HomeActivity.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                String date;
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
                date = df.format(c.getTime());

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
                Log.e("user_id", prefs.getUser_id());
                parameters.add(new BasicNameValuePair("Date", date));
                Log.e("formattedDate", "" + date);

                Log.e("params", parameters.toString());


                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.MEETING_CHECK, ServiceHandler.POST, parameters);

                Log.e("json", "->" + json);

                return json;


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: " + e.toString());

                Log.e("Exception", e.getMessage());

                return null;
            }


        }


        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            loadingView.dismiss();
            Log.e("myJSON", "->" + json);

            if (json == null || json.isEmpty()) {

                Toast.makeText(HomeActivity.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("json", json);

                try {

                    JSONObject object = new JSONObject(json);

                    boolean status = object.getBoolean("status");
                    String message = "";
                    object.getString("message" + message);


                    if (status) {
                        JSONArray dataArray = object.getJSONArray("data");


                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject main = dataArray.getJSONObject(i);
                            JSONObject jobject_meeting_history = main.getJSONObject("Meeting");


                            BeanVendorName bean_historymeeting = new BeanVendorName();


                            bean_historymeeting.setId(jobject_meeting_history.getString("id"));
                            bean_historymeeting.setUser_id(jobject_meeting_history.getString("user_id"));
                            bean_historymeeting.setVendor_id(jobject_meeting_history.getString("vendor_id"));
                            bean_historymeeting.setRoute_id(jobject_meeting_history.getString("route_id"));
                            bean_historymeeting.setMeeting_type(jobject_meeting_history.getString("meeting_type"));

                            bean_historymeeting.setStart_time(jobject_meeting_history.getString("start_time"));
                            bean_historymeeting.setEnd_time(jobject_meeting_history.getString("end_time"));
                            bean_historymeeting.setStart_latitude(jobject_meeting_history.getString("start_latitude"));
                            Log.e("Start", "" + jobject_meeting_history.getString("start_latitude"));
                            prefs.setStart_lat(jobject_meeting_history.getString("start_latitude"));
                            bean_historymeeting.setStart_longitude(jobject_meeting_history.getString("start_longitude"));
                            Log.e("Start1", "" + jobject_meeting_history.getString("start_longitude"));
                            prefs.setStart_long(jobject_meeting_history.getString("start_longitude"));

                            bean_historymeeting.setEnd_longitude(jobject_meeting_history.getString("end_longitude"));
                            // prefs.setEnd_long(jobject_meeting_history.getString("end_longitude"));
                            Log.e("end1", "" + jobject_meeting_history.getString("end_longitude"));

                            bean_historymeeting.setEnd_latitude(jobject_meeting_history.getString("end_latitude"));
                            //prefs.setEnd_lat(jobject_meeting_history.getString("end_latitude"));
                            Log.e("end", "" + jobject_meeting_history.getString("end_latitude"));

                            bean_historymeeting.setComments(jobject_meeting_history.getString("comments"));

                            ArrayList<String> attachments = new ArrayList<>();

                            attachments.add(Web.IMAGELINK2 + jobject_meeting_history.getString("attachments1"));
                            attachments.add(Web.IMAGELINK2 + jobject_meeting_history.getString("attachments2"));
                            attachments.add(Web.IMAGELINK2 + jobject_meeting_history.getString("attachments3"));

                            bean_historymeeting.setAttachmentPaths(attachments);


                            bean_historymeeting.setAttachments1(jobject_meeting_history.getString("attachments1"));
                            bean_historymeeting.setAttachments2(jobject_meeting_history.getString("attachments2"));
                            bean_historymeeting.setAttachments3(jobject_meeting_history.getString("attachments3"));
                            Log.e("Attach1111111111", "" + jobject_meeting_history.getString("attachments1"));
                            bean_historymeeting.setStart_location(jobject_meeting_history.getString("start_location"));
                            bean_historymeeting.setEnd_location(jobject_meeting_history.getString("end_location"));

                            JSONObject jobject_company_profile = main.getJSONObject("CompanyProfile");


                            bean_historymeeting.setEmail_id(jobject_company_profile.getString("email_id"));
                            bean_historymeeting.setPhone_no(jobject_company_profile.getString("phone_no"));
                            bean_historymeeting.setRole_id(jobject_company_profile.getString("role_id"));
                            bean_historymeeting.setFirst_name(jobject_company_profile.getString("first_name"));
                            bean_historymeeting.setLast_name(jobject_company_profile.getString("last_name"));
                            bean_historymeeting.setMiddle_name(jobject_company_profile.getString("middle_name"));
                            Log.e("first name", "" + jobject_company_profile.getString("first_name"));


                            JSONObject jsonObject_company = jobject_company_profile.getJSONObject("Distributor");

                            bean_historymeeting.setCompany_name(jsonObject_company.getString("firm_shop_name"));
                            prefs.setFirm_shop_name(jsonObject_company.getString("firm_shop_name"));
                            CompanyName = bean_historymeeting.getCompany_name();
                            Log.e("firm_shop_name", bean_historymeeting.getCompany_name());

                            JSONArray Order = main.getJSONArray("Order");
                            if (Order.length() > 0) {
                                JSONObject jsonObject = Order.getJSONObject(0);
                                bean_historymeeting.setMeetingID(jsonObject.getString("meeting_id"));
                                bean_historymeeting.setHasOrders(true);
                            } else {
                                bean_historymeeting.setHasOrders(false);
                            }


                            String endTime = bean_historymeeting.getEnd_time().toString();
                            String[] endTimeArray = endTime.split(" ");
                            String[] timingArray = endTimeArray[1].split(":");

                            int count = 0;

                            for (int j = 0; j < timingArray.length; j++) {
                                if (Integer.parseInt(timingArray[j]) == 0)
                                    count++;
                            }

                            if (count < 3) {
                                v_names.add(bean_historymeeting);
                            }


                        }


                        Log.e("array_companylist", "" + v_names.size());
                        for (int i = 0; i < v_names.size(); i++) {
                            Log.e("ABCABA", "" + v_names.get(i).getFirst_name().toString());
                        }


                        vendorListAdapter = new VendorListAdapter(getApplicationContext(), v_names, HomeActivity.this);
                        vendorListAdapter.notifyDataSetChanged();
                        meeting_list.setAdapter(vendorListAdapter);


                        if (v_names.size() == 0) {


                            view_list.setVisibility(View.GONE);
                            meeting_list.setVisibility(View.GONE);

                            Log.e("list", "gone");
                        } else {

                            view_list.setVisibility(View.VISIBLE);
                            meeting_list.setVisibility(View.VISIBLE);
                            Log.e("list", "visible");
                        }


                    } else {
                        view_list.setVisibility(View.GONE);
                        Log.e("Message Print", "2661-->" + object.getString("message"));
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException j) {
                    j.printStackTrace();
                    Log.e("excePtioN", j.getMessage());
                }

            }

        }


    }


    public class endResume_meeting extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String root_date;
        String comments;
        String end_map;
        int meetings_type1;
        double currentLongitude;
        double currentLatitude;
        int count = layout_cemara.getChildCount();

        public endResume_meeting(String root_date, String comments, int meetings_type1, String end_map, double currentLongitude, double currentLatitude) {
            this.root_date = root_date;
            this.comments = comments;
            this.end_map = end_map;
            this.meetings_type1 = meetings_type1;
            this.currentLongitude = currentLongitude;
            this.currentLatitude = currentLatitude;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(HomeActivity.this, "");

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
                prefs.setMeeting_type(String.valueOf(meetings_type1));
                Log.e("meeting_type1", prefs.getMeeting_type());

                parameters.add(new BasicNameValuePair("end_latitude", String.valueOf(currentLatitude)));
                Log.e("end_latitude", "" + String.valueOf(currentLatitude));

                parameters.add(new BasicNameValuePair("end_longitude", String.valueOf(currentLongitude)));
                Log.e("end_longitude", "" + String.valueOf(currentLongitude));

                if (count == 1) {
                    parameters.add(new BasicNameValuePair("attachments1", imagepath1));
                    Log.e("attachments1", "" + imagepath1);
                } else if (count == 2) {
                    parameters.add(new BasicNameValuePair("attachments1", imagepath1));
                    Log.e("attachments1", "" + imagepath1);
                    parameters.add(new BasicNameValuePair("attachments2", imagepath2));
                    Log.e("attachments2", "" + imagepath2);
                } else if (count == 3) {
                    parameters.add(new BasicNameValuePair("attachments1", imagepath1));
                    Log.e("attachments1", "" + imagepath1);
                    parameters.add(new BasicNameValuePair("attachments2", imagepath2));
                    Log.e("attachments2", "" + imagepath2);
                    parameters.add(new BasicNameValuePair("attachments3", imagepath3));
                    Log.e("attachments3", "" + imagepath3);
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
                Toast.makeText(HomeActivity.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {


                Log.e("json", json);


                try {


                    JSONObject object = new JSONObject(json);
                    boolean status = object.getBoolean("status");

                    if (status) {

                        String Message = object.getString("message");
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                        prefs = new AppPref(HomeActivity.this);
                        prefs.setMeetingStarted(false);
                        prefs.setPlaceorderclicked(false);
                        prefs.setVisiable("1");
                        prefs.setRouteEnded(false);
                        prefs.setOnceclicked(false);
                        Log.e("lat", "" + prefs.getRoute_startedLattitude());
                        Log.e("long", "" + prefs.getRoute_startedLongitude());


                        String end_location = "http://maps.google.com/maps?q=loc:" + "" + prefs.route_startedLattitude + "," + prefs.route_startedLongitude;

                        new root_onmeetingresume_end(root_date, end_location, prefs.route_startedLattitude, prefs.route_startedLongitude).execute();


                        loadingView.dismiss();


                    } else {

                        String Message = object.getString("message");
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                        loadingView.dismiss();


                    }

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());

                }

            }

        }


    }

    public class root_onmeetingresume_end extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String root_date;
        double currentLongitude;
        double currentLatitude;
        String end_location;

        public root_onmeetingresume_end(String root_date, String end_location, double currentLongitude, double currentLatitude) {
            this.root_date = root_date;
            this.end_location = end_location;
            this.currentLongitude = currentLongitude;
            this.currentLatitude = currentLatitude;

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("id", prefs.getId()));
                Log.e("id", "" + prefs.getId());
                parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
                Log.e("user_id", "" + prefs.getUser_id());
                parameters.add(new BasicNameValuePair("id", prefs.getRoute_id()));
                Log.e("end_route_id", "" + prefs.getRoute_id());
                parameters.add(new BasicNameValuePair("end_time", root_date));
                Log.e("end_end_time", "" + root_date);
                parameters.add(new BasicNameValuePair("end_latitude", String.valueOf(currentLatitude)));
                Log.e("end_end_latitude", "" + String.valueOf(currentLatitude));
                parameters.add(new BasicNameValuePair("end_longitude", String.valueOf(currentLongitude)));
                Log.e("end_end_longitude", "" + String.valueOf(currentLongitude));
                parameters.add(new BasicNameValuePair("end_location", end_location));
                Log.e("end_location", "" + end_location);

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.ROUTE_END, ServiceHandler.POST, parameters);

                return json;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: " + e.toString());

            }

            return json;

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                System.out.println(result_1);
                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(HomeActivity.this, "SERVER ERROR", Toast.LENGTH_LONG).show();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    Log.e("status", "" + date);
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("status", "" + date);
                        String Message = jObj.getString("message");
                        Toast.makeText(HomeActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                        prefs.setVisiable("0");
                        prefs.setRouteStarted(false);
                        vendor.setVisibility(View.GONE);
                        new_vendor.setVisibility(View.GONE);
                        start_time.setText("");
                        route_start.setGravity(Gravity.CENTER);
                        route_end.setEnabled(false);
                        route_end.setTextColor(Color.parseColor("#e1e1e1"));
                        route_start.setEnabled(true);
                        route_start.setBackgroundColor(Color.parseColor("#ffffff"));
                        last_time.setText("");
                        route_end.setEnabled(false);
                        route_end.setGravity(Gravity.CENTER);
                        route_end.setBackgroundColor(Color.parseColor("#ffffff"));
                        route_end.setTextColor(Color.parseColor("#e1e1e1"));
                        btn_start_meeting.setVisibility(View.GONE);
                        lists.setVisibility(View.GONE);
                        txt_search_by_person.setVisibility(View.GONE);
                        txt_search_by_company.setVisibility(View.GONE);


                        Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("excePtioN", j.getMessage());
            }

        }

    }


    private boolean checkPlayServices() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {

            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {

                Toast.makeText(this, "Get Data", Toast.LENGTH_SHORT).show();
            } else {


                Toast.makeText(this, "Doesnt Able", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;

    }

    private void startFusedLocation() {

        if (mGoogleApiClient == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(@Nullable Bundle bundle) {

                }

                @Override
                public void onConnectionSuspended(int i) {

                }
            }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                }
            }).build();
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.connect();
        }
    }

    private void registerRequestUpdate(final LocationListener listener) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {

                    if (ActivityCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);


                } catch (SecurityException e) {

                    e.printStackTrace();
                } catch (Exception e) {

                    e.printStackTrace();
                    if (!isGoogleApiClientConnected()) {

                        mGoogleApiClient.connect();
                    }

                    registerRequestUpdate(listener);
                }


            }
        }, 1000);


    }

    private boolean isGoogleApiClientConnected() {

        return mGoogleApiClient != null && mGoogleApiClient.isConnected();

    }

    @Override
    public void onLocationChanged(Location location) {

        setCurrentLatitude(location.getLatitude());
        setCurrentLongitude(location.getLongitude());


    }

    @Override
    protected void onStop() {
        super.onStop();
        stopFusedLocation();
    }

    private void stopFusedLocation() {

        if (mGoogleApiClient != null) {

            mGoogleApiClient.disconnect();
        }


    }


    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    private void displayLocationSettingRequest(Context context) {


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("", "All location settings are satisfied.");

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });


    }
}






