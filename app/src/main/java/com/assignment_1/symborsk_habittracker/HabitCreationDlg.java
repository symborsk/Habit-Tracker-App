package com.assignment_1.symborsk_habittracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by john on 23/09/16.
 */

/**
 *  Attributions made in this file:
 *  http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
 *  http://stackoverflow.com/questions/5308200/clear-text-in-edittext-when-entered
 */

public class HabitCreationDlg extends Dialog {
    private HabitDocument doc;

    HabitCreationDlg(Context context, HabitDocument mainDoc){
        super(context);
        this.doc = mainDoc;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.habit_add_dlg);
        this.getWindow().setBackgroundDrawableResource(android.R.color.white);

        InitializeListeners();
    }

    public ArrayList<Day> GetHabitDates(){
        ArrayList<Day> rgDates = new ArrayList<>();

        //Is monday selected
        CheckBox Mon = (CheckBox)findViewById(R.id.monCheckBox);
        if(Mon.isChecked())
        {
            rgDates.add(Day.MONDAY);
        }

        CheckBox Tues = (CheckBox)findViewById(R.id.tuesCheckBox);
        if(Tues.isChecked())
        {
            rgDates.add(Day.TUESDAY);
        }

        CheckBox Wed = (CheckBox)findViewById(R.id.wedCheckBox);
        if(Wed.isChecked())
        {
            rgDates.add(Day.WEDNESDAY);
        }

        CheckBox Thur = (CheckBox)findViewById(R.id.thuCheckBox);
        if(Thur.isChecked())
        {
            rgDates.add(Day.THURSDAY);
        }

        CheckBox Fri = (CheckBox)findViewById(R.id.friCheckBox);
        if(Fri.isChecked())
        {
            rgDates.add(Day.FRIDAY);
        }

        CheckBox Sat = (CheckBox)findViewById(R.id.satCheckBox);
        if(Sat.isChecked())
        {
            rgDates.add(Day.SATURDAY);
        }

        CheckBox Sun = (CheckBox)findViewById(R.id.sunCheckBox);
        if(Sun.isChecked())
        {
            rgDates.add(Day.SUNDAY);
        }

        return rgDates;
    }

    public String GetHabitName(){
        EditText textView = (EditText)findViewById(R.id.nameText);
        return textView.getText().toString();
    }

    private void InitializeListeners(){

        // We want to ensure that when the user clicks add or ]cancel the dialog closes
        Button dlgAddButton = (Button) findViewById(R.id.addButton);

        dlgAddButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                ArrayList<Day> rgDays = GetHabitDates();

                //taken from: http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
                //ensure the user had selected one day
                if(rgDays.size() == 0){
                    AlertDialog.Builder noDaysError = new AlertDialog.Builder(getContext());
                    noDaysError.setTitle("No date selected");
                    noDaysError.setMessage("Please select at least one day ");

                    noDaysError.setNegativeButton("OK", null);
                    noDaysError.create().show();
                }
                //Save the info
                else {
                    doc.AddHabit(new Habit(GetHabitDates(), GetHabitName()));
                    dismiss();
                }
            }
        });

        Button dlgCancelButton = (Button) findViewById(R.id.cancelButton);

        dlgCancelButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                dismiss();
            }
        });

        // taken from: http://stackoverflow.com/questions/5308200/clear-text-in-edittext-when-entered
        final TextView habitName = (TextView) findViewById(R.id.nameText);
        habitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitName.setText("");
            }
        });
    }
}
