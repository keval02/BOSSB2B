package com.nivida.bossb2b.Bean;

/**
 * Created by ravina on 4/28/2016.
 */
public class Bean_WhereToBuy {

    private String id;
    private String branch_name;
    private String pincode;
    private String address_1;
    private String address_2;
    private String city;
    private String mobile;
    private String email_id;

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Bean_WhereToBuy(String id, String branch_name, String pincode, String address_1, String address_2, String city, String state, String latitude, String longitude) {
        this.id = id;
        this.branch_name = branch_name;
        this.pincode = pincode;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private String state;
    private String latitude;
    private String longitude;

    public Bean_WhereToBuy(String id, String branch_name, String pincode, String address_1, String address_2, String state, String latitude, String longitude) {
        this.id = id;
        this.branch_name = branch_name;
        this.pincode = pincode;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Bean_WhereToBuy() {
    }
}
