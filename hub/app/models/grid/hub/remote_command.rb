module Grid
  module Hub

    class RemoteCommand
      attr_reader :operation, :session_id, :first_argument, :second_argument
      attr_reader :parameters
  
      def initialize(parameters)
        @parameters = parameters
        @operation = parameters["cmd"]
        @session_id = parameters["sessionId"]
        @first_argument = parameters["1"]
        @second_argument = parameters["2"]
      end
  
      def execute(remote_control_proxy)
        forward_to_remote_control remote_control_proxy
      end

      def forward_to_remote_control(remote_control_proxy)
        http_response = remote_control_proxy.forward self
        http_response.body
      end
  
    end
    
  end
end    
