package com.nivida.bossb2b;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Ajay on 11/25/2016.
 */

public class AppPref {

    private static final String USER_PREFS = "VENDOR_FILE";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private boolean loged_in_for_vendor;
    private boolean sales_has_user_selected = false;
    boolean loggedIn = false;

    private String Login = "login";
    private String visible = "visible";

    private String selectedUserRole = "";
    private String selectedUserID = "";


    Double route_startLat ;

    public Double getRoute_startLong() {
        return route_startLong;
    }

    public void setRoute_startLong(Double route_startLong) {
        this.route_startLong = route_startLong;
    }

    public Double getRoute_startLat() {
        return route_startLat;
    }

    public void setRoute_startLat(Double route_startLat) {
        this.route_startLat = route_startLat;
    }

    Double route_startLong ;
    String meeting_type="";
    String id="";
    String role_id="";
    String first_name="";
    String middle_name="";
    String last_name="";
    String email_id="";
    String mobile_no="";
    String phone_no="";
    String alternate_no="";
    String password="";
    String status="";
    String user_id="";
    String user_name ="";
    String device_id="";
    String LoginWith= "";
    String gender="";
    String route_start_time ="";
    String firm_shop_name = "";

    String Owner_id= "";
    String Partycode= "";
    String TfatUrl= "";
    String sales_per_id="";
    String com_sales_per_id="";

    String route_id;
    String meetings_id;

    String employee_no;
    String employee_eternity;
    String address_1;
    String address_2;

    String vendor_id;
    String vendorSelectiontime = "";

    String start_lat;
    String start_long;
    String end_lat;
    String end_long;
    String user_type;
    String social_login_id;
    String social_login_responce;

    String get_meetings;
    String meetingStartTime="";
    String selectedVendorName="";
    String new_vendor_name = "";
    String change_password = "";
    String OnSelectedFirmName = "";
    String current_date="";
    String called_date="";
    String comment="";
    String imagePath1 = "";
    String imagePath2 = "";
    String imagePathe3 = "";

    String disComment = "";
    String disImage1 = "";
    String disImage2 = "";
    String disImage3 = "";


    boolean isServicecalled=false;


    boolean routeStarted=false;
    boolean meetingStarted=false;
    boolean placeorderclicked=false;
    boolean onceclicked = false;
    boolean onceReportSaved=false;
    boolean routeEnded=false;
    boolean isNewvendorAdd=false;
    boolean isPasswordChanged=false;

    boolean isAddednewAddress=false;
    float route_startedLattitude;
    float route_startedLongitude;

    float meeting_startedLattitude;
    float meeting_startedLongitude;

    boolean distributorLogin = false;

    boolean listClicked = false;

    String firmShopName = "";

    boolean ownClicked = false;
    boolean retailerClicked = false ;

    String onSelectedUserId = "";



