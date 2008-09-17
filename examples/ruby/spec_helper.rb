$:.unshift
$:.unshift File.expand_path(File.dirname(__FILE__) + "/vendor/selenium-client-1.2/lib")

require 'rubygems'
require 'spec'
require "selenium"
require "selenium/rspec/spec_helper"
require File.expand_path(File.dirname(__FILE__) + "/book_example")
require File.expand_path(File.dirname(__FILE__) + "/lib/selenium_driver_extensions")


Spec::Runner.configure do |config|

  config.before(:each) do
    create_selenium_driver
    start_new_browser_session
  end

  config.after(:each) do
    @selenium_driver.stop
  end

  def start_new_browser_session
    @selenium_driver.start_new_browser_session
    @selenium_driver.set_context "Starting example '#{self.description}'"
  end

  def selenium_driver
    @selenium_driver
  end

  def page
    @selenium_driver
  end

  def create_selenium_driver
    remote_control_server = ENV['SELENIUM_RC_HOST'] || "localhost"
    port = ENV['SELENIUM_RC_PORT'] || 4444
    browser = ENV['SELENIUM_RC_BROWSER'] || "*firefox"
    timeout = ENV['SELENIUM_RC_TIMEOUT'] || 60
    application_host = ENV['SELENIUM_APPLICATION_HOST'] || "www.amazon.com"
    application_port = ENV['SELENIUM_APPLICATION_PORT'] || "80"

    puts "Contacting Selenium RC on #{remote_control_server}:#{port} -> http://#{application_host}:#{application_port}"
    @selenium_driver = Selenium::SeleniumDriver.new(
        remote_control_server, port, browser,
        "http://#{application_host}:#{application_port}", timeout)
    @selenium_driver.extend SeleniumDriverExtensions
  end

end

