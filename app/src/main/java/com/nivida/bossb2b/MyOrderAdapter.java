package com.nivida.bossb2b;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nivida.bossb2b.Bean.Bean_Order_history;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajay on 12/28/2016.
 */

public class MyOrderAdapter extends BaseAdapter {

    Context context;
    List<Bean_Order_history> historyList=new ArrayList<>();
    final File myDir = new File("/sdcard/Boss/Invoice/");
    String filesToSend = "";
    Activity activity;
    AppPrefs prefs;
    String fromActivity;

    public MyOrderAdapter(Context context, List<Bean_Order_history> historyList, Activity activity,String fromActivity){
        this.context=context;
        this.historyList=historyList;
        this.activity=activity;
        prefs=new AppPrefs(context);
        this.fromActivity=fromActivity;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=null;

        final LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.distributor_myorder_listitem,parent,false);

        Bean_Order_history order_history=historyList.get(position);

        TextView order_customer_name = (TextView) view.findViewById(R.id.order_customer_name);
        TextView tv_order_status = (TextView) view.findViewById(R.id.tv_order_status);
        TextView ordertakenby = (TextView) view.findViewById(R.id.ordertakenby);
        TextView tv_order_date = (TextView) view.findViewById(R.id.tv_order_date);

        TextView tv_total = (TextView) view.findViewById(R.id.tv_total);
        ImageView img_pdf = (ImageView) view.findViewById(R.id.img_pdf);
        ImageView img_detail = (ImageView) view.findViewById(R.id.img_detail);


        order_customer_name.setText(historyList.get(position).getFirm_shop_name());


        tv_order_status.setText(historyList.get(position).getOrder_status_is());
     ordertakenby.setText(historyList.get(position).getOwnerFirstName()+" "+historyList.get(position).getOwnerLastName());
     // ordertakenby.setText(historyList.get(position).getFirm_shop_name());
        Log.e("Name" , historyList.get(position).getOwnerFirstName()+" "+historyList.get(position).getOwnerLastName());
        tv_order_date.setText("Date: "+historyList.get(position).getOrder_date());
        //tv_total.setText("Total: "+array_remove_duplicate.get(position).getProduct_total());

        img_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFileFromURL(position).execute();
            }
        });

        img_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.setOrder_history_id(historyList.get(position).getOrder_id());
                prefs.setFromActivity(fromActivity);
                prefs.setAttachment(historyList.get(position).getAttachment());
                prefs.setComment(historyList.get(position).getComment());
                prefs.setScheme(historyList.get(position).getScheme_info());
                Intent intent=new Intent(context,OrderHistoryDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("fromOther",1);
                intent.putExtra("firm_shop_name" , historyList.get(position).getFirm_shop_name());
                context.startActivity(intent);
            }
        });

        return view;
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        ProgressDialog pDialog;
        int position;
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        public DownloadFileFromURL(int position){
            this.position=position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //activity.showDialog(0);
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Please Wait Downloading Invoice PDF");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... i) {

            try {


                //pDialog.setMessage("Please Wait Downloading Image");

                Log.e("file name",myDir+"/"+historyList.get(position).getOrder_date().replace(":","-")+"_"+historyList.get(position).getOrder_id()+".pdf");

                File temp_dir = new File (myDir+"/"+historyList.get(position).getOrder_date().replace(":","-")+"_"+historyList.get(position).getOrder_id()+".pdf");
                if(temp_dir.exists())
                {
                    filesToSend = myDir+"/"+historyList.get(position).getOrder_date().replace(":","-")+"_"+historyList.get(position).getOrder_id()+".pdf";


                }
                else
                {
                    File dir = new File (myDir+"");
                    if(!dir.exists())
                    {
                        dir.mkdirs();
                    }

                    URL url = new URL(GlobalVariable.link+historyList.get(position).getOrder_pdf());

                    URLConnection connection = url.openConnection();
                    connection.connect();

                    // this will be useful so that you can show a typical 0-100% progress bar
                    long fileLength = connection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(new File(myDir+"/"+historyList.get(position).getOrder_date().replace(":","-")+"_"+historyList.get(position).getOrder_id()+".pdf"));

                    filesToSend = myDir+"/"+historyList.get(position).getOrder_date().replace(":","-")+"_"+historyList.get(position).getOrder_id()+".pdf";
                    Log.e("", "out put :- "+myDir+"/"+historyList.get(position).getOrder_date().replace(":","-")+"_"+historyList.get(position).getOrder_id()+".pdf");
                    byte data[] = new byte[1024];
                    long total = 0;
                    //int count;


                    int count;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        //  publishProgress((int) ((total * 100)/fileLength));
                        long total1 = (total * 100)/fileLength;
                        publishProgress(total1+"");
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                }

            }
            catch (Exception e) {
                Log.e("Error: ", ""+e);
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //activity.dismissDialog(0);
            pDialog.dismiss();

            File file = new File(myDir+"/"+historyList.get(position).getOrder_date().replace(":","-")+"_"+historyList.get(position).getOrder_id()+".pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    public int getTotalQuantity(){
        int totalQty=0;

        for(int i=0; i<historyList.size(); i++){
            totalQty += historyList.get(i).getQuantity();
        }

        return totalQty;
    }
}
