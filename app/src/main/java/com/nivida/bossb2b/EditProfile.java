package com.nivida.bossb2b;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {


    String firstname,lastname,emailid,mobile,retailername,autoproduct,functionalname;
    String f_name,l_name,email_id,phone_no,city_name;
    DatabaseHandler db;
    String gender= new String ();
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    String gendernew= new String ();
    ProgressActivity loadingView;
    String json = new String();
    RadioGroup rgbGender;
    int uid;
    String idprofile;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    EditText et_firstname_editprofile,et_lastname_editprofile,et_mobile_editprofile,et_email_editprofile;
    RadioButton rbtnman,rbtnwoman;
    Button btn_update_editprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
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

        if(user_data.size() != 0){

            for (int i = 0; i < user_data.size(); i++) {

                et_firstname_editprofile.setText(user_data.get(i).getF_name());
                et_lastname_editprofile.setText(user_data.get(i).getL_name());
                et_mobile_editprofile.setText(user_data.get(i).getPhone_no());

                et_mobile_editprofile.setText("+91-");
                Selection.setSelection(et_mobile_editprofile.getText(), et_mobile_editprofile.getText().length());
                et_mobile_editprofile.setText(et_mobile_editprofile.getText().toString()+user_data.get(i).getPhone_no());
                et_mobile_editprofile.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!s.toString().contains("+91-")) {
                            et_mobile_editprofile.setText("+91-");
                            Selection.setSelection(et_mobile_editprofile.getText(), et_mobile_editprofile.getText().length());

                        }

                    }
                });
                et_email_editprofile.setText(user_data.get(i).getEmail_id());
                et_email_editprofile.setEnabled(true);



                if (user_data.get(i).getGender().toString().equalsIgnoreCase("0")) {
                    rbtnwoman.setChecked(true);

                    gender = "0";

                } else if (user_data.get(i).getGender().toString().equalsIgnoreCase("1")) {
                    rbtnman.setChecked(true);

                    gender = "1";
                }


            }     }


        rgbGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtn_man)
                {
                    rbtnman.setChecked(true);
                    gendernew = "1";
                    Log.e("man",""+gender.toString());

                } else if  (checkedId == R.id.rbtnwoman)
                {
                    rbtnwoman.setChecked(true);
                    gendernew = "0";
                    Log.e("woman",""+gender.toString());
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
                        EditProfile.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("id", ""+uid));
                parameters.add(new BasicNameValuePair("phone_no",mobile));
                parameters.add(new BasicNameValuePair("first_name", firstname));
                parameters.add(new BasicNameValuePair("last_name", lastname));
                //parameters.add(new BasicNameValuePair("gender", gender));

                Log.e("id", "" + uid);
                Log.e("phone_no", "" + mobile);
                Log.e("first_name", "" + firstname);
                Log.e("last_name", "" + lastname);
                //Log.e("gender", "" + gender);

                json = new ServiceHandler().makeServiceCall(GlobalVariable.link+"User/App_EditProfile",ServiceHandler.POST,parameters);

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

                if (result_1==null
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(EditProfile.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(EditProfile.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(EditProfile.this,""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(EditProfile.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {



                        String Message = jObj.getString("message");
                        Toast.makeText(EditProfile.this,""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(EditProfile.this, "" + Message, getLayoutInflater());
                        setRefershData();

                        String firstname1= et_firstname_editprofile.getText().toString();

                        String lastname1=et_lastname_editprofile.getText().toString();

                        String gender1=gendernew;
                        Log.e("gender1",""+gender1.toString());
                        String phoneno=et_mobile_editprofile.getText().toString().trim().replace("+91-","");
                        loadingView.dismiss();
                        for (int i = 0; i < user_data.size(); i++) {
                            db = new DatabaseHandler(EditProfile.this);
                            db.Update_User_edit(phoneno, firstname1, lastname1, gender1, user_data.get(i).getId());
                            Log.e("Update_User_edit", "" + phoneno + firstname1 + lastname1 + "gender   " + gender1 + "udid    " + user_data.get(i).getId());

                            db.close();
                        }
                        Intent i = new Intent(EditProfile.this, User_Profile.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                       /* if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");

                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");



                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(EditProfile.this,  android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_state_Careers.setAdapter(adapter_state);

                            }
                        }*/


                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }
    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(EditProfile.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            uid =user_array_from_db.get(i).getId();
            String user_id =user_array_from_db.get(i).getUser_id();
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
        et_firstname_editprofile=(EditText)findViewById(R.id.et_firstname_editprofile);
        et_lastname_editprofile=(EditText)findViewById(R.id.et_lastname_editprofile);
        et_mobile_editprofile=(EditText)findViewById(R.id.et_mobile_editprofile);
        et_email_editprofile=(EditText)findViewById(R.id.et_email_editprofile);
        rbtnman=(RadioButton)findViewById(R.id.rbtn_man);
        rbtnwoman=(RadioButton)findViewById(R.id.rbtnwoman);
        rgbGender=(RadioGroup)findViewById(R.id.rgbGender);
        btn_update_editprofile=(Button)findViewById(R.id.btn_update_editprofile);


        String text = "<font color=#00000>First Name</font> <font color=#ff0000>*</font>";
        et_firstname_editprofile.setText(Html.fromHtml(text));

        String text1 = "<font color=#00000>Last Name</font> <font color=#ff0000>*</font>";
        et_lastname_editprofile.setText(Html.fromHtml(text1));

        String text2 = "<font color=#00000>Mobile Number</font> <font color=#ff0000>*</font>";
        et_mobile_editprofile.setText(Html.fromHtml(text2));


        String text3 = "<font color=#00000>Email</font> <font color=#ff0000>*</font>";
        et_email_editprofile.setText(Html.fromHtml(text2));


        rgbGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtn_man) {
                    gender = "1";

                } else if (checkedId == R.id.rbtnwoman) {
                    gender = "0";
                } else {
                    gender = "2";
                }
            }
        });


        btn_update_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_firstname_editprofile.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(EditProfile.this,"First Name is required",Toast.LENGTH_LONG).show();

                    //GlobalVariable.CustomToast(EditProfile.this, "First Name is required", getLayoutInflater());

                } else if (et_lastname_editprofile.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(EditProfile.this,"Last Name is required",Toast.LENGTH_LONG).show();
                  //  GlobalVariable.CustomToast(EditProfile.this, "Last Name is required", getLayoutInflater());

                } else if (et_mobile_editprofile.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(EditProfile.this,"Mobile Number is Compulsory",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(EditProfile.this, "Mobile Number is Compulsory", getLayoutInflater());

                } else if (et_mobile_editprofile.length() < 14) {
                    Toast.makeText(EditProfile.this,"Mobile Number is Compulsory 10 Digit",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(EditProfile.this, "Mobile Number is Compulsory 10 Digit", getLayoutInflater());

                } else if (et_email_editprofile.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(EditProfile.this,"Please enter email address",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(EditProfile.this, "Please enter email address", getLayoutInflater());

                } else if (validateEmail1(et_email_editprofile.getText().toString()) != true) {
                    Toast.makeText(EditProfile.this,"Please enter a valid email address",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(EditProfile.this, "Please enter a valid email address", getLayoutInflater());

                }
                else if(gender.equalsIgnoreCase("2")){
                 //   GlobalVariable.CustomToast(EditProfile.this, "Gender is required", getLayoutInflater());
                     Toast.makeText(EditProfile.this,"Please select gender",Toast.LENGTH_LONG).show();
                }
                else {

                    firstname = et_firstname_editprofile.getText().toString();
                    lastname = et_lastname_editprofile.getText().toString();
                    emailid = et_email_editprofile.getText().toString();
                    mobile = et_mobile_editprofile.getText().toString();
                    mobile=mobile.replace("+91-","");

                    et_mobile_editprofile.setEnabled(true);



                    new update_profiledata().execute();
                }
            }
        });

    }

    private boolean validateEmail1(String email) {
        // TODO Auto-generated method stub
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
