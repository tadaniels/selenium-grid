package com.thoughtworks.selenium.grid.webserver;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class InvalidRouteExceptionTest {

    @Test
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    public void pathReturnsThePathThatWasGivenInTheConstructor() {
        assertEquals("/a/path", new InvalidRouteException("/a/path").path());
    }

    @Test
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    public void toStringReturnsAHumanFriendlyMessageIncludingThePath() {
        assertEquals("[InvalidRouteException path='/a/path']",
                     new InvalidRouteException("/a/path").toString());
    }
}
