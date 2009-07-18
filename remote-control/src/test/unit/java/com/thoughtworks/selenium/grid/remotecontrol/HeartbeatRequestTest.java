package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.Response;

public class HeartbeatRequestTest extends UsingClassMock {

    @Test
    public void heartbeatURLReturnsTheHeartbeatURLDerivedFromTheHubURLProvidedInConstructor() {
        final RegistrationInfo info;

        info = new RegistrationInfo("http://thehub.url:4444", "", "", "");
        assertEquals("http://thehub.url:4444/console",
                     new  HeartbeatRequest(info).heartBeatURL());
    }

    @Test
    public void executeReturnsTrueWhenTheHearbeatURLCanBeSuccessfulyReached() {
        final RegistrationInfo info;
        final HeartbeatRequest request;
        final Response successfulResponse;
        final Mock httpClient;

        httpClient = mock(HttpClient.class);
        successfulResponse = new Response(200, "");
        info = new RegistrationInfo("http://thehub.url:4444", "", "", "");
        request = new HeartbeatRequest(info) {
            @Override
            protected HttpClient httpClient() {
                return (HttpClient) httpClient;
            }
        };
        httpClient.expects("get").with(eq("http://thehub.url:4444/console"))
                  .will(returnValue(successfulResponse));
        assertTrue(request.execute());
        verifyMocks();
    }

    @Test
    public void executeReturnsFalseWhenTheHearbeatURLReturnsA500() {
        final RegistrationInfo info;
        final HeartbeatRequest request;
        final Response errorResponse;
        final Mock httpClient;

        httpClient = mock(HttpClient.class);
        errorResponse = new Response(500, "");
        info = new RegistrationInfo("http://thehub.url:4444", "", "", "");
        request = new HeartbeatRequest(info) {
            @Override
            protected HttpClient httpClient() {
                return (HttpClient) httpClient;
            }
        };
        httpClient.expects("get").with(eq("http://thehub.url:4444/console"))
                  .will(returnValue(errorResponse));
        assertFalse(request.execute());
        verifyMocks();
    }

    @Test
    public void executeReturnsFalseWhenAccessingHearbeatURLRaises() {
        final RegistrationInfo info;
        final HeartbeatRequest request;
        final Mock httpClient;

        httpClient = mock(HttpClient.class);
        info = new RegistrationInfo("http://thehub.url:4444", "", "", "");
        request = new HeartbeatRequest(info) {
            @Override
            protected HttpClient httpClient() {
                return (HttpClient) httpClient;
            }
        };
        httpClient.expects("get").with(eq("http://thehub.url:4444/console"))
                  .will(throwException(new RuntimeException("simulate an exception")));
        assertFalse(request.execute());
        verifyMocks();
    }


}