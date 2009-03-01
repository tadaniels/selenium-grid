require "rubygems"
require "dust"
require "mocha"
require "logger"

#
# Avoid requiring config/environment to save time when running unit tests
# from editor
#
require File.expand_path(File.dirname(__FILE__) + '/../../config/application_dependencies')
require File.expand_path(File.dirname(__FILE__) + '/../../app/models/grid/logger')
require File.expand_path(File.dirname(__FILE__) + '/../../app/models/grid/hub/remote_command')
Dir[File.dirname(__FILE__) + '/../../app/models/**/*.rb'].each do |filename|
  require File.expand_path(filename)
end

RAILS_DEFAULT_LOGGER = Logger.new(STDOUT)

Test::Unit::Assertions.module_eval do
  
  def assert_false(expression)
    assert_equal false, expression
  end

  def assert_true(expression)
    assert_equal true, expression
  end
    
end