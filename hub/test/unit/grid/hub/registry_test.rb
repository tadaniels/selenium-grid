require File.dirname(__FILE__) + '/../../test_helper'

unit_tests do
  
  test "remote_control_pool returns a valid remote control pool instance" do
    assert_true Grid::Hub::Registry.remote_control_pool.kind_of?(
                Grid::Hub::RemoteControlPool)
  end

  test "remote_control_pool is a singleton" do
    assert_equal Grid::Hub::Registry.remote_control_pool,
                 Grid::Hub::Registry.remote_control_pool
  end
  
end
