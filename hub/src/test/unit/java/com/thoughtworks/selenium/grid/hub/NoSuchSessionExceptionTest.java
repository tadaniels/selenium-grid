package com.thoughtworks.selenium.grid.hub;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;


public class NoSuchSessionExceptionTest {

    @Test
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    public void sessionIdReturnsTheOneProvidedInTheConstructor() {
      assertEquals("124ae235", new NoSuchSessionException("124ae235").sessionId());
    }

    @Test
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    public void messageIsExplicit() {
      assertEquals("com.thoughtworks.selenium.grid.hub.NoSuchSessionException: There is no registered session with ID '124ae235'.  " +
          "Either it timed out or you already closed it.", new NoSuchSessionException("124ae235").toString());
    }

}
