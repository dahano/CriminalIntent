package com.bigranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "time";


    private Date mDate;


    public static TimePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void sendResults(int resultCode){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, mDate);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //get date from extra
        mDate = (Date)getArguments().getSerializable(EXTRA_TIME);

        //create calendar to get hour and min
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        //inflate time dialog view
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_time,null);

        //get the time picker and set hour and min
        TimePicker timePicker = (TimePicker)view.findViewById(R.id.dialog_time_picker);
        timePicker.setCurrentHour( calendar.get(Calendar.HOUR));
        timePicker.setCurrentMinute( calendar.get(Calendar.MINUTE));

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                mDate.setTime( calendar.getTimeInMillis());
                getArguments().putSerializable(EXTRA_TIME, mDate);

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                sendResults(Activity.RESULT_OK);
                            }
                        })
                .create();
    }


}
