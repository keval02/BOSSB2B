package com.nivida.bossb2b.Bean;

public class Bean_Value_Selected_Detail {

    String value_id;
    String value_name;
    String value_mrp;

    public Bean_Value_Selected_Detail() {
    }

    public Bean_Value_Selected_Detail(String value_id, String value_name, String value_mrp) {
        this.value_id = value_id;
        this.value_name = value_name;
        this.value_mrp = value_mrp;
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
}
