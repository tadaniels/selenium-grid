require File.dirname(__FILE__) + '/../../test_helper'

unit_tests do
  
  test "execute releases the RC associated with the session id" do
    command = Grid::Hub::CloseBrowserSessionCommand.new "cmd" => "testComplete", "sessionId" => "a session id"
    rc = Grid::Hub::RemoteControlProxy.new(:environments => ["*safari"])
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    pool.reserve "*safari"
    pool.associate_with_session_id rc, "a session id"
    Grid::Hub::Registry.stubs(:remote_control_pool).returns(pool)

    pool.expects(:release).with(rc)
    command.expects(:forward_to_remote_control).with(rc).returns("the response")    
    assert_equal "the response", command.execute(rc)
  end

end
