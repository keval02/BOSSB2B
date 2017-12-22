package com.nivida.bossb2b;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_Address;
import com.nivida.bossb2b.Bean.Bean_User_data;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Cart_AddNewAddress extends AppCompatActivity {

    EditText et_addline1, et_addline2, et_addline3, et_pincode, et_landmark, edit_firmname ,editfirmname;
    Spinner spn_state, spn_city, spn_country, spn_area;
    Button btn_save, btn_reset;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();

    ArrayList<String> countryid = new ArrayList<String>();
    ArrayList<String> countryname = new ArrayList<String>();
    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();
    ArrayList<String> areaid = new ArrayList<String>();
    ArrayList<String> areaname = new ArrayList<String>();

    String position_country = new String();
    String position_state = new String();
    String position_city = new String();
    String position_area = new String();

    String position_countryname = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String position_areaname = new String();

    String position_country_selected = "";
    ArrayList<String> country_id = new ArrayList<>();

    String position_state_selected = "";
    ArrayList<String> state_id = new ArrayList<>();

    String position_city_selected = "";
    ArrayList<String> city_id = new ArrayList<>();

    String position_area_selected = "";
    ArrayList<String> area_id = new ArrayList<>();


    ProgressActivity loadingView;

    String user_id_main = new String();
    String json = new String();
    String userid, add1, add2, add3, lmark, pcode, sid, cid , address_id;

    String first_name, last_name, emailid, phone_no, landline, address, pincod, area, country, state, city, pass, firm_name, pan, vat, cst, vendortype, dis, otherarea;
    DatabaseHandler db;
    AppPref pref;

    String firstName = "";
    String lastName = "";
    String emailID = "";
    String mobileNum = "";
    String other_area_id = "";
    String firmName="";

    ArrayAdapter<String> adapter_country;
    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> adapter_city;
    ArrayAdapter<String> adapter_area;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart__add_new_address);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pref = new AppPref(getApplicationContext());
        Intent intent = getIntent();

        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        emailID = intent.getStringExtra("email");
        mobileNum = intent.getStringExtra("Mobileno");
        firmName = intent.getStringExtra("firmName");

        Log.e("Name" , firmName+lastName);

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

        new send_readymade_state_Data();
        fetchId();
        setRefershData();



        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main = user_data.get(i).getUser_id().toString();

            }

        } else {
            user_id_main = "";
        }

        new send_readymade_state_Data().execute();


    }

    private void fetchId() {
        et_addline1 = (EditText) findViewById(R.id.et_addline1);
        et_addline2 = (EditText) findViewById(R.id.et_addline2);
        et_addline3 = (EditText) findViewById(R.id.et_addline3);
        et_pincode = (EditText) findViewById(R.id.edit_pcode);
        editfirmname = (EditText) findViewById(R.id.editfirmname);
        editfirmname.setText(pref.getOnSelectedFirmName());
        editfirmname.setEnabled(false);
        editfirmname.setTextColor(Color.parseColor("#9e9e9e"));




        edit_firmname = (EditText) findViewById(R.id.edit_firm_name);
        edit_firmname.setText(firstName + " " + lastName);


        edit_firmname.setEnabled(false);

        et_landmark = (EditText) findViewById(R.id.et_landmark);

        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_save = (Button) findViewById(R.id.btn_save);

        spn_state = (Spinner) findViewById(R.id.spn_state);
        spn_city = (Spinner) findViewById(R.id.spn_city);
        spn_country = (Spinner) findViewById(R.id.spn_country);
        spn_area = (Spinner) findViewById(R.id.spn_area);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (et_addline1.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(Cart_AddNewAddress.this, "Please Enter Address-1", Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(Cart_AddNewAddress.this,"Please Enter Address Line1",getLayoutInflater());
                    et_addline1.requestFocus();
                } else if (et_addline2.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(Cart_AddNewAddress.this, "Please Enter Address-2", Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(Cart_AddNewAddress.this,"Please Enter Address Line2",getLayoutInflater());
                    et_addline2.requestFocus();
                }
               /* else if(position_country.toString().equalsIgnoreCase("0"))
                {

                    Toast.makeText(Cart_AddNewAddress.this , "Please Select Country" ,Toast.LENGTH_LONG).show();
                  spn_country.requestFocus();  //GlobalVariable.CustomToast(Cart_AddNewAddress.this,"Please Select City",getLayoutInflater());
                }*/

                else if (position_state.toString().equalsIgnoreCase("0")) {
                    Toast.makeText(Cart_AddNewAddress.this, "Please Select State", Toast.LENGTH_LONG).show();
                    spn_state.requestFocus();
                    // GlobalVariable.CustomToast(Cart_AddNewAddress.this,"Please Select State",getLayoutInflater());
                } else if (position_city.toString().equalsIgnoreCase("0")) {

                    Toast.makeText(Cart_AddNewAddress.this, "Please Select City", Toast.LENGTH_LONG).show();
                    spn_city.requestFocus();
                    //GlobalVariable.CustomToast(Cart_AddNewAddress.this,"Please Select City",getLayoutInflater());
                } else if (position_area.toString().equalsIgnoreCase("0")) {
                    Toast.makeText(Cart_AddNewAddress.this, "Please Select Area", Toast.LENGTH_LONG).show();
                    spn_area.requestFocus();
                    // GlobalVariable.CustomToast(Cart_AddNewAddress.this,"Please Select State",getLayoutInflater());
                } else if (et_pincode.getText().toString().equalsIgnoreCase("")) {

                    Toast.makeText(Cart_AddNewAddress.this, "Please Enter Address Pincode", Toast.LENGTH_LONG).show();
                    et_pincode.requestFocus();
                    //GlobalVariable.CustomToast(Cart_AddNewAddress.this,"Please Enter Address Pincode",getLayoutInflater());
                } else if (et_pincode.length() < 6) {
                    Toast.makeText(Cart_AddNewAddress.this, "Password minimum 6 character required", Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(Cart_AddNewAddress.this, "Pincode 6 character required", getLayoutInflater());
                    et_pincode.requestFocus();

                } else {

                    add1 = et_addline1.getText().toString();
                    add2 = et_addline2.getText().toString();
                    add3 = et_addline3.getText().toString();
                    lmark = et_landmark.getText().toString();
                    pcode = et_pincode.getText().toString();


                    new send_address().execute();
                }

            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_addline1.setText("");
                et_addline2.setText("");
                et_addline3.setText("");
                et_pincode.setText("");
                et_landmark.setText("");
                spn_state.setSelection(0);
                spn_city.setSelection(0);
                spn_area.setSelection(0);

            }
        });
        spn_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_country = countryid.get(position);
                position_countryname = countryname.get(position);
                if (position_country.equalsIgnoreCase("0")) {
                    //Globals.CustomToast(Add_Newuser_Address.this,"Please Select State",getLayoutInflater());
                } else if (spn_country.getSelectedItemPosition() == 0) {
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

       /* spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if (position_statename.equalsIgnoreCase("0")) {

                } else if (spn_state.getSelectedItemPosition() == 0) {
                    spn_city.setVisibility(View.GONE);
                    spn_area.setVisibility(View.GONE);
                    spn_city.setSelection(0);
                    spn_area.setSelection(0);
                    //Do nothing
                } else {
                    position_state_selected = state_id.get(position - 1);
                    new send_readymade_city_Data().execute();
                    spn_area.setVisibility(View.GONE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                // TODO Auto-generated method stub

                // position1=""+position;
                position_city = cityid.get(position);
                position_cityname = cityname.get(position);
                if (position_city.equalsIgnoreCase("0")) {
                    spn_area.setSelection(0);

                }  if (spn_area.getSelectedItemPosition() == 0) {
                    spn_area.setVisibility(View.GONE);



                } else {
                    position_city_selected = city_id.get(position - 1);
                    other_area_id = "";

                    new send_readymade_area_Data().execute();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spn_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                // position1=""+position;

                Log.e("Selected Position", "->" + position);
                Log.e("Name Position", "->" + position + areaname.get(position));

                position_area = areaid.get(spn_area.getSelectedItemPosition());
                position_areaname = areaname.get(spn_area.getSelectedItemPosition());

                if (spn_area.getSelectedItemPosition() == 1) {

                    selectOtherArea();
                }

                if (spn_area.getSelectedItemPosition() != 0 && spn_area.getSelectedItemPosition() != 1) {

                    position_area_selected = areaid.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
*/


        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if (position_statename.equalsIgnoreCase("0")) {

                } else if (spn_state.getSelectedItemPosition() == 0) {

                    spn_city.setVisibility(View.GONE);
                    spn_area.setVisibility(View.GONE);
                    spn_city.setSelection(0);
                    spn_area.setSelection(0);
                    other_area_id = "";



                } else {
                    position_state_selected = state_id.get(position - 1);
                    spn_city.setVisibility(View.GONE);
                    spn_area.setVisibility(View.GONE);
                    new send_readymade_city_Data().execute();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                // TODO Auto-generated method stub

                // position1=""+position;
                position_city = cityid.get(position);
                position_cityname = cityname.get(position);
                if (position_city.equalsIgnoreCase("0")) {
                    spn_area.setSelection(0);

                }  if (spn_city.getSelectedItemPosition() == 0) {
                    spn_area.setVisibility(View.GONE);



                } else {
                    position_city_selected = cityid.get(position);
                    other_area_id = "";
                    spn_area.setVisibility(View.GONE);
                    new send_readymade_area_Data().execute();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spn_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                // position1=""+position;

                Log.e("Selected Position", "->" + position);
                Log.e("Name Position", "->" + position + areaname.get(position));

                position_area = areaid.get(spn_area.getSelectedItemPosition());
                position_areaname = areaname.get(spn_area.getSelectedItemPosition());

                if (spn_area.getSelectedItemPosition() == 1) {

                    selectOtherArea();
                }

                if (spn_area.getSelectedItemPosition() != 0 && spn_area.getSelectedItemPosition() != 1) {

                    position_area_selected = areaid.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }


    private void selectOtherArea() {
        final Dialog dialog = new Dialog(Cart_AddNewAddress.this);
        LayoutInflater inflater = Cart_AddNewAddress.this.getLayoutInflater();
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


                if(otherarea.isEmpty()||otherarea.equalsIgnoreCase("")){


                    Toast.makeText(getApplicationContext(),"Area Name Should Not be Empty",Toast.LENGTH_SHORT).show();


                }
                else {

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


                spn_area.setSelection(0);
                dialog.dismiss();
            }
        });


        dialog.show();


    }



    public class send_readymade_state_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(Cart_AddNewAddress.this, "");

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


            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();*/
                    Toast.makeText(Cart_AddNewAddress.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Toast.makeText(Cart_AddNewAddress.this, "" + Message, Toast.LENGTH_SHORT).show();
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

                            adapter_state = new ArrayAdapter<String>(Cart_AddNewAddress.this, R.layout.custom_spinner, statename);
                            adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spn_state.setAdapter(adapter_state);


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
                loadingView = new ProgressActivity(Cart_AddNewAddress.this, "");

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


                    Toast.makeText(Cart_AddNewAddress.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Toast.makeText(Cart_AddNewAddress.this, "" + Message, Toast.LENGTH_SHORT).show();
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            spn_city.setVisibility(View.VISIBLE);
                            cityname.add("Select City*");
                            cityid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");

                                cityname.add(sname);
                                cityid.add(sId);

                                city_id.add(sId);

                            }

                            adapter_city = new ArrayAdapter<String>(Cart_AddNewAddress.this, R.layout.custom_spinner, cityname);
                            adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spn_city.setAdapter(adapter_city);


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
                        Cart_AddNewAddress.this, "");


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


                Log.e("", "" + parameters);

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

            spn_area.setVisibility(View.VISIBLE);
            spn_area.setSelection(0);
            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    //*Toast.makeText(Business_Registration.this, "SERVER ERROR",Toast.LENGTH_SHORT).show();*//**//*


                    Toast.makeText(Cart_AddNewAddress.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                      //  Toast.makeText(Cart_AddNewAddress.this, "" + Message, Toast.LENGTH_SHORT).show();
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

                    adapter_area = new ArrayAdapter<String>(Cart_AddNewAddress.this, R.layout.custom_spinner, areaname);
                    adapter_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_area.setAdapter(adapter_area);


                    Log.e("Other Area", "->" + other_area_id);

                    if (!other_area_id.isEmpty()) {
                        Log.e("Other Area", "->1" + other_area_id);
                        for (int i = 0; i < areaid.size(); i++) {
                            Log.e("Other Area", areaid.get(i) + "->" + other_area_id);
                            if (areaid.get(i).equalsIgnoreCase(other_area_id)) {
                                Log.e("Other Area", areaid.get(i) + "--->" + other_area_id);
                                spn_area.setSelection(i);
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
                loadingView = new ProgressActivity(Cart_AddNewAddress.this, "");

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


                    Toast.makeText(Cart_AddNewAddress.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Toast.makeText(Cart_AddNewAddress.this, "" + Message, Toast.LENGTH_SHORT).show();
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Toast.makeText(Cart_AddNewAddress.this, "" + Message, Toast.LENGTH_SHORT).show();

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

    public class send_address extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        Cart_AddNewAddress.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                if(pref.getRole_id().equalsIgnoreCase(C.DISTRIBUTOR_ROLE)&& pref.isListClicked()==false){

                    parameters.add(new BasicNameValuePair("user_id", pref.getUser_id()));

                }
                else{

                    parameters.add(new BasicNameValuePair("user_id", pref.getVendor_id()));
                }

                parameters.add(new BasicNameValuePair("first_name", firstName));
                parameters.add(new BasicNameValuePair("last_name", lastName));
                parameters.add(new BasicNameValuePair("email", emailID));
                parameters.add(new BasicNameValuePair("mobile", mobileNum));
                parameters.add(new BasicNameValuePair("address_1", add1));
                parameters.add(new BasicNameValuePair("address_2", add2));
                parameters.add(new BasicNameValuePair("address_3", add3));
                parameters.add(new BasicNameValuePair("landmark", lmark));
                parameters.add(new BasicNameValuePair("pincode", pcode));
                parameters.add(new BasicNameValuePair("country_id", "1"));
                parameters.add(new BasicNameValuePair("state_id", position_state));
                parameters.add(new BasicNameValuePair("city_id", position_city));
                parameters.add(new BasicNameValuePair("default_address", "0"));


                Log.e("", "" + parameters);

                Log.e("parameters" , "-->" + parameters);

                json = new ServiceHandler().makeServiceCall(GlobalVariable.link + "Address/App_Add_Address", ServiceHandler.POST, parameters);
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

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(Cart_AddNewAddress.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();
                    /*GlobalVariable.CustomToast(Cart_AddNewAddress.this, "SERVER ERROR", getLayoutInflater());*/
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(Cart_AddNewAddress.this, "" + Message, Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(Cart_AddNewAddress.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);
                        String Message = jObj.getString("message");
                        Toast.makeText(Cart_AddNewAddress.this, "" + Message, Toast.LENGTH_LONG).show();


                        address_id = jobj.getString("data");



                        //GlobalVariable.CustomToast(Cart_AddNewAddress.this, "" + Message, getLayoutInflater());
                      /*  if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            cityname.add("Select City");
                            cityid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");

                                cityname.add(cname);
                                cityid.add(cId);


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(Cart_AddNewAddress.this, android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_city.setAdapter(adapter_city);

                            }
                        }*/

                        pref.setAddednewAddress(true);

                        Intent i = new Intent(Cart_AddNewAddress.this, CheckoutPage.class);
                        i.putExtra("address_1" , add1);
                        i.putExtra("address_2" , add2);
                        i.putExtra("address_3" , add3);
                        i.putExtra("pincode" , pcode);
                        i.putExtra("city" , position_city);
                        i.putExtra("state" , position_area);
                        i.putExtra("address_id" , address_id);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Cart_AddNewAddress.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid = user_array_from_db.get(i).getId();
            String user_id = user_array_from_db.get(i).getUser_id();
            String email_id = user_array_from_db.get(i).getEmail_id();
            String phone_no = user_array_from_db.get(i).getPhone_no();
            String f_name = user_array_from_db.get(i).getF_name();
            String l_name = user_array_from_db.get(i).getL_name();
            String password = user_array_from_db.get(i).getPassword();
            String gender = user_array_from_db.get(i).getGender();
            String usertype = user_array_from_db.get(i).getUser_type();
            String login_with = user_array_from_db.get(i).getLogin_with();
            String str_rid = user_array_from_db.get(i).getStr_rid();
            String add1 = user_array_from_db.get(i).getAdd1();
            String add2 = user_array_from_db.get(i).getAdd2();
            String add3 = user_array_from_db.get(i).getAdd3();
            String landmark = user_array_from_db.get(i).getLandmark();
            String pincode = user_array_from_db.get(i).getPincode();
            String state_id = user_array_from_db.get(i).getState_id();
            String state_name = user_array_from_db.get(i).getState_name();
            String city_id = user_array_from_db.get(i).getCity_id();
            String city_name = user_array_from_db.get(i).getCity_name();
            String str_response = user_array_from_db.get(i).getStr_response();


            Bean_User_data contact = new Bean_User_data();
            contact.setId(uid);
            contact.setUser_id(user_id);
            contact.setEmail_id(email_id);
            contact.setPhone_no(phone_no);
            contact.setF_name(f_name);
            contact.setL_name(l_name);
            contact.setPassword(password);
            contact.setGender(gender);
            contact.setUser_type(usertype);
            contact.setLogin_with(login_with);
            contact.setStr_rid(str_rid);
            contact.setAdd1(add1);
            contact.setAdd2(add2);
            contact.setAdd3(add3);
            contact.setLandmark(landmark);
            contact.setPincode(pincode);
            contact.setState_id(state_id);
            contact.setState_name(state_name);
            contact.setCity_id(city_id);
            contact.setCity_name(city_name);
            contact.setStr_response(str_response);
            user_data.add(contact);


        }
        db.close();
    }
}