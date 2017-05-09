package com.nivida.bossb2b.Bean;

/**
 * Created by chaitalee on 4/14/2016.
 */
public class Bean_ProductStdSpec {

    private int id ;
    private String pro_id = new String();
    private String pro_cat_id = new String();
    private String pro_Stdspec_id = new String();
    private String pro_Stdspec_name = new String();

    public Bean_ProductStdSpec() {
    }

    private String pro_Stdspec_value = new String();

    public Bean_ProductStdSpec(int id, String pro_id, String pro_cat_id, String pro_Stdspec_id, String pro_Stdspec_name, String pro_Stdspec_value) {
        this.id = id;
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_Stdspec_id = pro_Stdspec_id;
        this.pro_Stdspec_name = pro_Stdspec_name;
        this.pro_Stdspec_value = pro_Stdspec_value;
    }

    public Bean_ProductStdSpec(String pro_id, String pro_cat_id, String pro_Stdspec_id, String pro_Stdspec_name, String pro_Stdspec_value) {
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_Stdspec_id = pro_Stdspec_id;
        this.pro_Stdspec_name = pro_Stdspec_name;
        this.pro_Stdspec_value = pro_Stdspec_value;
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

    public String getPro_Stdspec_id() {
        return pro_Stdspec_id;
    }

    public void setPro_Stdspec_id(String pro_Stdspec_id) {
        this.pro_Stdspec_id = pro_Stdspec_id;
    }

    public String getPro_Stdspec_name() {
        return pro_Stdspec_name;
    }

    public void setPro_Stdspec_name(String pro_Stdspec_name) {
        this.pro_Stdspec_name = pro_Stdspec_name;
    }

    public String getPro_Stdspec_value() {
        return pro_Stdspec_value;
    }

    public void setPro_Stdspec_value(String pro_Stdspec_value) {
        this.pro_Stdspec_value = pro_Stdspec_value;
    }
}
