require File.expand_path(File.dirname(__FILE__) + "/spec_helper")

describe "Testing Projects" do
  include GitHubExample
  
  it "Powers most web apps" do
    show_off_project :name => "prototype", :search_string => "Prototype"
  end

  it "Has effects" do
    show_off_project :name => "scriptaculous", :search_string => "Scriptaculous"
  end

  it "Gets fancy" do
    show_off_project :name => "sproutcore", :search_string => "Sproutcore"
  end

  it "Revolutionized web development" do
    show_off_project :name => "rails", :search_string => "Ruby on Rails"
  end

  it "Is mean and lean" do
    show_off_project :name => "merb-core", :search_string => "Merb"
  end

  it "Renders damn well" do
    show_off_project :name => "webkit", :search_string => "webkit"
  end

end
