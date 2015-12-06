package com.mobilecomputing.project.silencerapp.model;

import android.location.Location;

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
    private Long startTime;
    private Long endTime;
    private String dayOfWeek;
    private String placeName;

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
