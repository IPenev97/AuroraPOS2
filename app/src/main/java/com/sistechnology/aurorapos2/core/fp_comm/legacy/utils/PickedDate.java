package com.sistechnology.aurorapos2.core.fp_comm.legacy.utils;

import android.app.DatePickerDialog;
import android.widget.DatePicker;



import com.sistechnology.aurorapos2.R;
import com.sistechnology.aurorapos2.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PickedDate 
{
    public  int year = 0;
    public  int month = 0;
    public  int day = 0;

    
    public boolean isEditable() { return editable; }
    public void setEditable(boolean editable) { this.editable = editable; }

    public boolean editable;

    
    public String getDt() { return ToString(); }
    public void setDt(String dt) { SetFromString(dt); }

    public PickedDate() {
        editable = true;
        Calendar calendar = Calendar.getInstance();
        year = (calendar.get(Calendar.YEAR));
        month = (calendar.get(Calendar.MONTH));
        day = (calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void Clear()
    {
        day = 0;
        month = 0;
        year = 0;
    }

    public void Today()
    {
        Date now = Calendar.getInstance().getTime();
        day = (Integer.parseInt((String) android.text.format.DateFormat.format("dd", now)));
        month = (Integer.parseInt((String) android.text.format.DateFormat.format("MM", now)));
        year = (Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", now)));
    }

    public void Yesterday()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);

        Date now;
        now = c.getTime();

        day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", now));
        month = Integer.parseInt((String) android.text.format.DateFormat.format("MM", now));
        year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", now));
    }

    public void dateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    public void SetFromString(String dateStr)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = format.parse(dateStr);
            day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", date));
            month = Integer.parseInt((String) android.text.format.DateFormat.format("MM", date));
            year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void SetFromStringDb(String dateStr)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dateStr);
            day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", date));
            month = Integer.parseInt((String) android.text.format.DateFormat.format("MM", date));
            year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "dd.MM.yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Date dt = myCalendar.getTime();
        day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", dt));
        month = Integer.parseInt((String) android.text.format.DateFormat.format("MM", dt));
        year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", dt));


    }

    public String ToString()
    {
        if(day == 0 && month == 0 && year == 0)
            return "";

        return String.format(App.context.getString(R.string.dateFormat), day, month, year);
    }
    public String ToStringDb()
    {
        if(day == 0 && month == 0 && year == 0)
            return "";

        return String.format("%3$04d-%2$02d-%1$02d", day, month, year);
    }

    public String ToTimeStringDb()
    {
        if(day == 0 && month == 0 && year == 0)
            return "";

        return String.format("%3$04d-%2$02d-%1$02d 00:00:00", day, month, year);
    }

    public String ToTimeEndStringDb()
    {
        if(day == 0 && month == 0 && year == 0)
            return "";

        return String.format("%3$04d-%2$02d-%1$02d 23:59:59", day, month, year);
    }

    public String ToStringFp()
    {
        // DDMMYY
        if(day == 0 && month == 0 && year == 0)
            return "";

        return String.format("%02d%02d%02d", day, month, year >= 2000 ? year % 2000 : year % 1900);
    }


}

