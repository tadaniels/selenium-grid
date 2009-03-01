#
# Main Rakefile for Selenium Grid
#

task :default => :build

task :build do
    sh "cd hub && rake"
    sh "ant clean dist"
end

