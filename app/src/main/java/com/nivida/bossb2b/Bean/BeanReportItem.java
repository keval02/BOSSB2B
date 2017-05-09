package com.nivida.bossb2b.Bean;

import java.io.Serializable;

/**
 * Created by NWSPL-17 on 2/10/2017.
 */

public class BeanReportItem implements Serializable {

    private boolean isSubmitted=false;
    private String dateSubmitted="";

    public BeanReportItem() {
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }
}
