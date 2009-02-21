require File.dirname(__FILE__) + '/../../test_helper'

unit_tests do
  
  test "parameters returns parameters provided in the constructor" do
    command = Grid::Hub::RemoteCommand.new("cmd" => "an operation", "1" => "foo")
    assert_equal({"cmd" => "an operation", "1" => "foo"}, command.parameters)
  end

  test "operation returns the operation given in the constructor" do
    command = Grid::Hub::RemoteCommand.new "cmd" => "an operation"
    assert_equal "an operation", command.operation
  end

  test "session_id is null when not provided in the constructor" do
    command = Grid::Hub::RemoteCommand.new({})
    assert_nil command.session_id
  end

  test "session_id returns the selenium session id given in the constructor" do
    command = Grid::Hub::RemoteCommand.new "sessionId" => "the session id"
    assert_equal "the session id", command.session_id
  end

  test "first_argument is null no parameter '1' is provided in the constructor" do
    command = Grid::Hub::RemoteCommand.new({})
    assert_nil command.first_argument
  end

  test "first_argument return the value of parameter '1' when provided in constructor" do
    command = Grid::Hub::RemoteCommand.new "1" => "an argument"
    assert_equal "an argument", command.first_argument
  end

  test "second_argument is null no parameter '2' is provided in the constructor" do
    command = Grid::Hub::RemoteCommand.new({})
    assert_nil command.second_argument
  end

  test "second_argument return the value of parameter '2' when provided in constructor" do
    command = Grid::Hub::RemoteCommand.new "2" => "an argument"
    assert_equal "an argument", command.second_argument
  end

  test "constructor differentiates between first and second arguments" do
    command = Grid::Hub::RemoteCommand.new "1" => "first", "2" => "second"
    assert_equal "first", command.first_argument
    assert_equal "second", command.second_argument
  end

  test "execute forwards the request to the remote control proxy" do
    proxy = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 1234
    the_command = Grid::Hub::RemoteCommand.new({})
    proxy.expects(:forward).with(the_command).returns(stub_everything)
    response = the_command.execute proxy
  end

  test "execute returns the body of the forwarded command" do
    proxy = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 1234
    the_command = Grid::Hub::RemoteCommand.new({})
    proxy.expects(:forward).with(the_command).returns(stub_everything(:body => "the_body"))
    assert_equal "the_body", the_command.execute(proxy)
  end
  
end
