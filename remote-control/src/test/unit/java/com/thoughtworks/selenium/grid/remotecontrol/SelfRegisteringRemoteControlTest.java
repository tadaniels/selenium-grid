package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

public class SelfRegisteringRemoteControlTest extends UsingClassMock {

    @Test
    public void registrationInfoIsTheOneProvidedInTheConstructor() {
        final RegistrationInfo info;

        info = new RegistrationInfo("The URL", "", "", "");
        assertEquals(info, new SelfRegisteringRemoteControl(info).registrationInfo());
    }

    @Test
    public void registrationInfoCanBProvidedWithAConvenienceConstructor() {
        final SelfRegisteringRemoteControl rc;

        rc = new SelfRegisteringRemoteControl("The URL", "The Environment", "The Host", "The Port");
        assertEquals("The URL", rc.registrationInfo().hubURL());
        assertEquals("The Environment", rc.registrationInfo().environment());
        assertEquals("The Host", rc.registrationInfo().host());
        assertEquals("The Port", rc.registrationInfo().port());
    }

}