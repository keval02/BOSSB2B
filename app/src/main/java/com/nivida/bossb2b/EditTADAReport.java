package com.nivida.bossb2b;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_EditTadaReport;
import com.nivida.bossb2b.adapter.TADAeditReportAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditTADAReport extends AppCompatActivity {
    EditText edt_todate , ed_tadafromdate;
    ListView edittadalist;
    ProgressDialog loadingview;
    AppPref appPref;

    String json = new String();

    TADAeditReportAdapter tadAeditReportAdapter;

    DatePickerDialog datePickerDialog;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    String topDate;

    String tooDate = "";
    String fromDate = "";


    int mYear, mMonth, mDay;

    List<Bean_EditTadaReport> editTadaReports = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tadareport);

        ed_tadafromdate = (EditText) findViewById(R.id.ed_tadafromdate);
        edt_todate = (EditText) findViewById(R.id.edt_tadatodate);
        edittadalist = (ListView) findViewById(R.id.list_edittadareport);
        appPref = new AppPref(getApplicationContext());

        tadAeditReportAdapter = new TADAeditReportAdapter(getApplicationContext() , editTadaReports , EditTADAReport.this);

        edittadalist.setAdapter(tadAeditReportAdapter);


        edt_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todate();
            }
        });

        ed_tadafromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromdate();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
    private void fromdate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        final String mYear2=   String.valueOf(mYear);
        final String mMonth2=   String.valueOf(mMonth);
        final String mDay2=   String.valueOf(mDay);

        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        fromDate=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;


                        ed_tadafromdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                    }
                    //
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    private void todate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        final String mYear2=   String.valueOf(mYear);
        final String mMonth2=   String.valueOf(mMonth);
        final String mDay2=   String.valueOf(mDay);



        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {



                        tooDate=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;


                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar1 = Calendar.getInstance();
                        Calendar calendar2 = Calendar.getInstance();

                        Log.e("todate",""+tooDate);
                        Log.e("fromdate",""+fromDate);


                        if(!fromDate.equalsIgnoreCase("")){
                            // new GetOrderHistory(DistSalesRetailerOrderHistActivity.this).execute();
                            try {


                                Date fromDate1 = sdf.parse(fromDate);
                                Date toDate1 = sdf.parse(tooDate);
                                Log.e("todate",""+fromDate1);
                                Log.e("todate",""+toDate1);

                                Log.e("todate",""+tooDate);
                                Log.e("todate",""+fromDate);


                                if(toDate1.before(fromDate1)){
                                    //  C.Toast(getApplicationContext(),"From Date Must be before To Date",getLayoutInflater());
                                    Toast.makeText(EditTADAReport.this, "Fromdate is Greater than Todate", Toast.LENGTH_LONG).show();
                                    Log.e("todate11111","22222");
                                }
                                else {
                                    edt_todate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    new editTADAreport().execute();
                                    Log.e("todate11111","33333");
                                    // new GetOrderHistory(DistSalesRetailerOrderHistActivity.this).execute();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.e("todate11111","4444");
                            edt_todate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            new editTADAreport().execute();
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public class editTADAreport extends AsyncTask<Void, Void, String> {

        boolean status;
        String date;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingview = new ProgressActivity(EditTADAReport.this, "");

                loadingview.setCancelable(false);
                loadingview.show();
                date = edt_todate.getText().toString();


            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            String date;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            date = df.format(c.getTime());

            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("com_sales_person_id", appPref.getUser_id()));
            Log.e("com_sales_person_id", appPref.getUser_id());


            parameters.add(new BasicNameValuePair("start_date", fromDate));
            parameters.add(new BasicNameValuePair("end_date", tooDate));
            Log.e("report_date", tooDate);


            String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.EDIT_REPORT, ServiceHandler.POST, parameters);

            Log.e("parameters", parameters.toString());


            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            loadingview.dismiss();
            editTadaReports.clear();
            tadAeditReportAdapter.notifyDataSetChanged();


            Log.e("json", json);

            if (json==null || json.isEmpty()){

                Toast.makeText(EditTADAReport.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            }
            else {

                Log.e("json" , json);
                try {

                    JSONObject object = new JSONObject(json);

                    boolean status = object.getBoolean("status");

                    if(status){

                       String message = object.getString("message");
                        Toast.makeText(getApplicationContext() , ""   +message , Toast.LENGTH_LONG).show();


                        JSONArray dataArray = object.getJSONArray("data");

                        for (int i=0 ; i<dataArray.length();i++){

                            JSONObject main = dataArray.getJSONObject(i);

                            JSONObject tadareport = main.getJSONObject("TADAReport");

                            Bean_EditTadaReport editTadaReport = new Bean_EditTadaReport();

                            editTadaReport.setId(tadareport.getString("id"));
                            editTadaReport.setCom_sales_person_id(tadareport.getString("com_sales_person_id"));
                            editTadaReport.setReport_date(tadareport.getString("report_date"));
                            editTadaReport.setFrom_location(tadareport.getString("from_location"));
                            editTadaReport.setTo_location(tadareport.getString("to_location"));
                            editTadaReport.setTravelling_cost(tadareport.getString("travelling_cost"));
                            editTadaReport.setLoading_boarding(tadareport.getString("loading_boarding"));
                            editTadaReport.setMisc_expenses(tadareport.getString("misc_expenses"));
                            editTadaReport.setComment(tadareport.getString("comment"));


                            editTadaReports.add(editTadaReport);


                        }
                        Log.e("Repots" , ""+editTadaReports.size());


                    }
                    else {

                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    }



                }
                catch (JSONException j) {
                    j.printStackTrace();
                    Log.e("excePtioN", j.getMessage());
                }

                tadAeditReportAdapter.notifyDataSetChanged();

            }


        }
    }
    public void onBackPressed() {
        Intent i = new Intent(EditTADAReport.this, Allowancee.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

}
