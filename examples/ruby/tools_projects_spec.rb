require File.expand_path(File.dirname(__FILE__) + "/spec_helper")

describe "Testing Projects" do
  include GitHubExample
  
  it "Deploys the whole world" do
    show_off_project :name => "capistrano", :search_string => "Capistrano"
  end

  it "Packages your precious code" do
    show_off_project :name => "rubygems", :search_string => "RubyGems"
  end

  it "Makes you forget your painful past" do
    show_off_project :name => "rake", :search_string => "Rake"
  end

  it "Is faster than your file system" do
    show_off_project :name => "git", :search_string => "Git"
  end

  it "Uses git as a versioned database" do
    show_off_project :name => "git-wiki", :search_string => "Git Wiki"
  end

  it "Let's you write in plain text" do
    show_off_project :name => "maruku", :search_string => "Maruku"
  end

end
