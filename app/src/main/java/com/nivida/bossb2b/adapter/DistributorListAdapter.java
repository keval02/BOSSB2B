package com.nivida.bossb2b.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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


    String salesPerson, retailerName, emailId, mobileNo, address, cityName;


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

        ImageView img_userdistributor;

        String disName;


        final BeanShowHierarchy showHierarchy = hierarchyList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_hirearchy_list_view, parent, false);

        final LinearLayout layout_underPersons = (LinearLayout) view.findViewById(R.id.layout_underPersons);

        //final ListView listPersons=(ListView) inflater.inflate(R.layout.layout_listviewinside,null);
        //listPersons.setVisibility(View.GONE);

        txt_distributor_name = (TextView) view.findViewById(R.id.txt_dis_names);


        disName = showHierarchy.getFirm_shop_name();
        salesPerson = showHierarchy.getFirm_shop_name();
        Log.e("firmShopname", hierarchyList.get(position).getFirm_shop_name());

        final List<BeanReatailerHierarchy> reatailerHierarchies = hierarchyList.get(position).getReatailerHierarchies();

        for (int i = 0; i < reatailerHierarchies.size(); i++) {
            final LinearLayout layout_underView = (LinearLayout) inflater.inflate(R.layout.layout_retailer_text, null);
            final TextView txt_name = (TextView) layout_underView.findViewById(R.id.txt_name);
            ImageView img_info = (ImageView) layout_underView.findViewById(R.id.img_info);
            txt_name.setText(reatailerHierarchies.get(i).getFirm_shop_name());

            retailerName = reatailerHierarchies.get(i).getFirst_name() + "  " + reatailerHierarchies.get(i).getLast_name();
            emailId = reatailerHierarchies.get(i).getEmail_id();
            mobileNo = reatailerHierarchies.get(i).getPhone_no();
            address = reatailerHierarchies.get(i).getAddress_1() + " " + reatailerHierarchies.get(i).getAddress_2() + " " + reatailerHierarchies.get(i).getAddress_3();
            cityName = reatailerHierarchies.get(i).getCity_name();


            img_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String firmName = txt_name.getText().toString().trim();


                    Log.e("name", address);
                    Log.e("firmName", firmName);

                    moreInfo(firmName, address);


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

    private void moreInfo(String firmShop, String name) {


        Log.e("firmshop", firmShop);
        Log.e("name", name);

    }


}
