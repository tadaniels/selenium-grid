package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;

import java.util.List;

public class RemoteControlPoller implements Runnable {
    private final long pollingIntervalInMilliseconds;
    private final HubRegistry registry;
    private boolean done;

    public RemoteControlPoller(double pollingIntervalInSeconds, HubRegistry registry) {
        this.pollingIntervalInMilliseconds = (long) (pollingIntervalInSeconds * 1000);
        this.registry = registry;
        this.done = false;
    }

    public void run() {
        while (!done) {
            pollAllRegisteredRemoteControls();
        }
    }

    public void pollAllRegisteredRemoteControls() {
        unregisterAllUnresponsiveRemoteControls();

        try {
            Thread.sleep(pollingIntervalInMilliseconds);
        } catch (InterruptedException e) {
            this.done = true;
        }
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
