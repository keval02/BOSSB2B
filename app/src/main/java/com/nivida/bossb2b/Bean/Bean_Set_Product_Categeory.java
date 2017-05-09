package com.nivida.bossb2b.Bean;

/**
 * Created by Dhruvil Bhosle on 24-11-2016.
 */
public class Bean_Set_Product_Categeory {

    public Bean_Set_Product_Categeory() {
    }

    String name = "";
    String code = "";
    String id = "";
    String categeory_id = "";
    String categeory_name = "";
    String parent_id = "";
    String parent_name = "";
    String product_mrp="";
    String product_selling_price="";
    String total_price="";
    int quantity=0;
    int max_quantity=0;
    boolean checked = false;
    String b2b_stock="";

    public String getB2b_stock() {
        return b2b_stock;
    }

    public void setB2b_stock(String b2b_stock) {
        this.b2b_stock = b2b_stock;
    }

    public String getProduct_mrp() {
        return product_mrp;
    }

    public void setProduct_mrp(String product_mrp) {
        this.product_mrp = product_mrp;
    }

    public String getProduct_selling_price() {
        return product_selling_price;
    }

    public void setProduct_selling_price(String product_selling_price) {
        this.product_selling_price = product_selling_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }


    public String getCategeory_name() {
        return categeory_name;
    }

    public void setCategeory_name(String categeory_name) {
        this.categeory_name = categeory_name;
    }

    public String getCategeory_id() {
        return categeory_id;
    }

    public void setCategeory_id(String categeory_id) {
        this.categeory_id = categeory_id;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    public int getMax_quantity() {
        return max_quantity;
    }

    public void setMax_quantity(int max_quantity) {
        this.max_quantity = max_quantity;
    }
}

