package edu.vanier.fire.util.parser;

import edu.vanier.fire.model.WeatherModel;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author cstuser
 */
public class WeatherParser {
    
    public final static String HEAD_LINK = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&APPID=%s";
    
    public static WeatherModel parse(String response) {
        
        WeatherModel tempWeather = new WeatherModel();
        
        final String MAIN_KEY = "main";
        final String MAIN_TEMP_KEY = "temp";
        final String WIND_KEY = "wind";
        final String WIND_SPEED_KEY = "speed";
        final String WIND_DEG_KEY = "deg";
        final String RAIN_KEY = "rain";
        final String RAIN_VOL_KEY = "1h";
        final String SNOW_KEY = "snow";
        final String SNOW_VOL_KEY = "1h";
        
        try {
            JSONObject jresponse;
            jresponse = new JSONObject(response);
            
            JSONObject jtemp = null;
            
            if (jresponse.has(MAIN_KEY)) {
                jtemp = jresponse.getJSONObject(MAIN_KEY);
                
                tempWeather.setTemperature(jtemp.getDouble(MAIN_TEMP_KEY));
            }
            
            if (jresponse.has(WIND_KEY)) {
                jtemp = jresponse.getJSONObject(WIND_KEY);
                if (jtemp.has(WIND_SPEED_KEY)) {
                    tempWeather.setWindSpeed(jtemp.getDouble(WIND_SPEED_KEY));
                }
                if (jtemp.has(WIND_DEG_KEY)) {
                    tempWeather.setWindDeg(jtemp.getDouble(WIND_DEG_KEY));
                }                
            }
            
            if (jresponse.has(RAIN_KEY)) {
                jtemp = jresponse.getJSONObject(RAIN_KEY);
                if (jtemp.has(RAIN_VOL_KEY)) {
                    tempWeather.setRainVol(jtemp.getDouble(RAIN_VOL_KEY));
                }
            }
            
            if (jresponse.has(SNOW_KEY)) {
                jtemp = jresponse.getJSONObject(SNOW_KEY);
                if (jtemp.has(SNOW_VOL_KEY)) {
                    tempWeather.setSnowVol(jtemp.getDouble(SNOW_VOL_KEY));
                }
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Parsed Weather JSON!");
        
        return tempWeather;
    }
}
