package com.nivida.bossb2b.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nivida.bossb2b.AppPref;
import com.nivida.bossb2b.Bean.BeanReatailerHierarchy;
import com.nivida.bossb2b.Bean.BeanShowHierarchy;
import com.nivida.bossb2b.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by NWSPL-17 on 2/4/2017.
 */
public class RatailerListAdapter extends BaseAdapter {

    List<BeanReatailerHierarchy> hierarchyList = new ArrayList<>();


    Context context;
    Activity activity;
    AppPref pref;
    private static LayoutInflater inflater = null;
    public RatailerListAdapter(List<BeanReatailerHierarchy> hierarchyList, Context context, Activity activity) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        TextView txt_retailer_name;

        ListView listView;





        String disName;

        final BeanReatailerHierarchy showHierarchy = hierarchyList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_retailer_listview , parent , false);

        txt_retailer_name = (TextView) view.findViewById(R.id.txt_retailer_name);
        txt_retailer_name = (TextView) view.findViewById(R.id.txt_retailer_name);



        disName = showHierarchy.getFirm_shop_name();
        Log.e("firmShopname" , hierarchyList.get(position).getFirm_shop_name());

        txt_retailer_name.setText(disName);





        return view;
    }


}
