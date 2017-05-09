package com.nivida.bossb2b.Bean;

import java.io.Serializable;

/**
 * Created by Nivida new on 23-Aug-16.
 */
public class Bean_DistributorPerson implements Serializable {

    int distributor_id=0;
    int retailer_id=0;
    int role_id=0;
    String firstName="";
    String lastName="";
    String emailid="";
    String phone_no="";
    String password="";
    String firmName="";
    String country_id="";
    String state_id="";
    String city_id="";
    String area_id="";
    String pincode="";
    String firmAddress="";
    String PANID="";
    String VAT_TINid="";
    String CST_GSTid="";
    String imagePath="";
    int status=0;
    boolean checked=false;
    String areaName="";


    public Bean_DistributorPerson() {
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public int getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(int distributor_id) {
        this.distributor_id = distributor_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRetailer_id() {
        return retailer_id;
    }

    public void setRetailer_id(int retailer_id) {
        this.retailer_id = retailer_id;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getFirmAddress() {
        return firmAddress;
    }

    public void setFirmAddress(String firmAddress) {
        this.firmAddress = firmAddress;
    }

    public String getPANID() {
        return PANID;
    }

    public void setPANID(String PANID) {
        this.PANID = PANID;
    }

    public String getVAT_TINid() {
        return VAT_TINid;
    }

    public void setVAT_TINid(String VAT_TINid) {
        this.VAT_TINid = VAT_TINid;
    }

    public String getCST_GSTid() {
        return CST_GSTid;
    }

    public void setCST_GSTid(String CST_GSTid) {
        this.CST_GSTid = CST_GSTid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
