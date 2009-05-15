module FlickrExample
  
  def run_flickr_scenario(options)
    browser.open "/"
    page.location.should match(%r{http://flickr.com})
    page.type "q", options[:search_string]
    page.click "//form[@action='/search/']//input[@type='submit']", 
               :wait_for => :page
    page.click "link=Advanced Search", :wait_for => :page
    page.click "media_photos"
    page.click "//input[@value='SEARCH']", :wait_for => :page
    page.text?(options[:search_string].split(/ /).first).should be_true
  end
  
end
