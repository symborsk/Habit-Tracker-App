package com.assignment_1.symborsk_habittracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by john on 21/09/16.
 */

public class Habit {
    private ArrayList<Day> rgDays = new ArrayList<>();
    private Date startDate;
    private String title;
    private ArrayList<Date> rgCompletions = new ArrayList<>();

    Habit(ArrayList<Day> rgDays, String title){
        this.rgDays = rgDays;
        this.title = title;
        this.startDate = new Date();
    }

    //Getters
    public String GetTitle(){

        return title;
    }

    public ArrayList<Date> GetCompletions(){

        return rgCompletions;
    }

    public Date GetStartDate(){

        return this.startDate;
    }

    public String GetStringDate(){
        DateFormat dateString = new SimpleDateFormat("yyyy-MM-dd");
        return dateString.format(this.startDate);
    }

    //Setters
    public void SetStartDate(Date date){

        this.startDate = date;
    }

    public Boolean DoesHabitExistForDay(Day day){

        return rgDays.contains(day);
    }

    public void AddCompletion(){

        rgCompletions.add(new Date());
    }

    public void RemoveCompletion(Date date){

        if(!rgCompletions.contains(date)){
            throw new RuntimeException("Cannot remove a completion that is not stored in the habit");
        }

        rgCompletions.remove(date);
    }

    public Boolean HasHabitBeenCompletedToday(){
        Date today = new Date();

        for(Date date: rgCompletions){
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

            if(fmt.format(date).equals(fmt.format(today))){
                return true;
            }
        }

        return false;
    }


}
