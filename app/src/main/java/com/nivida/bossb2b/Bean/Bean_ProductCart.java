package com.nivida.bossb2b.Bean;

import java.io.Serializable;

/**
 * Created by chaitalee on 4/14/2016.
 */
public class Bean_ProductCart implements Serializable {

    private int id ;
    private String pro_id = new String();
    private String pro_cat_id = new String();
    private String pro_Images = new String();
    private String pro_code = new String();
    private String pro_name = new String();
    private String pro_qty = new String();
    private String pro_mrp = new String();
    private String pro_sellingprice = new String();
    private String pro_shortdesc = new String();
    private String pro_Option_id = new String();
    private String pro_Option_name = new String();
    private String pro_Option_value_id = new String();
    private String pro_Option_value_name = new String();
    private String pro_total = new String();

    public Bean_ProductCart() {
    }

    public Bean_ProductCart(int id, String pro_id, String pro_cat_id, String pro_Images, String pro_code, String pro_name, String pro_qty, String pro_mrp, String pro_sellingprice, String pro_shortdesc, String pro_Option_id, String pro_Option_name, String pro_Option_value_id, String pro_Option_value_name, String pro_total) {
        this.id = id;
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;

        this.pro_Images = pro_Images;
        this.pro_code = pro_code;
        this.pro_name = pro_name;
        this.pro_qty = pro_qty;
        this.pro_mrp = pro_mrp;
        this.pro_sellingprice = pro_sellingprice;
        this.pro_shortdesc = pro_shortdesc;
        this.pro_Option_id = pro_Option_id;
        this.pro_Option_name = pro_Option_name;
        this.pro_Option_value_id = pro_Option_value_id;
        this.pro_Option_value_name = pro_Option_value_name;
        this.pro_total = pro_total;
    }

    public Bean_ProductCart(String pro_id, String pro_cat_id, String pro_Images, String pro_code, String pro_name, String pro_qty, String pro_mrp, String pro_sellingprice, String pro_shortdesc, String pro_Option_id, String pro_Option_name, String pro_Option_value_id, String pro_Option_value_name, String pro_total) {
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_Images = pro_Images;
        this.pro_code = pro_code;
        this.pro_name = pro_name;
        this.pro_qty = pro_qty;
        this.pro_mrp = pro_mrp;
        this.pro_sellingprice = pro_sellingprice;
        this.pro_shortdesc = pro_shortdesc;
        this.pro_Option_id = pro_Option_id;
        this.pro_Option_name = pro_Option_name;
        this.pro_Option_value_id = pro_Option_value_id;
        this.pro_Option_value_name = pro_Option_value_name;
        this.pro_total = pro_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getPro_cat_id() {
        return pro_cat_id;
    }

    public void setPro_cat_id(String pro_cat_id) {
        this.pro_cat_id = pro_cat_id;
    }

    public String getPro_Images() {
        return pro_Images;
    }

    public void setPro_Images(String pro_Images) {
        this.pro_Images = pro_Images;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_qty() {
        return pro_qty;
    }

    public void setPro_qty(String pro_qty) {
        this.pro_qty = pro_qty;
    }

    public String getPro_mrp() {
        return pro_mrp;
    }

    public void setPro_mrp(String pro_mrp) {
        this.pro_mrp = pro_mrp;
    }

    public String getPro_sellingprice() {
        return pro_sellingprice;
    }

    public void setPro_sellingprice(String pro_sellingprice) {
        this.pro_sellingprice = pro_sellingprice;
    }

    public String getPro_shortdesc() {
        return pro_shortdesc;
    }

    public void setPro_shortdesc(String pro_shortdesc) {
        this.pro_shortdesc = pro_shortdesc;
    }

    public String getPro_Option_id() {
        return pro_Option_id;
    }

    public void setPro_Option_id(String pro_Option_id) {
        this.pro_Option_id = pro_Option_id;
    }

    public String getPro_Option_name() {
        return pro_Option_name;
    }

    public void setPro_Option_name(String pro_Option_name) {
        this.pro_Option_name = pro_Option_name;
    }

    public String getPro_Option_value_id() {
        return pro_Option_value_id;
    }

    public void setPro_Option_value_id(String pro_Option_value_id) {
        this.pro_Option_value_id = pro_Option_value_id;
    }

    public String getPro_Option_value_name() {
        return pro_Option_value_name;
    }

    public void setPro_Option_value_name(String pro_Option_value_name) {
        this.pro_Option_value_name = pro_Option_value_name;
    }

    public String getPro_total() {
        return pro_total;
    }

    public void setPro_total(String pro_total) {
        this.pro_total = pro_total;
    }
}
