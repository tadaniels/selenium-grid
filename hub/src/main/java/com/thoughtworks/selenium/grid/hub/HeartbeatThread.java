package com.thoughtworks.selenium.grid.hub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;

public class HeartbeatThread extends Thread {
	private final long sleepTime;
	private final HubRegistry registry;

	public HeartbeatThread(long sleepTime, HubRegistry registry) {
		super("RC Status Heartbeat");
		this.sleepTime = sleepTime;
		this.registry = registry;
	}

	@Override
	public void run() {
		while (true) {
			List<RemoteControlProxy> availableRemoteControls = registry.remoteControlPool()
					.availableRemoteControls();
			List<RemoteControlProxy> remotesToUnregister = new ArrayList<RemoteControlProxy>();
			for (RemoteControlProxy rc : availableRemoteControls) {
				try {
					rc.ping();
				} catch (IOException e) {
					remotesToUnregister.add(rc);
				}
			}
			
			for (RemoteControlProxy rc : remotesToUnregister) {
				registry.remoteControlPool().unregister(rc);
			}
			
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
