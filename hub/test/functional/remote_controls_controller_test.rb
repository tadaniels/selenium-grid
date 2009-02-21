require File.dirname(__FILE__) + '/../test_helper'

class RemoteControlsControllerTest < ActionController::TestCase
  
  test "index displays all registered remote controls" do
    the_pool = Grid::Hub::RemoteControlPool.new
    Grid::Hub::Registry.stubs(:remote_control_pool).returns(the_pool)
    get :index
    assert_response :success
    assert_equal the_pool, assigns(:pool)
    assert_template "index"
  end

  test "create registers a remote control" do
    the_pool = Grid::Hub::RemoteControlPool.new
    Grid::Hub::Registry.stubs(:remote_control_pool).returns(the_pool)
    post :create, :host => "the host", 
                  :port => "1234", 
                  :environments => ["*firefox", "*safari"]

    assert_redirected_to :action => :index
    assert_equal ["the host"], the_pool.remote_controls.collect(&:host)
    assert_equal ["1234"], the_pool.remote_controls.collect(&:port)
    assert_equal [["*firefox", "*safari"]], the_pool.remote_controls.collect(&:environments)
  end

  test "destroy unregisters a remote control" do
    the_pool = Grid::Hub::RemoteControlPool.new
    the_remote_control = Grid::Hub::RemoteControlProxy.new :host => "the host", 
                                                           :port => "1234"
    the_pool.add the_remote_control
    Grid::Hub::Registry.stubs(:remote_control_pool).returns(the_pool)
    
    delete :destroy, :id => the_remote_control.uid
    assert_redirected_to :action => :index
    assert_equal [], the_pool.remote_controls.collect(&:host)
  end

  test "destroy deos not bark when uid does not match any registered a remote control" do
    the_pool = Grid::Hub::RemoteControlPool.new
    the_remote_control = Grid::Hub::RemoteControlProxy.new :host => "the host", 
                                                           :port => "1234"
                                                           
    Grid::Hub::Registry.stubs(:remote_control_pool).returns(the_pool)
    
    delete :destroy, :id => the_remote_control.uid
    assert_redirected_to :action => :index
    assert_equal [], the_pool.remote_controls.collect(&:host)
  end

end
