package com.nivida.bossb2b.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nivida.bossb2b.Bean.Bean_EditTadaReport;
import com.nivida.bossb2b.R;
import com.nivida.bossb2b.edit_report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ajay on 1/5/2017.
 */

public class TADAeditReportAdapter extends BaseAdapter {

    Context context;
    Activity activity;
    List<Bean_EditTadaReport> bean_editTadaReports = new ArrayList<>();

    Date date1;

    public TADAeditReportAdapter(Context context , List<Bean_EditTadaReport> reports , Activity activity) {

        this.context = context;
        this.bean_editTadaReports=reports;
        this.activity = activity;

    }

    @Override
    public int getCount() {
       return bean_editTadaReports.size();
    }

    @Override
    public Object getItem(int position) {
        return bean_editTadaReports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;

        ImageView edittada;

        TextView txt_to, txt_from , txt_report_date;

        int pos = position+1;

        final Bean_EditTadaReport tadaReport = bean_editTadaReports.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_edit_tada_report , parent , false);




        edittada = (ImageView) view.findViewById(R.id.img_edit);
        txt_to = (TextView) view.findViewById(R.id.txt_to);
        txt_from = (TextView) view.findViewById(R.id.txt_from);
        txt_report_date = (TextView) view.findViewById(R.id.txt_report_date);

        txt_from.setText(pos +".  " +bean_editTadaReports.get(position).getFrom_location());
        txt_to.setText(bean_editTadaReports.get(position).getTo_location());


     /*   SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd2 = new SimpleDateFormat("dd-MM-yyyy");

        String report_date = bean_editTadaReports.get(position).getReport_date();
*/

        String converttime = bean_editTadaReports.get(position).getReport_date();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = simpleDateFormat.parse(converttime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txt_report_date.setText(new SimpleDateFormat("dd-MM-yyyy").format(date1));


       // txt_report_date.setText(bean_editTadaReports.get(position).getReport_date());

        edittada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , edit_report.class);
                intent.putExtra("id" ,tadaReport.getId());
                intent.putExtra("reportDate" , tadaReport.getReport_date());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });

        return view;
    }
}