    public AppPref(Context context) {


        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        loggedIn = appSharedPrefs.getBoolean("loggedIn", false);

        loged_in_for_vendor=this.appSharedPrefs.getBoolean("isLoggedInVendor",false);
        loged_in_for_vendor=this.appSharedPrefs.getBoolean("started",false);


        start_lat=appSharedPrefs.getString("start_lat","");

        start_long=appSharedPrefs.getString("start_long","");
        id=appSharedPrefs.getString("id","");
        role_id=appSharedPrefs.getString("role_id","");
        first_name=appSharedPrefs.getString("first_name","");
        middle_name=appSharedPrefs.getString("middle_name","");
        last_name=appSharedPrefs.getString("last_name","");
        email_id=appSharedPrefs.getString("email_id","");
        mobile_no=appSharedPrefs.getString("mobile_no","");
        new_vendor_name=appSharedPrefs.getString("new_vendor","");
        gender=appSharedPrefs.getString("gender","");
        phone_no=appSharedPrefs.getString("phone_no","");
        alternate_no=appSharedPrefs.getString("alternate_no","");
        password=appSharedPrefs.getString("password","");
        status=appSharedPrefs.getString("status","");
        user_id=appSharedPrefs.getString("user_id","");
        user_name = appSharedPrefs.getString("user_name","");
        device_id = appSharedPrefs.getString("device_id" , "");
        LoginWith = appSharedPrefs.getString("login_with" , "");
        Owner_id = appSharedPrefs.getString("owner_id" , "");
        Partycode = appSharedPrefs.getString("part_code" , "");
        OnSelectedFirmName = appSharedPrefs.getString("OnSelectedFirmName" , "");
        TfatUrl = appSharedPrefs.getString("tfat_id" , "");
        user_type = appSharedPrefs.getString("user_type" , "");
        social_login_id = appSharedPrefs.getString("social_login_id" , "");
        social_login_responce = appSharedPrefs.getString("social_login_response" , "");
        selectedUserID = appSharedPrefs.getString("selectedUserID" , "");
        selectedUserRole = appSharedPrefs.getString("selectedUserRole" , "");
        sales_has_user_selected = appSharedPrefs.getBoolean("sales_has_user_selected" , false);

        route_id=appSharedPrefs.getString("route_id","");
        meetings_id=appSharedPrefs.getString("meetings_id","");
        routeStarted=appSharedPrefs.getBoolean("routeStarted",false);
        routeEnded=appSharedPrefs.getBoolean("routeEnded",false);
        get_meetings=appSharedPrefs.getString("get_meetings","");
        vendor_id=appSharedPrefs.getString("vendor_id","");

        employee_no=appSharedPrefs.getString("employee_no","");
        employee_eternity=appSharedPrefs.getString("employee_eternity","");
        address_1=appSharedPrefs.getString("address_1","");
        address_2=appSharedPrefs.getString("address_2","");
        comment=appSharedPrefs.getString("comment" , "");

        meetingStarted=appSharedPrefs.getBoolean("meetingStarted",false);
        placeorderclicked=appSharedPrefs.getBoolean("placeorderclicked",false);
        onceclicked=appSharedPrefs.getBoolean("onceclicked",false);
        isPasswordChanged=appSharedPrefs.getBoolean("isPasswordChanged",false);
        isAddednewAddress=appSharedPrefs.getBoolean("isAddednewAddress",false);
        onceReportSaved=appSharedPrefs.getBoolean("onceReportSaved",false);
        isNewvendorAdd=appSharedPrefs.getBoolean("isNewvendorAdd",false);
        meetingStartTime=appSharedPrefs.getString("meetingStartTime","");
        selectedVendorName=appSharedPrefs.getString("selectedVendorName","");
        change_password=appSharedPrefs.getString("ischange_password","");

        route_startedLattitude = appSharedPrefs.getFloat("route_startedLattitude" , 0);
        route_startedLongitude = appSharedPrefs.getFloat("route_startedLongitude" , 0);

        meeting_startedLattitude = appSharedPrefs.getFloat("meeting_startedLattitude" , 0);
        meeting_startedLongitude = appSharedPrefs.getFloat("meeting_startedLongitude" , 0);

        current_date = appSharedPrefs.getString("current_date" , "");
        called_date = appSharedPrefs.getString("called_date" , "");
        isServicecalled = appSharedPrefs.getBoolean("isServicecalled" , false);

        imagePath1 = appSharedPrefs.getString("imagePath1" , "");
        imagePath2 = appSharedPrefs.getString("imagePath2" , "");
        imagePathe3 = appSharedPrefs.getString("imagePathe3" , "");

        distributorLogin = appSharedPrefs.getBoolean("distributorLogin" , false);
        listClicked = appSharedPrefs.getBoolean("listClicked" , false);

        firmShopName = appSharedPrefs.getString("firmShopName" , "");

        ownClicked = appSharedPrefs.getBoolean("ownClicked" , false);
        retailerClicked = appSharedPrefs.getBoolean("retailerClicked" , false);

        onSelectedUserId = appSharedPrefs.getString("onSelectedUserId" , "");

        disComment = appSharedPrefs.getString("disComment" , "");
        disImage1 = appSharedPrefs.getString("disImage1" , "");
        disImage2 = appSharedPrefs.getString("disImage2" , "");
        disImage3 = appSharedPrefs.getString("disImage3" , "");
    }



