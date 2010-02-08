package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.remotecontrol.HeartbeatRequest.Status;

public class HeartbeatRequestTest extends UsingClassMock {

    @Test
    public void heartbeatURLReturnsTheHeartbeatURLDerivedFromTheHubURLProvidedInConstructor() {
        final RegistrationInfo info;

        info = new RegistrationInfo("http://thehub.url:4444", "", "aHost", "aPort");
        assertEquals("http://thehub.url:4444/heartbeat?host=aHost&port=aPort",
                     new  HeartbeatRequest(info).heartBeatURL());
    }

    @Test
    public void executeReturnsOKWhenTheHearbeatURLCanBeSuccessfulyReachedAndResponseIsOK() {
        final RegistrationInfo info;
        final HeartbeatRequest request;
        final Response successfulResponse;
        final Mock httpClient;

        httpClient = mock(HttpClient.class);
        successfulResponse = new Response(200, "Hub : OK");
        info = new RegistrationInfo("http://thehub.url:4444", "", "aHost", "aPort");
        request = new HeartbeatRequest(info) {
            @Override
            protected HttpClient httpClient() {
                return (HttpClient) httpClient;
            }
        };
        httpClient.expects("get").with(eq("http://thehub.url:4444/heartbeat?host=aHost&port=aPort"))
                  .will(returnValue(successfulResponse));
        assertEquals(Status.OK, request.execute());
        verifyMocks();
    }

    @Test
    public void executeReturnsUnregisteredWhenTheHearbeatURLCanBeSuccessfulyReachedAndResponseNotOK() {
        final RegistrationInfo info;
        final HeartbeatRequest request;
        final Response successfulResponse;
        final Mock httpClient;

        httpClient = mock(HttpClient.class);
        successfulResponse = new Response(200, "Hub : Not Registered");
        info = new RegistrationInfo("http://thehub.url:4444", "", "aHost", "aPort");
        request = new HeartbeatRequest(info) {
            @Override
            protected HttpClient httpClient() {
                return (HttpClient) httpClient;
            }
        };
        httpClient.expects("get").with(eq("http://thehub.url:4444/heartbeat?host=aHost&port=aPort"))
                  .will(returnValue(successfulResponse));
        assertEquals(Status.UNREGISTERED, request.execute());
        verifyMocks();
    }

    @Test
    public void executeReturnsDownWhenTheHearbeatURLReturnsA500() {
        final RegistrationInfo info;
        final HeartbeatRequest request;
        final Response errorResponse;
        final Mock httpClient;

        httpClient = mock(HttpClient.class);
        errorResponse = new Response(500, "");
        info = new RegistrationInfo("http://thehub.url:4444", "", "aHost", "aPort");
        request = new HeartbeatRequest(info) {
            @Override
            protected HttpClient httpClient() {
                return (HttpClient) httpClient;
            }
        };
        httpClient.expects("get").with(eq("http://thehub.url:4444/heartbeat?host=aHost&port=aPort"))
                  .will(returnValue(errorResponse));
        assertEquals(Status.DOWN, request.execute());
        verifyMocks();
    }

    @Test
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    public void executeReturnsDownWhenAccessingHearbeatURLRaises() {
        final RegistrationInfo info;
        final HeartbeatRequest request;
        final Mock httpClient;

        httpClient = mock(HttpClient.class);
        info = new RegistrationInfo("http://thehub.url:4444", "", "aHost", "aPort");
        request = new HeartbeatRequest(info) {
            @Override
            protected HttpClient httpClient() {
                return (HttpClient) httpClient;
            }
        };
        httpClient.expects("get").with(eq("http://thehub.url:4444/heartbeat?host=aHost&port=aPort"))
                  .will(throwException(new RuntimeException("simulate an exception")));
        assertEquals(Status.DOWN, request.execute());
        verifyMocks();
    }


}