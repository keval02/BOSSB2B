package com.nivida.bossb2b.Bean;

/**
 * Created by chaitalee on 4/14/2016.
 */
public class Bean_ProductTechSpec {

    private int id ;
    private String pro_id = new String();
    private String pro_cat_id = new String();
    private String pro_Techspec_id = new String();
    private String pro_Techspec_name = new String();
    private String pro_Techspec_value = new String();

    public Bean_ProductTechSpec() {
    }

    public Bean_ProductTechSpec(int id, String pro_id, String pro_cat_id, String pro_Techspec_id, String pro_Techspec_name, String pro_Techspec_value) {
        this.id = id;
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_Techspec_id = pro_Techspec_id;
        this.pro_Techspec_name = pro_Techspec_name;
        this.pro_Techspec_value = pro_Techspec_value;
    }

    public Bean_ProductTechSpec(String pro_id, String pro_cat_id, String pro_Techspec_id, String pro_Techspec_name, String pro_Techspec_value) {
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_Techspec_id = pro_Techspec_id;
        this.pro_Techspec_name = pro_Techspec_name;
        this.pro_Techspec_value = pro_Techspec_value;
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

    public String getPro_Techspec_id() {
        return pro_Techspec_id;
    }

    public void setPro_Techspec_id(String pro_Techspec_id) {
        this.pro_Techspec_id = pro_Techspec_id;
    }

    public String getPro_Techspec_name() {
        return pro_Techspec_name;
    }

    public void setPro_Techspec_name(String pro_Techspec_name) {
        this.pro_Techspec_name = pro_Techspec_name;
    }

    public String getPro_Techspec_value() {
        return pro_Techspec_value;
    }

    public void setPro_Techspec_value(String pro_Techspec_value) {
        this.pro_Techspec_value = pro_Techspec_value;
    }
}
