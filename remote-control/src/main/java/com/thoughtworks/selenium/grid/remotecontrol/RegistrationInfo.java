package com.thoughtworks.selenium.grid.remotecontrol;

/**
 * Information required when registering the Remote Control to the Hub
 */
public class RegistrationInfo {
    private final String seleniumHubURL;
    private final String environment;
    private final String host;
    private final String port;

    public RegistrationInfo(String seleniumHubURL, String environment, String host, String port) {
        this.seleniumHubURL = seleniumHubURL;
        this.environment = environment;
        this.host = host;
        this.port = port;
    }

    public String hubURL() {
        return seleniumHubURL;
    }

    public String environment() {
        return environment;
    }

    public String host() {
        return host;
    }

    public String port() {
        return port;
    }

    @Override
    public String toString() {
        final StringBuffer description;

        description = new StringBuffer("[RegistrationInfo ");
        description.append("seleniumHubURL='").append(seleniumHubURL).append("', ");
        description.append("env='").append(environment).append("', ");
        description.append("host='").append(host).append("', ");
        description.append("port='").append(port).append("'");
        description.append("]");        

        return description.toString();
    }
}
