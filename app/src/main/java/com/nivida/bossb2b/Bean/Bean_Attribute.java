package com.nivida.bossb2b.Bean;

/**
 * Created by chaitalee on 4/20/2016.
 */
public class Bean_Attribute {

    String option_id;
    String option_name;
    String value_id;
    String value_name;
    String value_mrp;
    String value_image;
    String value_product_code;
    String value_selling_price;


    public Bean_Attribute() {
    }

    public Bean_Attribute(String option_id, String option_name, String value_id, String value_name, String value_mrp, String value_image, String value_product_code, String value_selling_price) {
        this.option_id = option_id;
        this.option_name = option_name;
        this.value_id = value_id;
        this.value_name = value_name;
        this.value_mrp = value_mrp;
        this.value_image = value_image;
        this.value_product_code = value_product_code;
        this.value_selling_price = value_selling_price;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getValue_id() {
        return value_id;
    }

    public void setValue_id(String value_id) {
        this.value_id = value_id;
    }

    public String getValue_name() {
        return value_name;
    }

    public void setValue_name(String value_name) {
        this.value_name = value_name;
    }

    public String getValue_mrp() {
        return value_mrp;
    }

    public void setValue_mrp(String value_mrp) {
        this.value_mrp = value_mrp;
    }

    public String getValue_image() {
        return value_image;
    }

    public void setValue_image(String value_image) {
        this.value_image = value_image;
    }

    public String getValue_product_code() {
        return value_product_code;
    }

    public void setValue_product_code(String value_product_code) {
        this.value_product_code = value_product_code;
    }

    public String getValue_selling_price() {
        return value_selling_price;
    }

    public void setValue_selling_price(String value_selling_price) {
        this.value_selling_price = value_selling_price;
    }
}
