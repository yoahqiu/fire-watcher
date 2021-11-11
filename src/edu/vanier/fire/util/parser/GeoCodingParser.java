/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.util.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * GeoCodingParser class that parses the API response to find the country
 *
 * @author brandon
 */
public class GeoCodingParser {

    public final static String HEAD_LINK = "https://api.opencagedata.com/geocode/v1/json?q=%f+%f&key=%s";

    public static String getCountry(String response) {
        final String RESULT_KEY = "results";
        final String RESULT_COMPONENTS_KEY = "components";
        final String COMPONENTS_COUNTRY_KEY = "country";

        try {
            JSONObject jresponse;
            jresponse = new JSONObject(response);

            if (jresponse.has(RESULT_KEY)) {
                JSONArray resultsArray = jresponse.getJSONArray(RESULT_KEY);

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject resultJSONObject = resultsArray.getJSONObject(i);

                    if (resultJSONObject.has(RESULT_COMPONENTS_KEY)) {
                        JSONObject compObject = resultJSONObject.getJSONObject(RESULT_COMPONENTS_KEY);
                        return compObject.getString(COMPONENTS_COUNTRY_KEY);
                    }
                }
            }

        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Parsed Weather JSON!");

        return null;
    }
}
