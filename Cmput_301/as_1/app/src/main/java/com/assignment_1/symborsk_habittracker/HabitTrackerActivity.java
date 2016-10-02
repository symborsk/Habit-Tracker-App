package com.assignment_1.symborsk_habittracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;


/**
 * Created by john on 28/09/16.
 */

/**
 *  Attributions made in this file:
 *  http://stackoverflow.com/questions/5469629/put-enum-values-to-android-spinner
 *  http://stackoverflow.com/questions/17545060/custom-view-with-button-in-arrayadapter
 *  http://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
 */

public class HabitTrackerActivity extends AppCompatActivity {
    private ListView listView;
    private Spinner  daySpinner;
    private HabitDocument doc;

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        doc = new HabitDocument(this);

        // taken from: http://stackoverflow.com/questions/5469629/put-enum-values-to-android-spinner
        ArrayAdapter<Day> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Day.values());
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        daySpinner.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);

        listView = (ListView) findViewById(R.id.habitListView);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        daySpinner.setBackgroundResource(android.R.drawable.btn_dropdown);

        //Set up all the listeners
        SetUpListeners();
    }

    public void SetUpListeners(){
        Button AddHabit = (Button) findViewById(R.id.addButton);

        //Added so we can use it in the inner class
        final Context CurrentContext = this;

        AddHabit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final HabitCreationDlg dlg = new HabitCreationDlg(CurrentContext, doc);
                dlg.show();
            }
        });

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Day day = (Day) adapterView.getItemAtPosition(position);
                doc.SetDate(day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing since we use an enum class things will never disappear
            }
        });
    }

    public void UpdateCall(){
        HabitArrayAdapter habitAdapter = new HabitArrayAdapter(this, R.layout.habit_list_view_item, doc);
        listView.setAdapter(habitAdapter);
    }

    /**
     *  Attributions made in this file:

     */
    public class HabitArrayAdapter extends ArrayAdapter<Habit> {
        private int  customRowId;
        private Context context;
        private HabitDocument doc;

        HabitArrayAdapter(Context context, int customRowId, HabitDocument doc){
            super(context, customRowId, doc.GetDailyHabitList());
            this.customRowId = customRowId;
            this.context = context;
            this.doc = doc;
        }

        // taken from: http://stackoverflow.com/questions/17545060/custom-view-with-button-in-arrayadapter
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View currentView = convertView;
            Habit currentHabit = doc.GetDailyHabitList().get(position);
            HabitTag currentTag;

            if(convertView ==  null){
                //We need to create the view using the row id
                LayoutInflater inflater = ((Activity)this.context).getLayoutInflater();
                currentView = inflater.inflate(customRowId, parent, false);

                setUpOnClickHandlers(currentView, position);

                //If we do not have a HabitTag we need to create one
                currentTag = new HabitTag();
                currentTag.habitNameView = (TextView) currentView.findViewById(R.id.habitName);
                currentTag.dateCreatedView = (TextView) currentView.findViewById(R.id.dateCreated);
                currentTag.timesCompleted = (TextView) currentView.findViewById(R.id.habitCompletionsText);

                currentView.setTag(currentTag);
            }

            else{
                currentTag  = (HabitTag) convertView.getTag();
            }

            currentTag.habitNameView.setText(currentHabit.GetTitle());
            String currentDate = ("Starts: " + currentHabit.GetStringDate());
            currentTag.dateCreatedView.setText(currentDate);
            currentTag.timesCompleted.setText("Completed: " + Integer.toString(currentHabit.GetCompletions().size()));

            if(currentHabit.HasHabitBeenCompletedToday()){
                currentTag.timesCompleted.setTextColor(ContextCompat.getColor(this.getContext(), android.R.color.holo_blue_light));
            }
            else{
                currentTag.timesCompleted.setTextColor(ContextCompat.getColor(this.getContext(), android.R.color.holo_red_light));
            }

            return currentView;
        }

        private void setUpOnClickHandlers(View view, int position){
            Button completeButton = (Button) view.findViewById(R.id.completeButton);
            Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
            Button historyButton = (Button) view.findViewById(R.id.historyButton);
            TextView changeView = (TextView) view.findViewById(R.id.changeTextView);

            final int currentPosition = position;
            final Context parentContext = context;
            final Habit currentHabit = doc.GetDailyHabitList().get(currentPosition);
            final DatePickerDialog.OnDateSetListener datePickerHandler = new DatePickerDlgDataSet(doc, currentHabit);

            //This is to disable the complete button if the habit has not started yet
            if(!HabitHelper.IsDateTodayOrBefore(currentHabit.GetStartDate()))
            {
                completeButton.setEnabled(false);
            }
            completeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doc.addCompletion(currentPosition);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doc.DeleteHabit(currentPosition);
                }
            });

            historyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final HabitCompletionDlg dlg = new HabitCompletionDlg(parentContext, doc, currentHabit);
                    dlg.show();
                }
            });

            //taken from: http://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
            changeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(currentHabit.GetStartDate());
                    new DatePickerDialog(context, datePickerHandler, cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }
    }

    static class HabitTag{
        TextView habitNameView;
        TextView dateCreatedView;
        TextView timesCompleted;
    }

    class DatePickerDlgDataSet implements DatePickerDialog.OnDateSetListener{
        private HabitDocument doc;
        private Habit habit;

        DatePickerDlgDataSet(HabitDocument doc, Habit habit){
            this.doc = doc;
            this.habit = habit;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);

            doc.ChangeStartDateHabit(habit, cal.getTime());
        }
    }
}

