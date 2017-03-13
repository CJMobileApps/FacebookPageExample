package com.cjfreelancing.facebookexample.models;

import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Date {

    public static String getDate(String timeStamp){

        String[] months = new String[]{"January","February", "March" , "April" , "May", "June", "July",  "August", "September", "October", "November", "December"};

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
        try {
            Log.d("LOVE_", "onBindViewHolder: " + dateFormat.parse(timeStamp).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTimeInMillis(dateFormat.parse(timeStamp).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        String hour = "";
        if(calendar.get(Calendar.HOUR) == 0){
            hour = "12";
        }
        else {
            hour += calendar.get(Calendar.HOUR);

        }

        String minute = "";
        if(calendar.get(Calendar.MINUTE) < 10){
            minute = "0" + calendar.get(Calendar.MINUTE) ;
        }
        else {
            minute += calendar.get(Calendar.MINUTE);
        }


        String amOrPM;
        if(calendar.get(Calendar.AM_PM) == 1){
            amOrPM = "pm";
        }
        else {
            amOrPM = "am";
        }
        return months[month] + " " + day + " at " +hour+":"+minute+amOrPM ;
    }







}
