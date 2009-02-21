module Grid
  module Hub
    
    class RemoteControlProxy
      attr_reader :host, :port, :environments
      attr_accessor :session_id
      
      def initialize(options)
        @host = options[:host]
        @port = options[:port]
        @environments = options[:environments] || []
        @reserved = false
      end
      
      def uri
        @uri ||= URI.parse("http://#{host}:#{port}/selenium-server/driver/")
      end
      
      def forward(command)
        Net::HTTP.post_form(uri, command.parameters)
      end

      def ==(other)
        host == other.host && port == other.port
      end
      
      def uid
        @uid ||= Digest::SHA1.hexdigest("#{host}:#{port}")
      end

      def to_param
        uid
      end

      def free?
        not @reserved
      end

      def reserve!
        @reserved = true
      end

      def release!
        @reserved = false
        @session_id = nil
      end

    end
    
  end
end
