package com.assignment_1.symborsk_habittracker;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
/**
 * Created by john on 30/09/16.
 */

public class HabitTestClass {

    @Test
    public void testHabitTitle(){
        Habit habit = new Habit(new ArrayList<Day>(), "TestHabit");

        assertEquals("TestHabit", habit.GetTitle());
    }

    @Test
    public void testHabitDateSetter(){
        Habit habit = new Habit(new ArrayList<Day>(), "TestHabit");
        Date date = new Date();
        habit.SetStartDate(date);

        assertEquals(date, habit.GetStartDate());
    }

    @Test
    // ensure the format is MM-DD-YYYY
    public void testHabitDateDisplay(){
        Habit habit = new Habit(new ArrayList<Day>(), "TestHabit");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 20);
        cal.set(Calendar.MONTH, 3); // because for whatever reason months are 0 indexed why???????
        cal.set(Calendar.YEAR, 1995);

        habit.SetStartDate(cal.getTime());

        assertEquals("1995-04-20", habit.GetStringDate());
    }

    @Test(expected = RuntimeException.class)
    public void testRemoveCompletionThatDoesNotExist(){
        Habit habit = new Habit(new ArrayList<Day>(), "TestHabit");
        Date date = new Date();

        habit.RemoveCompletion(date);
    }

    @Test
    public void testRemovalOfCompletion(){
        Habit habit = new Habit(new ArrayList<Day>(), "TestHabit");

        //Get the date and then remove it
        habit.AddCompletion();
        Date date = habit.GetCompletions().get(0);

        habit.RemoveCompletion(date);

        assertEquals(0, habit.GetCompletions().size());
    }

    @Test
    public void testMultipleCompletionsRemoval(){
        Habit habit = new Habit(new ArrayList<Day>(), "TestHabit");

        //Get the date and then remove it
        habit.AddCompletion();
        habit.AddCompletion();
        Date date1 = habit.GetCompletions().get(0);
        Date date2 = habit.GetCompletions().get(1);

        habit.RemoveCompletion(date1);

        assertEquals(date2, habit.GetCompletions().get(0));
    }

    @Test
    public void testCompletingAHabit(){
        Habit habit = new Habit(new ArrayList<Day>(), "TestHabit");

        habit.AddCompletion();
        habit.AddCompletion();
        habit.AddCompletion();
        habit.AddCompletion();
        habit.AddCompletion();
        habit.AddCompletion();

        assertEquals(6, habit.GetCompletions().size());
    }

    @Test
    public void testHabitExistForDay(){
        Habit habit = new Habit(new ArrayList<>(Arrays.asList(Day.MONDAY, Day.FRIDAY)), "TestHabit");
        assertEquals(true, habit.DoesHabitExistForDay(Day.MONDAY));
    }

    @Test
    public void testHabitDoesNotExistForDay(){
        Habit habit = new Habit(new ArrayList<>(Arrays.asList(Day.MONDAY, Day.FRIDAY)), "TestHabit");
        assertEquals(false, habit.DoesHabitExistForDay(Day.TUESDAY));
    }

    @Test
    public void HasHabitBeenCompletedTodayFalse(){
        Habit habit = new Habit(new ArrayList<Day>(), "TestHabit");
        assertEquals(false, habit.HasHabitBeenCompletedToday());
    }

    @Test
    public void HashHabitBeenCompletedTodayTrue(){
        Habit habit = new Habit(new ArrayList<Day>(), "TestHabit");
        habit.AddCompletion();
        assertEquals(true, habit.HasHabitBeenCompletedToday());
    }

}
