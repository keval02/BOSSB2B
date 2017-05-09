package com.nivida.bossb2b.Bean;

/**
 * Created by Dhruvil Bhosle on 28-11-2016.
 */
public class Bean_Invoice_List {

    String categeory_id="";
    String categeory_name = "";
    String parent_id = "";
    String parent_name = "";
    String product_id = "";
    String product_name = "";
    String product_code="";

    public String getCategeory_id() {
        return categeory_id;
    }

    public void setCategeory_id(String categeory_id) {
        this.categeory_id = categeory_id;
    }

    public String getCategeory_name() {
        return categeory_name;
    }

    public void setCategeory_name(String categeory_name) {
        this.categeory_name = categeory_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
}
