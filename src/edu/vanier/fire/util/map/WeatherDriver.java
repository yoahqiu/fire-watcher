package edu.vanier.fire.util.map;

import edu.vanier.fire.model.WeatherModel;
import edu.vanier.fire.util.network.RESTAPIController;
import edu.vanier.fire.util.parser.WeatherParser;

/**
 *
 * @author cstuser
 */
public class WeatherDriver {

    public static void main(String[] args) {
        String key = "dfee42369d44d77131f6aeac638522f2";

        double lat = 45.5;
        double lon = -73.6;

        RESTAPIController controller = new RESTAPIController();

        String response = controller.getJSon(String.format(WeatherParser.HEAD_LINK, lat, lon, key));

        WeatherModel model = WeatherParser.parse(response);

        System.out.println(model);
    }
}
