module GitHubExample
  
  def show_off_project(options)
    browser.open "/"
    page.location.should match(%r{http://github.com/})
    page.type "q", options[:search_string]
    page.click "//form[@class='search_repos']//input[@type='image']",
               :wait_for => :page
    page.text_present?(options[:name]).should be_true
    page.click "link=#{options[:name]}", :wait_for => :page
    page.type "q", "fix"
    page.select "choice", "Commit Messages"
    page.click "//input[@value='Go']", :wait_for => :page
    page.text_present?("Search Results").should be_true
    page.field("name=q").should eql("fix")
    page.field("name=choice").should eql("grep")
    page.click "link=Graphs", :wait_for => :page
    page.click "link=Impact", :wait_for => :page
    page.click "link=Punch Card", :wait_for => :page
    sleep 1
  end
  
end