/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.model;

/**
 *
 * @author brandon
 */
public class PersonalInfo extends Contact{
    private double lat;
    private double lon;
    
    public PersonalInfo(String name, String email) {
        super(name, email);
        this.lat = Double.NaN;
        this.lon = Double.NaN;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" + "lat=" + lat + ", lon=" + lon + '}';
    }
    
    
}
