package com.nivida.bossb2b.Bean;

/**
 * Created by ravina on 4/27/2016.
 */
public class Product_Youtube {

    private int id ;
    private String pro_id = new String();
    private String pro_cat_id = new String();
    private String pro_youtube_url = new String();

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

    public String getPro_youtube_url() {
        return pro_youtube_url;
    }

    public void setPro_youtube_url(String pro_youtube_url) {
        this.pro_youtube_url = pro_youtube_url;
    }

    public Product_Youtube(String pro_id, String pro_cat_id, String pro_youtube_url) {
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_youtube_url = pro_youtube_url;
    }

    public Product_Youtube(int id, String pro_id, String pro_cat_id, String pro_youtube_url) {
        this.id = id;
        this.pro_id = pro_id;
        this.pro_cat_id = pro_cat_id;
        this.pro_youtube_url = pro_youtube_url;
    }

    public Product_Youtube() {
    }
}
