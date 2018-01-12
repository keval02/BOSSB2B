package com.nivida.bossb2b;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
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

import com.google.gson.Gson;
import com.nivida.bossb2b.Bean.Bean_Categeory;
import com.nivida.bossb2b.Bean.Bean_Set_Product_Categeory;
import com.nivida.bossb2b.Model.APIServices;
import com.nivida.bossb2b.Model.OfflineProductModel;
import com.nivida.bossb2b.Model.ServiceGenerator;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


    OfflineProductModel offlineProductModelListData;

    Database db;
    boolean isReloadTimes = false;

    APIServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        apiServices = ServiceGenerator.getServiceClass();

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
        subCategeotyAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_spinner_item, subCategeoryItem);
        spinner.setAdapter(subCategeotyAdapter);

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
                // new GetProductData(subCategoryIDs.get(position)).execute();


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
                if(Internet.isConnectingToInternet(getApplicationContext())){
                    GetOfflineDatas();
                }else {

                    Internet.noInternet(getApplicationContext());
                }
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


        if (!prefs.getCategoryDatas().isEmpty()) {
            offlineProductModelListData = new Gson().fromJson(prefs.getCategoryDatas(), OfflineProductModel.class);
            for (int i = 0; i < offlineProductModelListData.getData().size(); i++) {
                Bean_Categeory bean_categeory = new Bean_Categeory();

                bean_categeory.setId(offlineProductModelListData.getData().get(i).getId());
                bean_categeory.setName(offlineProductModelListData.getData().get(i).getName());
                bean_categeory.setApp_image(offlineProductModelListData.getData().get(i).getApp_image());
                bean_categeory.setIs_child(offlineProductModelListData.getData().get(i).getIs_child());


                if (offlineProductModelListData.getData().get(i).getIs_child() == 1) {

                    subCategoryIDs.clear();
                    subCategeoryItem.clear();

                    subCategoryIDs.add("0");
                    subCategeoryItem.add("All Products");
                }

                bean_categeories.add(bean_categeory);
            }

            for (int k = 0; k < offlineProductModelListData.getData().get(0).getSubCategory().size(); k++) {
                subCategoryIDs.add(offlineProductModelListData.getData().get(0).getSubCategory().get(k).getId());
                subCategeoryItem.add(offlineProductModelListData.getData().get(0).getSubCategory().get(k).getName());
            }
            set_product_categeories.clear();


            for (int z = 0; z < offlineProductModelListData.getData().get(0).getSubCategory().size(); z++) {

                for (int m = 0; m < offlineProductModelListData.getData().get(0).getSubCategory().get(z).getProduct().size(); m++) {

                    Bean_Set_Product_Categeory categeory = new Bean_Set_Product_Categeory();

                    categeory.setId(offlineProductModelListData.getData().get(0).getSubCategory().get(z).getProduct().get(m).getId());
                    categeory.setCode(offlineProductModelListData.getData().get(0).getSubCategory().get(z).getProduct().get(m).getProduct_code());
                    categeory.setName(offlineProductModelListData.getData().get(0).getSubCategory().get(z).getProduct().get(m).getProduct_name());
                    categeory.setProduct_mrp(offlineProductModelListData.getData().get(0).getSubCategory().get(z).getProduct().get(m).getMrp());
                    categeory.setProduct_selling_price(offlineProductModelListData.getData().get(0).getSubCategory().get(z).getProduct().get(m).getSelling_price());
                    categeory.setCategeory_id(offlineProductModelListData.getData().get(0).getSubCategory().get(z).getProduct().get(m).getCategory_id());
                    categeory.setMax_quantity(Integer.parseInt(offlineProductModelListData.getData().get(0).getSubCategory().get(z).getProduct().get(m).getQty()));
                    categeory.setB2b_stock(offlineProductModelListData.getData().get(0).getSubCategory().get(z).getProduct().get(m).getB2b_stock());


                    set_product_categeories.add(categeory);


                }
            }

            spinner.setVisibility(View.VISIBLE);
            productdata_listview.setVisibility(View.VISIBLE);
            subCategeotyAdapter.notifyDataSetChanged();
            setSubCategeoryAdapter.notifyDataSetChanged();


        }


        if (prefs.getCategoryDatas().isEmpty()) {
            if(Internet.isConnectingToInternet(getApplicationContext())){
                GetOfflineDatas();
            }else {

                Internet.noInternet(getApplicationContext());
            }


        }
        //  new GetCategories("0").execute();
    }

    private void GetOfflineDatas() {

        try {
            loadingView = new ProgressActivity(PlaceOrderActivity.this, "");
            loadingView.setCancelable(false);
            loadingView.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<OfflineProductModel> offlineProductModelCall = apiServices.offlineModelList();
        offlineProductModelCall.enqueue(new Callback<OfflineProductModel>() {
            @Override
            public void onResponse(Call<OfflineProductModel> call, Response<OfflineProductModel> response) {
                loadingView.dismiss();
                prefs.setCategoryDatas("");
                bean_categeories.clear();

                subCategeoryItem.clear();
                subCategoryIDs.clear();

                set_product_categeories.clear();
                OfflineProductModel offlineProductModel = response.body();


                if (offlineProductModel != null) {
                    if (offlineProductModel.isStatus()) {

                        for (int i = 0; i < offlineProductModel.getData().size(); i++) {
                            Bean_Categeory bean_categeory = new Bean_Categeory();


                            bean_categeory.setId(offlineProductModel.getData().get(i).getId());
                            bean_categeory.setName(offlineProductModel.getData().get(i).getName());
                            bean_categeory.setApp_image(offlineProductModel.getData().get(i).getApp_image());
                            bean_categeory.setIs_child(offlineProductModel.getData().get(i).getIs_child());


                            bean_categeories.add(bean_categeory);

                        }


                        if (offlineProductModel.getData().get(0).getIs_child() == 1) {
                            subCategoryIDs.add("0");
                            subCategeoryItem.add("All Products");

                            for (int k = 0; k < offlineProductModel.getData().get(0).getSubCategory().size(); k++) {
                                subCategoryIDs.add(offlineProductModel.getData().get(0).getSubCategory().get(k).getId());
                                subCategeoryItem.add(offlineProductModel.getData().get(0).getSubCategory().get(k).getName());
                            }
                        }


                        for (int z = 0; z < offlineProductModel.getData().get(0).getSubCategory().size(); z++) {

                            for (int m = 0; m < offlineProductModel.getData().get(0).getSubCategory().get(z).getProduct().size(); m++) {

                                Bean_Set_Product_Categeory categeory = new Bean_Set_Product_Categeory();

                                categeory.setId(offlineProductModel.getData().get(0).getSubCategory().get(z).getProduct().get(m).getId());
                                categeory.setCode(offlineProductModel.getData().get(0).getSubCategory().get(z).getProduct().get(m).getProduct_code());
                                categeory.setName(offlineProductModel.getData().get(0).getSubCategory().get(z).getProduct().get(m).getProduct_name());
                                categeory.setProduct_mrp(offlineProductModel.getData().get(0).getSubCategory().get(z).getProduct().get(m).getMrp());
                                categeory.setProduct_selling_price(offlineProductModel.getData().get(0).getSubCategory().get(z).getProduct().get(m).getSelling_price());
                                categeory.setCategeory_id(offlineProductModel.getData().get(0).getSubCategory().get(z).getProduct().get(m).getCategory_id());
                                categeory.setMax_quantity(Integer.parseInt(offlineProductModel.getData().get(0).getSubCategory().get(z).getProduct().get(m).getQty()));
                                categeory.setB2b_stock(offlineProductModel.getData().get(0).getSubCategory().get(z).getProduct().get(m).getB2b_stock());


                                set_product_categeories.add(categeory);


                            }


                        }

                        categoryListAdapter.notifyDataSetChanged();
                        subCategeotyAdapter.notifyDataSetChanged();
                        setSubCategeoryAdapter.notifyDataSetChanged();


                        String getCategoryDatas = new Gson().toJson(response.body());
                        prefs.setCategoryDatas(getCategoryDatas);
                        offlineProductModelListData = new Gson().fromJson(prefs.getCategoryDatas(), OfflineProductModel.class);


                    }
                }


            }

            @Override
            public void onFailure(Call<OfflineProductModel> call, Throwable t) {
                loadingView.dismiss();
            }
        });


    }

    @Override
    public void onClick(final int position) {
        if (bean_categeories.get(position).getIs_child() == 1) {

            subCategoryIDs.clear();
            subCategeoryItem.clear();

            subCategoryIDs.add("0");
            subCategeoryItem.add("All Products");
            set_product_categeories.clear();


            for (int k = 0; k < offlineProductModelListData.getData().get(position).getSubCategory().size(); k++) {
                subCategoryIDs.add(offlineProductModelListData.getData().get(position).getSubCategory().get(k).getId());
                subCategeoryItem.add(offlineProductModelListData.getData().get(position).getSubCategory().get(k).getName());
            }

            for (int z = 0; z < offlineProductModelListData.getData().get(position).getSubCategory().size(); z++) {

                for (int m = 0; m < offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().size(); m++) {
                    Bean_Set_Product_Categeory categeory = new Bean_Set_Product_Categeory();

                    categeory.setId(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getId());
                    categeory.setCode(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getProduct_code());
                    categeory.setName(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getProduct_name());
                    categeory.setProduct_mrp(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getMrp());
                    categeory.setProduct_selling_price(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getSelling_price());
                    categeory.setCategeory_id(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getCategory_id());
                    categeory.setMax_quantity(Integer.parseInt(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getQty()));
                    categeory.setB2b_stock(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getB2b_stock());

                    set_product_categeories.add(categeory);
                }
            }


            spinner.setAdapter(subCategeotyAdapter);

            // new GetSubCategeoryData(bean_categeories.get(position).getId()).execute();
            spinner.setVisibility(View.VISIBLE);
            productdata_listview.setVisibility(View.VISIBLE);
            setSubCategeoryAdapter.notifyDataSetChanged();
        } else {
            // new GetProductData(bean_categeories.get(position).getId()).execute();

            set_product_categeories.clear();
            for (int z = 0; z < offlineProductModelListData.getData().get(position).getSubCategory().size(); z++) {


                for (int m = 0; m < offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().size(); m++) {
                    Bean_Set_Product_Categeory categeory = new Bean_Set_Product_Categeory();

                    categeory.setId(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getId());
                    categeory.setCode(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getProduct_code());
                    categeory.setName(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getProduct_name());
                    categeory.setProduct_mrp(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getMrp());
                    categeory.setProduct_selling_price(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getSelling_price());
                    categeory.setCategeory_id(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getCategory_id());
                    categeory.setMax_quantity(Integer.parseInt(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getQty()));
                    categeory.setB2b_stock(offlineProductModelListData.getData().get(position).getSubCategory().get(z).getProduct().get(m).getB2b_stock());

                    set_product_categeories.add(categeory);
                }
            }

            setSubCategeoryAdapter.notifyDataSetChanged();
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
                            categeory.setIs_child(main.getInt("is_child"));

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