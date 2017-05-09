package com.nivida.bossb2b;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.BeanVendorName;
import com.nivida.bossb2b.Bean.Bean_ProductCart;
import com.nivida.bossb2b.Bean.Bean_User_data;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChangePassword extends AppCompatActivity {

    String firstname,lastname,emailid,mobile,retailername,autoproduct,functionalname;
    String f_name,l_name,email_id,phone_no,city_name, gender;
    String uid;
    DatabaseHandler db;
    ProgressActivity loadingView;
    String json = new String();
    RadioGroup rgbGender;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    EditText et_firstname_editprofile,et_lastname_editprofile,et_mobile_editprofile,et_email_editprofile;
    RadioButton rbtnman,rbtnwoman;
    Button btn_update_editprofile;
    Button Change;
    Button btn_change;
    String oldpassword,newpassword;
    EditText etold_pass,etnew_pass,etconf_pass;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        setRefershData();

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


        fetchID();

        Change = (Button) findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etold_pass.getText().toString().trim().equalsIgnoreCase("")) {
                   // GlobalVariable.CustomToast(ChangePassword.this, "Password is compulsory", getLayoutInflater());
                     Toast.makeText(ChangePassword.this,"Please enter password",Toast.LENGTH_LONG).show();
                } else if (etold_pass.length() < 6) {
                     Toast.makeText(ChangePassword.this,"Password minimum 6 character required",Toast.LENGTH_LONG).show();
                   // GlobalVariable.CustomToast(ChangePassword.this, "Password minimum 6 character required", getLayoutInflater());
                } else if (etnew_pass.getText().toString().trim().equalsIgnoreCase("")) {
                    //GlobalVariable.CustomToast(ChangePassword.this, "Password is compulsory", getLayoutInflater());
                     Toast.makeText(ChangePassword.this,"Please enter New password",Toast.LENGTH_LONG).show();
                } else if (etnew_pass.length() < 6) {
                      Toast.makeText(ChangePassword.this,"Password minimum 6 character required",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(ChangePassword.this, "Password minimum 6 character required", getLayoutInflater());
                } else if (etconf_pass.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(ChangePassword.this,"Please enter confirm password", Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(ChangePassword.this, "Please enter confirm password", getLayoutInflater());
                } else if (!etnew_pass.getText().toString().trim().equals(etconf_pass.getText().toString().trim())) {
                      Toast.makeText(ChangePassword.this,"Confirm password did not match",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(ChangePassword.this, "Confirm password did not match", getLayoutInflater());
                } else {



                    oldpassword=etold_pass.getText().toString();
                    newpassword=etnew_pass.getText().toString();


                    new update_profiledata().execute();

                }


            }
        });

    }
    public class update_profiledata extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        ChangePassword.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("id",""+uid));

                parameters.add(new BasicNameValuePair("old_password",oldpassword ));
                parameters.add(new BasicNameValuePair("new_password",newpassword ));



                Log.e("id", "" + uid);
                Log.e("oldpassword", "" + oldpassword);
                Log.e("newpassword", "" + newpassword);

                json = new ServiceHandler().makeServiceCall(GlobalVariable.link+"User/App_ChangePassword",ServiceHandler.POST,parameters);

                System.out.println("array: " + json);
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
                        || result_1.isEmpty()) {
                    /*Toast.makeText(ChangePassword.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    GlobalVariable.CustomToast(ChangePassword.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(ChangePassword.this,""+Message,Toast.LENGTH_LONG).show();
                       // GlobalVariable.CustomToast(ChangePassword.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);
                        String Message = jObj.getString("message");
                        Toast.makeText(ChangePassword.this,""+Message,Toast.LENGTH_LONG).show();
                       // GlobalVariable.CustomToast(ChangePassword.this,""+Message, getLayoutInflater());
                        Intent i = new Intent(ChangePassword.this, User_Profile.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        loadingView.dismiss();

                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }




    private void fetchID()
    {
        etold_pass=(EditText)findViewById(R.id.etold_pass);
                etnew_pass=(EditText)findViewById(R.id.etnew_pass);

        etconf_pass=(EditText)findViewById(R.id.etconf_pass);

        btn_change=(Button)findViewById(R.id.btn_change);
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(ChangePassword.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            //uid =user_array_from_db.get(i).getId();

            uid =user_array_from_db.get(i).getUser_id();
            email_id = user_array_from_db.get(i).getEmail_id();
            phone_no =user_array_from_db.get(i).getPhone_no();
            f_name = user_array_from_db.get(i).getF_name();
            l_name = user_array_from_db.get(i).getL_name();
            String password = user_array_from_db.get(i).getPassword();
            gender = user_array_from_db.get(i).getGender();
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
            city_name = user_array_from_db.get(i).getCity_name();
            String str_response = user_array_from_db.get(i).getStr_response();


            Bean_User_data contact = new Bean_User_data();
            contact.setId(1);
            contact.setUser_id(uid);
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
