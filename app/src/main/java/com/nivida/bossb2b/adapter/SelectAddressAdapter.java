package com.nivida.bossb2b.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.nivida.bossb2b.Bean.Bean_Address;
import com.nivida.bossb2b.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajay on 1/2/2017.
 */

public class SelectAddressAdapter extends BaseAdapter {

    List<Bean_Address> addressList=new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public SelectAddressAdapter(List<Bean_Address> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.custom_select_address,parent,false);

        RadioButton radioButton=(RadioButton) view.findViewById(R.id.radio_address);

        radioButton.setText(addressList.get(position).getAddress1()+","+addressList.get(position).getAddress2()+","+addressList.get(position).getAddress3()+"\n"+addressList.get(position).getCity()+","+addressList.get(position).getState()+"-"+addressList.get(position).getPincode());

        Log.e("isChecked","--"+addressList.get(position).isChecked());

        radioButton.setChecked(addressList.get(position).isChecked());

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setChecked(position);
            }
        });

        return view;
    }

    public void setChecked(int position){
        for(int i=0;i<addressList.size(); i++){
                addressList.get(i).setChecked(i==position);
        }
        notifyDataSetChanged();
    }

    public Bean_Address getSelectedAddress(){
        Bean_Address address=new Bean_Address();

        for(int i=0; i<addressList.size(); i++){
            if(addressList.get(i).isChecked()){
                address=addressList.get(i);
            }
        }

        return address;
    }
}
