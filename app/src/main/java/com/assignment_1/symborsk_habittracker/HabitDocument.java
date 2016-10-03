package com.assignment_1.symborsk_habittracker;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by john on 28/09/16.
 */

/**
 *  Attributions made in this file:
 *  https://github.com/joshua2ua/lonelyTwitter
 */

public class HabitDocument {
    private ArrayList<Habit> rgHabitListDaily = new ArrayList<>();
    private ArrayList<Habit> rgHabitList = new ArrayList<>();
    private Day WeekDay;
    private LoaderAndSaver fileHandler;
    private Context context;

    HabitDocument(Context context){
        this.context = context;
        this.fileHandler = new LoaderAndSaver(context);
        LoadHabitsFile();
    }

    private void SaveHabitsFile() {

        fileHandler.SaveToFile(rgHabitList);
    }

    private void LoadHabitsFile(){

        rgHabitList = fileHandler.LoadFromFile();
    }

    public ArrayList<Habit> GetDailyHabitList(){

        return rgHabitListDaily;
    }

    public void AddHabit(Habit habit ){
        rgHabitList.add(habit);

        SaveHabitsFile();
        SetDate(WeekDay);
    }

    public void addCompletion(int position){
        if(position >= rgHabitListDaily.size()){
            throw  new RuntimeException("Cannot complete a habit that is not there");
        }

        Habit habit = rgHabitListDaily.get(position);
        int index = rgHabitList.indexOf(habit);

        habit.AddCompletion();
        rgHabitList.set(index, habit);

        SaveHabitsFile();
        SetDate(WeekDay);
    }

    public void DeleteHabit(int index){
        if(index >= rgHabitListDaily.size()){
            throw  new RuntimeException("Cannot delete a habit that is not there");
        }
        Habit deletedHabit = rgHabitListDaily.get(index);
        rgHabitList.remove(deletedHabit);
        SaveHabitsFile();

        //Reset the weekday so it update daily list with proper values
        SetDate(WeekDay);
    }

    public void SetDate(Day day){
        WeekDay = day;
        rgHabitListDaily.clear();

        for(Habit habit: rgHabitList){
            if(habit.DoesHabitExistForDay(day)){
                rgHabitListDaily.add(habit);
            }
        }

        HabitTrackerActivity activity = (HabitTrackerActivity) context;
        activity.UpdateCall();
    }

    public void RemoveHabitCompletion(Habit habit, Date date){
        if(!habit.GetCompletions().contains(date)){
            throw new RuntimeException("Trying to remove a completion that is not there");
        }

        int index = rgHabitList.indexOf(habit);

        habit.RemoveCompletion(date);
        rgHabitList.set(index, habit);

        SaveHabitsFile();
        SetDate(WeekDay);
    }

    public void ChangeStartDateHabit(Habit habit, Date date){
        int index = rgHabitList.indexOf(habit);

        habit.SetStartDate(date);
        UpdateHabitCompletions(habit);
        rgHabitList.set(index, habit);


        SaveHabitsFile();
        SetDate(WeekDay);
    }

    public void UpdateHabitCompletions(Habit habit){

        Date startDate = habit.GetStartDate();
        for(int iHabitCount = 0; iHabitCount<habit.GetCompletions().size();){

            Date temp = habit.GetCompletions().get(iHabitCount);

            if(startDate.after(temp)){
                RemoveHabitCompletion(habit, temp);
            }

            //Move up if we do not remove so the search can continue
            else{
                iHabitCount++;
            }
        }
    }

    private class LoaderAndSaver{
        Context context;
        private static final String FILENAME = "file.sav";

        LoaderAndSaver(Context context){
            this.context = context;
        }

        //taken from: Lonley twitter git repo: https://github.com/joshua2ua/lonelyTwitter
        public ArrayList<Habit> LoadFromFile(){
            ArrayList<Habit> rgHabits;
            try {
                FileInputStream fis = context.openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
                rgHabits = gson.fromJson(in, listType);

            } catch (FileNotFoundException e) {
                //this is fine as it simply means there is no saved data
                return  new ArrayList<Habit>();
            } catch (IOException e) {
                throw new RuntimeException();
            }

            return rgHabits;
        }

        //taken from: Lonley twitter git repo: https://github.com/joshua2ua/lonelyTwitter
        public void SaveToFile(ArrayList<Habit> rgHabits){
            try {
                FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);


                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

                Gson gson = new Gson();
                gson.toJson(rgHabits, out);
                out.flush();

                fos.close();

            }catch(FileNotFoundException e){
                throw new RuntimeException();
            }catch(IOException e){
                throw new RuntimeException();
            }
        }
    }

}
