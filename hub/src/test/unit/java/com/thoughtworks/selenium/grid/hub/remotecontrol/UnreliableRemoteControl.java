package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.HttpClient;

public class UnreliableRemoteControl extends RemoteControlProxy {

    public UnreliableRemoteControl(String host, int port, String environment, HttpClient httpClient) {
        super(host, port, environment, httpClient);
    }

    @Override
    public boolean unreliable() {
        return true;
    }
}

