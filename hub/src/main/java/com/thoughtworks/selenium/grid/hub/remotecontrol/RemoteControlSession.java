package com.thoughtworks.selenium.grid.hub.remotecontrol;

/**
 * Selenium Session in progress
 */
public class RemoteControlSession {
    private final String sessionId;
    private final RemoteControlProxy remoteControl;

    public RemoteControlSession(String sessionId, RemoteControlProxy remoteControl) {
        this.sessionId = sessionId;
        this.remoteControl = remoteControl;
    }

    public String sessionId() {
        return sessionId;
    }

    public RemoteControlProxy remoteControl() {
        return remoteControl;
    }
 
}
