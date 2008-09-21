package com.thoughtworks.selenium.grid.examples.java;

import org.testng.annotations.Test;


/**
 */
public class PlatformProjectsTest extends GitHubTest {

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Rubinius")
    public void rubinius() throws Throwable {
        showOffProject("Rubinius", "rubinius");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Mongrel")
    public void mongrel() throws Throwable {
        showOffProject("Mongrel", "mongrel");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Rack")
    public void rack() throws Throwable {
        showOffProject("Rack", "rack");
    }

}