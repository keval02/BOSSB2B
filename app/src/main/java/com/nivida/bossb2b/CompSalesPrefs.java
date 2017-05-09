package com.nivida.bossb2b;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nivida new on 12-Sep-16.
 */
public class CompSalesPrefs {

    private static final String COMP_PREFS = "COMP_PREFS";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String user_id="";
    String role_id="";
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
    String status="";
    boolean checked=false;
    String areaName="";

    public CompSalesPrefs(Context context){
        this.sharedPreferences = context.getSharedPreferences(COMP_PREFS, Activity.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();

        user_id=sharedPreferences.getString("user_id","");
        role_id=sharedPreferences.getString("role_id","");
        firstName=sharedPreferences.getString("firstName","");
        lastName=sharedPreferences.getString("lastName","");
        emailid=sharedPreferences.getString("emailid","");
        phone_no=sharedPreferences.getString("phone_no","");
        password=sharedPreferences.getString("password","");
        firmName=sharedPreferences.getString("firmName","");
        country_id=sharedPreferences.getString("country_id","");
        state_id=sharedPreferences.getString("state_id","");
        city_id=sharedPreferences.getString("city_id","");
        area_id=sharedPreferences.getString("area_id","");
        pincode=sharedPreferences.getString("pincode","");
        firmAddress=sharedPreferences.getString("firmAddress","");
        PANID=sharedPreferences.getString("PANID","");
        VAT_TINid=sharedPreferences.getString("VAT_TINid","");
        CST_GSTid=sharedPreferences.getString("CST_GSTid","");
        status=sharedPreferences.getString("status","");
        areaName=sharedPreferences.getString("areaName","");
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
        editor.putString("user_id",user_id).commit();
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
        editor.putString("role_id",role_id).commit();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        editor.putString("firstName",firstName).commit();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        editor.putString("lastName",lastName).commit();
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
        editor.putString("emailid",emailid).commit();
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
        editor.putString("phone_no",phone_no).commit();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        editor.putString("password",password).commit();
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
        editor.putString("firmName",firmName).commit();
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
        editor.putString("country_id",country_id).commit();
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
        editor.putString("state_id",state_id).commit();
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
        editor.putString("city_id",city_id).commit();
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
        editor.putString("area_id",area_id).commit();
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
        editor.putString("pincode",pincode).commit();
    }

    public String getFirmAddress() {
        return firmAddress;
    }

    public void setFirmAddress(String firmAddress) {
        this.firmAddress = firmAddress;
        editor.putString("firmAddress",firmAddress).commit();
    }

    public String getPANID() {
        return PANID;
    }

    public void setPANID(String PANID) {
        this.PANID = PANID;
        editor.putString("PANID",PANID).commit();
    }

    public String getVAT_TINid() {
        return VAT_TINid;
    }

    public void setVAT_TINid(String VAT_TINid) {
        this.VAT_TINid = VAT_TINid;
        editor.putString("VAT_TINid",VAT_TINid).commit();
    }

    public String getCST_GSTid() {
        return CST_GSTid;
    }

    public void setCST_GSTid(String CST_GSTid) {
        this.CST_GSTid = CST_GSTid;
        editor.putString("CST_GSTid",CST_GSTid).commit();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        editor.putString("imagePath",imagePath).commit();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        editor.putString("status",status).commit();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
        editor.putString("areaName",areaName).commit();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        editor.putBoolean("checked",checked).commit();
    }
}
