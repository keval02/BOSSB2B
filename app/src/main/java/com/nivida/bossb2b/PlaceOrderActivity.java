package com.nivida.bossb2b;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_Categeory;
import com.nivida.bossb2b.Bean.Bean_Set_Product_Categeory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity implements CategoryListAdapter.OnViewClick, SetSubCategeoryAdapter.OnItemChecked {


    List<Bean_Categeory> bean_categeories = new ArrayList<>();

    List<Bean_Set_Product_Categeory> set_product_categeories = new ArrayList<>();


    ProgressActivity loadingView;
    ListView productdata_listview;
    RecyclerView recyclerView;
    CategoryListAdapter categoryListAdapter;
    SetSubCategeoryAdapter setSubCategeoryAdapter;

    ListView drawerlistview;
    Toolbar toolbar;

    Button btn_con;
    Spinner spinner;
    AppPref prefs;
    TextView cart_count;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView cart_icon, home, reload;

    LinearLayout linearLayout;
    ArrayAdapter<String> subCategeotyAdapter;
    ArrayList<String> subCategoryIDs = new ArrayList<>();
    ArrayList<String> subCategeoryItem = new ArrayList<>();

    String CompanyName = "";

    Database db;
    boolean isReloadTimes = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Intent intent = getIntent();

        CompanyName = intent.getStringExtra("CompanyName");

        db = new Database(getApplicationContext());
        prefs = new AppPref(getApplicationContext());
        productdata_listview = (ListView) findViewById(R.id.lv_itemdetail);
        spinner = (Spinner) findViewById(R.id.spinner_list);
        linearLayout = (LinearLayout) findViewById(R.id.custom_linear);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btn_con = (Button) findViewById(R.id.btn_con);
        cart_count = (TextView) findViewById(R.id.updatecart);
        cart_icon = (ImageView) findViewById(R.id.cart_icon);
        home = (ImageView) findViewById(R.id.dr_image_home);
        reload = (ImageView) findViewById(R.id.dr_image_reload);


        categoryListAdapter = new CategoryListAdapter(getApplicationContext(), bean_categeories, this);


        setSubCategeoryAdapter = new SetSubCategeoryAdapter(getApplicationContext(), set_product_categeories, this);
        productdata_listview.setAdapter(setSubCategeoryAdapter);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoryListAdapter);
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


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new GetProductData(subCategoryIDs.get(position)).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Bean_Set_Product_Categeory> selectedProducts = setSubCategeoryAdapter.getProducts();
                if (db.getCartCount() > 0) {
                    Intent intent = new Intent(getApplicationContext(), InvoiceActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Add Atleast One Quantity of Product", Toast.LENGTH_SHORT).show();
                }

            }
        });


        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isReloadTimes = true;
                new GetCategories("0").execute();
            }
        });


        btn_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Bean_Set_Product_Categeory> selectedProducts = setSubCategeoryAdapter.getProducts();
                if (db.getCartCount() > 0) {
                    Intent intent = new Intent(getApplicationContext(), InvoiceActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Add Atleast One Quantity of Product", Toast.LENGTH_SHORT).show();
                }
            }
        });


        new GetCategories("0").execute();
    }

    @Override
    public void onClick(final int position) {
        if (bean_categeories.get(position).getIs_child()) {
            new GetSubCategeoryData(bean_categeories.get(position).getId()).execute();
            spinner.setVisibility(View.VISIBLE);
            productdata_listview.setVisibility(View.VISIBLE);
        } else {
            new GetProductData(bean_categeories.get(position).getId()).execute();
            productdata_listview.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
        }

    }

    @Override
    public void onItemCheckedListener() {
        int count = db.getCartCount();

        if (count > 0) {
            cart_count.setText(String.valueOf(count));
            cart_count.setVisibility(View.VISIBLE);

        } else {
            cart_count.setVisibility(View.GONE);
        }
    }

    class GetCategories extends AsyncTask<Void, Void, String> {

        String categoryID = "";
        public GetCategories(String categoryID) {
            this.categoryID = categoryID;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isReloadTimes) {
                try {
                    loadingView = new ProgressActivity(PlaceOrderActivity.this, "");
                    loadingView.setCancelable(false);
                    loadingView.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairs = new ArrayList<>();

            pairs.add(new BasicNameValuePair("parent_id", categoryID));

            String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_CATEGORY, ServiceHandler.POST, pairs);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            if (isReloadTimes) {
                loadingView.dismiss();
            }

            if (json == null || json.isEmpty()) {
                Toast.makeText(PlaceOrderActivity.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {

                try {
                    JSONObject object = new JSONObject(json);

                    boolean status = object.getBoolean("status");

                    if (status) {
                        JSONArray dataArray = object.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject main = dataArray.getJSONObject(i);

                            Bean_Categeory categeory = new Bean_Categeory();

                            categeory.setId(main.getString("id"));
                            categeory.setApp_image(main.getString("app_image"));
                            categeory.setName(main.getString("name"));
                            categeory.setIs_child(main.getInt("is_child") == 1);

                            bean_categeories.add(categeory);


                        }

                        new GetSubCategeoryData(bean_categeories.get(0).getId()).execute();
                    } else {
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                    categoryListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        }
    }

    class GetSubCategeoryData extends AsyncTask<Void, Void, String> {

        String categoryID = "";


        public GetSubCategeoryData(String categoryID) {
            this.categoryID = categoryID;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isReloadTimes) {
                try {
                    loadingView = new ProgressActivity(PlaceOrderActivity.this, "");
                    loadingView.setCancelable(false);
                    loadingView.show();

                } catch (Exception e) {

                }
            }

        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairs = new ArrayList<>();

            pairs.add(new BasicNameValuePair("parent_id", categoryID));

            Log.e("pairs", pairs.toString());

            String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_CATEGORY, ServiceHandler.POST, pairs);
            return json;
        }


        @Override
        protected void onPostExecute(String json) {

            super.onPostExecute(json);
            if (isReloadTimes) {
                loadingView.dismiss();
            }
            subCategeoryItem.clear();
            subCategoryIDs.clear();

            subCategoryIDs.add(categoryID);
            subCategeoryItem.add("All Products");

            if (json == null || json.isEmpty()) {
                Toast.makeText(PlaceOrderActivity.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {


                try {
                    JSONObject object = new JSONObject(json);
                    boolean status = object.getBoolean("status");
                    if (status) {

                        JSONArray dataArray = object.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject main = dataArray.getJSONObject(i);
                            subCategoryIDs.add(main.getString("id"));
                            subCategeoryItem.add(main.getString("name"));

                        }

                        subCategeotyAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_spinner_item, subCategeoryItem);
                        spinner.setAdapter(subCategeotyAdapter);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }


    }

    class GetProductData extends AsyncTask<Void, Void, String> {

        String categoryID = "";


        public GetProductData(String categoryID) {
            this.categoryID = categoryID;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isReloadTimes) {
                try {
                    loadingView = new ProgressActivity(PlaceOrderActivity.this, "");
                    loadingView.setCancelable(false);
                    loadingView.show();

                } catch (Exception e) {

                }
            }

        }


        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> pairs = new ArrayList<>();

            pairs.add(new BasicNameValuePair("category_id", categoryID));
            pairs.add(new BasicNameValuePair("page_limit", "-1"));

            Log.e("params", pairs.toString());

            String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.GET_PRODUCTLIST, ServiceHandler.POST, pairs);

            return json;


        }


        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            Log.e("json", json);
            if (isReloadTimes) {
                loadingView.dismiss();
            }

            set_product_categeories.clear();
            if (json == null || json.isEmpty()) {
                Toast.makeText(PlaceOrderActivity.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {

                try {
                    JSONObject object = new JSONObject(json);

                    boolean status = object.getBoolean("status");

                    if (status) {
                        JSONArray dataArray = object.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject main = dataArray.getJSONObject(i);
                            JSONObject Product = main.getJSONObject("Product");

                            Bean_Set_Product_Categeory categeory = new Bean_Set_Product_Categeory();

                            categeory.setId(Product.getString("id"));
                            categeory.setCode(Product.getString("product_code"));
                            categeory.setName(Product.getString("product_name"));
                            categeory.setProduct_mrp(Product.getString("mrp"));
                            categeory.setProduct_selling_price(Product.getString("selling_price"));
                            categeory.setCategeory_id(Product.getString("category_id"));
                            categeory.setMax_quantity(Integer.parseInt(Product.getString("qty")));
                            categeory.setB2b_stock(Product.getString("b2b_stock"));


                            set_product_categeories.add(categeory);


                        }
                    } else {
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                    setSubCategeoryAdapter.notifyDataSetChanged();
                    isReloadTimes = false;
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        }
    }


    @Override
    public void onResume() {

        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");

        int count = db.getCartCount();

        if (count > 0) {
            cart_count.setText(String.valueOf(count));
            cart_count.setVisibility(View.VISIBLE);

        } else {
            cart_count.setVisibility(View.GONE);
        }
        super.onResume();
    }

    @Override
    protected void onStart() {
        int count = db.getCartCount();

        if (count > 0) {
            cart_count.setText(String.valueOf(count));
            cart_count.setVisibility(View.VISIBLE);

        } else {
            cart_count.setText("");
        }
        super.onStart();
    }


    public void onBackPressed() {


        if (prefs.isDistributorLogin() == true) {
            Intent i = new Intent(PlaceOrderActivity.this, DistributorLogin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();

        } else {
            Intent i = new Intent(PlaceOrderActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

}