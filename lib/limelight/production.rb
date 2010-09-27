#- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
#- Limelight and all included source files are distributed under terms of the GNU LGPL.

require 'limelight/limelight_exception'
require 'limelight/dsl/styles_builder'
require 'limelight/producer'
require 'drb'


module Limelight

  # The root object of Limelight Production.  Every Prop in a production has access to its Production object.
  # Therefore it is typical to store reasources in the Production.
  #
  # Productions are configured, and attributes are added, by the ProductionBuilder.
  #
  class Production

    attr_reader :producer
    attr_reader :production
    attr_accessor :theater
    attr_accessor :closed #:nodoc:

    # Users typically need not create Production objects.
    #
    def initialize(production)
      @production = production
      @casting_director = CastingDirector.new(@production.resource_loader)
      @theater = Theater.new(self, @production.theater)

      @production.proxy = self
    end

    alias :getTheater :theater

    def open()
      @producer = Producer.new(path, nil, self)
      @producer.open
    end

    # Returns the name of the Production
    #
    def name
      return @production.name
    end

    # Sets the name of the Production.  The name must be unique amongst all Productions in memory.
    #
    def name=(value)
      Context.instance.studio.error_if_duplicate_name(value)
      @name = value
    end

    # Returns the resource loader for the Production
    #
    def root
      return @production.resource_loader
    end

    # Return the path to the root directory of the production
    #
    def path
      return @production.resource_loader.root
    end

    # Returns the path to the production's init file
    #
    def init_file
      return root.path_to("init.rb")
    end

    # Returns the path to the production's production.rb file
    #
    def production_file
      return root.path_to("production.rb")
    end

    # Returns the path to the production's stages file
    #
    def stages_file
      return root.path_to("stages.rb")
    end

    # Returns the path to the production's styles file
    #
    def styles_file
      return root.path_to("styles.rb")
    end

    # Returns the path to the production's gems directory
    #
    def gems_directory
      return root.path_to("__resources/gems/gems")
    end

    # Returns the path to the productions gems root
    #
    def gems_root
      return root.path_to("__resources/gems")
    end

    # Returns the path to the named Scene's directory within the Production
    #
    def scene_directory(name)
      return root.root if name == nil
      return root.path_to(name)
    end

    # Returns the minimum version of limelight required to run this production.  Default: "0.0.0"
    # If the version of limelight used to open this production is less than the minimum,
    # an error will be displayed (starting with version 0.4.0).
    #
    def minimum_limelight_version
      return "0.0.0"
    end

    # Returns true if the production allows itself to be closed.  The system will call this methods when
    # it wishes to close the production, perhaps when the user quits the application.  By default the production
    # will always return true.
    #
    def allow_close?
      return true
    end

    # Called when the production is about to be opened.  The default implementation does nothing but you may re-implement
    # it in the production.rb file.
    #
    def production_opening
    end

    # Called when the production has been loaded.  That is, when all the gems have been loaded stages have been
    # instantiated.
    #
    def production_loaded
    end

    # Called when the production is fully opened.  The default implementation does nothing but you may re-implement
    # it in the production.rb file.
    #
    def production_opened
    end

    # Called when the production is about to be closed.  The default implementation does nothing but you may re-implement
    # it in the production.rb file.
    #
    def production_closing
    end

    # Called when the production is fully closed.  The default implementation does nothing but you may re-implement
    # it in the production.rb file.
    #
    def production_closed
    end

    # returns true if the production has been closed.
    #
    def closed?
      return @closed
    end

    # Closes the production. If there are no more productions open, the Limelight runtime will shutdown.
    # The production will actually delegate to it's producer and the producer will close the production down.
    #
    def close
      @producer.close
    end

    # Publish this production using DRb on the specified port.  The production will delegate to its producer to
    # actually do the publishing.
    #
    def publish_on_drb(port)
      @producer.publish_production_on_drb(port)
    end

    # Called when the last stage in this production's theater is closed.  If the allow_close? returns true
    # this production will be closed.
    #
    def theater_empty!
      close if allow_close? && !closed?
    end

    # A production with multiple Scenes may have a 'styles.rb' file in the root directory.  This is called the
    # root_styles.  This method loads the root_styles, if they haven't already been loaded, and returns them.
    #
    def root_styles
      unless @root_styles
        if File.exists?(styles_file)
          @root_styles = Limelight.build_styles_from_file(styles_file)
        else
          @root_styles = {}
        end
      end
      return @root_styles
    end

    def casting_director
      return @casting_director
    end
    alias :getCastingDirector :casting_director

    alias :getName :name #:nodoc:
    alias :setName :name= #:nodoc:
    alias :allowClose :allow_close? #:nodoc: 

    def callMethod(name, java_obj_array) #:nodoc:
      args = []
      java_obj_array.length.times { |i| args << java_obj_array[i] }
      send(name.to_sym, *args)
    end

  end

end