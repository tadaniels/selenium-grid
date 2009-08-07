package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Periodicaly Poll Hub to check it is still up and re-register automatically
 * when the Hub disappears and come back up.
 */
public class HubPoller {
    private static final Log LOGGER = LogFactory.getLog(HubPoller.class);
    private final SelfRegisteringRemoteControl rc;
    private final int pollingIntervalInSeconds;
    private boolean active;
    private boolean lostConnectionToHub;

    public HubPoller(SelfRegisteringRemoteControl rc, int pollingIntervalInSeconds) {
        this(rc, pollingIntervalInSeconds, false);
    }

    protected HubPoller(SelfRegisteringRemoteControl rc, int pollingIntervalInSeconds,
                        boolean lostConnectionToHub) {
        this.rc = rc;
        this.pollingIntervalInSeconds = pollingIntervalInSeconds;
        this.lostConnectionToHub = lostConnectionToHub;
        this.active = true;
    }

    public SelfRegisteringRemoteControl remoteControl() {
        return this.rc;
    }

    public long pollingIntervalInMilliseconds() {
        return pollingIntervalInSeconds * 1000;
    }
    
    public boolean lostConnectionToHub() {
        return lostConnectionToHub;
    }

    public void checkConnectionToHub() {
        LOGGER.info("Checking connection to hub...");
        if (rc.canReachHub()) {
            if (lostConnectionToHub) {
                try {
                    rc.register();
                } catch (IOException e) {
                    LOGGER.error("Internal error while checking hub connection", e);
                }
            }
            lostConnectionToHub = false;
        } else {
            lostConnectionToHub = true;
        }
    }

    public boolean active() {
        return this.active;
    }

    public void stop() {
        this.active = false;
    }

    public void run() {
        while (active) {
            pollHub();
        }
    }

    public void pollHub() {
        sleepForALittleWhile();
        checkConnectionToHub();
    }

    protected void sleepForALittleWhile() {
        try {
            Thread.sleep(pollingIntervalInMilliseconds());
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted!");
        }
    }


}
