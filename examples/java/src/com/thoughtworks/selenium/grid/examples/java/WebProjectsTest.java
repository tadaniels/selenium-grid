package com.thoughtworks.selenium.grid.examples.java;

import org.testng.annotations.Test;


public class WebProjectsTest extends GitHubTest {

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Scriptacilous")
    public void scriptaculous() throws Throwable {
        showOffProject("Scriptaculous", "scriptaculous");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Prototype")
    public void prototype() throws Throwable {
        showOffProject("Prototype", "prototype");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Sproutcore")
    public void sproutcore() throws Throwable {
        showOffProject("Sproutcore", "sproutcore");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Rails")
    public void rails() throws Throwable {
        showOffProject("Ruby on Rails", "rails");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Merb")
    public void merb() throws Throwable {
        showOffProject("Merb", "merb-core");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Webkit")
    public void webkit() throws Throwable {
        showOffProject("webkit", "webkit");
    }

}