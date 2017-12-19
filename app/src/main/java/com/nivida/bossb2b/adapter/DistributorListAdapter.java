package com.nivida.bossb2b.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.AppPref;
import com.nivida.bossb2b.Bean.BeanReatailerHierarchy;
import com.nivida.bossb2b.Bean.BeanShowHierarchy;
import com.nivida.bossb2b.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by Keval on 02-Feb-17.
 */

public class DistributorListAdapter extends BaseAdapter {
    List<BeanShowHierarchy> hierarchyList = new ArrayList<>();


    String salesPerson;


    Context context;
    Activity activity;
    AppPref pref;
    private static LayoutInflater inflater = null;


    public DistributorListAdapter(List<BeanShowHierarchy> hierarchyList, Context context, Activity activity) {
        this.hierarchyList = hierarchyList;
        this.context = context;
        this.activity = activity;
        pref = new AppPref(context);

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return hierarchyList.size();
    }

    @Override
    public Object getItem(int position) {
        return hierarchyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;
        TextView txt_distributor_name;

        ImageView img_userdistributor, img_distriinfo;

        String disName;


        final BeanShowHierarchy showHierarchy = hierarchyList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_hirearchy_list_view, parent, false);

        final LinearLayout layout_underPersons = (LinearLayout) view.findViewById(R.id.layout_underPersons);

        //final ListView listPersons=(ListView) inflater.inflate(R.layout.layout_listviewinside,null);
        //listPersons.setVisibility(View.GONE);

        txt_distributor_name = (TextView) view.findViewById(R.id.txt_dis_names);
        img_distriinfo = (ImageView) view.findViewById(R.id.img_disinfo);


        disName = showHierarchy.getFirm_shop_name();
        salesPerson = showHierarchy.getFirm_shop_name();


