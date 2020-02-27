package com.kc.module_home.widget.timeAxis;

import java.util.Calendar;

class RecordFile {

    private Calendar mStartTime;
    private Calendar mStopTime;

    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        mStartTime = startTime;
    }

    public Calendar getStopTime() {
        return mStopTime;
    }

    public void setStopTime(Calendar stopTime) {
        mStopTime = stopTime;
    }
}
