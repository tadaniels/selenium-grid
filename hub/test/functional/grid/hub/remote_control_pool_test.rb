require File.dirname(__FILE__) + '/../../../test_helper'

unit_tests do
  
  test "reserve blocks when no matching remote control is available" do
    rc = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123, :environments => [ "*safari" ]
    pool = Grid::Hub::RemoteControlPool.new
    pool.add rc
    pool.reserve("*safari")
    begin
      Timeout.timeout(2) do
        pool.reserve("*safari")
      end
      flunk "Did not block as expected"
    rescue TimeoutError
      # all good
    end
  end

  test "reserve blocks then returns released remote control" do
    the_rc = Grid::Hub::RemoteControlProxy.new :host => "a host", :port => 123, :environments => [ "*safari" ]
    pool = Grid::Hub::RemoteControlPool.new
    pool.add the_rc
    fast = Thread.new do
      assert_equal the_rc, pool.reserve("*safari")
      sleep 2
      pool.release the_rc
    end
    slow = Thread.new do
      sleep 1
      assert_equal the_rc, pool.reserve("*safari")
    end
    fast.join
    slow.join    
  end

end
