package com.thoughtworks.selenium.grid.remotecontrol.instrumentation;

/**
 * Process Http Requests
 */
public interface HttpRequestProcessor {

    Response process(Request request);
    
}
