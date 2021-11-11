/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.util.map;

import edu.vanier.fire.util.network.RESTAPIController;
import edu.vanier.fire.util.parser.GeoCodingParser;

/**
 *
 * @author brandon
 */
public class GeoCodingDriver {
    public static void main(String[] args) {
        String key = "db8cce1f9b1244d1a97404d29c6e0ff8&fbclid=IwAR3tVR_yNKnCMxebUWD40QJAjLhDEaB4MHCHICVfgXUAByJDVgeDBOfUDSo";

        double lat = 31.267401;
        double lon = 121.522179;

        RESTAPIController controller = new RESTAPIController();

        String response = controller.getJSon(String.format(GeoCodingParser.HEAD_LINK, lat, lon, key));

        String country = GeoCodingParser.getCountry(response);

        System.out.println(country);
    }
}
