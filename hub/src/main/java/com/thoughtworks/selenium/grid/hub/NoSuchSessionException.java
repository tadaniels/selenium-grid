package com.thoughtworks.selenium.grid.hub;

/**
 * Signals attempt to retrieve a browser session that is not registered with the hub.
 */
public class NoSuchSessionException extends RuntimeException {

    private final String sessionId;

    public NoSuchSessionException(String sessionId) {
        super(String.format("There is no registered session with ID '%s'.  Either it timed out or you already closed it.", sessionId));

        this.sessionId = sessionId;
    }

    public String sessionId() {
        return sessionId;
    }

}