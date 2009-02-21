module Grid
  module Hub
    
    class NewBrowserSessionCommand < RemoteCommand

      def environment
        first_argument
      end

      def execute(remote_control_proxy)
        rc = Registry.remote_control_pool.reserve environment
        begin
          response = forward_to_remote_control(rc)
          session_id_match = response.scan(/OK,([^,]+)/)
          if (session_id_match.empty?) 
            Registry.remote_control_pool.release rc
            RAILS_DEFAULT_LOGGER.error "Could not retrieve a new session for environment '#{environment}' : #{response}"
            return "ERROR: Could not retrieve a new session"
          end

          session_id = session_id_match[0][0]
          Registry.remote_control_pool.associate_with_session_id rc, session_id
        # rescue Exception => e
        #   RAILS_DEFAULT_LOGGER.error "Problem while requesting new browser session", e
        #   Registry.remote_control_pool.release rc
        end

        response        
      end
      
    end
    
  end
end
