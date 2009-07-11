package com.thoughtworks.selenium.grid.hub.remotecontrol;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.IOException;

public class RemoteControlPollerTest extends UsingClassMock {

    @Test
    public void activeIsTrueJustAfterThePollerHasBeenCreated() {
        assertTrue(new RemoteControlPoller(0, null).active());
    }

    @Test
    public void activeIsFalseOnceStopHasBeenCalled() {
        final RemoteControlPoller poller;

        poller = new RemoteControlPoller(0, null);
        poller.stop();
        assertFalse(poller.active());
    }

    @Test
    public void pollingIntervalInMillisecondsIsDerivedFromTheConstructorValueInSeconds() {
        assertEquals(1000, new RemoteControlPoller(1, null).pollingIntervalInMilliseconds());
    }

    @Test
    public void pollAllRegisteredRemoteControlsCallsUnregisterAllUnresponsiveRemoteControlsOnThePool() throws IOException {
        final RemoteControlPoller poller;
        final Mock pool;

        pool = mock(DynamicRemoteControlPool.class);
        poller = new RemoteControlPoller(0, (DynamicRemoteControlPool) pool);

        pool.expects("unregisterAllUnresponsiveRemoteControls");
        poller.pollAllRegisteredRemoteControls();

        verifyMocks();
    }

}
