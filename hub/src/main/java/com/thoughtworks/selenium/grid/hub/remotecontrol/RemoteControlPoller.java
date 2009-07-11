package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.hub.HubRegistry;

public class RemoteControlPoller implements Runnable {
    private final long pollingIntervalInMilliseconds;
    private final HubRegistry registry;
    private boolean active;

    public RemoteControlPoller(double pollingIntervalInSeconds, HubRegistry registry) {
        this.pollingIntervalInMilliseconds = (long) (pollingIntervalInSeconds * 1000);
        this.registry = registry;
        this.active = true;
    }

    public void run() {
        while (active) {
            pollAllRegisteredRemoteControls();
        }
    }

    public void pollAllRegisteredRemoteControls() {
        sleepForALittleWhile();
        registry.remoteControlPool().unregisterAllUnresponsiveRemoteControls();
    }

    public void stop() {
        this.active = false;
    }

    protected void sleepForALittleWhile() {
        try {
            Thread.sleep(pollingIntervalInMilliseconds);
        } catch (InterruptedException e) {
            stop(); // TODO - This is wrong
        }
    }


}
