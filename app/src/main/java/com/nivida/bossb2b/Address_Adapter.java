package com.nivida.bossb2b;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.nivida.bossb2b.Bean.BeanVendorName;
import com.nivida.bossb2b.Bean.Bean_Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keval on 07-Dec-16.
 */

public class Address_Adapter extends BaseAdapter {

    TextView name1, name2;
    TextView add1, add2, add3, address1, address2, address3;


    Context context;
    List<Bean_Address> addresses = new ArrayList<>();

    public Address_Adapter(Context context, List<Bean_Address> addresses) {
        this.context = context;
        this.addresses = addresses;
    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        final Bean_Address beanAddress = addresses.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_checkout_page, parent, false);

        name1 = (TextView) view.findViewById(R.id.tv_user_name_billing);
        name2 = (TextView) view.findViewById(R.id.tv_user_name_delivery);

        add1 = (TextView) view.findViewById(R.id.tv_number_billing);
        add2 = (TextView) view.findViewById(R.id.tv_address_billing);


        address1 = (TextView) view.findViewById(R.id.tv_number_delivery);
        address2 = (TextView) view.findViewById(R.id.tv_address_delivery);


        name1.setText(beanAddress.getFirst_name());
        name2.setText(beanAddress.getFirst_name());

        add1.setText(beanAddress.getPincode());
        add2.setText(beanAddress.getAddress1());



        address1.setText(beanAddress.getPincode());
        address2.setText(beanAddress.getAddress1());



        return view;
    }
}
