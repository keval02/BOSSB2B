package com.nivida.bossb2b.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NWSPL-17 on 2/2/2017.
 */
public class BeanShowHierarchy {


    boolean isOpen=false;

    String firm_shop_name = "";
    String first_name="";
    String last_name="";
    String email_id="";
    String phone_no="";
    String mobile_no="";
    String address_1="";
    String address_2="";
    String address_3="";
    String pincode="";
    String name="";

    List<BeanReatailerHierarchy> reatailerHierarchies=new ArrayList<>();


    public BeanShowHierarchy() {
    }

    public String getFirm_shop_name() {
        return firm_shop_name;
    }

    public void setFirm_shop_name(String firm_shop_name) {
        this.firm_shop_name = firm_shop_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
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

    public String getAddress_3() {
        return address_3;
    }

    public void setAddress_3(String address_3) {
        this.address_3 = address_3;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BeanReatailerHierarchy> getReatailerHierarchies() {
        return reatailerHierarchies;
    }

    public void setReatailerHierarchies(List<BeanReatailerHierarchy> reatailerHierarchies) {
        this.reatailerHierarchies = reatailerHierarchies;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
