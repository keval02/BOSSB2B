package com.nivida.bossb2b.Bean;

/**
 * Created by chaitalee on 4/14/2016.
 */
public class Bean_ProductImage {

    private int id ;
    private String pro_id = new String();
    private String pro_cat_id = new String();
    private String pro_Images = new String();

    public Bean_ProductImage() {
    }

    public Bean_ProductImage(int id, String pro_id, String pro_cat_id, String pro_Images) {
        this.id = id;
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;

        this.pro_Images = pro_Images;
    }

    public Bean_ProductImage(String pro_id, String pro_cat_id, String pro_Images) {
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_Images = pro_Images;
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
}
