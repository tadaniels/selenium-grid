module Grid
  module Hub
    
    class CloseBrowserSessionCommand < RemoteCommand
      
      def execute(remote_control_proxy)
        response = forward_to_remote_control(remote_control_proxy)
        rc = Registry.remote_control_pool.find_by_session_id session_id
        Registry.remote_control_pool.release rc
        response
      end

    end
    
  end
end
