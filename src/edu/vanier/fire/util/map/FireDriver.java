/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.util.map;

import edu.vanier.fire.util.network.RESTAPIController;
import edu.vanier.fire.util.parser.FireParser;

/**
 *
 * @author Yoah
 */
public class FireDriver {
    public static void main(String[] args) {
        String url = "https://firms.modaps.eosdis.nasa.gov/data/active_fire/c6/csv/MODIS_C6_Global_24h.csv";
        
        RESTAPIController ctrl = new RESTAPIController();
        FireParser.parse(ctrl.GetCsvItems(url, true));
    
        System.out.println("Done");
    }
    
}
