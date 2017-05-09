package com.nivida.bossb2b.Bean;

import java.io.Serializable;

/**
 * Created by ravina on 5/12/2016.
 */
public class Bean_ProductCatalog implements Serializable{

    private int id ;
    private String pro_id = new String();
    private String label = new String();
    private String file_path = new String();

    public Bean_ProductCatalog() {
    }

    public Bean_ProductCatalog(String pro_id, String label, String file_path) {
        this.pro_id = pro_id;
        this.label = label;
        this.file_path = file_path;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
