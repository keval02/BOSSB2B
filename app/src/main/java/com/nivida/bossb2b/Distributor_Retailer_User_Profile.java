package com.nivida.bossb2b;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.BeanReatailerHierarchy;
import com.nivida.bossb2b.Bean.BeanShowHierarchy;
import com.nivida.bossb2b.adapter.DistributorListAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Distributor_Retailer_User_Profile extends AppCompatActivity {
    ProgressActivity loadingView;
    AppPref pref;
    String json = "";

    ListView listview, reailerlist;

    public static Boolean isScrolling = true;

    List<BeanShowHierarchy> showHierarchies = new ArrayList<BeanShowHierarchy>();
    List<BeanReatailerHierarchy> reatailerHierarchies = new ArrayList<BeanReatailerHierarchy>();
    DistributorListAdapter distributorListAdapter;

    TextView txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor__retailer__user__profile);

        pref = new AppPref(getApplicationContext());


        listview = (ListView) findViewById(R.id.listview);
        txt_title = (TextView) findViewById(R.id.txt_title);



        if(pref.getRole_id().equalsIgnoreCase(C.DIS_SALES_ROLE))
            txt_title.setText("Retailer Info");

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

                        Log.e("Data", main.toString());


                        JSONArray dataArray = main.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {

                            JSONObject arrayObject = dataArray.getJSONObject(i);
                            JSONArray mainDistributor = arrayObject.getJSONArray("MainDistributor");


                            BeanShowHierarchy showHierarchy = new BeanShowHierarchy();
                            for (int k = 0; k < mainDistributor.length(); k++) {


                                JSONObject underDistObject = mainDistributor.getJSONObject(k);

                                JSONObject userObject = underDistObject.getJSONObject("User");


                                showHierarchy.setFirst_name(userObject.getString("first_name"));
                                showHierarchy.setLast_name(userObject.getString("last_name"));
                                showHierarchy.setEmail_id(userObject.getString("email_id"));
                                showHierarchy.setMobile_no(userObject.getString("mobile_no"));
                                showHierarchy.setPhone_no(userObject.getString("phone_no"));


                                JSONObject addressObject = underDistObject.getJSONObject("Address");

                                showHierarchy.setAddress_1(addressObject.getString("address_1"));
                                showHierarchy.setAddress_2(addressObject.getString("address_2"));
                                showHierarchy.setAddress_3(addressObject.getString("address_3"));
                                showHierarchy.setPincode(addressObject.getString("pincode"));


                                JSONObject cityObject = addressObject.getJSONObject("City");

                                showHierarchy.setName(cityObject.getString("name"));


                                JSONObject distributorObject = underDistObject.getJSONObject("Distributor");

                                showHierarchy.setFirm_shop_name(distributorObject.getString("firm_shop_name"));


                                showHierarchies.add(showHierarchy);

                                //JSONObject retailerMainssObject = mainDistributor.getJSONObject(k);
                                //JSONArray retailers = mainDistributor.getJSONArray("Retailers");

                                JSONArray retailers = underDistObject.getJSONArray("Retailers");


                                List<BeanReatailerHierarchy> reatailerHierarchies = new ArrayList<>();


                                for (int j = 0; j < retailers.length(); j++) {

                                    BeanReatailerHierarchy reatailerHierarchy = new BeanReatailerHierarchy();
                                    JSONObject retailermainObject = retailers.getJSONObject(j);

                                    Log.e("Log2", "Log2");
                                    JSONObject userssObject = retailermainObject.getJSONObject("User");

                                    Log.e("User", "2ndUser");

                                    reatailerHierarchy.setId(userssObject.getString("id"));
                                    reatailerHierarchy.setFirst_name(userssObject.getString("first_name"));
                                    reatailerHierarchy.setLast_name(userssObject.getString("last_name"));
                                    reatailerHierarchy.setEmail_id(userssObject.getString("email_id"));
                                    reatailerHierarchy.setPhone_no(userssObject.getString("phone_no"));
                                    reatailerHierarchy.setMobile_no(userssObject.getString("mobile_no"));


                                    JSONObject addressObjects = retailermainObject.getJSONObject("Address");

                                    reatailerHierarchy.setAddress_1(addressObjects.getString("address_1"));
                                    reatailerHierarchy.setAddress_2(addressObjects.getString("address_2"));
                                    reatailerHierarchy.setAddress_3(addressObjects.getString("address_3"));
                                    reatailerHierarchy.setPincode(addressObject.getString("pincode"));


                                    JSONObject citysObject = addressObject.getJSONObject("City");

                                    reatailerHierarchy.setCity_name(citysObject.getString("name"));


                                    JSONObject distributorsObject = retailermainObject.getJSONObject("Distributor");

                                    reatailerHierarchy.setFirm_shop_name(distributorsObject.getString("firm_shop_name"));

                                    reatailerHierarchies.add(reatailerHierarchy);

                              /* reatailerHierarchies.add(reatailerHierarchy);*/

                                }

                                showHierarchy.setReatailerHierarchies(reatailerHierarchies);

                            }


                        }


                        Log.e("333330", "djkjjkjdk");

                        distributorListAdapter = new DistributorListAdapter(showHierarchies, getApplicationContext(), Distributor_Retailer_User_Profile.this);
                        distributorListAdapter.notifyDataSetChanged();

                        listview.setAdapter(distributorListAdapter);


                        Log.e("333330", "djkjjkjdk");
                    }


                }


            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("excePtioN", j.getMessage());
            }
        }
    }


}
