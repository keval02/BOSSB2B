package com.nivida.bossb2b;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.nivida.bossb2b.Bean.BeanVendorName;
import com.nivida.bossb2b.Bean.Bean_Address;
import com.nivida.bossb2b.Bean.Bean_Set_Product_Categeory;
import com.nivida.bossb2b.Bean.Bean_User_data;
import com.nivida.bossb2b.adapter.SelectAddressAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;


public class CheckoutPage extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double currentLatitude;
    private double currentLongitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    Button btn_pay1, btn_promocode;
    TextView tv_total_product, tv_order_total, tv_subtotal, txt_total_quantity;
    ListView lv_checkout_product_list;
    List<Bean_Set_Product_Categeory> bean_cart = new ArrayList<Bean_Set_Product_Categeory>();
    List<BeanVendorName> addresses = new ArrayList<>();

    List<Bean_Address> addressList = new ArrayList<>();

    String firstName = "";
    String lastName = "";
    String emailID = "";
    String mobileNum = "";
    String Companyname = "";
    String firmName = "";

    ImageView im_billing, im_delivery;
    Database db;
    CustomResultAdapter adapter;
    ProgressActivity loadingView;
    JSONArray jarray_OrderProductData = new JSONArray();
    JSONArray jarray_OrderUserData = new JSONArray();
    JSONArray jarray_OrderTotalData = new JSONArray();
    String json;
    AppPref appPrefs;
    TextView categeory;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    TextView tv_user_name_billing, tv_number_billing, tv_address_billing, tv_user_name_delivery, tv_number_delivery, tv_address_delivery;

    int totalQuantity = 0;

    String add1, add2, add3, pcode, city, state;

    ArrayList<Bean_Address> array_address = new ArrayList<Bean_Address>();
    ArrayList<String> array_final_address = new ArrayList<String>();

    String selected_billing_address = "";
    String selected_shipping_address = "";

    String selected_billing_address_id = "";
    String selected_shipping_address_id = "";

    String user_id = "";

    TextView tv_coupondiscount;
    LinearLayout lnr_amount_detail, lnr_subtotal, lnr_total, lnr_coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


        db = new Database(CheckoutPage.this);
        appPrefs = new AppPref(CheckoutPage.this);
        bean_cart = db.getCartData();
        im_delivery = (ImageView) this.findViewById(R.id.im_delivery);

        Intent intent = getIntent();

        add1 = intent.getStringExtra("address_1");
        add2 = intent.getStringExtra("address_2");
        add3 = intent.getStringExtra("address_3");
        pcode = intent.getStringExtra("pincode");
        city = intent.getStringExtra("city");
        state = intent.getStringExtra("state");
        selected_shipping_address_id = intent.getStringExtra("address_id");
        Log.e("afterAddnewAddress", "-->" + selected_shipping_address_id);


        BeanVendorName beanAddress = new BeanVendorName();


        new Checkout_Address("0").execute();

        lnr_amount_detail = (LinearLayout) findViewById(R.id.lnr_amount_detail);
        lnr_subtotal = (LinearLayout) findViewById(R.id.lnr_subtotal);
        lnr_total = (LinearLayout) findViewById(R.id.lnr_total);


        tv_total_product = (TextView) this.findViewById(R.id.tv_total_product);
        txt_total_quantity = (TextView) this.findViewById(R.id.total_quantity);
        tv_subtotal = (TextView) this.findViewById(R.id.tv_subtotal);
        tv_order_total = (TextView) this.findViewById(R.id.tv_order_total);
        lv_checkout_product_list = (ListView) this.findViewById(R.id.lv_checkout_product_list);

        tv_user_name_billing = (TextView) this.findViewById(R.id.tv_user_name_billing);
        tv_number_billing = (TextView) this.findViewById(R.id.tv_number_billing);
        tv_address_billing = (TextView) this.findViewById(R.id.tv_address_billing);
        tv_user_name_delivery = (TextView) this.findViewById(R.id.tv_user_name_delivery);
        tv_number_delivery = (TextView) this.findViewById(R.id.tv_number_delivery);
        tv_address_delivery = (TextView) this.findViewById(R.id.tv_address_delivery);

        tv_coupondiscount = (TextView) this.findViewById(R.id.tv_coupondiscount);
        lnr_coupon = (LinearLayout) this.findViewById(R.id.lnr_coupon);
        categeory = (TextView) findViewById(R.id.txt_categeory_name);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //notify = (ImageView) findViewById(R.id.notify);
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


        im_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(CheckoutPage.this);
                dialog.setContentView(R.layout.popup_dialog_address);

                ListView order_addresslist = (ListView) dialog.findViewById(R.id.order_addresslist);
                final SelectAddressAdapter addressAdapter = new SelectAddressAdapter(addressList, getApplicationContext());

                order_addresslist.setAdapter(addressAdapter);

                Button btn_add_new_address = (Button) dialog.findViewById(R.id.btn_add_new_address);
                Button btn_select = (Button) dialog.findViewById(R.id.btn_select);

                btn_add_new_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(CheckoutPage.this, Cart_AddNewAddress.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("firstName", firstName);
                        i.putExtra("lastName", lastName);
                        i.putExtra("email", emailID);
                        i.putExtra("Mobileno", mobileNum);
                        i.putExtra("CompanyName", Companyname);
                        i.putExtra("firmName", firmName);
                        startActivity(i);


                    }
                });

                btn_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bean_Address selectedAddress = addressAdapter.getSelectedAddress();

                        selected_shipping_address_id = selectedAddress.getSelectedAddressid();
                        tv_user_name_delivery.setText(appPrefs.getOnSelectedFirmName());
                        Log.e("OnItemSelectedFirmShop", appPrefs.getOnSelectedFirmName());
                        tv_number_delivery.setText(selectedAddress.getMobile_no());
                        tv_address_delivery.setText(selectedAddress.getAddress1() + ", " + selectedAddress.getAddress2() + ",\n" +
                                selectedAddress.getCity() + ", " + selectedAddress.getState() + " - " + selectedAddress.getPincode());


                        dialog.dismiss();
                    }
                });
                LinearLayout mLinearLayout = (LinearLayout) dialog.findViewById(R.id.layout_address_dialog);


                // create radio button
                //final RadioButton[] rb = new RadioButton[array_final_address.size()];
                RadioGroup rg = new RadioGroup(CheckoutPage.this);
                rg.setOrientation(RadioGroup.VERTICAL);
                for (int i = 0; i < array_final_address.size(); i++) {
                    final RadioButton rb = new RadioButton(CheckoutPage.this);
                    rg.addView(rb);

                    rb.setText(array_final_address.get(i));
                    rb.setTag(array_address.get(i).getSelectedAddressid());

                    Log.e("Select Address ID", "" + array_address.get(i).getAddress_id());
                    rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                selected_shipping_address = rb.getText().toString();
                                selected_shipping_address_id = rb.getText().toString();
                            }
                        }
                    });

                    Log.e("Select Address ID", array_address.get(i).getAddress_id());
                    View view = new View(CheckoutPage.this);
                    LinearLayout.LayoutParams view_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    view_param.setMargins(0, 5, 0, 5);
                    view.setLayoutParams(view_param);
                    view.setBackgroundColor(Color.parseColor("#000000"));
                    mLinearLayout.addView(view);
                }

                mLinearLayout.addView(rg);


                dialog.show();
            }
        });


        tv_total_product.setText("" + bean_cart.size());
        btn_pay1 = (Button) findViewById(R.id.btn_pay1);
        btn_promocode = (Button) findViewById(R.id.btn_promocode);


        btn_pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CheckoutPage.this);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.custom_take_order, null);
                dialogBuilder.setView(dialogView);

                final EditText edt_comment = (EditText) dialogView.findViewById(R.id.edt_comment);

                Button btn_go = (Button) dialogView.findViewById(R.id.btn_send);
                Button btn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);

                final AlertDialog b = dialogBuilder.create();
                b.show();

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.dismiss();

                    }
                });


                btn_go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e("currentLatitude", "" + currentLatitude);
                        Log.e("currentLongitude", "" + currentLongitude);


                        Log.e("111111111", "111111111111");
                        Log.e("address_id", "" + selected_billing_address_id);
                        Log.e("shipping_id", "" + selected_shipping_address_id);
                        try {
                            JSONObject jobject_OrderUserData = new JSONObject();
                            //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                            jobject_OrderUserData.put("user_id", appPrefs.getVendor_id());
                            jobject_OrderUserData.put("billing_address_id", selected_billing_address_id);
                            jobject_OrderUserData.put("shipping_address_id", selected_shipping_address_id);
                            jobject_OrderUserData.put("latitude", appPrefs.getRoute_startLat());
                            jobject_OrderUserData.put("longitude", appPrefs.getRoute_startLong());
                            jobject_OrderUserData.put("owner_id", appPrefs.getUser_id());
                            jarray_OrderUserData.put(jobject_OrderUserData);
                            appPrefs.setPlaceorderclicked(true);
                            appPrefs.setAddednewAddress(false);
                        } catch (JSONException e) {

                        }

                        int grandTotal = 0;

                        for (int i = 0; i < bean_cart.size(); i++) {
                            try {
                                JSONObject jobject = new JSONObject();

                                int total = Integer.parseInt(bean_cart.get(i).getProduct_selling_price()) * bean_cart.get(i).getQuantity();

                                grandTotal += total;
                                jobject.put("pro_id", bean_cart.get(i).getId());
                                jobject.put("pro_cat_id", bean_cart.get(i).getCategeory_id());
                                //Log.e("pro_cat_id", "" + bean_cart.get(i).getPro_cat_id());
                                jobject.put("pro_code", bean_cart.get(i).getCode());
                                jobject.put("pro_name", bean_cart.get(i).getName());
                                jobject.put("pro_qty", String.valueOf(bean_cart.get(i).getQuantity()));
                                jobject.put("pro_mrp", bean_cart.get(i).getProduct_mrp());
                                jobject.put("pro_sellingprice", bean_cart.get(i).getProduct_selling_price());
                                jobject.put("pro_Option_id", "0");
                                jobject.put("pro_Option_name", "color");
                                jobject.put("pro_Option_value_id", "1");
                                jobject.put("pro_Option_value_name", "blue");
                                jobject.put("pro_total", String.valueOf(total));
                                jarray_OrderProductData.put(jobject);
                            } catch (JSONException e) {

                            }
                            //   dialog.dismiss();

                        }

                        try {
                            JSONObject jobject_OrderTotalData = new JSONObject();
                            //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                            String comment = edt_comment.getText().toString().trim();

                            jobject_OrderTotalData.put("grandtotal", String.valueOf(grandTotal));
                            jobject_OrderTotalData.put("comment", comment);
                            jobject_OrderTotalData.put("scheme_info", "");
                            jarray_OrderTotalData.put(jobject_OrderTotalData);
                        } catch (JSONException e) {
                            Log.e("Exception", e.getMessage());
                        }

                        new send_order_details().execute();

                    }
                });


            }
        });


        adapter = new CustomResultAdapter();
        lv_checkout_product_list.setAdapter(adapter);

        txt_total_quantity.setText("Total Qty : " + adapter.getTotalQuantity());

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();


        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
         /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }


    }

    @Override
    public void onLocationChanged(Location location) {

        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

    }


    public class send_order_details extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        private String docPath = "";

        public send_order_details() {

        }

        public send_order_details(String docPath) {
            this.docPath = docPath;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        CheckoutPage.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("OrderUserData", "" + jarray_OrderUserData));
                Log.e("OrderUserData", "" + jarray_OrderUserData);
                parameters.add(new BasicNameValuePair("OrderTotalData", "" + jarray_OrderTotalData));
                Log.e("OrderTotalData", "" + jarray_OrderTotalData);
                parameters.add(new BasicNameValuePair("OrderProductData", "" + jarray_OrderProductData));
                Log.e("OrderProductData", "" + jarray_OrderProductData);
                parameters.add(new BasicNameValuePair("meeting_id", appPrefs.getMeetings_id()));
                Log.e("", appPrefs.getMeetings_id());

                if (!docPath.equalsIgnoreCase("")) {
                    parameters.add(new BasicNameValuePair("attachment", docPath));
                }




                //json = new ServiceHandler().makeServiceCall(GlobalVariable.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                //  json = new ServiceHandler().makeServiceCall(Web.LINK + Web.API_ADD_B2B_ORDER_SECOND, ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                if (appPrefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE) && appPrefs.getSelectedUserRole().equalsIgnoreCase(C.DISTRIBUTOR_ROLE)) {
                    json = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_ADD_B2B_ORDER_THIRD, ServiceHandler.POST, parameters, 1);
                    Log.e("Link", "" + GlobalVariable.link + GlobalVariable.API_ADD_B2B_ORDER_THIRD);

                } else if (appPrefs.getRole_id().equalsIgnoreCase(C.COMP_SALES_ROLE) && appPrefs.getSelectedUserRole().equalsIgnoreCase(C.DIS_RETAILER_ROLE)){
                    json = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_ADD_B2B_ORDER_FOURTH, ServiceHandler.POST, parameters, 1);
                    Log.e("Link", "" + GlobalVariable.link + GlobalVariable.API_ADD_B2B_ORDER_FOURTH);
                }
                else if (appPrefs.getRole_id().equalsIgnoreCase(C.DIS_SALES_ROLE) && appPrefs.getSelectedUserRole().equalsIgnoreCase(C.DIS_RETAILER_ROLE)){
                    json = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_ADD_B2B_ORDER_FIFTH, ServiceHandler.POST, parameters);
                    Log.e("Link", "" + GlobalVariable.link + GlobalVariable.API_ADD_B2B_ORDER_FIFTH);
                }


                Log.e("parameters", "-->" + parameters);

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
            loadingView.dismiss();

            //  Log.e("result", result_1);

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.isEmpty()) {
                    Toast.makeText(CheckoutPage.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();


                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Toast.makeText(CheckoutPage.this, "" + Message, Toast.LENGTH_SHORT).show();

                    } else {

                        String Message = jObj.getString("message");
                        db = new Database(CheckoutPage.this);
                        db.removeFromCart(null);
                        db.close();
                        loadingView.dismiss();
                        Intent i = new Intent(CheckoutPage.this, Thank.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                        finish();
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("error", String.valueOf(Html.fromHtml("<p style=\"color:#00F;\">" + j.getMessage() + "</p>")));
            }

        }
    }

    public class CustomResultAdapter extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        public CustomResultAdapter() {
            totalQuantity = 0;
        }

        @Override
        public int getCount() {
            return bean_cart.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // result_holder = new ResultHolder();

            if (convertView == null) {

                convertView = vi.inflate(R.layout.checkout_product_list, null);

            }

            final LinearLayout lnr_qty_amount = (LinearLayout) convertView.findViewById(R.id.lnr_qty_amount);
            //LinearLayout lnr_packof = (LinearLayout) convertView.findViewById(R.id.lnr_packof);
            //LinearLayout lnr_quantity = (LinearLayout) convertView.findViewById(R.id.lnr_quantity);

            final TextView tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
            final TextView tv_product_attribute = (TextView) convertView.findViewById(R.id.tv_product_attribute);
            //final TextView tv_pack_of = (TextView) convertView.findViewById(R.id.tv_pack_of);
            final TextView tv_product_selling_price = (TextView) convertView.findViewById(R.id.tv_product_selling_price);
            final TextView tv_product_qty = (TextView) convertView.findViewById(R.id.tv_product_qty);
            final TextView tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
            TextView qty = (TextView) convertView.findViewById(R.id.txt_qty);
            TextView tv_total_quantity = (TextView) convertView.findViewById(R.id.tv_total_quantity);
            final TextView tv_product_total_cost = (TextView) convertView.findViewById(R.id.tv_product_total_cost);


            tv_product_name.setText(bean_cart.get(position).getCode() + " - " + bean_cart.get(position).getName());

            Log.e("Total Qty", bean_cart.get(position).getQuantity() + " - " + totalQuantity);
            qty.setText(String.valueOf(bean_cart.get(position).getQuantity()));


            // tv_total_quantity.setText("Total Quantity = "+(bean_cart.get(position).getQuantity()+bean_cart.get(position).getQuantity()+bean_cart.get(position).getQuantity()+bean_cart.get(position).getQuantity()));

            //tv_pack_of.setText(bean_cart.get(position).getPro_shortdesc());

            //tv_product_selling_price.setText(bean_cart.get(position).getPro_sellingprice());
            //tv_product_qty.setText(bean_cart.get(position).getPro_qty());
            //  tv_quantity.setText(bean_cart.get(position).getQuantity());

            //tv_product_total_cost.setText(getResources().getString(R.string.Rs) + "" + bean_cart.get(position).getPro_total());


            //String[] option = bean_cart.get(position).getPro_Option_name().toString().split(", ");
            //String[] value = bean_cart.get(position).getPro_Option_value_name().split(", ");

            /*if (bean_cart.get(position).getPro_Option_name().equalsIgnoreCase("")) {
                tv_product_attribute.setVisibility(View.GONE);
            } else {
                tv_product_attribute.setVisibility(View.GONE);
            }*/

            //String option_value = "";


//            List myList = new ArrayList();
//
//            Collections.addAll(myList, option);
//
//            //  List<String> al = new ArrayList<>();
//// add elements to al, including duplicates
//            Set<String> hs = new HashSet<>();
//            hs.addAll(myList);
//            myList.clear();
//            myList.addAll(hs);
//
//            List myList1 = new ArrayList();
//
//            Collections.addAll(myList1, value);
//
//            //  List<String> al = new ArrayList<>();
//// add elements to al, including duplicates
//            Set<String> hs1 = new HashSet<>();
//            hs1.addAll(myList1);
//            myList1.clear();
//            myList1.addAll(hs1);
//
//            for(int i = 0 ; i < option.length ; i++)
//            {
//                if(i==0) {
//                    option_value = myList.get(i).toString() + ": " + myList1.get(i).toString();
//                }
//                else
//                {
//                    option_value = option_value +"  , " + myList.get(i).toString() + ": " + myList1.get(i).toString();
//                }
//            }


            /*List myList = new ArrayList();

            Collections.addAll(myList, option);

            //  List<String> al = new ArrayList<>();
// add elements to al, including duplicates
            Set<String> hs = new HashSet<>();
            hs.addAll(myList);
            myList.clear();
            myList.addAll(hs);

            List myList1 = new ArrayList();

            Collections.addAll(myList1, value);*/

            //  List<String> al = new ArrayList<>();
// add elements to al, including duplicates
            /*Set<String> hs1 = new HashSet<>();
            hs1.addAll(myList1);
            myList1.clear();
            myList1.addAll(hs1);

            tv_product_attribute.setVisibility(View.VISIBLE);
            Log.e("Option Length", "" + option.length);
            for (int i = 0; i < myList.size(); i++) {
                if (i == 0) {


                    if (myList1.get(i).toString() == "" || myList1.get(i).toString() == null) {
                        option_value = "";
                        tv_product_attribute.setVisibility(View.GONE);
                    } else {
                        option_value = myList.get(i).toString() + ": " + myList1.get(i).toString();

                        Log.e("OptionValue", "" + option_value);
                        Log.e("", "" + myList1.get(i).toString());
                    }
                } else {
                    option_value = option_value + ", " + myList.get(i).toString() + ": " + myList1.get(i).toString();

                    Log.e("OptionValue", "" + myList.toString());
                    Log.e("length", "" + option.length);
                }
            }
            Log.e("option_value", "" + option_value);
            tv_product_attribute.setText(option_value);*/


            // tv_product_attribute.setText(option_value);


            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            totalQuantity = 0;
            super.notifyDataSetChanged();
        }

        public int getTotalQuantity() {
            int totalQty = 0;

            for (int i = 0; i < bean_cart.size(); i++) {
                totalQty += bean_cart.get(i).getQuantity();
            }

            return totalQty;
        }
    }

    class Checkout_Address extends AsyncTask<Void, Void, String> {

        String user_id = "";

        public Checkout_Address(String user_id) {
            this.user_id = user_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(CheckoutPage.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> pairs = new ArrayList<>();

            pairs.add(new BasicNameValuePair("user_id", appPrefs.getVendor_id()));
            Log.e("vendor_id", "-> " + appPrefs.getVendor_id());

            String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_ADDRESS, ServiceHandler.POST, pairs);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            addressList.clear();
            loadingView.dismiss();
            Log.e("json", "-->" + json);
            try {

                JSONObject object = new JSONObject(json);
                boolean status = object.getBoolean("status");

                if (status) {

                    JSONArray dataArray = object.getJSONArray("data");


                    for (int i = 0; i < dataArray.length(); i++) {


                        JSONObject main = dataArray.getJSONObject(i);

                        JSONObject address = main.getJSONObject("Address");
                        JSONObject Country = main.getJSONObject("Country");
                        JSONObject State = main.getJSONObject("State");
                        JSONObject City = main.getJSONObject("City");

                        String defaultAddr = address.getString("default_address");

                        Bean_Address beanAddress = new Bean_Address();

                        beanAddress.setSelectedAddressid(address.getString("id"));
                        beanAddress.setFirst_name(address.getString("first_name"));
                        beanAddress.setLast_name(address.getString("last_name"));
                        beanAddress.setEmail(address.getString("email"));
                        beanAddress.setAddress1(address.getString("address_1"));
                        beanAddress.setPincode(address.getString("pincode"));
                        beanAddress.setMobile_no(address.getString("mobile"));
                        beanAddress.setFirm_name(address.getString("firm_name"));
                        beanAddress.setCity(City.getString("name"));
                        beanAddress.setState(State.getString("name"));
                        beanAddress.setCountry(Country.getString("name"));

                        if (appPrefs.isAddednewAddress == true) {


                            firstName = beanAddress.getFirst_name();
                            lastName = beanAddress.getLast_name();
                            emailID = beanAddress.getEmail();
                            mobileNum = beanAddress.getMobile_no();
                            firmName = beanAddress.getFirm_name();

                            selected_billing_address_id = address.getString("id");
                            // selected_shipping_address_id = address.getString("id");
                            Log.e("selected billing adress id", "" + selected_billing_address_id);
                            Log.e("selected Shipping adress id", "" + selected_shipping_address_id);


                            //tv_user_name_billing.setText(beanAddress.getFirst_name() + " " + beanAddress.getLast_name());

                            tv_user_name_billing.setText(appPrefs.getOnSelectedFirmName());
                            tv_number_billing.setText(beanAddress.getMobile_no());
                            tv_address_billing.setText(beanAddress.getAddress1() + ", " + beanAddress.getAddress2() + ",\n" +
                                    beanAddress.getCity() + ", " + beanAddress.getState() + " - " + beanAddress.getPincode());

                            //tv_user_name_delivery.setText(beanAddress.getFirst_name() + " " + beanAddress.getLast_name());

                            tv_user_name_delivery.setText(appPrefs.getOnSelectedFirmName());
                            tv_number_delivery.setText(beanAddress.getMobile_no());
                            tv_address_delivery.setText(add1 + " " + add2 + " " + add3 + "- " + pcode);


                        }


                        if (appPrefs.isAddednewAddress == false) {

                            if (defaultAddr.equalsIgnoreCase("1")) {

                                beanAddress.setChecked(true);

                                firstName = beanAddress.getFirst_name();
                                lastName = beanAddress.getLast_name();
                                emailID = beanAddress.getEmail();
                                mobileNum = beanAddress.getMobile_no();
                                firmName = beanAddress.getFirm_name();

                                selected_billing_address_id = address.getString("id");


                                selected_shipping_address_id = address.getString("id");
                                Log.e("selected billing adress id", "" + selected_billing_address_id);
                                Log.e("selected Shipping adress id", "" + selected_shipping_address_id);


                                //  tv_user_name_billing.setText(beanAddress.getFirst_name() + " " + beanAddress.getLast_name());
                                tv_user_name_billing.setText(appPrefs.getOnSelectedFirmName());
                                tv_number_billing.setText(beanAddress.getMobile_no());
                                tv_address_billing.setText(beanAddress.getAddress1() + ", " + beanAddress.getAddress2() + ",\n" +
                                        beanAddress.getCity() + ", " + beanAddress.getState() + " - " + beanAddress.getPincode());

                                //tv_user_name_delivery.setText(beanAddress.getFirst_name() + " " + beanAddress.getLast_name());

                                tv_user_name_delivery.setText(appPrefs.getOnSelectedFirmName());
                                tv_number_delivery.setText(beanAddress.getMobile_no());
                                tv_address_delivery.setText(beanAddress.getAddress1() + ", " + beanAddress.getAddress2() + ",\n" +
                                        beanAddress.getCity() + ", " + beanAddress.getState() + " - " + beanAddress.getPincode());
                            }
                        }

                        beanAddress.setAddress2(address.getString("address_2"));
                        beanAddress.setAddress3(address.getString("address_3"));

                        addressList.add(beanAddress);
                    }
                } else {


                    String Message = object.getString("message");
                    Toast.makeText(CheckoutPage.this, "" + Message, Toast.LENGTH_LONG).show();
                    loadingView.dismiss();

                }


            } catch (JSONException e) {

                Log.e("Exception", e.getMessage());


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

    public void onBackPressed() {

        appPrefs.setAddednewAddress(false);
        super.onBackPressed();
    }


}
