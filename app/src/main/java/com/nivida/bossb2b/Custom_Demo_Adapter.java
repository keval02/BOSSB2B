package com.nivida.bossb2b;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nivida.bossb2b.Bean.Bean_Custom_list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhruvil Bhosle on 02-12-2016.
 */
public class Custom_Demo_Adapter extends BaseAdapter {
    List<Bean_Custom_list> bean_custom_lists = new ArrayList<>();
    Context contex;
    public Custom_Demo_Adapter(Context contex , List<Bean_Custom_list> bean_custom_lists) {
        this.bean_custom_lists = bean_custom_lists;
        this.contex = contex;
    }

    @Override
    public int getCount() {
        return bean_custom_lists.size();
    }

    @Override
    public Object getItem(int position) {
        return bean_custom_lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view;
        Bean_Custom_list custom_list = bean_custom_lists.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.custom_demo_listview,parent,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.img_demo);
        TextView textView = (TextView) view.findViewById(R.id.txt_demo);

        imageView.setImageResource(custom_list.getImage());
        textView.setText(custom_list.getTxt());




        return view;
    }
}
