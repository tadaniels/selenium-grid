package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

public class SelfRegisteringRemoteControlTest extends UsingClassMock {

    @Test
    public void registrationInfoIsTheOneProvidedInTheConstructor() {
        final RegistrationInfo info;

        info = new RegistrationInfo("The URL", "", "", "");
        assertEquals(info, new SelfRegisteringRemoteControl(info, 0).registrationInfo());
    }

    @Test
    public void hubPollerIntervalInSecondsIsTheOneProvidedInTheConstructor() {
        final SelfRegisteringRemoteControl rc;
        final RegistrationInfo info;

        info = new RegistrationInfo("", "", "", "");
        rc = new SelfRegisteringRemoteControl(info, 24);
        assertEquals(24000, rc.hubPoller().pollingIntervalInMilliseconds());
    }

    @Test
    public void hubPollerRCIsTheCurrentInstance() {
        final SelfRegisteringRemoteControl rc;
        final RegistrationInfo info;

        info = new RegistrationInfo("", "", "", "");
        rc = new SelfRegisteringRemoteControl(info, 0);
        assertEquals(rc, rc.hubPoller().remoteControl());
    }

}