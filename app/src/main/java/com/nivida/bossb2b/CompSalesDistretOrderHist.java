package com.nivida.bossb2b;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_Filter_Status;
import com.nivida.bossb2b.Bean.Bean_Order_history;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CompSalesDistretOrderHist extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img_drawer, img_logout;

    ListView list_myorders;
    List<Bean_Order_history> historyList = new ArrayList<>();
    ArrayList<Bean_Order_history> array_order = new ArrayList<Bean_Order_history>();
    MyOrderAdapter orderAdapter;
    AppPrefs prefs;
    DatabaseHandler db;
    AppPref appPref;

    int page = 1;
    int totalPageCount = 1;
    boolean hasMoreOrders = true;

    ArrayList<Bean_Filter_Status> array_status = new ArrayList<Bean_Filter_Status>();

    TextView txt_fromDate, txt_toDate;

    String fromDate = "", toDate = "";

    LinearLayout lnr_filter;

    String meetingID = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_sales_distret_order_hist);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        meetingID = intent.getStringExtra("meeting_id");

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


        db = new DatabaseHandler(getApplicationContext());

        db.Delete_Order_histroy();
        prefs = new AppPrefs(getApplicationContext());
        appPref = new AppPref(getApplicationContext());

        fetchIDs();
    }

    public void fetchIDs() {
        list_myorders = (ListView) findViewById(R.id.list_myorders);
        orderAdapter = new MyOrderAdapter(getApplicationContext(), historyList, this, "Comp_SalesDistRetOrderHist");
        list_myorders.setAdapter(orderAdapter);

        /*list_myorders.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int totalListItems = list_myorders.getCount();
                int lastVisiblePosition = list_myorders.getLastVisiblePosition();

                if (lastVisiblePosition == (totalListItems - 1)) {
                    if (page < totalPageCount) {
                        page += 1;
                        new GetOrderHistory(CompSalesDistretOrderHist.this).execute();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
*/

        list_myorders.setOnScrollListener(new EndlessScrollListener());
        lnr_filter = (LinearLayout) findViewById(R.id.lin_filter);

        /*lnr_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Order_History_Filter.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("fromActivity","CompSalesDistRetOrderHist");
                startActivity(intent);
                finish();
            }
        });*/

        txt_fromDate = (TextView) findViewById(R.id.fromDate);
        txt_toDate = (TextView) findViewById(R.id.toDate);
        new GetOrderHistory(this).execute();

        txt_fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.YEAR);
                int month = mcurrentTime.get(Calendar.MONTH);
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(CompSalesDistretOrderHist.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = " From : " + String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                                + "-" + String.valueOf(year);
                        txt_fromDate.setText(date);
                        fromDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Calendar calendar1 = Calendar.getInstance();
                        Calendar calendar2 = Calendar.getInstance();

                        if (toDate.equalsIgnoreCase("")) {
                            new GetOrderHistory(CompSalesDistretOrderHist.this).execute();
                        } else {
                            try {
                                Date fromDate1 = sdf.parse(fromDate);
                                Date toDate1 = sdf.parse(toDate);

                                if (fromDate1.after(toDate1)) {
                                    Toast.makeText(getApplicationContext(), "From Date Must be before To Date", Toast.LENGTH_LONG).show();
                                } else {
                                    new GetOrderHistory(CompSalesDistretOrderHist.this).execute();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, year, month, day);
                datePicker.show();//Yes 24 hour time
                datePicker.setTitle("Select From Date");
                datePicker.show();
            }
        });

        txt_toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.YEAR);
                int month = mcurrentTime.get(Calendar.MONTH);
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(CompSalesDistretOrderHist.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = " To : " + String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                                + "-" + String.valueOf(year);
                        txt_toDate.setText(date);
                        toDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Calendar calendar1 = Calendar.getInstance();
                        Calendar calendar2 = Calendar.getInstance();

                        if (fromDate.equalsIgnoreCase("")) {
                            new GetOrderHistory(CompSalesDistretOrderHist.this).execute();
                        } else {
                            try {
                                Date fromDate1 = sdf.parse(fromDate);
                                Date toDate1 = sdf.parse(toDate);

                                if (toDate1.before(fromDate1)) {
                                    Toast.makeText(getApplicationContext(), "To Date Must be after From Date", Toast.LENGTH_SHORT).show();
                                } else {
                                    new GetOrderHistory(CompSalesDistretOrderHist.this).execute();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, year, month, day);
                datePicker.show();//Yes 24 hour time
                datePicker.setTitle("Select To Date");
                datePicker.show();
            }
        });
    }

    public class GetOrderHistory extends AsyncTask<Void, Void, String> {

        ProgressActivity dialog;

        public GetOrderHistory(Activity activity) {
            dialog = new ProgressActivity(activity, "");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> parameter = new ArrayList<>();
            //   parameter.add(new BasicNameValuePair("owner_id", appPref.getUser_id()));
            if (appPref.getRole_id().equalsIgnoreCase(C.DISTRIBUTOR_ROLE)) {


                parameter.add(new BasicNameValuePair("user_id", appPref.getUser_id()));
                //parameter.add(new BasicNameValuePair("meeting_id", meetingID));

                parameter.add(new BasicNameValuePair("page", String.valueOf(page)));
                Log.e("user_id", "" + appPref.getUser_id());

                Log.e("parameters", parameter.toString());


            } else {

                parameter.add(new BasicNameValuePair("page", String.valueOf(page)));
                parameter.add(new BasicNameValuePair("meeting_id", meetingID));
                Log.e("user_id", "" + appPref.getUser_id());

                Log.e("parameters", parameter.toString());
            }



            /*parameter.add(new BasicNameValuePair("role_id", prefs.getSelectedUserRole()));
            parameter.add(new BasicNameValuePair("owner_id", prefs.getLogin_userid()));

            if(!prefs.getOrder_history_filter_order_status().equalsIgnoreCase(""))
                parameter.add(new BasicNameValuePair("order_status", prefs.getOrder_history_filter_order_status()));

            if(!prefs.getOrder_history_filter_from_date().equalsIgnoreCase(""))
                parameter.add(new BasicNameValuePair("from_date", prefs.getOrder_history_filter_from_date()));

            if(!prefs.getOrder_history_filter_to_date().equalsIgnoreCase(""))
                parameter.add(new BasicNameValuePair("to_date", prefs.getOrder_history_filter_to_date()));

            if(!prefs.getOrder_history_filter_predefine().equalsIgnoreCase(""))
                parameter.add(new BasicNameValuePair("predefine", prefs.getOrder_history_filter_predefine()));

            Log.e("parameter",parameter.toString());
*/
            String json = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_GET_B2B_ORDER, ServiceHandler.POST, parameter);
            System.out.println("companylist: " + json);
            return json;
        }

        @Override
        protected void onPostExecute(String json) {

            Log.e("json", json);

            try {
                JSONObject object = new JSONObject(json);
                //historyList.clear();
                array_order.clear();

                if (object.getBoolean("status")) {
                    JSONArray data = object.getJSONArray("data");
                    totalPageCount = object.getInt("total_page_count");
                    JSONArray jsonobjcet_order_status = object.getJSONArray("order_status");

                    array_status.clear();

                    for (int j = 0; j < jsonobjcet_order_status.length(); j++) {
                        JSONObject jobject_main = jsonobjcet_order_status.getJSONObject(j);
                        JSONObject jobject_status = jobject_main.getJSONObject("OrderStatus");
                        Bean_Filter_Status bean = new Bean_Filter_Status();
                        bean.setId(jobject_status.getString("id"));
                        bean.setFilter_name(jobject_status.getString("order_status_name"));
                        array_status.add(bean);

                    }
                    db.Add_Filter_Status(array_status);

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jobject_main = data.getJSONObject(i);

                        JSONObject jobject_order = jobject_main.getJSONObject("Order");

                        String order_id = jobject_order.getString("id");
                        String invoice_no = jobject_order.getString("invoice_no");
                        String invoice_prefix = jobject_order.getString("invoice_prefix");
                        String user_id = jobject_order.getString("user_id");
                        String role_id = jobject_order.getString("role_id");
                        String billing_address_id = jobject_order.getString("billing_address_id");
                        String shipping_address_id = jobject_order.getString("shipping_address_id");
                        String first_name = jobject_order.getString("firstname");
                        String last_name = jobject_order.getString("lastname");
                        String email = jobject_order.getString("email");
                        String mobile = jobject_order.getString("mobile");
                        String payment_name = jobject_order.getString("payment_firstname") + " " + jobject_order.getString("payment_lastname");

                        String final_payment_address = "";
                        if (jobject_order.getString("payment_address_1").equalsIgnoreCase("") || jobject_order.getString("payment_address_1").equalsIgnoreCase("null")) {

                        } else {
                            final_payment_address = jobject_order.getString("payment_address_1");
                        }


                        if (jobject_order.getString("payment_address_2").equalsIgnoreCase("") || jobject_order.getString("payment_address_2").equalsIgnoreCase("null")) {

                        } else {
                            final_payment_address = final_payment_address + ", " + jobject_order.getString("payment_address_2");
                        }

                        if (jobject_order.getString("payment_address_3").equalsIgnoreCase("") || jobject_order.getString("payment_address_3").equalsIgnoreCase("null")) {

                        } else {
                            final_payment_address = final_payment_address + ",\n" + jobject_order.getString("payment_address_3");
                        }

                        if (jobject_order.getString("payment_city").equalsIgnoreCase("") || jobject_order.getString("payment_city").equalsIgnoreCase("null")) {

                        } else {
                            final_payment_address = final_payment_address + ", " + jobject_order.getString("payment_city");
                        }

                        if (jobject_order.getString("payment_postcode").equalsIgnoreCase("") || jobject_order.getString("payment_postcode").equalsIgnoreCase("null")) {

                        } else {
                            final_payment_address = final_payment_address + ", " + jobject_order.getString("payment_postcode");
                        }

                        if (jobject_order.getString("payment_state").equalsIgnoreCase("") || jobject_order.getString("payment_state").equalsIgnoreCase("null")) {

                        } else {
                            final_payment_address = final_payment_address + ", " + jobject_order.getString("payment_state");
                        }

                        if (jobject_order.getString("payment_country").equalsIgnoreCase("") || jobject_order.getString("payment_country").equalsIgnoreCase("null")) {

                        } else {
                            final_payment_address = final_payment_address + ", " + jobject_order.getString("payment_country");
                        }


                        String payment_address = final_payment_address;
                        String payment_method = jobject_order.getString("payment_method");
                        String payment_code = jobject_order.getString("payment_code");
                        String shipping_name = jobject_order.getString("shipping_firstname") + " " + jobject_order.getString("shipping_lastname");

                        String final_shipping_address = "";
                        if (jobject_order.getString("shipping_address_1").equalsIgnoreCase("") || jobject_order.getString("shipping_address_1").equalsIgnoreCase("null")) {

                        } else {
                            final_shipping_address = jobject_order.getString("shipping_address_1");
                        }

                        if (jobject_order.getString("shipping_address_2").equalsIgnoreCase("") || jobject_order.getString("shipping_address_2").equalsIgnoreCase("null")) {

                        } else {
                            final_shipping_address = final_shipping_address + ", " + jobject_order.getString("shipping_address_2");
                        }

                        if (jobject_order.getString("shipping_address_3").equalsIgnoreCase("") || jobject_order.getString("shipping_address_3").equalsIgnoreCase("null")) {

                        } else {
                            final_shipping_address = final_shipping_address + ", " + jobject_order.getString("shipping_address_3");
                        }

                        if (jobject_order.getString("shipping_city").equalsIgnoreCase("") || jobject_order.getString("shipping_city").equalsIgnoreCase("null")) {

                        } else {
                            final_shipping_address = final_shipping_address + ",\n" + jobject_order.getString("shipping_city");
                        }

                        if (jobject_order.getString("shipping_postcode").equalsIgnoreCase("") || jobject_order.getString("shipping_postcode").equalsIgnoreCase("null")) {

                        } else {
                            final_shipping_address = final_shipping_address + ", " + jobject_order.getString("shipping_postcode");
                        }

                        if (jobject_order.getString("shipping_state").equalsIgnoreCase("") || jobject_order.getString("shipping_state").equalsIgnoreCase("null")) {

                        } else {
                            final_shipping_address = final_shipping_address + ", " + jobject_order.getString("shipping_state");
                        }

                        if (jobject_order.getString("shipping_country").equalsIgnoreCase("") || jobject_order.getString("shipping_country").equalsIgnoreCase("null")) {

                        } else {
                            final_shipping_address = final_shipping_address + ", " + jobject_order.getString("shipping_country");
                        }


                        String shipping_address = final_shipping_address;
                        String shipping_cost = jobject_order.getString("shipping_cost");
                        String comment = jobject_order.getString("comment");
                        String total = jobject_order.getString("total");
                        String order_status_is = jobject_order.getString("order_status_id");
                        String attachment = jobject_order.getString("attachment");
                        String scheme_info = jobject_order.getString("scheme_info");


                        Log.e("order_status_is", "" + order_status_is);
                        String order_pdf = jobject_order.getString("order_pdf");
                        String order_date = jobject_order.getString("created");

                        JSONObject user = jobject_main.getJSONObject("User");
                        JSONObject Distributor = user.getJSONObject("Distributor");

                        String firmShopname = Distributor.getString("firm_shop_name");


                        JSONObject ownerInfo = jobject_main.getJSONObject("Owner");
                        Bean_Order_history bean_order_history = new Bean_Order_history();

                        String OwnerfirstName = ownerInfo.getString("first_name");
                        String OwnerlastName = ownerInfo.getString("last_name");


                        JSONArray jarray_OrderProduct = jobject_main.getJSONArray("OrderProduct");

                        for (int j = 0; j < jarray_OrderProduct.length(); j++) {
                            JSONObject jobject_OrderProduct = jarray_OrderProduct.getJSONObject(j);
                            Bean_Order_history bean = new Bean_Order_history();
                            bean.setId(jobject_OrderProduct.getString("id"));
                            bean.setProduct_id(jobject_OrderProduct.getString("product_id"));
                            bean.setProduct_name(jobject_OrderProduct.getString("name"));
                            String product_code = jobject_OrderProduct.getString("pro_code").replace("(", "");
                            product_code = product_code.replace(")", "");
                            bean.setProduct_code(product_code);
                            bean.setProduct_qty(jobject_OrderProduct.getString("quantity"));


                            bean.setProduct_selling_price(jobject_OrderProduct.getString("selling_price"));
                            bean.setProduct_total(jobject_OrderProduct.getString("item_total"));
                            bean.setProduct_option_name(jobject_OrderProduct.getString("option_name"));
                            bean.setProduct_value_name(jobject_OrderProduct.getString("option_value_name"));

                            bean.setOrder_id(order_id);
                            bean.setInvoice_no(invoice_no);
                            bean.setInvoice_prefix(invoice_prefix);
                            bean.setUser_id(user_id);
                            bean.setRole_id(role_id);
                            bean.setBilling_address_id(billing_address_id);
                            bean.setShipping_address_id(shipping_address_id);
                            bean.setFirst_name(first_name);
                            bean.setLast_name(last_name);
                            bean.setOwnerFirstName(OwnerfirstName);
                            bean.setOwnerLastName(OwnerlastName);
                            bean.setEmail(email);
                            bean.setMobile(mobile);
                            bean.setPayment_name(payment_name);
                            bean.setPayment_address(payment_address);
                            bean.setPayment_method(payment_method);
                            bean.setPayment_code(payment_code);
                            bean.setShipping_name(shipping_name);
                            bean.setShipping_address(shipping_address);
                            bean.setShipping_cost(shipping_cost);
                            bean.setComment(comment);
                            bean.setTotal(total);
                            bean.setOrder_status_is(order_status_is);
                            bean.setOrder_pdf(order_pdf);
                            bean.setOrder_date(order_date);
                            bean.setScheme_info(scheme_info);
                            bean.setAttachment(attachment);
                            bean.setFirm_shop_name(firmShopname);

                            array_order.add(bean);
                            db.Add_ORDER_HISTORY(bean);
                        }
                        //array_remove_duplicate = array_order;

                        for (Bean_Order_history event : array_order) {
                            boolean isFound = false;
                            // check if the event name exists in noRepeat
                            for (Bean_Order_history e : historyList) {
                                if (e.getOrder_id().equals(event.getOrder_id()))
                                    isFound = true;
                            }

                            if (!isFound) historyList.add(event);
                        }


                        //Toast.makeText(Order_history.this, ""+array_remove_duplicate.size(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                   // Toast.makeText(CompSalesDistretOrderHist.this, object.getString("message"), Toast.LENGTH_LONG).show();
                }

                int firstVisiblePosition = list_myorders.getFirstVisiblePosition();
                orderAdapter.notifyDataSetChanged();
                list_myorders.setSelection(firstVisiblePosition);

            } catch (JSONException e) {
                Log.e("json exception", e.getMessage());
            } finally {
                dialog.dismiss();
            }
        }
    }

    @Override
    public void onBackPressed() {

        prefs.setOrder_history_filter_order_status("");
        prefs.setOrder_history_filter_from_date("");
        prefs.setOrder_history_filter_to_date("");
        prefs.setOrder_history_filter_predefine("");


        finish();

        /*if(meetingID.equalsIgnoreCase("0")) {
            super.onBackPressed();
        }
        else {
           Intent intent=new Intent(getApplicationContext(),All_History.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }*/


    }


    public class EndlessScrollListener implements AbsListView.OnScrollListener {

        private int visibleThreshold = 5;
        private int currentPage = 0;
        private int previousTotal = 0;
        private boolean loading = true;

        public EndlessScrollListener() {
        }

        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            int totalListItems = list_myorders.getCount();
            int lastVisiblePosition = list_myorders.getLastVisiblePosition();

            if (lastVisiblePosition == (totalListItems - 1)) {
                if (page < totalPageCount) {
                    page += 1;
                    new GetOrderHistory(CompSalesDistretOrderHist.this).execute();
                }
            }


        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    }
}
