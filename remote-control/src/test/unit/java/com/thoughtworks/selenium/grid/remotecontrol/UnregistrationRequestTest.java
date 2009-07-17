package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;

public class UnregistrationRequestTest extends UsingClassMock {

    @Test
    public void postToHubUsingURLProvidedInConstructor() throws IOException {
        final RegistrationInfo info;

        info = new RegistrationInfo("http://thehub.url:4444", "", "", "");
        assertEquals("http://thehub.url:4444/registration-manager/unregister",
                     new UnregistrationRequest(info).postMethod().getURI().toString());
    }

}