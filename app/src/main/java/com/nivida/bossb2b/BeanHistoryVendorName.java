package com.nivida.bossb2b;

import java.util.ArrayList;

/**
 * Created by Ajay on 1/23/2017.
 */

public class BeanHistoryVendorName {

    String contact_person_name;
    String attachments1;
    String attachments2;

    public String getAttachments3() {
        return attachments3;
    }

    public void setAttachments3(String attachments3) {
        this.attachments3 = attachments3;
    }

    public String getAttachments2() {
        return attachments2;
    }

    public void setAttachments2(String attachments2) {
        this.attachments2 = attachments2;
    }

    String attachments3;
    String comments;
    String Designation;
    String id="";
    String user_id="";
    String vendor_id="";
    String route_id="";
    String meeting_type="";
    String first_name="";
    String middle_name="";
    String last_name="";
    String phone_no="";
    String email_id;
    String mobile_no;
    String alternate_no;
    String pincode;
    String city;
    String area;
    String role_id;
    String user_name;
    String str_id;
    String str_responce;
    String uuid;
    String owner_id;
    String device_id;
    String vname = "";
    String start_time;
    String end_time;
    String start_latitude;
    String start_longitude;
    String end_latitude;
    String end_longitude;
    String start_location;
    String end_location;
    String start_battery_status;
    String end_battery_status;
    String company_name;
    String Address;
    boolean hasOrders=false;
    String meetingID="0";

    String companySalesPersonName="";

    ArrayList<String> attachmentPaths = new ArrayList<>();

    public ArrayList<String> getAttachmentPaths() {
        return attachmentPaths;
    }

    public void setAttachmentPaths(ArrayList<String> attachmentPaths) {
        this.attachmentPaths = attachmentPaths;
    }

    public String getCompanySalesPersonName() {
        return companySalesPersonName;
    }

    public void setCompanySalesPersonName(String companySalesPersonName) {
        this.companySalesPersonName = companySalesPersonName;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStr_id() {
        return str_id;
    }

    public void setStr_id(String str_id) {
        this.str_id = str_id;
    }

    public String getStr_responce() {
        return str_responce;
    }

    public void setStr_responce(String str_responce) {
        this.str_responce = str_responce;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }





    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getMeeting_type() {
        return meeting_type;
    }

    public void setMeeting_type(String meeting_type) {
        this.meeting_type = meeting_type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    public boolean isHasOrders() {
        return hasOrders;
    }

    public void setHasOrders(boolean hasOrders) {
        this.hasOrders = hasOrders;
    }

    public BeanHistoryVendorName() {
    }

    public BeanHistoryVendorName(String start_time, String end_time, String start_latitude, String start_longitude, String end_latitude, String end_longitude, String start_location, String end_location, String start_battery_status, String end_battery_status, String company_name, String contact_person_name
            , String comments, String attachments1 , String email_id, String mobile_no, String alternate_no, String pincode, String city, String area, String Designation) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.start_latitude = start_latitude;
        this.start_longitude = start_longitude;
        this.end_latitude = end_latitude;
        this.end_longitude = end_longitude;
        this.start_location = start_location;
        this.end_location = end_location;
        this.start_battery_status=start_battery_status;
        this.end_battery_status=end_battery_status;
        this.attachments1=attachments1;
        this.comments=comments;
        this.Designation=Designation;
        this.company_name = company_name;
        this.contact_person_name = contact_person_name;
        this.email_id = email_id;
        this.mobile_no = mobile_no;
        this.alternate_no = alternate_no;
        this.pincode = pincode;
        this.city = city;
        this.area = area;
    }




    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time)
    {
        this.start_time = start_time;
    }

    public String getEnd_time()
    {
        return end_time;
    }

    public void setEnd_time(String end_time) {

        this.end_time = end_time;
    }

    public String getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    public String getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(String start_longitude) {
        this.start_longitude = start_longitude;
    }

    public String getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(String end_latitude) {
        this.end_latitude = end_latitude;
    }

    public String getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }

    public String getStart_battery_status() {
        return start_battery_status;
    }

    public void setStart_battery_status(String start_battery_status) {
        this.start_battery_status = start_battery_status;
    }

    public String getEnd_battery_status() {
        return end_battery_status;
    }

    public void setEnd_battery_status(String end_battery_status) {
        this.end_battery_status = end_battery_status;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getContact_person_name() {
        return contact_person_name;
    }

    public void setContact_person_name(String contact_person_name) {
        this.contact_person_name = contact_person_name;
    }

    public String getAttachments1() {
        return attachments1;
    }

    public void setAttachments1(String attachments1) {
        this.attachments1 = attachments1;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAlternate_no() {
        return alternate_no;
    }

    public void setAlternate_no(String alternate_no) {
        this.alternate_no = alternate_no;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
