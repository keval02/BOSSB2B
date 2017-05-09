package com.nivida.bossb2b;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nivida.bossb2b.Bean.Bean_ProductCart;
import com.nivida.bossb2b.Bean.Bean_User_data;

import java.util.ArrayList;

public class User_Profile extends AppCompatActivity {



    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    DatabaseHandler db;
    String firstname,lastname,emailid,mobile,retailername,autoproduct;
    String f_name,l_name,email_id,phone_no,city_name;
    TextView tv_username,tv_useremail;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    String user_id_main = new String();
    LinearLayout layout_myprofile,layout_changepassword,layout_savedaddress  , layout_dis_reta_profile;

    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        prefs=new AppPrefs(getApplicationContext());

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


        setRefershData();
        fetchID();
        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {


                user_id_main = user_data.get(i).getUser_id().toString();

            }

        }else{
            user_id_main ="";
        }
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(User_Profile.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid =user_array_from_db.get(i).getId();
            String user_id =user_array_from_db.get(i).getUser_id();
            email_id = user_array_from_db.get(i).getEmail_id();
            phone_no =user_array_from_db.get(i).getPhone_no();
            f_name = user_array_from_db.get(i).getF_name();
            l_name = user_array_from_db.get(i).getL_name();
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
            city_name = user_array_from_db.get(i).getCity_name();
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

    private void fetchID()
    {
        tv_useremail=(TextView)findViewById(R.id.tv_useremail);
        tv_username=(TextView)findViewById(R.id.tv_username);

        layout_myprofile=(LinearLayout)findViewById(R.id.layout_myprofile);
        layout_dis_reta_profile=(LinearLayout)findViewById(R.id.layout_dis_reta_profile);

        layout_changepassword=(LinearLayout)findViewById(R.id.layout_changepassword);
        layout_savedaddress=(LinearLayout)findViewById(R.id.layout_savedaddress);


        layout_savedaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User_Profile.this,SavedAddress.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        layout_dis_reta_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User_Profile.this,Distributor_Retailer_User_Profile.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        layout_myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User_Profile.this,EditProfile.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        layout_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(User_Profile.this,ChangePassword.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        tv_useremail.setText(email_id);
        tv_username.setText(f_name + " " + l_name);



    }
    public void onBackPressed() {
      super.onBackPressed();
    };
}
