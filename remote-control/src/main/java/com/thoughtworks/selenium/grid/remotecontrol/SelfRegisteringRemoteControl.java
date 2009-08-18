package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.server.SeleniumServer;

import java.io.IOException;

/*
 * Selenium Remote Control that registers/unregisters itself to a central Hub when it starts/stops.
 */
public class SelfRegisteringRemoteControl {


    private static final Log logger = LogFactory.getLog(SelfRegisteringRemoteControlLauncher.class);
    private final RegistrationInfo registrationInfo;
    private final HubPoller hubPoller;

    public SelfRegisteringRemoteControl(RegistrationInfo registrationInfo, int hubPollerIntervalInSeconds) {
        this.registrationInfo = registrationInfo;
        this.hubPoller = new HubPoller(this, hubPollerIntervalInSeconds);
    }

    public RegistrationInfo registrationInfo() {
        return registrationInfo;
    }

    public void register() throws IOException {
        new RegistrationRequest(registrationInfo).execute();
    }

    public void unregister() throws IOException {
        new UnregistrationRequest(registrationInfo).execute();
    }

    public boolean canReachHub() {
        return new HeartbeatRequest(registrationInfo).execute();
    }

    public void launch(String[] args) throws Exception {
        logStartingMessages(args);
        startHubPoller();
        SeleniumServer.main(args);
    }

    protected HubPoller hubPoller() {
        return hubPoller;
    }

    protected void startHubPoller() {
        new Thread(hubPoller).start();
    }


    protected void logStartingMessages(String[] args) {
        logger.info("Starting selenium server with options:" + registrationInfo);
        for (String arg : args) {
            logger.info(arg);
        }
    }

    protected void ensureUnregisterOnShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    hubPoller.stop();
                    unregister();
                } catch (IOException e) {
                    logger.error("Could not unregister " + this, e);
                }
            }
        });
    }
}
