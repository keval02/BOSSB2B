package com.nivida.bossb2b.Bean;

/**
 * Created by Ajay on 12/14/2016.
 */

public class Bean_User_data {

    private int id ;
    private String role_id;
    private String user_id;
    private String email_id;
    private String phone_no;
    private String f_name;
    private String l_name;
    private String password;
    private String gender;
    private String user_type;
    private String login_with;
    private String str_rid;
    private String add1;
    private String add2;
    private String add3;
    private String landmark;
    private String pincode;
    private String state_id;
    private String state_name;
    private String city_id;
    private String city_name;
    private String str_response;


    public Bean_User_data(int id, String user_id, String email_id, String phone_no, String f_name, String l_name, String password, String gender, String user_type, String login_with, String str_rid, String add1, String add2, String add3, String landmark, String pincode, String state_id, String state_name, String city_id, String city_name, String str_response) {
        this.id = id;
        this.user_id = user_id;
        this.email_id = email_id;
        this.phone_no = phone_no;
        this.f_name = f_name;
        this.l_name = l_name;
        this.password = password;
        this.gender = gender;
        this.user_type = user_type;
        this.login_with = login_with;
        this.str_rid = str_rid;
        this.add1 = add1;
        this.add2 = add2;
        this.add3 = add3;
        this.landmark = landmark;
        this.pincode = pincode;
        this.state_id = state_id;
        this.state_name = state_name;
        this.city_id = city_id;
        this.city_name = city_name;
        this.str_response = str_response;
    }

    public Bean_User_data(){

    }

    public Bean_User_data(int id, String role_id, String user_id, String email_id, String phone_no, String f_name, String l_name, String password, String gender, String user_type, String login_with, String str_rid, String add1, String add2, String add3, String landmark, String pincode, String state_id, String state_name, String city_id, String city_name, String str_response) {
        this.id = id;
        this.role_id = role_id;
        this.user_id = user_id;
        this.email_id = email_id;
        this.phone_no = phone_no;
        this.f_name = f_name;
        this.l_name = l_name;
        this.password = password;
        this.gender = gender;
        this.user_type = user_type;
        this.login_with = login_with;
        this.str_rid = str_rid;
        this.add1 = add1;
        this.add2 = add2;
        this.add3 = add3;
        this.landmark = landmark;
        this.pincode = pincode;
        this.state_id = state_id;
        this.state_name = state_name;
        this.city_id = city_id;
        this.city_name = city_name;
        this.str_response = str_response;
    }

    public Bean_User_data(String user_id, String email_id, String phone_no, String f_name, String l_name, String password, String gender, String user_type, String login_with, String str_rid, String add1, String add2, String add3, String landmark, String pincode, String state_id, String state_name, String city_id, String city_name, String str_response) {
        this.user_id = user_id;
        this.email_id = email_id;
        this.phone_no = phone_no;
        this.f_name = f_name;
        this.l_name = l_name;
        this.password = password;
        this.gender = gender;
        this.user_type = user_type;
        this.login_with = login_with;
        this.str_rid = str_rid;
        this.add1 = add1;
        this.add2 = add2;
        this.add3 = add3;
        this.landmark = landmark;
        this.pincode = pincode;
        this.state_id = state_id;
        this.state_name = state_name;
        this.city_id = city_id;
        this.city_name = city_name;
        this.str_response = str_response;
    }

    public Bean_User_data(String user_id, String email_id, String phone_no, String f_name, String l_name, String password, String gender, String user_type, String login_with, String str_rid, String add1, String add2, String add3, String landmark, String pincode, String state_id, String state_name, String city_id, String city_name, String role_id, String str_response) {
        this.role_id = role_id;
        this.user_id = user_id;
        this.email_id = email_id;
        this.phone_no = phone_no;
        this.f_name = f_name;
        this.l_name = l_name;
        this.password = password;
        this.gender = gender;
        this.user_type = user_type;
        this.login_with = login_with;
        this.str_rid = str_rid;
        this.add1 = add1;
        this.add2 = add2;
        this.add3 = add3;
        this.landmark = landmark;
        this.pincode = pincode;
        this.state_id = state_id;
        this.state_name = state_name;
        this.city_id = city_id;
        this.city_name = city_name;
        this.str_response = str_response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getLogin_with() {
        return login_with;
    }

    public void setLogin_with(String login_with) {
        this.login_with = login_with;
    }

    public String getStr_rid() {
        return str_rid;
    }

    public void setStr_rid(String str_rid) {
        this.str_rid = str_rid;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getAdd3() {
        return add3;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getStr_response() {
        return str_response;
    }

    public void setStr_response(String str_response) {
        this.str_response = str_response;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
