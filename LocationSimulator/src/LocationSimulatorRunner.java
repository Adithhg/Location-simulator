import com.locationsimulator.model.GeoLocation;
import com.locationsimulator.util.LocationUtil;

import java.util.ArrayList;
import java.util.List;

public class LocationSimulatorRunner {

    public static final String HELP = "source=12.93175,77.62872&&destination=12.92662,77.63696";

    public static void main(String[] args) {

        if (args == null && args.length == 0) {
            System.out.println("no input present. \n"+HELP);
            return;
        }
        String sourceCoOrdinates = args[0].split("source=")[1].split("&&")[0];
        String destinationCoOrdinates = args[0].split("destination=")[1];

        GeoLocation sourceLocation = new GeoLocation(sourceCoOrdinates);
        GeoLocation destinationLocation = new GeoLocation(destinationCoOrdinates);

        String urlString = LocationUtil.makeURL(sourceLocation, destinationLocation);
        System.out.println(urlString);
        List<List<GeoLocation>> routes = LocationUtil.getMapPointsFromUrl(urlString);
        StringBuilder endToEndPathPoints = null;
        List<String> routPathPoints = new ArrayList<>();
        for (List<GeoLocation> route : routes) {
            endToEndPathPoints = new StringBuilder();
            for (int j = 0; j < route.size(); j++) {
                endToEndPathPoints.append(route.get(j).getLatitude()).append(",");
                endToEndPathPoints.append(route.get(j).getLongitude()).append(",");
                endToEndPathPoints.append("#00FF00,");
                endToEndPathPoints.append("marker,");
                endToEndPathPoints.append(j);
                endToEndPathPoints.append("\n");
            }
            routPathPoints.add(endToEndPathPoints.toString());
        }
        for (String s : routPathPoints) {
            System.out.println(s);
            System.out.println("**********************************************");
        }
    }
}
