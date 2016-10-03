package com.assignment_1.symborsk_habittracker;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by john on 28/09/16.
 */

/**
 *  Attributions made in this file:
 *  Border around the list view: http://stackoverflow.com/questions/21253623/border-of-items-of-listview-in-android
 *  Selection process: http://stackoverflow.com/questions/16189651/android-listview-selected-item-stay-highlighted
 */

public class HabitCompletionDlg extends Dialog {
    private HabitDocument doc;
    private Habit habit;
    private Context context;
    private ListView listView;
    private Date selectedCompletion = null;

    HabitCompletionDlg(Context context, HabitDocument mainDoc, Habit habit){
        super(context);
        this.context = context;
        this.habit = habit;
        this.doc = mainDoc;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.habit_history_dlg);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, GetDateStrings());
        this.listView = (ListView)findViewById(R.id.habitCompletionList);
        listView.setAdapter(adapter);

        TextView titleHabit = (TextView)findViewById(R.id.historyName);
        String title = "Completions for:\n"+habit.GetTitle();
        titleHabit.setText(title);

        SetUpListeners();
    }

    public ArrayList<String> GetDateStrings() {
        ArrayList<String> rgStrings = new ArrayList<>();

        ArrayList<Date> dates = habit.GetCompletions();

        if(dates.size() == 0){
            rgStrings.add("No Completions");
        }

        for(Date date: dates){

            DateFormat dateString = new SimpleDateFormat("EEEE MMM dd, yyyy - hh:mm a");
            rgStrings.add(dateString.format(date));
        }

        return rgStrings;
    }

    private void SetUpListeners(){
        Button deleteButton = (Button) findViewById(R.id.deleteCompButton);
        Button closeButton = (Button) findViewById(R.id.closeHistory);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if(listView.getItemAtPosition(position).equals("No Completions")){
                    selectedCompletion = null;
                    return;
                }

                selectedCompletion = habit.GetCompletions().get(position);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(selectedCompletion != null){
                    doc.RemoveHabitCompletion(habit, selectedCompletion);
                    selectedCompletion = null;
                    UpdateHabitList();
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void UpdateHabitList() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, GetDateStrings());
        listView.setAdapter(adapter);
    }
 }


