package com.nivida.bossb2b;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class All_History extends AppCompatActivity {

    EditText ed_fromdate, ed_todate;
    ListView list_all_history;
    Button bt_history, btn_ready;

    TextView cart_count;
    Database db;

    ImageView cart, notify, notification;

    ProgressDialog loadingView;

    AppPref prefs;

    String json = "";

    VendorAllHistoryListAdapter vendorListAdapter;


    DatePickerDialog datePickerDialog;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    String topDate;

    String fromDate = "", toDate = "";


    EditText txt_search_by_company;

    int mYear, mMonth, mDay;
    List<BeanHistoryVendorName> v_names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__history);

        prefs = new AppPref(getApplicationContext());
        final Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());


        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        topDate = df.format(c.getTime());


        db = new Database(getApplicationContext());
        btn_ready = (Button) findViewById(R.id.btn_ready);
        txt_search_by_company = (EditText) findViewById(R.id.txt_searchby_company);
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
        txt_search_by_company.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
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


                List<BeanHistoryVendorName> retailerPersonListForSearch = new ArrayList<BeanHistoryVendorName>();

                for (int i = 0; i < v_names.size(); i++) {
                    if (v_names.get(i).getCompany_name().toLowerCase().startsWith(name.toLowerCase()) || v_names.get(i).getFirst_name().toLowerCase().startsWith(name.toLowerCase())) {
                        retailerPersonListForSearch.add(v_names.get(i));
                    }
                }

                vendorListAdapter.updateData(retailerPersonListForSearch);
            }


        });


        fetchid();

    }

    private void fetchid() {


        ed_fromdate = (EditText) findViewById(R.id.ed_fromdate);
        ed_todate = (EditText) findViewById(R.id.ed_todate);
        final Calendar c = Calendar.getInstance();


        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
        c.add(Calendar.DATE, -1);
        String from = simpleDateFormat1.format(c.getTime());

        ed_fromdate.setText(from);

        fromDate = from;


        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        calendar.add(Calendar.DATE, -1);
        String todate = simpleDateFormat.format(calendar.getTime());

        ed_todate.setText(todate);

        toDate = todate;


        new app_GetAllhistory(fromDate, toDate).execute();


        bt_history = (Button) findViewById(R.id.bt_history);

        list_all_history = (ListView) findViewById(R.id.list_all_history);


        ed_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromdate();
            }
        });
        ed_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todate();

            }
        });

        bt_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_fromdate.getText().toString().equals("")) {
                    Toast.makeText(All_History.this, "Please Select From Date", Toast.LENGTH_SHORT).show();

                } else if (ed_todate.getText().toString().equals("")) {
                    Toast.makeText(All_History.this, "Please Select To Date", Toast.LENGTH_SHORT).show();

                } else {

                    showdilog();
                }

            }
        });


    }

    private void showdilog() {
        final Dialog dialog = new Dialog(All_History.this);
        LayoutInflater inflater = All_History.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.email_text_dialog, null);

        dialog.setCancelable(false);
        //setting custom layout to dialog
        dialog.setContentView(dialogView);
        dialog.setTitle("Send Email");

        final EditText textedit1 = (EditText) dialogView.findViewById(R.id.textedit1);
        textedit1.setSelection(textedit1.getText().length());
       /* final EditText textedit2 = (EditText) dialogView.findViewById(R.id.textedit2);
        textedit2.setSelection(textedit2.getText().length());*/


        final Button resetButton = (Button) dialogView.findViewById(R.id.btn_textedit1_reset);
        final Button cancelbutton = (Button) dialogView.findViewById(R.id.btn_textedit1_cancel);
        final Button donebutton = (Button) dialogView.findViewById(R.id.btn_textedit1_done);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textedit1.setText("");

               /* textedit2.setText("");*/

            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String ccEmail = textedit1.getText().toString();
               /* String bccEmail = textedit2.getText().toString();*/

                if (!ccEmail.isEmpty() && !validateEmail1(textedit1.getText().toString())) {
                    Toast.makeText(All_History.this, "Please enter valid CC email address", Toast.LENGTH_LONG).show();
                    // Globals.CustomToast(Business_Registration.this, "Please enter a valid email address", getLayoutInflater());
                    textedit1.setFocusable(true);
                } /*else if (!bccEmail.isEmpty() && !validateEmail1(textedit2.getText().toString())) {
                    Toast.makeText(All_History.this, "Please enter valid Bcc email address", Toast.LENGTH_LONG).show();
                } */ else {
                    String emailtext = textedit1.getText().toString().trim();
                   /* String ccemail = textedit2.getText().toString().trim();*/
                    String to_mail_text = "noreply@bossindia.com";

                    Log.e("emails", emailtext + "<->" /*+ ccemail +*/ + "<->" + to_mail_text);

                    if (Internet.isConnectingToInternet(getApplicationContext())) {
                        new send_mail(emailtext/*, ccemail*/, to_mail_text).execute();
                    } else {
                        Toast.makeText(All_History.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private boolean validateEmail1(String s) {
        // TODO Auto-generated method stub

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(s);
        return matcher.matches();
    }

    private void fromdate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        final String mYear2 = String.valueOf(mYear);
        final String mMonth2 = String.valueOf(mMonth);
        final String mDay2 = String.valueOf(mDay);

        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        fromDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;


                        ed_fromdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        new app_GetAllhistory(fromDate, toDate).execute();
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


                    }
                    //
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void todate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        final String mYear2 = String.valueOf(mYear);
        final String mMonth2 = String.valueOf(mMonth);
        final String mDay2 = String.valueOf(mDay);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        toDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar1 = Calendar.getInstance();
                        Calendar calendar2 = Calendar.getInstance();

                        Log.e("todate", "" + toDate);
                        Log.e("fromdate", "" + fromDate);


                        if (!fromDate.equalsIgnoreCase("")) {
                            // new GetOrderHistory(DistSalesRetailerOrderHistActivity.this).execute();
                            try {


                                Date fromDate1 = sdf.parse(fromDate);
                                Date toDate1 = sdf.parse(toDate);
                                Log.e("todate", "" + fromDate1);
                                Log.e("todate", "" + toDate1);

                                Log.e("todate", "" + toDate);
                                Log.e("todate", "" + fromDate);


                                if (toDate1.before(fromDate1)) {
                                    //  C.Toast(getApplicationContext(),"From Date Must be before To Date",getLayoutInflater());
                                    Toast.makeText(All_History.this, "Fromdate is Greater than Todate", Toast.LENGTH_LONG).show();
                                    v_names.clear();
                                    bt_history.setVisibility(View.GONE);

                                    Log.e("todate11111", "22222");
                                } else {
                                    ed_todate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    new app_GetAllhistory(fromDate, toDate).execute();
                                    Log.e("todate11111", "33333");
                                    // new GetOrderHistory(DistSalesRetailerOrderHistActivity.this).execute();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("todate11111", "4444");
                            ed_todate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            new app_GetAllhistory(fromDate, toDate).execute();
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    public class app_GetAllhistory extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String enddate;
        String startdate;

        public app_GetAllhistory(String fromDate, String toDate) {
            enddate = toDate;
            startdate = fromDate;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(All_History.this, "");

                loadingView.setCancelable(false);
                loadingView.show();
                enddate = ed_todate.getText().toString().trim();
                startdate = ed_fromdate.getText().toString().trim();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                String date;
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
                date = df.format(c.getTime());

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
                Log.e("user_id", "" + prefs.getUser_id());
                parameters.add(new BasicNameValuePair("from_date", startdate));
                Log.e("allhistory", "" + fromDate);
                parameters.add(new BasicNameValuePair("to_date", enddate));
                Log.e("allhistory", "" + toDate);
                // Log.e("","" + login_arry);
                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_HISTORY, ServiceHandler.POST, parameters);

                //json = new ServiceHandler().makeServiceCall(Web.GET_HISTORY+"Meetings/App_Get_Meetings",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("companylist: " + json);
                return json;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: " + e.toString());

            }

            return json;

        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            loadingView.dismiss();
            v_names.clear();


            Log.e("myJSON", "->" + json);

            if (json == null || json.isEmpty()) {
                //new meeting_check().execute();
                Toast.makeText(All_History.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("json", json);

                try {

                    JSONObject object = new JSONObject(json);

                    boolean status = object.getBoolean("status");


                    if (status) {
                        JSONArray dataArray = object.getJSONArray("data");


                        for (int i = 0; i < dataArray.length(); i++) {
                            bt_history.setVisibility(View.VISIBLE);

                            JSONObject main = dataArray.getJSONObject(i);
                            JSONObject jobject_meeting_history = main.getJSONObject("Meeting");


                            BeanHistoryVendorName bean_historymeeting = new BeanHistoryVendorName();


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

                            bean_historymeeting.setStart_location(jobject_meeting_history.getString("start_location"));
                            bean_historymeeting.setEnd_location(jobject_meeting_history.getString("end_location"));


                            JSONObject jobject_company_profile = main.getJSONObject("CompanyProfile");
                            JSONObject jobject_User = main.getJSONObject("User");


                            bean_historymeeting.setEmail_id(jobject_company_profile.getString("email_id"));
                            bean_historymeeting.setPhone_no(jobject_company_profile.getString("phone_no"));
                            bean_historymeeting.setFirst_name(jobject_company_profile.getString("first_name"));
                            bean_historymeeting.setLast_name(jobject_company_profile.getString("last_name"));
                            bean_historymeeting.setMiddle_name(jobject_company_profile.getString("middle_name"));
                            Log.e("first name", "" + jobject_company_profile.getString("first_name"));

                            bean_historymeeting.setCompanySalesPersonName(jobject_User.getString("first_name") + " " + jobject_User.getString("last_name"));

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
                                v_names.add(bean_historymeeting);
                            }
                        }

                        Log.e("array_companylist", "" + v_names.size());
                        loadingView.dismiss();
                        //.notifyDataSetChanged();
                        for (int i = 0; i < v_names.size(); i++) {
                            Log.e("ABCABA", "" + v_names.get(i).getFirst_name().toString());
                        }

                        //vendorListAdapter = new VendorListAdapter(getApplicationContext(), v_names, HomeActivity.this);
                        vendorListAdapter = new VendorAllHistoryListAdapter(getApplicationContext(), v_names, All_History.this);
                        vendorListAdapter.notifyDataSetChanged();
                        list_all_history.setAdapter(vendorListAdapter);


                    } else {
                        v_names.clear();
                        list_all_history.setAdapter(null);
                        //vendorListAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.e("No Data Found", "" + object.getString("message"));
                    }

                } catch (JSONException j) {
                    j.printStackTrace();
                    Log.e("excePtioN", j.getMessage());
                }

            }

        }
    }

    public void onBackPressed() {
        finish();
    }


    public class send_mail extends AsyncTask<String, Void, String> {

        boolean status;

        String ccemail;
        String startdate;
        String enddate;
        String emailtext;

        String ccmail;
        String to_mail_text;

        public send_mail(String to_mail_text, String emailtext/*, String ccemail*/) {

           /* this.ccemail = ccemail;*/
            this.to_mail_text = to_mail_text;
            this.emailtext = emailtext;

            Log.e("email", this.emailtext + "->" + this.ccmail + "->" + this.to_mail_text);
        }


       /* public send_mail(String root_date, String batteryLevel, String finialAddress) {
            this.root_date=root_date;
            this.batteryLevel= batteryLevel;
            this.finialAddress=finialAddress;
        }*/


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(All_History.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

                enddate = ed_todate.getText().toString().trim();
                startdate = ed_fromdate.getText().toString().trim();
                //email=textedit1.getText().toString().trim();
                //ccemail="app4@nividaweb.com";

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("from_date", fromDate));
                Log.e("from_date", "" + startdate);
                parameters.add(new BasicNameValuePair("user_id", prefs.getUser_id()));
                Log.e("user_id", "" + prefs.getUser_id());

                parameters.add(new BasicNameValuePair("to_date", toDate));
                Log.e("to_date", "" + enddate);


                parameters.add(new BasicNameValuePair("toemail", emailtext));
                Log.e("toemail", "->" + this.emailtext);

                parameters.add(new BasicNameValuePair("ccemail", to_mail_text));
                Log.e("ccemail", "->" + this.to_mail_text);

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_EMAIL, ServiceHandler.POST, parameters);

                Log.e("Parameters", "" + parameters);

                // json = new ServiceHandler().makeServiceCall(Global.server_link+"Meetings/App_Email_Meetings",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("email: " + json);
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

            try {
                //db = new DatabaseHandler(());
                System.out.println(result_1);
                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(All_History.this, "SERVER ERROR", Toast.LENGTH_LONG).show();
                    //Global.CustomToast(Activity_Login.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    Log.e("status", "" + date);
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(All_History.this, "" + Message, Toast.LENGTH_LONG).show();
                        // Global.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        Log.e("status", "" + date);
                        String Message = jObj.getString("message");
                        Toast.makeText(All_History.this, "" + Message, Toast.LENGTH_LONG).show();
                        //JSONObject jarray_data = jObj.getJSONObject("data");
                        //JSONObject jUser=jData.getJSONObject("User");
                        //String route_id = jObj.getString("route_id");
                        // prefs.setRoute_id(jObj.getString("route_id"));

                        //Log.e("route_id",""+ route_id);

                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("excePtioN", j.getMessage());
            }
        }
    }


    @Override
    public void onResume() {

        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");

        super.onResume();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

}





