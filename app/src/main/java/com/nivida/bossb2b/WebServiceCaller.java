package com.nivida.bossb2b;

import android.app.Activity;
import android.util.Log;

import com.nivida.bossb2b.Bean.Bean_Area;
import com.nivida.bossb2b.Bean.Bean_DistSales__Plus_Retailer;
import com.nivida.bossb2b.Bean.Bean_DistributorPerson;
import com.nivida.bossb2b.Bean.Bean_RetailerPerson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajay on 12/28/2016.
 */

public class WebServiceCaller {

    String jsonData="";

    public WebServiceCaller(Activity activity){

    }

    public static boolean CheckUniqueMail(final String email, final String API){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        boolean isNewEmail=false;

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("email_id", "" + email));

                    //Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                    Log.e("", "" + email);
                    Log.e("",""+parameters);

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link+API,ServiceHandler.POST,parameters);
                    //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();

        while (notDone[0]){

        }

        try {
            JSONObject jsonObject=new JSONObject(json[0]);
            if(jsonObject.getBoolean("status")){
                isNewEmail=true;
            }
            else {
                isNewEmail=false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isNewEmail;
    }

    public static boolean CheckUniqueMobile(final String phone,final String user_id){

        final String[] json = new String[1];
        final boolean[] notDone = {true};
        boolean isNewMobile=false;

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("phone_no", "" + phone));

                    if(!user_id.equalsIgnoreCase("0"))
                        parameters.add(new BasicNameValuePair("user_id",user_id));

                    //Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                    Log.e("params",""+parameters.toString());

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link+GlobalVariable.API_CHECK_UNIQUPHONE,ServiceHandler.POST,parameters);
                    //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();

        while (notDone[0]){

        }

        try {
            JSONObject jsonObject=new JSONObject(json[0]);
            if(jsonObject.getBoolean("status")){
                isNewMobile=true;
            }
            else {
                isNewMobile=false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isNewMobile;
    }

    public static List<Bean_RetailerPerson> getAllReatilerByDistributor(final String distributorID){
        List<Bean_RetailerPerson> retailerPersonList=new ArrayList<>();
        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("distributor_id", distributorID));

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link+GlobalVariable.API_GET_DISTRIBUTOR_RETAILER,ServiceHandler.POST,parameters);
                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();

        while (notDone[0]){

        }
        if(!json[0].equalsIgnoreCase("")){
            Log.e("JSON","Full");
            try {
                JSONObject mainObject=new JSONObject(json[0]);
                if(mainObject.getBoolean("status")){
                    Log.e("JSON","True");
                    JSONArray dataArray=mainObject.getJSONArray("data");
                    Log.e("JSON",""+dataArray.length());
                    retailerPersonList.clear();

                    for(int i=0; i< dataArray.length(); i++){
                        Bean_RetailerPerson retailerPerson=new Bean_RetailerPerson();
                        JSONObject mainObjectData=dataArray.getJSONObject(i);

                        Log.e("JSON",mainObjectData.toString());

                        JSONObject userObj=mainObjectData.getJSONObject("User");
                        retailerPerson.setRetailer_id(C.getInt(userObj.getString("id")));
                        retailerPerson.setRole_id(C.getInt(userObj.getString("role_id")));
                        retailerPerson.setEmailid(userObj.getString("email_id"));
                        retailerPerson.setPhone_no(userObj.getString("phone_no"));
                        retailerPerson.setFirstName(userObj.getString("first_name"));
                        retailerPerson.setLastName(userObj.getString("last_name"));
                        retailerPerson.setDistributor_id(C.getInt(userObj.getString("owner_id")));
                        retailerPerson.setStatus(C.getInt(userObj.getString("status")));

                        JSONObject addr=mainObjectData.getJSONObject("Address");
                        retailerPerson.setFirmAddress(addr.getString("address_1"));
                        retailerPerson.setPincode(addr.getString("pincode"));
                        retailerPerson.setCountry_id(addr.getString("country_id"));
                        retailerPerson.setState_id(addr.getString("state_id"));
                        retailerPerson.setCity_id(addr.getString("city_id"));
                        retailerPerson.setArea_id(addr.getString("area_id"));

                        JSONObject detail=mainObjectData.getJSONObject("Distributor");
                        retailerPerson.setFirmName(detail.getString("firm_shop_name"));
                        retailerPerson.setPANID(detail.getString("pan_no_of_company"));
                        retailerPerson.setVAT_TINid(detail.getString("vat_tin_no"));
                        retailerPerson.setCST_GSTid(detail.getString("cst_gst_no"));

                        retailerPersonList.add(retailerPerson);
                    }

                    Log.e("Size",retailerPersonList.size()+"");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("eXCEption",e.getMessage());
            }
        }

        return retailerPersonList;
    }

    public static String AddDistSalesPerson(final List<NameValuePair> params){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_ADD_DISTRIBUTOR_SALES,ServiceHandler.POST,params);

                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });

        thread.start();
        while (notDone[0]){
            Log.e("AddDistSalesPerson",""+notDone[0]);
        }
        return json[0];
    }

    public static String EditDistSalesPerson(final List<NameValuePair> params){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_EDIT_DISTSALES,ServiceHandler.POST,params);

                    Log.e("params",params.toString());

                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){

        }
        return json[0];
    }

    public static ArrayList<String> getRetailersBySalesPerson(final String sales_id){
        ArrayList<String> retailers=new ArrayList<>();
        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<NameValuePair> params=new ArrayList<>();
                    params.add(new BasicNameValuePair("sales_id",sales_id));

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_GET_RETAILER_BY_DISSALES,ServiceHandler.POST,params);

                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){
            Log.e("AddDistSalesPerson",""+notDone[0]);
        }
        try {
            JSONObject mainObject=new JSONObject(json[0]);

            if(mainObject.getBoolean("status")){
                JSONArray data=mainObject.getJSONArray("data");

                for(int i=0; i<data.length(); i++){
                    JSONObject salesObject=data.getJSONObject(i);
                    JSONObject salesPerson=salesObject.getJSONObject("SalesPersonDistributor");
                    retailers.add(salesPerson.getString("distributor_id"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Excep JSON getRetailer",e.getMessage());
        }
        return retailers;
    }

    public static Bean_DistSales__Plus_Retailer GetListDistributorRetailerByComSales(final List<NameValuePair> params){

        Bean_DistSales__Plus_Retailer sales__plusRetailers=new Bean_DistSales__Plus_Retailer();
        List<Bean_DistributorPerson> distributorPersonList =new ArrayList<>();
        List<Bean_RetailerPerson> retailerPersonList =new ArrayList<>();
        List<Bean_Area> areaList=new ArrayList<>();

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_GET_DISTSALES_PLUS_RETAILER_BYCOM,ServiceHandler.POST,params);

                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){
            //Log.e("AddDistSalesPerson",""+notDone[0]);
        }

        Log.e("json",json[0]);

        try {
            JSONObject mainObj=new JSONObject(json[0]);
            if(mainObj.getBoolean("status")){

                JSONArray dataArray=mainObj.getJSONArray("data");

                for (int i=0; i<dataArray.length(); i++){
                    JSONObject subObj=dataArray.getJSONObject(i);

                    JSONObject userObject=subObj.getJSONObject("User");

                    if(userObject.getString("role_id").equalsIgnoreCase(C.DISTRIBUTOR_ROLE)){
                        Bean_DistributorPerson distributorPerson=new Bean_DistributorPerson();
                        distributorPerson.setDistributor_id(C.getInt(userObject.getString("id")));
                        distributorPerson.setRole_id(C.getInt(userObject.getString("role_id")));
                        distributorPerson.setEmailid(userObject.getString("email_id"));
                        distributorPerson.setPhone_no(userObject.getString("phone_no"));
                        distributorPerson.setFirstName(userObject.getString("first_name"));
                        distributorPerson.setLastName(userObject.getString("last_name"));
                        distributorPerson.setPassword(userObject.getString("password"));
                        distributorPerson.setStatus(C.getInt(userObject.getString("status")));

                        JSONObject addrObj=subObj.getJSONObject("Address");
                        distributorPerson.setFirmAddress(addrObj.getString("address_1"));
                        distributorPerson.setPincode(addrObj.getString("pincode"));
                        distributorPerson.setCountry_id(addrObj.getString("country_id"));
                        distributorPerson.setState_id(addrObj.getString("state_id"));
                        distributorPerson.setCity_id(addrObj.getString("city_id"));
                        distributorPerson.setArea_id(addrObj.getString("area_id"));

                        JSONObject distObj=subObj.getJSONObject("Distributor");
                        distributorPerson.setFirmName(distObj.getString("firm_shop_name"));

                        distributorPersonList.add(distributorPerson);
                    }
                    else if(userObject.getString("role_id").equalsIgnoreCase(C.DIS_RETAILER_ROLE)){
                        Bean_RetailerPerson retailerPerson=new Bean_RetailerPerson();
                        retailerPerson.setRetailer_id(C.getInt(userObject.getString("id")));
                        retailerPerson.setRole_id(C.getInt(userObject.getString("role_id")));
                        retailerPerson.setEmailid(userObject.getString("email_id"));
                        retailerPerson.setPhone_no(userObject.getString("phone_no"));
                        retailerPerson.setFirstName(userObject.getString("first_name"));
                        retailerPerson.setLastName(userObject.getString("last_name"));
                        retailerPerson.setPassword(userObject.getString("password"));
                        retailerPerson.setStatus(C.getInt(userObject.getString("status")));
                        retailerPerson.setDistributor_id(C.getInt(userObject.getString("owner_id")));

                        JSONObject addrObj=subObj.getJSONObject("Address");
                        retailerPerson.setFirmAddress(addrObj.getString("address_1"));
                        retailerPerson.setPincode(addrObj.getString("pincode"));
                        retailerPerson.setCountry_id(addrObj.getString("country_id"));
                        retailerPerson.setState_id(addrObj.getString("state_id"));
                        retailerPerson.setCity_id(addrObj.getString("city_id"));
                        retailerPerson.setArea_id(addrObj.getString("area_id"));

                        JSONObject distObj=subObj.getJSONObject("Distributor");
                        retailerPerson.setFirmName(distObj.getString("firm_shop_name"));

                        retailerPersonList.add(retailerPerson);
                    }
                }

                JSONArray areaArray=mainObj.getJSONArray("area");

                for(int i=0; i<areaArray.length(); i++){
                    JSONObject areaObj=areaArray.getJSONObject(i);

                    Bean_Area area=new Bean_Area();
                    area.setArea_id(areaObj.getString("id"));
                    area.setArea_name(areaObj.getString("name"));

                    Log.e("Area ID",areaObj.getString("id"));
                    Log.e("Area Name",areaObj.getString("name"));
                    areaList.add(area);
                }

                Log.e("Area Size",areaList.size()+"");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sales__plusRetailers.setDistributorPersonsList(distributorPersonList);
        sales__plusRetailers.setRetailerPersonList(retailerPersonList);
        sales__plusRetailers.setAreaList(areaList);

        return sales__plusRetailers;
    }

    public static Bean_DistSales__Plus_Retailer GetDistSalesRetailerList(final List<NameValuePair> params){

        Bean_DistSales__Plus_Retailer sales__plusRetailers=new Bean_DistSales__Plus_Retailer();
        List<Bean_RetailerPerson> retailerPersonList =new ArrayList<>();
        List<Bean_Area> areaList=new ArrayList<>();

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_GET_RETAILERS_BY_DISSALES,ServiceHandler.POST,params);

                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){
            //Log.e("AddDistSalesPerson",""+notDone[0]);
        }

        Log.e("json",json[0]);

        try {
            JSONObject mainObj=new JSONObject(json[0]);
            if(mainObj.getBoolean("status")){

                JSONArray dataA=mainObj.getJSONArray("data");

                for(int i=0; i< dataA.length(); i++){
                    JSONObject subObj=dataA.getJSONObject(i);

                    Bean_RetailerPerson retailer=new Bean_RetailerPerson();

                    JSONObject user=subObj.getJSONObject("User");
                    retailer.setRetailer_id(C.getInt(user.getString("id")));
                    retailer.setRole_id(C.getInt(user.getString("role_id")));
                    retailer.setEmailid(user.getString("email_id"));
                    retailer.setPhone_no(user.getString("phone_no"));
                    retailer.setFirstName(user.getString("first_name"));
                    retailer.setLastName(user.getString("last_name"));
                    retailer.setStatus(C.getInt(user.getString("status")));

                    JSONObject addr=subObj.getJSONObject("Address");
                    retailer.setFirmAddress(addr.getString("address_1"));
                    retailer.setCountry_id(addr.getString("country_id"));
                    retailer.setState_id(addr.getString("state_id"));
                    retailer.setCity_id(addr.getString("city_id"));
                    retailer.setArea_id(addr.getString("area_id"));
                    retailer.setPincode(addr.getString("pincode"));

                    JSONObject dist=subObj.getJSONObject("Distributor");
                    retailer.setFirmName(dist.getString("firm_shop_name"));

                    retailerPersonList.add(retailer);
                }

                Log.e("list size",retailerPersonList.size()+"");


                JSONArray areaA=mainObj.getJSONArray("area");

                for(int i=0; i<areaA.length(); i++){
                    JSONObject subO=areaA.getJSONObject(i);

                    Bean_Area area=new Bean_Area();
                    area.setArea_id(subO.getString("id"));
                    area.setArea_name(subO.getString("name"));

                    areaList.add(area);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sales__plusRetailers.setRetailerPersonList(retailerPersonList);
        sales__plusRetailers.setAreaList(areaList);

        return sales__plusRetailers;
    }

    public static String Get_UserDetail(final List<NameValuePair> params){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_GET_USER_DETAIL,ServiceHandler.POST,params);

                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){

        }
        return json[0];
    }

    public static String Get_ReasonList(){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        final List<NameValuePair> params=new ArrayList<>();

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link + GlobalVariable.API_GET_LIST_SKIPORDER_REASON,ServiceHandler.POST,params);

                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){

        }
        return json[0];
    }

    public static String GetProductDetailByCode(final List<NameValuePair> params){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(GlobalVariable.link + "Product/App_Get_Product_Details",ServiceHandler.POST,params);

                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){

        }
        return json[0];
    }



}
