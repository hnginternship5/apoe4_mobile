package hng.tech.apoe_4.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CONSTANTS {
    Calendar cal = Calendar.getInstance();
    static DateFormat dateFormat = new SimpleDateFormat("HH:mm");
    static Date noon;

    static {
        try {
            noon = dateFormat.parse("12:00");
            evening = dateFormat.parse("18:00");
            morning = dateFormat.parse("06:00");
            midnight = dateFormat.parse("00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static Date evening;



    static Date morning;



    static Date midnight;




    public CONSTANTS() {
    }

    public static String getState() {
        int currentDateString = Calendar.getInstance().getTime().getHours();
        if (currentDateString >= 12 && currentDateString <= 18){
            Log.e("Date", "Noon");
            return "Noon";
        }
        else if (currentDateString >= 6 && currentDateString <= 12){
            Log.e("Date", "Day");
            return "Day";
        }
        else if (currentDateString >= 18 && currentDateString <= 23){
            Log.e("Date", "Night");
            return "Night";
        } else {
            Log.e("Date", "MidNight");
            return "Midnight";
        }
    }
}
