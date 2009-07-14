package com.thoughtworks.selenium.grid.hub.remotecontrol;

import org.jbehave.classmock.UsingClassMock;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class RemoteControlSessionTest extends UsingClassMock {

    @Test
    public void constructorReturnsTheSessionIdProvidedInTheConstructor() {
        assertEquals("123", new RemoteControlSession("123", null).sessionId());
    }

    @Test
    public void constructorReturnsTheRemoteControlProvidedInTheConstructor() {
        final RemoteControlProxy rc;

        rc = new RemoteControlProxy("host", 24, "env", null);
        assertEquals(rc, new RemoteControlSession("whatever", rc).remoteControl());
    }

}
