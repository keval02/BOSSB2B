package com.nivida.bossb2b.helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;

/**
 * Created by NWSPL-17 on 2/15/2017.
 */

public class DatePickerDialogWithTitle extends DatePickerDialog {

    String title="";

    public DatePickerDialogWithTitle(Context context, int themeResId, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public DatePickerDialogWithTitle(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public DatePickerDialogWithTitle(Context context, int themeResId) {
        super(context, themeResId);
    }

    public DatePickerDialogWithTitle(Context context, OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
        super.onDateChanged(view, year, month, dayOfMonth);
        setTitle(title);
    }

    public void setAlwaysTitle(String title){
        this.title=title;
        setTitle(title);
    }
}
