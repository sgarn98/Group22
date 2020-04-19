package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class OpenStreetMapUtils {

    public final static Logger log = Logger.getLogger("OpenStreetMapUtils");

    private static OpenStreetMapUtils instance = null;

    public static OpenStreetMapUtils getInstance() {
        if (instance == null) {
            instance = new OpenStreetMapUtils();
        }
        return instance;
    }

    private String getRequest(String url) {
        try {
            URL obj = new URL(url);

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(obj.openStream()));
            String str;
            StringBuilder response = new StringBuilder();

            while ((str = in.readLine()) != null) {
                response.append(str);
                // str is one line of text; readLine() strips the newline character(s)
            }

            in.close();

            return response.toString();
        } catch (MalformedURLException e) {
            log.error("malformed url: " + e.getMessage());
        } catch (IOException e) {
            log.error("io error: " + e.getMessage());
        }

        return null;
    }

    public Map<String, Double> getCoordinates(String address) {
        Map<String, Double> res;
        StringBuffer query;
        String[] split = address.split(" ");
        String queryResult = null;

        query = new StringBuffer();
        res = new HashMap<>();

        query.append("https://nominatim.openstreetmap.org/search?q=");

        if (split.length == 0) {
            return null;
        }

        for (int i = 0; i < split.length; i++) {
            query.append(split[i]);
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        query.append("&format=json&addressdetails=1");

        try {
            queryResult = getRequest(query.toString());
        } catch (Exception e) {
            log.error("Error when trying to get data with the following query " + query);
        }

        try {
            Object obj = JSONValue.parseWithException(queryResult);

            if (obj instanceof JSONArray) {
                JSONArray array = (JSONArray) obj;
                if (array.size() > 0) {
                    JSONObject jsonObject = (JSONObject) array.get(0);

                    String lon = (String) jsonObject.get("lon");
                    String lat = (String) jsonObject.get("lat");
                    res.put("lon", Double.parseDouble(lon));
                    res.put("lat", Double.parseDouble(lat));
                }
            }
        } catch (ParseException e) {
            log.debug("parse error: could not parse from object: " + e.getMessage());
        }

        return res;
    }
}