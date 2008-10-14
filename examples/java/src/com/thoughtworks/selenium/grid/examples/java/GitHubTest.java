package com.thoughtworks.selenium.grid.examples.java;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.closeSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.startSeleniumSession;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;


/**
 * Base class for all tests in Selenium Grid Java examples.
 */
public class GitHubTest {

    public static final String TIMEOUT = "120000";

    @BeforeMethod(groups = {"default", "example"}, alwaysRun = true)
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    protected void startSession(String seleniumHost, int seleniumPort, String browser, String webSite) throws Exception {
        startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
        session().setTimeout(TIMEOUT);
    }

    @AfterMethod(groups = {"default", "example"}, alwaysRun = true)
    protected void closeSession() throws Exception {
        closeSeleniumSession();
    }

    protected void showOffProject(String searchString, String projectName) {
        session().open("/");
        assertTrue(session().getLocation(), session().getLocation().startsWith("http://github.com"));
        session().type("q", searchString);
        session().click("//form[@class='search_repos']//input[@type='image']");
        session().waitForPageToLoad(TIMEOUT);
        assertTrue(session().isTextPresent(projectName));
        session().click("link=" + projectName);
        session().waitForPageToLoad(TIMEOUT);
        session().type("q", "fix");
        session().select("choice", "Commit Messages");
        session().click("//input[@value='Go']");
        session().waitForPageToLoad(TIMEOUT);
        assertTrue(session().isTextPresent("Search Results"));
        assertEquals("fix", session().getValue("name=q"));
        assertEquals("grep", session().getValue("name=choice"));
        session().click("link=Graphs");
        session().waitForPageToLoad("60000");
        session().click("link=Impact");
        session().waitForPageToLoad("60000");
        session().click("link=Punch Card");
        session().waitForPageToLoad("60000");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
