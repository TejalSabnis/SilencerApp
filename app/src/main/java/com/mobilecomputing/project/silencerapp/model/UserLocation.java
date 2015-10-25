package com.mobilecomputing.project.silencerapp.model;

import android.location.Location;

import java.util.Date;

/**
 * Created by Tejal on 2015-10-22.
 */
public class UserLocation {

    private Location loc;
    private Date startTime;
    private Date endTime;
    private String dayOfWeek;

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
