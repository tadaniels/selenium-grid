package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
			List<RemoteControlProxy> remotesToUnregister = new ArrayList<RemoteControlProxy>();

			checkAvailableRCs(remotesToUnregister);
			checkActiveRCs(remotesToUnregister);

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

	private void checkActiveRCs(List<RemoteControlProxy> remotesToUnregister) {
		List<RemoteControlProxy> remoteControls = registry.remoteControlPool()
				.reservedRemoteControls();
		checkRemoteControls(remotesToUnregister, remoteControls);
	}

	private void checkAvailableRCs(List<RemoteControlProxy> remotesToUnregister) {
		List<RemoteControlProxy> remoteControls = registry.remoteControlPool()
				.availableRemoteControls();
		checkRemoteControls(remotesToUnregister, remoteControls);
	}

	private void checkRemoteControls(List<RemoteControlProxy> remotesToUnregister,
			List<RemoteControlProxy> remoteControls) {
		for (RemoteControlProxy rc : remoteControls) {
			try {
				rc.ping();
			} catch (IOException e) {
				remotesToUnregister.add(rc);
			}
		}
	}
}
