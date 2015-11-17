package com.mobilecomputing.project.silencerapp.model;

import android.location.Location;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tejal on 2015-10-22.
 */
public class UserLocation {

    public static Map<Integer, String> daysOfWeek = new HashMap<>();
    static {
        daysOfWeek.put(1,"Monday");
        daysOfWeek.put(2,"Tuesday");
        daysOfWeek.put(3,"Wednesday");
        daysOfWeek.put(4,"Thursday");
        daysOfWeek.put(5,"Friday");
        daysOfWeek.put(6,"Saturday");
        daysOfWeek.put(7,"Sunday");
    }

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
