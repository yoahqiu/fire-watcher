package edu.vanier.fire.model;

/**
 * Class of a country contact list
 * @author Yoah
 */
public class CountryContactModel {
    
    private final String name;
    private String emergency;
    private String police;
    private String ambulance;

    public CountryContactModel(String name) {
        this.name = name;
    }
    
    public void setContacts(String emergency, String police, String ambulance) {
        if (!emergency.equals("")) 
            this.emergency = emergency;
        else 
            this.emergency = "Not available";
        
        if (!police.equals("")) 
            this.police = police;
        else 
            this.police = "Not available";
        
        if (!ambulance.equals("")) 
            this.ambulance = ambulance;
        else 
            this.ambulance = "Not available";
    }

    @Override
    public String toString() {
        return "name=" + name + ", emergency=" + emergency + ", police=" + police + ", ambulance=" + ambulance;
    }

    public String getName() {
        return name;
    }

    public String getEmergency() {
        return emergency;
    }

    public String getPolice() {
        return police;
    }

    public String getAmbulance() {
        return ambulance;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public void setAmbulance(String ambulance) {
        this.ambulance = ambulance;
    }
}
