package com.thoughtworks.selenium.grid.hub;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.SocketUtils;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DummyWebServer;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;

public class HeartbeatThreadTest {
	@Test(timeout = 10000)
	public void heartbeatThreadLoopsThroughAllRCsPingingTheirHttpClients() throws Exception {
		int port1 = SocketUtils.getFreePort();
		DummyWebServer rcServer1 = new DummyWebServer(port1);
		rcServer1.start();
		
		int port2 = SocketUtils.getFreePort();
		DummyWebServer rcServer2 = new DummyWebServer(port2);
		rcServer2.start();
		
		int port3 = SocketUtils.getFreePort();
		DummyWebServer rcServer3 = new DummyWebServer(port3);
		rcServer3.start();

		MockHttpClient httpClient1 = new MockHttpClient();
		RemoteControlProxy rc1 = new RemoteControlProxy("localhost", port1, "environment", 2, httpClient1);

		MockHttpClient httpClient2 = new MockHttpClient();
		RemoteControlProxy rc2 = new RemoteControlProxy("localhost", port2, "environment", 2, httpClient2);

		MockHttpClient httpClient3 = new MockHttpClient();
		RemoteControlProxy rc3 = new RemoteControlProxy("localhost", port3, "environment", 2, httpClient3);

		RemoteControlProviderStub rcProvider = new RemoteControlProviderStub();
		rcProvider.rcs.add(rc1);
		rcProvider.rcs.add(rc2);
		rcProvider.rcs.add(rc3);
		HeartbeatThread heartbeatThread = new HeartbeatThread(10000, rcProvider);

		assertEquals(0, httpClient1.pingCallCount);
		assertEquals(0, httpClient2.pingCallCount);
		assertEquals(0, httpClient3.pingCallCount);

		heartbeatThread.start();
		Thread.sleep(100);

		assertEquals(1, httpClient1.pingCallCount);
		assertEquals(1, httpClient2.pingCallCount);
		assertEquals(1, httpClient3.pingCallCount);

		heartbeatThread.interrupt();
		heartbeatThread.join();
		
		rcServer1.stop();
		rcServer2.stop();
		rcServer3.stop();
	}

	@Test(timeout = 10000)
	public void heartbeatThreadSleepsForASpecifiedWhileInBetweenLooping() throws Exception {
		RemoteControlProviderStub rcProvider = new RemoteControlProviderStub();
		HeartbeatThread heartbeatThread = new HeartbeatThread(10, rcProvider);
		heartbeatThread.start();

		Thread.sleep(100);

		assertEquals(10, rcProvider.availableRemoteControlsCallCount, 1);

		heartbeatThread.interrupt();
		heartbeatThread.join();
	}

	private static class RemoteControlProviderStub implements IRemoteControlProvider {
		private final List<RemoteControlProxy> rcs = new ArrayList<RemoteControlProxy>();
		private int availableRemoteControlsCallCount;

		@Override
		public List<RemoteControlProxy> getAvailableRemoteControls() {
			availableRemoteControlsCallCount++;
			return rcs;
		}
	}

	private static class MockHttpClient extends HttpClient {
		private int pingCallCount;

		@Override
		public Response get(String url) throws IOException {
			pingCallCount++;
			return super.get(url);
		}
	}
}
