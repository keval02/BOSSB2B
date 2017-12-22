package com.nivida.bossb2b;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class corporate_office extends Fragment {

    EditText e_fname,e_lname,e_email,e_mobile,e_msg;

    String s_fname = new String();
    String s_lname = new String();
    String s_email = new String();
    String s_mobile = new String();
    String s_msg = new String();

    Button btn_submit;

    Spinner sp_state, sp_city;

    String position_state = new String();
    String position_city = new String();

    String position_statename = new String();
    String position_cityname = new String();

    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();

    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();

    TextView tvfname,tvlname,tvemail,tvmobile,tvstate,tvcity,tvmessage , txt_phone;

    View rootView;

    ProgressActivity loadingView;

    String json = new String();

    ArrayAdapter<String> adapter_city;

    DatabaseHandler db;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_corporate_office, container, false);

        fetchId();

        return  rootView;
    }

    private void fetchId() {

        e_fname=(EditText)rootView.findViewById(R.id.etfirst_name_corpo_offi);
        e_lname=(EditText)rootView.findViewById(R.id.etlast_name_corpo_offi);
        e_email=(EditText)rootView.findViewById(R.id.Et_Emailid_corpo_offi);
        e_mobile=(EditText)rootView.findViewById(R.id.etmobile_corpo_offi);
        e_msg=(EditText)rootView.findViewById(R.id.etmessage_corpo_offi);
        sp_state=(Spinner) rootView.findViewById(R.id.Spin_State_direct);
        sp_city=(Spinner)rootView.findViewById(R.id.Spin_City_direct);
        btn_submit=(Button)rootView.findViewById(R.id.co_submit);

        tvfname= (TextView) rootView.findViewById(R.id.tvfname1);
        tvlname= (TextView) rootView.findViewById(R.id.tvlname);
        tvemail= (TextView) rootView.findViewById(R.id.tvemail);
        tvmobile=(TextView)rootView.findViewById(R.id.tvmobile);
        tvstate= (TextView) rootView.findViewById(R.id.tvstate);
        tvcity = (TextView) rootView.findViewById(R.id.tvcity);
        tvmessage= (TextView) rootView.findViewById(R.id.tvmessage);
        txt_phone= (TextView) rootView.findViewById(R.id.txt_phone);

        String text = "First Name <font color=#ff0000>*</font>";
        tvfname.setText(Html.fromHtml(text));
        e_fname.setHint(Html.fromHtml(text));

        String text1 = "Last Name <font color=#ff0000>*</font>";
        tvlname.setText(Html.fromHtml(text1));
        e_lname.setHint(Html.fromHtml(text1));

        String text2 = "Email <font color=#ff0000>*</font>";
        tvemail.setText(Html.fromHtml(text2));
        e_email.setHint(Html.fromHtml(text2));

        String textm = "Mobile No. <font color=#ff0000>*</font>";
        tvmobile.setText(Html.fromHtml(textm));
        e_mobile.setHint(Html.fromHtml(textm));

        String text3 = "State <font color=#ff0000>*</font>";
        tvstate.setText(Html.fromHtml(text3));

        String text4 = "City <font color=#ff0000>*</font>";
        tvcity.setText(Html.fromHtml(text4));

        String text5 = "Message <font color=#ff0000>*</font>";
        tvmessage.setText(Html.fromHtml(text5));
        e_msg.setHint(Html.fromHtml(text5));

        setRefershData();
        if(user_data.size() == 0){

        }else
        {
            for (int i = 0; i < user_data.size(); i++) {

                e_fname.setText(user_data.get(i).getF_name());
                e_lname.setText(user_data.get(i).getL_name());
                e_mobile.setText(user_data.get(i).getPhone_no());

                e_mobile.setText("+91-");
                Selection.setSelection( e_mobile.getText(),  e_mobile.getText().length());
                e_mobile.setText(e_mobile.getText().toString()+user_data.get(i).getPhone_no());
                e_mobile.addTextChangedListener(new TextWatcher() {

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
                            e_mobile.setText("+91-");
                            Selection.setSelection( e_mobile.getText(),  e_mobile.getText().length());

                        }

                    }
                });
                e_email.setText(user_data.get(i).getEmail_id());

            }
        }
        stateid.clear();
        statename.clear();
        cityid.clear();
        cityname.clear();

        adapter_city = new ArrayAdapter<String>(getActivity(),  R.layout.custom_spinner_size, cityname);
         adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(adapter_city);

        Log.e("abc","abc");
        new send_state_Data().execute();


        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if(position_state.equalsIgnoreCase("0") || sp_state.getSelectedItemPosition()==0){
                    cityid.clear();
                    cityname.clear();

                    sp_city.setSelection(0);
                    sp_city.setVisibility(View.GONE);
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

        txt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getMobileNo = txt_phone.getText().toString().trim();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + getMobileNo));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });






        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (e_fname.getText().toString().trim().equalsIgnoreCase("")) {

                    Toast.makeText(getActivity(),"Please enter first name",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(getActivity(), "First Name is required", getActivity().getLayoutInflater());
                } else if (e_lname.getText().toString().trim().equalsIgnoreCase("")) {
                    //GlobalVariable.CustomToast(getActivity(), "Last Name is required", getActivity().getLayoutInflater());
                     Toast.makeText(getActivity(),"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (e_mobile.getText().toString().trim().equalsIgnoreCase("")) {
                    //GlobalVariable.CustomToast(getActivity(), "Mobile Number is Compulsory", getActivity().getLayoutInflater());
                    Toast.makeText(getActivity(),"Please enter mobile no",Toast.LENGTH_LONG).show();
                } else if (e_mobile.length() < 14) {
                    Toast.makeText(getActivity(),"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(getActivity(), "Mobile Number is Compulsory 10 Digit", getActivity().getLayoutInflater());
                } else if (e_email.getText().toString().trim().equalsIgnoreCase("")) {
                    //GlobalVariable.CustomToast(getActivity(), "Please enter email address", getActivity().getLayoutInflater());
                     Toast.makeText(getActivity(),"Please enter email address",Toast.LENGTH_LONG).show();
                } else if (validateEmail1(e_email.getText().toString()) != true) {
                    Toast.makeText(getActivity(),"Please enter valid email address",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(getActivity(), "Please enter a valid email address", getActivity().getLayoutInflater());
                }else if (position_state.equalsIgnoreCase("0")) {
                    Toast.makeText(getActivity(),"Please Select State",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(getActivity(), "Please Select State", getActivity().getLayoutInflater());
                } else if (position_city.equalsIgnoreCase("0")) {
                    Toast.makeText(getActivity(),"Please Select city",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(getActivity(), "Please Select city", getActivity().getLayoutInflater());
                }else if (e_msg.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(),"Please Enter Message",Toast.LENGTH_LONG).show();
                    //GlobalVariable.CustomToast(getActivity(), "Please Enter Message", getActivity().getLayoutInflater());
                }   else {

                    s_fname = e_fname.getText().toString().trim();
                    s_lname = e_lname.getText().toString().trim();
                    s_email = e_email.getText().toString().trim();
                    s_mobile = e_mobile.getText().toString().trim();
                    s_msg = e_msg.getText().toString().trim();

                    new Send_coenquiry_data().execute();

                }
            }
        });


    }
    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(getActivity());

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

       // Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

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
    private boolean validateEmail1(String email) {
        // TODO Auto-generated method stub

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public class Send_coenquiry_data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        getActivity(), "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("first_name", s_fname));
                parameters.add(new BasicNameValuePair("last_name", s_lname));
                parameters.add(new BasicNameValuePair("email", s_email));
                parameters.add(new BasicNameValuePair("contact_no", s_mobile));
                parameters.add(new BasicNameValuePair("message", s_msg));
                parameters.add(new BasicNameValuePair("city_name", position_cityname));
                parameters.add(new BasicNameValuePair("state_name", position_statename));

                Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(GlobalVariable.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(GlobalVariable.link+"CorporateOffice/App_AddCorporateOffice",ServiceHandler.POST,parameters);
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
                    Toast.makeText(getActivity(), "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(getActivity(), "SERVER ERROR", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(getActivity(),""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(getActivity(), "" + Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        Toast.makeText(getActivity(),""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(getActivity(), "" + Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }



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
                        getActivity(), "");

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

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_STATE, ServiceHandler.POST, parameters);
                // json = new ServiceHandler().makeServiceCall("http://www.bossindia.com/State/App_GetState",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);

                Log.e("abc",""+Web.LINK+Web.GET_STATE);
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
                    Toast.makeText(getActivity(), "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(getActivity(), "SERVER ERROR", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(getActivity(),""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(getActivity(),""+Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        // //GlobalVariable.CustomToast(getActivity(),"", getActivity().getLayoutInflater());
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


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(),  R.layout.custom_spinner_size, statename);
                                 adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_state.setAdapter(adapter_state);

                            }
                        }
                        loadingView.dismiss();
                        //new send_city_Data().execute();
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
                        getActivity(), "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("state_id",""+ position_state));

                Log.e("", "" + parameters);

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_CITY, ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
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
            cityid.clear();
            cityname.clear();
            cityname.add("Select City");
            cityid.add("0");
            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(getActivity(), "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(getActivity(), "SERVER ERROR", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(getActivity(),""+Message,Toast.LENGTH_LONG).show();
                        ////GlobalVariable.CustomToast(getActivity(),""+Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");

                            sp_city.setVisibility(View.VISIBLE);
                            sp_city.setSelection(0);
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");

                                cityname.add(cname);
                                cityid.add(cId);

                            }
                        }
                        loadingView.dismiss();

                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }
            adapter_city.notifyDataSetChanged();
        }
    }
}
