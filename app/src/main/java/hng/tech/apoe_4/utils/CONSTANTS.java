package hng.tech.apoe_4.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CONSTANTS {
    Calendar cal = Calendar.getInstance();
    Date date = cal.getTime();
    Date date2 = cal.getTime();
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



    DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");

    public CONSTANTS() {
    }

    static String getState(Date currentDate){
        if (currentDate.compareTo(noon) >= 0 && currentDate.compareTo(evening) <= 0){
            return "Noon";
        }
        else if (currentDate.compareTo(morning) >= 0 && currentDate.compareTo(noon) <= 0){
            return "Day";
        }
        else if (currentDate.compareTo(evening) >= 0 && currentDate.compareTo(midnight) <= 0){
            return "Night";
        } else return "Midnight";
    }
}
