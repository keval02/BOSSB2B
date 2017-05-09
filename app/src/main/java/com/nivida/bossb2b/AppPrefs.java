package com.nivida.bossb2b;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class AppPrefs {


    private static final String USER_PREFS = "USER_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;


    private String CategoryCheck = "Category_check";
    private String user_notification = "user_email_prefs";
    private String user_Loginwith = "user_Loginwith";
    private String PersonalInfo = "PersonalInfo";
    private String LoginInfo = "LoginInfo";
    private String SocialIdInfo = "SocialIdInfo";
    private String SocialReInfo = "SocialReInfo";
    private String User_SocialFirst = "User_SocialFirst";
    private String User_Sociallast = "User_Sociallast";
    private String User_Socialemail = "User_Socialemail";
    private String Categoryid = "Categoryid";
    private String jobid = "jobid";
    private String jobname = "jobname";
    private String product_id = "product_id";
    private String youtube_api = "youtube_api";
    private String add_newGroup = "add_newGroup";
    private String Ref_Shopping = "Ref_Shopping";

    private String Sort_Radio = "sortradio";
    private String Page_Data = "pagedata";
    private String detail_response = "detail_response";

    private String Filter_Sort = "filtersort";
    private String Filter_Option = "filteroption";
    private String Filter_SubCat = "filtersubCat";
    private String Filter_Cat = "filtercat";
    private String cart_grand_total = "cart_grand_total";
    private String wishid = "wishid";
    private String list_id = "list_id";
    private String slider_url = "slider_url";
    private String Ref_login = "Ref_login";

    private boolean sales_has_user_selected = false;

    private String order_history_filter_order_status = "order_history_filter_order_status";
    private String order_history_filter_from_date = "order_history_filter_from_date";
    private String order_history_filter_to_date = "order_history_filter_to_date";
    private String order_history_filter_predefine = "order_history_filter_predefine";
    private String Product_Sort = "productsort";
    private String order_history_id = "order_history_id";

    private String userRoleId = "2";
    private String distributorId = "";
    private String currentPage = "";
    private String selectedUserRole = "";
    private String selectedUserID = "";
    private String login_userid = "";
    private String owner_id = "";
    private String fromActivity = "";

    private String comment = "";
    private String scheme = "";
    private String attachment = "";

    private String partycode = "";
    private String TfatUrl = "";

    private boolean hasPromoCode=false;
    private String promocodevalue="";
    private String promoMinValue="";


    private String has_subcategroy = "has_subcategroy";
    private String searchFilter="searchFilter";

    public AppPrefs(Context context) {
        // TODO Auto-generated constructor stub
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        userRoleId = appSharedPrefs.getString("userRoleId", "");
        distributorId = appSharedPrefs.getString("distributorId", "");
        currentPage = appSharedPrefs.getString("currentPage", "");
        selectedUserRole = appSharedPrefs.getString("selectedUserRole", "");
        login_userid = appSharedPrefs.getString("login_userid", "");
        sales_has_user_selected = appSharedPrefs.getBoolean("sales_has_user_selected", false);
        owner_id = appSharedPrefs.getString("owner_id", "");
        selectedUserID = appSharedPrefs.getString("selectedUserID", "");
        fromActivity = appSharedPrefs.getString("fromActivity", "");
        comment = appSharedPrefs.getString("comment", "");
        scheme = appSharedPrefs.getString("scheme", "");
        attachment = appSharedPrefs.getString("attachment", "");
        partycode = appSharedPrefs.getString("partycode", "");
        TfatUrl = appSharedPrefs.getString("tfatUrl", "");
        hasPromoCode = appSharedPrefs.getBoolean("hasPromoCode", false);
        promocodevalue = appSharedPrefs.getString("promocodevalue", "");
        promoMinValue = appSharedPrefs.getString("promoMinValue", "");
        searchFilter = appSharedPrefs.getString("searchFilter", "");
    }

    public String getSearchFilter() {
        return appSharedPrefs.getString("searchFilter", "");
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
        prefsEditor.putString("searchFilter", searchFilter).commit();
    }

    public String getLogin_userid() {
        return appSharedPrefs.getString("login_userid", "");
    }

    public void setLogin_userid(String login_userid) {
        this.login_userid = login_userid;
        prefsEditor.putString("login_userid", login_userid).commit();
    }

    public void setUser_notification(String notification) {
        // TODO Auto-generated method stub
        prefsEditor.putString(user_notification, notification).commit();

    }

    public String getUser_notification() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(user_notification, "");
    }

    public void setUser_LoginWith(String Loginwith) {
        // TODO Auto-generated method stub
        prefsEditor.putString(user_Loginwith, Loginwith).commit();

    }

    public String getUser_LoginWith() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(user_Loginwith, "");
    }

    public void setRef_Shopping(String Ref_Shopping1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Ref_Shopping, Ref_Shopping1).commit();

    }

    public String getRef_Shopping() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Ref_Shopping, "");
    }

    public void setRef_login(String Ref_login1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Ref_login, Ref_login1).commit();

    }

    public String getRef_login() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Ref_login, "");
    }

    public void setUser_PersonalInfo(String PersonalInfo1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(PersonalInfo, PersonalInfo1).commit();

    }

    public String getdetail_response() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(detail_response, "");
    }

    public void setdetail_response(String detail_response1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(detail_response, detail_response1).commit();

    }

    public String getUser_PersonalInfo() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(PersonalInfo, "");
    }

    public void setUser_LoginInfo(String LoginInfo1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(LoginInfo, LoginInfo1).commit();

    }

    public String getUser_LoginInfo() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(LoginInfo, "");
    }

    public void setUser_SocialIdInfo(String SocialIdInfo1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(SocialIdInfo, SocialIdInfo1).commit();

    }

    public String getUser_SocialIdInfo() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(SocialIdInfo, "");
    }

    public void setUser_SocialReInfo(String SocialReInfo1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(SocialReInfo, SocialReInfo1).commit();

    }

    public String getUser_SocialReInfo() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(SocialReInfo, "");
    }

    public void setUser_SocialFirst(String User_SocialFirst1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(User_SocialFirst, User_SocialFirst1).commit();

    }

    public String getUser_SocialFirst() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(User_SocialFirst, "");
    }

    public void setUser_Sociallast(String User_SocialFirst1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(User_Sociallast, User_SocialFirst1).commit();

    }

    public String getUser_Sociallast() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(User_Sociallast, "");


    }

    public void setUser_Socialemail(String User_Socialemail1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(User_Socialemail, User_Socialemail1).commit();

    }

    public String getUser_Socialemail() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(User_Socialemail, "");
    }

    public void setCategoryid(String Categoryid1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Categoryid, Categoryid1).commit();

    }

    public String getCategoryid() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Categoryid, "");
    }

    public void setjobid(String jobid1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(jobid, jobid1).commit();

    }

    public String getjobid() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(jobid, "");
    }

    public void setjobname(String jobname1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(jobname, jobname1).commit();

    }

    public String getjobname() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(jobname, "");
    }

    public void setproduct_id(String product_id1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(product_id, product_id1).commit();

    }

    public String getproduct_id() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(product_id, "");
    }

    public void setyoutube_api(String youtube_api1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(youtube_api, youtube_api1).commit();

    }

    public String getyoutube_api() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(youtube_api, "");
    }

    public void setadd_newGroup(String add_newGroup1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(add_newGroup, add_newGroup1).commit();

    }

    public String getadd_newGroup() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(add_newGroup, "");
    }

    public void setSort_Radio(String Sort_Radio1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Sort_Radio, Sort_Radio1).commit();

    }

    public String getSort_Radio() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Sort_Radio, "");
    }


    public void setPage_Data(String Page_Data1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Page_Data, Page_Data1).commit();

    }

    public String getPage_Data() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Page_Data, "");
    }


    public void setFilter_Sort(String Filter_Sort1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Filter_Sort, Filter_Sort1).commit();

    }

    public String getFilter_Sort() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Filter_Sort, "");
    }

    public void setFilter_Option(String Filter_Option1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Filter_Option, Filter_Option1).commit();

    }

    public String getFilter_Option() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Filter_Option, "");
    }

    public void setFilter_SubCat(String Filter_SubCat1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Filter_SubCat, Filter_SubCat1).commit();

    }

    public String getFilter_SubCat() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Filter_SubCat, "");
    }

    public void setFilter_Cat(String Filter_Cat1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Filter_Cat, Filter_Cat1).commit();

    }

    public String getFilter_Cat() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Filter_Cat, "");
    }

    public void setProduct_Sort(String Product_Sort1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Product_Sort, Product_Sort1).commit();

    }

    public String getProduct_Sort() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Product_Sort, "");
    }

    public void setCart_Grand_total(String total) {
        // TODO Auto-generated method stub
        prefsEditor.putString(cart_grand_total, total).commit();

    }

    public String getCart_Grand_total() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(cart_grand_total, "");
    }

    public void setwishid(String wishid1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(wishid, wishid1).commit();

    }

    public String getwishid() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(wishid, "");
    }

    public void setlist_id(String list_id1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(list_id, list_id1).commit();

    }

    public String getlist_id() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(list_id, "");
    }

    public void setslider_url(String slider_url1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(slider_url, slider_url1).commit();

    }

    public String getslider_url() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(slider_url, "");
    }

    public void setOrder_history_id(String id) {
        // TODO Auto-generated method stub
        prefsEditor.putString(order_history_id, id).commit();

    }

    public String getOrder_history_id() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(order_history_id, "");
    }

    public void setOrder_history_filter_order_status(String status) {
        // TODO Auto-generated method stub
        prefsEditor.putString(order_history_filter_order_status, status).commit();

    }

    public String getOrder_history_filter_order_status() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(order_history_filter_order_status, "");
    }

    public void setOrder_history_filter_from_date(String date) {
        // TODO Auto-generated method stub
        prefsEditor.putString(order_history_filter_from_date, date).commit();

    }

    public String getOrder_history_filter_from_date() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(order_history_filter_from_date, "");
    }

    public void setOrder_history_filter_to_date(String date) {
        // TODO Auto-generated method stub
        prefsEditor.putString(order_history_filter_to_date, date).commit();

    }

    public String getOrder_history_filter_to_date() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(order_history_filter_to_date, "");
    }

    public void setOrder_history_filter_predefine(String predefine) {
        // TODO Auto-generated method stub
        prefsEditor.putString(order_history_filter_predefine, predefine).commit();

    }

    public String getOrder_history_filter_predefine() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(order_history_filter_predefine, "");
    }

    public void setHas_Subcategroy(boolean predefine) {
        // TODO Auto-generated method stub
        prefsEditor.putBoolean(has_subcategroy, predefine).commit();

    }

    public boolean getHas_Subcategroy() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getBoolean(has_subcategroy, false);
    }

    public String getCategoryCheck() {
        return appSharedPrefs.getString(CategoryCheck, "");
    }

    public void setCategoryCheck(String categoryCheck) {
        CategoryCheck = categoryCheck;
    }

    public String getUserRoleId() {
        return appSharedPrefs.getString("userRoleId", "");
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
        prefsEditor.putString("userRoleId", userRoleId).commit();
    }

    public String getDistributorId() {
        return appSharedPrefs.getString("distributorId", "");
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
        prefsEditor.putString("distributorId", distributorId).commit();
    }

    public String getCurrentPage() {
        return appSharedPrefs.getString("currentPage", "");
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
        prefsEditor.putString("currentPage", currentPage).commit();
    }

    public String getSelectedUserRole() {
        return appSharedPrefs.getString("selectedUserRole", "");
    }

    public void setSelectedUserRole(String selectedUserRole) {
        this.selectedUserRole = selectedUserRole;
        prefsEditor.putString("selectedUserRole", selectedUserRole).commit();
    }

    public boolean isSales_has_user_selected() {
        return appSharedPrefs.getBoolean("sales_has_user_selected", false);
    }

    public void setSales_has_user_selected(boolean sales_has_user_selected) {
        this.sales_has_user_selected = sales_has_user_selected;
        prefsEditor.putBoolean("sales_has_user_selected", sales_has_user_selected).commit();
    }

    public String getOwner_id() {
        return appSharedPrefs.getString("owner_id", "");
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
        prefsEditor.putString("owner_id", owner_id).commit();
    }

    public String getSelectedUserID() {
        return appSharedPrefs.getString("selectedUserID", "");
    }

    public void setSelectedUserID(String selectedUserID) {
        this.selectedUserID = selectedUserID;
        prefsEditor.putString("selectedUserID", selectedUserID).commit();
    }

    public String getFromActivity() {
        return appSharedPrefs.getString("fromActivity", "");
    }

    public void setFromActivity(String fromActivity) {
        this.fromActivity = fromActivity;
        prefsEditor.putString("fromActivity", fromActivity).commit();
    }

    public String getComment() {
        return appSharedPrefs.getString("comment", "");
    }

    public void setComment(String comment) {
        this.comment = comment;
        prefsEditor.putString("comment", comment).commit();
    }

    public String getScheme() {
        return appSharedPrefs.getString("scheme", "");
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
        prefsEditor.putString("scheme", scheme).commit();
    }

    public String getAttachment() {
        return appSharedPrefs.getString("attachment", "");
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
        prefsEditor.putString("attachment", attachment).commit();
    }

    public String getTfatUrl() {
        return appSharedPrefs.getString("tfatUrl", "");
    }

    public void setTfatUrl(String tfatUrl) {
        TfatUrl = tfatUrl;
        prefsEditor.putString("tfatUrl", tfatUrl).commit();
    }

    public String getPartycode() {
        return appSharedPrefs.getString("partycode", "");
    }

    public void setPartycode(String partycode) {
        this.partycode = partycode;
        prefsEditor.putString("partycode", partycode).commit();
    }

    public boolean isHasPromoCode() {
        return appSharedPrefs.getBoolean("hasPromoCode", false);
    }

    public void setHasPromoCode(boolean hasPromoCode) {
        this.hasPromoCode = hasPromoCode;
        prefsEditor.putBoolean("hasPromoCode", hasPromoCode).commit();
    }

    public String getPromocodevalue() {
        return appSharedPrefs.getString("promocodevalue", "");
    }

    public void setPromocodevalue(String promocodevalue) {
        this.promocodevalue = promocodevalue;
        prefsEditor.putString("promocodevalue", promocodevalue).commit();
    }

    public String getPromoMinValue() {
        return appSharedPrefs.getString("promoMinValue", "");
    }

    public void setPromoMinValue(String promoMinValue) {
        this.promoMinValue = promoMinValue;
        prefsEditor.putString("promoMinValue", promoMinValue).commit();
    }
}
