package com.nivida.bossb2b;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.BeanVendorName;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RetailerReport extends AppCompatActivity {

    Spinner spinner_type, spinner_persons;

    ListView list_report;

    ProgressDialog loadingView;

    AppPref prefs;

    ImageView img_search_filter;

    LinearLayout linear_manuallist;

    ArrayList<String> userTypes = new ArrayList<>();
    ArrayList<String> personIDs = new ArrayList<>();
    ArrayList<String> personNames = new ArrayList<>();

    List<BeanVendorName> vendorNames = new ArrayList<BeanVendorName>();

    List<BeanFilterHistory> filterHistories = new ArrayList<BeanFilterHistory>();


    ArrayAdapter<String> adapterType;
    ArrayAdapter<String> adapterPersons;

    VendorwiseHistoryListAdapter vendorListAdapter;

    EditText txt_search_by_company;

    AutoCompleteTextView autoTextView;

    ArrayAdapter<String> namesAdapter;
    ArrayList<String> distNames = new ArrayList<>();
    ArrayList<String> disIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_report);

        spinner_type = (Spinner) findViewById(R.id.spinner_type);
        spinner_persons = (Spinner) findViewById(R.id.spinner_persons);
        img_search_filter = (ImageView) findViewById(R.id.img_search_filter);
        linear_manuallist = (LinearLayout) findViewById(R.id.linear_manuallist);

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

        namesAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.auto_textview, distNames);

        prefs = new AppPref(getApplicationContext());
        adapterType = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_spinner_item, userTypes);
        adapterPersons = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_spinner_item, personNames);
        list_report = (ListView) findViewById(R.id.list_retailer_report);
        txt_search_by_company = (EditText) findViewById(R.id.txt_searchby_company);
        autoTextView = (AutoCompleteTextView) findViewById(R.id.autoTextView);
        autoTextView.setAdapter(namesAdapter);
        vendorListAdapter = new VendorwiseHistoryListAdapter(getApplicationContext(), vendorNames, RetailerReport.this);

        autoTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(autoTextView.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                return true;
            }
        });


        autoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Item Selected", position + "--");

                int mPosition = 0;

                for (int i = 0; i < distNames.size(); i++) {
                    if (autoTextView.getText().toString().equalsIgnoreCase(distNames.get(i))) {
                        mPosition = i;
                        break;
                    }
                }

                new app_GetAllhistory(disIDs.get(mPosition)).execute();
            }
        });

        autoTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("com_sales_per_id", prefs.getUser_id()));
        parameters.add(new BasicNameValuePair("filter_name", ""));
        new GetFilterName(parameters).execute();

        spinner_type.setAdapter(adapterType);
        spinner_persons.setAdapter(adapterPersons);

        list_report.setAdapter(vendorListAdapter);
        prepareUserType();

        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
                    if (position == 0) {
                        personIDs.clear();
                        personNames.clear();
                        vendorNames.clear();

                        adapterPersons.notifyDataSetChanged();
                        vendorListAdapter.notifyDataSetChanged();

                    } else if (position == 1) {
                      //  new Company_sales_Person3(C.DISTRIBUTOR_ROLE).execute();
                    } else if (position == 2) {
                      //  new Company_sales_Person3(C.DIS_RETAILER_ROLE).execute();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_persons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    vendorNames.clear();
                    vendorListAdapter.notifyDataSetChanged();
                } else {
                    String selectedPersonID = personIDs.get(position);
                    //new app_GetAllhistory(selectedPersonID).execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        img_search_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = txt_search_by_company.getText().toString().trim();

                List<BeanVendorName> retailerPersonListForSearch = new ArrayList<BeanVendorName>();


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                //parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
                parameters.add(new BasicNameValuePair("com_sales_per_id", prefs.getUser_id()));
                Log.e("com_sales_per_id", "" + prefs.getUser_id());

                parameters.add(new BasicNameValuePair("filter_name", name));
                Log.e("filter_name", name);


                if (name.length() < 3) {

                    Toast.makeText(getApplicationContext(), "Please Enter Atlest 3 Keyweord", Toast.LENGTH_SHORT).show();
                } else {

                }


            }
        });

        txt_search_by_company.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String name = txt_search_by_company.getText().toString().trim();

                List<BeanVendorName> retailerPersonListForSearch = new ArrayList<BeanVendorName>();


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                //parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
                parameters.add(new BasicNameValuePair("com_sales_per_id", prefs.getUser_id()));
                Log.e("com_sales_per_id", "" + prefs.getUser_id());

                parameters.add(new BasicNameValuePair("filter_name", name));
                Log.e("filter_name", name);

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (name.length() < 3) {

                        Toast.makeText(getApplicationContext(), "Please Enter Atlest 3 Keyweord", Toast.LENGTH_SHORT).show();
                    } else {
                        new GetFilterName(parameters).execute();
                    }


                    return true;

                }


                return false;
            }
        });

        txt_search_by_company.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String name = txt_search_by_company.getText().toString().trim();

            }


        });


    }

    private void prepareUserType() {
        if (prefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
            userTypes.add("Select Type");
            userTypes.add("Distributor");
            userTypes.add("Retailer");
            adapterType.notifyDataSetChanged();
        } else {
            userTypes.add("Retailer");
            adapterType.notifyDataSetChanged();
            spinner_type.setEnabled(false);

          //  new Company_sales_Person3(C.DIS_RETAILER_ROLE).execute();
        }
    }


    public class Company_sales_Person3 extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String selectedRole = "8";

        public Company_sales_Person3(String selectedRole) {
            this.selectedRole = selectedRole;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(RetailerReport.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {


            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("role_id", selectedRole));
            Log.e("role_id", "" + selectedRole);
            parameters.add(new BasicNameValuePair("com_sales_per_id", prefs.getUser_id()));
            Log.e("com_sales_per_id", "" + prefs.getUser_id());
            String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.USERLISTFORREPORT, ServiceHandler.POST, parameters);
            Log.e("Link", "" + Web.LINK + Web.COMPANY_SALESPERSON);
            return json;


        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            loadingView.dismiss();

            personIDs.clear();
            personNames.clear();
            personIDs.add("0");
            personNames.add("Select Shop");

            if (json == null || json.isEmpty()) {
                Toast.makeText(RetailerReport.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("json", json);

                try {
                    JSONObject object = new JSONObject(json);

                    boolean status = object.getBoolean("status");

                    if (status) {
                        JSONArray dataArray = object.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject main = dataArray.getJSONObject(i);
                            JSONObject User = main.getJSONObject("User");
                            JSONObject Distributor = main.getJSONObject("Distributor");

                            personIDs.add(User.getString("id"));
                            personNames.add(Distributor.getString("firm_shop_name"));

                        }
                    } else {

                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                }

                adapterPersons.notifyDataSetChanged();
                spinner_persons.setSelection(0);

            }
        }


    }

    public class app_GetAllhistory extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String enddate;
        String startdate;
        String selectedUserID = "0";

        String vendorID = "";

        public app_GetAllhistory(String vendorID) {
            this.vendorID = vendorID;
        }


        /*  public app_GetAllhistory(String selectedUserID) {
            this.selectedUserID = selectedUserID;
        }*/


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(RetailerReport.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {
            BeanVendorName beanVendorName = new BeanVendorName();
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            //parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
            parameters.add(new BasicNameValuePair("vendor_id", vendorID));
            Log.e("parameters", vendorID);
            Log.e("user_id", "" + selectedUserID);
            // Log.e("","" + login_arry);
            String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_HISTORY, ServiceHandler.POST, parameters);

            //json = new ServiceHandler().makeServiceCall(Web.GET_HISTORY+"Meetings/App_Get_Meetings",ServiceHandler.POST,parameters);
            //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
            System.out.println("companylist: " + json);
            return json;

        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            loadingView.dismiss();
            vendorNames.clear();
/*

            vendorNames.clear();
            vendorListAdapter.notifyDataSetChanged();
*/

            Log.e("myJSON", "->" + json);

            if (json == null || json.isEmpty()) {
                //new meeting_check().execute();
                Toast.makeText(RetailerReport.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("json", json);

                try {

                    JSONObject object = new JSONObject(json);

                    boolean status = object.getBoolean("status");


                    if (status) {
                        JSONArray dataArray = object.getJSONArray("data");
                        linear_manuallist.setVisibility(View.VISIBLE);


                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject main = dataArray.getJSONObject(i);
                            JSONObject jobject_meeting_history = main.getJSONObject("Meeting");

                            JSONObject User = main.getJSONObject("User");

                            BeanVendorName bean_historymeeting = new BeanVendorName();


                            bean_historymeeting.setId(jobject_meeting_history.getString("id"));
                            bean_historymeeting.setUser_id(jobject_meeting_history.getString("user_id"));
                            bean_historymeeting.setVendor_id(jobject_meeting_history.getString("vendor_id"));
                            bean_historymeeting.setRoute_id(jobject_meeting_history.getString("route_id"));
                            bean_historymeeting.setMeeting_type(jobject_meeting_history.getString("meeting_type"));

                            bean_historymeeting.setStart_time(jobject_meeting_history.getString("start_time"));
                            bean_historymeeting.setEnd_time(jobject_meeting_history.getString("end_time"));
                            bean_historymeeting.setStart_latitude(jobject_meeting_history.getString("start_latitude"));
                            Log.e("Start", "" + jobject_meeting_history.getString("start_latitude"));
                            prefs.setStart_lat(jobject_meeting_history.getString("start_latitude"));
                            bean_historymeeting.setStart_longitude(jobject_meeting_history.getString("start_longitude"));
                            Log.e("Start1", "" + jobject_meeting_history.getString("start_longitude"));
                            prefs.setStart_long(jobject_meeting_history.getString("start_longitude"));

                            bean_historymeeting.setEnd_latitude(jobject_meeting_history.getString("end_latitude"));
                            Log.e("end", "" + jobject_meeting_history.getString("end_latitude"));
                            bean_historymeeting.setEnd_longitude(jobject_meeting_history.getString("end_longitude"));
                            Log.e("end1", "" + jobject_meeting_history.getString("end_longitude"));
                            bean_historymeeting.setComments(jobject_meeting_history.getString("comments"));

                            ArrayList<String> attachments = new ArrayList<>();

                            attachments.add(Web.IMAGELINK2 +jobject_meeting_history.getString("attachments1"));
                            attachments.add(Web.IMAGELINK2 +jobject_meeting_history.getString("attachments2"));
                            attachments.add(Web.IMAGELINK2 +jobject_meeting_history.getString("attachments3"));

                            bean_historymeeting.setAttachmentPaths(attachments);




                            bean_historymeeting.setAttachments1(jobject_meeting_history.getString("attachments1"));
                            bean_historymeeting.setAttachments2(jobject_meeting_history.getString("attachments2"));
                            bean_historymeeting.setAttachments3(jobject_meeting_history.getString("attachments3"));
                            Log.e("Attach1111111111", "" + jobject_meeting_history.getString("attachments1"));
                            bean_historymeeting.setStart_location(jobject_meeting_history.getString("start_location"));
                            bean_historymeeting.setEnd_location(jobject_meeting_history.getString("end_location"));
                            // bean_historymeeting.setStart_battery_status(jobject_meeting_history.getString("start_battery_status"));
                            // bean_historymeeting.setEnd_battery_status(jobject_meeting_history.getString("end_battery_status"));


                            JSONObject jobject_company_profile = main.getJSONObject("CompanyProfile");



                            /*bean_historymeeting.setStr_id(jobject_company_profile.getString("social_login_id"));
                            bean_historymeeting.setStr_responce(jobject_company_profile.getString("social_login_responce"));*/
                            bean_historymeeting.setEmail_id(jobject_company_profile.getString("email_id"));
                            bean_historymeeting.setPhone_no(jobject_company_profile.getString("phone_no"));
                            bean_historymeeting.setFirst_name(User.getString("first_name"));
                            bean_historymeeting.setLast_name(User.getString("last_name"));
                            bean_historymeeting.setMiddle_name(jobject_company_profile.getString("middle_name"));
                            Log.e("first name", "" + jobject_company_profile.getString("first_name"));

                            JSONObject jsonObject_company = jobject_company_profile.getJSONObject("Distributor");
                            JSONArray Order = main.getJSONArray("Order");

                            if (Order.length() > 0) {
                                JSONObject jsonObject = Order.getJSONObject(0);
                                bean_historymeeting.setMeetingID(jsonObject.getString("meeting_id"));
                                bean_historymeeting.setHasOrders(true);
                            } else {
                                bean_historymeeting.setHasOrders(false);
                            }

                            bean_historymeeting.setCompany_name(jsonObject_company.getString("firm_shop_name"));
                            Log.e("firm_shop_name", bean_historymeeting.getCompany_name());

                            String endTime = bean_historymeeting.getEnd_time().toString();
                            String[] endTimeArray = endTime.split(" ");
                            String[] timingArray = endTimeArray[1].split(":");

                            int count = 0;

                            for (int j = 0; j < timingArray.length; j++) {
                                if (Integer.parseInt(timingArray[j]) == 0) {

                                    count++;
                                }

                            }

                            if (count < 3) {

                                vendorNames.add(bean_historymeeting);
                            }
                        }

                        // vendorListAdapter = new VendorListAdapter(getApplicationContext(), v_names, RetailerReport.this);


                    } else {

                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException j) {
                    j.printStackTrace();
                    Log.e("excePtioN", j.getMessage());
                }

            }
            vendorListAdapter.notifyDataSetChanged();

        }
    }

    public class GetFilterName extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String enddate;
        String startdate;
        String selectedUserID = "0";

        List<NameValuePair> parameters = new ArrayList<>();

        public GetFilterName(List<NameValuePair> parameters) {
            //this.selectedUserID = selectedUserID;

            this.parameters = parameters;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(RetailerReport.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {
            Log.e("parameter", parameters.toString());
            if (prefs.getRole_id().equalsIgnoreCase(C.DISTRIBUTOR_ROLE)) {


                parameters.add(new BasicNameValuePair("distributor_id" , prefs.getUser_id()));

                String json = new ServiceHandler().makeServiceCall(Web.LINK + "User/App_GetRetailer", ServiceHandler.POST, parameters);

                Log.e("parameters" ,"--->" +parameters);
                return json;
            } else {

                String json = new ServiceHandler().makeServiceCall(Web.LINK + "User/App_Dist_Retailer_Name_By_Company_Sales_Person", ServiceHandler.POST, parameters);
                return json;

            }





        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            loadingView.dismiss();

            distNames.clear();
            disIDs.clear();

            Log.e("myJSON", "->" + json);

            if (json == null || json.isEmpty()) {
                //new meeting_check().execute();
                Toast.makeText(RetailerReport.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("json", json);

                try {

                    JSONObject object = new JSONObject(json);

                    boolean status = object.getBoolean("status");


                    if (status) {
                        JSONArray dataArray = object.getJSONArray("data");


                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject main = dataArray.getJSONObject(i);
                            JSONObject Distributor = main.getJSONObject("Distributor");

                            disIDs.add(Distributor.getString("user_id"));
                            distNames.add(Distributor.getString("firm_shop_name"));

                        }

                    } else {

                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException j) {
                    j.printStackTrace();
                    Log.e("excePtioN", j.getMessage());
                }

            }

            namesAdapter.notifyDataSetChanged();

        }
    }

    public void onBackPressed() {
        finish();
    }
}