    public String getNew_vendor_name() {
        return new_vendor_name;
    }

    public void setNew_vendor_name(String new_vendor_name) {
        this.new_vendor_name = new_vendor_name;
        prefsEditor.putString("new_vendor_name", new_vendor_name).commit();
    }



    public String getVendorSelectiontime() {
        return appSharedPrefs.getString("vendorSelectiontime" , "");
    }

    public void setVendorSelectiontime(String vendorSelectiontime) {
        this.vendorSelectiontime = vendorSelectiontime;
        prefsEditor.putString("vendorSelectiontime" , vendorSelectiontime).commit();
    }




    public String getFirm_shop_name() {
        return firm_shop_name;
    }

    public void setFirm_shop_name(String firm_shop_name) {
        this.firm_shop_name = firm_shop_name;
    }

    public String getOnSelectedFirmName() {
        return appSharedPrefs.getString("OnSelectedFirmName" , "");
    }

    public void setOnSelectedFirmName(String onSelectedFirmName) {
        OnSelectedFirmName = onSelectedFirmName;
        prefsEditor.putString("OnSelectedFirmName" , onSelectedFirmName).commit();
    }

    public String getRoute_start_time() {

        return appSharedPrefs.getString("route_start_time" ,"");
    }

    public void setRoute_start_time(String route_start_time) {
        this.route_start_time = route_start_time;
        prefsEditor.putString("route_start_time",route_start_time).commit();
    }


    public String getMeeting_type() {
        return appSharedPrefs.getString("meeting_type" , "");
    }

    public void setMeeting_type(String meeting_type) {
        this.meeting_type = meeting_type;
        prefsEditor.putString("meeting_type" , meeting_type).commit();
    }

    public String getComment() {
        return appSharedPrefs.getString("comment" , "");
    }

    public void setComment(String comment) {
        this.comment = comment;
        prefsEditor.putString("comment"  ,comment).commit();
    }

    public String getLoginWith() {
        return LoginWith;
    }

    public void setLoginWith(String loginWith) {
        LoginWith = loginWith;
    }


    public String getPartycode() {
        return Partycode;
    }

    public void setPartycode(String partycode) {
        Partycode = partycode;
    }

    public String getTfatUrl() {
        return TfatUrl;
    }

    public void setTfatUrl(String tfatUrl) {
        TfatUrl = tfatUrl;
    }



    public String getCom_sales_per_id() {
        return com_sales_per_id;
    }

    public void setCom_sales_per_id(String com_sales_per_id) {
        this.com_sales_per_id = com_sales_per_id;
    }

    public String getSales_per_id() {
        return sales_per_id;
    }

