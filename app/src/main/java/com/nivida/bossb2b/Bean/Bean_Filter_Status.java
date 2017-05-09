package com.nivida.bossb2b.Bean;

/**
 * Created by prince on 5/9/2016.
 */
public class Bean_Filter_Status {

    String id;
    String filter_name;

    public Bean_Filter_Status() {
    }

    public Bean_Filter_Status(String id, String filter_name) {
        this.id = id;
        this.filter_name = filter_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilter_name() {
        return filter_name;
    }

    public void setFilter_name(String filter_name) {
        this.filter_name = filter_name;
    }
}
