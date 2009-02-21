module Grid
  module Hub
    
    class Registry
      
      def self.remote_control_pool
        $remote_control_pool ||= RemoteControlPool.new
      end
      
    end
    
  end
end
