package com.nivida.bossb2b;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_Login;
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

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class LoginActivity extends AppCompatActivity {

    boolean isMdevice;
    boolean pstatus;
    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.INTERNET,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    int code = 1;
    AppPref prefs;
    Button btn_login;
    EditText edt_phoneno, edt_pwd;
    List<Bean_Login> array_logins = new ArrayList<>();

    String mobile_no, password;
    DatabaseHandler db;

    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ProgressActivity loadingView;

    TextView txt_forgotpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isMdevice = isMarshmallowPlusDevice();
        pstatus = isPermissionRequestRequired(LoginActivity.this, perms, code);
        prefs = new AppPref(getApplicationContext());

        btn_login = (Button) findViewById(R.id.btn_login);
        edt_pwd = (EditText) findViewById(R.id.edt_pwd);
        edt_phoneno = (EditText) findViewById(R.id.edt_phoneno);
        db = new DatabaseHandler(getApplicationContext());

        txt_forgotpwd = (TextView) findViewById(R.id.txt_forgotpwd);

        txt_forgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, Forgotpassword.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String MobilePattern = "[0-9]{10}";

                if (!validateEmail1(edt_phoneno.getText().toString()) && !edt_phoneno.getText().toString().matches(MobilePattern)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your  Valid Email Id or Mobile no Here!!", Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(LoginActivity.this, "Please Enter Your  Valid Email Id Here!!", getLayoutInflater());
                }


                /*if (edt_phoneno.getText().toString().length() == 0) {


                    edt_phoneno.requestFocus();


                    Toast.makeText(getApplicationContext(), "Please Enter Your Mobile No Here!!", Toast.LENGTH_SHORT).show();


                } else if (!edt_phoneno.getText().toString().matches(MobilePattern)) {
                    edt_phoneno.requestFocus();

                    Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();

                }*/
                else if (edt_pwd.getText().toString().length() == 0) {
                    edt_pwd.requestFocus();

                    Toast.makeText(getApplicationContext(), "Please enter Your Valid Password", Toast.LENGTH_SHORT).show();
                    // GlobalVariable.CustomToast(LoginActivity.this, "Please enter Your Valid Password", getLayoutInflater());

                } else {


                    mobile_no = edt_phoneno.getText().toString();
                    password = edt_pwd.getText().toString();


                    if (Internet.isConnectingToInternet(getApplicationContext())) {
                        new WebLoging(mobile_no, password).execute();

                    } else {
                        Internet.noInternet(getApplicationContext());


                    }


                    //Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // startActivity(intent);

                }

            }
        });


    }

    private boolean validateEmail1(String email) {

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }


    class WebLoging extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String mobile_no = "";
        String password = "";


        public WebLoging(String mobile_no, String password) {

            this.mobile_no = mobile_no;
            this.password = password;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(LoginActivity.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("mobile_no", mobile_no));
            pairs.add(new BasicNameValuePair("password", password));

            Log.e("email", "" + mobile_no);
            Log.e("password", "" + password);
            String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.DSRUSER_LOGIN, ServiceHandler.POST, pairs);
            Log.e("Link", "" + Web.LINK + Web.DSRUSER_LOGIN);


            return json;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            loadingView.dismiss();
          /*  Log.e("json", result_1);*/
            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);
                Log.e("Link", "" + result_1);
                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(LoginActivity.this, "Server Error",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(LoginActivity.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    boolean status = jObj.getBoolean("status");
                    if (!status) {
                        String Message = jObj.getString("message");
                        //GlobalVariable.CustomToast(LoginActivity.this, "" + Message, getLayoutInflater());
                        Toast.makeText(LoginActivity.this, "" + Message, Toast.LENGTH_LONG).show();

                    } else {

                        String Message = jObj.getString("message");
                        //GlobalVariable.CustomToast(LoginActivity.this, "" + Message, getLayoutInflater());
                        Toast.makeText(LoginActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                        JSONObject jO = jObj.getJSONObject("data");
                        JSONObject jU = jO.getJSONObject("User");

                        String user_id = jU.getString("id");
                        System.out.println(jU.getString("id"));


                        prefs.setUser_id(jU.getString("id"));
                        prefs.setLoggedIn(true);
                        Log.e("user_id", "" + user_id);

                        /*Toast.makeText(LoginActivity.this, "--"+jU.getString("id"), Toast.LENGTH_SHORT).show();*/
                        String role_id = jU.getString("role_id");
                        prefs.setRole_id(jU.getString("role_id"));
                        Log.e("role_id", prefs.getRole_id());


                        String user_name = jU.getString("user_name");
                        prefs.setUser_name(jU.getString("user_name"));
                        String email_id = jU.getString("email_id");
                        prefs.setEmail_id(jU.getString("email_id"));
                        String phone_no = jU.getString("phone_no");
                        prefs.setPhone_no(jU.getString("phone_no"));

                        String mobile_no = jU.getString("mobile_no");
                        prefs.setMobile_no(jU.getString("mobile_no"));
                        String f_name = jU.getString("first_name");
                        prefs.setFirst_name(jU.getString("first_name"));
                        Log.e("first_name", "" + f_name);
                        String m_name = jU.getString("middle_name");

                        String l_name = jU.getString("last_name");
                        prefs.setLast_name(jU.getString("last_name"));
                        String password = jU.getString("password");
                        prefs.setPassword(jU.getString("password"));
                        String gender = jU.getString("gender");

                        String user_type = jU.getString("user_type");
                        String login_with = jU.getString("login_with");
                        String str_rid = jU.getString("social_login_id");
                        String str_response = jU.getString("social_login_response");
                        String owner_id = jU.getString("owner_id");
                        prefs.setOwner_id(jU.getString("owner_id"));
                        Log.e("owner_id", jU.getString("owner_id"));
                        String device_id = jU.getString("device_id");

                        String change_password = jU.getString("change_password");
                        prefs.setChange_password(jU.getString("change_password"));
                        Log.e("Change Password", prefs.getChange_password());

                        JSONObject distributor = jO.getJSONObject("Distributor");

                        prefs.setFirmShopName(distributor.getString("firm_shop_name"));


                        if (change_password.equalsIgnoreCase("1")) {

                            prefs.setPasswordChanged(true);

                        }
                        Log.e("isPasswordChanged", change_password);


                        String party_code = jU.getString("party_code");
                        String TfatUrl = "";

                        if (jObj.has("tfat_ip"))
                            TfatUrl = jObj.getString("tfat_ip");


                        setRefershData();
                        // Toast.makeText(Login_Boss.this, "--"+user_data.size(), Toast.LENGTH_SHORT).show();
                        System.out.println("UserSize--- " + user_data.size());
                        Log.e("1212121212", "" + user_id);
                        if (user_data.size() == 0) {
                            db = new DatabaseHandler(LoginActivity.this);
                            db.Add_Contact(new Bean_User_data(user_id, email_id, phone_no, f_name, l_name, password, gender, user_type, login_with, str_rid, "", "", "", "", "", "", "", "", "", role_id, str_response));

                            db.close();
                        }

                        prefs = new AppPref(LoginActivity.this);
                        //  prefs.setLoginWith(login_with);
                        prefs.setUser_name(user_name);
                        prefs.setEmail_id(email_id);
                        prefs.setPhone_no(phone_no);
                        prefs.setMobile_no(mobile_no);
                        prefs.setMiddle_name(m_name);
                        prefs.setLast_name(l_name);
                        prefs.setPassword(password);
                        prefs.setGender(gender);
                        prefs.setDevice_id(device_id);
                        prefs.setRole_id(role_id);
                        prefs.setOwner_id(owner_id);
                        prefs.setUser_type(user_type);
                        prefs.setSocial_login_id(str_rid);
                        prefs.setSocial_login_responce(str_response);
                        //  prefs.setPartycode(party_code);
                        //prefs.setTfatUrl(TfatUrl);
                        loadingView.dismiss();


                        prefs = new AppPref(LoginActivity.this);
                        Log.e("Change Password", prefs.getChange_password());
                        if (prefs.isPasswordChanged() == true) {
                            Intent i = new Intent(LoginActivity.this, ChangePasswordForFirstTime.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        } else {
                            if (prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                prefs.isSales_has_user_selected();
                                startActivity(i);
                            } else if (prefs.getRole_id().equalsIgnoreCase(C.DIS_SALES_ROLE)) {
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                prefs.isSales_has_user_selected();
                                startActivity(i);
                            } else if (prefs.getRole_id().equalsIgnoreCase(C.DISTRIBUTOR_ROLE)) {

                                Intent intent = new Intent(LoginActivity.this, DistributorLogin.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();


                            }


                        }


                        finish();
                    }
                }

            } catch (JSONException j) {
                Log.e("exception", j.getMessage());
                j.printStackTrace();
            }

        }
    }


    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(LoginActivity.this);

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


    public static boolean isMarshmallowPlusDevice() {

        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isPermissionRequestRequired(Activity activity, @NonNull String[] permissions, int requestCode) {
        if (isMarshmallowPlusDevice() && permissions.length > 0) {
            List<String> newPermissionList = new ArrayList<>();
            for (String permission : permissions) {
                if (PERMISSION_GRANTED != activity.checkSelfPermission(permission)) {
                    newPermissionList.add(permission);
                }
            }
            if (newPermissionList.size() > 0) {
                activity.requestPermissions(newPermissionList.toArray(new String[newPermissionList.size()]), requestCode);
                return true;
            }
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}





