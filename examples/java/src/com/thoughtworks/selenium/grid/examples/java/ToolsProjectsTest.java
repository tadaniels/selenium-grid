package com.thoughtworks.selenium.grid.examples.java;

import org.testng.annotations.Test;


/**
 */
public class ToolsProjectsTest extends GitHubTest {

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Capistrano")
    public void capistrano() throws Throwable {
        showOffProject("Capistrano", "capistrano");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Ruby Gems")
    public void rubygems() throws Throwable {
        showOffProject("RubyGems", "rubygems");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Rake")
    public void rake() throws Throwable {
        showOffProject("Rake", "rake");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off git")
    public void git() throws Throwable {
        showOffProject("Git", "git");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Git Wiki")
    public void gitwiki() throws Throwable {
        showOffProject("Git Wiki", "git-wiki");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Show off Maruku")
    public void maruku() throws Throwable {
        showOffProject("Maruku", "maruku");
    }

}