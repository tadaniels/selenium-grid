module Grid
  module Hub

    class CommandParser

      NEW_BROWSER_SESSION = "getNewBrowserSession";
      CLOSE_BROWSER_SESSION = "testComplete";
  
      def self.parse(params)
        case params["cmd"]
        when NEW_BROWSER_SESSION
          NewBrowserSessionCommand.new params
        when CLOSE_BROWSER_SESSION
          CloseBrowserSessionCommand.new params
        else
          RemoteCommand.new params
        end
      end
  
    end
  
  end
end  
