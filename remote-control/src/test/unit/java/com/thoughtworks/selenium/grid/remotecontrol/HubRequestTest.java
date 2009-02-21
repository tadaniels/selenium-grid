package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * {@link HubRequest} unit test class.
 */
public class HubRequestTest extends UsingClassMock {

    @Test
    public void seleniumHubURLURLIsTheOneProvidedInConstructor() throws IOException {
      final HubRequest request = new HubRequest("http://hub.url:4444/", "", "") {
          public PostMethod postMethod() {
              throw new UnsupportedOperationException("should not be called");
          }
      };
        assertEquals("http://hub.url:4444/", request.seleniumHubURL());
    }

    @Test
    public void hostIsTheOneProvidedInConstructor() throws IOException {
      final HubRequest request = new HubRequest("http://hub.url:4444/", "the.host.com", "") {
          public PostMethod postMethod() {
              throw new UnsupportedOperationException("should not be called");
          }
      };
        assertEquals("the.host.com", request.host());
    }

    @Test
    public void portIsTheOneProvidedInConstructor() throws IOException {
      final HubRequest request = new HubRequest("http://hub.url:4444/", "the.host.com", "234") {
          public PostMethod postMethod() {
              throw new UnsupportedOperationException("should not be called");
          }
      };
        assertEquals("234", request.port());
    }

}
