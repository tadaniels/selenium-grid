module BookExample
  
  def as_described_on_amazon(options)
    page.open "/"
    page.select "url", "Books"
    page.type "twotabsearchtextbox", options[:keywords]
    page.click "navGoButtonPanel", :wait_for => :page
    page.text_present?(options[:title]).should be_true
    page.click "link=#{options[:anchor]}", :wait_for => :page
    page.field("name=quantity").should == "1"
    page.text_present?("ISBN-10: #{options[:isbn]}").should be_true
    page.click "link=Explore similar items", :wait_for => :page
    page.go_back
    page.wait_for_page_to_load 60000
    page.field("quantity").should == "1"
    page.select "quantity", "label=5"
    page.click "submit.add-to-cart", :wait_for => :page
    page.text_present?("Added to your\nShopping Cart:").should be_true
    page.text_present?(options[:title]).should be_true
    page.text_present?("quantity: 5").should be_true
  end
  
end