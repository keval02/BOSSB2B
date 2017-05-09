package com.nivida.bossb2b;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_Filter_Status;
import com.nivida.bossb2b.Bean.Bean_Order_history;
import com.nivida.bossb2b.Bean.Bean_Product;
import com.nivida.bossb2b.Bean.Bean_ProductCart;
import com.nivida.bossb2b.Bean.Bean_ProductFeature;
import com.nivida.bossb2b.Bean.Bean_ProductImage;
import com.nivida.bossb2b.Bean.Bean_ProductOprtion;
import com.nivida.bossb2b.Bean.Bean_ProductStdSpec;
import com.nivida.bossb2b.Bean.Bean_ProductTechSpec;
import com.nivida.bossb2b.Bean.Bean_User_data;
import com.nivida.bossb2b.Bean.Product_Youtube;



public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "bossmanager";

    // Contacts table name
    private static final String TABLE_USERDATA = "userdata";
    private static final String TABLE_PRODUCTDATA = "productdata";
    private static final String TABLE_PRODUCT_CARTDATA = "cartproductdata";
    private static final String TABLE_PRODUCT_FEATUREDATA = "featureproductdata";
    private static final String TABLE_PRODUCT_IMAGES = "productimages";
    private static final String TABLE_PRODUCT_OPTION = "productoption";
    private static final String TABLE_PRODUCT_STDSPEC = "productstdspec";
    private static final String TABLE_PRODUCT_TECHSPEC = "producttechspec";
    private static final String TABLE_PRODUCT_YOUTUBE = "productyoutube";
    private static final String TABLE_FILTER_CATEGORY = "filtercategory";
    private static final String TABLE_FILTER_SUBCATEGORY = "filtersubcategory";
    private static final String TABLE_FILTER_VALUE = "filtervalue";
    private static final String TABLE_ORDER_HISTORY = "order_history";
    private static final String TABLE_ORDER_FILTER_STATUS = "order_status";


    private static final String TABLE_NOTIFICATION = "notification";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ROLE_ID = "role_id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_EMAILID = "email_id";
    private static final String KEY_PHONENO = "phone_no";
    private static final String KEY_FIRST_NAME = "f_name";
    private static final String KEY_LAST_NAME = "l_name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_USERTYPE = "usertype";
    private static final String KEY_LOGIN_WITH = "login_with";
    private static final String KEY_STR_RID = "str_rid";
    private static final String KEY_ADD1 = "add1";
    private static final String KEY_ADD2 = "add2";
    private static final String KEY_ADD3 = "add3";
    private static final String KEY_LANDMARK = "landmark";
    private static final String KEY_PINCODE = "pincode";
    private static final String KEY_STATE_ID = "state_id";
    private static final String KEY_STATE_NAME = "state_name";
    private static final String KEY_CITY_ID = "city_id";
    private static final String KEY_CITY_NAME = "city_name";
    private static final String KEY_STR_RESPONSE = "str_response";


    private static final String KEY_PID = "p_id";
    private static final String KEY_PRO_ID = "pro_id";
    private static final String KEY_PRO_CODE = "pro_code";
    private static final String KEY_PRO_NAME = "pro_name";
    private static final String KEY_PRO_LABEL = "pro_label";
    private static final String KEY_PRO_MRP = "pro_mrp";
    private static final String KEY_PRO_SELLINGPRICE = "pro_sellingprice";
    private static final String KEY_PRO_SHORTDESC = "pro_shortdesc";
    private static final String KEY_PRO_MOREINFO = "pro_moreinfo";
    private static final String KEY_PRO_CAT_ID = "pro_cat_id";
    private static final String KEY_PRO_QTY = "pro_qty";
    private static final String KEY_PRO_IMAGE = "pro_image";

    private static final String KEY_CART_ID = "c_id";
    private static final String KEY_CART_PRO_ID = "c_pro_id";
    private static final String KEY_CART_PRO_CAT_ID = "c_pro_cat_id";
    private static final String KEY_CART_PRO_IMAGES = "c_pro_Images";
    private static final String KEY_CART_PRO_CODE = "c_pro_code";
    private static final String KEY_CART_PRO_NAME = "c_pro_name";
    private static final String KEY_CART_PRO_QTY = "c_pro_qty";
    private static final String KEY_CART_PRO_MRP = "c_pro_mrp";
    private static final String KEY_CART_PRO_SELLING_PRICE = "c_pro_sellingprice";
    private static final String KEY_CART_PRO_SHORTDESC = "c_pro_shortdesc";
    private static final String KEY_CART_PRO_OPTIONID = "c_pro_Option_id";
    private static final String KEY_CART_PRO_OPTION_NAME = "c_pro_Option_name";
    private static final String KEY_CART_PRO_OPTION_VALUEID = "c_pro_Option_value_id";
    private static final String KEY_CART_PRO_OPTION_VALUENAME = "c_pro_Option_value_name";
    private static final String KEY_CART_PRO_TOTAL = "c_pro_total";

    private static final String KEY_FEATURE_ID = "f_id";
    private static final String KEY_FEATURE_PRO_ID = "f_pro_id";
    private static final String KEY_FEATURE_PRO_CAT_ID = "f_pro_cat_id";
    private static final String KEY_FEATURE_PRO_FETURE_ID = "f_pro_feature_id";
    private static final String KEY_FEATURE_NAME = "f_pro_feature_name";
    private static final String KEY_FEATURE_VALUE = "f_pro_feature_value";

    private static final String KEY_IMAGE_ID = "i_id";
    private static final String KEY_IMAGE_PRO_ID = "i_pro_id";
    private static final String KEY_IMAGE_PRO_CAT_ID = "i_pro_cat_id";
    private static final String KEY_IMAGE_PRO_IMAGES = "i_pro_images";


    private static final String KEY_OPTION_ID = "o_id";
    private static final String KEY_OPTION_PRO_ID = "o_pro_id";
    private static final String KEY_OPTION_PRO_CAT_ID = "o_pro_cat_id";
    private static final String KEY_OPTION_PRO_OPTION_ID = "o_pro_Option_id";
    private static final String KEY_OPTION_NAME = "o_pro_Option_name";
    private static final String KEY_OPTION_VALUE_ID = "o_pro_Option_value_id";
    private static final String KEY_OPTION_VALUENAME = "o_pro_Option_value_name";
    private static final String KEY_OPTION_MRP = "o_pro_Option_mrp";
    private static final String KEY_OPTION_SELLINGPRICE = "o_pro_Option_selling_price";
    private static final String KEY_OPTION_PROIMAGE = "o_pro_Option_proimage";
    private static final String KEY_OPTION_PROCODE = "o_pro_Option_procode";


    private static final String KEY_STDSPEC_ID = "s_id";
    private static final String KEY_STDSPEC_PRO_ID = "s_pro_id";
    private static final String KEY_STDSPEC_PRO_CAT_ID = "s_pro_cat_id";
    private static final String KEY_STDSPEC_STDSPEC_ID = "s_pro_stdspec_id";
    private static final String KEY_STDSPEC_NAME = "s_pro_stdspec_name";
    private static final String KEY_STDSPEC_VALUE = "s_pro_stdspec_value";

    private static final String KEY_TECHSPEC_ID = "t_id";
    private static final String KEY_TECHSPEC_PRO_ID = "t_pro_id";
    private static final String KEY_TECHSPEC_PRO_CAT_ID = "t_pro_cat_id";
    private static final String KEY_TECHSPEC_TECHSPEC_ID = "t_pro_techspec_id";
    private static final String KEY_TECHSPEC_NAME = "t_pro_techspec_name";
    private static final String KEY_TECHSPEC_VALUE = "t_pro_techspec_value";

    private static final String KEY_YOUTUBE_ID = "y_id";
    private static final String KEY_YOUTUBE_PRO_ID = "y_pro_id";
    private static final String KEY_YOUTUBE_PRO_CAT_ID = "y_pro_cat_id";
    private static final String KEY_YOUTUBE_URL = "t_pro_youtubeurl";

    private static final String KEY_CAT_F_ID = "cat_f_id";
    private static final String KEY_CAT_F_CID = "cat_f_cid";
    private static final String KEY_CAT_F_NAME = "cat_f_name";

    private static final String KEY_SUBCAT_F_ID = "subcat_f_id";
    private static final String KEY_SUBCAT_F_SID = "subcat_f_sid";
    private static final String KEY_SUBCAT_F_NAME = "subcat_f_name";

    private static final String KEY_VALUE_F_ID = "value_f_id";
    private static final String KEY_VALUE_F_VID = "value_f_vid";
    private static final String KEY_VALUE_F_NAME = "value_f_name";
    private static final String KEY_VALUE_F_OP_ID = "value_f_op_id";


    private final ArrayList<Bean_Product> product_list = new ArrayList<Bean_Product>();
    private final ArrayList<Bean_ProductCart> productcart_list = new ArrayList<Bean_ProductCart>();
    private final ArrayList<Bean_ProductFeature> productFeatures_list = new ArrayList<Bean_ProductFeature>();
    private final ArrayList<Bean_User_data> user_list = new ArrayList<Bean_User_data>();
    private final ArrayList<Bean_ProductImage> productImage_list = new ArrayList<Bean_ProductImage>();
    private final ArrayList<Bean_ProductOprtion> productOption_list = new ArrayList<Bean_ProductOprtion>();
    private final ArrayList<Bean_ProductStdSpec> productSTDSPEC_list = new ArrayList<Bean_ProductStdSpec>();
    private final ArrayList<Bean_ProductTechSpec> productTECHSPEC_list = new ArrayList<Bean_ProductTechSpec>();
    private final ArrayList<Product_Youtube> productYoutue_list = new ArrayList<Product_Youtube>();

    private static final String KEY_ORDER_HISTORY_ID = "id";
    private static final String KEY_ORDER_ID = "order_id";
    private static final String KEY_ORDER_INVOICE_NO = "invoice_no";
    private static final String KEY_ORDER_INVOICE_PREFIX = "invoice_prefix";
    private static final String KEY_ORDER_USER_ID = "user_id";
    private static final String KEY_ORDER_ROLE_ID = "role_id";
    private static final String KEY_ORDER_BILLING_ID = "billing_id";
    private static final String KEY_ORDER_SHIPPING_ID = "shipping_id";
    private static final String KEY_ORDER_FIRST_NAME = "fname";
    private static final String KEY_ORDER_LAST_NAME = "lname";
    private static final String KEY_ORDER_EMAIL = "email";
    private static final String KEY_ORDER_MOBILE = "mobile";
    private static final String KEY_ORDER_PAYMENT_NAME = "payment_name";
    private static final String KEY_ORDER_PAYMENT_ADDRESS = "payment_address";
    private static final String KEY_ORDER_PAYMENT_METHOD = "payment_method";
    private static final String KEY_ORDER_PAYMENT_CODE = "payment_mode";
    private static final String KEY_ORDER_SHIPPING_NAME = "shipping_name";
    private static final String KEY_ORDER_SHIPPING_ADDRESS = "shipping_address";
    private static final String KEY_ORDER_SHIPPING_COST = "shipping_cose";
    private static final String KEY_ORDER_SHIPPING_COMMENT = "shipping_comment";
    private static final String KEY_ORDER_SHIPPING_TOTAL = "shipping_total";
    private static final String KEY_ORDER_SHIPPING_ORDER_STATUS_id = "shipping_order_status_id";
    private static final String KEY_ORDER_SHIPPING_ORDER_PDF = "shipping_order_pdf";
    private static final String KEY_ORDER_SHIPPING_PRODUCT_ID = "shipping_product_id";
    private static final String KEY_ORDER_SHIPPING_PRODUCT_NAME = "shipping_product_name";
    private static final String KEY_ORDER_SHIPPING_PRODUCT_CODE = "shipping_product_code";
    private static final String KEY_ORDER_SHIPPING_PRODUCT_QTY = "shipping_product_qty";
    private static final String KEY_ORDER_SHIPPING_PRODUCT_MRP = "shipping_product_mrp";
    private static final String KEY_ORDER_SHIPPING_SELLING_PRICE = "shipping_selling_price";
    private static final String KEY_ORDER_SHIPPING_OPTION_NAME = "shipping_option_name";
    private static final String KEY_ORDER_SHIPPING_OPTION_VALUE_NAME = "shipping_option_value_name";
    private static final String KEY_ORDER_SHIPPING_OPTION_VALUE_ID = "shipping_option_value_id";
    private static final String KEY_ORDER_SHIPPING_OPTION_ID = "shipping_option_id";
    private static final String KEY_ORDER_SHIPPING_ITEM_TOTAL = "shipping_item_total";
    private static final String KEY_ORDER_DATE = "order_date";

    private static final String KEY_NOTIFICATION_ID = "n_id";
    private static final String KEY_NOTIFICATION_TITLE = "n_title";
    private static final String KEY_NOTIFICATION_MSG = "n_msg";
    private static final String KEY_NOTIFICATION_READ = "n_read";
    private static final String KEY_NOTIFICATION_TIME = "n_time";
    private static final String KEY_NOTIFICATION_USER = "n_user";


    private static final String KEY_FILTER_STATUS_ID = "status_id";
    private static final String KEY_FILTER_STATUS_NAME = "status_name";

    Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    SQLiteDatabase db;
    Cursor cursor;

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERDATA
                + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_ID + " TEXT,"
                + KEY_EMAILID + " TEXT," + KEY_PHONENO + " TEXT" + ","
                + KEY_FIRST_NAME + " TEXT" + "," + KEY_LAST_NAME + " TEXT" + "," + KEY_PASSWORD + " TEXT" + "," + KEY_GENDER + " TEXT" + "," + KEY_USERTYPE + " TEXT" + ","
                + KEY_LOGIN_WITH + " TEXT" + "," + KEY_STR_RID + " TEXT" + ","
                + KEY_ADD1 + " TEXT" + "," + KEY_ADD2 + " TEXT" + "," + KEY_ADD3 + " TEXT" + "," + KEY_LANDMARK + " TEXT" + ","
                + KEY_PINCODE + " TEXT" + "," + KEY_STATE_ID + " TEXT" + "," + KEY_STATE_NAME + " TEXT" + "," + KEY_CITY_ID + " TEXT" + ","
                + KEY_CITY_NAME + " TEXT" + ","
                + KEY_ROLE_ID + " TEXT,"
                + KEY_STR_RESPONSE + " TEXT" + ")";

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCTDATA
                + "(" + KEY_PID + " INTEGER PRIMARY KEY," + KEY_PRO_ID + " TEXT,"
                + KEY_PRO_CODE + " TEXT," + KEY_PRO_NAME + " TEXT" + ","
                + KEY_PRO_LABEL + " TEXT" + "," + KEY_PRO_MRP + " TEXT" + "," + KEY_PRO_SELLINGPRICE + " TEXT" + "," + KEY_PRO_SHORTDESC + " TEXT" + "," + KEY_PRO_MOREINFO + " TEXT" + ","
                + KEY_PRO_CAT_ID + " TEXT" + "," + KEY_PRO_QTY + " TEXT" + "," + KEY_PRO_IMAGE + " TEXT" + ")";

        String CREATE_PRODUCT_CART_TABLE = "CREATE TABLE " + TABLE_PRODUCT_CARTDATA
                + "(" + KEY_CART_ID + " INTEGER PRIMARY KEY," + KEY_CART_PRO_ID + " TEXT,"
                + KEY_CART_PRO_CAT_ID + " TEXT," + KEY_CART_PRO_IMAGES + " TEXT" + ","
                + KEY_CART_PRO_CODE + " TEXT" + "," + KEY_CART_PRO_NAME + " TEXT" + "," + KEY_CART_PRO_QTY + " TEXT" + "," + KEY_CART_PRO_MRP + " TEXT" + "," + KEY_CART_PRO_SELLING_PRICE + " TEXT" + ","
                + KEY_CART_PRO_SHORTDESC + " TEXT" + "," + KEY_CART_PRO_OPTIONID + " TEXT" + ","
                + KEY_CART_PRO_OPTION_NAME + " TEXT" + "," + KEY_CART_PRO_OPTION_VALUEID + " TEXT" + "," + KEY_CART_PRO_OPTION_VALUENAME + " TEXT" + "," + KEY_CART_PRO_TOTAL + " TEXT"
                + ")";

        String CREATE_PRODUCT_FEATURE_TABLE = "CREATE TABLE " + TABLE_PRODUCT_FEATUREDATA
                + "(" + KEY_FEATURE_ID + " INTEGER PRIMARY KEY," + KEY_FEATURE_PRO_ID + " TEXT,"
                + KEY_FEATURE_PRO_CAT_ID + " TEXT," + KEY_FEATURE_PRO_FETURE_ID + " TEXT" + ","
                + KEY_FEATURE_NAME + " TEXT" + "," + KEY_FEATURE_VALUE + " TEXT" + ")";

        String CREATE_PRODUCT_IMAGES_TABLE = "CREATE TABLE " + TABLE_PRODUCT_IMAGES
                + "(" + KEY_IMAGE_ID + " INTEGER PRIMARY KEY," + KEY_IMAGE_PRO_ID + " TEXT,"
                + KEY_IMAGE_PRO_CAT_ID + " TEXT," + KEY_IMAGE_PRO_IMAGES + " TEXT" + ")";

        String CREATE_PRODUCT_OPTION_TABLE = "CREATE TABLE " + TABLE_PRODUCT_OPTION
                + "(" + KEY_OPTION_ID + " INTEGER PRIMARY KEY," + KEY_OPTION_PRO_ID + " TEXT,"
                + KEY_OPTION_PRO_CAT_ID + " TEXT," + KEY_OPTION_PRO_OPTION_ID + " TEXT" + ","
                + KEY_OPTION_NAME + " TEXT" + "," + KEY_OPTION_VALUE_ID + " TEXT" + "," + KEY_OPTION_VALUENAME + " TEXT" + "," + KEY_OPTION_MRP + " TEXT" + "," + KEY_OPTION_SELLINGPRICE + " TEXT" + ","
                + KEY_OPTION_PROIMAGE + " TEXT" + "," + KEY_OPTION_PROCODE + " TEXT" + ")";


        String CREATE_PRODUCT_STDSPEC_TABLE = "CREATE TABLE " + TABLE_PRODUCT_STDSPEC
                + "(" + KEY_STDSPEC_ID + " INTEGER PRIMARY KEY," + KEY_STDSPEC_PRO_ID + " TEXT,"
                + KEY_STDSPEC_PRO_CAT_ID + " TEXT," + KEY_STDSPEC_STDSPEC_ID + " TEXT" + ","
                + KEY_STDSPEC_NAME + " TEXT" + "," + KEY_STDSPEC_VALUE + " TEXT" + ")";


        String CREATE_PRODUCT_TECHSPEC_TABLE = "CREATE TABLE " + TABLE_PRODUCT_TECHSPEC
                + "(" + KEY_TECHSPEC_ID + " INTEGER PRIMARY KEY," + KEY_TECHSPEC_PRO_ID + " TEXT,"
                + KEY_TECHSPEC_PRO_CAT_ID + " TEXT," + KEY_TECHSPEC_TECHSPEC_ID + " TEXT" + ","
                + KEY_TECHSPEC_NAME + " TEXT" + "," + KEY_TECHSPEC_VALUE + " TEXT" + ")";

        String CREATE_PRODUCT_YOUTUBE = "CREATE TABLE " + TABLE_PRODUCT_YOUTUBE
                + "(" + KEY_YOUTUBE_ID + " INTEGER PRIMARY KEY," + KEY_YOUTUBE_PRO_ID + " TEXT,"
                + KEY_YOUTUBE_PRO_CAT_ID + " TEXT," + KEY_YOUTUBE_URL + " TEXT" + ")";


        String CREATE_PRODUCT_CAT_TABLE = "CREATE TABLE " + TABLE_FILTER_CATEGORY
                + "(" + KEY_CAT_F_ID + " INTEGER PRIMARY KEY," + KEY_CAT_F_CID + " TEXT,"
                + KEY_CAT_F_NAME + " TEXT" + ")";

        String CREATE_PRODUCT_SUBCAT_TABLE = "CREATE TABLE " + TABLE_FILTER_SUBCATEGORY
                + "(" + KEY_SUBCAT_F_ID + " INTEGER PRIMARY KEY," + KEY_SUBCAT_F_SID + " TEXT,"
                + KEY_SUBCAT_F_NAME + " TEXT" + ")";

        String CREATE_PRODUCT_VAlUE_TABLE = "CREATE TABLE " + TABLE_FILTER_VALUE
                + "(" + KEY_VALUE_F_ID + " INTEGER PRIMARY KEY," + KEY_VALUE_F_VID + " TEXT,"
                + KEY_VALUE_F_NAME + " TEXT" + "," + KEY_VALUE_F_OP_ID + " TEXT" + ")";


        String CREATE_ORDER_HISTORY = "CREATE TABLE " + TABLE_ORDER_HISTORY
                + "(" + KEY_ORDER_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ORDER_ID + " TEXT,"
                + KEY_ORDER_INVOICE_NO + " TEXT,"
                + KEY_ORDER_INVOICE_PREFIX + " TEXT,"
                + KEY_ORDER_USER_ID + " TEXT,"
                + KEY_ORDER_ROLE_ID + " TEXT,"
                + KEY_ORDER_BILLING_ID + " TEXT,"
                + KEY_ORDER_SHIPPING_ID + " TEXT,"
                + KEY_ORDER_FIRST_NAME + " TEXT,"
                + KEY_ORDER_LAST_NAME + " TEXT,"
                + KEY_ORDER_EMAIL + " TEXT,"
                + KEY_ORDER_MOBILE + " TEXT,"
                + KEY_ORDER_PAYMENT_NAME + " TEXT,"
                + KEY_ORDER_PAYMENT_ADDRESS + " TEXT,"
                + KEY_ORDER_PAYMENT_METHOD + " TEXT,"
                + KEY_ORDER_PAYMENT_CODE + " TEXT,"
                + KEY_ORDER_SHIPPING_NAME + " TEXT,"
                + KEY_ORDER_SHIPPING_ADDRESS + " TEXT,"
                + KEY_ORDER_SHIPPING_COST + " TEXT,"
                + KEY_ORDER_SHIPPING_COMMENT + " TEXT,"
                + KEY_ORDER_SHIPPING_TOTAL + " TEXT,"
                + KEY_ORDER_SHIPPING_ORDER_STATUS_id + " TEXT,"
                + KEY_ORDER_SHIPPING_ORDER_PDF + " TEXT,"
                + KEY_ORDER_SHIPPING_PRODUCT_ID + " TEXT,"
                + KEY_ORDER_SHIPPING_PRODUCT_NAME + " TEXT,"
                + KEY_ORDER_SHIPPING_PRODUCT_CODE + " TEXT,"
                + KEY_ORDER_SHIPPING_PRODUCT_QTY + " TEXT,"
                + KEY_ORDER_SHIPPING_PRODUCT_MRP + " TEXT,"
                + KEY_ORDER_SHIPPING_SELLING_PRICE + " TEXT,"
                + KEY_ORDER_SHIPPING_OPTION_NAME + " TEXT,"
                + KEY_ORDER_SHIPPING_OPTION_VALUE_NAME + " TEXT,"
                + KEY_ORDER_DATE + " TEXT,"
                + KEY_ORDER_SHIPPING_ITEM_TOTAL + " TEXT,"
                + KEY_ORDER_SHIPPING_OPTION_VALUE_ID + " TEXT,"
                + KEY_ORDER_SHIPPING_OPTION_ID + " TEXT" + ")";


        String CREATE_ORDER_STATUS = "CREATE TABLE " + TABLE_ORDER_FILTER_STATUS
                + "(" + KEY_FILTER_STATUS_ID + " INTEGER, "
                + KEY_FILTER_STATUS_NAME + " TEXT" + ")";

        String CREATE_NOTIFICATION = "CREATE TABLE " + TABLE_NOTIFICATION + "(" + KEY_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOTIFICATION_TITLE + " TEXT," + KEY_NOTIFICATION_MSG + " TEXT," + KEY_NOTIFICATION_READ + " INT," + KEY_NOTIFICATION_TIME + " TEXT," + KEY_NOTIFICATION_USER + " TEXT" + ")";


        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_PRODUCT_CART_TABLE);
        db.execSQL(CREATE_PRODUCT_FEATURE_TABLE);
        db.execSQL(CREATE_PRODUCT_IMAGES_TABLE);
        db.execSQL(CREATE_PRODUCT_OPTION_TABLE);
        db.execSQL(CREATE_PRODUCT_STDSPEC_TABLE);
        db.execSQL(CREATE_PRODUCT_TECHSPEC_TABLE);
        db.execSQL(CREATE_PRODUCT_YOUTUBE);
        db.execSQL(CREATE_PRODUCT_CAT_TABLE);
        db.execSQL(CREATE_PRODUCT_SUBCAT_TABLE);
        db.execSQL(CREATE_PRODUCT_VAlUE_TABLE);
        db.execSQL(CREATE_ORDER_HISTORY);
        db.execSQL(CREATE_ORDER_STATUS);
        db.execSQL(CREATE_NOTIFICATION);
        /*Cursor s=db.rawQuery("SELECT * FROM " + DATABASE_NAME + " WHERE type='table'",null);
        if(s.isFirst())
		{
			do {
				Log.e("Table created", ""+s.getString(0));
			}while (s.moveToNext());
		}*/


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        int upgradeTo = oldVersion + 1;
        while (upgradeTo <= newVersion) {
            switch (upgradeTo) {
                case 1:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 2:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 3:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 4:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 5:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 6:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 7:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 8:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 9:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 10:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 11:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
                case 12:
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_CARTDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_FEATUREDATA);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_IMAGES);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_OPTION);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_STDSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_TECHSPEC);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_YOUTUBE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_CATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_SUBCATEGORY);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER_VALUE);
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
                    break;
            }
            upgradeTo++;
        }


        // Create tables again
        onCreate(db);
    }


    public void Add_Contact(Bean_User_data contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USER_ID, contact.getUser_id());
        //Toast.makeText(context, ""+contact.getUser_id(), Toast.LENGTH_SHORT).show();
        Log.e("1313131313", "" + contact.getUser_id());

        values.put(KEY_EMAILID, contact.getEmail_id());
        values.put(KEY_PHONENO, contact.getPhone_no());
        values.put(KEY_FIRST_NAME, contact.getF_name());
        values.put(KEY_LAST_NAME, contact.getL_name());
        values.put(KEY_PASSWORD, contact.getPassword());
        values.put(KEY_GENDER, contact.getGender());
        values.put(KEY_USERTYPE, contact.getUser_type());
        values.put(KEY_LOGIN_WITH, contact.getLogin_with());
        values.put(KEY_STR_RID, contact.getStr_rid());
        values.put(KEY_ADD1, contact.getAdd1());
        values.put(KEY_ADD2, contact.getAdd2());
        values.put(KEY_ADD3, contact.getAdd3());
        values.put(KEY_LANDMARK, contact.getLandmark());
        values.put(KEY_PINCODE, contact.getPincode());
        values.put(KEY_STATE_ID, contact.getState_id());
        values.put(KEY_STATE_NAME, contact.getState_name());
        values.put(KEY_CITY_ID, contact.getCity_id());
        values.put(KEY_CITY_NAME, contact.getCity_name());
        values.put(KEY_ROLE_ID, contact.getRole_id());
        values.put(KEY_STR_RESPONSE, contact.getStr_response());


        // Inserting Row
        db.insert(TABLE_USERDATA, null, values);
        db.close(); // Closing database connection
    }


    public void Add_Product(Bean_Product contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("bean_product1_af0-111", "" + contact.getPro_id());

        ContentValues values = new ContentValues();

        values.put(KEY_PRO_ID, contact.getPro_id());
        values.put(KEY_PRO_CODE, contact.getPro_code());
        values.put(KEY_PRO_NAME, contact.getPro_name());
        values.put(KEY_PRO_LABEL, contact.getPro_label());
        values.put(KEY_PRO_MRP, contact.getPro_mrp());
        values.put(KEY_PRO_SELLINGPRICE, contact.getPro_sellingprice());
        values.put(KEY_PRO_SHORTDESC, contact.getPro_shortdesc());
        values.put(KEY_PRO_MOREINFO, contact.getPro_moreinfo());
        values.put(KEY_PRO_CAT_ID, contact.getPro_cat_id());
        values.put(KEY_PRO_QTY, contact.getPro_qty());
        values.put(KEY_PRO_IMAGE, contact.getPro_image());
        // Inserting Row
        db.insert(TABLE_PRODUCTDATA, null, values);
        db.close(); // Closing database connection
    }

    public void Add_Product_cart(Bean_ProductCart contact){
        Add_Product_cart(contact,"","");
    }

    public void Add_Product_cart(Bean_ProductCart contact,String option){
        Add_Product_cart(contact,option,"");
    }

    public ArrayList<Bean_User_data> Get_Contact() {
        try {
            user_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_USERDATA;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Bean_User_data contact = new Bean_User_data();
                    contact.setId(Integer.parseInt(cursor.getString(0)));

                    contact.setUser_id(cursor.getString(1));
                    contact.setEmail_id(cursor.getString(2));
                    contact.setPhone_no(cursor.getString(3));
                    contact.setF_name(cursor.getString(4));
                    contact.setL_name(cursor.getString(5));
                    contact.setPassword(cursor.getString(6));
                    contact.setGender(cursor.getString(7));
                    contact.setUser_type(cursor.getString(8));
                    contact.setLogin_with(cursor.getString(9));
                    contact.setStr_rid(cursor.getString(10));
                    contact.setAdd1(cursor.getString(11));
                    contact.setAdd2(cursor.getString(12));
                    contact.setAdd3(cursor.getString(13));
                    contact.setLandmark(cursor.getString(14));
                    contact.setPincode(cursor.getString(15));
                    contact.setState_id(cursor.getString(16));
                    contact.setState_name(cursor.getString(17));
                    contact.setCity_id(cursor.getString(18));
                    contact.setCity_name(cursor.getString(19));
                    contact.setStr_response(cursor.getString(20));
                    // Adding contact to list
                    user_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return user_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_contact", "" + e);
        }

        return user_list;
    }

    public void Add_Product_cart(Bean_ProductCart contact,String option, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT " + KEY_CART_PRO_CODE+","+KEY_CART_PRO_QTY+","+KEY_CART_PRO_TOTAL+ " FROM " + TABLE_PRODUCT_CARTDATA + " WHERE " + KEY_CART_PRO_CODE+ " = '" + contact.getPro_code() + "' AND " + KEY_CART_PRO_OPTION_NAME + "='"+option+"' COLLATE NOCASE AND " + KEY_CART_PRO_OPTION_VALUENAME + "='"+value+"' COLLATE NOCASE", null);
            Log.e("db product code", "" + contact.getPro_code());

        Log.e("query","SELECT " + KEY_CART_PRO_CODE +","+KEY_CART_PRO_QTY+","+KEY_CART_PRO_TOTAL+ " FROM " + TABLE_PRODUCT_CARTDATA + " WHERE " + KEY_CART_PRO_CODE+ " = '" + contact.getPro_code() + "' AND " + KEY_CART_PRO_OPTION_NAME + "='"+option+"' COLLATE NOCASE AND " + KEY_CART_PRO_OPTION_VALUENAME + "='"+value+"' COLLATE NOCASE");


        c.moveToFirst();

        Log.e("query count",c.getCount()+"");

        if (c.getCount() > 0) {
            ContentValues values = new ContentValues();

            int quantity=C.getInt(c.getString(1))+C.getInt(contact.getPro_qty());

            float total = Float.parseFloat(c.getString(2)) + Float.parseFloat(contact.getPro_total());


            values.put(KEY_CART_PRO_ID, contact.getPro_id());
            values.put(KEY_CART_PRO_CAT_ID, contact.getPro_cat_id());
            values.put(KEY_CART_PRO_IMAGES, contact.getPro_Images());
            values.put(KEY_CART_PRO_CODE, contact.getPro_code());
            values.put(KEY_CART_PRO_NAME, contact.getPro_name());
            values.put(KEY_CART_PRO_QTY, C.getStr(quantity));
            values.put(KEY_CART_PRO_MRP, contact.getPro_mrp());
            values.put(KEY_CART_PRO_SELLING_PRICE, contact.getPro_sellingprice());
            values.put(KEY_CART_PRO_SHORTDESC, contact.getPro_shortdesc());
            values.put(KEY_CART_PRO_OPTIONID, contact.getPro_Option_id());
            values.put(KEY_CART_PRO_OPTION_NAME, contact.getPro_Option_name());
            values.put(KEY_CART_PRO_OPTION_VALUEID, contact.getPro_Option_value_id());
            values.put(KEY_CART_PRO_OPTION_VALUENAME, contact.getPro_Option_value_name());
            values.put(KEY_CART_PRO_TOTAL, String.valueOf(total));
            db.update(TABLE_PRODUCT_CARTDATA, values, KEY_CART_PRO_CODE +"=? AND "+KEY_CART_PRO_OPTION_NAME+"=? AND "+KEY_CART_PRO_OPTION_VALUENAME+"=?", new String[]{contact.getPro_code(),option,value});
            Log.e("db Update product code", "" + contact.getPro_code());

        } else {

            ContentValues values = new ContentValues();


            values.put(KEY_CART_PRO_ID, contact.getPro_id());
            values.put(KEY_CART_PRO_CAT_ID, contact.getPro_cat_id());
            values.put(KEY_CART_PRO_IMAGES, contact.getPro_Images());
            values.put(KEY_CART_PRO_CODE, contact.getPro_code());
            values.put(KEY_CART_PRO_NAME, contact.getPro_name());
            values.put(KEY_CART_PRO_QTY, contact.getPro_qty());
            values.put(KEY_CART_PRO_MRP, contact.getPro_mrp());
            values.put(KEY_CART_PRO_SELLING_PRICE, contact.getPro_sellingprice());
            values.put(KEY_CART_PRO_SHORTDESC, contact.getPro_shortdesc());
            values.put(KEY_CART_PRO_OPTIONID, contact.getPro_Option_id());
            values.put(KEY_CART_PRO_OPTION_NAME, contact.getPro_Option_name());
            values.put(KEY_CART_PRO_OPTION_VALUEID, contact.getPro_Option_value_id());
            values.put(KEY_CART_PRO_OPTION_VALUENAME, contact.getPro_Option_value_name());
            values.put(KEY_CART_PRO_TOTAL, contact.getPro_total());
            // Inserting Row
            db.insert(TABLE_PRODUCT_CARTDATA, null, values);
            Log.e("db Insert product code", "" + contact.getPro_code());
        }

        c.close();
        db.close(); // Closing database connection
    }

    public void Add_ProductFeature(Bean_ProductFeature contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FEATURE_PRO_ID, contact.getPro_id());
        values.put(KEY_FEATURE_PRO_CAT_ID, contact.getPro_cat_id());
        values.put(KEY_FEATURE_PRO_FETURE_ID, contact.getPro_feature_id());
        values.put(KEY_FEATURE_NAME, contact.getPro_feature_name());
        values.put(KEY_FEATURE_VALUE, contact.getPro_feature_value());

        // Inserting Row
        db.insert(TABLE_PRODUCT_FEATUREDATA, null, values);
        db.close(); // Closing database connection
    }

    public void Add_ProductImage(Bean_ProductImage contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("bean_pro_image_before", "" + contact.getPro_Images());
        values.put(KEY_IMAGE_PRO_ID, contact.getPro_id());
        values.put(KEY_IMAGE_PRO_CAT_ID, contact.getPro_cat_id());
        values.put(KEY_IMAGE_PRO_IMAGES, contact.getPro_Images());

        // Inserting Row
        db.insert(TABLE_PRODUCT_IMAGES, null, values);
        db.close(); // Closing database connection
    }

    public void Add_Product_Option(Bean_ProductOprtion contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_OPTION_PRO_ID, contact.getPro_id());
        values.put(KEY_OPTION_PRO_CAT_ID, contact.getPro_cat_id());
        values.put(KEY_OPTION_PRO_OPTION_ID, contact.getPro_Option_id());
        values.put(KEY_OPTION_NAME, contact.getPro_Option_name());
        values.put(KEY_OPTION_VALUE_ID, contact.getPro_Option_value_id());
        values.put(KEY_OPTION_VALUENAME, contact.getPro_Option_value_name());
        values.put(KEY_OPTION_MRP, contact.getPro_Option_mrp());
        values.put(KEY_OPTION_SELLINGPRICE, contact.getPro_Option_selling_price());
        values.put(KEY_OPTION_PROIMAGE, contact.getPro_Option_proimage());
        values.put(KEY_OPTION_PROCODE, contact.getPro_Option_procode());

        // Inserting Row
        db.insert(TABLE_PRODUCT_OPTION, null, values);
        db.close(); // Closing database connection
    }

    public void Add_ProductStdspec(Bean_ProductStdSpec contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_STDSPEC_PRO_ID, contact.getPro_id());
        values.put(KEY_STDSPEC_PRO_CAT_ID, contact.getPro_cat_id());
        values.put(KEY_STDSPEC_STDSPEC_ID, contact.getPro_Stdspec_id());
        values.put(KEY_STDSPEC_NAME, contact.getPro_Stdspec_name());
        values.put(KEY_STDSPEC_VALUE, contact.getPro_Stdspec_value());

        // Inserting Row
        db.insert(TABLE_PRODUCT_STDSPEC, null, values);
        db.close(); // Closing database connection
    }


    public void Add_ProductTechSpec(Bean_ProductTechSpec contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TECHSPEC_PRO_ID, contact.getPro_id());
        values.put(KEY_TECHSPEC_PRO_CAT_ID, contact.getPro_cat_id());
        values.put(KEY_TECHSPEC_TECHSPEC_ID, contact.getPro_Techspec_id());
        values.put(KEY_TECHSPEC_NAME, contact.getPro_Techspec_name());
        values.put(KEY_TECHSPEC_VALUE, contact.getPro_Techspec_value());

        // Inserting Row
        db.insert(TABLE_PRODUCT_TECHSPEC, null, values);
        db.close(); // Closing database connection
    }

    public void Add_ProductYoutube(Product_Youtube contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_YOUTUBE_PRO_ID, contact.getPro_id());
        values.put(KEY_YOUTUBE_PRO_CAT_ID, contact.getPro_cat_id());
        values.put(KEY_YOUTUBE_URL, contact.getPro_youtube_url());

        // Inserting Row
        db.insert(TABLE_PRODUCT_YOUTUBE, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<Bean_Product> Get_Product() {
        try {
            product_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTDATA;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Bean_Product contact = new Bean_Product();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setPro_id(cursor.getString(1));
                    contact.setPro_code(cursor.getString(2));
                    contact.setPro_name(cursor.getString(3));
                    contact.setPro_label(cursor.getString(4));
                    contact.setPro_mrp(cursor.getString(5));
                    contact.setPro_sellingprice(cursor.getString(6));
                    contact.setPro_shortdesc(cursor.getString(7));
                    contact.setPro_moreinfo(cursor.getString(8));
                    contact.setPro_cat_id(cursor.getString(9));
                    contact.setPro_qty(cursor.getString(10));
                    contact.setPro_image(cursor.getString(11));

                    // Adding contact to list
                    product_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return product_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_product", "" + e);
        }

        return product_list;
    }

    public ArrayList<Bean_ProductCart> Get_Product_Cart() {
        try {
            productcart_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_CARTDATA;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Bean_ProductCart contact = new Bean_ProductCart();
                    contact.setId(Integer.parseInt(cursor.getString(0)));


                    contact.setPro_id(cursor.getString(1));
                    contact.setPro_cat_id(cursor.getString(2));
                    contact.setPro_Images(cursor.getString(3));
                    contact.setPro_code(cursor.getString(4));
                    Log.e("Pro_code", contact.getPro_code().toString());
                    contact.setPro_name(cursor.getString(5));
                    contact.setPro_qty(cursor.getString(6));
                    contact.setPro_mrp(cursor.getString(7));
                    contact.setPro_sellingprice(cursor.getString(8));
                    contact.setPro_shortdesc(cursor.getString(9));
                    contact.setPro_Option_id(cursor.getString(10));
                    contact.setPro_Option_name(cursor.getString(11));
                    contact.setPro_Option_value_id(cursor.getString(12));
                    contact.setPro_Option_value_name(cursor.getString(13));
                    contact.setPro_total(cursor.getString(14));

                    // Adding contact to list
                    productcart_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return productcart_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_cart", "" + e);
        }

        return productcart_list;
    }

    public ArrayList<Bean_ProductFeature> Get_ProductFeature() {
        try {
            productFeatures_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_FEATUREDATA;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    Bean_ProductFeature contact = new Bean_ProductFeature();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setPro_id(cursor.getString(1));
                    contact.setPro_cat_id(cursor.getString(2));
                    contact.setPro_feature_id(cursor.getString(3));
                    contact.setPro_feature_name(cursor.getString(4));
                    contact.setPro_feature_value(cursor.getString(5));

                    // Adding contact to list
                    productFeatures_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return productFeatures_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_productfeture", "" + e);
        }

        return productFeatures_list;
    }

    public ArrayList<Bean_ProductImage> Get_ProductImage() {
        try {
            productImage_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_IMAGES;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    Bean_ProductImage contact = new Bean_ProductImage();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setPro_id(cursor.getString(1));
                    contact.setPro_cat_id(cursor.getString(2));
                    contact.setPro_Images(cursor.getString(3));

                    // Adding contact to list
                    productImage_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return productImage_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_productimages", "" + e);
        }

        return productImage_list;
    }

    public ArrayList<Bean_ProductOprtion> Get_Product_Option() {
        productOption_list.clear();
        try {
            productOption_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_OPTION;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Bean_ProductOprtion contact = new Bean_ProductOprtion();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setPro_id(cursor.getString(1));
                    contact.setPro_cat_id(cursor.getString(2));
                    contact.setPro_Option_id(cursor.getString(3));
                    contact.setPro_Option_name(cursor.getString(4));
                    contact.setPro_Option_value_id(cursor.getString(5));
                    contact.setPro_Option_value_name(cursor.getString(6));
                    contact.setPro_Option_mrp(cursor.getString(7));
                    contact.setPro_Option_selling_price(cursor.getString(8));
                    contact.setPro_Option_proimage(cursor.getString(9));
                    contact.setPro_Option_procode(cursor.getString(10));

                    // Adding contact to list
                    productOption_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return productOption_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_opton", "" + e);
        }

        return productOption_list;
    }

    public ArrayList<Bean_ProductStdSpec> Get_Productstdspec() {
        try {
            productSTDSPEC_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_STDSPEC;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    Bean_ProductStdSpec contact = new Bean_ProductStdSpec();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setPro_id(cursor.getString(1));
                    contact.setPro_cat_id(cursor.getString(2));
                    contact.setPro_Stdspec_id(cursor.getString(3));
                    contact.setPro_Stdspec_name(cursor.getString(4));
                    contact.setPro_Stdspec_value(cursor.getString(5));

                    // Adding contact to list
                    productSTDSPEC_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return productSTDSPEC_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_productstdspec", "" + e);
        }

        return productSTDSPEC_list;
    }

    public ArrayList<Bean_ProductTechSpec> Get_Producttechspec() {
        try {
            productTECHSPEC_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_TECHSPEC;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    Bean_ProductTechSpec contact = new Bean_ProductTechSpec();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setPro_id(cursor.getString(1));
                    contact.setPro_cat_id(cursor.getString(2));
                    contact.setPro_Techspec_id(cursor.getString(3));
                    contact.setPro_Techspec_name(cursor.getString(4));
                    contact.setPro_Techspec_value(cursor.getString(5));

                    // Adding contact to list
                    productTECHSPEC_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return productTECHSPEC_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_producttechspec", "" + e);
        }

        return productTECHSPEC_list;
    }

    public void Add_Filter_Status(ArrayList<Bean_Filter_Status> contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < contact.size(); i++) {
            ContentValues values = new ContentValues();

            values.put(KEY_FILTER_STATUS_ID, contact.get(i).getId());
            values.put(KEY_FILTER_STATUS_NAME, contact.get(i).getFilter_name());


            db.insert(TABLE_ORDER_FILTER_STATUS, null, values);
        }
        db.close(); // Closing database connection
    }


    public ArrayList<Bean_Order_history> get_order_history(String order_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Bean_Order_history> array_product_cart = new ArrayList<Bean_Order_history>();
        try {
            Cursor c = db.rawQuery("SELECT "
                    + KEY_ORDER_ID + ", "
                    + KEY_ORDER_INVOICE_NO + ", "
                    + KEY_ORDER_INVOICE_PREFIX + ", "
                    + KEY_ORDER_USER_ID + ", "
                    + KEY_ORDER_ROLE_ID + ", "
                    + KEY_ORDER_BILLING_ID + ", "
                    + KEY_ORDER_SHIPPING_ID + ", "
                    + KEY_ORDER_FIRST_NAME + ", "
                    + KEY_ORDER_LAST_NAME + ", "
                    + KEY_ORDER_EMAIL + ", "
                    + KEY_ORDER_MOBILE + ", "
                    + KEY_ORDER_PAYMENT_NAME + ", "
                    + KEY_ORDER_PAYMENT_ADDRESS + ", "
                    + KEY_ORDER_PAYMENT_METHOD + ", "
                    + KEY_ORDER_PAYMENT_CODE + ", "
                    + KEY_ORDER_SHIPPING_NAME + ", "
                    + KEY_ORDER_SHIPPING_ADDRESS + ", "
                    + KEY_ORDER_SHIPPING_COST + ", "
                    + KEY_ORDER_SHIPPING_COMMENT + ", "
                    + KEY_ORDER_SHIPPING_TOTAL + ", "
                    + KEY_ORDER_SHIPPING_ORDER_STATUS_id + ", "
                    + KEY_ORDER_SHIPPING_ORDER_PDF + ", "
                    + KEY_ORDER_SHIPPING_PRODUCT_ID + ", "
                    + KEY_ORDER_SHIPPING_PRODUCT_NAME + ", "
                    + KEY_ORDER_SHIPPING_PRODUCT_CODE + ", "
                    + KEY_ORDER_SHIPPING_PRODUCT_QTY + ", "
                    + KEY_ORDER_SHIPPING_SELLING_PRICE + ", "
                    + KEY_ORDER_SHIPPING_OPTION_NAME + ", "
                    + KEY_ORDER_SHIPPING_OPTION_VALUE_NAME + ", "
                    + KEY_ORDER_DATE + ", "
                    + KEY_ORDER_SHIPPING_ITEM_TOTAL + ", "
                    + KEY_ORDER_SHIPPING_OPTION_VALUE_ID + ", "
                    + KEY_ORDER_SHIPPING_OPTION_ID + " " +
                    " FROM " + TABLE_ORDER_HISTORY + " WHERE " + KEY_ORDER_ID + " = '" + order_id + "'", null);

            c.moveToFirst();


            if (c.getCount() > 0) {
                do {
                    Bean_Order_history bean = new Bean_Order_history();

                    bean.setOrder_id(c.getString(0));
                    bean.setInvoice_no(c.getString(1));
                    bean.setInvoice_prefix(c.getString(2));
                    bean.setUser_id(c.getString(3));
                    bean.setRole_id(c.getString(4));
                    bean.setBilling_address_id(c.getString(5));
                    bean.setShipping_address_id(c.getString(6));
                    bean.setFirst_name(c.getString(7));
                    bean.setLast_name(c.getString(8));
                    bean.setEmail(c.getString(9));
                    bean.setMobile(c.getString(10));
                    bean.setPayment_name(c.getString(11));
                    bean.setPayment_address(c.getString(12));
                    bean.setPayment_method(c.getString(13));
                    bean.setPayment_code(c.getString(14));
                    bean.setShipping_name(c.getString(15));
                    bean.setShipping_address(c.getString(16));
                    bean.setShipping_cost(c.getString(17));
                    bean.setComment(c.getString(18));
                    bean.setTotal(c.getString(19));
                    bean.setOrder_status_is(c.getString(20));
                    bean.setOrder_pdf(c.getString(21));
                    bean.setProduct_id(c.getString(22));
                    bean.setProduct_name(c.getString(23));
                    bean.setProduct_code(c.getString(24));
                    bean.setProduct_qty(c.getString(25));
                    bean.setProduct_selling_price(c.getString(26));
                    bean.setProduct_option_name(c.getString(27));
                    bean.setProduct_value_name(c.getString(28));
                    bean.setOrder_date(c.getString(29));
                    bean.setProduct_total(c.getString(30));
                    bean.setProduct_value_id(c.getString(31));
                    bean.setProduct_option_id(c.getString(32));

                    array_product_cart.add(bean);
                } while (c.moveToNext());
            } else {
                array_product_cart.clear();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }


        db.close();

        return array_product_cart;
    }



    public ArrayList<Bean_Filter_Status> get_order_history_filter_status() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Bean_Filter_Status> array_filter = new ArrayList<Bean_Filter_Status>();
        Cursor c = db.query(TABLE_ORDER_FILTER_STATUS, null, null, null, null, null, null);
        //Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ORDER_FILTER_STATUS, null, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                Bean_Filter_Status bean = new Bean_Filter_Status();
                bean.setId(c.getString(0));
                bean.setFilter_name(c.getString(1));
                array_filter.add(bean);
            } while (c.moveToNext());
        } else {
            array_filter.clear();
        }
        c.close();
        db.close();
        return array_filter;
    }

    public void Add_ORDER_HISTORY(Bean_Order_history contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(KEY_ORDER_ID, contact.getOrder_id());

        values.put(KEY_ORDER_INVOICE_NO, contact.getInvoice_no());
        values.put(KEY_ORDER_INVOICE_PREFIX, contact.getInvoice_prefix());
        values.put(KEY_ORDER_USER_ID, contact.getUser_id());
        values.put(KEY_ORDER_ROLE_ID, contact.getRole_id());
        values.put(KEY_ORDER_BILLING_ID, contact.getBilling_address_id());
        values.put(KEY_ORDER_SHIPPING_ID, contact.getShipping_address_id());
        values.put(KEY_ORDER_FIRST_NAME, contact.getFirst_name());
        values.put(KEY_ORDER_LAST_NAME, contact.getLast_name());
        values.put(KEY_ORDER_EMAIL, contact.getEmail());
        values.put(KEY_ORDER_MOBILE, contact.getMobile());
        values.put(KEY_ORDER_PAYMENT_NAME, contact.getPayment_name());
        values.put(KEY_ORDER_PAYMENT_ADDRESS, contact.getPayment_address());
        values.put(KEY_ORDER_PAYMENT_METHOD, contact.getPayment_method());
        values.put(KEY_ORDER_PAYMENT_CODE, contact.getPayment_code());
        values.put(KEY_ORDER_SHIPPING_NAME, contact.getShipping_name());
        values.put(KEY_ORDER_SHIPPING_ADDRESS, contact.getShipping_address());
        values.put(KEY_ORDER_SHIPPING_COST, contact.getShipping_cost());
        values.put(KEY_ORDER_SHIPPING_COMMENT, contact.getComment());
        values.put(KEY_ORDER_SHIPPING_TOTAL, contact.getTotal());
        values.put(KEY_ORDER_SHIPPING_ORDER_STATUS_id, contact.getOrder_status_is());
        values.put(KEY_ORDER_SHIPPING_ORDER_PDF, contact.getOrder_pdf());
        values.put(KEY_ORDER_SHIPPING_PRODUCT_ID, contact.getProduct_id());
        values.put(KEY_ORDER_SHIPPING_PRODUCT_NAME, contact.getProduct_name());
        values.put(KEY_ORDER_SHIPPING_PRODUCT_CODE, contact.getProduct_code());
        values.put(KEY_ORDER_SHIPPING_PRODUCT_QTY, contact.getProduct_qty());
        values.put(KEY_ORDER_SHIPPING_PRODUCT_MRP, "");
        values.put(KEY_ORDER_SHIPPING_SELLING_PRICE, contact.getProduct_selling_price());
        values.put(KEY_ORDER_SHIPPING_OPTION_NAME, contact.getProduct_option_name());
        values.put(KEY_ORDER_SHIPPING_OPTION_VALUE_NAME, contact.getProduct_value_name());
        values.put(KEY_ORDER_DATE, contact.getOrder_date());

        values.put(KEY_ORDER_SHIPPING_ITEM_TOTAL, contact.getProduct_total());

        // Inserting Row
        db.insert(TABLE_ORDER_HISTORY, null, values);
        db.close(); // Closing database connection
    }




    public ArrayList<Product_Youtube> Get_ProductYoutube() {
        try {
            productYoutue_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_YOUTUBE;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    Product_Youtube contact = new Product_Youtube();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setPro_id(cursor.getString(1));
                    contact.setPro_cat_id(cursor.getString(2));
                    contact.setPro_youtube_url(cursor.getString(3));

                    // Adding contact to list
                    productYoutue_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return productYoutue_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("productYoutue_list", "" + e);
        }

        return productYoutue_list;
    }


	/*public int Update_User_second(String add1, String add2, String add3,
                                  String landmark, String pincode, String state_id, String state_name,String city_id,
								  String city_name,String st_responce,int pid,String user_id) {

		String countQuery = "UPDATE " + TABLE_USERDATA + " SET " + KEY_ADD1 + " = "
				+ "\"" + add1 + "\"" + " , " + KEY_ADD2 + " = " + "\"" + add2
				+ "\"" + "," + KEY_ADD3 + " = " + "\"" + add3 + "\""
				+ "," + KEY_PINCODE + " = " + "\"" + pincode + "\"" + ","
				+ KEY_LANDMARK + " = " + "\"" + landmark + "\"" + "," + KEY_STATE_ID
				+ " = " + "\"" + state_id + "\"" + "," + KEY_STATE_NAME
				+ " = " + "\"" + state_name + "\"" + ", "+ KEY_CITY_ID + " = " + "\""
				+ city_id + "\"" + ", "+ KEY_CITY_NAME + " = " + "\""
				+ city_name + "\"" + ", "+ KEY_STR_RESPONSE + " = " + "\""
				+ st_responce + "\"" + ", "+ KEY_USER_ID + " = " + "\""
				+ user_id + "\"" + " where " + KEY_ID + " = " + "" + pid
				+ "\n";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;

	}*/

    public int Update_User_second(String add1, String add2, String add3,
                                  String landmark, String pincode, String state_id, String state_name, String city_id,
                                  String city_name, String st_responce, int pid, String user_id) {

        String countQuery = "UPDATE " + TABLE_USERDATA + " SET " + KEY_ADD1 + " = "
                + "\"" + add1 + "\"" + " , " + KEY_ADD2 + " = " + "\"" + add2
                + "\"" + "," + KEY_ADD3 + " = " + "\"" + add3 + "\""
                + "," + KEY_PINCODE + " = " + "\"" + pincode + "\"" + ","
                + KEY_LANDMARK + " = " + "\"" + landmark + "\"" + "," + KEY_STATE_ID
                + " = " + "\"" + state_id + "\"" + "," + KEY_STATE_NAME
                + " = " + "\"" + state_name + "\"" + ", " + KEY_CITY_ID + " = " + "\""
                + city_id + "\"" + ", " + KEY_CITY_NAME + " = " + "\""
                + city_name + "\"" + ", " + KEY_STR_RESPONSE + " = " + "\""
                + st_responce + "\"" + ", " + KEY_USER_ID + " = " + "\""
                + user_id + "\"" + " where " + KEY_ID + "=" + "\"" + pid
                + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;

    }

    /*public int Update_User_second(String add1, String add2, String add3,
                              String landmark, String pincode, String state_id, String state_name,String city_id,
                              String city_name,String st_responce,int pid,String user_id) {

        String countQuery = "UPDATE " + TABLE_USERDATA + " SET " + KEY_ADD1 + " = "
                + "\"" + add1 + "\"" + " , " + KEY_ADD2 + " = " + "\"" + add2
                + "\"" + "," + KEY_ADD3 + " = " + "\"" + add3 + "\""
                + "," + KEY_PINCODE + " = " + "\"" + pincode + "\"" + ","
                + KEY_LANDMARK + " = " + "\"" + landmark + "\"" + "," + KEY_STATE_ID
                + " = " + "\"" + state_id + "\"" + "," + KEY_STATE_NAME
                + " = " + "\"" + state_name + "\"" + ", "+ KEY_CITY_ID + " = " + "\""
                + city_id + "\"" + ", "+ KEY_CITY_NAME + " = " + "\""
                + city_name + "\"" + ", "+ KEY_STR_RESPONSE + " = " + "\""
                + st_responce + "\"" + ", "+ KEY_USER_ID + " = " + "\""
                + user_id + "\"" + " where " + KEY_ID + "=" + "'" + pid
                + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;

    }
*/
    public int Update_User_first(String email_id, String phoneno, String f_name,
                                 String l_name, String password, String gender, String login_with, String str_rid, int pid, String usertype) {

        String countQuery = "UPDATE " + TABLE_USERDATA + " SET " + KEY_EMAILID + " = "
                + "\"" + email_id + "\"" + " , " + KEY_PHONENO + " = " + "\"" + phoneno
                + "\"" + "," + KEY_FIRST_NAME + " = " + "\"" + f_name + "\""
                + "," + KEY_LAST_NAME + " = " + "\"" + l_name + "\"" + ","
                + KEY_PASSWORD + " = " + "\"" + password + "\"" + "," + KEY_GENDER
                + " = " + "\"" + gender + "\"" + "," + KEY_LOGIN_WITH
                + " = " + "\"" + login_with + "\"" + ", " + KEY_STR_RID + " = " + "\""
                + str_rid + "\"" + ", " + KEY_USERTYPE + "=" + "\"" + usertype + "\"" + " where " + KEY_ID + "=" + "\"" + pid
                + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;

    }


    public int Update_User_edit(String phoneno, String f_name, String l_name, String gender, int pid) {

        String countQuery = "UPDATE " + TABLE_USERDATA + " SET " + KEY_PHONENO + " = " + "\"" + phoneno
                + "\"" + "," + KEY_FIRST_NAME + " = " + "\"" + f_name + "\""
                + "," + KEY_LAST_NAME + " = " + "\"" + l_name + "\"" + "," + KEY_GENDER
                + " = " + "\"" + gender + "\"" + " where " + KEY_ID + "=" + "\"" + pid
                + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;

    }


    public void Delete_user_main(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERDATA, KEY_ID + "=" + id, null);
        db.close();

    }

    public void Logout() {
        SQLiteDatabase db = this.getWritableDatabase();
        // context.deleteDatabase(DATABASE_NAME);
        db.execSQL("delete from " + TABLE_USERDATA);

    }


    public void Delete_ALL_table() {
        SQLiteDatabase db = this.getWritableDatabase();
        // context.deleteDatabase(DATABASE_NAME);

        //db.execSQL("delete from " + TABLE_USERDATA);
        db.execSQL("delete from " + TABLE_PRODUCTDATA);
        //db.execSQL("delete from " + TABLE_PRODUCT_CARTDATA);
        db.execSQL("delete from " + TABLE_PRODUCT_FEATUREDATA);
        db.execSQL("delete from " + TABLE_PRODUCT_IMAGES);
        db.execSQL("delete from " + TABLE_PRODUCT_OPTION);
        db.execSQL("delete from " + TABLE_PRODUCT_STDSPEC);
        db.execSQL("delete from " + TABLE_PRODUCT_TECHSPEC);
        db.execSQL("delete from " + TABLE_PRODUCT_YOUTUBE);


    }

    public void clear_cart() {
        SQLiteDatabase db = this.getWritableDatabase();
        // context.deleteDatabase(DATABASE_NAME);
        db.execSQL("delete from " + TABLE_PRODUCT_CARTDATA);

    }

    public void LogoutFilter() {
        SQLiteDatabase db = this.getWritableDatabase();
        // context.deleteDatabase(DATABASE_NAME);
        db.execSQL("delete from " + TABLE_FILTER_CATEGORY);
        db.execSQL("delete from " + TABLE_FILTER_SUBCATEGORY);
        db.execSQL("delete from " + TABLE_FILTER_VALUE);

    }

    public void LogoutSubcategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        // context.deleteDatabase(DATABASE_NAME);

        db.execSQL("delete from " + TABLE_FILTER_SUBCATEGORY);


    }


    public void Delete_user_table() {
        SQLiteDatabase db = this.getWritableDatabase();
        // context.deleteDatabase(DATABASE_NAME);

        db.execSQL("delete from " + TABLE_USERDATA);


    }

    public void Delete_Cat(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FILTER_CATEGORY, KEY_CAT_F_ID + "=" + id, null);
        db.close();

    }

    public void Delete_SubCat(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FILTER_SUBCATEGORY, KEY_SUBCAT_F_ID + "=" + id, null);
        db.close();

    }

    public void Delete_Value(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FILTER_VALUE, KEY_VALUE_F_ID + "=" + id, null);
        db.close();

    }

    public void delete_product_from_cart_list(String Id,String option, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            db.delete(TABLE_PRODUCT_CARTDATA,KEY_CART_PRO_CODE+"=? AND "+KEY_CART_PRO_OPTION_NAME+"=? AND "+KEY_CART_PRO_OPTION_VALUENAME+"=?",new String[]{Id,option,value});
            //db.execSQL("DELETE FROM " + TABLE_PRODUCT_CARTDATA + " WHERE " + KEY_CART_PRO_CODE + " = '" + Id + "'");
            Log.e("", "" + "DELETE FROM " + TABLE_PRODUCT_CARTDATA + " WHERE " + KEY_CART_PRO_CODE + " = " + Id);
            System.out.println("" + "DELETE FROM " + TABLE_PRODUCT_CARTDATA + " WHERE " + KEY_CART_PRO_CODE + " = " + Id);
            //db.delete(TABLE_PRODUCT_CARTDATA, KEY_CART_PRO_ID+"="+Id, null);
        } catch (Exception e) {

        }
        db.close();
    }

    public void update_product_from_cart(String id, String total_price, String qty,String option, String value) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {

            ContentValues values = new ContentValues();
            values.put(KEY_CART_PRO_QTY, qty);
            values.put(KEY_CART_PRO_TOTAL, total_price);
            db.update(TABLE_PRODUCT_CARTDATA, values, " " + KEY_CART_PRO_ID + " = '" + id + "' AND " + KEY_CART_PRO_OPTION_NAME + "='"+option+"' COLLATE NOCASE AND " + KEY_CART_PRO_OPTION_VALUENAME + "='"+value+"' COLLATE NOCASE", null);
        } catch (Exception e) {
            System.out.println("" + e);
        }
        db.close();
    }

    public ArrayList<Bean_ProductCart> is_product_in_cart(String pro_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Product_code_main = pro_code;
        ArrayList<Bean_ProductCart> array_product_cart = new ArrayList<Bean_ProductCart>();
        try {
            Cursor c = db.rawQuery("SELECT "
                    + KEY_CART_ID + ", "
                    + KEY_CART_PRO_ID + ", "
                    + KEY_CART_PRO_CAT_ID + ", "
                    + KEY_CART_PRO_IMAGES + ", "
                    + KEY_CART_PRO_CODE + ", "
                    + KEY_CART_PRO_NAME + ", "
                    + KEY_CART_PRO_QTY + ", "
                    + KEY_CART_PRO_MRP + ", "
                    + KEY_CART_PRO_SELLING_PRICE + ", "
                    + KEY_CART_PRO_SHORTDESC + ", "
                    + KEY_CART_PRO_OPTIONID + ", "
                    + KEY_CART_PRO_OPTION_NAME + ", "
                    + KEY_CART_PRO_OPTION_VALUEID + ", "
                    + KEY_CART_PRO_OPTION_VALUENAME + ", "
                    + KEY_CART_PRO_TOTAL + " " +
                    " FROM " + TABLE_PRODUCT_CARTDATA + " WHERE " + KEY_CART_PRO_CODE + "='" + Product_code_main + "'", null);
            Log.e("", "SELECT "
                    + KEY_CART_ID + ", "
                    + KEY_CART_PRO_ID + ", "
                    + KEY_CART_PRO_CAT_ID + ", "
                    + KEY_CART_PRO_IMAGES + ", "
                    + KEY_CART_PRO_CODE + ", "
                    + KEY_CART_PRO_NAME + ", "
                    + KEY_CART_PRO_QTY + ", "
                    + KEY_CART_PRO_MRP + ", "
                    + KEY_CART_PRO_SELLING_PRICE + ", "
                    + KEY_CART_PRO_SHORTDESC + ", "
                    + KEY_CART_PRO_OPTIONID + ", "
                    + KEY_CART_PRO_OPTION_NAME + ", "
                    + KEY_CART_PRO_OPTION_VALUEID + ", "
                    + KEY_CART_PRO_OPTION_VALUENAME + ", "
                    + KEY_CART_PRO_TOTAL + " " +
                    " FROM " + TABLE_PRODUCT_CARTDATA + " WHERE " + KEY_CART_PRO_CODE + "=' " + Product_code_main + "'");
            c.moveToFirst();
            if (c.getCount() > 0) {
                do {
                    Bean_ProductCart bean = new Bean_ProductCart();
                    bean.setId(c.getInt(0));
                    bean.setPro_id(c.getString(1));
                    bean.setPro_cat_id(c.getString(2));
                    bean.setPro_Images(c.getString(3));
                    bean.setPro_code(c.getString(4));
                    bean.setPro_name(c.getString(5));
                    bean.setPro_qty(c.getString(6));
                    bean.setPro_mrp(c.getString(7));
                    bean.setPro_sellingprice(c.getString(8));
                    bean.setPro_shortdesc(c.getString(9));
                    bean.setPro_Option_id(c.getString(10));
                    bean.setPro_Option_name(c.getString(11));
                    bean.setPro_Option_value_id(c.getString(12));
                    bean.setPro_Option_value_name(c.getString(13));
                    bean.setPro_total(c.getString(14));
                    array_product_cart.add(bean);
                } while (c.moveToNext());
            } else {
                array_product_cart.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("ABABABA", "" + array_product_cart.size());
        db.close();
        return array_product_cart;
    }

    public void Delete_Order_histroy() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER_HISTORY, null, null);
        db.delete(TABLE_ORDER_FILTER_STATUS, null, null);
        db.close();
    }

    public int countNotification(String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = 0;

        Cursor cursor = db.query(TABLE_NOTIFICATION, new String[]{KEY_NOTIFICATION_ID}, KEY_NOTIFICATION_READ + "=? AND " + KEY_NOTIFICATION_USER + "=?", new String[]{C.getStr(0), user}, null, null, null);

        count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }

    public void setReadNotication(String user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NOTIFICATION_READ, 1);

        db.update(TABLE_NOTIFICATION, cv, KEY_NOTIFICATION_USER + "=?", new String[]{user});
        db.close();
    }

    public void ClearNotification() {
        //This function we were using to clear notification on logout but now as to delete notification only on
        //clear button click as per userid, we are not using this function...Thank you.....:)-
        //Aksharbrahm Chintak Patel
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_NOTIFICATION,null,null);
        db.close();
    }

    public void deleteNotification(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTIFICATION, KEY_NOTIFICATION_USER + "=? OR "+KEY_NOTIFICATION_USER+"=?", new String[]{userid,String.valueOf(0)});
        db.close();
    }


}
