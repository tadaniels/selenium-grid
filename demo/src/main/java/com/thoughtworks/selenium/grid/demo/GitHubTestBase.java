package com.thoughtworks.selenium.grid.demo;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Base class for Amazon Web Acceptance tests
 */
public abstract class GitHubTestBase {

    protected void runGitHubScenario(String searchString, String project) throws Exception {
        session().open("/");
        assertTrue(session().getLocation(), session().getLocation().startsWith("http://github.com"));
        session().type("q", searchString);
        session().click("//form[@class='search_repos']//input[@type='image']");
        session().waitForPageToLoad("60000");
        assertTrue(session().isTextPresent(project));
        session().click("link=" + project);
        session().waitForPageToLoad("60000");
        session().type("q", "fix");
        session().select("choice", "Commit Messages");
        session().click("//input[@value='Go']");
        session().waitForPageToLoad("60000");
        assertTrue(session().isTextPresent("Search Results"));
        assertEquals("fix", session().getValue("name=q"));
        assertEquals("grep", session().getValue("name=choice"));
        session().click("link=Graphs");
        session().waitForPageToLoad("60000");
        session().click("link=Impact");
        session().waitForPageToLoad("60000");
        session().click("link=Punch Card");
        session().waitForPageToLoad("60000");
        Thread.sleep(1000);
    }

}
