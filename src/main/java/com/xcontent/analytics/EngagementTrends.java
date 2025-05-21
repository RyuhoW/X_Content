package com.xcontent.analytics;

import java.util.List;
import java.util.Map;

public class EngagementTrends {
    private Map<String, Double> dailyTrends;
    private Map<String, Double> weeklyTrends;
    private Map<String, Double> monthlyTrends;
    private List<TrendPoint> trendLine;

    public static class TrendPoint {
        private String date;
        private double value;
        private double trend;

        // Getters and Setters
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public double getValue() { return value; }
        public void setValue(double value) { this.value = value; }

        public double getTrend() { return trend; }
        public void setTrend(double trend) { this.trend = trend; }
    }

    // Getters and Setters
    public Map<String, Double> getDailyTrends() { return dailyTrends; }
    public void setDailyTrends(Map<String, Double> dailyTrends) { this.dailyTrends = dailyTrends; }

    public Map<String, Double> getWeeklyTrends() { return weeklyTrends; }
    public void setWeeklyTrends(Map<String, Double> weeklyTrends) { this.weeklyTrends = weeklyTrends; }

    public Map<String, Double> getMonthlyTrends() { return monthlyTrends; }
    public void setMonthlyTrends(Map<String, Double> monthlyTrends) { this.monthlyTrends = monthlyTrends; }

    public List<TrendPoint> getTrendLine() { return trendLine; }
    public void setTrendLine(List<TrendPoint> trendLine) { this.trendLine = trendLine; }
} 