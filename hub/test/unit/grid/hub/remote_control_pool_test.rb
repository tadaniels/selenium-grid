require File.dirname(__FILE__) + '/../../test_helper'

unit_tests do
  
  test "remote_controls is empty when no remote control has been added" do
    assert_equal [], Grid::Hub::RemoteControlPool.new.remote_controls
  end

  test "find returns the registered remote control matching the uid when there is one" do
    rc = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    assert_equal rc, pool.find(rc.uid)
  end

  test "find returns nil when no registered remote control match the uid" do
    rc = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123
    pool = Grid::Hub::RemoteControlPool.new
    assert_nil pool.find(rc.uid)
  end

  test "add adds a remote control to the list" do
    rc1 = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123
    rc2 = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 456
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc1
    pool.add rc2
    assert_equal [rc1.uid, rc2.uid].sort, 
                 pool.remote_controls.collect {|rc| rc.uid}.sort
  end

  test "adding the same remote control multiple time has no side effect" do
    rc1 = Grid::Hub::RemoteControlProxy.new :host => "same host", :port => 111
    rc2 = Grid::Hub::RemoteControlProxy.new :host => "same host", :port => 111
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc1
    pool.add rc2
    assert_equal rc1, rc2
    assert_equal [rc1], pool.remote_controls
  end

  test "remove delete a remote control from the remote control list" do
    rc1 = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123
    rc2 = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 456
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc1
    pool.add rc2
    pool.remove rc1
    assert_equal [rc2], pool.remote_controls
  end

  test "removing a non existing remote control has no side effect" do
    rc1 = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123
    rc2 = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 456
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc1
    pool.remove rc2
    assert_equal [rc1], pool.remote_controls
  end

  test "reserve returns an available remote control for that environment when there is one" do
    rc = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123, :environments => [ "*safari" ]
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    assert_equal rc, pool.reserve("*safari")
  end

  test "reserve elects remote controls providing mulitple environments" do
    rc = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123, 
                                            :environments => [ "*safari", "*firefox" ]
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    assert_equal rc, pool.reserve("*firefox")
  end
  
  test "reserve returns the next available rc when none are released" do
    rc1 = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123, :environments => [ "*safari" ]
    rc2 = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 345, :environments => [ "*safari" ]
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc1
    pool.add rc2
    first = pool.reserve("*safari")
    second = pool.reserve("*safari")
    assert_not_nil first
    assert_not_nil second
    assert_not_equal first, second
  end

  test "reserve returns a previously reserved remote control once it is released" do
    rc = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123, :environments => [ "*safari" ]
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    assert_equal rc, pool.reserve("*safari")
    pool.release rc
    assert_equal rc, pool.reserve("*safari")
  end

  test "find_by_session_id returns nil when the pool is empty" do
    pool = Grid::Hub::RemoteControlPool.new
    assert_nil pool.find_by_session_id("unknown session id")
  end

  test "find_by_session_id returns nil when there is no associated remote control" do
    rc = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123, :environments => [ "*safari" ]
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    assert_nil pool.find_by_session_id("unknown session id")
  end

  test "find_by_session_id returns a remote control associated with a session id" do
    rc = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123, :environments => [ "*safari" ]
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    pool.associate_with_session_id(rc, "a session id")
    assert_equal rc, pool.find_by_session_id("a session id")
  end

  test "find_by_session_id returns discriminates between a session id" do
    rc1 = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123, :environments => [ "*safari" ]
    rc2 = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 345, :environments => [ "*safari" ]
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc1
    pool.add rc2
    pool.associate_with_session_id(rc1, "a session id")
    pool.associate_with_session_id(rc2, "another session id")
    assert_equal rc1, pool.find_by_session_id("a session id")
    assert_equal rc2, pool.find_by_session_id("another session id")
  end

  test "find_by_session_id returns nil once a remote control associate with a session id has been released" do
    rc = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123, :environments => [ "*safari" ]
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    pool.associate_with_session_id(rc, "a session id")
    pool.release rc
    assert_nil pool.find_by_session_id("a session id")
  end

end
