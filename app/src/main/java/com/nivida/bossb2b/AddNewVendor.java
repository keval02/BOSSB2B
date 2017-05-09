package com.nivida.bossb2b;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewVendor extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    public static final int CAMERA_DIRECT = 2;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private String imgPath = "";

    String imagepath1 = "";
    String imagepath2 = "";

    ImageView im_cemara_direct_icon, im_direct_cemara1;

    LinearLayout layout_direct_cemara;

    ProgressActivity loadingView;

    Spinner spn_readymade_country, spn_readymade_state, spn_ready_city, spn_ready_area, spn_distributor;


    ArrayList<String> disId = new ArrayList<String>();
    ArrayList<String> disName = new ArrayList<String>();
    ArrayList<String> countryid = new ArrayList<String>();
    ArrayList<String> countryname = new ArrayList<String>();
    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();
    ArrayList<String> areaid = new ArrayList<String>();
    ArrayList<String> areaname = new ArrayList<String>();

    String postion_distributor = new String();
    String position_country = new String();
    String position_state = new String();
    String position_city = new String();
    String position_area = new String();

    String postion_distributorname = new String();
    String position_countryname = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String position_areaname = new String();

    String position_dis_selected = "";
    ArrayList<String> dis_id = new ArrayList<>();

    String position_country_selected = "";
    ArrayList<String> country_id = new ArrayList<>();

    String position_state_selected = "";
    ArrayList<String> state_id = new ArrayList<>();

    String position_city_selected = "";
    ArrayList<String> city_id = new ArrayList<>();

    String position_area_selected = "";
    ArrayList<String> area_id = new ArrayList<>();


    String first_name, last_name, emailid, phone_no, landline, address, pincod, area, country, state, city, pass, firm_name, pan, vat, cst, vendortype, dis, otherarea, gst;
    AppPref prefs;
    String other_area_id = "";
    String json = "";

    ArrayAdapter<String> adapter_country;
    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> adapter_city;
    ArrayAdapter<String> adapter_area;


    RadioGroup rg_newtype;

    RadioButton rdo_distributor, rdo_retailer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vendor);

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

        im_cemara_direct_icon = (ImageView) findViewById(R.id.im_cemara_direct_icon);
        layout_direct_cemara = (LinearLayout) findViewById(R.id.layout_direct_cemara);


        prefs = new AppPref(getApplicationContext());

        im_cemara_direct_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int count = layout_direct_cemara.getChildCount();

                if (count < 1) {
                    takeimage(CAMERA_REQUEST);
                } else {
                    Toast.makeText(AddNewVendor.this, "You Can Upload Only One Visting Card.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //setting custom layout to dialog
        final EditText edit_first = (EditText) findViewById(R.id.edit_first);
        //  textedit1.setSelection(textedit1.getText().length());
        final EditText edit_last = (EditText) findViewById(R.id.edit_last);
        final EditText edit_email = (EditText) findViewById(R.id.edit_email);
        final EditText edit_phone_no = (EditText) findViewById(R.id.edit_phone_no);
        final EditText edit_landline_no = (EditText) findViewById(R.id.edit_landline_no);
        final EditText edit_pass = (EditText) findViewById(R.id.edit_pass);
        final EditText edit_address = (EditText) findViewById(R.id.edit_address);
        final EditText edit_address_one = (EditText) findViewById(R.id.edit_address_one);
        final EditText edit_firm_name = (EditText) findViewById(R.id.edit_firm_name);
        final EditText edit_pan = (EditText) findViewById(R.id.edit_pan);
        final EditText edit_vat = (EditText) findViewById(R.id.edit_vat);
        final EditText edit_cst = (EditText) findViewById(R.id.edit_cst);
        final EditText edit_gst = (EditText) findViewById(R.id.edit_gst);
        final EditText edit_other = (EditText) findViewById(R.id.edit_other);

        rg_newtype = (RadioGroup) findViewById(R.id.rg_newtype);

        rdo_distributor = (RadioButton) findViewById(R.id.rb_distributor);
        rdo_retailer = (RadioButton) findViewById(R.id.rb_retailer);

        rdo_retailer.setChecked(true);
        spn_readymade_country = (Spinner) findViewById(R.id.spn_readymade_country);
        spn_readymade_state = (Spinner) findViewById(R.id.spn_readymade_state);
        spn_ready_city = (Spinner) findViewById(R.id.spn_ready_city);
        spn_ready_area = (Spinner) findViewById(R.id.spn_ready_area);
        spn_distributor = (Spinner) findViewById(R.id.spn_distributor);
        final EditText edit_pcode = (EditText) findViewById(R.id.edit_pcode);

        final Button resetButton = (Button) findViewById(R.id.btn_textedit1_reset);
        // final Button skipbutton = (Button) findViewById(R.id.btn_textedit1_skip);
        final Button donebutton = (Button) findViewById(R.id.btn_textedit1_done);
        final Button btn_cancel = (Button) findViewById(R.id.btn_textedit1_cancel);


        spn_distributor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                postion_distributor = disId.get(position);
                postion_distributorname = disName.get(position);
                if (postion_distributor.equalsIgnoreCase("0")) {

                } else if (spn_distributor.getSelectedItemPosition() == 0) {


                } else {

                    position_dis_selected = dis_id.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_readymade_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                countryname.add("India");
                countryid.add("0");
                position_country = countryid.get(position);
                position_countryname = countryname.get(position);
                if (position_country.equalsIgnoreCase("0")) {
                    //Globals.CustomToast(Add_Newuser_Address.this,"Please Select State",getLayoutInflater());
                } else if (spn_readymade_country.getSelectedItemPosition() == 0) {
                    //Do nothing
                } else {

                    position_country_selected = country_id.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spn_readymade_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                Log.e("Selected Position", "->" + position);
                Log.e("Name Position", "->" + position + statename.get(position));

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if (position_statename.equalsIgnoreCase("0")) {

                } else if (spn_readymade_state.getSelectedItemPosition() == 0) {

                    spn_ready_city.setVisibility(View.GONE);
                    spn_ready_area.setVisibility(View.GONE);
                    spn_ready_city.setSelection(0);
                    spn_ready_area.setSelection(0);
                    other_area_id = "";


                } else {
                    position_state_selected = state_id.get(position - 1);
                    spn_ready_city.setVisibility(View.GONE);
                    spn_ready_area.setVisibility(View.GONE);

                    new send_readymade_city_Data().execute();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spn_ready_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.e("Selected Position", "->" + position);
                Log.e("Name Position", "->" + position + cityname.get(position));


                // TODO Auto-generated method stub

                // position1=""+position;
                position_city = cityid.get(position);
                position_cityname = cityname.get(position);
                if (position_city.equalsIgnoreCase("0")) {
                    spn_ready_area.setSelection(0);

                }
                if (spn_ready_city.getSelectedItemPosition() == 0) {
                    spn_ready_area.setVisibility(View.GONE);
                } else {
                    position_city_selected = cityid.get(position);
                    other_area_id = "";
                    spn_ready_area.setVisibility(View.GONE);
                    if (rdo_retailer.isChecked() || prefs.getRole_id().equalsIgnoreCase(C.DIS_SALES_ROLE)) {
                        new send_readymade_area_Data().execute();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spn_ready_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                // position1=""+position;

                Log.e("Selected Position", "->" + position);
                Log.e("Name Position", "->" + position + areaname.get(position));

                position_area = areaid.get(spn_ready_area.getSelectedItemPosition());
                position_areaname = areaname.get(spn_ready_area.getSelectedItemPosition());

                if (spn_ready_area.getSelectedItemPosition() == 1) {

                    selectOtherArea();
                }

                if (spn_ready_area.getSelectedItemPosition() != 0 && spn_ready_area.getSelectedItemPosition() != 1) {

                    position_area_selected = areaid.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_first.setText("");
                edit_last.setText("");
                edit_email.setText("");
                edit_phone_no.setText("");
                edit_pass.setText("");
                edit_address.setText("");
                edit_address_one.setText("");
                edit_pcode.setText("");
                edit_firm_name.setText("");
                edit_pan.setText("");
                edit_vat.setText("");
                edit_cst.setText("");
                edit_gst.setText("");
                spn_readymade_country.setSelection(0);
                spn_readymade_state.setSelection(0);
                spn_ready_city.setSelection(0);
                spn_ready_area.setSelection(0);
                spn_distributor.setSelection(0);
                rdo_distributor.setChecked(true);

                layout_direct_cemara.removeAllViews();


                // spn_ready_area.setSelection(0);
            }
        });


        if (!prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
            rg_newtype.setVisibility(View.GONE);
            spn_distributor.setVisibility(View.VISIBLE);
        }
        rg_newtype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rdo_retailer.isChecked()) {

                    spn_distributor.setVisibility(View.VISIBLE);

                    if (spn_ready_city.getSelectedItemPosition() >= 1) {
                        new send_readymade_area_Data().execute();
                    }

                }
                if (rdo_distributor.isChecked()) {

                    spn_distributor.setVisibility(View.GONE);
                    spn_ready_area.setVisibility(View.GONE);
                }
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {


                                          }
                                      }
        );

        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isNewMobile = false;
                boolean isNewEmail = false;
                String email = edit_email.getText().toString().trim();
                String mobile = edit_phone_no.getText().toString().trim();
                String pan_no = edit_pan.getText().toString().trim();

                if (validateEmail1(email))
                    isNewEmail = WebServiceCaller.CheckUniqueMail(email, GlobalVariable.API_CHECK_UNIQUEMAIL);
                if (mobile.length() == 10)
                    isNewMobile = WebServiceCaller.CheckUniqueMobile(mobile, "0");

                if (edit_firm_name.getText().toString().equals("")) {
                    Toast.makeText(AddNewVendor.this, "Please Enter Company Name", Toast.LENGTH_SHORT).show();
                    edit_firm_name.requestFocus();
                } else if (edit_address.getText().toString().equals("")) {
                    Toast.makeText(AddNewVendor.this, "Please Enter Address-1", Toast.LENGTH_SHORT).show();
                    edit_address.requestFocus();
                } else if (edit_address_one.getText().toString().equals("")) {
                    Toast.makeText(AddNewVendor.this, "Please Enter Address-2", Toast.LENGTH_SHORT).show();
                    edit_address_one.requestFocus();
                } /*else if (spn_readymade_country.getSelectedItemPosition() == 0)

                {
                    Toast.makeText(AddNewVendor.this, "Select Country please", Toast.LENGTH_SHORT).show();
                    spn_readymade_country.requestFocus();
                }*/ else if (spn_readymade_state.getSelectedItemPosition() == 0)

                {
                    Toast.makeText(AddNewVendor.this, "Please Select State", Toast.LENGTH_SHORT).show();
                    spn_readymade_state.requestFocus();
                } else if (spn_ready_city.getVisibility() == View.VISIBLE &&
                        spn_ready_city.getSelectedItemPosition() == 0)

                {
                    Toast.makeText(AddNewVendor.this, "Please Select City", Toast.LENGTH_SHORT).show();
                    spn_ready_city.requestFocus();
                } else if (rdo_retailer.isChecked() && spn_ready_area.getSelectedItemPosition() == 0) {


                    Toast.makeText(AddNewVendor.this, "Please Select Area", Toast.LENGTH_SHORT).show();
                    spn_ready_area.requestFocus();

                } else if (edit_pcode.getText().toString().equals("")) {
                    Toast.makeText(AddNewVendor.this, "Please Enter Pincode", Toast.LENGTH_SHORT).show();
                    edit_pcode.requestFocus();
                } else if (edit_pcode.getText().toString().length() < 6) {
                    Toast.makeText(AddNewVendor.this, "Please Enter a Valid Pincode", Toast.LENGTH_SHORT).show();
                    edit_pcode.requestFocus();
                } else if (edit_first.getText().toString().equals("")) {
                    Toast.makeText(AddNewVendor.this, "Please Enter First Name", Toast.LENGTH_SHORT).show();
                    edit_first.requestFocus();
                } else if (edit_last.getText().toString().equals("")) {
                    Toast.makeText(AddNewVendor.this, "Please Enter Last Name", Toast.LENGTH_SHORT).show();
                    edit_last.requestFocus();
                } else if (edit_phone_no.getText().toString().equals("")) {
                    Toast.makeText(AddNewVendor.this, "Please Enter Mobile No", Toast.LENGTH_SHORT).show();
                    edit_phone_no.requestFocus();
                } else if (edit_phone_no.getText().toString().length() < 10) {
                    Toast.makeText(AddNewVendor.this, "Please Enter 10 digit Mobile No", Toast.LENGTH_SHORT).show();
                    edit_phone_no.requestFocus();
                } else if (!isNewMobile) {
                    Toast.makeText(AddNewVendor.this, "Please Enter Unique Mobile No", Toast.LENGTH_SHORT).show();
                    edit_phone_no.requestFocus();
                } /*else if (edit_email.getText().toString().equals("")) {
                    Toast.makeText(AddNewVendor.this, "Please Enter Your Email id", Toast.LENGTH_SHORT).show();
                    edit_email.requestFocus();
                }*/ else if (!email.isEmpty() && validateEmail1(edit_email.getText().toString()) != true) {
                    Toast.makeText(AddNewVendor.this, "Please Enter Valid Email id", Toast.LENGTH_LONG).show();
                    // Globals.CustomToast(Business_Registration.this, "Please enter a valid email address", getLayoutInflater());
                    edit_email.requestFocus();

                } else if (!email.isEmpty() && !isNewEmail) {
                    Toast.makeText(AddNewVendor.this, "Please Enter Unique Email Id", Toast.LENGTH_LONG).show();

                    edit_email.requestFocus();
                }  else if (!pan_no.isEmpty() && pan_no.length() < 10) {

                    Toast.makeText(AddNewVendor.this, "Please Enter Valid PAN Number", Toast.LENGTH_SHORT).show();
                    edit_pan.requestFocus();

                } else if (rdo_retailer.isChecked() && spn_distributor.getSelectedItemPosition() == 0) {

                    Toast.makeText(AddNewVendor.this, "Please Select the Distributor", Toast.LENGTH_SHORT).show();
                    spn_distributor.requestFocus();
                }
                else

                {


                    first_name = edit_first.getText().toString().trim();
                    Log.e("contact_person_name", "" + first_name);
                    last_name = edit_last.getText().toString().trim();
                    emailid = edit_email.getText().toString().trim();

                    phone_no = edit_phone_no.getText().toString().trim();
                    landline = edit_landline_no.getText().toString().trim();
                    pass = edit_pass.getText().toString().trim();
                    address = (edit_address.getText().toString().trim() + " " + edit_address_one.getText().toString().trim());

                    pincod = edit_pcode.getText().toString().trim();
                    firm_name = edit_firm_name.getText().toString().trim();
                    pan = edit_pan.getText().toString().trim();

                    vat = edit_vat.getText().toString().trim();
                    cst = edit_cst.getText().toString().trim();
                    gst = edit_gst.getText().toString().trim();
                    country = "India";
                    state = position_state_selected;
                    city = position_city_selected;


                    area = position_area_selected;


                    dis = position_dis_selected;

                    int checked_id = rg_newtype.getCheckedRadioButtonId();


                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("sales_person_role_id", prefs.getRole_id()));

                    if (!prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
                        parameters.add(new BasicNameValuePair("role_id", "8"));
                    } else {

                        if (checked_id == R.id.rb_distributor) {
                            parameters.add(new BasicNameValuePair("role_id", C.DISTRIBUTOR_ROLE));

                        } else if (checked_id == R.id.rb_retailer) {
                            parameters.add(new BasicNameValuePair("role_id", C.DIS_RETAILER_ROLE));

                        }
                    }

                    if (rdo_retailer.isChecked() || prefs.getRole_id().equalsIgnoreCase(C.DIS_SALES_ROLE)) {
                        parameters.add(new BasicNameValuePair("distributor_id", dis));

                        parameters.add(new BasicNameValuePair("area", area));
                        Log.e("area", "" + area);

                    }
                    if (rdo_distributor.isChecked()) {


                        parameters.add(new BasicNameValuePair("area", "1"));
                        Log.e("area", "" + area);
                    }
                    parameters.add(new BasicNameValuePair("first_name", first_name));
                    Log.e("first_name", "" + first_name);
                    parameters.add(new BasicNameValuePair("last_name", last_name));
                    Log.e("last_name", "" + last_name);

                    if (emailid.equalsIgnoreCase("") || emailid.equalsIgnoreCase(null) || emailid.isEmpty()) {
                        parameters.add(new BasicNameValuePair("email", ""));
                        Log.e("email_id", "" + emailid);

                    } else {
                        parameters.add(new BasicNameValuePair("email", emailid));
                        Log.e("email_id", "" + emailid);
                    }
                    parameters.add(new BasicNameValuePair("phone", phone_no));
                    Log.e("phone_no", "" + phone_no);
                    parameters.add(new BasicNameValuePair("mobile_no", landline));
                    Log.e("mobile_no", "" + landline);
                    parameters.add(new BasicNameValuePair("password", ""));
                    Log.e("password", "" + pass);
                    parameters.add(new BasicNameValuePair("sales_person_id", prefs.getUser_id()));
                    Log.e("sales_person_id", "" + prefs.getUser_id());
                    parameters.add(new BasicNameValuePair("firm_address", address));
                    Log.e("firm_address", "" + address);
                    parameters.add(new BasicNameValuePair("pincode", pincod));
                    Log.e("pincode", "" + pincod);
                    parameters.add(new BasicNameValuePair("country", "1"));
                    Log.e("country", "" + country);
                    parameters.add(new BasicNameValuePair("state", state));
                    Log.e("state", "" + state);
                    parameters.add(new BasicNameValuePair("city", city));
                    Log.e("city", "" + city);


                    parameters.add(new BasicNameValuePair("firm_name", firm_name));
                    Log.e("firm_name", "" + firm_name);
                    parameters.add(new BasicNameValuePair("pan", pan));
                    Log.e("pan", "" + pan);
                    parameters.add(new BasicNameValuePair("vat", vat));
                    Log.e("vat", "" + vat);
                    parameters.add(new BasicNameValuePair("cst", cst));
                    parameters.add(new BasicNameValuePair("gst", gst));

                    int count = layout_direct_cemara.getChildCount();

                    for (int i = 0; i < count; i++) {
                        View vieww = layout_direct_cemara.getChildAt(i);

                        parameters.add(new BasicNameValuePair("image" + (i + 1), ((TextView) vieww.findViewById(R.id.txt_imagePath)).getText().toString()));
                    }
                    new add_newvendor(parameters).execute();

                }

            }


        });


        new GetDistributorList().execute();


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
            final int REQUIRED_SIZE = 500;

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

            /*im_direct_cemara2.setImageBitmap(photo);
            im_direct_cemara3.setImageBitmap(photo);*/
            //layout_imageview_1.setVisibility(View.GONE);
            // im_cemara_icon.setVisibility(View.VISIBLE);
            imagepath1 = "";
            //Uri tempUri = getImageUri(getApplicationContext(), photo);

            imagepath1 = selectedImagePath;
            resizeImage(selectedImagePath);
            Log.e("image path", imagepath1);
        } else if (requestCode == CAMERA_DIRECT && resultCode == RESULT_OK) {
            String selectedImagePath = getImagePath();

            Bitmap photo = decodeFile(selectedImagePath);


            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            im_direct_cemara1.setVisibility(View.VISIBLE);
            im_direct_cemara1.setImageBitmap(photo);
            //layout_imageview_1.setVisibility(View.GONE);
            // im_cemara_icon.setVisibility(View.VISIBLE);
            imagepath2 = "";
            //Uri tempUri = getImageUri(getApplicationContext(), photo);

            imagepath2 = selectedImagePath;
            resizeImage(selectedImagePath);
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

            final View view = getLayoutInflater().inflate(R.layout.layout_subimage_add, null);
            ImageView img_attachment = (ImageView) view.findViewById(R.id.img_attachment);
            ImageView img_remove = (ImageView) view.findViewById(R.id.img_remove);
            TextView txt_imagePath = (TextView) view.findViewById(R.id.txt_imagePath);

            img_attachment.setImageBitmap(bm);
            txt_imagePath.setText(path);

            img_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_direct_cemara.removeView(view);
                }
            });


            layout_direct_cemara.addView(view);

            ostream.close();
        } catch (Exception e) {

        }
    }

    public void imageRemove() {


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


    private void selectOtherArea() {
        final Dialog dialog = new Dialog(AddNewVendor.this);
        LayoutInflater inflater = AddNewVendor.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_add_area, null);

        dialog.setContentView(view);
        dialog.setTitle("Add Othet Area");
        final EditText edit_other_area = (EditText) view.findViewById(R.id.edt_other_area);
        final Button send = (Button) view.findViewById(R.id.btn_send_other);
        final Button cancel = (Button) view.findViewById(R.id.btn_cancel_other);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherarea = edit_other_area.getText().toString().trim();

                if (otherarea.equalsIgnoreCase("") || otherarea.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Area Name Should Not be Empty", Toast.LENGTH_SHORT).show();


                } else {
                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("area_name", otherarea));
                    parameters.add(new BasicNameValuePair("city_id", position_city_selected));


                    new add_other_area(parameters).execute();
                    dialog.dismiss();
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn_ready_area.setSelection(0);
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    private boolean validateEmail1(String s) {
        // TODO Auto-generated method stub

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public class add_newvendor extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String root_date;

        int done = 0;
        List<NameValuePair> parameters = new ArrayList<>();

        public add_newvendor(List<NameValuePair> parameters) {
            this.parameters = parameters;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(AddNewVendor.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                Log.e("parameter", parameters.toString());

                // parameters.add(new BasicNameValuePair("password",password));

                // Log.e("","" + login_arry);
                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.ADD_VENDOR, ServiceHandler.POST, parameters);

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
                //db = new DatabaseHandler(());
                System.out.println(result_1);
                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase("")) || result_1.isEmpty()) {
                    Toast.makeText(AddNewVendor.this, "SERVER ERROR", Toast.LENGTH_LONG).show();

                    // GlobalVariable.CustomToast(AddNewVendor.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    Log.e("status", "" + date);
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(AddNewVendor.this, "" + Message, Toast.LENGTH_LONG).show();
                        // Global.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        Log.e("status", "" + date);
                        String Message = jObj.getString("message");
                        Toast.makeText(AddNewVendor.this, "" + Message, Toast.LENGTH_LONG).show();
                        String vendor_id = jObj.getString("vendor_id");
                        prefs.setVendor_id(jObj.getString("vendor_id"));
                        Log.e("vendor_id", "" + vendor_id);

                        prefs.setNewvendorAdd(true);

                        Intent intent = new Intent(AddNewVendor.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("NewCompanyName", firm_name);


                        startActivity(intent);
                        finish();

                        loadingView.dismiss();
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("excePtioN", j.getMessage());
            }
        }


    }

    public class send_readymade_state_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(AddNewVendor.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... voids) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("country_id", "1"));


                Log.e("", "" + parameters);

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_STATE, ServiceHandler.POST, parameters);

                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            statename.clear();
            stateid.clear();
            cityname.clear();
            cityid.clear();

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();*/
                    Toast.makeText(AddNewVendor.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Toast.makeText(AddNewVendor.this, "" + Message, Toast.LENGTH_SHORT).show();
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);
                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            statename.add("Select State*");
                            stateid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");

                                state_id.add(sId);
                                statename.add(sname);
                                stateid.add(sId);

                            }

                          /*  adapter_state = new ArrayAdapter<String>(AddNewVendor.this, R.layout.custom_spinner, statename);
                            adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spn_readymade_state.setAdapter(adapter_state);
*/


                            adapter_state = new ArrayAdapter<String>(AddNewVendor.this, R.layout.custom_spinner, statename);
                            adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spn_readymade_state.setAdapter(adapter_state);


                        }

                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }


    public class send_readymade_city_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(AddNewVendor.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... voids) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("state_id", position_state_selected));


                Log.e("", "" + parameters);


                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_CITY, ServiceHandler.POST, parameters);

                return json;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
