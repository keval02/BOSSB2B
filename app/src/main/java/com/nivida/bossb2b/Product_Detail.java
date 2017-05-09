package com.nivida.bossb2b;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_Attribute;
import com.nivida.bossb2b.Bean.Bean_Product;
import com.nivida.bossb2b.Bean.Bean_ProductCart;
import com.nivida.bossb2b.Bean.Bean_ProductCatalog;
import com.nivida.bossb2b.Bean.Bean_ProductFeature;
import com.nivida.bossb2b.Bean.Bean_ProductImage;
import com.nivida.bossb2b.Bean.Bean_ProductOprtion;
import com.nivida.bossb2b.Bean.Bean_ProductStdSpec;
import com.nivida.bossb2b.Bean.Bean_ProductTechSpec;
import com.nivida.bossb2b.Bean.Bean_Value_Selected_Detail;
import com.nivida.bossb2b.Bean.Product_Youtube;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class Product_Detail extends AppCompatActivity {

    TextView txt_high, product_detail_marquee , txt_productdetail;
    String option_name = "";
    String option_id = "";
    TextView tv_pop_pname, tv_pop_code, tv_pop_packof, tv_pop_mrp, tv_pop_sellingprice, tv_total, tv_txt_mrp;
    LinearLayout layout_imagegrid, Feature_text_layout, option_text_layout, l_video_upper, l_video_lower, layout_youtube_forimg;
    int count = 0;
    ArrayList<ArrayList<Bean_Attribute>> array_attribute_main = new ArrayList<ArrayList<Bean_Attribute>>();
    ArrayList<Integer> list_data = new ArrayList<Integer>();
    ArrayList<Bean_Attribute> array_att = new ArrayList<Bean_Attribute>();
    String CategoryID;
    ArrayList<Bean_Value_Selected_Detail> array_value = new ArrayList<Bean_Value_Selected_Detail>();
    LinearLayout.LayoutParams params;
    public static ArrayList<String> arrOptionTypeID = new ArrayList<String>();
    public static ArrayList<String> moreinfo_arry = new ArrayList<String>();
    public static ArrayList<String> arrOptionTypeName = new ArrayList<String>();
    float amount1;
    public static ArrayList<String> WishList = new ArrayList<String>();


    Button btn_buyonline_product_detail, btn_ready, btn_buy;
    ArrayList<String> list_of_images = new ArrayList<String>();
    ResultHolder result_holder;
    LinearLayout l_spinner, l_spinner_text, l_linear, l_sort;
    ArrayList<Bean_ProductImage> bean_data = new ArrayList<Bean_ProductImage>();
    //private SectionsPagerAdapter mSectionsPagerAdapter;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    LinearLayout l_view_spinner;
    /*ArrayList<Bean_Product> bean_product1 = new ArrayList<Bean_Product>();
    ArrayList<Bean_ProductFeature> bean_productFeatures = new ArrayList<Bean_ProductFeature>();
    ArrayList<Bean_ProductTechSpec> bean_productTechSpecs = new ArrayList<Bean_ProductTechSpec>();
    ArrayList<Bean_ProductStdSpec> bean_productStdSpecs = new ArrayList<Bean_ProductStdSpec>();
    ArrayList<Bean_ProductOprtion> bean_productOprtions = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductCart> bean_productCart = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_ProductImage> bean_productImages = new ArrayList<Bean_ProductImage>();
    ArrayList<Bean_ProductCatalog> bean_ProductCatalog = new ArrayList<Bean_ProductCatalog>();
    ArrayList<String> pro_img = new ArrayList<String>();
    ArrayList<Product_Youtube> bean_productyoutube = new ArrayList<Product_Youtube>();*/
    ArrayList<Bean_Product> bean_product_db = new ArrayList<Bean_Product>();
    ArrayList<Bean_ProductFeature> bean_productFeatures_db = new ArrayList<Bean_ProductFeature>();
    ArrayList<Bean_ProductTechSpec> bean_productTechSpecs_db = new ArrayList<Bean_ProductTechSpec>();
    ArrayList<Bean_ProductStdSpec> bean_productStdSpecs_db = new ArrayList<Bean_ProductStdSpec>();
    ArrayList<Bean_ProductOprtion> bean_productOprtions_db = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductCart> bean_productCart_db = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_ProductImage> bean_productImages_db = new ArrayList<Bean_ProductImage>();
    ArrayList<Product_Youtube> bean_productYoutube_db = new ArrayList<Product_Youtube>();

    ArrayList<Product_Youtube> bean_Product_Youtube = new ArrayList<Product_Youtube>();
    ArrayList<Bean_Product> bean_product1 = new ArrayList<Bean_Product>();
    ArrayList<Bean_ProductFeature> bean_productFeatures = new ArrayList<Bean_ProductFeature>();
    ArrayList<Bean_ProductTechSpec> bean_productTechSpecs = new ArrayList<Bean_ProductTechSpec>();
    ArrayList<Bean_ProductStdSpec> bean_productStdSpecs = new ArrayList<Bean_ProductStdSpec>();
    ArrayList<Bean_ProductOprtion> bean_productOprtions = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductCart> bean_productCart = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_ProductImage> bean_productImages = new ArrayList<Bean_ProductImage>();
    ArrayList<Bean_ProductCatalog> bean_ProductCatalog = new ArrayList<Bean_ProductCatalog>();
    ArrayList<String> pro_img = new ArrayList<String>();
    ArrayList<Product_Youtube> bean_productyoutube = new ArrayList<Product_Youtube>();
    DatabaseHandler db;
    Database db1;
    String json = new String();
    //ImageLoader imageloader;

    TextView txt;

    int selected_image_position = 0;

    ProgressActivity loadingView;
    ImageView minuss, plus;
    Button buy_cart, cancel, btn_wherebuy;
    EditText edt_count;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    TextView p_prize1;
    String url, setthumbnail;
    int s = 0;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    TextView tv_product_mrp, tv_product_selling_price, tv_product_name, tv_product_code, tv_packof, video_text;
    ImageView img_prodetail_wishlist, img_prodetail_shoppinglist, img_full;
    LinearLayout l_desc, l_Std_spec, l_technical, l_features, l_videos, l_more, l_video;
    LinearLayout l_desc_detail, l_std_spec_detail, l_technical_detail, l_features_detail, l_videos_detail, l_more_info1, moreinfo_text_layout, standard_text_layout, technical_text_layout;
    ImageView img_desc, img_std_spec, img_technical, img_features, img_videos, img_more, videoyoutube_img;
    TextView text_desc, text_std_spec, text_features, text_Tech_spec, text_option, more_Info_text, tvbullet;
    /* ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();*/
    Boolean desc = false, spec = false, technical = false, catalogue = false, features = false, videos = false, more = false, videoupper = false;
    String pid;
    AppPrefs app;
    String user_id_main = new String();
    /*  private static final Integer[] IMAGES = {R.drawable.boss1,
              R.drawable.boss2,
              R.drawable.boss3,
              R.drawable.boss4

      };*/
    TextView offers;
    String wishid;
    String mrp, sellingprice;
    Dialog dialog;
    private static ViewPager img_slider, mViewPager;
    String product_code = "";
    String selected_image_url = "";
    String selected_lable = "";
    LinearLayout l_youtubevideo_main;
    LinearLayout lnr_mrp, lnr_sp, lnr_pack;

    CircleIndicator indicator;
    com.nivida.bossb2b.CustomPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__detail);
        Log.e("Page ", "Product_Detail");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        setActionBar();
        Intent i1 = getIntent();
        ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
        //    bean_ProductCatalog=(ArrayList<Bean_ProductCatalog>) i1.getSerializableExtra("catalog");
