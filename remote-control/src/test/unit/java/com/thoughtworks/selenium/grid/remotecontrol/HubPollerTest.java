package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class HubPollerTest extends UsingClassMock {

    @Test
    public void remoteControlIsTheOneProvidedInTheConstructor() {
        final SelfRegisteringRemoteControl rc;

        rc = new SelfRegisteringRemoteControl(null, 0);
        assertEquals(rc, new HubPoller(rc, 0).remoteControl());
    }

    @Test
    public void pollingIntervalIsTheOneProvidedInTheConstructor() {
        final SelfRegisteringRemoteControl rc;

        rc = new SelfRegisteringRemoteControl(null, 0);
        assertEquals(3000, new HubPoller(rc, 3).pollingIntervalInMilliseconds());
    }

    @Test
    public void activeIsTrueByDefault() {
        assertTrue(new HubPoller(null, 0).active());
    }

    @Test
    public void activeIsFalseAfterCallingStop() {
        final HubPoller poller;

        poller = new HubPoller(null, 0);
        poller.stop();
        assertFalse(poller.active());
    }

}