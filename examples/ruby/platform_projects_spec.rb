require File.expand_path(File.dirname(__FILE__) + "/spec_helper")

describe "Platform Projects" do
  include GitHubExample
  
  it "Builds a bad-ass Ruby VM" do
    show_off_project :name => "rubinius", :search_string => "Rubinius"
  end

  it "Serves HTTP requests" do
    show_off_project :name => "mongrel", :search_string => "Mongrel"
  end
  
  it "Plugs HTTP wherever" do
    show_off_project :name => "rack", :search_string => "Rack"
  end
  
end