//        Log.e("bean_ProductC.size", "" + bean_ProductCatalog.size());
        app = new AppPrefs(Product_Detail.this);
        pid = app.getproduct_id().toString();
        Log.e("pid", "" + pid);
        wishid = app.getwishid();
        CategoryID = app.getCategoryid().toString();
        // imageloader = new ImageLoader(Product_Detail.this);
        fetchID();

        Log.e("wishid------------error", "" + app.getwishid().toString());
        setRefershData();

        btn_ready = (Button) findViewById(R.id.btn_ready);
        btn_buy = (Button) findViewById(R.id.btn_buyonline);

        txt_productdetail = (TextView) findViewById(R.id.txt_productdetail);


        db1 = new Database(getApplicationContext());

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




        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_Detail.this, InvoiceActivity.class);
                startActivity(i);

            }
        });


        /*if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                app = new AppPrefs(getApplicationContext());
                if (app.getUserRoleId().equalsIgnoreCase(C.DIS_SALES_ROLE) ||
                        app.getUserRoleId().equalsIgnoreCase(C.COMP_SALES_ROLE)) {
                    CompSalesPrefs salesPrefs = new CompSalesPrefs(getApplicationContext());
                    user_id_main = salesPrefs.getUser_id();
                } else {
                    user_id_main = user_data.get(i).getUser_id().toString();
                }
            }

        } else {
            user_id_main = "";
        }*/

        new set_marquee().execute();

    /*    btn_ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_Detail.this, InvoiceActivity.class);
                startActivity(i);

            }
        })*/
        ;

        tv_packof.setVisibility(View.GONE);
        tv_product_mrp.setVisibility(View.GONE);
        tv_product_selling_price.setVisibility(View.GONE);


        lnr_mrp.setVisibility(View.GONE);
        lnr_sp.setVisibility(View.GONE);
        lnr_pack.setVisibility(View.GONE);


    }


    private void fetchID() {
        tv_product_mrp = (TextView) findViewById(R.id.tv_product_mrp);
        tv_product_selling_price = (TextView) findViewById(R.id.tv_product_selling_price);
        tv_product_name = (TextView) findViewById(R.id.tv_product_name);
        tv_product_code = (TextView) findViewById(R.id.tv_product_code);
        tv_packof = (TextView) findViewById(R.id.tv_packof);
        tvbullet = (TextView) findViewById(R.id.tvbullet);
        offers = (TextView) findViewById(R.id.offer);
        img_slider = (ViewPager) findViewById(R.id.img_slider);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        l_youtubevideo_main = (LinearLayout) findViewById(R.id.l_youtubevideo_main);
        /*img_prodetail_wishlist = (ImageView) findViewById(R.id.img_prodetail_wishlist);*/
        img_prodetail_shoppinglist = (ImageView) findViewById(R.id.img_prodetail_shoppinglist);
        img_full = (ImageView) findViewById(R.id.img_full);

        lnr_mrp = (LinearLayout) findViewById(R.id.lnr_mrp);
        lnr_sp = (LinearLayout) findViewById(R.id.lnr_sp);
        lnr_pack = (LinearLayout) findViewById(R.id.lnr_pack);


        product_detail_marquee = (TextView) this.findViewById(R.id.product_detail_marquee);
        product_detail_marquee.setSelected(true);

        btn_buyonline_product_detail = (Button) findViewById(R.id.btn_buyonline_product_detail);

        layout_imagegrid = (LinearLayout) findViewById(R.id.layout_imagegrid);
        Feature_text_layout = (LinearLayout) findViewById(R.id.Feature_text_layout);
        moreinfo_text_layout = (LinearLayout) findViewById(R.id.moreinfo_text_layout);
        standard_text_layout = (LinearLayout) findViewById(R.id.standard_text_layout);
       /* technical_text_layout = (LinearLayout) findViewById(R.id.technical_text_layout);*/
        layout_youtube_forimg = (LinearLayout) findViewById(R.id.layout_youtube_forimg);


        l_desc = (LinearLayout) findViewById(R.id.l_description);
        l_Std_spec = (LinearLayout) findViewById(R.id.l_standard_technical);
        l_technical = (LinearLayout) findViewById(R.id.l_technical);
        option_text_layout = (LinearLayout) findViewById(R.id.option_text_layout);

        l_features = (LinearLayout) findViewById(R.id.l_Feature);
        l_videos = (LinearLayout) findViewById(R.id.l_videos);
        l_more = (LinearLayout) findViewById(R.id.l_more_Info);


        l_desc_detail = (LinearLayout) findViewById(R.id.l_description_detail);
        l_std_spec_detail = (LinearLayout) findViewById(R.id.l_standard_technical_detail);
        l_technical_detail = (LinearLayout) findViewById(R.id.l_technical_detail);
        l_features_detail = (LinearLayout) findViewById(R.id.l_Feature_detail);
        l_videos_detail = (LinearLayout) findViewById(R.id.l_videos_detail);
        l_more_info1 = (LinearLayout) findViewById(R.id.l_more_Info_detail);
        l_video_upper = (LinearLayout) findViewById(R.id.l_video_upper);
        l_video_lower = (LinearLayout) findViewById(R.id.l_video_lower);

        img_desc = (ImageView) findViewById(R.id.desc_image);
        img_std_spec = (ImageView) findViewById(R.id.standard_technical_image);
        img_technical = (ImageView) findViewById(R.id.technical_image);

        img_features = (ImageView) findViewById(R.id.Feature_image);
        img_videos = (ImageView) findViewById(R.id.videos_image);
        videoyoutube_img = (ImageView) findViewById(R.id.videoyoutube_img);
        img_more = (ImageView) findViewById(R.id.more_Info_image);

        text_desc = (TextView) findViewById(R.id.desc_text);
        text_std_spec = (TextView) findViewById(R.id.standard_technical_text);
        text_features = (TextView) findViewById(R.id.Feature_text);
        /*text_Tech_spec = (TextView) findViewById(R.id.technical_text);*/
        more_Info_text = (TextView) findViewById(R.id.more_Info_text);
        btn_wherebuy = (Button) findViewById(R.id.btn_wherebuy);

        if (!app.getUserRoleId().equalsIgnoreCase(C.CUSTOMER_ROLE) && !app.getUserRoleId().equalsIgnoreCase("")) {
            btn_wherebuy.setVisibility(View.GONE);
        }

        tv_product_mrp.setPaintFlags(tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        setRefershData();

        /*Log.e("wishid",""+wishid.toString());
        if (wishid.toString().equalsIgnoreCase("1")) {

            img_prodetail_wishlist.setImageResource(R.drawable.whishlist_boss);
            img_prodetail_shoppinglist.setTag("a");
            Log.e("wishid1", "" + wishid.toString());
        }
        else if(wishid.toString().equalsIgnoreCase("2"))
        {
            img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
            img_prodetail_wishlist.setTag("b");
            Log.e("wishid2", "" + wishid.toString());
        }*/
   /* more_Info_text.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            SetRefershDataProduct();

            if (bean_product1.size() != 0) {

                for (int i = 0; i < bean_product1.size(); i++) {

                    if (bean_product1.get(i).getPro_id().equalsIgnoreCase(pid)) {

                        new DownloadFile().execute("http://app.nivida.in/boss" + bean_product1.get(i).getPro_moreinfo().toString(), "ProductDetail.pdf");
                    }
                }
            }

        }
    });*/


        btn_wherebuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(Product_Detail.this);
                db.Delete_ALL_table();
                db.close();
                /*Intent i = new Intent(Product_Detail.this, Where_toBuy.class);
                i.putExtra("catalog", bean_ProductCatalog);
                startActivity(i);
                finish();*/
            }
        });
        img_prodetail_shoppinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(Product_Detail.this);
                db.Delete_ALL_table();
                db.close();
                /*Intent i = new Intent(Product_Detail.this, Add_to_shoppinglist.class);
                app.setRef_Shopping("product_detail");
                app.setproduct_id(pid);
                startActivity(i);
                finish();*/
            }
        });


        img_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_Detail.this, Event_Gallery_photo_view_Activity.class);
                app.setproduct_id(pid);
                Log.e("img_full", "" + pid);
                i.putExtra("position", selected_image_position);
                Log.e("pos selected", "" + selected_image_position);
                startActivity(i);
                finish();
            }
        });

        setRefershData();

        /*if(user_data.size() != 0){


            img_prodetail_wishlist.setVisibility(View.VISIBLE);
            img_prodetail_shoppinglist.setVisibility(View.GONE);
            if(WishList.size() != 0) {

                String st = WishList.toString();
                Log.e("111111111", st);
                if(st.contains(pid)){
                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist_boss);
                    img_prodetail_shoppinglist.setTag("b");
                }else{
                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
                    img_prodetail_shoppinglist.setTag("a");
                }


            }else{
                img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
                img_prodetail_shoppinglist.setTag("a");
            }

            for (int i = 0; i < user_data.size(); i++) {

                user_id_main =user_data.get(i).getUser_id().toString();

            }

        }else{

            img_prodetail_wishlist.setVisibility(View.GONE);
            img_prodetail_shoppinglist.setVisibility(View.GONE);
        }*/

        /*img_prodetail_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Log.e("wishlist pro",""+pro_id+"---"+proW_id+"----"+WishList.toString());
                Log.e("wishlist con", "" + WishList.contains(pid));
                if (WishList.size() != 0) {
                    if (WishList.contains(pid)) {


                        img_prodetail_wishlist.setTag("0");
                        new delete_wish_list().execute();

                    } else {

                        img_prodetail_wishlist.setTag("1");
                        new set_wish_list().execute();
                    }
                } else if (WishList.size() == 0) {
                    img_prodetail_wishlist.setTag("1");
                    new set_wish_list().execute();

                }


                 *//*   Log.e("WishList",""+result_holder.img_wish.getTag().toString());
                    if(result_holder.img_wish.getTag().toString().equals("1")){
                      *//**//*  result_holder.img_wish.setImageResource(R.drawable.whishlist);
                        *//**//*

                       result_holder.img_wish.setTag("0");
                        new delete_wish_list().execute();

                    }else if(result_holder.img_wish.getTag().toString().equals("0")) {
                       *//**//* result_holder.img_wish.setImageResource(R.drawable.whishlist_fill);
                       *//**//*
                        result_holder.img_wish.setTag("1");
                        new set_wish_list().execute();
                    }*//*

            }
        });*/

    }


    private void setLayout(ArrayList<String> str1) {
        // TODO Auto-generated method stub

        img_slider.removeAllViews();
        pagerAdapter=new com.nivida.bossb2b.CustomPagerAdapter(getApplicationContext(),str1,this,true);
        img_slider.setAdapter(pagerAdapter);
        indicator.setViewPager(img_slider);

        img_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selected_image_position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        layout_imagegrid.setVisibility(View.GONE);
        layout_imagegrid.removeAllViews();
        Log.e("1", str1.size() + "");

        for (int ij = 0; ij < str1.size(); ij++) {

            Log.e("2", ij + "");
            LinearLayout.LayoutParams par12 = new LinearLayout.LayoutParams(150, 150);
            LinearLayout lmain = new LinearLayout(Product_Detail.this);
            lmain.setLayoutParams(par12);
            lmain.setBackgroundResource(R.drawable.background_gradiant);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            lmain.setPadding(1, 2, 1, 2);

            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    150, 150);

            LinearLayout l1 = new LinearLayout(Product_Detail.this);
            l1.setLayoutParams(par1);
            l1.setPadding(5, 0, 5, 0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(Product_Detail.this);
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 0, 5, 0);

            final LinearLayout l2 = new LinearLayout(Product_Detail.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 0, 5, 0);
            l2.setVisibility(View.GONE);
            final ImageView img_a = new ImageView(Product_Detail.this);
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
            par21.setMargins(5, 5, 5, 5);
            Picasso.with(getApplicationContext())
                    .load(GlobalVariable.link + "files/" + str1.get(ij))
                    .placeholder(R.drawable.boss_logo_final)
                    .error(R.drawable.boss_logo_final)
                    .into(img_a);
            //imageloader.DisplayImage(GlobalVariable.link+"files/"+str1.get(ij), R.drawable.boss_logo_final, img_a);

            img_a.setId(ij);
            img_a.setTag(GlobalVariable.link + "files/" + str1.get(ij));

            img_a.setPadding(5, 0, 5, 0);

            img_a.setLayoutParams(par21);
          /*  if (ij == 0) {

                img_a.setImageResource(str1.get().getPro_Images());
                img_a.setId(ij);
                img_a.setTag(ij);

                img_a.setPadding(5, 0, 5, 0);

                img_a.setLayoutParams(par21);
            } else if (ij == 1) {
                img_a.setId(ij);
                img_a.setImageResource(R.drawable.boss2);
                img_a.setTag(ij);
                img_a.setPadding(5, 5, 5, 5);

                img_a.setLayoutParams(par21);
            }*//* else if (ij == 2) {
                img_a.setId(ij);
                img_a.setImageResource(R.drawable.boss3);
                img_a.setTag(ij);
                img_a.setPadding(5, 0, 5, 0);

                img_a.setLayoutParams(par21);
            } else if (ij == 3) {
                img_a.setId(ij);
                img_a.setImageResource(R.drawable.boss4);
                img_a.setTag(ij);
                img_a.setPadding(5, 0, 5, 0);

                img_a.setLayoutParams(par21);
   }*/
            final int Ij = ij;
            img_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("IMAGETAG", "" + img_a.getTag().toString());
                    Picasso.with(getApplicationContext())
                            .load(img_a.getTag().toString())
                            .placeholder(R.drawable.boss_logo_final)
                            .error(R.drawable.boss_logo_final)
                            .into(img_full);
                    //imageloader.DisplayImage(img_a.getTag().toString(), R.drawable.boss_logo_final, img_full);
                    selected_image_position = Ij;

                }
            });
            lmain.addView(img_a);
            layout_imagegrid.addView(lmain);


        }
    }

    private void setActionBar() {

        /*// TODO Auto-generated method stub
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();

        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);

        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        image_drawer.setImageResource(R.drawable.back_min);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);

        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(Product_Detail.this);
        bean_cart.clear();
        bean_cart = db.Get_Product_Cart();
        db.close();
        Log.e("SIZE", "" + bean_cart.size());
        if (bean_cart.size() == 0) {
            txt.setVisibility(View.GONE);
            txt.setText("");
        } else {
            txt.setText(bean_cart.size() + "");
            txt.setVisibility(View.VISIBLE);
        }
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(Product_Detail.this);
                db.Delete_ALL_table();
                db.close();
                Intent i = new Intent(Product_Detail.this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });


        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(Product_Detail.this);
                app.setwishid("");
                db = new DatabaseHandler(Product_Detail.this);
                db.Delete_ALL_table();
                db.close();
                if (app.getdetail_response().equalsIgnoreCase("product")) {
                    Intent i = new Intent(Product_Detail.this, InvoiceActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                } else if (app.getdetail_response().equalsIgnoreCase("home")) {
                    Intent i = new Intent(Product_Detail.this, HomeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                } else if (app.getdetail_response().equalsIgnoreCase("wish")) {
                    Intent i = new Intent(Product_Detail.this, Btl_WishList.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                } else if (app.getdetail_response().equalsIgnoreCase("search")) {
                    Intent i = new Intent(Product_Detail.this, Search.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            }
        });


        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(Product_Detail.this);
                db.Delete_ALL_table();
                db.close();
                Intent i = new Intent(Product_Detail.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(Product_Detail.this);
                db.Delete_ALL_table();
                db.close();
                Intent i = new Intent(Product_Detail.this, BTL_Cart.class);
                app.setUser_notification("product_detail");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }*/
    }

    public void onBackPressed() {
        app = new AppPrefs(Product_Detail.this);
        app.setwishid("");
        db = new DatabaseHandler(Product_Detail.this);
        db.Delete_ALL_table();
        db.close();
        /*Intent i = new Intent(Product_Detail.this, InvoiceActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
*/
        super.onBackPressed();
    }

    ;

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }



    /*public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {



            switch(position)
            {
                case 0:
                    return new Features();
                case 1:
                    return new Technical_Specifications();
                case 2:
                    return new Standard_Specifications();
                case 3:
                    return new More_Information();
            }

            return null;
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Features";
                case 1:
                    return "Technical Specifications";
                case 2:
                    return "Standard Specifications";
                case 4:
                    return "More Information";

            }
            return null;
        }
    }*/

    /*public class SlidingImage_Adapter_pDetail extends PagerAdapter {


        private ArrayList<String> IMAGES;
        private LayoutInflater inflater;

        private Context context;


        public SlidingImage_Adapter_pDetail(Context context, ArrayList<String> bean_productImages) {
            this.context = context;
            this.IMAGES = bean_productImages;

            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return ImagesArray.size();

        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.slidingimages_layout_pdetail, view, false);

            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);

            try {

                imageloader.DisplayImage(ImagesArray.get(position), R.drawable.boss_blender_min, imageView);
                Log.e("", "" + ImagesArray.get(position));
            } catch (Exception e) {
                Log.e("", "" + ImagesArray.get(position));
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent i = new Intent(Product_Detail.this, Event_Gallery_photo_view_Activity.class);
                    i.putExtra("position", position);
                    Log.e("pos selected", "" + position);
                    startActivity(i);
                }
            });


            view.addView(imageLayout, position);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


    }*/

    public void SetRefershDataProduct() {
        // TODO Auto-generated method stub
        bean_product1.clear();
        db = new DatabaseHandler(Product_Detail.this);

        ArrayList<Bean_Product> user_array_from_db = db.Get_Product();


        for (int i = 0; i < user_array_from_db.size(); i++) {

            int Pro_id = user_array_from_db.get(i).getId();

            String KEY_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_PRO_CODE = user_array_from_db.get(i).getPro_code();
            String KEY_PRO_NAME = user_array_from_db.get(i).getPro_name();
            String KEY_PRO_LABEL = user_array_from_db.get(i).getPro_label();
            String KEY_PRO_MRP = user_array_from_db.get(i).getPro_mrp();
            String KEY_PRO_SELLINGPRICE = user_array_from_db.get(i).getPro_sellingprice();
            String KEY_PRO_SHORTDESC = user_array_from_db.get(i).getPro_shortdesc();
            String KEY_PRO_MOREINFO = user_array_from_db.get(i).getPro_moreinfo();
            String KEY_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_PRO_QTY = user_array_from_db.get(i).getPro_qty();
            String KEY_PRO_IMAGE = user_array_from_db.get(i).getPro_image();


            Bean_Product contact = new Bean_Product();

            contact.setId(Pro_id);
            contact.setPro_id(KEY_PRO_ID);
            contact.setPro_code(KEY_PRO_CODE);
            contact.setPro_name(KEY_PRO_NAME);
            contact.setPro_label(KEY_PRO_LABEL);
            contact.setPro_mrp(KEY_PRO_MRP);
            contact.setPro_sellingprice(KEY_PRO_SELLINGPRICE);
            contact.setPro_shortdesc(KEY_PRO_SHORTDESC);
            contact.setPro_moreinfo(KEY_PRO_MOREINFO);
            contact.setPro_cat_id(KEY_PRO_CAT_ID);
            contact.setPro_qty(KEY_PRO_QTY);
            contact.setPro_image(KEY_PRO_IMAGE);


            bean_product1.add(contact);


        }
        db.close();
    }

    private void setRefershDataCart() {
        // TODO Auto-generated method stub
        bean_productCart.clear();
        db = new DatabaseHandler(Product_Detail.this);

        ArrayList<Bean_ProductCart> user_array_from_db = db.Get_Product_Cart();


        for (int i = 0; i < user_array_from_db.size(); i++) {

            int cart_id = user_array_from_db.get(i).getId();
            String KEY_CART_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_CART_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_CART_PRO_IMAGES = user_array_from_db.get(i).getPro_Images();
            String KEY_CART_PRO_CODE = user_array_from_db.get(i).getPro_code();
            String KEY_CART_PRO_NAME = user_array_from_db.get(i).getPro_name();
            String KEY_CART_PRO_QTY = user_array_from_db.get(i).getPro_qty();
            String KEY_CART_PRO_MRP = user_array_from_db.get(i).getPro_mrp();
            String KEY_CART_PRO_SELLING_PRICE = user_array_from_db.get(i).getPro_sellingprice();
            String KEY_CART_PRO_SHORTDESC = user_array_from_db.get(i).getPro_shortdesc();
            String KEY_CART_PRO_OPTIONID = user_array_from_db.get(i).getPro_Option_id();
            String KEY_CART_PRO_OPTION_NAME = user_array_from_db.get(i).getPro_Option_name();
            String KEY_CART_PRO_OPTION_VALUEID = user_array_from_db.get(i).getPro_Option_value_id();
            String KEY_CART_PRO_OPTION_VALUENAME = user_array_from_db.get(i).getPro_Option_value_name();
            String KEY_CART_PRO_TOTAL = user_array_from_db.get(i).getPro_total();

            Bean_ProductCart contact = new Bean_ProductCart();
            contact.setId(cart_id);
            contact.setPro_id(KEY_CART_PRO_ID);
            contact.setPro_cat_id(KEY_CART_PRO_CAT_ID);
            contact.setPro_Images(KEY_CART_PRO_IMAGES);
            contact.setPro_code(KEY_CART_PRO_CODE);
            contact.setPro_name(KEY_CART_PRO_NAME);
            contact.setPro_qty(KEY_CART_PRO_QTY);
            contact.setPro_mrp(KEY_CART_PRO_MRP);
            contact.setPro_sellingprice(KEY_CART_PRO_SELLING_PRICE);
            contact.setPro_shortdesc(KEY_CART_PRO_SHORTDESC);
            contact.setPro_Option_id(KEY_CART_PRO_OPTIONID);
            contact.setPro_Option_name(KEY_CART_PRO_OPTION_NAME);
            contact.setPro_Option_value_id(KEY_CART_PRO_OPTION_VALUEID);
            contact.setPro_Option_value_name(KEY_CART_PRO_OPTION_VALUENAME);
            contact.setPro_total(KEY_CART_PRO_TOTAL);

            bean_productCart.add(contact);


        }
        db.close();
    }

    private void setRefershDataFeture() {
        // TODO Auto-generated method stub
        bean_productFeatures.clear();
        db = new DatabaseHandler(Product_Detail.this);

        ArrayList<Bean_ProductFeature> user_array_from_db = db.Get_ProductFeature();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int feature_id = user_array_from_db.get(i).getId();
            String KEY_FEATURE_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_FEATURE_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_FEATURE_PRO_FETURE_ID = user_array_from_db.get(i).getPro_feature_id();
            String KEY_FEATURE_NAME = user_array_from_db.get(i).getPro_feature_name();
            String KEY_FEATURE_VALUE = user_array_from_db.get(i).getPro_feature_value();

            Bean_ProductFeature contact = new Bean_ProductFeature();
            contact.setId(feature_id);
            contact.setPro_id(KEY_FEATURE_PRO_ID);
            contact.setPro_cat_id(KEY_FEATURE_PRO_CAT_ID);
            contact.setPro_feature_id(KEY_FEATURE_PRO_FETURE_ID);
            contact.setPro_feature_name(KEY_FEATURE_NAME);
            contact.setPro_feature_value(KEY_FEATURE_VALUE);

            bean_productFeatures.add(contact);


        }
        db.close();
    }

    private void setRefershDataImage() {
        // TODO Auto-generated method stub
        bean_productImages.clear();
        db = new DatabaseHandler(Product_Detail.this);

        ArrayList<Bean_ProductImage> user_array_from_db = db.Get_ProductImage();

        bean_productImages.clear();

        for (int i = 0; i < user_array_from_db.size(); i++) {


            int image_id = user_array_from_db.get(i).getId();
            String KEY_IMAGE_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_IMAGE_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_IMAGE_PRO_IMAGES = user_array_from_db.get(i).getPro_Images();


            Bean_ProductImage contact = new Bean_ProductImage();
            contact.setId(image_id);
            contact.setPro_id(KEY_IMAGE_PRO_ID);
            contact.setPro_cat_id(KEY_IMAGE_PRO_CAT_ID);
            contact.setPro_Images(KEY_IMAGE_PRO_IMAGES);


            bean_productImages.add(contact);
        }
        db.close();
    }

    private void setRefershDataOption() {
        // TODO Auto-generated method stub
        bean_productOprtions.clear();
        db = new DatabaseHandler(Product_Detail.this);

        ArrayList<Bean_ProductOprtion> user_array_from_db = db.Get_Product_Option();

        Log.e("23232323", "" + user_array_from_db.size());

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int option_id = user_array_from_db.get(i).getId();
            String KEY_OPTION_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_OPTION_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_OPTION_PRO_OPTION_ID = user_array_from_db.get(i).getPro_Option_id();
            String KEY_OPTION_NAME = user_array_from_db.get(i).getPro_Option_name();
            String KEY_OPTION_VALUE_ID = user_array_from_db.get(i).getPro_Option_value_id();
            String KEY_OPTION_VALUENAME = user_array_from_db.get(i).getPro_Option_value_name();
            String KEY_OPTION_MRP = user_array_from_db.get(i).getPro_Option_mrp();
            String KEY_OPTION_SELLINGPRICE = user_array_from_db.get(i).getPro_Option_selling_price();
            String KEY_OPTION_PROIMAGE = user_array_from_db.get(i).getPro_Option_proimage();

            Log.e("4545454545", "" + KEY_OPTION_PROIMAGE);

            String KEY_OPTION_PROCODE = user_array_from_db.get(i).getPro_Option_procode();


            Bean_ProductOprtion contact = new Bean_ProductOprtion();

            contact.setId(option_id);
            contact.setPro_id(KEY_OPTION_PRO_ID);
            contact.setPro_cat_id(KEY_OPTION_PRO_CAT_ID);
            contact.setPro_Option_id(KEY_OPTION_PRO_OPTION_ID);
            contact.setPro_Option_name(KEY_OPTION_NAME);
            contact.setPro_Option_value_id(KEY_OPTION_VALUE_ID);
            contact.setPro_Option_value_name(KEY_OPTION_VALUENAME);
            contact.setPro_Option_mrp(KEY_OPTION_MRP);
            contact.setPro_Option_selling_price(KEY_OPTION_SELLINGPRICE);
            contact.setPro_Option_proimage(KEY_OPTION_PROIMAGE);
            contact.setPro_Option_procode(KEY_OPTION_PROCODE);


            bean_productOprtions.add(contact);


        }
        db.close();
    }

    private void setRefershDataStdspec() {
        // TODO Auto-generated method stub
        bean_productStdSpecs.clear();
        db = new DatabaseHandler(Product_Detail.this);

        ArrayList<Bean_ProductStdSpec> user_array_from_db = db.Get_Productstdspec();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {


            int stdspec_id = user_array_from_db.get(i).getId();
            String KEY_STDSPEC_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_STDSPEC_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_STDSPEC_STDSPEC_ID = user_array_from_db.get(i).getPro_Stdspec_id();
            String KEY_STDSPEC_NAME = user_array_from_db.get(i).getPro_Stdspec_name();
            String KEY_STDSPEC_VALUE = user_array_from_db.get(i).getPro_Stdspec_value();

            Bean_ProductStdSpec contact = new Bean_ProductStdSpec();

            contact.setId(stdspec_id);
            contact.setPro_id(KEY_STDSPEC_PRO_ID);
            contact.setPro_cat_id(KEY_STDSPEC_PRO_CAT_ID);
            contact.setPro_Stdspec_id(KEY_STDSPEC_STDSPEC_ID);
            contact.setPro_Stdspec_name(KEY_STDSPEC_NAME);
            contact.setPro_Stdspec_value(KEY_STDSPEC_VALUE);

            bean_productStdSpecs.add(contact);


        }
        db.close();
    }

    private void setRefershDataYouTube() {
        // TODO Auto-generated method stub
        bean_productyoutube.clear();
        db = new DatabaseHandler(Product_Detail.this);

        ArrayList<Product_Youtube> user_array_from_db = db.Get_ProductYoutube();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {


            int youtube_id = user_array_from_db.get(i).getId();
            String KEY_YOUTUBE_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_YOUTUBE_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_YOUTUBE_URL = user_array_from_db.get(i).getPro_youtube_url();


            Product_Youtube contact = new Product_Youtube();

            contact.setId(youtube_id);
            contact.setPro_id(KEY_YOUTUBE_PRO_ID);
            contact.setPro_cat_id(KEY_YOUTUBE_PRO_CAT_ID);
            contact.setPro_youtube_url(KEY_YOUTUBE_URL);


            bean_productyoutube.add(contact);


        }
        db.close();
    }

    private void setRefershDataTechDesc() {
        // TODO Auto-generated method stub
        bean_productTechSpecs.clear();
        db = new DatabaseHandler(Product_Detail.this);

        ArrayList<Bean_ProductTechSpec> user_array_from_db = db.Get_Producttechspec();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {


            int techdesc_id = user_array_from_db.get(i).getId();
            String KEY_TECHSPEC_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_TECHSPEC_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_TECHSPEC_TECHSPEC_ID = user_array_from_db.get(i).getPro_Techspec_id();
            String KEY_TECHSPEC_NAME = user_array_from_db.get(i).getPro_Techspec_name();
            String KEY_TECHSPEC_VALUE = user_array_from_db.get(i).getPro_Techspec_value();

            Bean_ProductTechSpec contact = new Bean_ProductTechSpec();

            contact.setId(techdesc_id);
            contact.setPro_id(KEY_TECHSPEC_PRO_ID);
            contact.setPro_cat_id(KEY_TECHSPEC_PRO_CAT_ID);
            contact.setPro_Techspec_id(KEY_TECHSPEC_TECHSPEC_ID);
            contact.setPro_Techspec_name(KEY_TECHSPEC_NAME);
            contact.setPro_Techspec_value(KEY_TECHSPEC_VALUE);

            bean_productTechSpecs.add(contact);
        }
        db.close();
    }

    public class set_marquee extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        Product_Detail.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                json = new ServiceHandler().makeServiceCall(GlobalVariable.link + "FlashMessage/App_GetFlashMessage", ServiceHandler.POST, parameters);

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

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(Product_Detail.this , "SERVER ERRER" , Toast.LENGTH_SHORT);
                    //GlobalVariable.CustomToast(Product_Detail.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(Product_Detail.this , "" +Message , Toast.LENGTH_SHORT);
                        //GlobalVariable.CustomToast(Product_Detail.this, "" + Message, getLayoutInflater());

                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");


                      /*  JSONObject jO = jObj.getJSONObject("data");
                        JSONObject jU = jO.getJSONObject("FlashMessage");


                        String mrquee= jU.getString("msg");
                        product_detail_marquee.setText(mrquee.toString());
                        Log.e("mrquee",""+mrquee.toString());*/


                        JSONArray jmarquee_msg = jObj.getJSONArray("data");

                        for (int i = 0; i < jmarquee_msg.length(); i++) {
                            JSONObject jsonobject = jmarquee_msg.getJSONObject(i);
                            JSONObject jobject_marquee_msg = jsonobject.getJSONObject("FlashMessage");
                            String msg_marquee = jobject_marquee_msg.getString("msg");
                            product_detail_marquee.setText(msg_marquee.toString());


                        }

                        loadingView.dismiss();
