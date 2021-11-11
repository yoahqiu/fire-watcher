package edu.vanier.fire;

import javafx.fxml.FXMLLoader;

/**
 * Loader Class to make changing scene with controllers in different packages
 * @author doraFC
 */
public class Loader {
    
    private FXMLLoader loader;
    
    public void setAsMainPage() {
        loader = new FXMLLoader(getClass().getResource("fxml/MainPage.fxml"));
    }
    
    public void setAsWelcomePage() {
        loader = new FXMLLoader(getClass().getResource("fxml/WelcomePage.fxml"));
    }

    public FXMLLoader getLoader() {
        return loader;
    }    

    public void setAsAbout() {
        loader = new FXMLLoader(getClass().getResource("fxml/AboutPopup.fxml"));
    }
    
    public void setAsNotif() {
        loader = new FXMLLoader(getClass().getResource("fxml/NotificationPopup.fxml"));
    }
    
    public void setAsEditContact() {
        loader = new FXMLLoader(getClass().getResource("fxml/EditContact.fxml"));
    }
    
    public void setAsPersonalInfo() {
        loader = new FXMLLoader(getClass().getResource("fxml/PersonalInfo.fxml"));
    }
    
    public void setAsContacts() {
        loader = new FXMLLoader(getClass().getResource("fxml/ContactPopup.fxml"));
    }

    void setAsChecklist() {
        loader = new FXMLLoader(getClass().getResource("fxml/ChecklistPopup.fxml"));
    }
    
    public void setAsSelectLocation() {
        loader = new FXMLLoader(getClass().getResource("fxml/SelectLocation.fxml"));
    }
}