    public void setSales_per_id(String sales_per_id) {
        this.sales_per_id = sales_per_id;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getSelectedUserRole() {
        return appSharedPrefs.getString("selectedUserRole" , "");
    }

    public void setSelectedUserRole(String selectedUserRole) {
        this.selectedUserRole = selectedUserRole;
        prefsEditor.putString("selectedUserRole" , selectedUserRole).commit();

    }

    public float getRoute_startedLattitude() {
        return appSharedPrefs.getFloat("route_startedLattitude" ,0);
    }

    public void setRoute_startedLattitude(float route_startedLattitude) {
        this.route_startedLattitude = route_startedLattitude;
        prefsEditor.putFloat("route_startedLattitude" , route_startedLattitude).commit();
    }

    public float getRoute_startedLongitude() {
        return appSharedPrefs.getFloat("route_startedLongitude" , 0);
    }

    public void setRoute_startedLongitude(float route_startedLongitude) {
        this.route_startedLongitude = route_startedLongitude;
        prefsEditor.putFloat("route_startedLongitude" , route_startedLongitude).commit();
    }


    public float getMeeting_startedLattitude() {
        return appSharedPrefs.getFloat("meeting_startedLattitude" , 0);
    }

    public void setMeeting_startedLattitude(float meeting_startedLattitude) {
        this.meeting_startedLattitude = meeting_startedLattitude;
        prefsEditor.putFloat("meeting_startedLattitude" , meeting_startedLattitude);
    }

    public float getMeeting_startedLongitude() {
        return appSharedPrefs.getFloat("meeting_startedLongitude" , 0);
    }

    public void setMeeting_startedLongitude(float meeting_startedLongitude) {
        this.meeting_startedLongitude = meeting_startedLongitude;
        prefsEditor.putFloat("meeting_startedLongitude" , meeting_startedLongitude);
    }

    public String getSelectedUserID() {
        return selectedUserID;
    }

    public void setSelectedUserID(String selectedUserID) {
        this.selectedUserID = selectedUserID;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
        prefsEditor.putString("middle_name", middle_name).commit();
    }

    public String getChange_password() {
        return change_password;
    }

    public void setChange_password(String change_password) {
        this.change_password = change_password;
        prefsEditor.putString("change_password" , change_password).commit();
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

    public String getSocial_login_id() {
        return social_login_id;
    }

    public void setSocial_login_id(String social_login_id) {
        this.social_login_id = social_login_id;
    }

    public String getSocial_login_responce() {
        return social_login_responce;
    }

    public void setSocial_login_responce(String social_login_responce) {
        this.social_login_responce = social_login_responce;
    }


    public String getSelectedVendorName() {
        return appSharedPrefs.getString("selectedVendorName","");
    }

    public void setSelectedVendorName(String selectedVendorName) {
        this.selectedVendorName = selectedVendorName;
        prefsEditor.putString("selectedVendorName",selectedVendorName).commit();
    }

    public String getMeetingStartTime() {
        return appSharedPrefs.getString("meetingStartTime","");
    }

    public void setMeetingStartTime(String meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
        prefsEditor.putString("meetingStartTime",meetingStartTime).commit();
    }

    public boolean isPlaceorderclicked() {
        return appSharedPrefs.getBoolean("placeorderclicked" , false);
    }

    public void setPlaceorderclicked(boolean placeorderclicked) {
        this.placeorderclicked = placeorderclicked;
        prefsEditor.putBoolean("placeorderclicked" , placeorderclicked).commit();
    }

    public boolean isOnceclicked() {
        return appSharedPrefs.getBoolean("onceclicked" , false);
    }

    public void setOnceclicked(boolean onceclicked) {
        this.onceclicked = onceclicked;
        prefsEditor.putBoolean("onceclicked" , onceclicked).commit();
    }

    public boolean isRouteStarted() {
        return appSharedPrefs.getBoolean("routeStarted" , false);
    }

    public void setRouteStarted(boolean routeStarted) {
        this.routeStarted = routeStarted;
        prefsEditor.putBoolean("routeStarted" , routeStarted).commit();

    }


    public boolean isPasswordChanged() {
        return appSharedPrefs.getBoolean("isPasswordChanged",false);
    }

    public void setPasswordChanged(boolean passwordChanged) {
        isPasswordChanged = passwordChanged;
        prefsEditor.putBoolean("isPasswordChanged",passwordChanged).commit();
    }

    public boolean isAddednewAddress() {
        return appSharedPrefs.getBoolean("isAddednewAddress" , false);
    }

    public void setAddednewAddress(boolean addednewAddress) {
        isAddednewAddress = addednewAddress;
        prefsEditor.putBoolean("isAddednewAddress" , addednewAddress).commit();
    }

    public boolean isNewvendorAdd() {
        return appSharedPrefs.getBoolean("isNewvendorAdd",false);
    }

    public void setNewvendorAdd(boolean newvendorAdd) {
        this.isNewvendorAdd = newvendorAdd;
        prefsEditor.putBoolean("isNewvendorAdd",newvendorAdd).commit();
    }

    public boolean isMeetingStarted() {
        return appSharedPrefs.getBoolean("meetingStarted",false);
    }

    public void setMeetingStarted(boolean meetingStarted) {
        this.meetingStarted = meetingStarted;
        prefsEditor.putBoolean("meetingStarted", meetingStarted).commit();

    }

    public boolean isRouteEnded() {
        return appSharedPrefs.getBoolean("routeEnded",false);
    }

    public void setRouteEnded(boolean routeEnded) {
        this.routeEnded = routeEnded;
        prefsEditor.putBoolean("routeEnded",routeEnded).commit();
    }

    public boolean isLoggedIn() {
        return appSharedPrefs.getBoolean("loggedIn", false);
    }

    public boolean isSales_has_user_selected() {
        return appSharedPrefs.getBoolean("sales_has_user_selected " ,  false) ;
    }

    public void setSales_has_user_selected(boolean sales_has_user_selected) {
        this.sales_has_user_selected = sales_has_user_selected;
        prefsEditor.putBoolean("sales_has_user_selected", sales_has_user_selected).commit();
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        prefsEditor.putBoolean("loggedIn", loggedIn).commit();

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        prefsEditor.putString("id", id).commit();
    }

    public String getRole_id() {

        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
        prefsEditor.putString("role_id", role_id).commit();
    }


    public String getOwner_id() {
        return Owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.Owner_id = owner_id;
        prefsEditor.putString("owner_id",owner_id).commit();
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
        prefsEditor.putString("first_name", first_name).commit();
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
        prefsEditor.putString("last_name", last_name).commit();
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
        prefsEditor.putString("email_id", email_id).commit();
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
        prefsEditor.putString("mobile_no", mobile_no).commit();
    }

    public String getAlternate_no() {
        return alternate_no;
    }

    public void setAlternate_no(String alternate_no) {
        this.alternate_no = alternate_no;
        prefsEditor.putString("alternate_no", alternate_no).commit();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        prefsEditor.putString("password", password).commit();
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
        prefsEditor.putString("phone_no" , phone_no).commit();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        prefsEditor.putString("id", id).commit();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
        prefsEditor.putString("user_id", user_id).commit();
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
        prefsEditor.putString("route_id", route_id).commit();
    }

    public String getMeetings_id() {
        return appSharedPrefs.getString("meetings_id","");
    }

    public void setMeetings_id(String meetings_id) {
        this.meetings_id = meetings_id;
        prefsEditor.putString("meetings_id", meetings_id).commit();
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
        prefsEditor.putString("employee_no",employee_no).commit();
    }

    public String getEmployee_eternity() {
        return employee_eternity;
    }

    public void setEmployee_eternity(String employee_eternity) {
        this.employee_eternity = employee_eternity;
        prefsEditor.putString("employee_eternity",employee_eternity).commit();
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
        prefsEditor.putString("address_1",address_1).commit();
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
        prefsEditor.putString("address_2",address_2).commit();
    }

    public String getVendor_id() {
        return appSharedPrefs.getString("vendor_id","");
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
        prefsEditor.putString("vendor_id",vendor_id).commit();
    }

    public String getStart_lat() {

        return start_lat;
    }

    public void setStart_lat(String start_lat) {
        this.start_lat = start_lat;

    }

    public String getStart_long() {

        return appSharedPrefs.getString("start_longitude", "");
    }

    public void setStart_long(String start_long) {

        this.start_long = start_long;

    }

    public String getEnd_lat() {
        return appSharedPrefs.getString("end_latitude", "");
    }

    public void setEnd_lat(String end_lat) {
        this.end_lat = end_lat;

    }

    public String getEnd_long() {
        return appSharedPrefs.getString("end_longitude", "");
    }

    public void setEnd_long(String end_long) {
        this.end_long = end_long;

    }

    public String getGet_meetings() {
        return get_meetings;
    }

    public void setGet_meetings(String get_meetings) {
        this.get_meetings = get_meetings;}
    public boolean isLoged_in_for_vendor() {
        return loged_in_for_vendor;
    }

    public void setLoged_in_for_vendor(boolean loged_in_for_vendor) {
        this.loged_in_for_vendor = loged_in_for_vendor;
        //prefsEditor.putBoolean("isLoggedInVendor", loged_in_for_vendor);
        //prefsEditor.commit();
    }
    public void setLogin(String Login1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Login, Login1).commit();

    }

    public String getLogin() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Login, "");
    }

    public void setVisiable(String visiable1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(visible, visiable1).commit();

    }

    public String getVisiable() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(visible, "");
    }

    public String getCurrent_date() {
        return appSharedPrefs.getString("current_date" , "");
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
        prefsEditor.putString("current_date" , current_date).commit();
    }

    public String getCalled_date() {
        return appSharedPrefs.getString("called_date" , "");
    }

    public void setCalled_date(String called_date) {
        this.called_date = called_date;
        prefsEditor.putString("called_date" , called_date).commit();

    }

    public String getImagePath1() {
        return appSharedPrefs.getString("imagePath1" , "");
    }

    public void setImagePath1(String imagePath1) {
        this.imagePath1 = imagePath1;
        prefsEditor.putString("imagePath1" , imagePath1).commit();
    }

    public String getImagePath2() {
        return appSharedPrefs.getString("imagePath2" , "");
    }

    public void setImagePath2(String imagePath2) {
        this.imagePath2 = imagePath2;
        prefsEditor.putString("imagePath2" , imagePath2).commit();
    }

    public String getImagePathe3() {
        return appSharedPrefs.getString("imagePathe3" , "");

    }

    public void setImagePathe3(String imagePathe3) {
        this.imagePathe3 = imagePathe3;
        prefsEditor.putString("imagePathe3" , imagePathe3).commit();
    }


    public boolean isDistributorLogin() {
        return appSharedPrefs.getBoolean("distributorLogin" , false);
    }

    public void setDistributorLogin(boolean distributorLogin) {
        this.distributorLogin = distributorLogin;
        prefsEditor.putBoolean("distributorLogin" , distributorLogin).commit();
    }


    public boolean isListClicked() {
        return appSharedPrefs.getBoolean("listClicked" , false);
    }

    public void setListClicked(boolean listClicked) {
        this.listClicked = listClicked;
        prefsEditor.putBoolean("listClicked" , listClicked).commit();
    }

    public String getFirmShopName() {
        return appSharedPrefs.getString("firmShopName"  , "");
    }

    public void setFirmShopName(String firmShopName) {
        this.firmShopName = firmShopName;
        prefsEditor.putString("firmShopName" , firmShopName).commit();
    }

    public boolean isOwnClicked() {
        return appSharedPrefs.getBoolean("ownClicked" , false);
    }

    public void setOwnClicked(boolean ownClicked) {
        this.ownClicked = ownClicked;
        prefsEditor.putBoolean("ownClicked" , ownClicked).commit();
    }

    public boolean isRetailerClicked() {
        return appSharedPrefs.getBoolean("retailerClicked"  , false);
    }

    public void setRetailerClicked(boolean retailerClicked) {
        this.retailerClicked = retailerClicked;
        prefsEditor.putBoolean("retailerClicked" , retailerClicked).commit();
    }

    public String getOnSelectedUserId() {
        return appSharedPrefs.getString("onSelectedUserId" , "");
    }

    public void setOnSelectedUserId(String onSelectedUserId) {
        this.onSelectedUserId = onSelectedUserId;
        prefsEditor.putString("onSelectedUserId"  , onSelectedUserId).commit();
    }

    public String getDisComment() {
        return appSharedPrefs.getString("disComment" , "");
    }

    public void setDisComment(String disComment) {
        this.disComment = disComment;
        prefsEditor.putString("disComment" , disComment).commit();
    }

    public String getDisImage1() {
        return appSharedPrefs.getString("disImage1" , "");
    }

    public void setDisImage1(String disImage1) {
        this.disImage1 = disImage1;
        prefsEditor.putString("disImage1" , disImage1).commit();
    }

    public String getDisImage2() {
        return appSharedPrefs.getString("disImage2" , "");
    }

    public void setDisImage2(String disImage2) {
        this.disImage2 = disImage2;
        prefsEditor.putString("disImage2" , disImage2).commit();
    }

    public String getDisImage3() {
        return appSharedPrefs.getString("disImage3" , "");
    }

    public void setDisImage3(String disImage3) {
        this.disImage3 = disImage3;
        prefsEditor.putString("disImage3" , disImage3).commit();
    }

    public void clearData(Context context){


        SharedPreferences.Editor prefs = context.getSharedPreferences(USER_PREFS , 0).edit();
        prefs.clear();
        prefs.commit();

        Log.e("Congo" , "Clear All Data");


    }



}
