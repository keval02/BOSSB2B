package com.nivida.bossb2b.adapter;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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
            final TextView txt_email = (TextView) layout_underView.findViewById(R.id.txt_email);
            final TextView txt_phone = (TextView) layout_underView.findViewById(R.id.txt_mobile);
            final TextView txt_mobile = (TextView) layout_underView.findViewById(R.id.txt_mobileNo);
            final TextView txt_address = (TextView) layout_underView.findViewById(R.id.txt_addressss);
            final TextView txt_city = (TextView) layout_underView.findViewById(R.id.txt_city);
            final TextView txt_first = (TextView) layout_underView.findViewById(R.id.txt_firstlast);
            ImageView img_info = (ImageView) layout_underView.findViewById(R.id.img_info);


            txt_name.setText(reatailerHierarchies.get(i).getFirm_shop_name());
            txt_email.setText(reatailerHierarchies.get(i).getEmail_id());
            txt_phone.setText(reatailerHierarchies.get(i).getPhone_no());
            txt_address.setText(reatailerHierarchies.get(i).getAddress_1() + ", " + reatailerHierarchies.get(i).getAddress_2() + ", " + reatailerHierarchies.get(i).getAddress_3() + " " + reatailerHierarchies.get(i).getPincode());
            txt_city.setText(reatailerHierarchies.get(i).getCity_name());
            txt_mobile.setText(reatailerHierarchies.get(i).getMobile_no());
            txt_first.setText(reatailerHierarchies.get(i).getFirst_name() + " " + reatailerHierarchies.get(i).getLast_name());


            img_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String firmName = txt_name.getText().toString().trim();
                    String emailId = txt_email.getText().toString().trim();
                    String mobileNo = txt_phone.getText().toString().trim();
                    String address = txt_address.getText().toString().trim();
                    String cityName = txt_city.getText().toString().trim();
                    String mobileNum = txt_mobile.getText().toString().trim();
                    String firstLast = txt_first.getText().toString().trim();


                    moreInfo(position, firmName, emailId, mobileNo, address, cityName, mobileNum , firstLast);


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

            mobileNo.setText("N/A");
        } else {


            mobileNo.setText(mobile);

        }

        String address = hierarchyList.get(position).getAddress_1() + " " + hierarchyList.get(position).getAddress_2() + " " + hierarchyList.get(position).getAddress_3() + " " + hierarchyList.get(position).getPincode();

        addresses.setText(address);


        cityName.setText(hierarchyList.get(position).getName());


        String landlinrNo = hierarchyList.get(position).getPhone_no();

        if (landlinrNo.isEmpty() || landlinrNo.equalsIgnoreCase("") || landlinrNo.equalsIgnoreCase(null) || landlinrNo.equalsIgnoreCase("null")) {

            telephoneNo.setText("N/A");
        } else {

            telephoneNo.setText(landlinrNo);
        }


        mobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String no = mobileNo.getText().toString();
                if(!no.equalsIgnoreCase("N/A")){

                    ClipboardManager _clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    _clipboard.setText(no);
                    Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        telephoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = telephoneNo.getText().toString();
                if(!no.equalsIgnoreCase("N/A")){

                    ClipboardManager _clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    _clipboard.setText(no);
                    Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });


        emailId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:" + mailId));
                activity.startActivity(Intent.createChooser(mailIntent , "Send Email"));
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

    private void moreInfo(int position, String firm, final String emailID, String mobile_No, String Address, String city_Name, String moibleNum  , String firstLast) {


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
                if(!no.equalsIgnoreCase("N/A")){

                    ClipboardManager _clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    _clipboard.setText(no);
                    Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        telephoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = telephoneNo.getText().toString();

                if(!no.equalsIgnoreCase("N/A")){

                    ClipboardManager _clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    _clipboard.setText(no);
                    Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                }

            }
        });

        emailId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:" + emailID));
                activity.startActivity(mailIntent.createChooser(mailIntent , "Send Mail"));
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
