package com.nivida.bossb2b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nivida.bossb2b.Bean.Bean_Categeory;
import com.nivida.bossb2b.Bean.Bean_Invoice_List;
import com.nivida.bossb2b.Bean.Bean_Set_Product_Categeory;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Keval Shukla on 28-11-2016.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "BOSSB2B";
    private static final int DB_VER = 1;


    private static final String TABLE_INVOICE = "invoice_products";


    public static final String category_id = "categeory_id";
    public static final String categeory_name = "categeory_name";
    public static final String parent_id = "subcategeory_id";
    public static final String parent_name = "subcategeory_name";
    public static final String product_id = "product_id";
    public static final String product_code = "product_code";
    public static final String product_name = "product_name";
    public static final String Quantity = "quantity";
    public static final String mrp = "product_price";
    public static final String selling_price = "product_selling_price";
    public static final String total_price = "total_price";





    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_INVOICE + "(" + category_id + " TEXT, " + categeory_name + " TEXT , " + parent_id + " TEXT , " + parent_name + " TEXT , " + product_id + " TEXT , " + product_name + " TEXT , " + product_code + " TEXT , " + Quantity + " TEXT , " + mrp + " TEXT , " + selling_price + " TEXT , " + total_price + " TEXT )";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICE);

        onCreate(db);


    }

    public void addToCart(List<Bean_Set_Product_Categeory> detailList) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < detailList.size(); i++) {

            Bean_Set_Product_Categeory detail = detailList.get(i);

            Cursor c = db.rawQuery("SELECT " + product_code + "," + Quantity + "," + total_price + " FROM " + TABLE_INVOICE + " WHERE " + product_code + " = '" + detail.getCode() + "' ", null);
            Log.e("db product code", "" + detail.getCode());

            Log.e("query", "SELECT " + product_code + "," + Quantity + "," + total_price + " FROM " + TABLE_INVOICE + " WHERE " + product_code + " = '" + detail.getCode() + "' ");


            c.moveToFirst();

            Log.e("query count", c.getCount() + "");
            if (c.getCount() > 0) {
                ContentValues cv = new ContentValues();

                int quantity = C.getInt(c.getString(1)) + C.getInt(String.valueOf(detail.getQuantity()));

                float total = Float.parseFloat(c.getString(2)) + Float.parseFloat(String.valueOf(detail.getTotal_price()));


                cv.put(category_id, detail.getCategeory_id());
                cv.put(categeory_name, detail.getCategeory_name());
                cv.put(parent_id, detail.getParent_id());
                cv.put(parent_name, detail.getParent_name());
                cv.put(product_id, detail.getId());
                cv.put(product_name, detail.getName());
                cv.put(product_code, detail.getCode());
                cv.put(Quantity, String.valueOf(detail.getQuantity()));
                cv.put(mrp, detail.getProduct_mrp());
                cv.put(selling_price, detail.getProduct_selling_price());
                cv.put(total_price, detail.getTotal_price());

                db.update(TABLE_INVOICE, cv, product_code + "=?", new String[]{detail.getCode()});

            } else {

                ContentValues cv = new ContentValues();
                cv.put(category_id, detail.getCategeory_id());
                cv.put(categeory_name, detail.getCategeory_name());
                cv.put(parent_id, detail.getParent_id());
                cv.put(parent_name, detail.getParent_name());
                cv.put(product_id, detail.getId());
                cv.put(product_name, detail.getName());
                cv.put(product_code, detail.getCode());
                cv.put(Quantity, String.valueOf(detail.getQuantity()));
                cv.put(mrp, detail.getProduct_mrp());
                cv.put(selling_price, detail.getProduct_selling_price());
                cv.put(total_price, detail.getTotal_price());


                db.insert(TABLE_INVOICE, null, cv);

            }

        }


        db.close();
    }

    public void addToCart(Bean_Set_Product_Categeory detail) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT " + product_code + "," + Quantity + "," + total_price + " FROM " + TABLE_INVOICE + " WHERE " + product_code + " = '" + detail.getCode() + "' ", null);
        Log.e("db product code", "" + detail.getCode());

        Log.e("query", "SELECT " + product_code + "," + Quantity + "," + total_price + " FROM " + TABLE_INVOICE + " WHERE " + product_code + " = '" + detail.getCode() + "' ");


        c.moveToFirst();

        Log.e("query count", c.getCount() + "");
        if (c.getCount() > 0) {
            ContentValues cv = new ContentValues();

            int quantity = C.getInt(c.getString(1)) + C.getInt(String.valueOf(detail.getQuantity()));

            float total = Float.parseFloat(c.getString(2)) + Float.parseFloat(String.valueOf(detail.getTotal_price()));


            cv.put(category_id, detail.getCategeory_id());
            cv.put(categeory_name, detail.getCategeory_name());
            cv.put(parent_id, detail.getParent_id());
            cv.put(parent_name, detail.getParent_name());
            cv.put(product_id, detail.getId());
            cv.put(product_name, detail.getName());
            cv.put(product_code, detail.getCode());
            cv.put(Quantity, String.valueOf(detail.getQuantity()));
            cv.put(mrp, detail.getProduct_mrp());
            cv.put(selling_price, detail.getProduct_selling_price());
            cv.put(total_price, detail.getTotal_price());

            db.update(TABLE_INVOICE, cv, product_code + "=?", new String[]{detail.getCode()});

        } else {

            ContentValues cv = new ContentValues();
            cv.put(category_id, detail.getCategeory_id());
            cv.put(categeory_name, detail.getCategeory_name());
            cv.put(parent_id, detail.getParent_id());
            cv.put(parent_name, detail.getParent_name());
            cv.put(product_id, detail.getId());
            cv.put(product_name, detail.getName());
            cv.put(product_code, detail.getCode());
            cv.put(Quantity, String.valueOf(detail.getQuantity()));
            cv.put(mrp, detail.getProduct_mrp());
            cv.put(selling_price, detail.getProduct_selling_price());
            cv.put(total_price, detail.getTotal_price());


            db.insert(TABLE_INVOICE, null, cv);

        }


        db.close();
    }

    public boolean isInCart(Bean_Set_Product_Categeory detail){
        boolean isAdded=false;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT " + product_code + "," + Quantity + "," + total_price + " FROM " + TABLE_INVOICE + " WHERE " + product_code + " = '" + detail.getCode() + "' ", null);

        if(c.getCount()>0)
            isAdded=true;

        c.close();

        db.close();

        return isAdded;
    }

    public Bean_Set_Product_Categeory getCurrentProduct(Bean_Set_Product_Categeory detail){
        Bean_Set_Product_Categeory productCategeory=new Bean_Set_Product_Categeory();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT " + product_code + "," + Quantity + "," + total_price + " FROM " + TABLE_INVOICE + " WHERE " + product_code + " = '" + detail.getCode() + "' ", null);

        if(c.moveToFirst()){
            productCategeory.setQuantity(Integer.parseInt(c.getString(1)));
            productCategeory.setTotal_price(c.getString(2));
        }

        c.close();

        db.close();
        return productCategeory;
    }

    public void updateToCart(Bean_Set_Product_Categeory detailItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.e("Item", detailItem.getCode() + " - " + detailItem.getQuantity() + " - " + detailItem.getTotal_price());

        ContentValues cv = new ContentValues();

        cv.put(Quantity, String.valueOf(detailItem.getQuantity()));
        cv.put(total_price, String.valueOf(detailItem.getTotal_price()));

        db.update(TABLE_INVOICE, cv, product_code + "=?", new String[]{detailItem.getCode()});


        db.close();
    }

    public void updateInCart(Bean_Set_Product_Categeory detail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(category_id, detail.getCategeory_id());
        cv.put(categeory_name, detail.getCategeory_name());
        cv.put(parent_id, detail.getParent_id());
        cv.put(parent_name, detail.getParent_name());
        cv.put(product_id, detail.getId());
        cv.put(product_name, detail.getName());
        cv.put(product_code, detail.getCode());
        cv.put(Quantity, String.valueOf(detail.getQuantity()));
        cv.put(mrp, detail.getProduct_mrp());
        cv.put(selling_price, detail.getProduct_selling_price());
        cv.put(total_price, detail.getTotal_price());


        db.update(TABLE_INVOICE, cv, product_id + "=?", new String[]{detail.getId()});

        db.close();
    }

    public boolean checkInCart(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_INVOICE, null, product_id + "=?", new String[]{id}, null, null, null);


        int count = cursor.getCount();
        if (count > 0) {
            return true;
        }
        return false;
    }


    public int getCartCount() {
        SQLiteDatabase db = this.getWritableDatabase();

        String cartQuery = "SELECT * FROM " + TABLE_INVOICE;
        Cursor cursor = db.rawQuery(cartQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public void removeFromCart(@Nullable String pro_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (pro_id == null) {
            db.delete(TABLE_INVOICE, null, null);
        } else {
            db.delete(TABLE_INVOICE, product_id + "=?", new String[]{pro_id});
        }
        db.close();
    }


    public List<Bean_Set_Product_Categeory> getCartData() {

        List<Bean_Set_Product_Categeory> set_product_categeories = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_INVOICE;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Bean_Set_Product_Categeory detail = new Bean_Set_Product_Categeory();
                detail.setCategeory_id(cursor.getString(0));
                detail.setCategeory_name(cursor.getString(1));
                detail.setParent_id(cursor.getString(2));
                detail.setParent_name(cursor.getString(3));
                detail.setId(cursor.getString(4));
                detail.setName(cursor.getString(5));
                detail.setCode(cursor.getString(6));
                detail.setQuantity(Integer.parseInt(cursor.getString(7)));
                detail.setProduct_mrp(cursor.getString(8));
                detail.setProduct_selling_price(cursor.getString(9));
                detail.setTotal_price(cursor.getString(10));

                set_product_categeories.add(detail);
            } while (cursor.moveToNext());
        }

        return set_product_categeories;
    }


    public void Add_Product_cart(Bean_Set_Product_Categeory detail) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT " + product_code + "," + Quantity + "," + total_price + " FROM " + TABLE_INVOICE + " WHERE " + product_code + " = '" + detail.getCode() + "' AND " + product_name, null);
        Log.e("db product code", "" + detail.getCode());

        Log.e("query", "SELECT " + product_code + "," + Quantity + "," + total_price + " FROM " + TABLE_INVOICE + " WHERE " + product_code + " = '" + detail.getCode() + "' AND " + product_name);


        c.moveToFirst();

        Log.e("query count", c.getCount() + "");

        if (c.getCount() > 0) {
            ContentValues cv = new ContentValues();

            int quantity = C.getInt(c.getString(1)) + C.getInt(String.valueOf(detail.getQuantity()));

            float total = Float.parseFloat(c.getString(2)) + Float.parseFloat(String.valueOf(detail.getQuantity()));


            cv.put(category_id, detail.getCategeory_id());
            cv.put(categeory_name, detail.getCategeory_name());
            cv.put(parent_id, detail.getParent_id());
            cv.put(parent_name, detail.getParent_name());
            cv.put(product_id, detail.getId());
            cv.put(product_name, detail.getName());
            cv.put(product_code, detail.getCode());
            cv.put(Quantity, String.valueOf(detail.getQuantity()));
            cv.put(mrp, detail.getProduct_mrp());
            cv.put(selling_price, detail.getProduct_selling_price());
            cv.put(total_price, detail.getTotal_price());
            cv.put(Quantity, String.valueOf(total));



           /* values.put(KEY_CART_PRO_ID, contact.getPro_id());
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
            values.put(KEY_CART_PRO_TOTAL, String.valueOf(total));*/
            db.update(TABLE_INVOICE, cv, product_id + "=?", new String[]{detail.getId()});
            //db.update(TABLE_PRODUCT_CARTDATA, values, KEY_CART_PRO_CODE +"=? AND "+KEY_CART_PRO_OPTION_NAME+"=? AND "+KEY_CART_PRO_OPTION_VALUENAME+"=?", new String[]{contact.getPro_code(),option,value});
            Log.e("db Update product code", "" + detail.getCode());

        } else {

            ContentValues cv = new ContentValues();

            cv.put(category_id, detail.getCategeory_id());
            cv.put(categeory_name, detail.getCategeory_name());
            cv.put(parent_id, detail.getParent_id());
            cv.put(parent_name, detail.getParent_name());
            cv.put(product_id, detail.getId());
            cv.put(product_name, detail.getName());
            cv.put(product_code, detail.getCode());
            cv.put(Quantity, String.valueOf(detail.getQuantity()));
            cv.put(mrp, detail.getProduct_mrp());
            cv.put(selling_price, detail.getProduct_selling_price());
            cv.put(total_price, detail.getTotal_price());
//            cv.put(Quantity ,String.valueOf(total) );




            /*values.put(KEY_CART_PRO_ID, contact.getPro_id());
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
*/            // Inserting Row
            db.insert(TABLE_INVOICE, null, cv);
            //db.insert(TABLE_PRODUCT_CARTDATA, null, values);
            Log.e("db Insert product code", "" + detail.getCode());
        }

        c.close();
        db.close(); // Closing database connection
    }
}