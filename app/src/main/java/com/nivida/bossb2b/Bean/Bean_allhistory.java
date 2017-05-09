package com.nivida.bossb2b.Bean;

/**
 * Created by prince on 9/21/2016.
 */
public class Bean_allhistory {

    String start_time;
    String end_time;
    String start_latitude;
    String start_longitude;
    String end_latitude;
    String end_longitude;
    String start_location;
    String end_location;
    String attachments1;
    String comments;
    String email_id;
    String mobile_no;
    String pincode;
    String city;
    String area;

    public Bean_allhistory() {
    }



    public String getAllStart_time() {
        return start_time;
    }

    public void setAllStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getAllEnd_time() {
        return end_time;
    }

    public void setAllEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAllStart_latitude() {
        return start_latitude;
    }

    public void setAllStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    public String getAllStart_longitude() {
        return start_longitude;
    }

    public void setAllStart_longitude(String start_longitude) {
        this.start_longitude = start_longitude;
    }

    public String getAllEnd_latitude() {
        return end_latitude;
    }

    public void setAllEnd_latitude(String end_latitude) {
        this.end_latitude = end_latitude;
    }

    public String getAllEnd_longitude() {
        return end_longitude;
    }

    public void setAllEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }

        public String getAllAttachments1() {
        return attachments1;
    }

    public void setAllAttachments1(String attachments1) {
        this.attachments1 = attachments1;
    }

    public String getAllComments() {
        return comments;
    }

    public void setAllComments(String comments) {
        this.comments = comments;
    }

    public String getAllEmail_id() {
        return email_id;
    }

    public void setAllEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getAllMobile_no() {
        return mobile_no;
    }

    public void setAllMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAllPincode() {
        return pincode;
    }

    public void setAllPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAllCity() {
        return city;
    }

    public void setAllCity(String city) {
        this.city = city;
    }

    public String getAllArea() {
        return area;
    }

    public void setAllArea(String area) {
        this.area = area;
    }

    public Bean_allhistory(String start_time, String end_time, String start_latitude, String start_longitude, String end_latitude, String end_longitude, String start_location, String end_location, String start_battery_status, String end_battery_status, String company_name, String contact_person_name
            , String comments, String attachments1 , String email_id, String mobile_no, String alternate_no, String pincode, String city, String area,String designation) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.start_latitude = start_latitude;

        this.start_longitude = start_longitude;
        this.end_latitude = end_latitude;
        this.end_longitude = end_longitude;
        this.start_location = start_location;
        this.end_location = end_location;

        this.attachments1=attachments1;
        this.comments=comments;

        this.email_id = email_id;
        this.mobile_no = mobile_no;

        this.pincode = pincode;

        this.city = city;
        this.area = area;
    }
}