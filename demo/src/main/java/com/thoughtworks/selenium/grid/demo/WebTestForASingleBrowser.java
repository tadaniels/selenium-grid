package com.thoughtworks.selenium.grid.demo;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.closeSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.startSeleniumSession;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Traditional Selenium Test checking the quality of Amazon comments ;o).
 * <br/>
 * Each test request a different browser/environment.
 * <br/>
 * <code>testAmazonOnFirefox</code> can run against a plain vanilla
 * Selenium remote control.       
 * <br/>
 * The other tests need to run again a Selenium Hub: They demonstrate
 * the capacity of requesting a specific environment per
 * test/test suite/build. Of course these environments must be defined
 * on the Hub and at least one remote control must register as providing
 * this particular environment.
 */
public class WebTestForASingleBrowser extends FlickrTestBase {

    @Test(groups = {"demo", "firefox", "default"}, description = "Show off Sarlat on Flickr.")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void sarlat(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            runFlickrScenario("Sarlat", "selenium-grid");
        } finally {
            closeSeleniumSession();

        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Show off Lascaux on Flickr.")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void lascaux(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            runFlickrScenario("Lascaux", "deep-test");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Show off Domme on Flickr.")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void domme(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            runFlickrScenario("Domme Perigord", "deep-test");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Show off Montbazillac on Flickr.")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void montbazillac(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            runFlickrScenario("Montbazillac", "deep-test");
        } finally {
            closeSeleniumSession();
        }
    }


    protected void runFlickrScenario(String searchString, String project) throws Exception {
        super.runFlickrScenario(searchString, project);    //To change body of overridden methods use File | Settings | File Templates.
    }
}



