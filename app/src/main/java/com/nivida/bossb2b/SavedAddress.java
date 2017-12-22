package com.nivida.bossb2b;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_Address;
import com.nivida.bossb2b.Bean.Bean_ProductCart;
import com.nivida.bossb2b.Bean.Bean_User_data;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SavedAddress extends AppCompatActivity {


    ListView lst_address;
    Button btn_add;
    String json =new String();
    String uid =new String();
    String add_id =new String();

    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ProgressActivity loadingView;
    DatabaseHandler db;
    ArrayList<Bean_Address> array_address = new ArrayList<Bean_Address>();
    ArrayList<String> array_final_address = new ArrayList<String>();
    ResultHolder result_holder;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_address);


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

        setRefershData();

        new get_Address().execute();


    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(SavedAddress.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int userid =user_array_from_db.get(i).getId();
            uid=user_array_from_db.get(i).getUser_id();
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
            contact.setId(userid);
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

    private void fetchID() {

        lst_address =(ListView)findViewById(R.id.listView_addresst);
        btn_add=(Button)findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SavedAddress.this, AddNewUserAddress.class);
                i.putExtra("user_id",uid);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(SavedAddress.this, User_Profile.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    };
    public class get_Address extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        SavedAddress.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", uid));
                Log.e("user_id", uid);



                Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(GlobalVariable.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(GlobalVariable.link+"Address/App_Get_Addresses",ServiceHandler.POST,parameters);
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
                    Toast.makeText(SavedAddress.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();
                    /*GlobalVariable.CustomToast(SavedAddress.this, "SERVER ERROR", SavedAddress.this.getLayoutInflater());*/
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(SavedAddress.this,""+Message,Toast.LENGTH_LONG).show();
                        /*GlobalVariable.CustomToast(Saved_Address.this, "" + Message, Saved_Address.this.getLayoutInflater());*/
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        array_final_address.clear();
                        array_address.clear();
                        JSONArray jarray_data = jObj.getJSONArray("data");

                        for(int i = 0 ; i < jarray_data.length() ; i ++)
                        {
                            JSONObject jobject_sub = jarray_data.getJSONObject(i);
                            JSONObject jobject_address = jobject_sub.getJSONObject("Address");
                            JSONObject jobject_country = jobject_sub.getJSONObject("Country");
                            JSONObject jobject_state = jobject_sub.getJSONObject("State");
                            JSONObject jobject_city = jobject_sub.getJSONObject("City");

                            Bean_Address bean = new Bean_Address();

                            bean.setAddress_id(jobject_address.getString("id"));





                            bean.setAddress1(jobject_address.getString("address_1"));
                            bean.setAddress2(jobject_address.getString("address_2"));
                            bean.setAddress3(jobject_address.getString("address_3"));
                            bean.setLandmark(jobject_address.getString("landmark"));
                            bean.setPincode(jobject_address.getString("pincode"));
                            bean.setCountry(jobject_country.getString("name"));
                            bean.setState(jobject_state.getString("name"));
                            bean.setCity(jobject_city.getString("name"));
                            //bean.setState_id(jobject_state.getString("id"));
                            //bean.setCity_id(jobject_city.getString("id"));
                            bean.setDefault_add(jobject_address.getString("default_address"));


                            array_address.add(bean);

                        }
                        lst_address.setVisibility(View.VISIBLE);
                        lst_address.setAdapter(new CustomResultAdapterDoctor());

                    }
                }}catch(JSONException j){
                j.printStackTrace();
                Log.e("saved addr exce",j.getMessage());
            }
            finally {
                loadingView.dismiss();
            }

        }
    }
    public class CustomResultAdapterDoctor extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return array_address.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            // result_holder = null;

            result_holder = new ResultHolder();

            if (convertView == null) {

                convertView = vi.inflate(R.layout.address_row, null);

            }

            result_holder.tvaddress = (TextView) convertView
                    .findViewById(R.id.address);

            result_holder.img_edit = (ImageView) convertView
                    .findViewById(R.id.add_edit);
            result_holder.img_delete = (ImageView) convertView
                    .findViewById(R.id.add_delete);
            result_holder.img_on = (ImageView) convertView
                    .findViewById(R.id.img_on);

            result_holder.img_on.setTag(array_address.get(position).getAddress_id());

            if(array_address.get(position).getDefault_add().equalsIgnoreCase("1")){
                result_holder.img_on.setImageResource(R.drawable.icon_on);
            }else{
                result_holder.img_on.setImageResource(R.drawable.icon_off);
            }

            result_holder.img_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    add_id= array_address.get(position).getAddress_id();

                    new default_address().execute();


                }
            });
