module Grid  
  module Hub
    
    class RemoteControlPool
      
      def initialize
        @rc_by_uid = Hash.new
        @rc_by_session_id = Hash.new
        @mutex = Mutex.new
        @available_remote_control = ConditionVariable.new
      end
      
      def add(new_rc_proxy)
        @mutex.synchronize do        
          @rc_by_uid[new_rc_proxy.uid] = new_rc_proxy
        end
      end

      def remove(remote_control_proxy)
        @mutex.synchronize do
          @rc_by_uid.delete remote_control_proxy.uid
        end
      end
      
      def remote_controls
        @rc_by_uid.values
      end
      
      def find(uid)
        @rc_by_uid[uid]
      end

      def reserve(environment)
        @mutex.synchronize do
          rc = loop do
            rc = find_free_rc_for environment
            break rc unless rc.nil?
            
            @available_remote_control.wait(@mutex)
          end

          rc.reserve!
          return rc
        end
      end
      
      def release(remote_control_proxy)
        @mutex.synchronize do
          session_id = remote_control_proxy.session_id
          remote_control_proxy.release!
          clear_session_id session_id
          @available_remote_control.broadcast
        end
      end

      def find_by_session_id(session_id)
        @rc_by_session_id[session_id]
      end
      
      def associate_with_session_id(rc, session_id)
        rc.session_id = session_id
        @rc_by_session_id[session_id] = rc
      end

      def clear_session_id(session_id)
        @rc_by_session_id[session_id] = nil if session_id
      end

      def find_free_rc_for(environment)
        remote_controls.find do |rc|
          rc.free? && rc.environments.include?(environment)
        end
      end

    end

  end
  
end
