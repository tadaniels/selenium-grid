package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.hub.HubRegistry;

import java.util.List;

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
        try {
            Thread.sleep(pollingIntervalInMilliseconds);
        } catch (InterruptedException e) {
            this.active = false;
        }

        unregisterAllUnresponsiveRemoteControls();
    }

    public void unregisterAllUnresponsiveRemoteControls() {
        final DynamicRemoteControlPool pool;

        pool = registry.remoteControlPool();
        unregisterUnresponsiveRemoteControls(pool.availableRemoteControls());
        unregisterUnresponsiveRemoteControls(pool.reservedRemoteControls());
    }

    public void unregisterUnresponsiveRemoteControls(List<RemoteControlProxy> remoteControls) {
        for (RemoteControlProxy rc : remoteControls) {
            if (rc.unreliable()) {
                registry.remoteControlPool().unregister(rc);
            }
        }
    }

}
