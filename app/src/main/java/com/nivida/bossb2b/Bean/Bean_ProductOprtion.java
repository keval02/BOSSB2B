package com.nivida.bossb2b.Bean;

/**
 * Created by chaitalee on 4/14/2016.
 */
public class Bean_ProductOprtion {

    private int id ;
    private String pro_id = new String();
    private String pro_cat_id = new String();
    private String pro_Option_id = new String();
    private String pro_Option_name = new String();
    private String pro_Option_value_id = new String();
    private String pro_Option_value_name = new String();
    private String pro_Option_mrp = new String();
    private String pro_Option_selling_price = new String();
    private String pro_Option_proimage = new String();
    private String pro_Option_procode = new String();

    public Bean_ProductOprtion() {
    }

    public String getPro_Option_procode() {
        return pro_Option_procode;
    }

    public void setPro_Option_procode(String pro_Option_procode) {
        this.pro_Option_procode = pro_Option_procode;
    }

    public Bean_ProductOprtion(String pro_id, String pro_cat_id, String pro_Option_id, String pro_Option_name, String pro_Option_value_id, String pro_Option_value_name, String pro_Option_mrp, String pro_Option_selling_price, String pro_Option_proimage, String pro_Option_procode) {
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_Option_id = pro_Option_id;
        this.pro_Option_name = pro_Option_name;

        this.pro_Option_value_id = pro_Option_value_id;
        this.pro_Option_value_name = pro_Option_value_name;
        this.pro_Option_mrp = pro_Option_mrp;
        this.pro_Option_selling_price = pro_Option_selling_price;
        this.pro_Option_proimage = pro_Option_proimage;
        this.pro_Option_procode = pro_Option_procode;
    }

    public Bean_ProductOprtion(int id, String pro_id, String pro_cat_id, String pro_Option_id, String pro_Option_name, String pro_Option_value_id, String pro_Option_value_name, String pro_Option_mrp, String pro_Option_selling_price, String pro_Option_proimage, String pro_Option_procode) {
        this.id = id;
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_Option_id = pro_Option_id;
        this.pro_Option_name = pro_Option_name;
        this.pro_Option_value_id = pro_Option_value_id;
        this.pro_Option_value_name = pro_Option_value_name;
        this.pro_Option_mrp = pro_Option_mrp;
        this.pro_Option_selling_price = pro_Option_selling_price;
        this.pro_Option_proimage = pro_Option_proimage;
        this.pro_Option_procode = pro_Option_procode;
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

    public String getPro_Option_mrp() {
        return pro_Option_mrp;
    }

    public void setPro_Option_mrp(String pro_Option_mrp) {
        this.pro_Option_mrp = pro_Option_mrp;
    }

    public String getPro_Option_selling_price() {
        return pro_Option_selling_price;
    }

    public void setPro_Option_selling_price(String pro_Option_selling_price) {
        this.pro_Option_selling_price = pro_Option_selling_price;
    }

    public String getPro_Option_proimage() {
        return pro_Option_proimage;
    }

    public void setPro_Option_proimage(String pro_Option_proimage) {
        this.pro_Option_proimage = pro_Option_proimage;
    }
}
