package com.nivida.bossb2b;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nivida new on 30-Aug-16.
 */
public class  C {

    public static final String CUSTOMER_ROLE = "2";
    public static final String DISTRIBUTOR_ROLE = "3";
    public static final String DIS_RETAILER_ROLE = "8";
    public static final String DIS_SALES_ROLE = "7";
    public static final String COMP_SALES_ROLE = "6";
    public static final String DIRECT_DEALER_ROLE = "4";

    private static final String IMAGE_PATTERN =
            "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)";

    public static int getInt(String value) {
        return Integer.parseInt(value);
    }

    @NonNull
    public static String getStr(int value) {
        return String.valueOf(value);
    }

    @NonNull
    public static String getStr(boolean value) {
        return String.valueOf(value);
    }

    public static void Toast(Context context, String st,
                             LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.custom_toast, null);

        TextView text = (TextView) view.findViewById(R.id.toasttext);
        text.setText(st);
        text.setTextSize(14);
        //text.setTypeface(GlobalVariable.app_font());
//        text.setTypeface(GlobalVariable.app_font(context));
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();

    }

    public static boolean validatePANNUM(String pan) {
        Pattern pattern;
        Matcher matcher;
        String PAN_PATTERN = "[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}";
        pattern = Pattern.compile(PAN_PATTERN);
        matcher = pattern.matcher(pan);
        return matcher.matches();
    }

    public static boolean validateEmail1(String email) {

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static boolean validateImage(final String image) {
        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(image);
        return matcher.matches();
    }

    public static void loadImage(Context context, String url, int placeholder, ImageView view) {
        Picasso picasso = Picasso.with(context);
        //picasso.setIndicatorsEnabled(true);
        //picasso.setLoggingEnabled(true);
        //picasso.setDebugging(true);
        picasso.load(url)
                .placeholder(placeholder)
                .into(view);
    }

    public static int getMinimumValue(int value1, int value2) {
        if (value1 < value2)
            return value1;
        else if (value2 < value1)
            return value2;

        return value1;
    }



}
