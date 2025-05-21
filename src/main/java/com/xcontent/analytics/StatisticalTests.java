package com.xcontent.analytics;

import java.util.Map;

public class StatisticalTests {
    private Map<String, Double> correlations;
    private Map<String, Double> pValues;
    private Map<String, TestResult> hypothesisTests;

    public static class TestResult {
        private String testName;
        private double statistic;
        private double pValue;
        private boolean significant;
        private String interpretation;

        // Getters and Setters
        public String getTestName() { return testName; }
        public void setTestName(String testName) { this.testName = testName; }

        public double getStatistic() { return statistic; }
        public void setStatistic(double statistic) { this.statistic = statistic; }

        public double getPValue() { return pValue; }
        public void setPValue(double pValue) { this.pValue = pValue; }

        public boolean isSignificant() { return significant; }
        public void setSignificant(boolean significant) { this.significant = significant; }

        public String getInterpretation() { return interpretation; }
        public void setInterpretation(String interpretation) { this.interpretation = interpretation; }
    }

    // Getters and Setters
    public Map<String, Double> getCorrelations() { return correlations; }
    public void setCorrelations(Map<String, Double> correlations) { this.correlations = correlations; }

    public Map<String, Double> getPValues() { return pValues; }
    public void setPValues(Map<String, Double> pValues) { this.pValues = pValues; }

    public Map<String, TestResult> getHypothesisTests() { return hypothesisTests; }
    public void setHypothesisTests(Map<String, TestResult> hypothesisTests) { this.hypothesisTests = hypothesisTests; }
} 