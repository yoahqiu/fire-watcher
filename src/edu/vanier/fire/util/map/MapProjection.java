package edu.vanier.fire.util.map;

import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.Size;
import edu.vanier.fire.model.WeatherModel;

/**
 * Util Class of MapProjection
 *
 * @author Dam, Haowyeeh Brandon
 */
public class MapProjection {

    /**
     * method that calculates the coordinates for the projection path
     *
     * @param center the coordinates of the center of the fire
     * @param weather the weather data of the fire
     * @return a MVCArray for the shape of the projection
     */
    public static MVCArray getProjectionPath(LatLong center, WeatherModel weather) {
        double bearing;
        double distance;
        double latCenter, lonCenter;
        MVCArray coorStack = new MVCArray();

        double bearingA;
        double distanceA;
        double latA, lonA;

        double bearingB;
        double distanceB;
        double latB, lonB;

        double bearingC;
        double distanceC;
        double latC, lonC;

        double bearingD;
        double distanceD;
        double latD, lonD;

        double bearingE;
        double distanceE;
        double latE, lonE;

        double bearingF;
        double distanceF;
        double latF, lonF;

        double bearingG;
        double distanceG;
        double latG, lonG;

        double bearingH;
        double distanceH;
        double latH, lonH;

        final double R = 6378.1;

        bearing = weather.getWindDeg();
        distance = weather.getWindSpeed() * 3.6;
        latCenter = Math.toRadians(center.getLatitude());
        lonCenter = Math.toRadians(center.getLongitude());

        // point A 
        bearingA = Math.toRadians(bearing + 18.5);
        distanceA = distance * 1.5;

        latA = Math.asin(Math.sin(latCenter) * Math.cos(distanceA / R)
                + Math.cos(latCenter) * Math.sin(distanceA / R) * Math.cos(bearingA));

        lonA = lonCenter + Math.atan2(Math.sin(bearingA) * Math.sin(distanceA / R)
                * Math.cos(latCenter), Math.cos(distanceA / R) - Math.sin(latCenter)
                * Math.sin(latA));

        coorStack.push(new LatLong(Math.toDegrees(latA), Math.toDegrees(lonA)));

        // point B
        bearingB = Math.toRadians(bearing + 18.5 + 30);
        distanceB = distance * 0.9;

        latB = Math.asin(Math.sin(latCenter) * Math.cos(distanceB / R)
                + Math.cos(latCenter) * Math.sin(distanceB / R) * Math.cos(bearingB));

        lonB = lonCenter + Math.atan2(Math.sin(bearingB) * Math.sin(distanceB / R)
                * Math.cos(latCenter), Math.cos(distanceB / R) - Math.sin(latCenter)
                * Math.sin(latB));

        coorStack.push(new LatLong(Math.toDegrees(latB), Math.toDegrees(lonB)));

        // point C
        bearingC = Math.toRadians(bearing + 90);
        distanceC = distance;

        latC = Math.asin(Math.sin(latCenter) * Math.cos(distanceC / R)
                + Math.cos(latCenter) * Math.sin(distanceC / R) * Math.cos(bearingC));

        lonC = lonCenter + Math.atan2(Math.sin(bearingC) * Math.sin(distanceC / R)
                * Math.cos(latCenter), Math.cos(distanceC / R) - Math.sin(latCenter)
                * Math.sin(latC));

        coorStack.push(new LatLong(Math.toDegrees(latC), Math.toDegrees(lonC)));

        // point D 
        bearingD = Math.toRadians(bearing + 90 + 55);
        distanceD = distance;

        latD = Math.asin(Math.sin(latCenter) * Math.cos(distanceD / R)
                + Math.cos(latCenter) * Math.sin(distanceD / R) * Math.cos(bearingD));

        lonD = lonCenter + Math.atan2(Math.sin(bearingD) * Math.sin(distanceD / R)
                * Math.cos(latCenter), Math.cos(distanceD / R) - Math.sin(latCenter)
                * Math.sin(latD));

        coorStack.push(new LatLong(Math.toDegrees(latD), Math.toDegrees(lonD)));

        // point E
        bearingE = Math.toRadians(bearing - 90 - 55);
        distanceE = distance;

        latE = Math.asin(Math.sin(latCenter) * Math.cos(distanceE / R)
                + Math.cos(latCenter) * Math.sin(distanceE / R) * Math.cos(bearingE));

        lonE = lonCenter + Math.atan2(Math.sin(bearingE) * Math.sin(distanceE / R)
                * Math.cos(latCenter), Math.cos(distanceE / R) - Math.sin(latCenter)
                * Math.sin(latE));

        coorStack.push(new LatLong(Math.toDegrees(latE), Math.toDegrees(lonE)));

        // point F
        bearingF = Math.toRadians(bearing - 90);
        distanceF = distance;

        latF = Math.asin(Math.sin(latCenter) * Math.cos(distanceF / R)
                + Math.cos(latCenter) * Math.sin(distanceF / R) * Math.cos(bearingF));

        lonF = lonCenter + Math.atan2(Math.sin(bearingF) * Math.sin(distanceF / R)
                * Math.cos(latCenter), Math.cos(distanceF / R) - Math.sin(latCenter)
                * Math.sin(latF));

        coorStack.push(new LatLong(Math.toDegrees(latF), Math.toDegrees(lonF)));

        // point G
        bearingG = Math.toRadians(bearing - 18 - 30);
        distanceG = distance * 0.9;

        latG = Math.asin(Math.sin(latCenter) * Math.cos(distanceG / R)
                + Math.cos(latCenter) * Math.sin(distanceG / R) * Math.cos(bearingG));

        lonG = lonCenter + Math.atan2(Math.sin(bearingG) * Math.sin(distanceG / R)
                * Math.cos(latCenter), Math.cos(distanceG / R) - Math.sin(latCenter)
                * Math.sin(latG));

        coorStack.push(new LatLong(Math.toDegrees(latG), Math.toDegrees(lonG)));

        // point H
        bearingH = Math.toRadians(bearing - 18.5);
        distanceH = distance * 1.5;

        latH = Math.asin(Math.sin(latCenter) * Math.cos(distanceH / R)
                + Math.cos(latCenter) * Math.sin(distanceH / R) * Math.cos(bearingH));

        lonH = lonCenter + Math.atan2(Math.sin(bearingH) * Math.sin(distanceH / R)
                * Math.cos(latCenter), Math.cos(distanceH / R) - Math.sin(latCenter)
                * Math.sin(latH));

        coorStack.push(new LatLong(Math.toDegrees(latH), Math.toDegrees(lonH)));

        return coorStack;
    }

