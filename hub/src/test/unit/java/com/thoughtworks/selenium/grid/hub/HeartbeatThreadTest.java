package com.thoughtworks.selenium.grid.hub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.SocketUtils;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DummyWebServer;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;

public class HeartbeatThreadTest extends UsingClassMock {
	@Test(timeout = 10000)
	public void heartbeatThreadLoopsThroughAllRCsPingingTheirHttpClients() throws Exception {
		DummyWebServer rcServer1 = null;
		DummyWebServer rcServer2 = null;
		DummyWebServer rcServer3 = null;

		try {
			int port1 = SocketUtils.getFreePort();
			rcServer1 = new DummyWebServer(port1);
			rcServer1.start();

			int port2 = SocketUtils.getFreePort();
			rcServer2 = new DummyWebServer(port2);
			rcServer2.start();

			int port3 = SocketUtils.getFreePort();
			rcServer3 = new DummyWebServer(port3);
			rcServer3.start();

			MockHttpClient httpClient1 = new MockHttpClient();
			RemoteControlProxy rc1 = new RemoteControlProxy("localhost", port1, "environment", 2,
					httpClient1);

			MockHttpClient httpClient2 = new MockHttpClient();
			RemoteControlProxy rc2 = new RemoteControlProxy("localhost", port2, "environment", 2,
					httpClient2);

			MockHttpClient httpClient3 = new MockHttpClient();
			RemoteControlProxy rc3 = new RemoteControlProxy("localhost", port3, "environment", 2,
					httpClient3);
			rc3.registerNewSession();

			Mock registry = mock(HubRegistry.class);
			DynamicRemoteControlPoolStub remoteControlPool = new DynamicRemoteControlPoolStub();
			registry.stubs("remoteControlPool").will(returnValue(remoteControlPool));

			remoteControlPool.availableRCs.add(rc1);
			remoteControlPool.availableRCs.add(rc2);
			remoteControlPool.activeRCs.add(rc3);
			HeartbeatThread heartbeatThread = new HeartbeatThread(10000, (HubRegistry) registry);

			assertEquals(0, httpClient1.pingCallCount);
			assertEquals(0, httpClient2.pingCallCount);
			assertEquals(0, httpClient3.pingCallCount);

			heartbeatThread.start();
			Thread.sleep(200);

			assertEquals(1, httpClient1.pingCallCount);
			assertEquals(1, httpClient2.pingCallCount);
			assertEquals(1, httpClient3.pingCallCount);

			heartbeatThread.interrupt();
			heartbeatThread.join();
		} finally {
			rcServer1.stop();
			rcServer2.stop();
			rcServer3.stop();
		}
	}
	
	@Test(timeout = 10000)
	public void whenRCsPingingBlowsUpUnregisterTheRcWithTheHub() throws Exception {
		DummyWebServer rcServer1 = null;

		try {
			int port1 = SocketUtils.getFreePort();
			rcServer1 = new DummyWebServer(port1);
			rcServer1.shouldGive500 = true;
			rcServer1.start();


			MockHttpClient httpClient1 = new MockHttpClient();
			RemoteControlProxy rc1 = new RemoteControlProxy("localhost", port1, "environment", 2,
					httpClient1);

			Mock registry = mock(HubRegistry.class);
			DynamicRemoteControlPoolStub remoteControlPool = new DynamicRemoteControlPoolStub();
			registry.stubs("remoteControlPool").will(returnValue(remoteControlPool));

			remoteControlPool.availableRCs.add(rc1);
			HeartbeatThread heartbeatThread = new HeartbeatThread(10000, (HubRegistry) registry);

			assertNull(remoteControlPool.unregisteredRC);
			
			heartbeatThread.start();
			Thread.sleep(200);
			
			heartbeatThread.interrupt();
			heartbeatThread.join();

			assertEquals(rc1, remoteControlPool.unregisteredRC);
		} finally {
			rcServer1.stop();
		}
	}

	@Test(timeout = 10000)
	public void heartbeatThreadSleepsForASpecifiedWhileInBetweenLooping() throws Exception {
		Mock registry = mock(HubRegistry.class);
		DynamicRemoteControlPoolStub remoteControlPool = new DynamicRemoteControlPoolStub();
		registry.stubs("remoteControlPool").will(returnValue(remoteControlPool));

		assertEquals(0, remoteControlPool.availableRemoteControlsCallCount);

		HeartbeatThread heartbeatThread = new HeartbeatThread(100, (HubRegistry) registry);
		heartbeatThread.start();

		Thread.sleep(1000);

		assertEquals(10, remoteControlPool.availableRemoteControlsCallCount, 1);

		heartbeatThread.interrupt();
		heartbeatThread.join();
	}

	private static class MockHttpClient extends HttpClient {
		private int pingCallCount;

		@Override
		public Response get(String url) throws IOException {
			pingCallCount++;
			return super.get(url);
		}
	}

	private static class DynamicRemoteControlPoolStub implements DynamicRemoteControlPool {
		private List<RemoteControlProxy> activeRCs = new ArrayList<RemoteControlProxy>();
		private List<RemoteControlProxy> availableRCs = new ArrayList<RemoteControlProxy>();
		
		private int availableRemoteControlsCallCount;
		private int activeRemoteControlProxyCallCount;

		private RemoteControlProxy unregisteredRC;
		
		@Override
		public List<RemoteControlProxy> availableRemoteControls() {
			availableRemoteControlsCallCount++;
			return availableRCs;
		}

		@Override
		public void register(RemoteControlProxy newRemoteControl) {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<RemoteControlProxy> reservedRemoteControls() {
			activeRemoteControlProxyCallCount++;
			return activeRCs;
		}

		@Override
		public boolean unregister(RemoteControlProxy remoteControl) {
			this.unregisteredRC = remoteControl;
			return true;
		}

		@Override
		public void associateWithSession(RemoteControlProxy remoteControl, String sessionId) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void release(RemoteControlProxy remoteControl) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void releaseForSession(String sessionId) {
			throw new UnsupportedOperationException();
		}

		@Override
		public RemoteControlProxy reserve(Environment environment) {
			throw new UnsupportedOperationException();
		}

		@Override
		public RemoteControlProxy retrieve(String sessionId) {
			throw new UnsupportedOperationException();
		}
	}
}
