package com.locationsimulator.util;

import com.locationsimulator.model.GeoLocation;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.*;

public class LocationUtil {

    public static String makeURL (GeoLocation source, GeoLocation destination){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(source.getLatitude());
        urlString.append(",");
        urlString.append(source.getLongitude());
        urlString.append("&destination=");// to
        urlString.append(destination.getLatitude());
        urlString.append(",");
        urlString.append(destination.getLongitude());
        urlString.append("&sensor=false&mode=walking&alternatives=true");
        urlString.append("&key=AIzaSyAEQvKUVouPDENLkQlCF6AAap1Ze-6zMos");
        return urlString.toString();
    }

    public static List<List<GeoLocation>> getMapPointsFromUrl(String urlString) {
        List<List<GeoLocation>> routPaths = new ArrayList<>();
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
            System.out.println(result);
            JSONObject json = new JSONObject(result.toString());
            JSONArray routeArr = json.getJSONArray("routes");

            for (int i = 0; i < routeArr.length(); i++) {
                JSONObject routeJson = routeArr.getJSONObject(i);
                JSONArray routeLegs = routeJson.getJSONArray("legs");
                List<GeoLocation> locationList = new ArrayList<>();
                for (int j = 0; j < routeLegs.length(); j++) {
                    JSONObject routeLegJson = routeLegs.getJSONObject(j);
                    JSONArray steps = routeLegJson.getJSONArray("steps");
                    for (int step = 0; step < steps.length(); step++) {
                        JSONObject stepsJson = steps.getJSONObject(step);
                        if (step == 0) {
                            //for the first step, take both start and end locations
                            JSONObject startLocation = (JSONObject) stepsJson.get("start_location");
                            locationList.add(new GeoLocation(startLocation.get("lat").toString(), startLocation.get("lng").toString()));
                        }
                        JSONObject endLocation = (JSONObject) stepsJson.get("end_location");
                        locationList.add(new GeoLocation(endLocation.get("lat").toString(), endLocation.get("lng").toString()));
                    }
                }
                routPaths.add(locationList);
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }

        return routPaths;
    }
}
