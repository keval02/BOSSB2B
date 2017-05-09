package com.nivida.bossb2b;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.BeanReatailerHierarchy;
import com.nivida.bossb2b.Bean.BeanShowHierarchy;
import com.nivida.bossb2b.adapter.DistributorListAdapter;
import com.nivida.bossb2b.adapter.RatailerListAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

public class Distributor_Retailer_User_Profile extends AppCompatActivity {
    ProgressActivity loadingView;
    AppPref pref;
    String json = "";

    ListView listview , reailerlist;

    List<BeanShowHierarchy> showHierarchies = new ArrayList<BeanShowHierarchy>();
    List<BeanReatailerHierarchy> reatailerHierarchies = new ArrayList<BeanReatailerHierarchy>();
    DistributorListAdapter distributorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor__retailer__user__profile);

        pref = new AppPref(getApplicationContext());


        listview = (ListView) findViewById(R.id.listview);

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

        new get_dis_ret_name().execute();

    }

    public class get_dis_ret_name extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(Distributor_Retailer_User_Profile.this, "");

                loadingView.setCancelable(false);
                loadingView.show();


            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {


            try {

                List<NameValuePair> parameters = new ArrayList<>();
                if (pref.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {

                    parameters.add(new BasicNameValuePair("sales_per_id", pref.getUser_id()));
                    parameters.add(new BasicNameValuePair("role_id", pref.getRole_id()));

                    Log.e("Parameters", "" + parameters);


                } else {


                    parameters.add(new BasicNameValuePair("sales_per_id", pref.getUser_id()));
                    parameters.add(new BasicNameValuePair("role_id", pref.getRole_id()));
                    parameters.add(new BasicNameValuePair("owner_id", pref.getOwner_id()));

                    Log.e("Parameters", "" + parameters);

                }

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.HIERARCHY, ServiceHandler.POST, parameters);

                System.out.println("List" + json);
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
            loadingView.dismiss();

            try {
                if (result_1.equalsIgnoreCase("")) {
                    Toast.makeText(Distributor_Retailer_User_Profile.this, "SERVER ERROR", Toast.LENGTH_LONG).show();
                    loadingView.dismiss();

                } else {

                    JSONObject main = new JSONObject(result_1);




                    if (!main.getBoolean("status")) {

                        String message = main.getString("message");
                        Toast.makeText(Distributor_Retailer_User_Profile.this, "" + message, Toast.LENGTH_SHORT).show();

                        Log.e("messsage", "" + message);


                    } else {

                        String message = main.getString("message");
                        Toast.makeText(Distributor_Retailer_User_Profile.this, "" + message, Toast.LENGTH_SHORT).show();

                        Log.e("messsage", "" + message);

                        Log.e("Data",main.toString());


                        JSONArray dataArray = main.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {

                            JSONObject arrayObject = dataArray.getJSONObject(i);

                            BeanShowHierarchy showHierarchy = new BeanShowHierarchy();
                            showHierarchy.setFirm_shop_name(arrayObject.getString("firm_shop_name"));


                            JSONArray retailers = arrayObject.getJSONArray("Retailers");



                            List<BeanReatailerHierarchy> reatailerHierarchies=new ArrayList<>();

                            Log.e("name" , arrayObject.getString("firm_shop_name"));
                           for (int j = 0; j < retailers.length(); j++){

                               BeanReatailerHierarchy reatailerHierarchy = new BeanReatailerHierarchy();
                                JSONObject retailermainObject = retailers.getJSONObject(j);

                                JSONObject userObject = retailermainObject.getJSONObject("User");

                               reatailerHierarchy.setId(userObject.getString("id"));
                               reatailerHierarchy.setFirst_name(userObject.getString("first_name"));
                               reatailerHierarchy.setLast_name(userObject.getString("last_name"));
                               reatailerHierarchy.setEmail_id(userObject.getString("email_id"));
                               reatailerHierarchy.setPhone_no(userObject.getString("phone_no"));


                               JSONObject addressObject = retailermainObject.getJSONObject("Address");

                               reatailerHierarchy.setAddress_1(addressObject.getString("address_1"));
                               reatailerHierarchy.setAddress_2(addressObject.getString("address_2"));
                               reatailerHierarchy.setAddress_3(addressObject.getString("address_3"));


                               JSONObject cityObject = addressObject.getJSONObject("City");

                               reatailerHierarchy.setCity_name(cityObject.getString("name"));


                               JSONObject distributorObject = retailermainObject.getJSONObject("Distributor");

                               reatailerHierarchy.setFirm_shop_name(distributorObject.getString("firm_shop_name"));

                               reatailerHierarchies.add(reatailerHierarchy);

                              /* reatailerHierarchies.add(reatailerHierarchy);*/

                            }

                            showHierarchy.setReatailerHierarchies(reatailerHierarchies);



                            showHierarchies.add(showHierarchy);


                        }



                        Log.e("333330" , "djkjjkjdk");

                        distributorListAdapter = new DistributorListAdapter(showHierarchies, getApplicationContext(), Distributor_Retailer_User_Profile.this);
                        distributorListAdapter.notifyDataSetChanged();

                        listview.setAdapter(distributorListAdapter);




                        Log.e("333330" , "djkjjkjdk");
                    }


                }


            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("excePtioN", j.getMessage());
            }
        }
    }


}
