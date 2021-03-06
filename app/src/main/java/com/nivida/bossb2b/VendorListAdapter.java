package com.nivida.bossb2b;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.BeanVendor;
import com.nivida.bossb2b.Bean.BeanVendorName;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Ajay on 11/25/2016.
 */

public class    VendorListAdapter extends BaseAdapter {

    List<BeanVendorName> vnames = new ArrayList<>();

    TextView txt_v_names, txt_v_address;


    String f_name, l_name, emailid, mobnumber, s_time, e_time, s_lat, s_long, e_lat, e_long, e_comments, e_attachment1, meeting_type;

    Context context;
    Activity activity;
    AppPref prefs;
    Date date1;

    int totalSize=0;
    int selectedPosition = 0;
    private static LayoutInflater inflater = null;

    LinearLayout layout_cemara;

    public VendorListAdapter(Context context, List<BeanVendorName> vnames, Activity activity ) {

        this.vnames = vnames;

        this.context = context;
        this.activity = activity;

        prefs = new AppPref(context);

        totalSize=vnames.size();

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public int getCount() {
        return vnames.size();
    }

    @Override
    public Object getItem(int position) {
        return vnames.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ImageView img_details, img_order_history;

        final BeanVendorName bvn = vnames.get(position);


        View view;
        int pos =totalSize-position;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            view = inflater.inflate(R.layout.custom_listview_2, parent, false);



        txt_v_names = (TextView) view.findViewById(R.id.txt_v_names);
        txt_v_address = (TextView) view.findViewById(R.id.txt_v_address);
        img_details = (ImageView) view.findViewById(R.id.img_details);
        img_order_history = (ImageView) view.findViewById(R.id.img_order_history);

        f_name = vnames.get(position).getFirst_name().toString();
        Log.e("first_name", "" + vnames.get(position).getFirst_name().toString());
        l_name = vnames.get(position).getLast_name().toString();
        emailid = bvn.getEmail_id();
        mobnumber = prefs.getPhone_no();

        String startTime = bvn.getStart_time();

        String[] times = startTime.split(" ");
        String date = times[0];
        s_time = times[1];

        String endTime = bvn.getEnd_time();
        String[] etimes = startTime.split(" ");
        String edate = etimes[0];
        e_time = times[1];

       /* s_lat = bvn.getStart_latitude();
        s_long = bvn.getStart_longitude();

        e_lat = bvn.getEnd_latitude();


        e_long = bvn.getEnd_longitude();*/


        s_lat = vnames.get(position).getStart_latitude();
        s_long = vnames.get(position).getStart_longitude();

        e_lat = vnames.get(position).getEnd_latitude();
        e_long = vnames.get(position).getEnd_longitude();



        e_comments = bvn.getComments();

        e_attachment1 = vnames.get(position).getAttachments1();
        Log.e("111111111111", "" + vnames.get(position).getAttachments1());
        Log.e("222222222222", "" + e_attachment1);


        if (!bvn.isHasOrders()) {
            img_order_history.setVisibility(View.GONE);
        }

        img_order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CompSalesDistretOrderHist.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("meeting_id", bvn.getMeetingID());
                context.startActivity(intent);
            }
        });
        txt_v_names.setText(pos + ". " + bvn.getCompany_name());

        String converttime = vnames.get(position).getStart_time();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd k:mm:ss");
        try {
            date1 = simpleDateFormat.parse(converttime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txt_v_address.setText(new SimpleDateFormat("dd-MM-yyyy k:mm:ss").format(date1));


        img_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showalertdialog(position);
            }
        });

        return view;
    }

    private void showalertdialog(final int position) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_popup_detail_list, null);
        dialogBuilder.setView(dialogView);
        //Bean_History companyProfile=result.get(position);
        final TextView d_sales_personname = (TextView) dialogView.findViewById(R.id.d_sales_personname);
        final TextView d_sales_personnameLabel = (TextView) dialogView.findViewById(R.id.textView5);

        d_sales_personnameLabel.setText("Name :");
        //final TextView d_dpersonname=(TextView)dialogView.findViewById(R.id.d_dpersonname);
        final TextView d_dcompanyname = (TextView) dialogView.findViewById(R.id.d_dcompanyname);
        final TextView d_dcompanyname1 = (TextView) dialogView.findViewById(R.id.d_dcompanyname1);
        final TextView meeting_type = (TextView) dialogView.findViewById(R.id.meeting_type);
        //final TextView d_ddesignation=(TextView)dialogView.findViewById(R.id.d_ddesignation);
        final TextView d_demailid = (TextView) dialogView.findViewById(R.id.d_demailid);
        final TextView d_dmobilenumber = (TextView) dialogView.findViewById(R.id.d_dmobilenumber);
        //final TextView d_dalternetno=(TextView)dialogView.findViewById(R.id.d_dalternetno);
        final TextView d_dstarttime = (TextView) dialogView.findViewById(R.id.d_dstarttime);
        final TextView d_dendtime = (TextView) dialogView.findViewById(R.id.d_dendtime);
        final TextView d_dstartlatitude = (TextView) dialogView.findViewById(R.id.d_dstartlatitude);
        final TextView d_dstartlongitude = (TextView) dialogView.findViewById(R.id.d_dstartlongitude);
         layout_cemara = (LinearLayout) dialogView.findViewById(R.id.layout_camera);
      final LinearLayout  layout_direct_cemara = (LinearLayout) dialogView.findViewById(R.id.layout_direct_cemara);

        final TextView d_dcomments = (TextView) dialogView.findViewById(R.id.d_dcomments);
        // final TextView d_dcity=(TextView)dialogView.findViewById(R.id.d_dcity);
        final ImageView d_img1 = (ImageView) dialogView.findViewById(R.id.img1);
        final ImageView d_img2 = (ImageView) dialogView.findViewById(R.id.img2);
        final ImageView d_img3 = (ImageView) dialogView.findViewById(R.id.img3);
        final ImageView cancel = (ImageView) dialogView.findViewById(R.id.btn_cancel);
        d_img1.setTag(vnames.get(position).getAttachments1());
        final String img_1 = vnames.get(position).getAttachments1();
        final String img_2 = vnames.get(position).getAttachments2();
        final String img_3 = vnames.get(position).getAttachments3();

       // if(!img_2.equalsIgnoreCase("null")||!img_2.isEmpty()||!img_2.equalsIgnoreCase("")||!img_2.equalsIgnoreCase(null)){

          //  d_img2.setVisibility(View.VISIBLE);
            d_img2.setTag(vnames.get(position).getAttachments2());
            Log.e("e_att2222", "" + vnames.get(position).getAttachments2());

            Picasso.with(context)
                    .load(Web.LINK + d_img2.getTag().toString())
                    .resize(150 , 150)
                    .placeholder(R.drawable.bossimageloading)
                    .error(R.drawable.noimagefound)
                    .into(d_img2);
        //}
       // if(!img_3.equalsIgnoreCase("null")||!img_3.isEmpty()||!img_3.equalsIgnoreCase("")||!img_3.equalsIgnoreCase(null)) {

          //  d_img3.setVisibility(View.VISIBLE);
            d_img3.setTag(vnames.get(position).getAttachments3());
            Log.e("e_33333", "" + vnames.get(position).getAttachments3());

            Picasso.with(context)
                    .load(Web.LINK + d_img3.getTag().toString())
                    .resize(150 , 150)
                    .placeholder(R.drawable.bossimageloading)
                    .error(R.drawable.noimagefound)
                    .into(d_img3);

        //}
        Log.e("e_att1111", "" + vnames.get(position).getAttachments1());
        Log.e("e_att1111", "" + d_img1.getTag().toString());


        Picasso.with(context)
                .load(Web.LINK + d_img1.getTag().toString())
                .placeholder(R.drawable.bossimageloading)
                .error(R.drawable.noimagefound)
                .resize(150 , 150)
                .into(d_img1);

        Log.e("getAttachments" , "-->" + vnames.get(position).getAttachmentPaths());

        for(int i=0 ; i<vnames.get(position).getAttachmentPaths().size();i++){


            View view = inflater.inflate(R.layout.layout_history_subimage , null);

            ImageView img_attachment = (ImageView) view.findViewById(R.id.img_attachment);
            ImageView img_remove = (ImageView) view.findViewById(R.id.img_remove);
            TextView txt_imagePath = (TextView) view.findViewById(R.id.txt_imagePath);


            img_remove.setVisibility(View.GONE);

            img_attachment.setMaxWidth(50);
            img_attachment.setMaxHeight(50);


            Picasso.with(context)
                    .load(vnames.get(position).getAttachmentPaths().get(i))
                    .error(R.drawable.noimagefound)
                    .into(img_attachment);

            txt_imagePath.setText(vnames.get(position).getAttachmentPaths().get(i));

            img_attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> imagePaths = new ArrayList<>();

                    for (int i = 0; i < layout_direct_cemara.getChildCount(); i++) {
                        View view = layout_direct_cemara.getChildAt(i);
                        String path = ((TextView) view.findViewById(R.id.txt_imagePath)).getText().toString();

                        if (path.equalsIgnoreCase(vnames.get(position).getAttachmentPaths().get(i)))
                            //selectedPosition = i;


                        imagePaths.add(path);
                    }

                    Intent intent = new Intent(context, FullZoomViewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putStringArrayListExtra("imagePaths", imagePaths);
                    //intent.putExtra("ImagePosition", selectedPosition);
                    context.startActivity(intent);
                }
            });


            layout_direct_cemara.addView(view);






        }



        d_sales_personname.setText(vnames.get(position).getFirst_name() + " " + vnames.get(position).getLast_name());
        d_dcompanyname1.setText(vnames.get(position).getCompany_name());

        if (vnames.get(position).getLast_name().toString().equalsIgnoreCase("null")) {
            d_dcompanyname.setText("N/A");
        } else {
            d_dcompanyname.setText(vnames.get(position).getLast_name());
        }

        if (vnames.get(position).getEmail_id().equalsIgnoreCase("null")|| vnames.get(position).getEmail_id().isEmpty()) {
            d_demailid.setText("N/A");
            d_demailid.setEnabled(false);
            d_demailid.setTextColor(Color.parseColor("#9e9e9e"));
        } else {
            d_demailid.setText(vnames.get(position).getEmail_id());
        }
        if (vnames.get(position).getPhone_no().equalsIgnoreCase("null")||vnames.get(position).getPhone_no().isEmpty()) {
            d_dmobilenumber.setText("N/A");
            d_dmobilenumber.setTextColor(Color.parseColor("#9e9e9e"));
        } else {
            d_dmobilenumber.setText(vnames.get(position).getPhone_no());
        }



        d_dmobilenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String no = d_dmobilenumber.getText().toString();
                if(!no.equalsIgnoreCase("N/A")){

                    ClipboardManager _clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    _clipboard.setText(no);
                    Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                }
            }
        });



        String starttime1 = vnames.get(position).getStart_time();
        if (starttime1.equalsIgnoreCase("null")) {
            d_dstarttime.setText("N/A");
        } else {

            try {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd k:mm:ss");
                date1 = sdf.parse(starttime1);
            } catch (final ParseException e) {
                e.printStackTrace();
            }
            d_dstarttime.setText("" + new SimpleDateFormat("dd-MM-yyyy k:mm:ss").format(date1));
        }
