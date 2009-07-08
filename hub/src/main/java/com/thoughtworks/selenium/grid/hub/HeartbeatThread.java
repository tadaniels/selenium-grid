package com.thoughtworks.selenium.grid.hub;

import java.io.IOException;
import java.util.List;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;

public class HeartbeatThread extends Thread {
	private final long sleepTime;
	private final IRemoteControlProvider remoteControlProvider;

	public HeartbeatThread(long sleepTime, IRemoteControlProvider remoteControlProvider) {
		super("RC Status Heartbeat");
		this.sleepTime = sleepTime;
		this.remoteControlProvider = remoteControlProvider;
	}

	@Override
	public void run() {
		while (true) {
			List<RemoteControlProxy> availableRemoteControls = remoteControlProvider
					.getAvailableRemoteControls();
			for (RemoteControlProxy rc : availableRemoteControls) {
				try {
					rc.ping();
				} catch (IOException e) {
					// TODO remove the unresponsive rc
					e.printStackTrace();
				}
			}
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