//            Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            cityname.clear();
            cityid.clear();

            areaname.clear();
            areaid.clear();
            try {
                Log.e("json :", "" + result_1);
                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    //*Toast.makeText(Business_Registration.this, "SERVER ERROR",Toast.LENGTH_SHORT).show();*//**//*


                    Toast.makeText(AddNewVendor.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Toast.makeText(AddNewVendor.this, "" + Message, Toast.LENGTH_SHORT).show();
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);


                        if (jobj != null) {

                            JSONArray categories = jObj.getJSONArray("data");
                            spn_ready_city.setVisibility(View.VISIBLE);
                            cityname.add("Select City*");
                            cityid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");

                                cityname.add(sname);
                                cityid.add(sId);

                                //city_id.add(sId);

                            }

                            adapter_city = new ArrayAdapter<String>(AddNewVendor.this, R.layout.custom_spinner, cityname);
                            adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spn_ready_city.setAdapter(adapter_city);


                        }

                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }


    public class send_readymade_area_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        AddNewVendor.this, "");


                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("city_id", position_city_selected));


                Log.e("CITY AREA", "" + parameters.toString());

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_AREA, ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
//            Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            areaname.clear();
            areaid.clear();


            areaname.add("Select Area*");
            areaid.add("0");

            areaname.add("Other");
            areaid.add("1");

            if (rdo_retailer.isChecked() || prefs.getRole_id().equalsIgnoreCase(C.DIS_SALES_ROLE)) {
                spn_ready_area.setSelection(0);
                spn_ready_area.setVisibility(View.VISIBLE);
            } else {


                spn_ready_area.setSelection(0);
                spn_ready_area.setVisibility(View.GONE);

            }


            try {
                Log.e("json :", "" + result_1);

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    //*Toast.makeText(Business_Registration.this, "SERVER ERROR",Toast.LENGTH_SHORT).show();*//**//*


                    Toast.makeText(AddNewVendor.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        // Toast.makeText(AddNewVendor.this, "" + Message, Toast.LENGTH_SHORT).show();
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);


                        JSONArray categories = jObj.getJSONArray("data");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            String sId = catObj.getString("id");
                            String sname = catObj.getString("name");

                            areaname.add(sname);
                            areaid.add(sId);

                            //area_id.add(sId);

                        }


                        loadingView.dismiss();
                    }

                    adapter_area = new ArrayAdapter<String>(AddNewVendor.this, R.layout.custom_spinner, areaname);
                    adapter_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_ready_area.setAdapter(adapter_area);


                    Log.e("Other Area", "->" + other_area_id);

                    if (!other_area_id.isEmpty()) {
                        Log.e("Other Area", "->1" + other_area_id);
                        for (int i = 0; i < areaid.size(); i++) {
                            Log.e("Other Area", areaid.get(i) + "->" + other_area_id);
                            if (areaid.get(i).equalsIgnoreCase(other_area_id)) {
                                Log.e("Other Area", areaid.get(i) + "--->" + other_area_id);
                                spn_ready_area.setSelection(i);
                            }
                        }
                    }

                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

            Log.e("Total Areas", areaid.size() + "--");


        }
    }

    public class add_other_area extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String root_date;

        int done = 0;
        List<NameValuePair> parameters = new ArrayList<>();

        public add_other_area(List<NameValuePair> parameters) {
            this.parameters = parameters;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(AddNewVendor.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                Log.e("parameter", parameters.toString());

                // parameters.add(new BasicNameValuePair("password",password));

                // Log.e("","" + login_arry);
                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_OTHER_AREA, ServiceHandler.POST, parameters);

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

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    //*Toast.makeText(Business_Registration.this, "SERVER ERROR",Toast.LENGTH_SHORT).show();*//**//*


                    Toast.makeText(AddNewVendor.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Toast.makeText(AddNewVendor.this, "" + Message, Toast.LENGTH_SHORT).show();
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Toast.makeText(AddNewVendor.this, "" + Message, Toast.LENGTH_SHORT).show();

                        other_area_id = jobj.getString("new_area_id");

                        //areaname.add(otherarea);
                        //areaid.add(other_area_id);


                            /*ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(AddNewVendor.this, R.layout.custom_spinner, areaname);
                            adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spn_ready_area.setAdapter(adapter_state);*/


                        loadingView.dismiss();
                        new send_readymade_area_Data().execute();
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }


        }

    }

    public class GetDistributorList extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        AddNewVendor.this, "");


                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {


            List<NameValuePair> parameters = new ArrayList<NameValuePair>();


            parameters.add(new BasicNameValuePair("sales_person_role_id", prefs.getRole_id()));
            parameters.add(new BasicNameValuePair("owner_id", prefs.getOwner_id()));
            String json = new ServiceHandler().makeServiceCall(Web.LINK + "User/App_Get_Distributor", ServiceHandler.POST, parameters);

            Log.e("parameters", parameters.toString());

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            loadingView.dismiss();
            Log.e("finaljson :", "" + json);

            if (json.isEmpty() || json == null) {


                Toast.makeText(AddNewVendor.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {

                try {
                    JSONObject object = new JSONObject(json);
                    boolean status = object.getBoolean("status");


                    if (status) {
                        JSONArray dataArray = object.getJSONArray("data");
                        disName.clear();
                        disId.clear();
                        disName.add("Select Distributor");
                        disId.add("0");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject main = dataArray.getJSONObject(i);
                            JSONObject user = main.getJSONObject("User");

                            String disid = user.getString("id");
                            String disname = user.getString("FullName");


                            dis_id.add(disid);
                            disName.add(disname);
                            disId.add(disid);


                        }
                        ArrayAdapter<String> adapter_distributor_name = new ArrayAdapter<String>(AddNewVendor.this, R.layout.custom_spinner, disName);
                        adapter_distributor_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spn_distributor.setAdapter(adapter_distributor_name);


                        new send_readymade_state_Data().execute();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Exception", e.getMessage());
                }


            }
        }
    }

    public void onBackPressed() {
        Intent i = new Intent(AddNewVendor.this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}