        img_distriinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disMoreInfo(position);
            }
        });


        final List<BeanReatailerHierarchy> reatailerHierarchies = hierarchyList.get(position).getReatailerHierarchies();

        for (int i = 0; i < reatailerHierarchies.size(); i++) {
            final LinearLayout layout_underView = (LinearLayout) inflater.inflate(R.layout.layout_retailer_text, null);
            final TextView txt_name = (TextView) layout_underView.findViewById(R.id.txt_name);

            ImageView img_info = (ImageView) layout_underView.findViewById(R.id.img_info);


            txt_name.setText(reatailerHierarchies.get(i).getFirm_shop_name());

            final String getName = reatailerHierarchies.get(i).getFirm_shop_name();
            final String getMail = reatailerHierarchies.get(i).getEmail_id();
            final String getPhone = reatailerHierarchies.get(i).getPhone_no();
            final String getAddress = reatailerHierarchies.get(i).getAddress_1() + ", " + reatailerHierarchies.get(i).getAddress_2() + ", " + reatailerHierarchies.get(i).getAddress_3() + " " + reatailerHierarchies.get(i).getPincode();
            final String getcity = reatailerHierarchies.get(i).getCity_name();
            final String getFirst = reatailerHierarchies.get(i).getFirst_name() + " " + reatailerHierarchies.get(i).getLast_name();
            final String getMobileNo = reatailerHierarchies.get(i).getMobile_no();


            Log.e("datas", "-->" + getName + "-->" + getMail + "-->" + getPhone + "-->" + getAddress + "-->" + getcity + "-->" + getFirst);

            img_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String firmName = txt_name.getText().toString().trim();
                    String emailId = getMail.toString().trim();
                    String mobileNo = getPhone.toString().trim();
                    String address = getAddress.toString().trim();
                    String cityName = getcity.toString().trim();
                    String mobileNum = getMobileNo.toString().trim();
                    String firstLast = getFirst.toString().trim();


                    moreInfo(position, firmName, emailId, mobileNo, address, cityName, mobileNum, firstLast);


                }
            });

            layout_underPersons.addView(layout_underView);
        }

        if (showHierarchy.isOpen()) {
            layout_underPersons.setVisibility(VISIBLE);
        }

        /*ArrayList<String> retailerNames=new ArrayList<>();
        for(int i=0; i<reatailerHierarchies.size(); i++){
            retailerNames.add(reatailerHierarchies.get(i).getFirm_shop_name());
        }
        ArrayAdapter<String> retailerAdapter=new ArrayAdapter<String>(context,R.layout.layout_textview,retailerNames);*/
        //listPersons.setAdapter(retailerAdapter);

        //((LinearLayout) view).addView(listPersons);

        txt_distributor_name.setText(disName + "  (" + reatailerHierarchies.size() + ")");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reatailerHierarchies.size() > 0) {
                    if (layout_underPersons.getVisibility() == VISIBLE) {
                        layout_underPersons.setVisibility(View.GONE);
                        hierarchyList.get(position).setOpen(false);
                    } else {
                        layout_underPersons.setVisibility(VISIBLE);
                        hierarchyList.get(position).setOpen(true);

                    }
                }
            }
        });


        return view;
    }


    private void disMoreInfo(int position) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_distributor_info, null);
        dialogBuilder.setView(dialogView);


        final TextView salesPerson = (TextView) dialogView.findViewById(R.id.d_sales_personname);
        final TextView companyName = (TextView) dialogView.findViewById(R.id.d_dcompanyname1);
        final TextView emailId = (TextView) dialogView.findViewById(R.id.d_demailid);
        final TextView mobileNo = (TextView) dialogView.findViewById(R.id.d_dmobilenumber);
        final TextView addresses = (TextView) dialogView.findViewById(R.id.d_address);
        final TextView cityName = (TextView) dialogView.findViewById(R.id.d_cityName);
        final TextView telephoneNo = (TextView) dialogView.findViewById(R.id.d_telephone);

        final ImageView cancel = (ImageView) dialogView.findViewById(R.id.btn_cancel);


        salesPerson.setText(hierarchyList.get(position).getFirm_shop_name());

        companyName.setText(hierarchyList.get(position).getFirst_name() + " " + hierarchyList.get(position).getLast_name());

        final String mailId = hierarchyList.get(position).getEmail_id();

        if (mailId.isEmpty() || mailId.equalsIgnoreCase("") || mailId.equalsIgnoreCase(null) || mailId.equalsIgnoreCase("null")) {

            emailId.setText("N/A");
        } else {


            emailId.setText(mailId);
        }

        final String mobile = hierarchyList.get(position).getMobile_no();

        if (mobile.isEmpty() || mobile.equalsIgnoreCase(null) || mobile.equalsIgnoreCase("null") || mobile.equalsIgnoreCase("")) {

            telephoneNo.setText("N/A");
        } else {


            telephoneNo.setText(mobile);

        }

        String address = hierarchyList.get(position).getAddress_1() + " " + hierarchyList.get(position).getAddress_2() + " " + hierarchyList.get(position).getAddress_3() + " " + hierarchyList.get(position).getPincode();

        addresses.setText(address);


        cityName.setText(hierarchyList.get(position).getName());


        String landlinrNo = hierarchyList.get(position).getPhone_no();

        if (landlinrNo.isEmpty() || landlinrNo.equalsIgnoreCase("") || landlinrNo.equalsIgnoreCase(null) || landlinrNo.equalsIgnoreCase("null")) {


            mobileNo.setText("N/A");
        } else {


            mobileNo.setText(landlinrNo);
        }


        mobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String no = mobileNo.getText().toString();
                if (!no.equalsIgnoreCase("N/A")) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + no));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(callIntent);
                }
            }
        });

        telephoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = telephoneNo.getText().toString();
                if (!no.equalsIgnoreCase("N/A")) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + no));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(callIntent);
                }
            }
        });


        emailId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:" + mailId));
                activity.startActivity(Intent.createChooser(mailIntent, "Send Email"));
            }
        });


        final AlertDialog alert = dialogBuilder.create();
        alert.setCancelable(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        alert.show();


    }

    private void moreInfo(int position, String firm, final String emailID, String mobile_No, String Address, String city_Name, String moibleNum, String firstLast) {


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_retailerinfo, null);
        dialogBuilder.setView(dialogView);


        final TextView salesPerson = (TextView) dialogView.findViewById(R.id.d_sales_personname);
        final TextView companyName = (TextView) dialogView.findViewById(R.id.d_dcompanyname1);
        final TextView emailId = (TextView) dialogView.findViewById(R.id.d_demailid);
        final TextView mobileNo = (TextView) dialogView.findViewById(R.id.d_dmobilenumber);
        final TextView addresses = (TextView) dialogView.findViewById(R.id.d_address);
        final TextView cityName = (TextView) dialogView.findViewById(R.id.d_cityName);
        final TextView telephoneNo = (TextView) dialogView.findViewById(R.id.d_telephone);
        final TextView firstLastName = (TextView) dialogView.findViewById(R.id.d_firstlast);

        final ImageView cancel = (ImageView) dialogView.findViewById(R.id.btn_cancel);


        salesPerson.setText(hierarchyList.get(position).getFirm_shop_name());
        companyName.setText(firm);


        addresses.setText(Address);
        cityName.setText(city_Name);

        if (emailID.equalsIgnoreCase("") || emailID.isEmpty() || emailID.equalsIgnoreCase(null) || emailID.equalsIgnoreCase("null")) {


            emailId.setText("N/A");
        } else {

            emailId.setText(emailID);
        }


        if (mobile_No.equalsIgnoreCase("") || mobile_No.equalsIgnoreCase(null) || mobile_No.equalsIgnoreCase("null") || mobile_No.isEmpty()) {


            mobileNo.setText("N/A");
        } else {

            mobileNo.setText(mobile_No);
        }


        if (moibleNum.equalsIgnoreCase("") || moibleNum.equalsIgnoreCase(null) || moibleNum.equalsIgnoreCase("null") || moibleNum.isEmpty()) {


            telephoneNo.setText("N/A");
        } else {


            telephoneNo.setText(moibleNum);
        }

        firstLastName.setText(firstLast);


        mobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = mobileNo.getText().toString();
                if (!no.equalsIgnoreCase("N/A")) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + no));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(callIntent);
                }
            }
        });

        telephoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = telephoneNo.getText().toString();

                if (!no.equalsIgnoreCase("N/A")) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + no));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(callIntent);
                }

            }
        });

        emailId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:" + emailID));
                activity.startActivity(mailIntent.createChooser(mailIntent, "Send Mail"));
            }
        });

        final AlertDialog alert = dialogBuilder.create();
        alert.setCancelable(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        alert.show();


    }


}
