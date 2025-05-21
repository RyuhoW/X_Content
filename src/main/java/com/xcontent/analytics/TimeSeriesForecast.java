package com.xcontent.analytics;

import java.util.List;
import java.util.Map;

public class TimeSeriesForecast {
    private List<ForecastPoint> forecast;
    private Map<String, Double> confidenceIntervals;
    private double accuracy;
    private String model;

    public static class ForecastPoint {
        private String date;
        private double value;
        private double lowerBound;
        private double upperBound;

        // Getters and Setters
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public double getValue() { return value; }
        public void setValue(double value) { this.value = value; }

        public double getLowerBound() { return lowerBound; }
        public void setLowerBound(double lowerBound) { this.lowerBound = lowerBound; }

        public double getUpperBound() { return upperBound; }
        public void setUpperBound(double upperBound) { this.upperBound = upperBound; }
    }

    // Getters and Setters
    public List<ForecastPoint> getForecast() { return forecast; }
    public void setForecast(List<ForecastPoint> forecast) { this.forecast = forecast; }

    public Map<String, Double> getConfidenceIntervals() { return confidenceIntervals; }
    public void setConfidenceIntervals(Map<String, Double> confidenceIntervals) { this.confidenceIntervals = confidenceIntervals; }

    public double getAccuracy() { return accuracy; }
    public void setAccuracy(double accuracy) { this.accuracy = accuracy; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
} 