//        d_dstarttime.setText(vnames.get(position).getStart_time());
       String endtime1 = vnames.get(position).getEnd_time();
        if (endtime1.equalsIgnoreCase("null")) {
            d_dendtime.setText("N/A");
        } else {
            try {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd k:mm:ss");
                date1 = sdf.parse(endtime1);
            } catch (final ParseException e) {
                e.printStackTrace();
            }
            d_dendtime.setText("" + new SimpleDateFormat("dd-MM-yyyy k:mm:ss").format(date1));
        }

      //  d_dendtime.setText(vnames.get(position).getEnd_time());

        if (vnames.get(position).getComments().toString().equalsIgnoreCase("null")||vnames.get(position).getComments().isEmpty()) {

            Log.e("check null", "null");
            d_dcomments.setText("N/A");
            d_dcomments.setTextColor(Color.parseColor("#9e9e9e"));
        } else {
            d_dcomments.setText(vnames.get(position).getComments());
            Log.e("check11111", "null");

        }


        d_demailid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + vnames.get(position).getEmail_id()));
                activity.startActivity(Intent.createChooser(emailIntent, "Send feedback"));
            }
        });
        d_dmobilenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + vnames.get(position).getPhone_no()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);
            }
        });

        d_dstartlongitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in3 = new Intent(activity, MapEndview.class);
            /*    in3.putExtra("lat", "" + e_lat);


                in3.putExtra("lonng", "" + e_long);
*/

                in3.putExtra("lat" ,""+ vnames.get(position).getEnd_latitude());
                in3.putExtra("lonng" , "" + vnames.get(position).getEnd_longitude());

                Log.e("Lat" , ""+ vnames.get(position).getEnd_latitude());
                Log.e("Long" , ""+ vnames.get(position).getEnd_longitude());


                activity.startActivity(in3);
                // activity.finish();
            }
        });
        d_dstartlatitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent in3 = new Intent(activity, Mapview.class);
               /* in3.putExtra("lat", "" + s_lat);
                in3.putExtra("lonng", "" + s_long);*/


                in3.putExtra("lat" ,""+ vnames.get(position).getStart_latitude());
                in3.putExtra("lonng" , "" + vnames.get(position).getStart_longitude());

                Log.e("Lat" , ""+ vnames.get(position).getStart_latitude());
                Log.e("Long" , ""+ vnames.get(position).getStart_longitude());


                activity.startActivity(in3);
                // activity.finish();
            }
        });

        String meetingtype = vnames.get(position).getMeeting_type();

        if(meetingtype.equalsIgnoreCase("3")){
            meeting_type.setText("Cold");
        }
        if(meetingtype.equalsIgnoreCase("2")){
            meeting_type.setText("Average");
        }
        if(meetingtype.equalsIgnoreCase("1")){
            meeting_type.setText("Hot");
        }




        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b.dismiss();
            }
        });
        b.show();
    }


}
