package edu.vanier.fire.model;

import java.io.Serializable;

/**
 * Model class of a notification
 * @author Yoah
 */
public class NotificationModel implements Serializable{
    private int month;
    private int dayFrom;
    private int dayTo;
    private double longitude;
    private double latitude;
    private double radius;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayFrom() {
        return dayFrom;
    }

    public void setDayFrom(int dayFrom) {
        this.dayFrom = dayFrom;
    }

    public int getDayTo() {
        return dayTo;
    }

    public void setDayTo(int dayTo) {
        this.dayTo = dayTo;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
