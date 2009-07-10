package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;

import java.util.List;

public class RemoteControlPoller implements Runnable {
    private final long thinkTimeInMilliseconds;
    private final HubRegistry registry;
    private boolean done;

    public RemoteControlPoller(double thinkTimeInSeconds, HubRegistry registry) {
        this.thinkTimeInMilliseconds = (long) (thinkTimeInSeconds * 1000);
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
            Thread.sleep(thinkTimeInMilliseconds);
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
            if (!rc.alive()) {
                registry.remoteControlPool().unregister(rc);
            }
        }
    }

}
