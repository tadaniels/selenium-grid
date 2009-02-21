class SeleniumProtocolMultiplexerController < ApplicationController
  
  def index
    pool = Grid::Hub::Registry.remote_control_pool
    proxy = pool.find_by_session_id params["sessionId"]
    response = Grid::Hub::CommandParser.parse(params).execute(proxy)
    
    render :text => response, :content_type => "text/plain"
  end

end
