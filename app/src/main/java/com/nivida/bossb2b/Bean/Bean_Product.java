package com.nivida.bossb2b.Bean;

/**
 * Created by chaitalee on 4/14/2016.
 */
public class Bean_Product {

    private int id ;
    private String pro_id = new String();
    private String pro_code = new String();
    private String pro_name = new String();
    private String pro_label = new String();
    private String pro_mrp = new String();
    private String pro_sellingprice = new String();
    private String pro_shortdesc = new String();
    private String pro_moreinfo = new String();

    private String pro_cat_id = new String();
    private String pro_qty = new String();
    private String pro_image = new String();
    public Bean_Product() {

    }

    public Bean_Product(int id, String pro_id, String pro_code, String pro_name, String pro_label, String pro_mrp, String pro_sellingprice, String pro_shortdesc, String pro_moreinfo, String pro_cat_id, String pro_qty, String pro_image) {
        this.id = id;
        this.pro_id = pro_id;
        this.pro_code = pro_code;
        this.pro_name = pro_name;
        this.pro_label = pro_label;
        this.pro_mrp = pro_mrp;
        this.pro_sellingprice = pro_sellingprice;
        this.pro_shortdesc = pro_shortdesc;
        this.pro_moreinfo = pro_moreinfo;
        this.pro_cat_id = pro_cat_id;
        this.pro_qty = pro_qty;
        this.pro_image = pro_image;
    }

    public Bean_Product(String pro_id, String pro_code, String pro_name, String pro_label, String pro_mrp, String pro_sellingprice, String pro_shortdesc, String pro_moreinfo, String pro_cat_id, String pro_qty, String pro_image) {
        this.pro_id = pro_id;
        this.pro_code = pro_code;
        this.pro_name = pro_name;
        this.pro_label = pro_label;
        this.pro_mrp = pro_mrp;
        this.pro_sellingprice = pro_sellingprice;
        this.pro_shortdesc = pro_shortdesc;
        this.pro_moreinfo = pro_moreinfo;
        this.pro_cat_id = pro_cat_id;
        this.pro_qty = pro_qty;
        this.pro_image = pro_image;
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

    public String getPro_label() {
        return pro_label;
    }

    public void setPro_label(String pro_label) {
        this.pro_label = pro_label;
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

    public String getPro_moreinfo() {
        return pro_moreinfo;
    }

    public void setPro_moreinfo(String pro_moreinfo) {
        this.pro_moreinfo = pro_moreinfo;
    }

    public String getPro_cat_id() {
        return pro_cat_id;
    }

    public void setPro_cat_id(String pro_cat_id) {
        this.pro_cat_id = pro_cat_id;
    }

    public String getPro_qty() {
        return pro_qty;
    }

    public void setPro_qty(String pro_qty) {
        this.pro_qty = pro_qty;
    }

    public String getPro_image() {
        return pro_image;
    }

    public void setPro_image(String pro_image) {
        this.pro_image = pro_image;
    }
}
