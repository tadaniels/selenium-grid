package com.thoughtworks.selenium.grid.hub.remotecontrol;

import java.util.Date;

/**
 * Selenium Session in progress
 */
public class RemoteControlSession {
    private final RemoteControlProxy remoteControl;
    private long lastActiveAt;
    private final String sessionId;

    public RemoteControlSession(String sessionId, RemoteControlProxy remoteControl) {
        this.sessionId = sessionId;
        this.remoteControl = remoteControl;
        updateLastActiveAt();

    }

    public String sessionId() {
        return sessionId;
    }

    public RemoteControlProxy remoteControl() {
        return remoteControl;
    }

    public long lastActiveAt() {
        return lastActiveAt;
    }

    public void updateLastActiveAt() {
        this.lastActiveAt = new Date().getTime();
    }
}
