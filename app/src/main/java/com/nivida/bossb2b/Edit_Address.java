package com.nivida.bossb2b;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Edit_Address extends AppCompatActivity {

    EditText edt_add1,edt_add2,edt_add3,edt_pincode;

    Spinner sp_state,sp_city;

    String addId = new String();
    String add1 = new String();
    String add2 = new String();
    String add3 = new String();
    String pincode = new String();
    String state = new String();
    String city = new String();
    Button save,reset;

    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();

    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();

    String position_state = new String();
    String position_city = new String();
    String position_statename = new String();
    String position_cityname = new String();

    ProgressActivity loadingView;

    DatabaseHandler db;

    AppPrefs apps;
    String json = new String();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__address);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent=getIntent();


        addId = intent.getStringExtra("addId");
        add1 =getIntent().getExtras().getString("add1").toString();
        add2 = getIntent().getExtras().getString("add2").toString();
        add3 = getIntent().getExtras().getString("add3").toString();
        pincode = getIntent().getExtras().getString("pincode").toString();
        state = getIntent().getExtras().getString("state").toString();
        city = getIntent().getExtras().getString("city").toString();


        FetchId();
    }
    private void FetchId() {

        save = (Button) findViewById(R.id.btn_save_dist);
        reset = (Button) findViewById(R.id.btn_Reset_dist);
        edt_add1 = (EditText) findViewById(R.id.Et_Addressline_no1_direct);
        edt_add2 = (EditText) findViewById(R.id.Et_Addressline_no2_direct);
        edt_add3 = (EditText) findViewById(R.id.Et_Addressline_no3_direct);

        edt_pincode = (EditText) findViewById(R.id.Et_Pincode_direct);
        sp_state = (Spinner) findViewById(R.id.spn_select_state);
        sp_city = (Spinner) findViewById(R.id.spn_select_city);

        stateid.clear();
        statename.clear();
        cityid.clear();
        cityname.clear();

        if(add1.equalsIgnoreCase("") || add1.equalsIgnoreCase("null")){

        }else{
            edt_add1.setText(add1);
        }

        if(add2.equalsIgnoreCase("") || add2.equalsIgnoreCase("null")){

        }else{
            edt_add2.setText(add2);
        }

        if(add3.equalsIgnoreCase("") || add3.equalsIgnoreCase("null")){

        }else{
            edt_add3.setText(add3);
        }

        if(pincode.equalsIgnoreCase("") || pincode.equalsIgnoreCase("null")){

        }else{
            edt_pincode.setText(pincode);
        }


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edt_add1.setText("");
                edt_add2.setText("");
                edt_add3.setText("");
                edt_pincode.setText("");
                stateid.clear();
                statename.clear();
                cityid.clear();
                cityname.clear();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_add1.getText().toString().trim().equalsIgnoreCase("")) {

                     Toast.makeText(Edit_Address.this,"Please Enter Address 1",Toast.LENGTH_LONG).show();
                   // GlobalVariable.CustomToast(Edit_Address.this, "Please Enter Address 1", Edit_Address.this.getLayoutInflater());
                } else if (edt_add2.getText().toString().trim().equalsIgnoreCase("")) {
                    //GlobalVariable.CustomToast(Edit_Address.this, "Please Enter Address 2", Edit_Address.this.getLayoutInflater());
                     Toast.makeText(Edit_Address.this,"Please Enter Address 2",Toast.LENGTH_LONG).show();
                } else if (edt_add3.getText().toString().trim().equalsIgnoreCase("")) {
                    //GlobalVariable.CustomToast(Edit_Address.this, "Please Enter Address 3", Edit_Address.this.getLayoutInflater());
                      Toast.makeText(Edit_Address.this,"Please Enter Address 3",Toast.LENGTH_LONG).show();
                } else if (edt_pincode.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(Edit_Address.this,"Please Enter Pincode",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(Edit_Address.this, "Please Enter Pincode", Edit_Address.this.getLayoutInflater());
                }else if (position_state.equalsIgnoreCase("0")) {
                    Toast.makeText(Edit_Address.this,"Please Select State",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(Edit_Address.this, "Please Select State", Edit_Address.this.getLayoutInflater());
                } else if (position_city.equalsIgnoreCase("0")) {
                    Toast.makeText(Edit_Address.this,"Please Select city",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(Edit_Address.this, "Please Select city", Edit_Address.this.getLayoutInflater());
                }else{

                    add1 =edt_add1.getText().toString().trim();
                    add2 =edt_add2.getText().toString().trim();
                    add3 = edt_add3.getText().toString().trim();
                    pincode = edt_pincode.getText().toString().trim();
                    state = position_state;
                    city = position_city;


                    new set_edit_Address().execute();

                }
            }
        });

        new send_state_Data().execute();


        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if(position_state.equalsIgnoreCase("0")){

                }
                else{
                    new send_city_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_city = cityid.get(position);
                position_cityname = cityname.get(position);
                if(position_city.equalsIgnoreCase("0")){}
                else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(Edit_Address.this, SavedAddress.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    };

    public class send_state_Data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        Edit_Address.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("country_id", "1"));



                Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(GlobalVariable.link+"State/App_GetState",ServiceHandler.POST,parameters);
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
                    Toast.makeText(Edit_Address.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(Edit_Address.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(Edit_Address.this,""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(Edit_Address.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            statename.add("Select State");
                            stateid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");

                                statename.add(sname);
                                stateid.add(sId);


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(Edit_Address.this,  android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_state.setAdapter(adapter_state);
                                if(state.equalsIgnoreCase("") || state.equalsIgnoreCase("null")){

                                }else{
                                    for (int j = 0; j < stateid.size(); j++) {

                                        Log.e("", "" + stateid.get(j) + "   -- " + stateid);
                                        if (stateid.get(j).equalsIgnoreCase(state)) {
                                            sp_state.setSelection(j);
                                            position_state = state;
                                        }
                                    }
                                }


                            }
                        }
                        loadingView.dismiss();

                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }
    public class send_city_Data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        Edit_Address.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("state_id", position_state));



                Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(GlobalVariable.link+"City/App_GetCity",ServiceHandler.POST,parameters);
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
                    Toast.makeText(Edit_Address.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();
                   // GlobalVariable.CustomToast(Edit_Address.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(Edit_Address.this,""+Message,Toast.LENGTH_LONG).show();
                       // GlobalVariable.CustomToast(Edit_Address.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);
                        cityname.clear();
                        cityid.clear();
                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            cityname.add("Select City");
                            cityid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");

                                cityname.add(cname);
                                cityid.add(cId);


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(Edit_Address.this,  android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_city.setAdapter(adapter_city);

                                if(city.equalsIgnoreCase("") || city.equalsIgnoreCase("null")){

                                }else{
                                    for (int j = 0; j < cityid.size(); j++) {

                                        Log.e("", "" + cityid.get(j) + "   -- " + cityid);
                                        if (cityid.get(j).equalsIgnoreCase(city)) {
                                            sp_city.setSelection(j);
                                            position_city = city;
                                        }
                                    }
                                }

                            }
                        }
                        loadingView.dismiss();

                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class set_edit_Address extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        Edit_Address.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                parameters.add(new BasicNameValuePair("id", "" + addId));
                parameters.add(new BasicNameValuePair("address_1", "" + add1));
                parameters.add(new BasicNameValuePair("address_2", ""+add2));
                parameters.add(new BasicNameValuePair("address_3", "" + add3));
                parameters.add(new BasicNameValuePair("landmark", ""));
                parameters.add(new BasicNameValuePair("pincode", ""+pincode));
                parameters.add(new BasicNameValuePair("country_id", "1"));
                parameters.add(new BasicNameValuePair("state_id", "" + position_state));
                parameters.add(new BasicNameValuePair("city_id", ""+position_city));


                Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(GlobalVariable.link+"Address/App_Edit_Address",ServiceHandler.POST,parameters);
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
                    /*Toast.makeText(Edit_Address.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();*/
                    GlobalVariable.CustomToast(Edit_Address.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(Edit_Address.this,""+Message,Toast.LENGTH_LONG).show();
                       // GlobalVariable.CustomToast(Edit_Address.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        loadingView.dismiss();

                        String Message = jObj.getString("message");
                         Toast.makeText(Edit_Address.this,""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(Edit_Address.this, "" + Message, getLayoutInflater());

                        Intent i = new Intent(Edit_Address.this, SavedAddress.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }
}