txt_productdetail.setVisibility(View.VISIBLE);
                        btn_buy.setVisibility(View.VISIBLE);

                        new get_product().execute();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    /*  private void imageslider() {
          for (int i = 0; i < bean_productImages.size(); i++)
              ImagesArray.add(bean_productImages.get(i).getPro_Images());
          Log.e("Imagearray",""+ImagesArray.size());



          mPager.setAdapter(new SlidingImage_Adapter_pDetail(Product_Detail.this, ImagesArray));


          CirclePageIndicator indicator = (CirclePageIndicator)
                  findViewById(R.id.indicator);

          indicator.setViewPager(mPager);



      }
  */

    public class get_product extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        public get_product() {
            bean_product1.clear();
            bean_productTechSpecs.clear();
            bean_productOprtions.clear();
            bean_productFeatures.clear();
            bean_productStdSpecs.clear();
            bean_productImages.clear();
            bean_Product_Youtube.clear();
            list_of_images.clear();
            db = new DatabaseHandler(Product_Detail.this);
            db.Delete_ALL_table();
            db.close();
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        Product_Detail.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("product_id", pid));


                parameters.add(new BasicNameValuePair("user_id", user_id_main));

                Log.e("pid", "" + pid);

                Log.e("user_id", "" + user_id_main);

                System.out.println("Product category ID :- " + CategoryID);
                json = new ServiceHandler().makeServiceCall(GlobalVariable.link + "Product/App_Get_Product_Details", ServiceHandler.POST, parameters);

                System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {


                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Toast.makeText(Product_Detail.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();
                    //GlobalVariable.CustomToast(Product_Detail.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {

                    Log.e("Json:", "" + result_1);
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(Product_Detail.this, ""+Message,
                                Toast.LENGTH_SHORT).show();

                       // GlobalVariable.CustomToast(Product_Detail.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                       /* page_limit = jObj.getString("total_page_count");
                        Log.e("Page limit",""+page_limit);*/

                        JSONArray jsonwish = jObj.getJSONArray("wish_list");

                        WishList = new ArrayList<String>();
                        bean_product1.clear();

                        for (int i = 0; i < jsonwish.length(); i++) {
                            WishList.add(jsonwish.get(i).toString());
                        }
                        Log.e("wishlistsize", "" + WishList.size());

                       /* if (page_id > Integer.parseInt(page_limit)) {

                        } else {*/

                        JSONObject jobj = new JSONObject(result_1);
                          /*  bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();*/


                       /* bean_product1.clear();
                        bean_productTechSpecs.clear();
                        bean_productOprtions.clear();
                        bean_productFeatures.clear();
                        bean_productStdSpecs.clear();
                        bean_productImages.clear();
                        bean_Product_Youtube.clear();*/
                        if (jobj != null) {


                            JSONObject jsonArray = jObj.getJSONObject("data");


                            JSONObject jproduct = jsonArray.getJSONObject("Product");


                            Bean_Product bean = new Bean_Product();


                            bean.setPro_id(jproduct.getString("id"));

                            Log.e("IDDD", "" + jproduct.getString("id").toString());
                            bean.setPro_cat_id(jproduct.getString("category_id"));
                            bean.setPro_code(jproduct.getString("product_code"));
                            bean.setPro_name(jproduct.getString("product_name"));
                            // bean.setPro_label(jproduct.getString("label_id"));
                            bean.setPro_qty(jproduct.getString("qty"));
                            bean.setPro_mrp(jproduct.getString("mrp"));
                            bean.setPro_sellingprice(jproduct.getString("selling_price"));
                            bean.setPro_shortdesc(jproduct.getString("short_description"));
                            bean.setPro_image(jproduct.getString("image"));
                            bean.setPro_moreinfo(jproduct.getString("more_info"));

                            JSONObject jlabel = jsonArray.getJSONObject("Label");
                            bean.setPro_label(jlabel.getString("name"));

                            bean_product1.add(bean);


                            JSONArray jProductFeature = jsonArray.getJSONArray("ProductFeature");
                            bean_productFeatures.clear();
                            for (int f = 0; f < jProductFeature.length(); f++) {
                                JSONObject jproProductFeature = jProductFeature.getJSONObject(f);
                                JSONObject jsubobject1 = jproProductFeature.getJSONObject("Attribute");

                                Bean_ProductFeature beanf = new Bean_ProductFeature();


                                beanf.setPro_feature_id(jsubobject1.getString("id"));
                                beanf.setPro_id(jproProductFeature.getString("product_id"));
                                beanf.setPro_feature_name(jsubobject1.getString("name"));
                                beanf.setPro_feature_value(jproProductFeature.getString("attribute_value"));


                                //beanf.setPro_name(jsonobject.getString("product_name"));


                                bean_productFeatures.add(beanf);

                            }

                            JSONArray jProductOption = jsonArray.getJSONArray("ProductOption");
                            bean_productOprtions.clear();
                            list_of_images.clear();
                            if (jProductOption.length() == 0) {
                                list_of_images.add(jproduct.getString("image"));

                            } else {
                                list_of_images.clear();
                                list_of_images.add(jproduct.getString("image"));
                                for (int s = 0; s < jProductOption.length(); s++) {
                                    JSONObject jProductOptiono = jProductOption.getJSONObject(s);
                                    JSONObject jPOOption = jProductOptiono.getJSONObject("Option");
                                    JSONObject jPOOptionValue = jProductOptiono.getJSONObject("OptionValue");
                                    JSONArray jPOProductOptionImage = jProductOptiono.getJSONArray("ProductOptionImage");


                                    Bean_ProductOprtion beanoption = new Bean_ProductOprtion();
                                    // arrOptionType=new ArrayList<String>();


                                    //Option
                                    beanoption.setPro_Option_id(jPOOption.getString("id"));
                                    beanoption.setPro_Option_name(jPOOption.getString("name"));

                                    //OptionValue
                                    beanoption.setPro_Option_value_name(jPOOptionValue.getString("name"));
                                    beanoption.setPro_Option_value_id(jPOOptionValue.getString("id"));

                                    //ProductOptionImage

                                    Log.e("ABABABABBABBA", "" + jProductOptiono.getString("product_id"));
                                    beanoption.setPro_id(jProductOptiono.getString("product_id"));
                                    beanoption.setPro_Option_mrp(jProductOptiono.getString("mrp"));
                                    beanoption.setPro_Option_selling_price(jProductOptiono.getString("selling_price"));

                                    list_of_images.clear();
                                    if (jPOProductOptionImage.length() == 0) {
                                        beanoption.setPro_Option_proimage("");
                                        list_of_images.add("");
                                    } else {

                                        for (int t = 0; t < jPOProductOptionImage.length(); t++) {
                                            JSONObject jProductOptio = jPOProductOptionImage.getJSONObject(t);
                                            beanoption.setPro_Option_proimage(jProductOptio.getString("image"));
                                            list_of_images.add(jProductOptio.getString("image"));
                                        }

                                        Log.e("sizeimages", "" + list_of_images.size());
                                        // GlobalVariable.CustomToast(Product_List.this, "" + list_of_images.size(), getLayoutInflater());
                                    }
                                    bean_productOprtions.add(beanoption);
                                    //   arrOptionType.add(jPOOption.getString("id"));

                                    Log.e("list of img", list_of_images.size() + "");


                                }
                            }
                               /* LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                                arrOptionType.clear();
                                //Creating Arraylist without duplicate values
                                arrOptionType = new ArrayList<String>(listToSet);

                                Log.e("AAAA", "" + arrOptionType.size());

                                arrOptionSize.add(""+arrOptionType);*/

                            JSONArray jprostdspec = jsonArray.getJSONArray("ProductStandardSpecification");
                            bean_productStdSpecs.clear();
                            for (int s = 0; s < jprostdspec.length(); s++) {
                                JSONObject jpss = jprostdspec.getJSONObject(s);
                                JSONObject jpssAttribute = jpss.getJSONObject("Attribute");

                                Bean_ProductStdSpec beanstudspec = new Bean_ProductStdSpec();

//attribute
                                beanstudspec.setPro_Stdspec_id(jpssAttribute.getString("id"));
                                beanstudspec.setPro_Stdspec_name(jpssAttribute.getString("name"));

                                beanstudspec.setPro_id(jpss.getString("product_id"));
                                beanstudspec.setPro_Stdspec_value(jpss.getString("attribute_value"));

                                bean_productStdSpecs.add(beanstudspec);

                            }
                            JSONArray jprotechs = jsonArray.getJSONArray("ProductTechnicalSpecification");
                            bean_productTechSpecs.clear();
                            for (int s = 0; s < jprotechs.length(); s++) {
                                JSONObject jpts = jprotechs.getJSONObject(s);
                                JSONObject jpssAttribute = jpts.getJSONObject("Attribute");


                                Bean_ProductTechSpec beantechspec = new Bean_ProductTechSpec();

                                beantechspec.setPro_id(jpts.getString("product_id"));
                                beantechspec.setPro_Techspec_value(jpts.getString("attribute_value"));

                                beantechspec.setPro_Techspec_id(jpssAttribute.getString("id"));
                                beantechspec.setPro_Techspec_name(jpssAttribute.getString("name"));


                                bean_productTechSpecs.add(beantechspec);

                            }
                            //setRefershDataImage();

                            JSONArray jpProductImage = jsonArray.getJSONArray("ProductImage");
                            bean_productImages.clear();

                            Bean_ProductImage beanproductimg1 = new Bean_ProductImage();

                            beanproductimg1.setPro_id(jproduct.getString("id"));
                            beanproductimg1.setPro_Images(jproduct.getString("image"));

                            bean_productImages.add(beanproductimg1);
                            for (int s = 0; s < jpProductImage.length(); s++) {
                                JSONObject jpimg = jpProductImage.getJSONObject(s);


                                Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                beanproductimg.setPro_id(jpimg.getString("product_id"));
                                beanproductimg.setPro_Images(jpimg.getString("image"));

                                bean_productImages.add(beanproductimg);

                            }

                            JSONArray jpProductCatalog = jsonArray.getJSONArray("ProductCatalog");
                            bean_ProductCatalog.clear();
                            for (int ct = 0; ct < jpProductCatalog.length(); ct++) {
                                JSONObject ipcatalog = jpProductCatalog.getJSONObject(ct);


                                Bean_ProductCatalog bean_productCatalog1 = new Bean_ProductCatalog();

                                bean_productCatalog1.setLabel(ipcatalog.getString("label"));
                                bean_productCatalog1.setFile_path(ipcatalog.getString("file_path"));
                                bean_productCatalog1.setPro_id(ipcatalog.getString("product_id"));

                                bean_ProductCatalog.add(bean_productCatalog1);

                            }
                            JSONArray jyoutube = jsonArray.getJSONArray("ProductVideo");
                            bean_Product_Youtube.clear();
                            for (int s1 = 0; s1 < jyoutube.length(); s1++) {
                                JSONObject jyou = jyoutube.getJSONObject(s1);


                                Product_Youtube beanProyoutube = new Product_Youtube();

                                beanProyoutube.setPro_id(jyou.getString("product_id"));
                                beanProyoutube.setPro_youtube_url(jyou.getString("url"));

                                bean_Product_Youtube.add(beanProyoutube);

                            }


                            Log.e("Size:Product ::", "" + bean_product1.size());
                            Log.e("Size:TechSpecification", "" + bean_productTechSpecs.size());
                            Log.e("Size:ProductOptions ::", "" + bean_productOprtions.size());
                            Log.e("Size:ProductFeatures ::", "" + bean_productFeatures.size());
                            Log.e("Size:ProductIamge ::", "" + bean_productImages.size());
                            Log.e("Size:youtubesize ::", "" + bean_Product_Youtube.size());


                        }

                     /*   bean_productImages.clear();
                        db= new DatabaseHandler(Product_Detail.this);
                        db.Delete_ALL_table();
                        db.close();
                        app= new AppPrefs(Product_Detail.this);
                        app.setwishid("");

                        SetRefershDataProduct();


                        Log.e("bean_product1_before", "" + bean_product_db.size());

                        try {
                            if (bean_product_db.size() == 0) {

                                for (int a = 0; a < bean_product1.size(); a++) {
                                    Log.e("123", "" + bean_product1.size());
                                    db = new DatabaseHandler(Product_Detail.this);
                                    db.Add_Product(new Bean_Product(bean_product1.get(a).getPro_id(),
                                            bean_product1.get(a).getPro_code(),
                                            bean_product1.get(a).getPro_name(),
                                            bean_product1.get(a).getPro_label(),
                                            bean_product1.get(a).getPro_mrp(),
                                            bean_product1.get(a).getPro_sellingprice(),
                                            bean_product1.get(a).getPro_shortdesc(),
                                            bean_product1.get(a).getPro_moreinfo(),
                                            bean_product1.get(a).getPro_cat_id(),
                                            bean_product1.get(a).getPro_qty(),
                                            bean_product1.get(a).getPro_image()));
                                    db.close();


                                }

                            }
                            else{

                                Log.e("Datbase....22", "" + bean_product1.size());
                                for (int a = 0; a < bean_product1.size(); a++) {
                                    Log.e("List....22", "" + bean_product1.size());
                                    int k = 0;
                                    for (int b = 0; b < bean_product_db.size(); b++) {
                                        if (k != 1) {
                                            if (bean_product_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_product1.get(a).getPro_id().toString())) {

                                            } else {
                                                db = new DatabaseHandler(Product_Detail.this);
                                                db.Add_Product(new Bean_Product(bean_product1.get(a).getPro_id(),
                                                        bean_product1.get(a).getPro_code(),
                                                        bean_product1.get(a).getPro_name(),
                                                        bean_product1.get(a).getPro_label(),
                                                        bean_product1.get(a).getPro_mrp(),
                                                        bean_product1.get(a).getPro_sellingprice(),
                                                        bean_product1.get(a).getPro_shortdesc(),
                                                        bean_product1.get(a).getPro_moreinfo(),
                                                        bean_product1.get(a).getPro_cat_id(),
                                                        bean_product1.get(a).getPro_qty(),
                                                        bean_product1.get(a).getPro_image()));
                                                db.close();

                                                k = 1;
                                            }
                                        }
                                    }
                                }

                            }
                        } catch (Exception e) {
                            Log.e("Error", "" + e.toString());
                        }
                        SetRefershDataProduct();
                        Log.e("bean_product1", "" + bean_product1.size());

                        //   setRefershDataImage(); */

                        Log.e("bean_pro_image_before", "" + bean_productImages.size());

                        try {
                            if (bean_productImages_db.size() == 0) {
                                for (int a = 0; a < bean_productImages.size(); a++) {

                                    db = new DatabaseHandler(Product_Detail.this);
                                    db.Add_ProductImage(new Bean_ProductImage(bean_productImages.get(a).getPro_id(),
                                            bean_productImages.get(a).getPro_cat_id(),
                                            bean_productImages.get(a).getPro_Images())
                                    );
                                    db.close();
                                }
                                Log.e("bean_pro_image_after", "" + bean_productImages_db.size());
                            } else {

                                Log.e("Datbase....22", "" + bean_productImages.size());
                                for (int a = 0; a < bean_productImages.size(); a++) {
                                    Log.e("List....22", "" + bean_productImages.size());
                                    int k = 0;
                                    for (int b = 0; b < bean_productImages_db.size(); b++) {
                                        if (k != 1) {
                                            if (bean_productImages_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_productImages.get(a).getPro_id().toString())) {

                                            } else {
                                                db = new DatabaseHandler(Product_Detail.this);
                                                db.Add_ProductImage(new Bean_ProductImage(bean_productImages.get(a).getPro_id(),
                                                        bean_productImages.get(a).getPro_cat_id(),
                                                        bean_productImages.get(a).getPro_Images())
                                                );
                                                db.close();

                                                k = 1;
                                            }
                                        }
                                    }
                                }

                            }
                        } catch (Exception e) {
                            Log.e("Exception_inimage", "" + e.toString());
                        }

                        setRefershDataImage();
                        Log.e("bean_productImages", "" + bean_productImages_db.size());
                        setRefershDataYouTube();

                        Log.e("bean_pro_youtube_before", "" + bean_productYoutube_db.size());

                        try {
                            if (bean_productYoutube_db.size() == 0) {
                                Log.e("bean_Product_Youtube", "" + bean_Product_Youtube.size());
                                for (int a = 0; a < bean_Product_Youtube.size(); a++) {

                                    db = new DatabaseHandler(Product_Detail.this);
                                    db.Add_ProductYoutube(new Product_Youtube(bean_Product_Youtube.get(a).getPro_id(),
                                            bean_Product_Youtube.get(a).getPro_cat_id(),
                                            bean_Product_Youtube.get(a).getPro_youtube_url())
                                    );
                                    db.close();
                                }
                                Log.e("bean_pro_youtube_after", "" + bean_productYoutube_db.size());
                            } else {

                                Log.e("Datbase....22", "" + bean_Product_Youtube.size());
                                for (int a = 0; a < bean_Product_Youtube.size(); a++) {
                                    Log.e("List....22", "" + bean_Product_Youtube.size());
                                    int k = 0;
                                    for (int b = 0; b < bean_productImages_db.size(); b++) {
                                        if (k != 1) {
                                            if (bean_productYoutube_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_Product_Youtube.get(a).getPro_id().toString())) {

                                            } else {
                                                db = new DatabaseHandler(Product_Detail.this);
                                                db.Add_ProductYoutube(new Product_Youtube(bean_Product_Youtube.get(a).getPro_id(),
                                                        bean_Product_Youtube.get(a).getPro_cat_id(),
                                                        bean_Product_Youtube.get(a).getPro_youtube_url())
                                                );
                                                db.close();

                                                k = 1;
                                            }
                                        }
                                    }
                                }

                            }
                        } catch (Exception e) {
                            Log.e("Exception_youtube", "" + e.toString());
                        }
                        Log.e("bean_pro_feature_before", "" + bean_productFeatures_db.size());
