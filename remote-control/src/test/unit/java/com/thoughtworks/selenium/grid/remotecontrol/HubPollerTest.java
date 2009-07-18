package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import java.io.IOException;

public class HubPollerTest extends UsingClassMock {

    @Test
    public void remoteControlIsTheOneProvidedInTheConstructor() {
        final SelfRegisteringRemoteControl rc;

        rc = new SelfRegisteringRemoteControl(null);
        assertEquals(rc, new HubPoller(rc).remoteControl());
    }

    @Test
    public void lostConnectionToHubCanBeInjectedInTheConstructor() {
        final SelfRegisteringRemoteControl rc;

        rc = new SelfRegisteringRemoteControl(null);
        assertTrue(new HubPoller(rc, true).lostConnectionToHub());
    }

    @Test
    public void lostConnectionToHubIsFalseByDefault() {
        assertFalse(new HubPoller(null).lostConnectionToHub());
    }

    @Test
    public void lostConnectionToHubIsTrueAfterCallerCheckConnectionToHubForANonResponsiveHub() throws IOException {
        final Mock rc;
        final HubPoller poller;

        rc = mock(SelfRegisteringRemoteControl.class);
        rc.expects("canReachHub").will(returnValue(false));
        poller = new HubPoller((SelfRegisteringRemoteControl) rc);
        poller.checkConnectionToHub();
        assertTrue(poller.lostConnectionToHub());
        verifyMocks();
    }

    @Test
    public void lostConnectionToHubIsFalseAfterAfterCallerCheckConnectionToHubForAResponsiveHub() throws IOException {
        final Mock rc;
        final HubPoller poller;

        rc = mock(SelfRegisteringRemoteControl.class);
        poller = new HubPoller((SelfRegisteringRemoteControl) rc);
        rc.expects("canReachHub").will(returnValue(true));
        poller.checkConnectionToHub();
        assertFalse(poller.lostConnectionToHub());
        verifyMocks();
    }

    @Test
    public void lostConnectionToHubIsTrueAfterReregisteringAPreviouslyNonResponsiveHub() throws IOException {
        final Mock rc;
        final HubPoller poller;

        rc = mock(SelfRegisteringRemoteControl.class);
        poller = new HubPoller((SelfRegisteringRemoteControl) rc, true);
        rc.expects("canReachHub").will(returnValue(true));
        rc.expects("register");
        poller.checkConnectionToHub();
        assertFalse(poller.lostConnectionToHub());
        verifyMocks();
    }

}