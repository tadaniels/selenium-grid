require File.dirname(__FILE__) + '/../../test_helper'

unit_tests do
  
  test "parses return a command with operation matching the request parameters for generic requests" do
    command = Grid::Hub::CommandParser.parse("cmd" => "a generic command")
    assert_equal "a generic command", command.operation
  end

  test "parses return a command with a selenium session id matching the request parameters for generic requests" do
    command = Grid::Hub::CommandParser.parse("sessionId" => "1234")
    assert_equal "1234", command.session_id
  end

  test "parses return a new browser session command for getNewBrowserSession command" do
    command = Grid::Hub::CommandParser.parse("cmd" => "getNewBrowserSession")
    assert command.kind_of?(Grid::Hub::NewBrowserSessionCommand)
  end

  test "parses return a close session command for testComplete command" do
    command = Grid::Hub::CommandParser.parse("cmd" => "testComplete")
    assert command.kind_of?(Grid::Hub::CloseBrowserSessionCommand)
  end
  
end
