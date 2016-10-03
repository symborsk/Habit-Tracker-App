package com.assignment_1.symborsk_habittracker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by john on 01/10/16.
 */

public class HabitHelper {

    static public Boolean IsDateTodayOrBefore(Date date){
        Date today = new Date();
        if(today.after(date)){
            return true;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar calToday = Calendar.getInstance();
        calToday.setTime(today);

        if(calToday.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) &&
                calToday.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                calToday.get(Calendar.MONTH) == cal.get(Calendar.MONTH)){
            return true;
        }

        return false;
    }
}
