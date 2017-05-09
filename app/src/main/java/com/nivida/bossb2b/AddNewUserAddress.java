package com.nivida.bossb2b;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_User_data;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddNewUserAddress extends AppCompatActivity {

    EditText et_addline1,et_addline2,et_addline3,et_pincode,et_landmark;
    Spinner spn_state,spn_city;
    Button btn_save,btn_reset;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();

    ArrayAdapter<String> adapter_city;


    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();

    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    ProgressActivity loadingView;
    String position_state = new String();
    String position_city = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String user_id_main = new String();
    String json = new String();
    String userid,add1,add2,add3,lmark,pcode,sid,cid;
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user_address);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        fetchId();
        setRefershData();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main =user_data.get(i).getUser_id().toString();

            }

        }else{
            user_id_main ="";
        }
        
        new send_state_Data().execute();
    }

    private void fetchId()
    {
        et_addline1=(EditText)findViewById(R.id.et_addline1);
        String text134 = "Address Line1 <font color=#ff0000>*</font>";
        et_addline1.setHint(Html.fromHtml(text134));

        et_addline2=(EditText)findViewById(R.id.et_addline2);
        String text135 = "Address Line2 <font color=#ff0000>*</font>";
        et_addline2.setHint(Html.fromHtml(text135));

        et_addline3=(EditText)findViewById(R.id.et_addline3);
        String text136 = "Address Line2 <font color=#ff0000>*</font>";
        et_addline3.setHint(Html.fromHtml(text136));

        et_pincode=(EditText)findViewById(R.id.et_pincode);
        String text138 = "Pincode <font color=#ff0000>*</font>";
        et_pincode.setHint(Html.fromHtml(text138));

        et_landmark=(EditText)findViewById(R.id.et_landmark);
        String text137 = "Landmark <font color=#ff0000>*</font>";
        et_landmark.setHint(Html.fromHtml(text137));

        btn_reset=(Button)findViewById(R.id.btn_reset);
        btn_save=(Button)findViewById(R.id.btn_save);

        spn_state=(Spinner)findViewById(R.id.spn_state);
        spn_city=(Spinner)findViewById(R.id.spn_city);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (et_addline1.getText().toString().equalsIgnoreCase(""))
                {

                    Toast.makeText(AddNewUserAddress.this ,"Please Enter Address Line1" , Toast.LENGTH_SHORT).show();

                }else if(et_addline2.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(AddNewUserAddress.this ,"Please Enter Address Line2" , Toast.LENGTH_SHORT).show();

                }else if(et_addline3.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(AddNewUserAddress.this ,"Please Enter Address Line3" , Toast.LENGTH_SHORT).show();

                }
                else if(et_landmark.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(AddNewUserAddress.this ,"Please Enter Landmark" , Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(AddNewUserAddress.this,"Please Enter Landmark",getLayoutInflater());
                }
                else if(et_pincode.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(AddNewUserAddress.this ,"Please Enter Address Pincode" , Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(AddNewUserAddress.this,"Please Enter Address Pincode",getLayoutInflater());
                }else if (et_pincode.length() < 6) {
                    //  Toast.makeText(Business_Registration.this,"Password minimum 6 character required",Toast.LENGTH_LONG).show();

                    Toast.makeText(AddNewUserAddress.this ,"Pincode 6 character required" , Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(AddNewUserAddress.this, "Pincode 6 character required", getLayoutInflater());
                }
                else if(position_state.toString().equalsIgnoreCase("0"))
                {
                    Toast.makeText(AddNewUserAddress.this ,"Please Select State" , Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(AddNewUserAddress.this,"Please Select State",getLayoutInflater());
                }
                else if(position_city.toString().equalsIgnoreCase("0"))
                {
                    Toast.makeText(AddNewUserAddress.this ,"Please Select City" , Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(AddNewUserAddress.this,"Please Select City",getLayoutInflater());
                }else {

                    add1 = et_addline1.getText().toString();
                    add2 = et_addline2.getText().toString();
                    add3 = et_addline3.getText().toString();
                    lmark = et_landmark.getText().toString();
                    pcode = et_pincode.getText().toString();


                    Bean_Delivery_Address bean_delivery_address = new Bean_Delivery_Address();

                    bean_delivery_address.setAdd1(add1);
                    bean_delivery_address.setAdd2(add2);
                    bean_delivery_address.setAdd3(add3);
                    bean_delivery_address.setLmark(lmark);
                    bean_delivery_address.setPcode(pcode);

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
                spn_state.setSelection(-1);
                spn_city.setSelection(-1);

            }
        });

        adapter_city = new ArrayAdapter<String>(AddNewUserAddress.this, android.R.layout.simple_spinner_item, cityname);
         adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter_city.notifyDataSetChanged();

        spn_city.setAdapter(adapter_city);
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if (position_state.equalsIgnoreCase("0")) {
//GlobalVariable.CustomToast(AddNewUserAddress.this,"Please Select State",getLayoutInflater());
                } else {
                    new send_city_Data().execute();
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
                    //   GlobalVariable.CustomToast(AddNewUserAddress.this,"Please Select City",getLayoutInflater());
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


    }
    public class send_state_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(AddNewUserAddress.this, "");

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

                json = new ServiceHandler().makeServiceCall(GlobalVariable.link + "State/App_GetState", ServiceHandler.POST, parameters);
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
                    Toast.makeText(AddNewUserAddress.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(AddNewUserAddress.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(AddNewUserAddress.this,""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(AddNewUserAddress.this, "" + Message, getLayoutInflater());
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


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(AddNewUserAddress.this, android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_state.setAdapter(adapter_state);

                            }
                        }
                        loadingView.dismiss();



                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class send_city_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        AddNewUserAddress.this, "");

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


                Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(GlobalVariable.link + "City/App_GetCity", ServiceHandler.POST, parameters);
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
            cityid.clear();
            cityname.clear();
            cityname.add("Select City");
            cityid.add("0");
            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(AddNewUserAddress.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(AddNewUserAddress.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(AddNewUserAddress.this,""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(AddNewUserAddress.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);
                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");

                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");

                                cityname.add(cname);
                                cityid.add(cId);


                                 /*adapter_city = new ArrayAdapter<String>(AddNewUserAddress.this, android.R.layout.simple_spinner_item, cityname);
                               // adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //adapter_city.notifyDataSetChanged();

                                spn_city.setAdapter(adapter_city);*/

                            }
                        }
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }
            adapter_city.notifyDataSetChanged();

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
                        AddNewUserAddress.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("user_id", user_id_main));
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

                json = new ServiceHandler().makeServiceCall(GlobalVariable.link + "Address/App_Add_Address", ServiceHandler.POST, parameters);

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
                    Toast.makeText(AddNewUserAddress.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(AddNewUserAddress.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(AddNewUserAddress.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);
                        String Message = jObj.getString("message");
                        //GlobalVariable.CustomToast(AddNewUserAddress.this, "" + Message, getLayoutInflater());
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


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(AddNewUserAddress.this, android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_city.setAdapter(adapter_city);

                            }
                        }*/

                        Intent i = new Intent(AddNewUserAddress.this, SavedAddress.class);
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
        db = new DatabaseHandler(AddNewUserAddress.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid =user_array_from_db.get(i).getId();
            String user_id =user_array_from_db.get(i).getUser_id();
            String email_id = user_array_from_db.get(i).getEmail_id();
            String phone_no =user_array_from_db.get(i).getPhone_no();
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
