package com.nivida.bossb2b;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nivida.bossb2b.Bean.BeanVendor;
import com.nivida.bossb2b.Bean.BeanVendorName;
import com.nivida.bossb2b.Model.ListData;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Ajay on 11/24/2016.
 */

public class ListAdapter extends BaseAdapter {

    List<BeanVendor> names=new ArrayList<>();

    ListData listData;

    AppPref pref;

    Context context;


    public ListAdapter(Context context,List<BeanVendor> names) {
        this.names = names;
        this.context=context;
    }




    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view=null;
        BeanVendor  bv=names.get(position);

        listData = new ListData(context);

        pref = new AppPref(context);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.getvendorlist, parent, false);


        TextView  text_companyname=(TextView) view.findViewById(R.id.text_company);
        TextView  text_address=(TextView) view.findViewById(R.id.txt_address);



        text_companyname.setText(bv.getCompany_name());
        text_address.setText(bv.getAddress());


        return view;
    }

    public void updateData(List<BeanVendor> retailerPersonList) {
        this.names=retailerPersonList;
        notifyDataSetChanged();
    }

    public List<BeanVendor> getCurrentRetailers(){
        return this.names;
    }
}
