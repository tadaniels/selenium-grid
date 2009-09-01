package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

public class RegistrationInfoTest extends UsingClassMock {

    @Test
    public void hubURLIsTheOneProvidedInTheConstructor() {
        assertEquals("The URL", new RegistrationInfo("The URL", "", "", "").hubURL());
    }

    @Test
    public void environmentIsTheOneProvidedInTheConstructor() {
        assertEquals("The Environment", new RegistrationInfo("", "The Environment", "", "").environment());
    }

    @Test
    public void hostIsTheOneProvidedInTheConstructor() {
        assertEquals("The Host", new RegistrationInfo("", "", "The Host", "").host());
    }

    @Test
    public void portIsTheOneProvidedInTheConstructor() {
        assertEquals("The Port", new RegistrationInfo("", "", "", "The Port").port());
    }

    @Test
    public void toStringReturhsAHumanFriendlyMessage() {
        assertEquals(
            "[RegistrationInfo seleniumHubURL='http://the.hub:4444', env='*firefox', host='the.rc', port='5555']", 
            new RegistrationInfo("http://the.hub:4444", "*firefox", "the.rc", "5555").toString());
    }

}