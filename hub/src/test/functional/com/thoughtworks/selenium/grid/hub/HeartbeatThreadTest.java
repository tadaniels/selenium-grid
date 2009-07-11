package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.SocketUtils;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DummyWebServer;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.hub.remotecontrol.HealthyRemoteControl;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPoller;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProvisioner;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeartbeatThreadTest extends UsingClassMock {

	@Test(timeout = 10000)
	public void heartbeatThreadLoopsThroughAllRCsPingingTheirHttpClients() throws Exception {
        final DynamicRemoteControlPoolStub remoteControlPool;
        final MockHttpClient httpClient1;
        final MockHttpClient httpClient2;
        final MockHttpClient httpClient3;
		DummyWebServer rcServer1 = null;
		DummyWebServer rcServer2 = null;
		DummyWebServer rcServer3 = null;
        final Thread pollerThread;
        final RemoteControlProxy rc1;
        final RemoteControlProxy rc2;
        final RemoteControlProxy rc3;
        final Mock registry;
        final int port1;
        final int port2;
        final int port3;

		try {
            port1 = SocketUtils.getFreePort();
            rcServer1 = new DummyWebServer(port1);
			rcServer1.start();

            port2 = SocketUtils.getFreePort();
            rcServer2 = new DummyWebServer(port2);
			rcServer2.start();

            port3 = SocketUtils.getFreePort();
            rcServer3 = new DummyWebServer(port3);
			rcServer3.start();

            httpClient1 = new MockHttpClient();
            rc1 = new HealthyRemoteControl("localhost", port1, "environment", httpClient1);

            httpClient2 = new MockHttpClient();
            rc2 = new HealthyRemoteControl("localhost", port2, "environment", httpClient2);

            httpClient3 = new MockHttpClient();
            rc3 = new HealthyRemoteControl("localhost", port3, "environment", httpClient3);
            rc3.registerNewSession();

            registry = mock(HubRegistry.class);
            remoteControlPool = new DynamicRemoteControlPoolStub();
            registry.stubs("remoteControlPool").will(returnValue(remoteControlPool));

			remoteControlPool.availableRCs.add(rc1);
			remoteControlPool.availableRCs.add(rc2);
			remoteControlPool.activeRCs.add(rc3);
            pollerThread = new Thread(new RemoteControlPoller(10, (HubRegistry) registry));
            assertEquals(0, httpClient1.pingCallCount);
			assertEquals(0, httpClient2.pingCallCount);
			assertEquals(0, httpClient3.pingCallCount);

			pollerThread.start();
			Thread.sleep(200);

			assertEquals(1, httpClient1.pingCallCount);
			assertEquals(1, httpClient2.pingCallCount);
			assertEquals(1, httpClient3.pingCallCount);

			pollerThread.interrupt();
			pollerThread.join();
		} finally {
			if (null != rcServer1) { rcServer1.stop(); }
			if (null != rcServer2) { rcServer2.stop(); }
			if (null != rcServer3) { rcServer3.stop(); }
		}
	}
	
	@Test(timeout = 10000)
	public void whenRCsPingingBlowsUpUnregisterTheRcWithTheHub() throws Exception {
		DummyWebServer rcServer = null;
        final Mock registry;
        final DynamicRemoteControlPoolStub pool;
        final MockHttpClient httpClient;
        final Thread pollerThread;
        final int port;

		try {
            port = SocketUtils.getFreePort();
            rcServer = new DummyWebServer(port);
			rcServer.shouldGive500 = true;
			rcServer.start();


            httpClient = new MockHttpClient();
            RemoteControlProxy rc1 = new RemoteControlProxy("localhost", port, "environment", httpClient);

            registry = mock(HubRegistry.class);
            pool = new DynamicRemoteControlPoolStub();
            registry.stubs("remoteControlPool").will(returnValue(pool));

			pool.availableRCs.add(rc1);
            pollerThread = new Thread(new RemoteControlPoller(10, (HubRegistry) registry));

            assertNull(pool.unregisteredRC);
			
			pollerThread.start();
			Thread.sleep(200);
			
			pollerThread.interrupt();
			pollerThread.join();

			assertEquals(rc1, pool.unregisteredRC);
		} finally {
			if (null != rcServer) { rcServer.stop(); }
		}
	}

	@Test(timeout = 10000)
	public void heartbeatThreadSleepsForASpecifiedWhileInBetweenLooping() throws Exception {
        DynamicRemoteControlPoolStub pool;
        final Thread pollerThread;
		Mock registry;

        registry = mock(HubRegistry.class);
        pool = new DynamicRemoteControlPoolStub();
        registry.stubs("remoteControlPool").will(returnValue(pool));
		assertEquals(0, pool.availableRemoteControlsCallCount);

        pollerThread = new Thread(new RemoteControlPoller(0.1, (HubRegistry) registry));
        pollerThread.start();

		Thread.sleep(1000);
		assertEquals(10, pool.availableRemoteControlsCallCount, 1);

		pollerThread.interrupt();
		pollerThread.join();
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

		private RemoteControlProxy unregisteredRC;
		
		public List<RemoteControlProxy> availableRemoteControls() {
			availableRemoteControlsCallCount++;
			return availableRCs;
		}

		public void register(RemoteControlProxy newRemoteControl) {
			throw new UnsupportedOperationException();
		}

		public List<RemoteControlProxy> reservedRemoteControls() {
			return activeRCs;
		}

		public boolean unregister(RemoteControlProxy remoteControl) {
			this.unregisteredRC = remoteControl;
			return true;
		}

		public void associateWithSession(RemoteControlProxy remoteControl, String sessionId) {
			throw new UnsupportedOperationException();
		}

		public void release(RemoteControlProxy remoteControl) {
			throw new UnsupportedOperationException();
		}

		public void releaseForSession(String sessionId) {
			throw new UnsupportedOperationException();
		}

		public RemoteControlProxy reserve(Environment environment) {
			throw new UnsupportedOperationException();
		}

		public RemoteControlProxy retrieve(String sessionId) {
			throw new UnsupportedOperationException();
		}

        public RemoteControlProvisioner getProvisioner(String environment) {
            throw new UnsupportedOperationException();
        }

        public List<RemoteControlProxy> allRegisteredRemoteControls() {
            throw new UnsupportedOperationException();
        }

        public void unregisterAllUnresponsiveRemoteControls() {
            throw new UnsupportedOperationException();
        }
    }
}
