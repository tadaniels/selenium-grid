require File.expand_path(File.dirname(__FILE__) + "/spec_helper")

describe "Testing Projects" do
  include GitHubExample
  
  it "Run distributed tests in parallel" do
    show_off_project :name => "deep-test", :search_string => "Deep Test"
  end

  it "Should convince you" do
    show_off_project :name => "rspec", :search_string => "RSpec"
  end

  it "Implements lean an mean BDD" do
    show_off_project :name => "shoulda", :search_string => "shoulda"
  end

  it "Keeps you honest" do
    show_off_project :name => "rcov", :search_string => "RCov"
  end

  it "Makes you browser click" do
    show_off_project :name => "selenium", :search_string => "Selenium"
  end

  it "Runs web acceptance tests in a blink" do
    show_off_project :name => "selenium-grid", :search_string => "Selenium Grid"
  end

  it "Keeps your builds running" do
    show_off_project :name => "cruisecontrolrb", :search_string => "Cruise Control"
  end

end
