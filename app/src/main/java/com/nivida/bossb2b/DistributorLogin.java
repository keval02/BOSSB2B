package com.nivida.bossb2b;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.BeanVendor;
import com.nivida.bossb2b.Model.APIServices;
import com.nivida.bossb2b.Model.ListData;
import com.nivida.bossb2b.Model.ServiceGenerator;
import com.nivida.bossb2b.Model.VendorDataList;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributorLogin extends AppCompatActivity implements Callback<VendorDataList>, NavigationView.OnNavigationItemSelectedListener {

    private static final int CAMERA_REQUEST = 1888;
    public static final int CAMERA_DIRECT = 2;

    private String imgPath = "";
    String imagepath1 = "";
    String imagepath2 = "";
    String imagepath3 = "";


    Button btn_own, btn_retailer;

    EditText txt_search_by_company, edit_comment;

    ListView lists;

    Toolbar toolbar;

    ImageView img_refersh, tool_grid, img_camera;

    AppPref prefs;

    ProgressActivity loadingView;

    LinearLayout lv_search, list_hide;

    List<BeanVendor> vendornames = new ArrayList<BeanVendor>();

    ListData dataBase;

    APIServices apiServices;

    ListAdapter listAdapter;

    NavigationView myNavigationview;
    Menu menus;


    DrawerLayout drawer;

    ActionBarDrawerToggle actionBarDrawerToggle;

    DatabaseHandler db1;

    Database db;

    TextView txt_date;

    String topDate;

    String previousId = "", currentId = "";

    LinearLayout layout_cemara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_login);

        fetchId();
    }

    private void fetchId() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lists = (ListView) findViewById(R.id.lists);
        txt_search_by_company = (EditText) findViewById(R.id.txt_searchby_company);
        btn_own = (Button) findViewById(R.id.btn_own);
        btn_retailer = (Button) findViewById(R.id.btn_retailer);
        img_refersh = (ImageView) findViewById(R.id.img_refersh);

        tool_grid = (ImageView) findViewById(R.id.tool_grid);
        tool_grid.setVisibility(View.GONE);

        prefs = new AppPref(getApplicationContext());
        lv_search = (LinearLayout) findViewById(R.id.lv_search);
        dataBase = new ListData(getApplicationContext());
        db = new Database(getApplicationContext());
        db1 = new DatabaseHandler(getApplicationContext());
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        list_hide = (LinearLayout) findViewById(R.id.list_hide);
        txt_date = (TextView) findViewById(R.id.txt_date);

        img_camera = (ImageView) findViewById(R.id.img_camera);
        layout_cemara = (LinearLayout) findViewById(R.id.layout_cemara);
        edit_comment = (EditText) findViewById(R.id.edit_comment);


        apiServices = ServiceGenerator.getServiceClass();

        myNavigationview = (NavigationView) findViewById(R.id.nav_drawer);
        myNavigationview.setNavigationItemSelectedListener(this);

        menus = myNavigationview.getMenu();

        MenuItem dashboardmenu = menus.findItem(R.id.daseboard);
        MenuItem meetingHistory = menus.findItem(R.id.retailer);
        MenuItem history = menus.findItem(R.id.history);


        if (prefs.getRole_id().equalsIgnoreCase(C.DISTRIBUTOR_ROLE)) {
            dashboardmenu.setVisible(false);

            meetingHistory.setTitle("Retailer Order History");
            history.setTitle("Own Order History");


            //meetingHistory.setVisible(false);
            // history.setVisible(false);
        }


        final Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        topDate = df.format(c.getTime());


        txt_date.setText("" + topDate);


        loadingView = new ProgressActivity(DistributorLogin.this, "");
        loadingView.setCancelable(false);

        listAdapter = new ListAdapter(getApplicationContext(), vendornames);
        lists.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();


        btn_retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Internet.isConnectingToInternet(getApplicationContext())) {

                    btn_own.setBackgroundColor(Color.parseColor("#ffffff"));
                    btn_retailer.setBackgroundColor(Color.parseColor("#7BC552"));

                    lists.setVisibility(View.VISIBLE);
                    list_hide.setVisibility(View.VISIBLE);
                    img_refersh.setVisibility(View.VISIBLE);

                    btn_retailer.setEnabled(true);
                    // btn_new_start_meeting.setVisibility(View.GONE);


                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String date = simpleDateFormat.format(calendar.getTime());

                    prefs.setCurrent_date(date);

                    Log.e("ServiceCalled", "-->" + prefs.isOnceclicked());
                    Log.e("UserID", "-->" + prefs.getUser_id());

                    if (prefs.isOnceclicked() == false) {


                        if (prefs.getRole_id().equalsIgnoreCase(C.DISTRIBUTOR_ROLE)) {

                            loadingView.show();
                            Call<VendorDataList> dataListCall = apiServices.retailersList(prefs.getUser_id());
                            dataListCall.enqueue(DistributorLogin.this);
                        }


                    } else {
                        vendornames = dataBase.getAllData();
                        listAdapter = new ListAdapter(getApplicationContext(), vendornames);
                        lists.setAdapter(listAdapter);
                        lv_search.setVisibility(View.VISIBLE);
                        txt_search_by_company.setVisibility(View.VISIBLE);
                        listAdapter.notifyDataSetChanged();
                    }
                } else {
                    Internet.noInternet(getApplicationContext());
                }


            }
        });

        previousId = prefs.getOnSelectedUserId();

        btn_own.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String comment = edit_comment.getText().toString().trim();
                prefs.setDisComment(comment);


                if (!imagepath1.isEmpty()) {
                    prefs.setDisImage1(imagepath1);
                }
                if (!imagepath2.isEmpty()) {
                    prefs.setDisImage2(imagepath2);

                }
                if (!imagepath3.isEmpty()) {
                    prefs.setDisImage3(imagepath3);
                }

                Log.e("Imagepath1", "-->" + imagepath1);
                Log.e("Imagepath2", "-->" + imagepath2);
                Log.e("Imagepath3", "-->" + imagepath3);

                Log.e("Imagepath11", "-->" + prefs.getDisImage1());
                Log.e("Imagepath22", "-->" + prefs.getDisImage2());
                Log.e("Imagepath33", "-->" + prefs.getDisImage3());

                Log.e("count", "-->" + layout_cemara.getChildCount());


                Intent intent = new Intent(getApplicationContext(), PlaceOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                prefs.setDistributorLogin(true);
                prefs.setListClicked(false);
                prefs.setOnSelectedFirmName(prefs.getFirmShopName());
                prefs.setOnSelectedUserId(prefs.getUser_id());

                currentId = prefs.getOnSelectedUserId();

                if (previousId != currentId) {

                    db.removeFromCart(null);
                }

                Log.e("UserID", prefs.getUser_id());

                startActivity(intent);
                finish();
            }
        });

        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                List<BeanVendor> currentLists = listAdapter.getCurrentRetailers();
                txt_search_by_company.setText("");
                String itemValue = currentLists.get(position).getCompany_name();
                String Id = currentLists.get(position).getUser_id();

                prefs.setOnSelectedUserId(Id);

                Log.e("ID", "" + id);
                prefs.setSelectedVendorName(itemValue);
                prefs.setListClicked(true);


                //  listAdapter.notifyDataSetChanged();
                // list_hide.setVisibility(View.GONE);

                //lv_search.setVisibility(View.GONE);

                prefs.setOnSelectedFirmName(itemValue);


                currentId = prefs.getOnSelectedUserId();

                if (previousId != currentId) {

                    db.removeFromCart(null);
                }


                prefs.setVendor_id(currentLists.get(position).getVendor_id());
                Log.e("vendor_id", currentLists.get(position).getVendor_id());
                prefs.setSelectedUserRole(currentLists.get(position).getRole_id());

                String comment = edit_comment.getText().toString().trim();
                prefs.setDisComment(comment);


                if (!imagepath1.isEmpty()) {
                    prefs.setDisImage1(imagepath1);
                }
                if (!imagepath2.isEmpty()) {
                    prefs.setDisImage2(imagepath2);

                }
                if (!imagepath3.isEmpty()) {
                    prefs.setDisImage3(imagepath3);
                }

                Log.e("Imagepath1", "-->" + imagepath1);
                Log.e("Imagepath2", "-->" + imagepath2);
                Log.e("Imagepath3", "-->" + imagepath3);

                Log.e("Imagepath11", "-->" + prefs.getDisImage1());
                Log.e("Imagepath22", "-->" + prefs.getDisImage2());
                Log.e("Imagepath33", "-->" + prefs.getDisImage3());

                Log.e("count", "-->" + layout_cemara.getChildCount());


                Intent intent = new Intent(getApplicationContext(), PlaceOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                //img_refersh.setVisibility(View.GONE);

                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(txt_search_by_company.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });


        img_refersh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prefs.getRole_id().equalsIgnoreCase(C.DISTRIBUTOR_ROLE)) {
                    loadingView.show();
                    Call<VendorDataList> dataListCall = apiServices.retailersList("4743");
                    dataListCall.enqueue(DistributorLogin.this);
                }


            }
        });

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


        txt_search_by_company.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(txt_search_by_company.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                return true;
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
                    Toast.makeText(DistributorLogin.this, "No Result Found", Toast.LENGTH_SHORT).show();

                listAdapter.updateData(retailerPersonListForSearch);


            }


        });

        img_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int count = layout_cemara.getChildCount();

                if (count <= 2) {
                    takeimage(CAMERA_REQUEST);
                } else {
                    Toast.makeText(DistributorLogin.this, "You Can Upload Max 3 photos", Toast.LENGTH_SHORT).show();
                }


            }
        });


        if (prefs.isListClicked() == true) {

            btn_own.setBackgroundColor(Color.parseColor("#ffffff"));
            btn_retailer.setBackgroundColor(Color.parseColor("#7BC552"));

            list_hide.setVisibility(View.VISIBLE);
            lists.setVisibility(View.VISIBLE);
            txt_search_by_company.setVisibility(View.VISIBLE);
            img_refersh.setVisibility(View.VISIBLE);


            vendornames = dataBase.getAllData();
            listAdapter = new ListAdapter(getApplicationContext(), vendornames);
            lists.setAdapter(listAdapter);
            lv_search.setVisibility(View.VISIBLE);
            listAdapter.notifyDataSetChanged();

        } else if (prefs.isListClicked() == false && prefs.isDistributorLogin() == true) {

            btn_retailer.setBackgroundColor(Color.parseColor("#ffffff"));
            btn_own.setBackgroundColor(Color.parseColor("#7BC552"));


            list_hide.setVisibility(View.GONE);
            lists.setVisibility(View.GONE);
            txt_search_by_company.setVisibility(View.GONE);

        } else {


            btn_retailer.setBackgroundColor(Color.parseColor("#ffffff"));
            btn_own.setBackgroundColor(Color.parseColor("#ffffff"));


            list_hide.setVisibility(View.GONE);
            lists.setVisibility(View.GONE);
            txt_search_by_company.setVisibility(View.GONE);


        }

        String comments = prefs.getDisComment();
        edit_comment.setText(comments);

        String image1 = prefs.getDisImage1();
        String image2 = prefs.getDisImage2();
        String image3 = prefs.getDisImage3();

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
            txt_search_by_company.setVisibility(View.VISIBLE);
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

            dataBase.addAllVendors(vendorList);

            Log.e("Vendors", "-->" + dataBase.getAllData().size());

            return null;
        }
    }

    @Override
    public void onFailure(Call<VendorDataList> call, Throwable t) {
        loadingView.show();
        Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.daseboard:

                break;


            case R.id.myaccount:

                Intent c = new Intent(DistributorLogin.this, User_Profile.class);
                c.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c);
                drawer.closeDrawer(GravityCompat.START);

                break;

            case R.id.contactus:

                Intent intent = new Intent(DistributorLogin.this, Contact.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

                break;

            case R.id.history:

                Intent h = new Intent(DistributorLogin.this, CompSalesDistretOrderHist.class);
                h.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(h);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.retailer:

                Intent j = new Intent(DistributorLogin.this, DistributorsRetailerHistory.class);
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

    public void logout() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(DistributorLogin.this);
        builder1.setMessage("Are you sure to logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        prefs.setSales_has_user_selected(false);
                        prefs = new AppPref(DistributorLogin.this);
                        prefs.setLoggedIn(false);
                        prefs.setVisiable("");
                        prefs.setMeetingStarted(false);
                        prefs.setOnceclicked(false);
                        prefs.setDistributorLogin(false);
                        prefs.setListClicked(false);
                        dataBase.deleteList();


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


    private void takeimage(int requestCode) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            String selectedImagePath = getImagePath();

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

                    if (prefs.getDisImage1().equalsIgnoreCase(txt_imagePath.getText().toString())) {
                        prefs.setDisImage1("");
                    } else if (prefs.getDisImage2().equalsIgnoreCase(txt_imagePath.getText().toString())) {
                        prefs.setDisImage2("");
                    } else if (prefs.getDisImage3().equalsIgnoreCase(txt_imagePath.getText().toString())) {
                        prefs.setDisImage3("");
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

}


