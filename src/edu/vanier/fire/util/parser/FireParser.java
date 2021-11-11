package edu.vanier.fire.util.parser;

import edu.vanier.fire.model.FireModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Class that gets all info from NASA API
 *
 * @author Yoah
 */
public class FireParser {

    public final static String HEAD_LINK = "https://firms.modaps.eosdis.nasa.gov/data/active_fire/c6/csv/MODIS_C6_Global_7d.csv";

    public static List<FireModel>[] parse(Set<String> response) {

        List<FireModel> fireList = new ArrayList<>();

        boolean isHeader = true;

        FireModel tempFire;

        int month;
        int day;
        int year;
        String dateTemp;
        String[] parsedInfo;
        String[] parsedDate;

        for (String currLine : response) {
            parsedInfo = currLine.split(",");
            if (!isHeader) {
                dateTemp = parsedInfo[5];
                parsedDate = dateTemp.split("-");
                month = Integer.parseInt(parsedDate[1]);
                day = Integer.parseInt(parsedDate[2]);
                year = Integer.parseInt(parsedDate[0]);

                tempFire = new FireModel();
                tempFire.setLatitude(Double.parseDouble(parsedInfo[0]));
                tempFire.setLongitude(Double.parseDouble(parsedInfo[1]));
                tempFire.setMonth(month);
                tempFire.setDay(day);
                tempFire.setTime(parsedInfo[6]);
                tempFire.setYear(year);

                fireList.add(tempFire);
            } else {
                isHeader = false;
            }
        }

        System.out.println("Parsed Fire CSV!");

        Collections.sort(fireList);

        System.out.println("Sorted");

        List<FireModel>[] fireLists = new ArrayList[8];
        fireLists[0] = new ArrayList<>();

        int currDay = fireList.get(0).getDay();
        int currIdx = 0;

        for (FireModel fire : fireList) {
            if (fire.getDay() != currDay) {
                currDay = fire.getDay();
                currIdx++;

                fireLists[currIdx] = new ArrayList<>();
            }

            fireLists[currIdx].add(fire);
        }

        return fireLists;
    }
}
