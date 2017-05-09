package com.nivida.bossb2b.helpers;

import android.graphics.Color;
import android.util.Log;

import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by NWSPL-17 on 2/10/2017.
 */

public class ColorDecorator implements DayDecorator {

    private boolean isSubmittedMonth = false;
    private List<String> filledDateList = new ArrayList<>();
    String selectedDate="";

    public ColorDecorator(boolean isSubmittedMonth) {
        this.isSubmittedMonth = isSubmittedMonth;
    }

    public ColorDecorator(boolean isSubmittedMonth,Date selectedDate) {
        this.isSubmittedMonth = isSubmittedMonth;
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateInDay=sdf.format(selectedDate);
        this.selectedDate = dateInDay;
    }

    public ColorDecorator(boolean isSubmittedMonth, List<String> filledDateList) {
        this.isSubmittedMonth = isSubmittedMonth;
        this.filledDateList = filledDateList;
    }

    public ColorDecorator(boolean isSubmittedMonth, List<String> filledDateList, Date selectedDate) {
        this.isSubmittedMonth = isSubmittedMonth;
        this.filledDateList = filledDateList;

        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateInDay=sdf.format(selectedDate);
        this.selectedDate = dateInDay;
    }

    @Override
    public void decorate(DayView cell) {
        /*if(cell.getDate() == ){*/

        if (isSubmittedMonth) {

            cell.setEnabled(false);
            cell.setClickable(false);
            cell.setTextIsSelectable(false);
            cell.setTextColor(Color.parseColor("#FF0000"));

            Log.e("Dates",cell.getDate().toString()+" --- "+selectedDate);

            SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
            String dateInDay=sdf.format(cell.getDate());

            if (selectedDate!=null && dateInDay.equalsIgnoreCase(selectedDate)) {

                Random rnd = new Random();
                int color = Color.parseColor("#F44336");
                cell.setBackgroundColor(color);
                cell.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }
        else {
            cell.setEnabled(false);
            cell.setClickable(false);
            cell.setTextIsSelectable(false);

            SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
            String dateInDay=sdf.format(cell.getDate());

            Log.e("List",filledDateList.contains(dateInDay)+"--"+dateInDay);

            if(filledDateList.contains(dateInDay))
                cell.setTextColor(Color.parseColor("#CC0000FF"));
            else
                cell.setTextColor(Color.parseColor("#000000"));

            if (selectedDate!=null && dateInDay.equalsIgnoreCase(selectedDate)) {

                Random rnd = new Random();
                int color = Color.parseColor("#396085");
                cell.setBackgroundColor(color);
                cell.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }

        /*}*/
    }
}
