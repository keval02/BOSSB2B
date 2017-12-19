package com.nivida.bossb2b;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DistributorsRetailerHistory extends AppCompatActivity {

    AutoCompleteTextView autoTextView;

    ArrayAdapter<String> namesAdapter;
    ArrayList<String> distNames = new ArrayList<>();
    ArrayList<String> disIDs = new ArrayList<>();

    ProgressActivity loadingView;

    AppPref appPref;

    AppPrefs prefs;


    ListView list_myorders;
    List<Bean_Order_history> historyList = new ArrayList<>();
    ArrayList<Bean_Order_history> array_order = new ArrayList<Bean_Order_history>();
    MyOrderAdapter orderAdapter;

    int page = 1;
    int totalPageCount = 1;
    boolean hasMoreOrders = true;

    ArrayList<Bean_Filter_Status> array_status = new ArrayList<Bean_Filter_Status>();

    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributors_retailer_history);

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


        fetchIds();
    }

    private void fetchIds() {

        appPref  = new AppPref(getApplicationContext());
        prefs = new AppPrefs(getApplicationContext());
        db = new DatabaseHandler(getApplicationContext());

        namesAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.auto_textview, distNames);

        autoTextView = (AutoCompleteTextView) findViewById(R.id.autoTextView);
        autoTextView.setAdapter(namesAdapter);

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

               new GetOrderHistory(disIDs.get(mPosition) , DistributorsRetailerHistory.this).execute();
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


        list_myorders = (ListView) findViewById(R.id.list_myorders);
        orderAdapter = new MyOrderAdapter(getApplicationContext(), historyList, this, "Comp_SalesDistRetOrderHist");
        list_myorders.setAdapter(orderAdapter);

        list_myorders.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int totalListItems = list_myorders.getCount();
                int lastVisiblePosition = list_myorders.getLastVisiblePosition();

                if (lastVisiblePosition == (totalListItems - 1)) {
                    if (page < totalPageCount) {
                        page += 1;
                      //  new GetOrderHistory(DistributorsRetailerHistory.this).execute();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });








        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("com_sales_per_id", appPref.getUser_id()));
        parameters.add(new BasicNameValuePair("filter_name", ""));
        new GetFilterName(parameters).execute();



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
                loadingView = new ProgressActivity(DistributorsRetailerHistory.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {
            Log.e("parameter", parameters.toString());
            if (appPref.getRole_id().equalsIgnoreCase(C.DISTRIBUTOR_ROLE)) {


                parameters.add(new BasicNameValuePair("distributor_id" , appPref.getUser_id()));

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
                Toast.makeText(DistributorsRetailerHistory.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
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

    public class GetOrderHistory extends AsyncTask<Void, Void, String> {

        ProgressActivity dialog;

        String userId = "";

        public GetOrderHistory(Activity activity) {
            dialog = new ProgressActivity(activity, "");
            dialog.setCancelable(false);
            dialog.show();
        }

        public GetOrderHistory(String userId , Activity activity) {
            this.userId = userId;
            dialog = new ProgressActivity(activity, "");
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> parameter = new ArrayList<>();




                parameter.add(new BasicNameValuePair("user_id", userId));
                //parameter.add(new BasicNameValuePair("meeting_id", meetingID));

                parameter.add(new BasicNameValuePair("page", String.valueOf(page)));
                Log.e("user_id", "" + appPref.getUser_id());

                Log.e("parameters", parameter.toString());


            String json = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_GET_B2B_ORDER, ServiceHandler.POST, parameter);
            System.out.println("companylist: " + json);
            return json;
        }

        @Override
        protected void onPostExecute(String json) {

            Log.e("json", json);
            array_order.clear();
            array_status.clear();
            historyList.clear();


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
                    //Toast.makeText(DistributorsRetailerHistory.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                    array_order.clear();
                    list_myorders.setAdapter(null);
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


}
