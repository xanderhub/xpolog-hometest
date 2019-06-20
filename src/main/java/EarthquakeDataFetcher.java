import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class EarthquakeDataFetcher {
    private static Logger LOG = LoggerFactory.getLogger(EarthquakeDataFetcher.class);
    private static final String earthquakeDataUrl = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";

    public static InputStreamReader getEarthquakeData() throws IOException {
        URL resource = new URL(earthquakeDataUrl);
        HttpURLConnection con = (HttpURLConnection) resource.openConnection();

        int responseCode = con.getResponseCode();
        LOG.info("Sending 'GET' request to URL : " + earthquakeDataUrl);
        LOG.info("Response Code : " + responseCode);

        return new InputStreamReader(con.getInputStream());
    }

}
