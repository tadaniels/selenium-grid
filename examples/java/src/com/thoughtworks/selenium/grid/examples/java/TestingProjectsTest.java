package com.thoughtworks.selenium.grid.examples.java;

import org.testng.annotations.Test;


public class TestingProjectsTest extends GitHubTest {

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Deep Test")
    public void deeptest() throws Throwable {
        showOffProject("Deep Test", "deep-test");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off RSpec")
    public void rspec() throws Throwable {
        showOffProject("RSpec", "rspec");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Shoulda")
    public void shoulda() throws Throwable {
        showOffProject("shoulda", "shoulda");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off RCov")
    public void rcov() throws Throwable {
        showOffProject("RCov", "rcov");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Selenium")
    public void selenium() throws Throwable {
        showOffProject("Selenium", "selenium");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Selenium Grid")
    public void seleniumGrid() throws Throwable {
        showOffProject("Selenium Grid", "selenium-grid");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Cruise Control")
    public void cruiseControlRB() throws Throwable {
        showOffProject("Cruise Control", "cruisecontrolrb");
    }

}