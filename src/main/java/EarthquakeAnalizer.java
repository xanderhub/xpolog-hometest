import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EarthquakeAnalizer {
    private static Logger LOG = LoggerFactory.getLogger(EarthquakeAnalizer.class);
    final static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    final static Map<String, DailyMagnitude> magnitudesMap = new LinkedHashMap<>();

    public static void main(String[] args) {
        getLastWeekStats();
        printLastWeekStats();
    }

    public static void getLastWeekStats() {
        try {
            LOG.info("Analizing response...");
            long lastWeek = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)).toInstant().toEpochMilli();
            DateFormat df = new SimpleDateFormat("dd:MM:yy");
            JsonObject jsonObject = gson.fromJson(EarthquakeDataFetcher.getEarthquakeData(), JsonObject.class);
            JsonArray features = jsonObject.getAsJsonObject().getAsJsonArray("features");
            for (JsonElement feature : features) {
                JsonObject properties = feature.getAsJsonObject().get("properties").getAsJsonObject();
                if(properties.get("time").getAsLong() >= lastWeek) {
                    String day = df.format(new Date(properties.get("time").getAsLong()));
                    Double magnitude = properties.get("mag").getAsDouble();
                    DailyMagnitude currentMagnitude = magnitudesMap.get(day);
                    if(currentMagnitude == null)
                        magnitudesMap.put(day, new DailyMagnitude(day, magnitude));
                    else {
                        magnitudesMap.put(day, calculateNewAvg(magnitude, currentMagnitude));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static DailyMagnitude calculateNewAvg(Double newMagnitude, DailyMagnitude currentMagnitude) {
        currentMagnitude.setUpdatesCount(currentMagnitude.getUpdatesCount() + 1);
        return new DailyMagnitude(currentMagnitude.getDay(), (newMagnitude + currentMagnitude.getAvgMagnitude())/currentMagnitude.getUpdatesCount());
    }

    private static void printLastWeekStats() {
        DecimalFormat dec = new DecimalFormat("#0.00");
        for (Map.Entry<String, DailyMagnitude> entry : magnitudesMap.entrySet()) {
            System.out.println(entry.getKey() + "   " + dec.format(entry.getValue().getAvgMagnitude()));
        }
    }
}
