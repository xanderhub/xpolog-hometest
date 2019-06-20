public class DailyMagnitude {
    private String day;
    private double avgMagnitude;
    private int updatesCount;

    public DailyMagnitude(String day, double avgMagnitude) {
        this.day = day;
        this.avgMagnitude = avgMagnitude;
        this.updatesCount = 1;
    }

    public String getDay() {
        return day;
    }

    public double getAvgMagnitude() {
        return avgMagnitude;
    }

    public int getUpdatesCount() {
        return updatesCount;
    }

    public void setAvgMagnitude(double avgMagnitude) {
        this.avgMagnitude = avgMagnitude;
    }

    public void setUpdatesCount(int updatesCount) {
        this.updatesCount = updatesCount;
    }
}
