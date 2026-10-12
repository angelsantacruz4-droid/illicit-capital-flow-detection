package com.example.illicitcapitalflow.screening;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration for the OpenSanctions match API, which aggregates open-source
 * sanctions lists (OFAC, UN, EU, UK...), politically exposed persons (PEPs)
 * and criminal watchlists.
 */
@ConfigurationProperties(prefix = "opensanctions")
public class OpenSanctionsProperties {

    private String apiUrl = "https://api.opensanctions.org";
    private String apiKey;
    /** OpenSanctions dataset to match against: "default", "sanctions" or "peps". */
    private String dataset = "default";
    /** Minimum score (0-1) for a candidate to be reported. */
    private double threshold = 0.7;
    private int limit = 5;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
