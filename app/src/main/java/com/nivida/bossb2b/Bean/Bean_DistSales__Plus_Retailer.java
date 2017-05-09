package com.nivida.bossb2b.Bean;

import java.util.List;

/**
 * Created by Nivida new on 05-Sep-16.
 */
public class Bean_DistSales__Plus_Retailer {

    List<Bean_RetailerPerson> retailerPersonList;
    List<Bean_DistributorPerson> distributorPersonsList;
    List<Bean_Area> areaList;

    public Bean_DistSales__Plus_Retailer() {

    }

    public List<Bean_RetailerPerson> getRetailerPersonList() {
        return retailerPersonList;
    }

    public void setRetailerPersonList(List<Bean_RetailerPerson> retailerPersonList) {
        this.retailerPersonList = retailerPersonList;
    }

    public List<Bean_DistributorPerson> getDistributorPersonsList() {
        return distributorPersonsList;
    }

    public void setDistributorPersonsList(List<Bean_DistributorPerson> distributorPersonsList) {
        this.distributorPersonsList = distributorPersonsList;
    }

    public List<Bean_Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Bean_Area> areaList) {
        this.areaList = areaList;
    }
}
