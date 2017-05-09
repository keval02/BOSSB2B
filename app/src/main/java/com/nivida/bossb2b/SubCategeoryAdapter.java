package com.nivida.bossb2b;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.nivida.bossb2b.Bean.Bean_Product_Categeory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhruvil Bhosle on 25-11-2016.
 */
public class SubCategeoryAdapter extends BaseAdapter {

    List<Bean_Product_Categeory> product_categeories = new ArrayList<>();

    Context context;

    public SubCategeoryAdapter(List<Bean_Product_Categeory> product_categeories, Context context) {
        this.product_categeories = product_categeories;
        this.context = context;
    }

    @Override
    public int getCount() {
        return product_categeories.size();
    }

    @Override
    public Object getItem(int position) {
        return product_categeories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        Bean_Product_Categeory product_categeory = product_categeories.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_item_details_list, parent, false);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.categeory_checkbox);

        checkBox.setText(product_categeory.getName());
        checkBox.setText(product_categeory.getCode());


        return view;
    }
}
