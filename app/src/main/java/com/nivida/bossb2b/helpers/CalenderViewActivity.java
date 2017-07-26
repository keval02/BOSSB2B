package com.nivida.bossb2b.helpers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;
import com.imanoweb.calendarview.DayDecorator;
import com.nivida.bossb2b.AppPref;
import com.nivida.bossb2b.Bean.BeanReportItem;
import com.nivida.bossb2b.ProgressActivity;
import com.nivida.bossb2b.R;
import com.nivida.bossb2b.ServiceHandler;
import com.nivida.bossb2b.Web;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalenderViewActivity extends AppCompatActivity {

    CustomCalendarView calendarView;
    List<BeanReportItem> reportItemList = new ArrayList<>();
    String dateSelected = "";

    AppPref pref;

    TextView currentdate, saveddates, submittedmonth;

    Button btnSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);
        pref = new AppPref(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        currentdate = (TextView) findViewById(R.id.txt_currentdate);
        saveddates = (TextView) findViewById(R.id.txt_saved);
        submittedmonth = (TextView) findViewById(R.id.txt_submitted);

        fetchIDs();
    }

    private void fetchIDs() {
        calendarView = (CustomCalendarView) findViewById(R.id.calendar_view);
        btnSelect = (Button) findViewById(R.id.btnSelect);

        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dateSelected.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                    try {
                        Date date = sdf.parse(dateSelected);
                        Date currentDate = new Date();

                        if (date.after(currentDate)) {
                            Toast.makeText(getApplicationContext(), "Please Select Date for Today or Previous Days!", Toast.LENGTH_SHORT).show();
                        } else {

                            boolean isSubmittedMonth = false;

                            for (int i = 0; i < reportItemList.size(); i++) {
                                if (reportItemList.get(i).isSubmitted()) {
                                    isSubmittedMonth = true;
                                    break;
                                }
                            }

                            if (isSubmittedMonth) {
                                Toast.makeText(getApplicationContext(), "You have already submitted report for this month!\nPlease Select date on which you have not submitted.", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent();
                                intent.putExtra("selectedDate", dateSelected);
                                //Log.e("Date Exception" , dateSelected);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }
                    } catch (ParseException e) {


                        e.printStackTrace();
                    }
                }
            }
        });

        calendarView.setShowOverflowDate(false);


        List<DayDecorator> dayDecoratorList = new ArrayList<>();
        dayDecoratorList.add(new ColorDecorator(true));
        calendarView.setDecorators(dayDecoratorList);
        calendarView.refreshCalendar(calendarView.getCurrentCalendar());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());

        String monthYear = sdf.format(new Date());
        new GetReportByMonth(monthYear + "-01", monthYear + "-31").execute();

        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dateSelected = sdf.format(date);

                setCalenderRefresh(date);

                /*List<DayDecorator> dayDecoratorList=new ArrayList<>();
                dayDecoratorList.add(new ColorDecorator(true, new ArrayList<String>(),date));
                calendarView.setDecorators(dayDecoratorList);
                calendarView.refreshCalendar(calendarView.getCurrentCalendar());*/
            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());

                String monthYear = sdf.format(date);
                new GetReportByMonth(monthYear + "-01", monthYear + "-31").execute();
            }
        });
    }

    private class GetReportByMonth extends AsyncTask<Void, Void, String> {

        String startDate = "";
        String endDate = "";
        ProgressActivity dialog;

        public GetReportByMonth(String startDate, String endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            dialog = new ProgressActivity(CalenderViewActivity.this, "");
            dialog.setCancelable(false);
            dialog.show();
        }


        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairList = new ArrayList<>();
            pairList.add(new BasicNameValuePair("com_sales_person_id", pref.getUser_id()));
            pairList.add(new BasicNameValuePair("start_date", startDate));
            pairList.add(new BasicNameValuePair("end_date", endDate));

            String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.TADA_CALENDER, ServiceHandler.POST, pairList);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            reportItemList.clear();

            dialog.dismiss();

            try {
                JSONObject object = new JSONObject(json);

                if (object.getBoolean("status")) {
                    JSONArray data = object.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject main = data.getJSONObject(i);

                        JSONObject TADAReport = main.getJSONObject("TADAReport");

                        BeanReportItem reportItem = new BeanReportItem();
                        reportItem.setSubmitted(TADAReport.getString("is_submit").equals("1"));

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Date date = sdf.parse(TADAReport.getString("report_date"));
                        reportItem.setDateSubmitted(sdf2.format(date));

                        reportItemList.add(reportItem);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Exception", e.getMessage());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            setCalenderRefresh();

        }
    }

    private void setCalenderRefresh() {
        boolean isSubmittedMonth = false;

        for (int i = 0; i < reportItemList.size(); i++) {
            if (reportItemList.get(i).isSubmitted()) {
                isSubmittedMonth = true;
                break;
            }
        }

        if (isSubmittedMonth) {
            List<DayDecorator> dayDecoratorList = new ArrayList<>();
            dayDecoratorList.add(new ColorDecorator(true));
            calendarView.setDecorators(dayDecoratorList);
            calendarView.refreshCalendar(calendarView.getCurrentCalendar());
        } else {
            List<String> savedDates = new ArrayList<>();

            for (int i = 0; i < reportItemList.size(); i++) {
                savedDates.add(reportItemList.get(i).getDateSubmitted());
            }

            List<DayDecorator> dayDecoratorList = new ArrayList<>();
            dayDecoratorList.add(new ColorDecorator(false, savedDates));
            calendarView.setDecorators(dayDecoratorList);
            calendarView.refreshCalendar(calendarView.getCurrentCalendar());

        }
    }

    private void setCalenderRefresh(Date date) {
        boolean isSubmittedMonth = false;

        for (int i = 0; i < reportItemList.size(); i++) {
            if (reportItemList.get(i).isSubmitted()) {
                isSubmittedMonth = true;
                break;
            }
        }

        if (isSubmittedMonth) {
            List<DayDecorator> dayDecoratorList = new ArrayList<>();
            dayDecoratorList.add(new ColorDecorator(true, date));
            calendarView.setDecorators(dayDecoratorList);
            calendarView.refreshCalendar(calendarView.getCurrentCalendar());

        } else {
            List<String> savedDates = new ArrayList<>();

            for (int i = 0; i < reportItemList.size(); i++) {
                savedDates.add(reportItemList.get(i).getDateSubmitted());
            }

            List<DayDecorator> dayDecoratorList = new ArrayList<>();
            dayDecoratorList.add(new ColorDecorator(false, savedDates, date));
            calendarView.setDecorators(dayDecoratorList);
            calendarView.refreshCalendar(calendarView.getCurrentCalendar());

        }
    }


}
