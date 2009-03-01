module Grid  
  module Hub
    
    class RemoteControlPool
      include Grid::Logger
      
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
        logger.info "[reserve][#{Thread.current.object_id}] Reserving a new RC for #{environment}"
        @mutex.synchronize do
          logger.info "[reserve][#{Thread.current.object_id}] Acquired mutex"
          rc = loop do            
            rc = find_free_rc_for environment
            logger.info "[reserve][#{Thread.current.object_id}] Found RC #{rc.inspect}"
            break rc unless rc.nil?
            
            logger.info "[reserve][#{Thread.current.object_id}] Going to sleep"
            @available_remote_control.wait(@mutex)
            logger.info "[reserve][#{Thread.current.object_id}] Woke up!"
          end

          rc.reserve!
          logger.info "[reserve][#{Thread.current.object_id}] Reserved RC #{rc.inspect} for #{environment}"
          return rc
        end
      end
      
      def release(rc)
        logger.info "[release][#{Thread.current.object_id}] Releasing RC #{rc.inspect}"
        @mutex.synchronize do
          logger.info "[release][#{Thread.current.object_id}] Acquired lock"
          session_id = rc.session_id
          logger.info "[release][#{Thread.current.object_id}] Release"
          rc.release!
          logger.info "[release][#{Thread.current.object_id}] Released"
          clear_session_id session_id

          logger.info "[release][#{Thread.current.object_id}] broadcasting"
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
