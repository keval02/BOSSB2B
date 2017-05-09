package com.nivida.bossb2b;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ajay on 12/28/2016.
 */

public class OrderTrackAdapter extends BaseAdapter {

    Context context;
    List<Bean_OrderTrack> orderTrackList;

    public OrderTrackAdapter(Context context, List<Bean_OrderTrack> orderTrackList){
        this.context=context;
        this.orderTrackList=orderTrackList;
    }

    @Override
    public int getCount() {
        return orderTrackList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderTrackList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=null;

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_ordertrack_list,parent,false);


        TextView txt_date=(TextView) view.findViewById(R.id.txt_date);
        TextView txt_comment=(TextView) view.findViewById(R.id.txt_comment);
        TextView txt_status=(TextView) view.findViewById(R.id.txt_status);

        txt_date.setText(orderTrackList.get(position).getDate());
        txt_comment.setText(orderTrackList.get(position).getComment());
        txt_status.setText(orderTrackList.get(position).getStatus());

        return view;
    }

}
