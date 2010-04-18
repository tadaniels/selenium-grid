package com.thoughtworks.selenium.grid.configuration;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class HubConfigurationTest {

    @Test
    public void defaultPortIs4444() {
        assertEquals(4444, new HubConfiguration().getPort());
    }

    @Test
    public void portCanBeSetToANonDefaultValue() {
        final HubConfiguration configuration = new HubConfiguration();
        configuration.setPort(5555);
        assertEquals(5555, configuration.getPort());
    }

    @Test
    public void defaultEnvironmentsArrayIsEmpty() {
        assertEquals(0, new HubConfiguration().getEnvironments().length);
    }

    @Test
    public void environmentsCanBeSetToANewValue() {
        final EnvironmentConfiguration[] expected;
        final HubConfiguration hubConfiguration;

        expected = new EnvironmentConfiguration[] {new EnvironmentConfiguration("a name", "a browser") };
        hubConfiguration = new HubConfiguration();
        hubConfiguration.setEnvironments(expected);
        assertEquals(expected, hubConfiguration.getEnvironments());
    }

    @Test
    public void defaultRemoteControlPollingIntervalInSecondsIs3Minutes() {
        assertEquals(180.0, new HubConfiguration().getRemoteControlPollingIntervalInSeconds());
    }

    @Test
    public void remoteControlPollingIntervalInSecondsCanBeSetToANonDefaultValue() {
        final HubConfiguration configuration = new HubConfiguration();
        configuration.setRemoteControlPollingIntervalInSeconds(5);
        assertEquals(5.0, configuration.getRemoteControlPollingIntervalInSeconds());
    }

    @Test
    public void defaultSessionMaxIdleTimeInSecondsIs5Minutes() {
        assertEquals(300.0, new HubConfiguration().getSessionMaxIdleTimeInSeconds());
    }

    @Test
    public void sessionMaxIdleTimeInSecondsCanBeSetToANonDefaultValue() {
        final HubConfiguration configuration = new HubConfiguration();
        configuration.setSessionMaxIdleTimeInSeconds(24);
        assertEquals(24.0, configuration.getSessionMaxIdleTimeInSeconds());
    }

    @Test
    public void defaultNewSessionMaxWaitTimeInSecondsIsInfinity() {
        assertEquals(Double.POSITIVE_INFINITY, new HubConfiguration().getNewSessionMaxWaitTimeInSeconds());
    }

    @Test
    public void newSessionMaxWaitTimeInSecondsCanBeSetToANonDefaultValue() {
        final HubConfiguration configuration = new HubConfiguration();
        configuration.setNewSessionMaxWaitTimeInSeconds(24.0);
        assertEquals(24.0, configuration.getNewSessionMaxWaitTimeInSeconds());
    }
}