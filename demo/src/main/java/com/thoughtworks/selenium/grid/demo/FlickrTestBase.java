package com.thoughtworks.selenium.grid.demo;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Base class for Amazon Web Acceptance tests
 */
public abstract class FlickrTestBase {

    public static final String TIMEOUT = "120000";

    protected void runFlickrScenario(String searchString, String project) throws Exception {
        session().open("/");
        assertTrue(session().getLocation(), session().getLocation().startsWith("http://flickr.com"));
        session().type("q", searchString);
        session().click("//form[@action='/search/']//input[@type='submit']");
        session().waitForPageToLoad(TIMEOUT);
//        assertTrue(session().isTextPresent(project));
//        session().click("link=" + project);
//        session().waitForPageToLoad(TIMEOUT);
//        session().type("q", "fix");
//        session().select("choice", "Commit Messages");
//        session().click("//input[@value='Go']");
//        session().waitForPageToLoad(TIMEOUT);
//        assertTrue(session().isTextPresent("Search Results"));
//        assertEquals("fix", session().getValue("name=q"));
//        assertEquals("grep", session().getValue("name=choice"));
//        session().click("link=Graphs");
//        session().waitForPageToLoad(TIMEOUT);
//        session().click("link=Impact");
//        session().waitForPageToLoad(TIMEOUT);
//        session().click("link=Punch Card");
//        session().waitForPageToLoad(TIMEOUT);
//        Thread.sleep(1000);
    }

}
