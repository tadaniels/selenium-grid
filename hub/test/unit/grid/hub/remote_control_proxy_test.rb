require File.dirname(__FILE__) + '/../../test_helper'

unit_tests do
  
  test "host returns the host provided in the constructor" do
    proxy = Grid::Hub::RemoteControlProxy.new(:host => "the host")
    assert_equal "the host", proxy.host
  end

  test "ports returns the port provided in the constructor" do
    proxy = Grid::Hub::RemoteControlProxy.new(:port => 4000)
    assert_equal 4000, proxy.port
  end

  test "environments is empty by default" do
    proxy = Grid::Hub::RemoteControlProxy.new({})
    assert_equal [], proxy.environments
  end

  test "environments returns the environment provided in the constructor" do
    proxy = Grid::Hub::RemoteControlProxy.new(:environments => ["*safari"])
    assert_equal ["*safari"], proxy.environments
  end
  
  test "uri returns the selenium RC driver uri" do
    proxy = Grid::Hub::RemoteControlProxy.new(:host => "the.host", :port => 1234)
    assert_equal "http://the.host:1234/selenium-server/driver/",
                 proxy.uri.to_s
  end

  test "uri parse the URI only once" do
    the_uri = URI.parse "http://the.host:2424/selenium-server/driver/"
    proxy = Grid::Hub::RemoteControlProxy.new(:host => "the.host", :port => 1234)
    
    URI.expects(:parse).once.returns(the_uri)
    proxy.uri.to_s
    assert_equal "http://the.host:2424/selenium-server/driver/",
                 proxy.uri.to_s
  end
  
  test "forward forward the POST request to the remote control" do
    proxy = Grid::Hub::RemoteControlProxy.new(:host => "the.host", :port => 1234)
    command = Grid::Hub::RemoteCommand.new(:a_param => :a_value)
    proxy.stubs(:uri).returns(:the_uri)
    
    Net::HTTP.expects(:post_form).with(:the_uri, {:a_param, :a_value}).
              returns(:the_http_response)
    assert_equal :the_http_response, proxy.forward(command)    
  end
  
  test "uid is a SHA of the concatenated host and port" do
    proxy = Grid::Hub::RemoteControlProxy.new(:host => "the.host.com", :port => 1234)
    expected = Digest::SHA1.hexdigest "the.host.com:1234"
    assert_equal expected, proxy.uid
  end

  test "2 remote control proxy with the same host and port are equals" do
    assert_equal Grid::Hub::RemoteControlProxy.new(:host => "same host", :port => 123),
                 Grid::Hub::RemoteControlProxy.new(:host => "same host", :port => 123)
  end

  test "2 remote control proxy with the same host but a different port are not equals" do
    assert_not_equal Grid::Hub::RemoteControlProxy.new(:host => "same host", :port => 123),
                     Grid::Hub::RemoteControlProxy.new(:host => "same host", :port => 456)
  end

  test "2 remote control proxy with the same port but a different host are not equals" do
    assert_not_equal Grid::Hub::RemoteControlProxy.new(:host => "a host", :port => 123),
                     Grid::Hub::RemoteControlProxy.new(:host => "another host", :port => 123)
  end

  test "2 remote control proxy with the diffrent hosts and ports are not equals" do
    assert_not_equal Grid::Hub::RemoteControlProxy.new(:host => "a host", :port => 123),
                     Grid::Hub::RemoteControlProxy.new(:host => "another host", :port => 456)
  end
  
  test "free? return true by default" do
    assert_equal true, Grid::Hub::RemoteControlProxy.new({}).free?
  end

  test "free? return false when reserved!" do
    rc = Grid::Hub::RemoteControlProxy.new({})
    rc.reserve!
    assert_equal false, rc.free?
  end

  test "free? return true when reserved! and released!" do
    rc = Grid::Hub::RemoteControlProxy.new({})
    rc.reserve!
    rc.release!
    assert_equal true, rc.free?
  end
  
  test "session_id is nil by default" do
    assert_nil Grid::Hub::RemoteControlProxy.new({}).session_id
  end

  test "session_id can be set" do
    rc = Grid::Hub::RemoteControlProxy.new({})
    rc.session_id = "a session id"
    assert_equal 'a session id', rc.session_id
  end
  
end