/*
                        try {

                            if (bean_productFeatures_db.size() == 0) {
                                Log.e("bean_productFeatu_size", "" + bean_productFeatures.size());
                                for (int a = 0; a < bean_productFeatures.size(); a++) {
                                    db = new DatabaseHandler(Product_Detail.this);
                                    db.Add_ProductFeature(new Bean_ProductFeature(bean_productFeatures.get(a).getPro_id(),
                                            bean_productFeatures.get(a).getPro_cat_id(),
                                            bean_productFeatures.get(a).getPro_feature_id(),
                                            bean_productFeatures.get(a).getPro_feature_name(),
                                            bean_productFeatures.get(a).getPro_feature_value()));


                                    db.close();
                                }
                                Log.e("bean_pro_feature_after", "" + bean_productFeatures_db.size());

                            }

                            else{

                                Log.e("Datbase....22", "" + bean_productFeatures.size());
                                for (int a = 0; a < bean_productFeatures.size(); a++) {
                                    Log.e("List....22", "" + bean_productFeatures.size());
                                    int k = 0;
                                    for (int b = 0; b < bean_productFeatures_db.size(); b++) {
                                        if (k != 1) {
                                            if (bean_productFeatures_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_productFeatures.get(a).getPro_id().toString())) {

                                            } else {
                                                db = new DatabaseHandler(Product_Detail.this);
                                                db.Add_ProductFeature(new Bean_ProductFeature(bean_productFeatures.get(a).getPro_id(),
                                                        bean_productFeatures.get(a).getPro_cat_id(),
                                                        bean_productFeatures.get(a).getPro_feature_id(),
                                                        bean_productFeatures.get(a).getPro_feature_name(),
                                                        bean_productFeatures.get(a).getPro_feature_value()));


                                                db.close();

                                                k = 1;
                                            }
                                        }
                                    }
                                }

                            }
                        } catch (Exception e) {
                            Log.e("Exception_pro_feacture", "" + e.toString());
                        }
                        setRefershDataFeture();
                        Log.e("bean_pro_feature_after", "" + bean_productFeatures_db.size());

                        Log.e("bean_pro_studsp_before", "" + bean_productStdSpecs_db.size());

                        try {
                            if (bean_productStdSpecs_db.size() == 0) {
                                for (int a = 0; a < bean_productStdSpecs.size(); a++) {
                                    db = new DatabaseHandler(Product_Detail.this);
                                    db.Add_ProductStdspec(new Bean_ProductStdSpec(bean_productStdSpecs.get(a).getPro_id(),
                                            bean_productStdSpecs.get(a).getPro_cat_id(),
                                            bean_productStdSpecs.get(a).getPro_Stdspec_id(),
                                            bean_productStdSpecs.get(a).getPro_Stdspec_name()
                                            , bean_productStdSpecs.get(a).getPro_Stdspec_value()));
                                    db.close();
                                }
                                Log.e("bean_pro_studsp_before", "" + bean_productStdSpecs_db.size());

                            }

                            else{

                                Log.e("Datbase....22", "" + bean_productStdSpecs.size());
                                for (int a = 0; a < bean_productStdSpecs.size(); a++) {
                                    Log.e("List....22", "" + bean_productStdSpecs.size());
                                    int k = 0;
                                    for (int b = 0; b < bean_productStdSpecs_db.size(); b++) {
                                        if (k != 1) {
                                            if (bean_productStdSpecs_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_productStdSpecs.get(a).getPro_id().toString())) {

                                            } else {
                                                db = new DatabaseHandler(Product_Detail.this);
                                                db.Add_ProductStdspec(new Bean_ProductStdSpec(bean_productStdSpecs.get(a).getPro_id(),
                                                        bean_productStdSpecs.get(a).getPro_cat_id(),
                                                        bean_productStdSpecs.get(a).getPro_Stdspec_id(),
                                                        bean_productStdSpecs.get(a).getPro_Stdspec_name()
                                                        , bean_productStdSpecs.get(a).getPro_Stdspec_value()));
                                                db.close();

                                                k = 1;
                                            }
                                        }
                                    }
                                }

                            }
                        } catch (Exception e)

                        {
                            Log.e("Exception_pro_studspec", "" + e.toString());
                        }
                        setRefershDataStdspec();
                        Log.e("setRefershDataStdspec", "" + bean_productStdSpecs_db.size());

                        Log.e("bean_pr_techspec_before", "" + bean_productTechSpecs_db.size());

                        try {
                            if (bean_productTechSpecs_db.size() == 0) {
                                for (int a = 0; a < bean_productTechSpecs.size(); a++) {
                                    db = new DatabaseHandler(Product_Detail.this);
                                    db.Add_ProductTechSpec(new Bean_ProductTechSpec(bean_productTechSpecs.get(a).getPro_id(),
                                            bean_productTechSpecs.get(a).getPro_cat_id(),
                                            bean_productTechSpecs.get(a).getPro_Techspec_id(),
                                            bean_productTechSpecs.get(a).getPro_Techspec_name(),
                                            bean_productTechSpecs.get(a).getPro_Techspec_value()));
                                    db.close();
                                }
                                Log.e("bean_pro_techspec_after", "" + bean_productTechSpecs_db.size());

                            }

                            else{

                                Log.e("Datbase....22", "" + bean_productTechSpecs.size());
                                for (int a = 0; a < bean_productTechSpecs.size(); a++) {
                                    Log.e("List....22", "" + bean_productTechSpecs.size());
                                    int k = 0;
                                    for (int b = 0; b < bean_productTechSpecs_db.size(); b++) {
                                        if (k != 1) {
                                            if (bean_productTechSpecs_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_productTechSpecs.get(a).getPro_id().toString())) {

                                            } else {
                                                db = new DatabaseHandler(Product_Detail.this);
                                                db.Add_ProductTechSpec(new Bean_ProductTechSpec(bean_productTechSpecs.get(a).getPro_id(),
                                                        bean_productTechSpecs.get(a).getPro_cat_id(),
                                                        bean_productTechSpecs.get(a).getPro_Techspec_id(),
                                                        bean_productTechSpecs.get(a).getPro_Techspec_name(),
                                                        bean_productTechSpecs.get(a).getPro_Techspec_value()));
                                                db.close();

                                                k = 1;
                                            }
                                        }
                                    }
                                }

                            }
                        } catch (Exception e) {
                            Log.e("exception_techspec", "" + e.toString());
                        }
                        setRefershDataTechDesc();
                        Log.e("setRefershDataTechDesc", "" + bean_productTechSpecs_db.size());

                        Log.e("bean_pro_options_before", "" + bean_productOprtions_db.size());

                        try {
                            if (bean_productOprtions_db.size() == 0) {
                                for (int a = 0; a < bean_productOprtions.size(); a++) {


                                    db = new DatabaseHandler(Product_Detail.this);
                                    db.Add_Product_Option(new Bean_ProductOprtion(bean_productOprtions.get(a).getPro_id(),
                                            bean_productOprtions.get(a).getPro_cat_id(),
                                            bean_productOprtions.get(a).getPro_Option_id(),
                                            bean_productOprtions.get(a).getPro_Option_name(),
                                            bean_productOprtions.get(a).getPro_Option_value_id(),
                                            bean_productOprtions.get(a).getPro_Option_value_name(),
                                            bean_productOprtions.get(a).getPro_Option_mrp(),
                                            bean_productOprtions.get(a).getPro_Option_selling_price(),
                                            bean_productOprtions.get(a).getPro_Option_proimage(),
                                            bean_productOprtions.get(a).getPro_Option_procode()

                                    ));
                                    db.close();
                                }
                                Log.e("bean_prooption_after", "" + bean_productOprtions_db.size());
                            }


                            else{

                                Log.e("Datbase....22", "" + bean_productOprtions.size());
                                for (int a = 0; a < bean_productOprtions.size(); a++) {
                                    Log.e("List....22", "" + bean_productOprtions.size());
                                    int k = 0;
                                    for (int b = 0; b < bean_productOprtions_db.size(); b++) {
                                        if (k != 1) {
                                            if (bean_productOprtions_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_productOprtions.get(a).getPro_id().toString())) {

                                            } else {
                                                db = new DatabaseHandler(Product_Detail.this);
                                                db.Add_Product_Option(new Bean_ProductOprtion(bean_productOprtions.get(a).getPro_id(),
                                                        bean_productOprtions.get(a).getPro_cat_id(),
                                                        bean_productOprtions.get(a).getPro_Option_id(),
                                                        bean_productOprtions.get(a).getPro_Option_name(),
                                                        bean_productOprtions.get(a).getPro_Option_value_id(),
                                                        bean_productOprtions.get(a).getPro_Option_value_name(),
                                                        bean_productOprtions.get(a).getPro_Option_mrp(),
                                                        bean_productOprtions.get(a).getPro_Option_selling_price(),
                                                        bean_productOprtions.get(a).getPro_Option_proimage(),
                                                        bean_productOprtions.get(a).getPro_Option_procode()

                                                ));
                                                db.close();

                                                k = 1;
                                            }
                                        }
                                    }
                                }

                            }
                        } catch (Exception e) {
                            Log.e("Exception_option", "" + e.toString());

                        }
                        setRefershDataOption();
                        Log.e("setRefershDataOption", "" + bean_productOprtions_db.size());*/

                        /*list_product.setSelection(current_record);
                        if(flagscroll.equalsIgnoreCase("2")) {

                            list_product.setVisibility(View.VISIBLE);

                            list_product.setAdapter(new CustomResultAdapterDoctor());
                        }
                        else if(flagscroll.equalsIgnoreCase("1")) {

                            list_product.setVisibility(View.VISIBLE);

                            list_product.setAdapter(new CustomResultAdapterDoctor1());
                        }*/


                        loadingView.dismiss();
                        db = new DatabaseHandler(Product_Detail.this);
                        bean_cart.clear();
                        bean_cart = db.Get_Product_Cart();
                        db.close();
                        /*if (user_data.size() != 0) {
                            img_prodetail_wishlist.setVisibility(View.VISIBLE);
                        } else {
                            img_prodetail_wishlist.setVisibility(View.GONE);
                        }*/
                        Log.e("SIZE", "" + bean_cart.size());
                        if (bean_cart.size() == 0) {
                            //txt.setVisibility(View.GONE);
                            //txt.setText("");
                        } else {
                            //txt.setText(bean_cart.size() + "");
                            //txt.setVisibility(View.VISIBLE);
                        }
                        set_data();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
                Log.e("jsonerror", "" + j.toString());
            }

        }
    }

    private void set_data() {

        /*if (user_data.size() != 0) {

            if (WishList.size() != 0) {

                String st = WishList.toString();
                Log.e("12122", st);
                Log.e("pro_id", "" + pid);
                if (st.contains(pid)) {
                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist_boss);
                   *//* result_holder.img_wish.setTag("1");
                    result_holder.img_shopping.setTag("b");
                    Log.e("1111", "111"+pro_id);*//*
                } else {
                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
                   *//* result_holder.img_wish.setTag("0");
                    result_holder.img_shopping.setTag("a");
                    Log.e("0000","000"+pro_id);*//*
                }


            } else if (WishList.size() == 0) {
                img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
               *//* result_holder.img_wish.setTag("0");
                result_holder.img_shopping.setTag("a");
                Log.e("0000", "000"+pro_id);*//*
            }


            for (int i = 0; i < user_data.size(); i++) {

                if(app.getUserRoleId().equalsIgnoreCase(C.COMP_SALES_ROLE) ||
                        app.getUserRoleId().equalsIgnoreCase(C.DIS_SALES_ROLE)){
                    CompSalesPrefs salesPrefs=new CompSalesPrefs(getApplicationContext());
                    user_id_main=salesPrefs.getUser_id();
                }
                else
                    user_id_main = user_data.get(i).getUser_id().toString();

            }

        } else */
            /*img_prodetail_wishlist.setVisibility(View.VISIBLE);*/


        // SetRefershDataProduct();

        Log.e("ABCCC", "" + bean_product1.size());


        if (bean_product1.size() != 0) {

            for (int i = 0; i < bean_product1.size(); i++) {

                if (bean_product1.get(i).getPro_id().equalsIgnoreCase(pid)) {

                    if (bean_product1.get(i).getPro_qty().toString().equalsIgnoreCase("0")) {
                        btn_buyonline_product_detail.setBackgroundColor(Color.parseColor("#f00000"));
                        btn_buyonline_product_detail.setText("OUT OF STOCK");
                        btn_buyonline_product_detail.setEnabled(false);
                    } else if (bean_product1.get(i).getPro_qty().toString().equalsIgnoreCase("")) {
                        btn_buyonline_product_detail.setBackgroundColor(Color.parseColor("#f00000"));
                        btn_buyonline_product_detail.setText("OUT OF STOCK");
                        btn_buyonline_product_detail.setEnabled(false);
                    } else if (bean_product1.get(i).getPro_qty().toString().equalsIgnoreCase("null")) {
                        btn_buyonline_product_detail.setBackgroundColor(Color.parseColor("#f00000"));
                        btn_buyonline_product_detail.setText("OUT OF STOCK");
                        btn_buyonline_product_detail.setEnabled(false);
                    } else {
                        btn_buyonline_product_detail.setBackgroundColor(Color.parseColor("#007BC5"));
                        btn_buyonline_product_detail.setText("Buy Now");
                        btn_buyonline_product_detail.setEnabled(true);
                    }


                    tv_product_name.setText(bean_product1.get(i).getPro_name());
                    Log.e("name", pid + bean_product1.get(i).getPro_name());
                    tv_product_code.setText("(" + bean_product1.get(i).getPro_code() + ")");
                    product_code = bean_product1.get(i).getPro_code();
                    Log.e("code", pid + bean_product1.get(i).getPro_code());
                    mrp = bean_product1.get(i).getPro_mrp();
                    //tv_product_mrp.setText("" + bean_product1.get(i).getPro_mrp());
                    Log.e("mrp", pid + bean_product1.get(i).getPro_mrp());
                    sellingprice = bean_product1.get(i).getPro_sellingprice();
                    //tv_product_selling_price.setText(""+bean_product1.get(i).getPro_sellingprice());
                    Log.e("sellngprice", pid + bean_product1.get(i).getPro_sellingprice());
                    tv_packof.setText(" 1 " + bean_product1.get(i).getPro_label());
                    selected_lable = bean_product1.get(i).getPro_label();
                    Log.e("label", pid + bean_product1.get(i).getPro_label());

                    text_desc.setText(Html.fromHtml(bean_product1.get(i).getPro_shortdesc()));
                    if (text_desc.getText().toString().equalsIgnoreCase("") || text_desc.getText().toString().contains("null")) {
                        l_desc.setVisibility(View.GONE);
                    } else {
                        l_desc.setVisibility(View.VISIBLE);
                    }
                    Log.e("desc", pid + bean_product1.get(i).getPro_shortdesc());

                    //imageloader.DisplayImage("http://app.nivida.in/boss/files/catalog/product_image/B121_Black_White.jpg", R.drawable.boss_logo_final, img_full);
                    Picasso.with(getApplicationContext())
                            .load(GlobalVariable.link + "files/" + bean_product1.get(i).getPro_image())
                            .placeholder(R.drawable.boss_logo_final)
                            .error(R.drawable.boss_logo_final)
                            .into(img_full);
                    //imageloader.DisplayImage(GlobalVariable.link +"files/"+ bean_product1.get(i).getPro_image(), R.drawable.boss_logo_final, img_full);
                    selected_image_url = bean_product1.get(i).getPro_image();
                    Log.e("mainproimg", pid + bean_product1.get(i).getPro_image());
                    String moreInformation = GlobalVariable.link + bean_product1.get(i).getPro_moreinfo();
                    Log.e("more_information", pid + bean_product1.get(i).getPro_moreinfo());

                    // more_Info_text.setText(moreInformation.toString());

                    //tv_product_mrp.setText("" + bean_product1.get(i).getPro_mrp());


                    float mrp1 = Float.parseFloat(bean_product1.get(i).getPro_mrp());
                    String mpr = String.format("%.2f", mrp1);


                    lnr_pack.setVisibility(View.VISIBLE);
                    if (app.getUserRoleId().equalsIgnoreCase(C.CUSTOMER_ROLE) ||
                            app.getUserRoleId().equalsIgnoreCase("")) {
                        lnr_mrp.setVisibility(View.VISIBLE);
                        lnr_sp.setVisibility(View.VISIBLE);
                    }

                    if (!app.getUserRoleId().equalsIgnoreCase(C.CUSTOMER_ROLE) &&
                            !app.getUserRoleId().equalsIgnoreCase(""))
                        offers.setVisibility(View.GONE);

                    tv_product_mrp.setText(mpr);

                    //tv_product_selling_price.setText(""+bean_product1.get(i).getPro_sellingprice());

                    float Selling = Float.parseFloat(bean_product1.get(i).getPro_sellingprice());
                    String Sell = String.format("%.2f", Selling);


                    tv_product_selling_price.setText(Sell);


                    float offer = mrp1 - Selling;

                    float offerreal = offer * 100;

                    float off = offerreal / mrp1;


                    BigDecimal bd = new BigDecimal(Float.toString(off));
                    bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);

                    int aa = bd.intValue();

                    String off1 = String.valueOf(aa);


                    offers.setPadding(5, 0, 5, 0);
                    offers.setText(off1 + " % OFF");

                } else {

                }

            }
        }

        if (bean_ProductCatalog.size() != 0) {
            l_more.setVisibility(View.VISIBLE);
            ArrayList<String> stringCa = new ArrayList<String>();
            for (int i = 0; i < bean_ProductCatalog.size(); i++) {
                LinearLayout.LayoutParams paramsdot = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams paramname = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0F);

                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 5.0F);
                LinearLayout.LayoutParams params_l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 70);
                final LinearLayout lhorizontalop = new LinearLayout(Product_Detail.this);
                lhorizontalop.setOrientation(LinearLayout.HORIZONTAL);
                lhorizontalop.setPadding(5, 5, 5, 5);
                lhorizontalop.setLayoutParams(params_l);


                final TextView tvop = new TextView(Product_Detail.this);
                tvop.setPadding(5, 0, 5, 0);
                tvop.setTextSize(13);
                tvop.setTextColor(Color.BLACK);
                tvop.setLayoutParams(paramsdot);

                final TextView tvoption = new TextView(Product_Detail.this);
                tvoption.setTextColor(Color.BLACK);
                tvoption.setTextSize(14);
                tvoption.setLayoutParams(paramname);

             /*   final   TextView tvtitle = new TextView(Product_Detail.this);
                tvtitle.setTextColor(Color.BLACK);
                tvtitle.setTextSize(12);*/

                final ImageView img = new ImageView(Product_Detail.this);
                img.setImageResource(R.drawable.c_download);
                img.setLayoutParams(params1);
                img.setPadding(5, 0, 5, 0);
                img.setTag(bean_ProductCatalog.get(i).getFile_path());

                if (bean_ProductCatalog.get(i).getPro_id().equalsIgnoreCase(pid)) {

                    Spanned optionv = Html.fromHtml("<b>" + bean_ProductCatalog.get(i).getLabel() + "</b> ");


                    //  StringArrayop.add("\u2022" + " " + bean_productOprtions.get(i).getPro_Option_value_name());
                    // stringdot.add("\u2022");

                    tvop.setText("\u2022");

                    tvoption.setText(optionv);

                    lhorizontalop.addView(tvop);
                    lhorizontalop.addView(tvoption);
                    lhorizontalop.addView(img);
                    //   tvtitle.setText(features.toString());
                    //   Feature_text_layout.addView(tvtitle);
                    moreinfo_text_layout.addView(lhorizontalop);


                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String imgf = "" + img.getTag();
                            new DownloadFile().execute(GlobalVariable.link + imgf, "ProductDetail.pdf");

                        }
                    });
                } else {

                }


            }
        } else {
            l_more.setVisibility(View.GONE);
        }
        //  setRefershDataCart();

        //  setRefershDataFeture();


        if (bean_productFeatures.size() != 0) {
            l_features.setVisibility(View.VISIBLE);
            ArrayList<String> StringArray = new ArrayList<String>();
            ArrayList<String> StringArraybullet = new ArrayList<String>();
            Log.e("bean_productFeatures", "" + bean_productFeatures.size());

            for (int i = 0; i < bean_productFeatures.size(); i++) {

                final LinearLayout lhorizontal = new LinearLayout(Product_Detail.this);
                lhorizontal.setOrientation(LinearLayout.HORIZONTAL);
                lhorizontal.setPadding(5, 5, 5, 5);
                final TextView tvbullet = new TextView(Product_Detail.this);
                tvbullet.setTextSize(13);
                tvbullet.setPadding(5, 0, 5, 0);
                tvbullet.setTextColor(Color.BLACK);
                final TextView tv = new TextView(Product_Detail.this);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(14);
                final TextView tvtitle = new TextView(Product_Detail.this);
                tvtitle.setTextColor(Color.BLACK);
                tvtitle.setTextSize(12);


                if (bean_productFeatures.get(i).getPro_id().equalsIgnoreCase(pid)) {


                    Spanned features = Html.fromHtml("<b>" + bean_productFeatures.get(i).getPro_feature_name() + "</b>");



                   /* StringArray.add( bean_productFeatures.get(i).getPro_feature_value());
                    StringArraybullet.add("\u2022");
                    StringBuilder builder = new StringBuilder();
                     for (String s: StringArray) {


                         builder.append(s);
                         builder.append(System.getProperty("line.separator"));
                    }*/


                    tvbullet.setText("\u2022");

                    /*tv.setText(features + ": " + bean_productFeatures.get(i).getPro_feature_value());*/

                    tv.setText(bean_productFeatures.get(i).getPro_feature_value());

                    lhorizontal.addView(tvbullet);
                    lhorizontal.addView(tv);
                    //   tvtitle.setText(features.toString());
                    //   Feature_text_layout.addView(tvtitle);
                    Feature_text_layout.addView(lhorizontal);


                } else {

                }


                Log.e("string array", "" + StringArray.size());

            }
        } else {
            l_features.setVisibility(View.GONE);
        }
        //  setRefershDataImage();

        Log.e("bean_productImages_size", "" + bean_productImages.size());
        pro_img.clear();
        if (bean_productImages.size() != 0) {
            pro_img.clear();
            Log.e("pro_img before", "" + pro_img.size());
            for (int i = 0; i < bean_productImages.size(); i++) {

                Log.e("pid", "" + bean_productImages.get(i).getPro_id() + ":" + pid);
                if (bean_productImages.get(i).getPro_id().equalsIgnoreCase(pid)) {
                    Log.e("bean_productImages_loop", "" + i);
                    pro_img.add((bean_productImages.get(i).getPro_Images()));

                }
            }
            setLayout(pro_img);
            Log.e("pro_img after", "" + pro_img.size());
        } else {
            Log.e("setRefershDataImage", "" + bean_productImages.size());
        }


        //  setRefershDataOption();
        //Log.e("121212121212",""+bean_productOprtions.size());
        if (bean_productOprtions.size() != 0) {
            option_text_layout.setVisibility(View.VISIBLE);
            ArrayList<String> StringArrayop = new ArrayList<String>();
            Log.e("121212121212", "" + bean_productOprtions.size());
            for (int i = 0; i < bean_productOprtions.size(); i++) {

                final LinearLayout lhorizontalop = new LinearLayout(Product_Detail.this);
                lhorizontalop.setOrientation(LinearLayout.HORIZONTAL);
                lhorizontalop.setPadding(5, 5, 5, 5);
                final TextView tvop = new TextView(Product_Detail.this);
                tvop.setPadding(5, 0, 5, 0);
                tvop.setTextSize(13);
                tvop.setTextColor(Color.BLACK);
                final TextView tvoption = new TextView(Product_Detail.this);
                tvoption.setTextColor(Color.BLACK);
                tvoption.setTextSize(14);
                final TextView tvtitle = new TextView(Product_Detail.this);
                tvtitle.setTextColor(Color.BLACK);
                tvtitle.setTextSize(12);
                if (bean_productOprtions.get(i).getPro_id().equalsIgnoreCase(pid)) {

                    Spanned optionv = Html.fromHtml("<b>" + bean_productOprtions.get(i).getPro_Option_name() + "</b> ");


                    //  StringArrayop.add("\u2022" + " " + bean_productOprtions.get(i).getPro_Option_value_name());
                    // stringdot.add("\u2022");

                    tvop.setText("\u2022");

                    tvoption.setText(optionv + ": " + bean_productOprtions.get(i).getPro_Option_value_name());

                    lhorizontalop.addView(tvop);
                    lhorizontalop.addView(tvoption);
                    //   tvtitle.setText(features.toString());
                    //   Feature_text_layout.addView(tvtitle);
                    option_text_layout.addView(lhorizontalop);

                } else {

                }


            }
        }
        // setRefershDataStdspec();
        if (bean_productStdSpecs.size() != 0) {
            l_Std_spec.setVisibility(View.VISIBLE);
            for (int i = 0; i < bean_productStdSpecs.size(); i++) {


                final LinearLayout lhorizontalst = new LinearLayout(Product_Detail.this);
                lhorizontalst.setOrientation(LinearLayout.HORIZONTAL);
                lhorizontalst.setPadding(5, 5, 5, 5);
                final TextView tvstandard = new TextView(Product_Detail.this);
                tvstandard.setPadding(5, 0, 5, 0);
                tvstandard.setTextSize(13);
                tvstandard.setTextColor(Color.BLACK);
                final TextView tvstd = new TextView(Product_Detail.this);
                tvstd.setTextColor(Color.BLACK);
                tvstd.setTextSize(14);


                if (bean_productStdSpecs.get(i).getPro_id().equalsIgnoreCase(pid)) {


                    Spanned studesc = Html.fromHtml("<b>" + bean_productStdSpecs.get(i).getPro_Stdspec_name() + "</b> ");
                    tvstandard.setText("\u2022");

                    tvstd.setText(studesc + ": " + bean_productStdSpecs.get(i).getPro_Stdspec_value());

                    lhorizontalst.addView(tvstandard);
                    lhorizontalst.addView(tvstd);
                    //   tvtitle.setText(features.toString());
                    //   Feature_text_layout.addView(tvtitle);
                    standard_text_layout.addView(lhorizontalst);
                } else {

                }


            }
        } else {
            l_Std_spec.setVisibility(View.GONE);
        }
        // setRefershDataYouTube();
        if (bean_productyoutube.size() != 0) {
            l_youtubevideo_main.setVisibility(View.VISIBLE);
            final LinearLayout lhorizontaltechy = new LinearLayout(Product_Detail.this);
            lhorizontaltechy.setOrientation(LinearLayout.HORIZONTAL);
            lhorizontaltechy.setPadding(5, 5, 5, 5);
            final TextView tvtech = new TextView(Product_Detail.this);
            tvtech.setPadding(5, 0, 5, 0);
            tvtech.setTextSize(13);
            tvtech.setTextColor(Color.BLACK);
            final TextView tvt = new TextView(Product_Detail.this);
            tvt.setTextColor(Color.BLACK);
            tvt.setTextSize(14);
            final ListView lst = new ListView(Product_Detail.this);


            for (int i = 0; i < bean_productyoutube.size(); i++) {
                Log.e("size", "" + bean_productyoutube.size());
                setLayout_video(bean_productyoutube);
                if (bean_productyoutube.get(i).getPro_id().equalsIgnoreCase(pid)) {


                    //imageloader.DisplayImage(setthumbnail, R.drawable.boss_blender_min, img);
                    // lst.setAdapter(new ImagenewAdapter());
                    Log.e("123", "" + bean_productyoutube.size());
                    setLayout_video(bean_productyoutube);
                    // tvt.setText(bean_productyoutube.get(i).getPro_youtube_url());

 /*tvt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AppPrefs app= new AppPrefs(Product_Detail.this);
        app.setyoutube_api(url);
        Intent i = new Intent(Product_Detail.this,Video_View_Activity.class);
        startActivity(i);
    }
});*/
                    // lhorizontaltechy.addView(tvt);

                    //layout_youtube_forimg.addView(lst);
                }
            }
        } else {
            l_youtubevideo_main.setVisibility(View.GONE);
        }

        //  setRefershDataTechDesc();
        if (bean_productTechSpecs.size() != 0) {
            l_technical.setVisibility(View.VISIBLE);
            for (int i = 0; i < bean_productTechSpecs.size(); i++) {

                /*final LinearLayout lhorizontaltech = new LinearLayout(Product_Detail.this);
                lhorizontaltech.setOrientation(LinearLayout.HORIZONTAL);
                lhorizontaltech.setPadding(5, 5, 5, 5);
                final TextView tvtech = new TextView(Product_Detail.this);
                tvtech.setPadding(5, 0, 5, 0);
                tvtech.setTextSize(13);
                tvtech.setTextColor(Color.BLACK);
                final TextView tvt = new TextView(Product_Detail.this);
                tvt.setTextColor(Color.BLACK);
                tvt.setTextSize(14);
*/
                TableLayout stk = (TableLayout) findViewById(R.id.table_main);


                if (bean_productTechSpecs.get(i).getPro_id().equalsIgnoreCase(pid)) {




                    TableRow tbrow = new TableRow(this);
                    TextView t1v = new TextView(this);
                    t1v.setText(bean_productTechSpecs.get(i).getPro_Techspec_name()+" :");
                    t1v.setTextColor(Color.BLACK);
                    t1v.setGravity(Gravity.LEFT);
                    t1v.setGravity(Gravity.START);
                    tbrow.addView(t1v);
                    TextView t2v = new TextView(this);
                    t2v.setText(bean_productTechSpecs.get(i).getPro_Techspec_value());
                    t2v.setTextColor(Color.BLACK);
                    t2v.setGravity(Gravity.LEFT);
                    tbrow.addView(t2v);
                    t1v.setPadding(20, 3, 20, 3);
                    t2v.setPadding(20, 3, 20, 3);
                    stk.addView(tbrow);
                    /*Spanned techspc = Html.fromHtml("<b>" + bean_productTechSpecs.get(i).getPro_Techspec_name() + "</b> ");
                    tvtech.setText("\u2022");

                    tvt.setText(techspc + ": " + bean_productTechSpecs.get(i).getPro_Techspec_value());

                    lhorizontaltech.addView(tvtech);
                    lhorizontaltech.addView(tvt);
                    //   tvtitle.setText(features.toString());
                    //   Feature_text_layout.addView(tvtitle);
                    technical_text_layout.addView(lhorizontaltech);
*/
                } else {

                }


            }

        } else {
            l_technical.setVisibility(View.GONE);
        }

        l_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (desc == false) {

                    img_desc.setImageResource(R.drawable.ic_boss_arrow_down);
                    img_std_spec.setImageResource(R.drawable.ic_boss_right);
                    img_technical.setImageResource(R.drawable.ic_boss_right);
                    //img_catalogue.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_features.setImageResource(R.drawable.ic_boss_right);
                    img_videos.setImageResource(R.drawable.ic_boss_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_boss_right);
                    img_more.setImageResource(R.drawable.ic_boss_right);
                    l_video_lower.setVisibility(View.GONE);

                    l_desc_detail.setVisibility(View.VISIBLE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_more_info1.setVisibility(View.GONE);


                       /* text_desc.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\n " +
                                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. ");
*/
                    desc = true;

                } else {
                    desc = false;
                    img_desc.setImageResource(R.drawable.ic_boss_right);
                    l_desc_detail.setVisibility(View.GONE);


                }


            }

        });

        l_Std_spec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spec == false) {

                    img_desc.setImageResource(R.drawable.ic_boss_right);
                    img_std_spec.setImageResource(R.drawable.ic_boss_arrow_down);
                    img_technical.setImageResource(R.drawable.ic_boss_right);
                    //img_catalogue.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_features.setImageResource(R.drawable.ic_boss_right);
                    img_videos.setImageResource(R.drawable.ic_boss_right);
                    img_more.setImageResource(R.drawable.ic_boss_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_boss_right);
                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.VISIBLE);
                    l_technical_detail.setVisibility(View.GONE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_video_lower.setVisibility(View.GONE);
                    l_more_info1.setVisibility(View.GONE);
                    // text_std_spec.setText("Available in 5 Colour Combinations to suit your kitchen");

                    spec = true;

                } else {
                    spec = false;
                    img_std_spec.setImageResource(R.drawable.ic_boss_right);
                    l_std_spec_detail.setVisibility(View.GONE);


                }


            }

        });

        l_technical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (technical == false) {


                    img_more.setImageResource(R.drawable.ic_boss_right);
                    img_desc.setImageResource(R.drawable.ic_boss_right);
                    img_std_spec.setImageResource(R.drawable.ic_boss_right);
                    img_technical.setImageResource(R.drawable.ic_boss_arrow_down);
                    // img_catalogue.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_features.setImageResource(R.drawable.ic_boss_right);
                    img_videos.setImageResource(R.drawable.ic_boss_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_boss_right);
                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.VISIBLE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_video_lower.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_more_info1.setVisibility(View.GONE);

                       /* text_Tech_spec.setText("Power\t160 W\n" +
                                "Voltage\t230 V AC\n" +
                                "Frequency\t50 Hz\n" +
                                "Speed\t2 Speeds\n" +
                                "Motor Speed\t12000 RPM");*/
                    technical = true;

                } else {
                    technical = false;
                    img_technical.setImageResource(R.drawable.ic_boss_right);
                    l_technical_detail.setVisibility(View.GONE);


                }

            }

        });
