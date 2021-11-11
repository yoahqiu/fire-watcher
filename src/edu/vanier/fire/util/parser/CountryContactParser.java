package edu.vanier.fire.util.parser;

import edu.vanier.fire.model.CountryContactModel;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class that parses a CSV to generate the emergency contact list
 * @author Yoah
 */
public class CountryContactParser {
    
    private final String filePath;

    public CountryContactParser(String filePath) {
        this.filePath = filePath;
    }
    
    public CountryContactParser() {
        this.filePath = "data/country_contacts.csv";
    }
    
    public HashMap<String, CountryContactModel> parse() {      
        CSVReader reader;
        
        try {
            File f = new File(filePath);
            FileReader file = new FileReader(f);
            reader = new CSVReader(file);
        } 
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("File not found");
            return null;
        }
        HashMap<String, CountryContactModel> contacts = new HashMap<>();
        String[] nextLine;
        try {
            while ((nextLine = reader.readNext()) != null) {
                CountryContactModel country = new CountryContactModel(nextLine[0]);
                country.setContacts(nextLine[1], nextLine[2], nextLine[3]);
                if (nextLine.length != 4)
                    System.out.println(nextLine);
                
                contacts.put(country.getName(), country);
            }
        } 
        catch (IOException ex) {
            System.out.println("Read exception");
        }
        
        return contacts;
    }
}