//            if (array_address.get(position).getDefault_add().equalsIgnoreCase("1"))
//            {
//                result_holder.img_delete.setVisibility(View.GONE);
//            }

            if (array_address.size() == 1)
            {
                result_holder.img_delete.setVisibility(View.GONE);
            }
            else
            {
                result_holder.img_delete.setVisibility(View.VISIBLE);
            }

            String final_address = "";
            if(array_address.get(position).getAddress1().equalsIgnoreCase("")||array_address.get(position).getAddress1().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = array_address.get(position).getAddress1();
            }

            if(array_address.get(position).getAddress2().equalsIgnoreCase("")||array_address.get(position).getAddress2().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ array_address.get(position).getAddress2();
            }

            if(array_address.get(position).getAddress3().equalsIgnoreCase("")||array_address.get(position).getAddress3().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+",\n"+ array_address.get(position).getAddress3();
            }

            if(array_address.get(position).getLandmark().equalsIgnoreCase("")||array_address.get(position).getLandmark().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ array_address.get(position).getLandmark();
            }

            if(array_address.get(position).getCity().equalsIgnoreCase("")||array_address.get(position).getCity().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+",\n"+ array_address.get(position).getCity();
            }

            if(array_address.get(position).getPincode().equalsIgnoreCase("")||array_address.get(position).getPincode().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+"-"+ array_address.get(position).getPincode();
            }

            if(array_address.get(position).getState().equalsIgnoreCase("")||array_address.get(position).getState().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ array_address.get(position).getState();
            }

            if(array_address.get(position).getCountry().equalsIgnoreCase("")||array_address.get(position).getCountry().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ array_address.get(position).getCountry();
            }

            result_holder.tvaddress.setText(final_address);

            result_holder.img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(SavedAddress.this, Edit_Address.class);
                    i.putExtra("addId",array_address.get(position).getAddress_id());
                    i.putExtra("add1",array_address.get(position).getAddress1());
                    i.putExtra("add2",array_address.get(position).getAddress2());
                    i.putExtra("add3",array_address.get(position).getAddress3());
                    i.putExtra("pincode",array_address.get(position).getPincode());
                    i.putExtra("state",array_address.get(position).getState_id());
                    i.putExtra("city",array_address.get(position).getCity_id());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });
            result_holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            SavedAddress.this);
                    // Setting Dialog Title
                    alertDialog.setTitle("Delete Address?");
                    // Setting Dialog Message
                    alertDialog
                            .setMessage("Are you sure you want to delete address?");


                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    add_id= array_address.get(position).getAddress_id();

                                    new delete_address().execute();

                                }
                            });
                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // Write your code here to invoke NO event
                                    dialog.cancel();
                                }
                            });
                    // Showing Alert Message
                    alertDialog.show();
                }
            });

            return convertView;
        }

    }
    static class ResultHolder {


        TextView tvaddress;

        ImageView img_edit, img_delete,img_on;


    }
    public class delete_address extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        SavedAddress.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("address_id", add_id));
                Log.e("user_id", uid);

                Log.e("4", "" + parameters);

                json = new ServiceHandler().makeServiceCall(GlobalVariable.link+"Address/App_Remove_Address",ServiceHandler.POST,parameters);
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
                   Toast.makeText(SavedAddress.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(Saved_Address.this, "SERVER ERROR", Saved_Address.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                         Toast.makeText(SavedAddress.this,""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(Saved_Address.this, "" + Message, Saved_Address.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        Toast.makeText(SavedAddress.this,""+Message,Toast.LENGTH_LONG).show();



                        //GlobalVariable.CustomToast(Saved_Address.this, "" + Message, Saved_Address.this.getLayoutInflater());
                        loadingView.dismiss();

                        new get_Address().execute();
                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }
    public class default_address extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        SavedAddress.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", uid));
                parameters.add(new BasicNameValuePair("address_id", add_id));
                Log.e("user_id", uid);



                Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(GlobalVariable.link+"Address/App_Set_Default_Addresses",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
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
                   Toast.makeText(SavedAddress.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(Saved_Address.this, "SERVER ERROR", Saved_Address.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                         Toast.makeText(SavedAddress.this,""+Message,Toast.LENGTH_LONG).show();
                        //GlobalVariable.CustomToast(Saved_Address.this, "" + Message, Saved_Address.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        Toast.makeText(SavedAddress.this,""+Message,Toast.LENGTH_LONG).show();



//                        GlobalVariable.CustomToast(Saved_Address.this, "" + Message, Saved_Address.this.getLayoutInflater());
                        loadingView.dismiss();

                        new get_Address().execute();




                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

}