    public static InfoWindow getProjectionInfoWindow(WeatherModel weather) {
        InfoWindowOptions infoWindowOptions;
        InfoWindow weatherInfoWindow;
        String content;
        double temp;
        double windSpeeed;
        double windDeg;

        temp = weather.getTemperature() - 273;
        windSpeeed = weather.getWindSpeed() * 3.6;
        windDeg = weather.getWindDeg();
        content = String.format("<h2>Weather</h2>\n"
                + "<p>Temp : %.02f &deg;C<br />" + "Wind Speed :%.02f km/h<br />"
                + "Wind Degree : %.02f &deg;</p>", temp, windSpeeed, windDeg);

        Size size = new Size(0, -50);
        infoWindowOptions = new InfoWindowOptions().content(content);
        infoWindowOptions.pixelOffset(size);
        weatherInfoWindow = new InfoWindow(infoWindowOptions);

        return weatherInfoWindow;
    }

    public static double distanceTo(LatLong from, LatLong to) {
        final double R = 6371;

        double dLat = Math.toRadians(to.getLatitude() - from.getLatitude());
        double dLon = Math.toRadians(to.getLongitude() - from.getLongitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(from.getLatitude())) 
                * Math.cos(Math.toRadians(to.getLatitude()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return c * R;
    }
}
