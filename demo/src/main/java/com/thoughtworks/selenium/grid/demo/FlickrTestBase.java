package com.thoughtworks.selenium.grid.demo;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Base class for Amazon Web Acceptance tests
 */
public abstract class FlickrTestBase {

    public static final String TIMEOUT = "120000";

    
    protected void runFlickrScenario(String searchString) {
        session().setTimeout(TIMEOUT);
        session().open("/");
        assertTrue(session().getLocation(), session().getLocation().contains("flickr.com"));
        session().type("q", searchString);
        session().click("//form[@action='/search/']//input[@type='submit']");
        session().waitForPageToLoad(TIMEOUT);
        session().click("link=Advanced Search");
        session().waitForPageToLoad(TIMEOUT);
    }

}
