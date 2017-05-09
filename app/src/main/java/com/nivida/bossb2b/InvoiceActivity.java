package com.nivida.bossb2b;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_Invoice_List;
import com.nivida.bossb2b.Bean.Bean_Set_Product_Categeory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InvoiceActivity extends AppCompatActivity {
    Button countiune, preview;
    ListView invoice_view;
    SetSubCategeoryAdapter setSubCategeoryAdapter;
    Custom_Invoice_Adapter customInvoiceAdapter;
    List<Bean_Set_Product_Categeory> set_product_categeories = new ArrayList<>();
    Database db;
    TextView categeory;
    AppPref pref;
    ImageView delete;
    String CompanyName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        Intent intent = getIntent();

        CompanyName = intent.getStringExtra("CompanyName");


        db = new Database(getApplicationContext());
        pref = new AppPref(getApplicationContext());
        invoice_view = (ListView) findViewById(R.id.invoice_listview);
        set_product_categeories = db.getCartData();
        preview = (Button) findViewById(R.id.btn_preview);
        categeory = (TextView) findViewById(R.id.txt_categeory_name);
        customInvoiceAdapter = new Custom_Invoice_Adapter(getApplicationContext(), set_product_categeories);
        invoice_view.setAdapter(customInvoiceAdapter);
        delete = (ImageView) findViewById(R.id.img_delete);


        countiune = (Button) findViewById(R.id.btn_continue);
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


        invoice_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //hide KB
                InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) { }
        });


        if(db.getCartCount()<=0){

            Toast.makeText(getApplicationContext(),"There is No Product in Cart", Toast.LENGTH_LONG).show();
        }


        /*cart_icon.setVisibility(View.GONE);
        cart_count.setVisibility(View.GONE);
*/

        countiune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (db.getCartCount() > 0) {

                    if (customInvoiceAdapter.isAnyBlankQty()) {
                        Toast.makeText(InvoiceActivity.this, "Please Enter At least One Quantity in Product", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(InvoiceActivity.this, CheckoutPage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("CompanyName", CompanyName);
                        startActivity(intent);
                    }


                } else {


                    Toast.makeText(getApplicationContext(), "Please add Atleast One Product for Checkout", Toast.LENGTH_SHORT).show();

                }

            }


        });


        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db.getCartCount() > 0) {

                    if (customInvoiceAdapter.isAnyBlankQty()) {
                        Toast.makeText(InvoiceActivity.this, "Please Enter At least One Quantity in Product Or Delete The Product", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), PlaceOrderActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }


                }

                else{
                    Intent intent = new Intent(getApplicationContext(), PlaceOrderActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();



                }


            }
        });


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

    public void onBackPressed() {
        if (db.getCartCount() > 0) {

            if (customInvoiceAdapter.isAnyBlankQty()) {
                Toast.makeText(InvoiceActivity.this, "Please Enter At least One Quantity in Product or Delete The Product", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), PlaceOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
        else{
            Intent intent = new Intent(getApplicationContext(), PlaceOrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


    }
}


