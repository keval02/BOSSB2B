package com.nivida.bossb2b;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Dhruvil Bhosle on 28-11-2016.
 */
public class DrawerAdapter extends BaseAdapter {
    Context context;
    int[] images = {R.drawable.drhome , R.drawable.drprofile ,R.drawable.drmessage,R.drawable.drhistory,R.drawable.drlogout};
    String[] titles = {"Home", "My Account","Contact Us", "History" ,"Logout"};

    public DrawerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = null;


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_drawerlist, parent, false);

        ImageView drawer_image = (ImageView) view.findViewById(R.id.drawer_image);
        TextView drawer_item = (TextView) view.findViewById(R.id.drawer_item);


        drawer_image.setImageResource(images[position]);
        drawer_item.setText(titles[position]);

        return view;
    }


}
