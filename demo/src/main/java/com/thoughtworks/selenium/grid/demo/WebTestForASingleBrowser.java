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
public class WebTestForASingleBrowser extends GitHubTestBase {

    @Test(groups = {"demo", "firefox", "default"}, description = "Show off github features for Selenium Grid project.")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void seleniumGrid(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            runGitHubScenario("Selenium Grid", "selenium-grid");
        } finally {
            closeSeleniumSession();

        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Show off github features for Deep Test project.")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void deeptest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            runGitHubScenario("Deep Test", "deep-test");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Show off github features for Rubinious project..")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void rubinius(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            runGitHubScenario("Rubinius", "rubinius");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Show off github features for Rails project.")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void merb(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            runGitHubScenario("Merb", "merb-core");
        } finally {
            closeSeleniumSession();
        }
    }


    protected void runGitHubScenario(String searchString, String project) throws Exception {
        super.runGitHubScenario(searchString, project);    //To change body of overridden methods use File | Settings | File Templates.
    }
}



