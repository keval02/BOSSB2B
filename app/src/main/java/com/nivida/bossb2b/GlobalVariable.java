package com.nivida.bossb2b;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GlobalVariable {

    public static String UserName;
    public static String Password;
    public static String PortalName;
    public static String deviceSN;
    public static String deviceGCM;
    /** Checking the internet is available or not */
    public static boolean isConnected = true;

    /** Setting DEBUG to false */
    public static final boolean DEBUG = false;

    public static final String UserPreference = "Pref";
    public static final String UserType = "User";
    public static String DEVICEID_PUSHWOOSH;
    public static String window = "window_end";

    public static String share = "https://play.google.com/store/apps/details?id=com.nivida.user.Boss&hl=en";
    public static String link2 = "http://192.168.1.221/boss/";
   // public static String link = "http://www.bossindia.com/";
   public static final String link="http://demo.bossindia.com/";

    public static String API_GET_DISTRIBUTOR_SALESPERSON="User/App_GetDistributorSalesPerson";
    public static String API_GET_DISTRIBUTOR_RETAILER="User/App_GetRetailer";
    public static String API_ADD_DISTRIBUTOR_RETAILER="User/App_AddRetailer";
    public static String API_ADD_DISTRIBUTOR_SALES="User/App_AddDistributorSalesPerson";
    public static String API_GET_COUNTRIES="Country/App_GetCountry";
    public static String API_GET_STATES="State/App_GetState";
    public static String API_GET_CITIES="City/App_GetCity";
    public static String API_GET_AREAS="Area/App_GetArea";


    public static String API_DELETE_RETAILER="User/App_DeleteData";
    public static String API_EDIT_RETAILER="User/App_EditRetailer";
    public static String API_EDIT_DISTSALES="User/App_EditDistributorSalesPerson";
    public static String API_GET_RETAILER_BY_DISSALES="User/App_GetDistributorSalesPersonRetailer";
    public static String API_GET_DISTSALES_PLUS_RETAILER_BYCOM="User/App_Get_Distributor_Retailer_By_Company_Sales_Person";

    public static String API_GET_RETAILER_BY_DISSALES_SELECTED="User/App_GetListRetailerByDistSales";

    public static String API_CHECK_UNIQUEMAIL="User/App_CheckUniqueEmail";
    public static String API_CHECK_UNIQUPHONE="User/App_CheckUniqueMobile";

    public static String API_CHECK_COUPONCODE="Coupon/App_get_coupon_data";

    public static String API_GET_ORDERSTATUS="Order/App_OrderResponse";

    public static String API_GET_USER_DETAIL="User/App_Get_User_Info";

    public static String API_GET_LIST_SKIPORDER_REASON="OrderSkipReason/App_GetOrderSkipReason";
    public static String API_SKIP_ADDORDER="OrderReason/App_AddOrderReason";


    public static String API_ADD_B2B_ORDER="Order/App_Add_B2B_Order";
    public static String API_ADD_B2B_ORDER_SECOND="Order/App_Add_B2B_Orders_Second";
    public static String API_ADD_B2B_ORDER_THIRD="Order/App_Add_B2B_Orders_Third";
    public static String API_ADD_B2B_ORDER_FOURTH="Order/App_Add_B2B_Orders_Fourth";
    public static String API_ADD_B2B_ORDER_FIFTH="Order/App_Add_B2B_Orders_Fifth";
    public static String API_GET_RETAILERS_BY_DISSALES="User/App_Get_List_Retailer_By_Distributor_Sales";

    public static String API_GET_B2B_ORDER="Order/App_B2B_Get_Order";
    public static String API_GET_B2B_DIST_SALES_ORDER="Order/App_B2B_Dist_Sales_Get_Order";

    public static String API_GET_SKIPORDER_HISTORY="OrderReason/App_GetSkipOrder";

    public static String API_GET_TRACK_ORDER="OrderHistory/App_B2B_Track_Order";

    public static String API_GET_TFAT_REPORT="/api/TFATOrder/ViewOutstanding";
    public static String API_GET_TFAT_LEDGER="/api/TFATOrder/ViewLedger";
    public static String API_GET_TFAT_ORDERVIEW="/api/TFATOrder/ViewOrder";

    public static String HTTP="http://";


    public static String link11 = "http://www.bossindia.com/files/";

   /* public static String link = "http://app.nivida.in/boss/";
    public static String link11 = "http://app.nivida.in/boss/files/";*/

   // public static String link ="http://192.168.1.109/agraeta/";


    public static String imglink =  link + "files/";

    public static String DISTRIBUTOR = "";
    public static String RETAILER = "";
    public static String RETAILER_SALES_MEN = "";
    public static String DISTRIBUTOR_SALES_MEN = "";



   public static void CustomToast(Context context, String st,
                                   LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.custom_toast, null);

        TextView text = (TextView) view.findViewById(R.id.toasttext);
        text.setText(st);
        text.setTextSize(14);
		//text.setTypeface(GlobalVariable.app_font());
//        text.setTypeface(GlobalVariable.app_font(context));
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();

    }

  /*  public static BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("BroadCast", "Is Connected =" + isConnected);
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            Log.i("BroadCastReceiver", "Connected =" + isConnected);
            if (ni != null && ni.isConnected()) {
                isConnected = true;
            } else {
                isConnected = false;
            }
        }
    };*/

    public static void AlertForNetwork(Context con) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle("Network error!");
        builder.setMessage(
                "Network seems Unavailable! Please try after sometime.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static boolean isConnectingToInternet(Context con) {
        ConnectivityManager connectivity = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static Typeface app_font(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),"Font/Raleway-Regular.ttf");
        return tf;
    }

}
