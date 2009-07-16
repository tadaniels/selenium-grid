package com.thoughtworks.selenium.grid.configuration;

/**
 * Selenium Hub Configuration.
 */
public class HubConfiguration {

    private int port;
    private double remoteControlPollingIntervalInSeconds;
    private double sessionMaxIdleTimeInSeconds;
    private EnvironmentConfiguration[] environments;

    public HubConfiguration() {
        this.port = 4444;
        this.environments = new EnvironmentConfiguration[] {};
        this.remoteControlPollingIntervalInSeconds = 3 * 60;
        this.sessionMaxIdleTimeInSeconds = 5 * 60;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public EnvironmentConfiguration[] getEnvironments() {
        return environments;
    }

    public void setEnvironments(EnvironmentConfiguration[] environments) {
        this.environments = environments;
    }

    public double getRemoteControlPollingIntervalInSeconds() {
        return remoteControlPollingIntervalInSeconds;
    }

    public void setRemoteControlPollingIntervalInSeconds(double intervalInSeconds) {
        this.remoteControlPollingIntervalInSeconds = intervalInSeconds;
    }

    public double getSessionMaxIdleTimeInSeconds() {
        return sessionMaxIdleTimeInSeconds;
    }

    public void setSessionMaxIdleTimeInSeconds(double newIdleTimeInSeconds) {
        this.sessionMaxIdleTimeInSeconds = newIdleTimeInSeconds;
    }

}