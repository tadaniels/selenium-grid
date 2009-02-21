require File.dirname(__FILE__) + '/../../test_helper'

unit_tests do
  
  test "environment is the value for the first argument" do
    command = Grid::Hub::CommandParser.parse("cmd" => "getNewBrowserSession", "1" => "*safari")
    assert_equal "*safari", command.environment
  end
  
  test "execute reserve a RC and associate it with a session id" do
    command = Grid::Hub::NewBrowserSessionCommand.new "cmd" => "getNewBrowserSession", "1" => "*safari", "2" => "a path"
    rc = Grid::Hub::RemoteControlProxy.new({})
    pool = Grid::Hub::RemoteControlPool.new
    Grid::Hub::Registry.stubs(:remote_control_pool).returns(pool)
    pool.expects(:reserve).with("*safari").returns(rc)
    pool.expects(:associate_with_session_id).with(rc, "a session id").returns(rc)
    command.expects(:forward_to_remote_control).with(rc).returns("OK,a session id")
    
    assert_equal "OK,a session id", command.execute(:dummy)
  end

end
