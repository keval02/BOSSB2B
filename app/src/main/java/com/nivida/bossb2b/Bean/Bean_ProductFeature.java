package com.nivida.bossb2b.Bean;

/**
 * Created by chaitalee on 4/14/2016.
 */
public class Bean_ProductFeature {

    private int id ;
    private String pro_id = new String();
    private String pro_cat_id = new String();
    private String pro_feature_id = new String();
    private String pro_feature_name = new String();
    private String pro_feature_value = new String();

    public Bean_ProductFeature() {
    }

    public Bean_ProductFeature(int id, String pro_id, String pro_cat_id, String pro_feature_id, String pro_feature_name, String pro_feature_value) {
        this.id = id;

        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_feature_id = pro_feature_id;
        this.pro_feature_name = pro_feature_name;
        this.pro_feature_value = pro_feature_value;
    }

    public Bean_ProductFeature(String pro_id, String pro_cat_id, String pro_feature_id, String pro_feature_name, String pro_feature_value) {
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_feature_id = pro_feature_id;
        this.pro_feature_name = pro_feature_name;
        this.pro_feature_value = pro_feature_value;
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

    public String getPro_feature_id() {
        return pro_feature_id;
    }

    public void setPro_feature_id(String pro_feature_id) {
        this.pro_feature_id = pro_feature_id;
    }

    public String getPro_feature_name() {
        return pro_feature_name;
    }

    public void setPro_feature_name(String pro_feature_name) {
        this.pro_feature_name = pro_feature_name;
    }

    public String getPro_feature_value() {
        return pro_feature_value;
    }

    public void setPro_feature_value(String pro_feature_value) {
        this.pro_feature_value = pro_feature_value;
    }
}
