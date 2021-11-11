package edu.vanier.fire.util.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author doraFC
 */
public class RESTAPIController {

    public Set<String> GetCsvItems(String url, boolean hasHeader) {
        try {
            Set<String> returnSet = new HashSet<>();
            BufferedReader reader = getResource(new URL(url));

            System.out.println("Downloaded file");

            String line;

            if (hasHeader) {
                reader.readLine();
            }

            while ((line = reader.readLine()) != null) {
                returnSet.add(line);
            }

            System.out.println("Finished importing file");

            return returnSet;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedReader getResource(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        InputStream inputStream = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return reader;
    }

    public String getJSon(String url) {
        String response = "";

        try {
            BufferedReader reader = getResource(new URL(url));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            response = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
