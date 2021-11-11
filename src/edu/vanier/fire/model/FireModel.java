package edu.vanier.fire.model;

/**
 * Class of a fire
 * @author Yoah
 */
public class FireModel implements Comparable<FireModel>{
    private int month;
    private int day;
    private int year;
    private int hours;
    private int minute;
    private double longitude;
    private double latitude;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
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
    
    public void setTime(String time) {
        int timeNum = Integer.parseInt(time);
        this.hours = timeNum / 100;
        this.minute = timeNum % 100;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(FireModel o) {
        int thisValue = this.year * 10000;
        thisValue += this.month * 100;
        thisValue += this.day;
        
        int otherVal = o.getYear() * 10000;
        otherVal += o.getMonth() * 100;
        otherVal += o.getDay();
        
        return thisValue - otherVal;
    }
}
