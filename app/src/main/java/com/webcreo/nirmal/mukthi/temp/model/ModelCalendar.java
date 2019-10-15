package com.webcreo.nirmal.mukthi.temp.model;

import java.io.Serializable;

public class ModelCalendar implements Serializable {

    private String mDate;
    private String mMonth;
    private String mYear;

    private String mSeconds;
    private String mMinutes;
    private String mHours;

    public ModelCalendar()
    {
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmMonth() {
        return mMonth;
    }

    public void setmMonth(String mMonth) {
        this.mMonth = mMonth;
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public String getmSeconds() {
        return mSeconds;
    }

    public void setmSeconds(String mSeconds) {
        this.mSeconds = mSeconds;
    }

    public String getmMinutes() {
        return mMinutes;
    }

    public void setmMinutes(String mMinutes) {
        this.mMinutes = mMinutes;
    }

    public String getmHours() {
        return mHours;
    }

    public void setmHours(String mHours) {
        this.mHours = mHours;
    }
}