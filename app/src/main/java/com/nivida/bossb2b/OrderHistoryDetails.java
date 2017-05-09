package com.nivida.bossb2b;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nivida.bossb2b.Bean.Bean_Address;
import com.nivida.bossb2b.Bean.Bean_Order_history;
import com.nivida.bossb2b.Bean.Bean_ProductCart;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderHistoryDetails extends AppCompatActivity {
    Button btn_pay1;
    TextView tv_total_product, tv_order_total, tv_subtotal;
    ListView lv_checkout_product_list;
    ArrayList<Bean_Order_history> bean_cart = new ArrayList<Bean_Order_history>();

    Bean_Order_history bean_CartIntent;
    int fromOther = 0;

    String firm_shop_name = "";

    TextView txt;

    CustomResultAdapter adapter;
    ProgressActivity loadingView;
    ArrayList<Bean_ProductCart> bean_cart1 = new ArrayList<Bean_ProductCart>();
    JSONArray jarray_OrderProductData = new JSONArray();
    JSONArray jarray_OrderUserData = new JSONArray();
    JSONArray jarray_OrderTotalData = new JSONArray();
    String json;
    AppPrefs appPrefs;
    // ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    TextView tv_user_name_billing, tv_number_billing, tv_address_billing, tv_user_name_delivery, tv_number_delivery, tv_address_delivery;

    OrderTrackAdapter orderTrackAdapter;
    List<Bean_OrderTrack> orderTrackList = new ArrayList<>();


    ImageView im_billing, im_delivery;

    ArrayList<Bean_Address> array_address = new ArrayList<Bean_Address>();
    ArrayList<String> array_final_address = new ArrayList<String>();

    String selected_billing_address = "";
    String selected_shipping_address = "";

    String selected_billing_address_id = "";
    String selected_shipping_address_id = "";
    String comment = "";

    String user_id = "";
    DatabaseHandler db;
    Button btn_moreinfo;

    TextView txt_comment , total_quantity;

    Button reorder_all;

    Bean_Order_history history;

    LinearLayout lnr_amountdetails, lnr_moreinfo, lnr_coupon;

    TextView txt_couponname, txt_couponamount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);


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




        Intent intent = getIntent();
        fromOther = intent.getIntExtra("fromOther", 0);
        firm_shop_name = intent.getStringExtra("firm_shop_name");

        Log.e("firm_shop_name"  ,firm_shop_name);

        history = (Bean_Order_history) intent.getSerializableExtra("order");
        appPrefs = new AppPrefs(OrderHistoryDetails.this);

        lnr_coupon = (LinearLayout) findViewById(R.id.lnr_coupon);
        txt_couponname = (TextView) findViewById(R.id.txt_coupon_name);
        txt_couponamount = (TextView) findViewById(R.id.tv_coupondiscount);

        txt_comment = (TextView) findViewById(R.id.txt_commentdisplay);
        total_quantity = (TextView) findViewById(R.id.total_quantity);




        /*txt_comment.setText(history.getComment());*/


        db = new DatabaseHandler(OrderHistoryDetails.this);
        bean_cart = db.get_order_history(appPrefs.getOrder_history_id());

        if (history != null) {
            if (history.isHasCouponApplied()) {
                lnr_coupon.setVisibility(View.VISIBLE);
            }
        }


        im_billing = (ImageView) this.findViewById(R.id.im_billing);
        im_delivery = (ImageView) this.findViewById(R.id.im_delivery);


        tv_total_product = (TextView) this.findViewById(R.id.tv_total_product);
        tv_subtotal = (TextView) this.findViewById(R.id.tv_subtotal);
        tv_order_total = (TextView) this.findViewById(R.id.tv_order_total);
        lv_checkout_product_list = (ListView) this.findViewById(R.id.lv_checkout_product_list);

        tv_user_name_billing = (TextView) this.findViewById(R.id.tv_user_name_billing);
        tv_number_billing = (TextView) this.findViewById(R.id.tv_number_billing);
        tv_address_billing = (TextView) this.findViewById(R.id.tv_address_billing);
        tv_user_name_delivery = (TextView) this.findViewById(R.id.tv_user_name_delivery);
        tv_number_delivery = (TextView) this.findViewById(R.id.tv_number_delivery);
        tv_address_delivery = (TextView) this.findViewById(R.id.tv_address_delivery);

        tv_user_name_billing.setText(firm_shop_name);
        tv_user_name_delivery.setText(firm_shop_name);
        tv_number_billing.setText(bean_cart.get(0).getPayment_address());
        tv_number_delivery.setText(bean_cart.get(0).getShipping_address());

        lnr_amountdetails = (LinearLayout) findViewById(R.id.lnr_amountdetails);
        lnr_moreinfo = (LinearLayout) findViewById(R.id.lnr_moreinfo);
        btn_moreinfo = (Button) findViewById(R.id.btn_moreinfo);

        if (!appPrefs.getUserRoleId().equalsIgnoreCase(C.CUSTOMER_ROLE) && !appPrefs.getUserRoleId().equalsIgnoreCase("")) {
            lnr_amountdetails.setVisibility(View.GONE);

            if (appPrefs.getUserRoleId().equalsIgnoreCase(C.COMP_SALES_ROLE) ||
                    appPrefs.getUserRoleId().equalsIgnoreCase(C.DIS_SALES_ROLE))
                lnr_moreinfo.setVisibility(View.VISIBLE);
        }

            btn_moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OrderHistoryDetails.this);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialog = inflater.inflate(R.layout.b2b_moreinfo_order, null);
                dialogBuilder.setView(dialog);



                final AlertDialog b = dialogBuilder.create();
                b.show();

                TextView txt_comment;

                Button btn_close;
                orderTrackAdapter = new OrderTrackAdapter(getApplicationContext(), orderTrackList);

                btn_close = (Button) dialog.findViewById(R.id.btn_close);

                                txt_comment = (TextView) dialog.findViewById(R.id.txt_comment);

                new GetOrderTrackHistory(OrderHistoryDetails.this).execute();

                if (!appPrefs.getComment().equalsIgnoreCase("") && !appPrefs.getComment().equalsIgnoreCase("null")) {
                    txt_comment.setText(appPrefs.getComment());
                }

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.dismiss();
                    }
                });

                b.show();
            }
        });

       /* String final_full_address = "";

        if(!user_data.get(0).getAdd1().equalsIgnoreCase("")||!user_data.get(0).getAdd1().equalsIgnoreCase("null"))
        {
            final_full_address = user_data.get(0).getAdd1();
        }

        if(!user_data.get(0).getAdd2().equalsIgnoreCase("")||!user_data.get(0).getAdd2().equalsIgnoreCase("null"))
        {
            final_full_address = final_full_address +", "+ user_data.get(0).getAdd2();
        }

        if(!user_data.get(0).getAdd3().equalsIgnoreCase("")||!user_data.get(0).getAdd3().equalsIgnoreCase("null"))
        {
            final_full_address = final_full_address +", "+ user_data.get(0).getAdd3();
        }

        if(!user_data.get(0).getCity_name().equalsIgnoreCase("")||!user_data.get(0).getCity_name().equalsIgnoreCase("null"))
        {
            final_full_address = final_full_address +",\n"+ user_data.get(0).getCity_name();
        }
        if(!user_data.get(0).getPincode().equalsIgnoreCase("")||!user_data.get(0).getPincode().equalsIgnoreCase("null"))
        {
            final_full_address = final_full_address +", "+ user_data.get(0).getPincode();
        }
        if(!user_data.get(0).getState_name().equalsIgnoreCase("")||!user_data.get(0).getState_name().equalsIgnoreCase("null"))
        {
            final_full_address = final_full_address +", "+ user_data.get(0).getState_name();
            final_full_address = final_full_address + ", India";
        }
        tv_address_delivery.setText(final_full_address);

        tv_address_billing.setText(final_full_address);*/

        im_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        im_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        tv_total_product.setText("" + bean_cart.size());
        if (history != null) {
            if (history.isHasCouponApplied()) {

                int total = C.getInt(bean_cart.get(0).getTotal());
                int subTotal = C.getInt(bean_cart.get(0).getTotal()) + C.getInt(history.getDiscount_amount());
                txt_couponname.setText("Coupon (" + history.getCoupon_name() + ") Discount :");
                txt_couponamount.setText(history.getDiscount_amount());

                tv_subtotal.setText(C.getStr(subTotal));
                tv_order_total.setText(C.getStr(total));
            } else {
                tv_order_total.setText("" + bean_cart.get(0).getTotal());
                tv_subtotal.setText("" + bean_cart.get(0).getTotal());
            }
        } else {
            tv_order_total.setText("" + bean_cart.get(0).getTotal());
            tv_subtotal.setText("" + bean_cart.get(0).getTotal());
        }


        btn_pay1 = (Button) findViewById(R.id.btn_pay1);
        btn_pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent i = new Intent(CheckoutPage.this, Btl_Payment.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/

                final Dialog dialog = new Dialog(OrderHistoryDetails.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.checkout_comment_layout);


                // set the custom dialog components - text, image and button
                final EditText ed_comment = (EditText) dialog.findViewById(R.id.ed_comment);
                Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
                Button btn_skip = (Button) dialog.findViewById(R.id.btn_skip);


                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

                btn_skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


                dialog.show();


                // new send_order_details().execute();
            }
        });


        adapter = new CustomResultAdapter();
        adapter.notifyDataSetChanged();
        lv_checkout_product_list.setAdapter(adapter);

        total_quantity.setText("Total Qty : " + adapter.getTotalQuantity());



     /*   tv_subtotal.setText(appPrefs.getCart_Grand_total());
        tv_order_total.setText(appPrefs.getCart_Grand_total());*/


    }


    public class GetOrderTrackHistory extends AsyncTask<Void, Void, String> {

        ProgressActivity dialog;

        public GetOrderTrackHistory(Activity activity) {
            dialog = new ProgressActivity(activity , "");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> parameter = new ArrayList<>();
            parameter.add(new BasicNameValuePair("order_id", appPrefs.getOrder_history_id()));

            Log.e("parameter", parameter.toString());

            String json = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_GET_TRACK_ORDER, ServiceHandler.POST, parameter);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {

            Log.e("json", json);

            orderTrackList.clear();



            try {
                JSONObject object = new JSONObject(json);

                if (object.getBoolean("status")) {
                    JSONArray data = object.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject object1 = data.getJSONObject(i);

                        JSONObject oh = object1.getJSONObject("OrderHistory");
                        JSONObject os = object1.getJSONObject("OrderStatus");

                        Bean_OrderTrack orderTrack1 = new Bean_OrderTrack();
                        orderTrack1.setDate(oh.getString("created"));
                        orderTrack1.setComment(oh.getString("comment"));

                        orderTrack1.setStatus(os.getString("order_status_name"));
                        orderTrackList.add(orderTrack1);
                    }
                } else {
                    C.Toast(OrderHistoryDetails.this, object.getString("message"), getLayoutInflater());
                }
                orderTrackAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                Log.e("json exception", e.getMessage());
            } finally {
                dialog.dismiss();
            }
        }
    }
    public class CustomResultAdapter extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

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

                convertView = vi.inflate(R.layout.checkout_product_listt, null);

            }

            LinearLayout lnr_qty_amount = (LinearLayout) convertView.findViewById(R.id.lnr_qty_amount);
            LinearLayout lnr_packof = (LinearLayout) convertView.findViewById(R.id.lnr_packof);
            LinearLayout lnr_quantity = (LinearLayout) convertView.findViewById(R.id.lnr_quantity);
            final TextView tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
            final TextView tv_product_attribute = (TextView) convertView.findViewById(R.id.tv_product_attribute);
            final TextView tv_pack_of = (TextView) convertView.findViewById(R.id.tv_pack_of);
            final TextView tv_product_selling_price = (TextView) convertView.findViewById(R.id.tv_product_selling_price);
            final TextView tv_product_qty = (TextView) convertView.findViewById(R.id.tv_product_qty);
            final TextView tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
            final ImageView reorder = (ImageView) convertView.findViewById(R.id.reorder);

            reorder.setVisibility(View.GONE);

            if((appPrefs.getUserRoleId().equalsIgnoreCase(C.COMP_SALES_ROLE) || appPrefs.getUserRoleId().equalsIgnoreCase(C.DIS_SALES_ROLE)) && (!appPrefs.isSales_has_user_selected())){
                reorder.setVisibility(View.GONE);
            }

            final TextView tv_product_total_cost = (TextView) convertView.findViewById(R.id.tv_product_total_cost);

            if (!appPrefs.getUserRoleId().equalsIgnoreCase(C.CUSTOMER_ROLE) && !appPrefs.getUserRoleId().equalsIgnoreCase("")) {
                lnr_qty_amount.setVisibility(View.GONE);
                lnr_packof.setVisibility(View.GONE);
                lnr_quantity.setVisibility(View.VISIBLE);
            }


            tv_product_name.setText(bean_cart.get(position).getProduct_code()+" - "+bean_cart.get(position).getProduct_name());


            //tv_pack_of.setVisibility(View.GONE);

            tv_product_selling_price.setText(bean_cart.get(position).getProduct_selling_price());
            tv_product_qty.setText(bean_cart.get(position).getProduct_qty());
            tv_quantity.setText(bean_cart.get(position).getProduct_qty());

            tv_product_total_cost.setText(getResources().getString(R.string.Rs) + "" + bean_cart.get(position).getProduct_total());


            String[] option = bean_cart.get(position).getProduct_option_name().toString().split(", ");
            String[] value = bean_cart.get(position).getProduct_value_name().split(", ");
            String option_value = "";


            List myList = new ArrayList();

            Collections.addAll(myList, option);

            //  List<String> al = new ArrayList<>();
