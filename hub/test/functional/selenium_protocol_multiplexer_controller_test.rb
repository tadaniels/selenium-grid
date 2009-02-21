require File.dirname(__FILE__) + '/../test_helper'

class SeleniumProtocolMultiplexerControllerTest < ActionController::TestCase
  
  test "getNewBrowserSession reserves a remote control" do
    rc = Grid::Hub::RemoteControlProxy.new(:environments => ["*safari"])
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    Grid::Hub::Registry.stubs(:remote_control_pool).returns(pool)
    
    Grid::Hub::NewBrowserSessionCommand.any_instance.expects(:forward_to_remote_control).with(rc).returns("OK,a session id")    
    
    post :index, :cmd => "getNewBrowserSession", :'1' => "*safari"
    assert_response :success
    assert_equal "text/plain", @response.content_type
    assert_equal "OK,a session id", @response.body
    assert_equal false, rc.free?
    assert_equal "a session id", rc.session_id
    assert_equal rc, pool.find_by_session_id("a session id")
  end

  test "relays Remote Control response" do
    rc = Grid::Hub::RemoteControlProxy.new(:environments => ["*safari"])
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    pool.reserve "*safari"
    pool.associate_with_session_id rc, "a session id"
    Grid::Hub::Registry.stubs(:remote_control_pool).returns(pool)
    
    Grid::Hub::RemoteCommand.any_instance.expects(:forward_to_remote_control).with(rc).returns("the response")    
    
    post :index, :cmd => "aCommand", :'sessionId' => "a session id"
    assert_response :success
    assert_equal "text/plain", @response.content_type
    assert_equal "the response", @response.body
  end

  test "testComplete releases a remote control" do
    rc = Grid::Hub::RemoteControlProxy.new(:environments => ["*safari"])
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    pool.reserve "*safari"
    pool.associate_with_session_id rc, "a session id"
    Grid::Hub::Registry.stubs(:remote_control_pool).returns(pool)
    
    Grid::Hub::CloseBrowserSessionCommand.any_instance.expects(:forward_to_remote_control).with(rc).returns("OK")    
    
    post :index, :cmd => "testComplete", :'sessionId' => "a session id"
    assert_response :success
    assert_equal "text/plain", @response.content_type
    assert_equal "OK", @response.body
    assert_equal true, rc.free?
    assert_nil rc.session_id
    assert_nil pool.find_by_session_id("a session id")
  end

end
