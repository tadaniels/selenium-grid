package com.thoughtworks.selenium.grid.hub;

import java.util.List;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;

public interface IRemoteControlProvider {
	List<RemoteControlProxy> getAvailableRemoteControls();
}
