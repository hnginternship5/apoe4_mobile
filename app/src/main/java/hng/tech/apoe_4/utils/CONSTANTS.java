package hng.tech.apoe_4.utils;

import android.util.Log;

import java.util.Calendar;

public class CONSTANTS {

    public CONSTANTS() {
    }

    public static String getTimeOfDay() {
        int currentDateString = Calendar.getInstance().getTime().getHours();
        if (currentDateString >= 12 && currentDateString < 18){
            Log.e("Date", "Noon");
            return "Noon";
        }
        else if (currentDateString >= 6 && currentDateString < 12){
            Log.e("Date", "Day");
            return "Morning";
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
