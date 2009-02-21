class RemoteControlsController < ApplicationController
  
  def index
    @pool = Grid::Hub::Registry.remote_control_pool
  end

  def create
    pool = Grid::Hub::Registry.remote_control_pool
    pool.add Grid::Hub::RemoteControlProxy.new(params)
    redirect_to remote_controls_path
  end

  def destroy
    pool = Grid::Hub::Registry.remote_control_pool
    remote_control = pool.find params[:id]
    pool.remove(remote_control) if remote_control
    redirect_to remote_controls_path
  end

end