// add elements to al, including duplicates
            Set<String> hs = new HashSet<>();
            hs.addAll(myList);
            myList.clear();
            myList.addAll(hs);

            List myList1 = new ArrayList();

            Collections.addAll(myList1, value);

            //  List<String> al = new ArrayList<>();
// add elements to al, including duplicates
            Set<String> hs1 = new HashSet<>();
            hs1.addAll(myList1);
            myList1.clear();
            myList1.addAll(hs1);


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

            tv_product_attribute.setText(option_value);

            reorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bean_Order_history orderHistory = bean_cart.get(position);

                    ArrayList<Bean_ProductCart> array_product_cart = new ArrayList<Bean_ProductCart>();

                    db = new DatabaseHandler(OrderHistoryDetails.this);
                    array_product_cart = db.is_product_in_cart(orderHistory.getProduct_code());
                    Log.e("product code",orderHistory.getProduct_code());
                    Log.e("New array product size", "" + array_product_cart.size());

                    if (array_product_cart.size() > 0)
                        Log.e("PRO QTY", array_product_cart.get(0).getPro_qty());

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("product_id", orderHistory.getProduct_id()));

                    Log.e("params", params.toString());

                    ProgressActivity progressDialog = new ProgressActivity(OrderHistoryDetails.this, "");
                    progressDialog.setIndeterminate(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    String json = WebServiceCaller.GetProductDetailByCode(params);
                    progressDialog.dismiss();

                    if (json != null && !json.equalsIgnoreCase("")) {

                        try {

                            JSONObject mainOnj = new JSONObject(json);

                            if (mainOnj.getBoolean("status")) {
                                JSONObject data = mainOnj.getJSONObject("data");
                                JSONObject product = data.getJSONObject("Product");
                                JSONObject label = data.getJSONObject("Label");


                                Bean_ProductCart bean = new Bean_ProductCart();
                                String value_id = "";
                                String value_name = "";
                                value_name = orderHistory.getProduct_value_name();
                                value_id = orderHistory.getProduct_value_id();
                                bean.setPro_id("("+orderHistory.getProduct_id()+")");
                                bean.setPro_cat_id(product.getString("category_id"));
                                Log.e("Cat_ID....", "" + product.getString("category_id"));
                                bean.setPro_Images(product.getString("image"));
                                bean.setPro_code("("+product.getString("product_code")+")");
                                Log.e("-- ", "pro_code " + product.getString("product_code"));
                                bean.setPro_name(product.getString("product_name"));

                                String quantity = "0";

                                if (array_product_cart.size() > 0) {
                                    quantity = array_product_cart.get(0).getPro_qty();
                                }

                                int qty = C.getInt(quantity) + C.getInt(orderHistory.getProduct_qty());

                                Log.e("Qty", "" + qty);

                                bean.setPro_qty(C.getStr(qty));
                                bean.setPro_mrp(product.getString("mrp"));
                                bean.setPro_sellingprice(product.getString("selling_price"));
                                bean.setPro_shortdesc("1 " + label.getString("name"));
                                bean.setPro_Option_id(orderHistory.getProduct_option_id());
                                Log.e("--", "Option Id " + orderHistory.getProduct_option_id());

                                bean.setPro_Option_name(orderHistory.getProduct_option_name());
                                Log.e("-- ", "Option Name " + orderHistory.getProduct_option_name());

                                bean.setPro_Option_value_id(value_id);

                                Log.e("", "Value Name " + value_id);
                                bean.setPro_Option_value_name(value_name);
                                Log.e("", "Value Name " + value_name);

                                int total = C.getInt(product.getString("selling_price")) * qty;

                                bean.setPro_total(C.getStr(total));

                                db.Add_Product_cart(bean,orderHistory.getProduct_option_name(),orderHistory.getProduct_value_name());
                                C.Toast(getApplicationContext(), "Product Inserted into Cart", getLayoutInflater());
                            } else {
                                C.Toast(getApplicationContext(), mainOnj.getString("message"), getLayoutInflater());
                            }

                        } catch (JSONException j) {
                            Log.e("JSON Exception", j.getMessage());
                        }
                    } else {
                        C.Toast(getApplicationContext(), "SERVER ERROR", getLayoutInflater());
                    }


                }
            });
            return convertView;
        }

        public int getTotalQuantity(){
            int totalQty=0;

            for(int i=0; i<bean_cart.size(); i++){

                totalQty += Integer.parseInt(bean_cart.get(i).getProduct_qty());

            }

            return totalQty;
        }


    }


    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
      /*  appPrefs.setAttachment("");
        appPrefs.setScheme("");
        appPrefs.setComment("");*/

        /*Intent i;
        if (appPrefs.getFromActivity().equalsIgnoreCase("Dist_MyOrder")) {
            i = new Intent(OrderHistoryDetails.this, DistMyOrderHistActivity.class);
        } else if (appPrefs.getFromActivity().equalsIgnoreCase("Dist_SalesRetailerOrder")) {
            i = new Intent(OrderHistoryDetails.this, DistSalesRetailerOrderHistActivity.class);
        } else if (appPrefs.getFromActivity().equalsIgnoreCase("Retailer_MyOrderHist")) {
            i = new Intent(OrderHistoryDetails.this, RetailerMyOrderHistActivity.class);
        } else if (appPrefs.getFromActivity().equalsIgnoreCase("Comp_SalesDistRetOrderHist")) {
            i = new Intent(OrderHistoryDetails.this, CompSalesDistRetOrderHistActivity.class);
        } else {
            i = new Intent(OrderHistoryDetails.this, Order_history.class);
        }*/
        /*appPrefs.setFromActivity("");
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();*/
    }
}
