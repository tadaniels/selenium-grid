package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

/**
 * {@link com.thoughtworks.selenium.grid.remotecontrol.HubRequest} Unit test class.
 */
public class SelfRegisteringRemoteControlTest extends UsingClassMock {

    @Test
    public void hubURLIsTheOneProvidedInTheConstructor() {
        assertEquals("The URL", new SelfRegisteringRemoteControl("The URL", "", "", "").hubURL());
    }

    @Test
    public void environmentIsTheOneProvidedInTheConstructor() {
        assertEquals("The Environment", new SelfRegisteringRemoteControl("", "The Environment", "", "").environment());
    }

    @Test
    public void hostIsTheOneProvidedInTheConstructor() {
        assertEquals("The Host", new SelfRegisteringRemoteControl("", "", "The Host", "").host());
    }

    @Test
    public void portIsTheOneProvidedInTheConstructor() {
        assertEquals("The Port", new SelfRegisteringRemoteControl("", "", "", "The Port").port());
    }

}