/*
        l_catalogue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(catalogue == false) {

                    img_desc.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_spec.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_technical.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_catalogue.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
                    img_features.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_videos.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);

                    l_desc_detail.setVisibility(View.GONE);
                    l_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    l_catalogue_detail.setVisibility(View.VISIBLE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);

                    text_catalogue.setText(Html.fromHtml(" <u>Download User Manual</u>"));


                    catalogue = true;

                }else{
                    catalogue = false;
                    img_catalogue.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_catalogue_detail.setVisibility(View.GONE);


                }

            }

        });*/
        l_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (features == false) {

                    img_more.setImageResource(R.drawable.ic_boss_right);
                    img_desc.setImageResource(R.drawable.ic_boss_right);
                    img_std_spec.setImageResource(R.drawable.ic_boss_right);
                    img_technical.setImageResource(R.drawable.ic_boss_right);
                    //       img_catalogue.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_features.setImageResource(R.drawable.ic_boss_arrow_down);
                    img_videos.setImageResource(R.drawable.ic_boss_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_boss_right);
                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_video_lower.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.VISIBLE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_more_info1.setVisibility(View.GONE);
                    features = true;

                     /*   text_features.setText("3 SS Blades to do a variety of jobs\n" +
                                "Wall Mounting Stand");*/

                } else {
                    features = false;
                    img_features.setImageResource(R.drawable.ic_boss_right);
                    l_features_detail.setVisibility(View.GONE);


                }

            }

        });
        l_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (videos == false) {

                    img_desc.setImageResource(R.drawable.ic_boss_right);
                    img_more.setImageResource(R.drawable.ic_boss_right);
                    img_std_spec.setImageResource(R.drawable.ic_boss_right);
                    img_technical.setImageResource(R.drawable.ic_boss_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_boss_right);
                    img_features.setImageResource(R.drawable.ic_boss_right);
                    img_videos.setImageResource(R.drawable.ic_boss_arrow_down);
                    l_more_info1.setVisibility(View.GONE);
                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.VISIBLE);
                    l_video_lower.setVisibility(View.GONE);
                    videos = true;

                } else {
                    videos = false;
                    img_videos.setImageResource(R.drawable.ic_boss_right);
                    l_videos_detail.setVisibility(View.GONE);


                }

            }

        });


        l_video_upper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (videoupper == false) {

                    img_desc.setImageResource(R.drawable.ic_boss_right);
                    img_more.setImageResource(R.drawable.ic_boss_right);
                    img_std_spec.setImageResource(R.drawable.ic_boss_right);
                    img_technical.setImageResource(R.drawable.ic_boss_right);

                    img_features.setImageResource(R.drawable.ic_boss_right);
                    img_videos.setImageResource(R.drawable.ic_boss_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_boss_arrow_down);
                    l_more_info1.setVisibility(View.GONE);
                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_video_lower.setVisibility(View.VISIBLE);

                    videoupper = true;

                } else {
                    videoupper = false;
                    videoyoutube_img.setImageResource(R.drawable.ic_boss_right);
                    l_video_lower.setVisibility(View.GONE);


                }

            }

        });

        l_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (more == false) {


                    img_desc.setImageResource(R.drawable.ic_boss_right);
                    img_std_spec.setImageResource(R.drawable.ic_boss_right);
                    img_technical.setImageResource(R.drawable.ic_boss_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_boss_right);

                    img_features.setImageResource(R.drawable.ic_boss_right);
                    img_videos.setImageResource(R.drawable.ic_boss_right);
                    img_more.setImageResource(R.drawable.ic_boss_arrow_down);

                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    l_video_lower.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_more_info1.setVisibility(View.VISIBLE);


                    more = true;


                } else {
                    more = false;
                    img_more.setImageResource(R.drawable.ic_boss_right);
                    l_more_info1.setVisibility(View.GONE);


                }


            }
        });


        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 1.0f);

        //  imageslider();


        list_data = new ArrayList<Integer>();

        /*list_data.add(R.drawable.boss_blender_min);
        list_data.add(R.drawable.bossmixer_min);
        list_data.add(R.drawable.bossjucer_min);
        list_data.add(R.drawable.bossssbotel_min);*/


        // mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
    /*   mViewPager = (ViewPager) findViewById(R.id.container1);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        mViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(mViewPager);*/



        btn_buyonline_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Bean_ProductCart> array_product_cart = new ArrayList<Bean_ProductCart>();

                db = new DatabaseHandler(Product_Detail.this);
                bean_cart = db.Get_Product_Cart();
                db.close();
                Log.e("Bean_Cart", "" + bean_cart.size());
                Log.e("Product_Code", "" + tv_product_code.getText().toString());
                array_product_cart = db.is_product_in_cart(product_code);
                Log.e("New array product size", "" + array_product_cart.size());

                LinearLayout lnr_pop_mrp, lnr_pop_sp, lnr_total;


                Log.e("Option Size", "" + bean_productOprtions.size());

                if (array_product_cart.size() > 0) {
                    Log.e("Product Code ", "" + array_product_cart.get(0).getPro_code());
                    Log.e("Product Name ", "" + array_product_cart.get(0).getPro_name());
                    dialog = new Dialog(Product_Detail.this);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    dialog.setCanceledOnTouchOutside(false);
                    wlp.gravity = Gravity.BOTTOM;
                    wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window.setAttributes(wlp);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.popup_cart);
                    minuss = (ImageView) dialog.findViewById(R.id.minus);
                    plus = (ImageView) dialog.findViewById(R.id.plus);
                    buy_cart = (Button) dialog.findViewById(R.id.Btn_Buy_online11);
                    cancel = (Button) dialog.findViewById(R.id.cancel);
                    edt_count = (EditText) dialog.findViewById(R.id.edt_count);
                    edt_count.setSelection(edt_count.getText().length());

                    l_view_spinner = (LinearLayout) findViewById(R.id.l_view_spinner);
                    tv_pop_pname = (TextView) dialog.findViewById(R.id.product_name);
                    tv_pop_code = (TextView) dialog.findViewById(R.id.product_code);
                    tv_pop_packof = (TextView) dialog.findViewById(R.id.product_packof);
                    tv_pop_mrp = (TextView) dialog.findViewById(R.id.product_mrp);
                    tv_txt_mrp = (TextView) dialog.findViewById(R.id.txt_mrp_text);
                    tv_pop_sellingprice = (TextView) dialog.findViewById(R.id.product_sellingprice);
                    tv_total = (TextView) dialog.findViewById(R.id.txt_total);
                    l_spinner = (LinearLayout) dialog.findViewById(R.id.l_spinner);
                    l_spinner_text = (LinearLayout) dialog.findViewById(R.id.l_spinnertext);
                    tv_pop_pname.setText(tv_product_name.getText().toString());
                    tv_pop_code.setText(" " + tv_product_code.getText().toString() + " ");
                    tv_pop_packof.setText(tv_packof.getText().toString());
                    tv_pop_mrp.setText(tv_product_mrp.getText().toString());
                    tv_pop_sellingprice.setText(tv_product_selling_price.getText().toString());
                    tv_pop_sellingprice.setTag(tv_product_selling_price.getText().toString());

                    lnr_pop_mrp = (LinearLayout) dialog.findViewById(R.id.lnr_pop_mrp);
                    lnr_pop_sp = (LinearLayout) dialog.findViewById(R.id.lnr_pop_sp);
                    lnr_total = (LinearLayout) dialog.findViewById(R.id.lnr_total);

                    tv_total.setText(tv_product_selling_price.getText().toString());
                    //  tv_total.setText(bean_product1.get(position).getPro_sellingprice().toString());

                    int temp_count = Integer.parseInt(edt_count.getText().toString());
                    int temp_total_count = temp_count + Integer.parseInt(array_product_cart.get(0).getPro_qty().toString());
                    float temp_total = Float.parseFloat(tv_total.getText().toString());
                    edt_count.setText("1");
                    tv_total.setText(String.format("%.2f", Float.parseFloat(tv_pop_sellingprice.getText().toString())));

                    float mrp = Float.parseFloat(tv_pop_mrp.getText().toString());
                    float sellingprice = Float.parseFloat(tv_pop_sellingprice.getText().toString());

                    String mrp_price = String.format("%.2f", mrp);
                    String sell_price = String.format("%.2f", sellingprice);

                    if (!app.getUserRoleId().equalsIgnoreCase(C.CUSTOMER_ROLE) &&
                            !app.getUserRoleId().equalsIgnoreCase("")) {
                        lnr_pop_mrp.setVisibility(View.GONE);
                        lnr_pop_sp.setVisibility(View.GONE);
                        lnr_total.setVisibility(View.GONE);
                    }

                    if (mrp > sellingprice) {
                        tv_pop_mrp.setVisibility(View.VISIBLE);
                        tv_txt_mrp.setVisibility(View.VISIBLE);
                        tv_pop_mrp.setPaintFlags(tv_pop_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    } else {
                        tv_pop_mrp.setVisibility(View.GONE);
                        tv_txt_mrp.setVisibility(View.GONE);

                    }

                    arrOptionTypeID.clear();
                    arrOptionTypeName.clear();
                    arrOptionTypeID = new ArrayList<String>();
                    arrOptionTypeName = new ArrayList<String>();
                    if (bean_productOprtions.size() != 0) {
                        ArrayList<Bean_Attribute> array_attributes = new ArrayList<Bean_Attribute>();

                        Log.e("121212 ::", "" + bean_productOprtions.size());

                        for (int c = 0; c < bean_productOprtions.size(); c++) {
                            Log.e("232323 ::", "" + bean_productOprtions.get(c).getPro_id());
                            Log.e("343434 ::", "" + pid);
                            if (pid.equalsIgnoreCase(pid)) {
                                Bean_Attribute bean_attribute = new Bean_Attribute();
                                arrOptionTypeID.add(bean_productOprtions.get(c).getPro_Option_id());
                                arrOptionTypeName.add("Select " + bean_productOprtions.get(c).getPro_Option_name());
                                bean_attribute.setOption_id(bean_productOprtions.get(c).getPro_Option_id());
                                bean_attribute.setOption_name(bean_productOprtions.get(c).getPro_Option_name());

                                //OptionValue
                                bean_attribute.setValue_name(bean_productOprtions.get(c).getPro_Option_value_name());
                                Log.e("Value Name", "------" + bean_productOprtions.get(c).getPro_Option_value_name());
                                bean_attribute.setValue_id(bean_productOprtions.get(c).getPro_Option_value_id());

                                //ProductOptionImage
                                bean_attribute.setValue_image(bean_productOprtions.get(c).getPro_Option_proimage());


                                bean_attribute.setValue_product_code(bean_productOprtions.get(c).getPro_Option_procode());
                                bean_attribute.setValue_mrp(bean_productOprtions.get(c).getPro_Option_mrp());
                                bean_attribute.setValue_selling_price(bean_productOprtions.get(c).getPro_Option_selling_price());
                              /*  bean_attribute.setOption_pro_id(bean_productOprtions.get(c).getOption_pro_id());

                                Log.e("Product ID", "------" + bean_attribute.getOption_pro_id());

                                Log.e("Product ID11", "------" + bean_productOprtions.get(c).getOption_pro_id());*/
                                array_attributes.add(bean_attribute);
                            }
                        }

                        Log.e("45454545 ::", "" + array_attributes.size());

                        if (arrOptionTypeID.size() != 0) {


                            ArrayList<String> temp_array = new ArrayList<String>();
                            for (int i = 0; i < arrOptionTypeID.size(); i++) {
                                temp_array.add(arrOptionTypeID.get(i));
                            }
                            LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionTypeID);

                            arrOptionTypeID.clear();
                            //Creating Arraylist without duplicate values
                            arrOptionTypeID = new ArrayList<String>(listToSet);



                            /* LinkedHashSet<String> listToSet1 = new LinkedHashSet<String>(arrOptionTypeName);

                             arrOptionTypeName.clear();
                             //Creating Arraylist without duplicate values
                             arrOptionTypeName = new ArrayList<String>(listToSet1);

                             Log.e("AAAABBBccc", "" + arrOptionTypeName.size());*/
                            Log.e("AAAABBB", "1 - " + arrOptionTypeID.size() + " 2- " + temp_array.size());
                            //int z = 0;

                             /*for(int p = 0 ; p < temp_array.size() ; p ++)
                             {
                                   // Bean_Attribute
                                 for(int s = 0 ; s < arrOptionTypeID.size() ; s++)
                                 {
                                     if(temp_array.get(p).equalsIgnoreCase(arrOptionTypeID.get(s)))
                                     {
                                         Log.e("", p + "---" + "option ID - " + temp_array.get(p) + "List hash ID  - " + arrOptionTypeID.get(s));
                                       //  bean_attribute.setOption_id();
                                     }

                                 }

                             }*/

                            for (int p = 0; p < arrOptionTypeID.size(); p++) {
                                Log.e("arrOptionTypeID", "----------" + arrOptionTypeID.get(p));
                            }
                            for (int p = 0; p < temp_array.size(); p++) {
                                Log.e("temp_array", "----------" + temp_array.get(p));
                            }

                            for (int p = 0; p < array_attributes.size(); p++) {
                                Log.e("array_attributes", "----------" + array_attributes.get(p).getOption_id());
                            }

                            array_attribute_main = new ArrayList<ArrayList<Bean_Attribute>>();


                            for (int p = 0; p < arrOptionTypeID.size(); p++) {

                                ArrayList<Bean_Attribute> array_attribute = new ArrayList<Bean_Attribute>();

                                for (int s = 0; s < temp_array.size(); s++) {
                                    if (temp_array.get(s).equalsIgnoreCase(arrOptionTypeID.get(p))) {
                                        //Log.e("", p + "---" + "option ID - " + temp_array.get(s) + "List hash ID  - " + arrOptionTypeID.get(p));
                                        Bean_Attribute bean_demo = new Bean_Attribute();
                                        bean_demo.setOption_id(array_attributes.get(s).getOption_id());
                                        Log.e("Option Id", "-----" + array_attributes.get(s).getOption_id());
                                        bean_demo.setOption_name(array_attributes.get(s).getOption_name());
                                        bean_demo.setValue_id(array_attributes.get(s).getValue_id());
                                        bean_demo.setValue_name(array_attributes.get(s).getValue_name());
                                        Log.e("Value Name", "-----" + array_attributes.get(s).getValue_name());
                                        bean_demo.setValue_mrp(array_attributes.get(s).getValue_mrp());
                                        bean_demo.setValue_product_code(array_attributes.get(s).getValue_product_code());
                                        bean_demo.setValue_selling_price(array_attributes.get(s).getValue_selling_price());
                                        bean_demo.setValue_image(array_attributes.get(s).getValue_image());

                                        // bean_demo.setOption_pro_id(array_attributes.get(s).getOption_pro_id());
                                        array_attribute.add(bean_demo);
                                    }
                                }
                                Log.e("", "new bean size -- " + array_attribute.size());

                                array_attribute_main.add(array_attribute);
                                Log.e("", "new bean size -- " + array_attribute_main.size());
                            }


                            for (int i = 0; i < array_attribute_main.size(); i++) {
                                Bean_Value_Selected_Detail bean = new Bean_Value_Selected_Detail();
                                bean.setValue_id("0");
                                bean.setValue_name("Test");
                                bean.setValue_mrp("00");
                                array_value.add(bean);
                            }
                            for (int k = 0; k < array_attribute_main.size(); k++) {

                                final TextView text = new TextView(Product_Detail.this);

                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                                params1.setMargins(2, 2, 2, 2);

                                text.setLayoutParams(params1);

                              /*  ArrayList<Bean_Attribute> array_att = new ArrayList<Bean_Attribute>();
                                array_att = array_attribute_main.get(k);

                                text.setText(""+array_att.get(0).getOption_name());*/

                                text.setTextColor(Color.parseColor("#000000"));

                                text.setTextSize(12);

                                l_spinner_text.addView(text);

                                final Spinner spinner = new Spinner(Product_Detail.this);

                                spinner.setBackgroundResource(R.drawable.spinner_bg);


                                spinner.setTag("" + k);

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                                params.setMargins(2, 2, 2, 2);

                                spinner.setLayoutParams(params);

                                spinner.setAdapter(new MyAdapter(Product_Detail.this, R.layout.textview, array_attribute_main.get(k)));

                                l_spinner.addView(spinner);

                                array_att = new ArrayList<Bean_Attribute>();
                                array_att = array_attribute_main.get(k);


                                Log.e("array_attr size : ", "" + array_att.size());
                                for (int t = 0; t < array_att.size(); t++) {


                                    text.setText("Select " + array_att.get(t).getOption_name());


                                    if (k == 0) {
                                        option_id = array_att.get(t).getOption_id();
                                        option_name = array_att.get(t).getOption_name();
                                    } else {
                                        option_id = option_id + ", " + array_att.get(t).getOption_id();
                                        option_name = option_name + ", " + array_att.get(t).getOption_name();
                                    }

                                    Log.e("Option id", "" + option_id);
                                    Log.e("Option name", option_name);
                                }
                                final int finalK = k;
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int position1, long id) {
                                        // TODO Auto-generated method stub

                                        // position1=""+position;

                                        int pos = Integer.parseInt(spinner.getTag().toString());
                                        ArrayList<Bean_Attribute> att_array = new ArrayList<Bean_Attribute>();
                                        att_array = array_attribute_main.get(pos);

                                        Log.e("Position", "" + att_array.get(position1).getValue_name());
                                        Log.e("Position", "" + att_array.get(position1).getValue_id());
                                        Log.e("Position", "" + att_array.get(position1).getValue_mrp());


                                        Bean_Value_Selected_Detail bean = new Bean_Value_Selected_Detail();
                                        bean.setValue_id(att_array.get(position1).getValue_id());
                                        bean.setValue_name(att_array.get(position1).getValue_name());
                                        bean.setValue_mrp(att_array.get(position1).getValue_mrp());
                                        array_value.set(finalK, bean);


                                        Log.e("", "ID :- " + array_value.get(finalK).getValue_id());
                                        Log.e("", "Name :- " + array_value.get(finalK).getValue_name());
                                        Log.e("", "MRP :- " + array_value.get(finalK).getValue_mrp());
                                        //   Log.e("", "Product Id :- " + att_array.get(position1).getOption_pro_id());
                                        Log.e("", "Product Code :- " + att_array.get(position1).getValue_product_code().toString());
                                        //    tv_pop_pname.setTag(att_array.get(position1).getOption_pro_id());
                                        //  tv_pop_code.setText(" ( " + att_array.get(position1).getValue_product_code().toString() + " )");

                                        if (att_array.get(position1).getValue_image().equalsIgnoreCase("") || att_array.get(position1).getValue_image().equalsIgnoreCase("null")) {


                                        } else {
                                            list_of_images.add(0, att_array.get(position1).getValue_image());
                                            //adapter.notifyDataSetChanged();
                                        }

                                        if (att_array.get(position1).getValue_selling_price().toString().equalsIgnoreCase("0")) {

                                        } else {
                                            tv_pop_mrp.setText("" + att_array.get(position1).getValue_mrp().toString());
                                            tv_pop_sellingprice.setText("" + att_array.get(position1).getValue_selling_price().toString());

                                            if (att_array.get(position1).getValue_product_code().toString().equalsIgnoreCase("null") || att_array.get(position1).getValue_product_code().toString().equalsIgnoreCase("")) {
                                                Log.e("121212", "" + att_array.get(position1).getValue_product_code().toString());
                                                tv_pop_code.setText(" " + tv_product_code.getText().toString() + "");

                                            } else {
                                                Log.e("131313", "" + att_array.get(position1).getValue_product_code().toString());
                                                tv_pop_code.setText(" (" + att_array.get(position1).getValue_product_code().toString() + ")");
                                                //result_holder.tvproduct_code.setText("ABC");

                                            }
                                            tv_pop_sellingprice.setTag(att_array.get(position1).getValue_selling_price().toString());
                                            if (edt_count.getText().toString().equalsIgnoreCase("1")) {
                                                tv_total.setText(att_array.get(position1).getValue_selling_price().toString());


                                            } else {
                                                int counter = Integer.parseInt(edt_count.getText().toString());
                                                float amt = Integer.parseInt(tv_pop_sellingprice.getText().toString());
                                                float total = amt * counter;
                                                float finalValue = (float) (Math.round(total * 100) / 100);
                                                String str = String.format("%.2f", total);
                                                tv_total.setText(str);
                                            }
                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        // TODO Auto-generated method stub

                                    }
                                });

                            }
                                    /*
                             Log.e("",""+"option ID - "+temp_array.get(1) + "List hash ID  - "+arrOptionTypeID.get(1));
                             if(temp_array.get(0).equalsIgnoreCase(arrOptionTypeID.get(0)))
                             {
                                 System.out.println("option ID - "+temp_array.get(p) + "List hash ID  - "+arrOptionTypeID.get(z));
                                 Log.e("------",""+"option ID - "+temp_array.get(p) + "List hash ID  - "+arrOptionTypeID.get(z));
                             }
                             else
                             {
                                 z++;
                                 System.out.println("option ID - "+temp_array.get(p) + "List hash ID  - "+arrOptionTypeID.get(z));
                                 Log.e("", "" + "option ID - " + temp_array.get(p) + "List hash ID  - " + arrOptionTypeID.get(z));
                             }*/
                            /*for (int s = 0; s < arrOptionTypeID.size(); s++) {



                                    Spinner spinner = new Spinner(Product_List.this);

                                    spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                                    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(Product_List.this,
                                            android.R.layout.simple_spinner_dropdown_item, );
                                    spinner.setAdapter(spinnerArrayAdapter);

                                    l_spinner.addView(spinner);

                            }*/


                        }



                        /*LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                        arrOptionType.clear();
                        //Creating Arraylist without duplicate values
                        arrOptionType = new ArrayList<String>(listToSet);

                        Log.e("AAAA", "" + arrOptionType.size());

                        List newList = new ArrayList(new LinkedHashSet(
                                arrOptionType));
                        Iterator it = newList.iterator();
                        while (it.hasNext()) {
                            System.out.println(it.next());

                        }*/


                    } else {
                        //  GlobalVariable.CustomToast(Product_List.this, "ALLL NO DATA", getLayoutInflater());
                          /*  if(bean_productOprtions.size() == 0){
                                l_view_spinner.setVisibility(View.GONE);
                            }else{
                                l_view_spinner.setVisibility(View.VISIBLE);
                            }*/
                    }


                    dialog.show();

                    String c = edt_count.getText().toString();
                    Log.e("float - ", "" + edt_count.getText().toString());
                    s = Integer.parseInt(c);
                    minuss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String c = edt_count.getText().toString();
                            Log.e("float - ", "" + edt_count.getText().toString());
                            s = Integer.parseInt(c);
                            if (s == 0) {
                                //GlobalVariable.CustomToast(Product_List.this, "Its impossible", getLayoutInflater());
                                edt_count.setText("1");
                                tv_total.setText(Integer.parseInt(tv_pop_sellingprice.getTag().toString()));

                            } else {

                                s = s - 1;
                                if (s == 0) {
                                    edt_count.setText("1");
                                    tv_total.setText(tv_pop_sellingprice.getTag().toString());
                                } else {
                                    //   edt_count.setText(String.valueOf(s));
                                    edt_count.setText(String.valueOf(s));
                                    int counter = s;
                                    float amt = Integer.parseInt(tv_pop_sellingprice.getTag().toString());
                                    float total = amt * counter;
                                    float finalValue = (float) (Math.round(total * 100) / 100);

                                    String str = String.format("%.2f", total);
                                    tv_total.setText(str);


                                }


                                // Toast.makeText(activity,"Total "+to,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    String c1 = edt_count.getText().toString();
                    Log.e("float - ", "" + edt_count.getText().toString());
                    // s=Integer.parseInt(c1);
                    s = Integer.parseInt(c1);

                    plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String c1 = edt_count.getText().toString();
                            Log.e("float - ", "" + edt_count.getText().toString());
                            // s=Integer.parseInt(c1);
                            s = Integer.parseInt(c1);
                            s = s + 1;
                            edt_count.setText(String.valueOf(s));
                            int counter = s;
                            float amt = Integer.parseInt(tv_pop_sellingprice.getTag().toString());
                            float total = amt * counter;
                            float finalValue = (float) (Math.round(total * 100) / 100);

                            String str = String.format("%.2f", total);
                            tv_total.setText(str);

                            // Toast.makeText(getApplicationContext(),"Total "+to,Toast.LENGTH_SHORT).show();
                        }
                    });

                    edt_count.addTextChangedListener(new TextWatcher() {

                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {
                            if (s.length() == 0) { // do your work here }
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                edt_count.setText("1");
                                tv_total.setText(productpricce);
                            }

                        }

                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            // TODO Auto-generated method stub
                            if (edt_count.getText().toString().equals("0")) {
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                edt_count.setText("1");
                                tv_total.setText(productpricce);
                            } else if (edt_count.getText().toString().equals("")) {
                                // Do nothing here
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                edt_count.setText("1");
                                tv_total.setText(productpricce);
                            } else if (Integer.parseInt(edt_count.getText().toString()) == 0) {
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                edt_count.setText("1");
                                tv_total.setText(productpricce);
                            } else if (s.length() != 0) { // do your work here }
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                amount1 = Integer.parseInt(s.toString());
                                amount1 = Integer.parseInt(productpricce) * amount1;
                                float finalValue = (float) (Math.round(amount1 * 100) / 100);

                                String str = String.format("%.2f", amount1);
                                tv_total.setText(str);


                            } else {
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                edt_count.setText("1");
                                tv_total.setText(productpricce);
                        /*if(txt_count.getText().toString().equalsIgnoreCase("0")){
                            txt_amount.setEnabled(false);
						}
						*/

                            }
                        }
                    });

                    buy_cart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            //  Globals.CustomToast(BTLProduct_Detail.this, "Item insert in cart", getLayoutInflater());
                            Bean_ProductCart bean = new Bean_ProductCart();
                            String value_id = "";
                            String value_name = "";


                            for (int i = 0; i < array_value.size(); i++) {
                                if (i == 0) {
                                    value_name = array_value.get(i).getValue_name();
                                    value_id = array_value.get(i).getValue_id();
                                } else {
                                    value_id = value_id + ", " + array_value.get(i).getValue_id();
                                    value_name = value_name + ", " + array_value.get(i).getValue_name();

                                }
                            }


                            //*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            /*option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*//**//**/


                            bean.setPro_id(pid);
                            // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                            bean.setPro_cat_id(CategoryID);
                            Log.e("Cat_ID....", "" + CategoryID);
                            bean.setPro_Images(bean_product1.get(0).getPro_image());
                            // bean.setPro_Images(list_of_images.get(position));
                            //bean.setPro_code(result_holder.tvproduct_code.getText().toString());
                            bean.setPro_code(tv_pop_code.getText().toString());
                            Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                            bean.setPro_name(tv_pop_pname.getText().toString());
                            bean.setPro_qty(edt_count.getText().toString());
                            bean.setPro_mrp(tv_pop_mrp.getText().toString());
                            bean.setPro_sellingprice(tv_pop_sellingprice.getText().toString());
                            bean.setPro_shortdesc(tv_packof.getText().toString());
                            bean.setPro_Option_id(option_id);
                            Log.e("--", "Option Id " + option_id);

                            bean.setPro_Option_name(option_name);
                            Log.e("-- ", "Option Name " + option_name);

                            bean.setPro_Option_value_id(value_id);

                            Log.e("", "Value Name " + value_id);
                            bean.setPro_Option_value_name(value_name);
                            Log.e("", "Value Name " + value_name);

                            bean.setPro_total(tv_total.getText().toString());

                            db.Add_Product_cart(bean, option_name, value_name);

                            array_value.clear();

                            Toast.makeText(Product_Detail.this , "Product insreted in to cart" , Toast.LENGTH_SHORT);
                           // GlobalVariable.CustomToast(Product_Detail.this, "Product insreted in to cart", getLayoutInflater());
                            Intent i = new Intent(Product_Detail.this, Product_Detail.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();
                            bean_productImages.clear();
                            bean_Product_Youtube.clear();
                            db = new DatabaseHandler(Product_Detail.this);
                            db.Delete_ALL_table();
                            db.close();
                            new get_product().execute();

                            dialog.dismiss();
                        }

                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                } else {

                    LinearLayout lnr_pop_mrp1, lnr_pop_sp1, lnr_total1;

                    dialog = new Dialog(Product_Detail.this);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    dialog.setCanceledOnTouchOutside(false);
                    wlp.gravity = Gravity.BOTTOM;
                    wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window.setAttributes(wlp);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.popup_cart);
                    minuss = (ImageView) dialog.findViewById(R.id.minus);
                    plus = (ImageView) dialog.findViewById(R.id.plus);
                    buy_cart = (Button) dialog.findViewById(R.id.Btn_Buy_online11);
                    cancel = (Button) dialog.findViewById(R.id.cancel);
                    edt_count = (EditText) dialog.findViewById(R.id.edt_count);
                    edt_count.setSelection(edt_count.getText().length());

                    lnr_pop_mrp1 = (LinearLayout) dialog.findViewById(R.id.lnr_pop_mrp);
                    lnr_pop_sp1 = (LinearLayout) dialog.findViewById(R.id.lnr_pop_sp);
                    lnr_total1 = (LinearLayout) dialog.findViewById(R.id.lnr_total);

                    if (!app.getUserRoleId().equalsIgnoreCase(C.CUSTOMER_ROLE) &&
                            !app.getUserRoleId().equalsIgnoreCase("")) {
                        lnr_pop_mrp1.setVisibility(View.GONE);
                        lnr_pop_sp1.setVisibility(View.GONE);
                        lnr_total1.setVisibility(View.GONE);
                    }


                    tv_pop_pname = (TextView) dialog.findViewById(R.id.product_name);
                    tv_pop_code = (TextView) dialog.findViewById(R.id.product_code);
                    tv_pop_packof = (TextView) dialog.findViewById(R.id.product_packof);
                    tv_pop_mrp = (TextView) dialog.findViewById(R.id.product_mrp);
                    tv_txt_mrp = (TextView) dialog.findViewById(R.id.txt_mrp_text);
                    tv_pop_sellingprice = (TextView) dialog.findViewById(R.id.product_sellingprice);
                    tv_total = (TextView) dialog.findViewById(R.id.txt_total);
                    l_spinner = (LinearLayout) dialog.findViewById(R.id.l_spinner);
                    l_spinner_text = (LinearLayout) dialog.findViewById(R.id.l_spinnertext);
                    tv_pop_pname.setText(tv_product_name.getText().toString());
                    tv_pop_code.setText("  " + tv_product_code.getText().toString() + " ");
                    tv_pop_packof.setText(tv_packof.getText().toString());
                    l_view_spinner = (LinearLayout) dialog.findViewById(R.id.l_view_spinner);
                    Log.e("tv_pop_mrp", "" + tv_product_mrp.getText().toString());
                    tv_pop_mrp.setText(tv_product_mrp.getText().toString());
                    tv_pop_sellingprice.setText(tv_product_selling_price.getText().toString());
                    tv_pop_sellingprice.setTag(tv_product_selling_price.getText().toString());
                    tv_total.setText(tv_product_selling_price.getText().toString());


                    float mrp = Float.parseFloat(tv_pop_mrp.getText().toString());
                    float sellingprice = Float.parseFloat(tv_pop_sellingprice.getText().toString());

                    if (mrp > sellingprice) {
                        tv_pop_mrp.setVisibility(View.VISIBLE);
                        tv_txt_mrp.setVisibility(View.VISIBLE);
                        tv_pop_mrp.setPaintFlags(tv_pop_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    } else {
                        tv_pop_mrp.setVisibility(View.GONE);
                        tv_txt_mrp.setVisibility(View.GONE);
                    }

                    arrOptionTypeID.clear();
                    arrOptionTypeName.clear();
                    arrOptionTypeID = new ArrayList<String>();
                    arrOptionTypeName = new ArrayList<String>();
                    if (bean_productOprtions.size() != 0) {
                        ArrayList<Bean_Attribute> array_attributes = new ArrayList<Bean_Attribute>();

                        Log.e("121212 ::", "" + bean_productOprtions.size());

                        for (int c = 0; c < bean_productOprtions.size(); c++) {
                            Log.e("232323 ::", "" + bean_productOprtions.get(c).getPro_id());
                            Log.e("343434 ::", "" + pid);
                            if (pid.equalsIgnoreCase(pid)) {
                                Bean_Attribute bean_attribute = new Bean_Attribute();
                                arrOptionTypeID.add(bean_productOprtions.get(c).getPro_Option_id());
                                arrOptionTypeName.add("Select " + bean_productOprtions.get(c).getPro_Option_name());
                                bean_attribute.setOption_id(bean_productOprtions.get(c).getPro_Option_id());
                                bean_attribute.setOption_name(bean_productOprtions.get(c).getPro_Option_name());

                                //OptionValue
                                bean_attribute.setValue_name(bean_productOprtions.get(c).getPro_Option_value_name());
                                Log.e("Value Name", "------" + bean_productOprtions.get(c).getPro_Option_value_name());
                                bean_attribute.setValue_id(bean_productOprtions.get(c).getPro_Option_value_id());

                                //ProductOptionImage
                                bean_attribute.setValue_image(bean_productOprtions.get(c).getPro_Option_proimage());

                                bean_attribute.setValue_product_code(bean_productOprtions.get(c).getPro_Option_procode());
                                   /* if(p_code.equalsIgnoreCase("")) {
                                        p_code = bean_productOprtions.get(c).getPro_Option_procode();
                                    }*/
                                Log.e("Product code", "------" + bean_productOprtions.get(c).getPro_Option_procode());
                                Log.e("Product code1", "------" + bean_attribute.getValue_product_code());

                                bean_attribute.setValue_mrp(bean_productOprtions.get(c).getPro_Option_mrp());
                                bean_attribute.setValue_selling_price(bean_productOprtions.get(c).getPro_Option_selling_price());
                                // bean_attribute.setOption_pro_id(bean_productOprtions.get(c).getOption_pro_id());

                                //  Log.e("Product ID", "------" + bean_attribute.getOption_pro_id());

                                //  Log.e("Product ID11", "------" + bean_productOprtions.get(c).getOption_pro_id());
                                array_attributes.add(bean_attribute);
                            }
                        }

                        Log.e("45454545 ::", "" + array_attributes.size());
                        if (arrOptionTypeID.size() != 0) {


                            ArrayList<String> temp_array = new ArrayList<String>();
                            for (int i = 0; i < arrOptionTypeID.size(); i++) {
                                temp_array.add(arrOptionTypeID.get(i));
                            }
                            LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionTypeID);

                            arrOptionTypeID.clear();
                            //Creating Arraylist without duplicate values
                            arrOptionTypeID = new ArrayList<String>(listToSet);



                            /* LinkedHashSet<String> listToSet1 = new LinkedHashSet<String>(arrOptionTypeName);

                             arrOptionTypeName.clear();
                             //Creating Arraylist without duplicate values
                             arrOptionTypeName = new ArrayList<String>(listToSet1);

                             Log.e("AAAABBBccc", "" + arrOptionTypeName.size());*/
                            Log.e("AAAABBB", "1 - " + arrOptionTypeID.size() + " 2- " + temp_array.size());
                            //int z = 0;

                             /*for(int p = 0 ; p < temp_array.size() ; p ++)
                             {
                                   // Bean_Attribute
                                 for(int s = 0 ; s < arrOptionTypeID.size() ; s++)
                                 {
                                     if(temp_array.get(p).equalsIgnoreCase(arrOptionTypeID.get(s)))
                                     {
                                         Log.e("", p + "---" + "option ID - " + temp_array.get(p) + "List hash ID  - " + arrOptionTypeID.get(s));
                                       //  bean_attribute.setOption_id();
                                     }

                                 }

                             }*/

                            for (int p = 0; p < arrOptionTypeID.size(); p++) {
                                Log.e("arrOptionTypeID", "----------" + arrOptionTypeID.get(p));
                            }
                            for (int p = 0; p < temp_array.size(); p++) {
                                Log.e("temp_array", "----------" + temp_array.get(p));
                            }

                            for (int p = 0; p < array_attributes.size(); p++) {
                                Log.e("array_attributes", "----------" + array_attributes.get(p).getOption_id());
                            }

                            array_attribute_main = new ArrayList<ArrayList<Bean_Attribute>>();

                            for (int p = 0; p < arrOptionTypeID.size(); p++) {

                                ArrayList<Bean_Attribute> array_attribute = new ArrayList<Bean_Attribute>();

                                for (int s = 0; s < temp_array.size(); s++) {
                                    if (temp_array.get(s).equalsIgnoreCase(arrOptionTypeID.get(p))) {
                                        //Log.e("", p + "---" + "option ID - " + temp_array.get(s) + "List hash ID  - " + arrOptionTypeID.get(p));
                                        Bean_Attribute bean_demo = new Bean_Attribute();
                                        bean_demo.setOption_id(array_attributes.get(s).getOption_id());
                                        Log.e("Option Id", "-----" + array_attributes.get(s).getOption_id());
                                        bean_demo.setOption_name(array_attributes.get(s).getOption_name());
                                        bean_demo.setValue_id(array_attributes.get(s).getValue_id());
                                        bean_demo.setValue_name(array_attributes.get(s).getValue_name());
                                        Log.e("Value Name111", "-----" + array_attributes.get(s).getValue_name());
                                        bean_demo.setValue_mrp(array_attributes.get(s).getValue_mrp());
                                        bean_demo.setValue_product_code(array_attributes.get(s).getValue_product_code());
                                           /* if(p_code.equalsIgnoreCase("")) {
                                                p_code = array_attributes.get(s).getValue_product_code();
                                            }*/
                                        Log.e("Value Product Code", "" + array_attributes.get(s).getValue_product_code());
                                        Log.e("Value Product Code1", "" + bean_demo.getValue_product_code());
                                        bean_demo.setValue_selling_price(array_attributes.get(s).getValue_selling_price());
                                        bean_demo.setValue_image(array_attributes.get(s).getValue_image());
                                        // bean_demo.setOption_pro_id(array_attributes.get(s).getOption_pro_id());
                                        // Log.e("Value Product ID1", "" + bean_demo.getOption_pro_id());
                                        array_attribute.add(bean_demo);
                                    }
                                }
                                Log.e("", "new bean size -- " + array_attribute.size());

                                array_attribute_main.add(array_attribute);
                                Log.e("", "new bean size -- " + array_attribute_main.size());
                            }


                            for (int i = 0; i < array_attribute_main.size(); i++) {
                                Bean_Value_Selected_Detail bean = new Bean_Value_Selected_Detail();
                                bean.setValue_id("0");
                                bean.setValue_name("Test");
                                bean.setValue_mrp("00");
                                array_value.add(bean);
                            }
                            for (int k = 0; k < array_attribute_main.size(); k++) {

                                final TextView text = new TextView(Product_Detail.this);

                                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                                params1.setMargins(2, 2, 2, 2);

                                text.setLayoutParams(params1);

                              /*  ArrayList<Bean_Attribute> array_att = new ArrayList<Bean_Attribute>();
                                array_att = array_attribute_main.get(k);

                                text.setText(""+array_att.get(0).getOption_name());*/

                                text.setTextColor(Color.parseColor("#000000"));

                                text.setTextSize(12);

                                l_spinner_text.addView(text);

                                final Spinner spinner = new Spinner(Product_Detail.this);

                                spinner.setBackgroundResource(R.drawable.spinner_bg);


                                spinner.setTag("" + k);

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                                params.setMargins(2, 2, 2, 2);

                                spinner.setLayoutParams(params);

                                spinner.setAdapter(new MyAdapter(Product_Detail.this, R.layout.textview, array_attribute_main.get(k)));

                                l_spinner.addView(spinner);

                                array_att = new ArrayList<Bean_Attribute>();
                                array_att = array_attribute_main.get(k);


                                for (int t = 0; t < array_att.size(); t++) {


                                    text.setText("Select " + array_att.get(t).getOption_name());


                                    if (k == 0) {
                                        option_id = array_att.get(t).getOption_id();
                                        option_name = array_att.get(t).getOption_name();
                                    } else {
                                        option_id = option_id + ", " + array_att.get(t).getOption_id();
                                        option_name = option_name + ", " + array_att.get(t).getOption_name();
                                    }

                                    Log.e("Option id", "" + option_id);
                                }
                                final int finalK = k;
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int position1, long id) {
                                        // TODO Auto-generated method stub

                                        // position1=""+position;

                                        int pos = Integer.parseInt(spinner.getTag().toString());
                                        ArrayList<Bean_Attribute> att_array = new ArrayList<Bean_Attribute>();
                                        att_array = array_attribute_main.get(pos);

                                        Log.e("Position", "" + att_array.get(position1).getValue_name());
                                        Log.e("Position", "" + att_array.get(position1).getValue_id());
                                        Log.e("Position", "" + att_array.get(position1).getValue_mrp());


                                        Bean_Value_Selected_Detail bean = new Bean_Value_Selected_Detail();
                                        bean.setValue_id(att_array.get(position1).getValue_id());
                                        bean.setValue_name(att_array.get(position1).getValue_name());
                                        bean.setValue_mrp(att_array.get(position1).getValue_mrp());
                                        array_value.set(finalK, bean);


                                        Log.e("", "ID :- " + array_value.get(finalK).getValue_id());
                                        Log.e("", "Name :- " + array_value.get(finalK).getValue_name());
                                        Log.e("", "MRP :- " + array_value.get(finalK).getValue_mrp());
                                        //  Log.e("", "Product Id :- " + att_array.get(position1).getOption_pro_id());
                                        Log.e("", "Product Code :- " + att_array.get(position1).getValue_product_code().toString());
                                        // tv_pop_pname.setTag(att_array.get(position1).getOption_pro_id());
                                        //  tv_pop_code.setText(" ( " + att_array.get(position1).getValue_product_code().toString() + " )");

                                        if (att_array.get(position1).getValue_image().equalsIgnoreCase("") || att_array.get(position1).getValue_image().equalsIgnoreCase("null")) {


                                        } else {
                                            list_of_images.add(0, att_array.get(position1).getValue_image());
                                            //adapter.notifyDataSetChanged();
                                        }

                                        if (att_array.get(position1).getValue_selling_price().toString().equalsIgnoreCase("0")) {

                                        } else {
                                            tv_pop_mrp.setText(att_array.get(position1).getValue_mrp().toString());
                                            tv_pop_sellingprice.setText("" + att_array.get(position1).getValue_selling_price().toString());

                                            if (att_array.get(position1).getValue_product_code().toString().equalsIgnoreCase("null") || att_array.get(position1).getValue_product_code().toString().equalsIgnoreCase("")) {
                                                Log.e("121212", "" + att_array.get(position1).getValue_product_code().toString());
                                                tv_pop_code.setText("" + tv_product_code.getText().toString() + "");

                                            } else {
                                                Log.e("131313", "" + att_array.get(position1).getValue_product_code().toString());
                                                tv_pop_code.setText(" (" + att_array.get(position1).getValue_product_code().toString() + ")");
                                                //result_holder.tvproduct_code.setText("ABC");

                                            }
                                            tv_pop_sellingprice.setTag(att_array.get(position1).getValue_selling_price().toString());
                                            if (edt_count.getText().toString().equalsIgnoreCase("1")) {
                                                tv_total.setText(att_array.get(position1).getValue_selling_price().toString());


                                            } else {
                                                int counter = Integer.parseInt(edt_count.getText().toString());
                                                float amt = Integer.parseInt(tv_pop_sellingprice.getText().toString());
                                                float total = amt * counter;
                                                float finalValue = (float) (Math.round(total * 100) / 100);
                                                String str = String.format("%.2f", total);
                                                tv_total.setText(str);
                                            }
                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        // TODO Auto-generated method stub

                                    }
                                });

                            }
                                    /*
                             Log.e("",""+"option ID - "+temp_array.get(1) + "List hash ID  - "+arrOptionTypeID.get(1));
                             if(temp_array.get(0).equalsIgnoreCase(arrOptionTypeID.get(0)))
                             {
                                 System.out.println("option ID - "+temp_array.get(p) + "List hash ID  - "+arrOptionTypeID.get(z));
                                 Log.e("------",""+"option ID - "+temp_array.get(p) + "List hash ID  - "+arrOptionTypeID.get(z));
                             }
                             else
                             {
                                 z++;
                                 System.out.println("option ID - "+temp_array.get(p) + "List hash ID  - "+arrOptionTypeID.get(z));
                                 Log.e("", "" + "option ID - " + temp_array.get(p) + "List hash ID  - " + arrOptionTypeID.get(z));
                             }*/
                            /*for (int s = 0; s < arrOptionTypeID.size(); s++) {



                                    Spinner spinner = new Spinner(Product_List.this);

                                    spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                                    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(Product_List.this,
                                            android.R.layout.simple_spinner_dropdown_item, );
                                    spinner.setAdapter(spinnerArrayAdapter);

                                    l_spinner.addView(spinner);

                            }*/


                        }



                        /*LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                        arrOptionType.clear();
                        //Creating Arraylist without duplicate values
                        arrOptionType = new ArrayList<String>(listToSet);

                        Log.e("AAAA", "" + arrOptionType.size());

                        List newList = new ArrayList(new LinkedHashSet(
                                arrOptionType));
                        Iterator it = newList.iterator();
                        while (it.hasNext()) {
                            System.out.println(it.next());

                        }*/


                    } else {
                        //GlobalVariable.CustomToast(Product_List.this, "ALLL NO DATA", getLayoutInflater());
                        if (bean_productOprtions.size() == 0) {
                            l_view_spinner.setVisibility(View.GONE);
                        } else {
                            l_view_spinner.setVisibility(View.VISIBLE);
                        }
                    }


                    dialog.show();

                    String c = edt_count.getText().toString();
                    s = Integer.parseInt(c);
                    minuss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String c1 = edt_count.getText().toString();
                            s = Integer.parseInt(c1);
                            if (s == 0) {
                                //GlobalVariable.CustomToast(Product_List.this, "Its impossible", getLayoutInflater());
                                edt_count.setText("1");
                                tv_total.setText(tv_pop_sellingprice.getTag().toString());

                            } else {

                                s = s - 1;
                                if (s == 0) {
                                    edt_count.setText("1");
                                    tv_total.setText(tv_pop_sellingprice.getTag().toString());
                                } else {
                                    //   edt_count.setText(String.valueOf(s));
                                    edt_count.setText(String.valueOf(s));
                                    int counter = s;
                                    float amt = Float.parseFloat(tv_pop_sellingprice.getTag().toString());
                                    float total = amt * counter;
                                    float finalValue = (float) (Math.round(total * 100) / 100);

                                    String str = String.format("%.2f", total);
                                    tv_total.setText(str);


                                }


                                // Toast.makeText(activity,"Total "+to,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    String c1 = edt_count.getText().toString();
                    s = Integer.parseInt(c1);
                    plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String c1 = edt_count.getText().toString();
                            s = Integer.parseInt(c1);
                            s = s + 1;
                            edt_count.setText(String.valueOf(s));
                            int counter = s;
                            float amt = Float.parseFloat(tv_pop_sellingprice.getTag().toString());
                            float total = amt * counter;
                            float finalValue = (float) (Math.round(total * 100) / 100);

                            String str = String.format("%.2f", total);
                            tv_total.setText(str);

                            // Toast.makeText(getApplicationContext(),"Total "+to,Toast.LENGTH_SHORT).show();
                        }
                    });

                    edt_count.addTextChangedListener(new TextWatcher() {

                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {
                            if (s.length() == 0) { // do your work here }
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                edt_count.setText("1");
                                tv_total.setText(productpricce);
                            }

                        }

                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            // TODO Auto-generated method stub
                            if (edt_count.getText().toString().equals("0")) {
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                edt_count.setText("1");
                                tv_total.setText(productpricce);
                                Log.e("total", productpricce);
                            } else if (edt_count.getText().toString().equals("")) {
                                // Do nothing here
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                edt_count.setText("1");
                                tv_total.setText(productpricce);
                            } else if (Integer.parseInt(edt_count.getText().toString()) == 0) {
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                edt_count.setText("1");
                                tv_total.setText(productpricce);
                            } else if (s.length() != 0) { // do your work here }
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                amount1 = Integer.parseInt(s.toString());

                                Log.e("amount1", amount1 + "" + productpricce);
                                amount1 = Float.parseFloat(productpricce) * amount1;
                                float finalValue = (float) (Math.round(amount1 * 100) / 100);

                                String str = String.format("%.2f", amount1);
                                tv_total.setText(str);


                            } else {
                                String productpricce = tv_pop_sellingprice.getTag().toString();
                                edt_count.setText("1");
                                tv_total.setText(productpricce);
						/*if(txt_count.getText().toString().equalsIgnoreCase("0")){
							txt_amount.setEnabled(false);
						}
						*/

                            }
                        }
                    });

                    buy_cart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //  Globals.CustomToast(BTLProduct_Detail.this, "Item insert in cart", getLayoutInflater());
                            Bean_ProductCart bean = new Bean_ProductCart();
                            String value_id = "";
                            String value_name = "";


                            for (int i = 0; i < array_value.size(); i++) {
                                if (i == 0) {
                                    value_name = array_value.get(i).getValue_name();
                                    value_id = array_value.get(i).getValue_id();
                                } else {
                                    value_id = value_id + ", " + array_value.get(i).getValue_id();
                                    value_name = value_name + ", " + array_value.get(i).getValue_name();

                                }
                            }


                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                            bean.setPro_id(pid);
                            // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                            bean.setPro_cat_id(CategoryID);
                            bean.setPro_Images(bean_product1.get(0).getPro_image());

                            bean.setPro_code(tv_pop_code.getText().toString());
                            Log.e("-- ", "setPro_code " + tv_pop_code.getText().toString());
                            bean.setPro_name(tv_pop_pname.getText().toString());
                            bean.setPro_qty(edt_count.getText().toString());
                            bean.setPro_mrp(tv_pop_mrp.getText().toString().replace("", ""));
                            bean.setPro_sellingprice(tv_pop_sellingprice.getText().toString().replace("", ""));
                            bean.setPro_shortdesc(tv_packof.getText().toString());
                            bean.setPro_Option_id(option_id);
                            Log.e("-- ", "Option Id " + option_id);

                            bean.setPro_Option_name(option_name);
                            Log.e("-- ", "Option Name " + option_name);

                            bean.setPro_Option_value_id(value_id);

                            Log.e("", "Value Name " + value_id);
                            bean.setPro_Option_value_name(value_name);
                            Log.e("", "Value Name " + value_name);

                            bean.setPro_total(tv_total.getText().toString().replace("", ""));

                            db.Add_Product_cart(bean, option_name, value_name);

                            array_value.clear();

                            Toast.makeText(Product_Detail.this, "Product insreted in to cart",
                                    Toast.LENGTH_SHORT).show();
                            //GlobalVariable.CustomToast(Product_Detail.this, "Product insreted in to cart", getLayoutInflater());
                           /* Intent i = new Intent(Product_Detail.this, Product_Detail.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);*/
                            bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();
                            new get_product().execute();

                            dialog.dismiss();
                        }

                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
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

                super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public class CustomPagerAdapter extends PagerAdapter {

        public Object instantiateItem(View collection, final int position) {

            LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.viewpager_row, null);

            ((ViewPager) collection).addView(view, 0);


            ImageView ia = (ImageView) view.findViewById(R.id.img_flipperimag);

            try {

                Picasso.with(getApplicationContext())
                        .load(bean_productImages.get(position)
                                .getPro_Images())
                        .placeholder(R.drawable.boss_blender_min)
                        .error(R.drawable.boss_blender_min)
                        .into(ia);
                //imageloader.DisplayImage(bean_productImages.get(position).getPro_Images(), R.drawable.boss_blender_min, ia);
            } catch (Exception e) {
                Log.e("Error", e.toString());
            }

           /* ia.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(getActivity(), Slider_Product.class);
                    startActivity(i);
                }
            });

            if (position == 0) {
                im_previous.setVisibility(View.GONE);
                im_next.setVisibility(View.VISIBLE);
            } else if (position == array_slider.size() - 1) {
                im_next.setVisibility(View.GONE);
                im_previous.setVisibility(View.VISIBLE);
            }else
            {
                im_previous.setVisibility(View.VISIBLE);
                im_next.setVisibility(View.VISIBLE);
            }
            im_next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (position == 0 || position < (array_slider.size() - 1)) {
                        customviewpager.setCurrentItem(position + 1);
                        Log.e("", "Size :- " + array_slider.size()
                                + " position " + (position + 1));
                    }

                    else if (position == (array_slider.size() - 1)) {
                        Log.e("", "Size :- " + array_slider.size()
                                + " position " + (position + 1));
                    }

                }
            });
            im_previous.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (position == 0) {

                    } else if (position == (array_slider.size() - 1)
                            || position > 0) {
                        customviewpager.setCurrentItem(position - 1);
                        Log.e("", "Size :- " + array_slider.size()
                                + " position " + (position - 1));
                    }
                }
            });*/
			/*
			 * title.setText(bean.get(position).getTitle());
			 *
			 * if (bean.get(position).getImage().equalsIgnoreCase("") ||
			 * bean.get(position).getImage().equalsIgnoreCase("null")) {
			 * Log.e("", "Inside null===" + bean.get(position).getImage());
			 * ia.setImageResource(R.drawable.docicon);
			 *
			 * } else {
			 *
			 * byte [] encodeByte=Base64.decode(bean.get(position).getImage()
			 * ,Base64.DEFAULT); Bitmap
			 * bitmap=BitmapFactory.decodeByteArray(encodeByte, 0,
			 * encodeByte.length); ia.setImageBitmap(bitmap);
			 *
			 * Log.e("", "Inside else===" + bean.get(position).getImage());
			 * imageloader.DisplayImage(bean.get(position).getImage(),
			 * R.drawable.docicon, ia); }
			 */
            // ia.setImageResource(Integer.parseInt(bean.get(position).getImage()));

            return view;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public int getCount() {

            return bean_productImages.size();

        }
    }


    public class ImagenewAdapter extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return bean_productyoutube.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            // result_holder = null;

            result_holder = new ResultHolder();

            if (convertView == null) {

                convertView = vi.inflate(R.layout.imageview_row, null);

            }

            result_holder.img_row = (ImageView) convertView
                    .findViewById(R.id.img_row);

            url = bean_productyoutube.get(position).getPro_youtube_url();
            String setthumbnail = "http://img.youtube.com/vi/" + url + "/1.jpg";
            Log.e("setthumbnail", url + " : " + setthumbnail);
            C.loadImage(getApplicationContext(), setthumbnail, R.drawable.boss_logo_final, result_holder.img_row);

            //imageloader.DisplayImage(setthumbnail, R.drawable.boss_logo_final, );


            result_holder.img_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*AppPrefs app = new AppPrefs(Product_Detail.this);
                    app.setyoutube_api(url);
                    Intent i = new Intent(Product_Detail.this, Video_View_Activity.class);
                    startActivity(i);*/
                }
            });


            return convertView;
        }

    }

    static class ResultHolder {


        ImageView img_row;


    }

    private void setLayout_video(final ArrayList<Product_Youtube> str) {
        // TODO Auto-generated method stub

        l_video_lower.removeAllViews();

        Log.e("str.size", "" + str.size());

        for (int ij = 0; ij < str.size(); ij++) {

            LinearLayout lmain = new LinearLayout(Product_Detail.this);
            //  params.setMargins(7, 7, 7, 7);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            lmain.setPadding(2, 2, 2, 2);

            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    200, 200, 1.0f);
            LinearLayout l1 = new LinearLayout(Product_Detail.this);

            l1.setLayoutParams(par1);
            l1.setPadding(5, 5, 5, 5);
            l1.setOrientation(LinearLayout.VERTICAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(Product_Detail.this);
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 5, 5, 5);

            final LinearLayout l2 = new LinearLayout(Product_Detail.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 5, 5, 5);
            l2.setVisibility(View.GONE);

            count = ij;
            final ImageView img_a = new ImageView(Product_Detail.this);
            img_a.setTag(ij);
            // img_a.setImageResource(str.get(count));
            Log.e("count", "" + count);
            Log.e("img", "" + str.get(count).getPro_youtube_url());
            try {

                url = str.get(count).getPro_youtube_url();
                String setthumbnail = "http://img.youtube.com/vi/" + url + "/0.jpg";
                Log.e("setthumbnail", url + " : " + setthumbnail);
                app.setyoutube_api(str.get(count).getPro_youtube_url());

                Picasso.with(getApplicationContext())
                        .load(setthumbnail)
                        .placeholder(R.drawable.boss_logo_final)
                        .error(R.drawable.boss_logo_final)
                        .into(img_a);
                // imageloader.DisplayImage(setthumbnail, R.drawable.boss_logo_final, img_a);

            } catch (NullPointerException e) {
                Log.e("Error", "" + e);
            }


            par21.setMargins(5, 5, 5, 5);

            img_a.setLayoutParams(par21);
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
            l1.addView(img_a);

            img_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppPrefs app = new AppPrefs(Product_Detail.this);

                    Log.e("url_tonext_page", "" + str.get(Integer.parseInt(img_a.getTag().toString())).getPro_youtube_url());
                    /*Intent i = new Intent(Product_Detail.this, Video_View_Activity.class);
                    app.setyoutube_api(str.get(Integer.parseInt(img_a.getTag().toString())).getPro_youtube_url());
                    startActivity(i);*/
                }
            });
            lmain.addView(l1);

            l_video_lower.addView(lmain);

        }

    }

    public class MyAdapter extends ArrayAdapter<Bean_Attribute> {

        ArrayList<Bean_Attribute> array;
        int k = 0;

        public MyAdapter(Context context, int textViewResourceId, ArrayList<Bean_Attribute> objects) {
            super(context, textViewResourceId, objects);
            this.array = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.textview, parent, false);

            TextView label = (TextView) row.findViewById(R.id.tv_row);
         /*   if(k == 0){
                label.setText("Select "+array.get(position).getOption_name());
                k++;
                position = 0;
            }else {*/
            label.setText(array.get(position).getValue_name());
            /*}*/
            return row;
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {

                loadingView = new ProgressActivity(
                        Product_Detail.this, "");

                loadingView.setCancelable(false);
                loadingView.show();
            } catch (Exception e) {

            }

        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf

            File dir = new File(Environment.getExternalStorageDirectory() + "/BOSS");
            if (dir.exists() && dir.isDirectory()) {
                // do something here
                String extStorageDirectory1 = Environment.getExternalStorageDirectory().toString();
                File folder1 = new File(extStorageDirectory1, "BOSS");
                //  folder1.delete();
                if (folder1.isDirectory()) {
                    String[] children = folder1.list();
                    for (int i = 0; i < children.length; i++) {
                        new File(folder1, children[i]).delete();
                    }
                }
            }


            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "BOSS");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            loadingView.dismiss();

            File file = new File(Environment.getExternalStorageDirectory() + "/BOSS/" + "ProductDetail.pdf");
            PackageManager packageManager = getPackageManager();
            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            testIntent.setType("application/pdf");
            List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);

            return null;
        }


    }


    private void setRefershData() {
    }

}