package com.thoughtworks.selenium.grid.demo;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Base class for Amazon Web Acceptance tests
 */
public abstract class FlickrTestBase {

    public static final String TIMEOUT = "120000";

    protected void runFlickrScenario(String searchString) throws Exception {
        session().open("/");
        assertTrue(session().getLocation(), session().getLocation().startsWith("http://flickr.com"));
        session().type("q", searchString);
        session().click("//form[@action='/search/']//input[@type='submit']");
        session().waitForPageToLoad(TIMEOUT);
        session().click("link=Advanced Search");
        session().waitForPageToLoad(TIMEOUT);
        session().click("media_photos");
        session().click("//input[@value='SEARCH']");
        session().waitForPageToLoad(TIMEOUT);
        assertTrue(session().isTextPresent(searchString.split(" ")[0]));
        session().click("//img[@class='pc_img']");
        session().waitForPageToLoad(TIMEOUT);                           
        if (session().isTextPresent("photo_gne_button_zoom")) {
          session().click("photo_gne_button_zoom");
        }
        session().goBack();
        session().waitForPageToLoad(TIMEOUT);
        session().click("//img[@class='pc_img']");
        session().waitForPageToLoad(TIMEOUT);
        Thread.sleep(20000);
        session().click("link=*photostream");
        session().waitForPageToLoad(TIMEOUT);
    }

}
