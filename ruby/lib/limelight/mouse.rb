#- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
#- Limelight and all included source files are distributed under terms of the MIT License.

module Limelight

  class Mouse

    def press(prop, x = 0, y = 0, modifiers = 0, click_count = 1)
      location = point_for(prop, x, y)
      owner = owner_of(location, prop)
      Java::limelight.ui.events.panel.MousePressedEvent.new(modifiers, location, click_count).dispatch(owner)
    end

    def release(prop, x = 0, y = 0, modifiers = 0, click_count = 1)
      location = point_for(prop, x, y)
      owner = owner_of(location, prop)
      Java::limelight.ui.events.panel.MouseReleasedEvent.new(modifiers, location, click_count).dispatch(owner)
    end

    def click(prop, x = 0, y = 0, modifiers = 0, click_count = 1)
      location = point_for(prop, x, y)
      owner = owner_of(location, prop)
      Java::limelight.ui.events.panel.MouseClickedEvent.new(modifiers, location, click_count).dispatch(owner)
    end

    def move(prop, x = 0, y = 0, modifiers = 0, click_count = 1)
      location = point_for(prop, x, y)
      owner = owner_of(location, prop)
      Java::limelight.ui.events.panel.MouseMovedEvent.new(modifiers, location, click_count).dispatch(owner)
    end

    def drag(prop, x = 0, y = 0, modifiers = 0, click_count = 1)
      location = point_for(prop, x, y)
      owner = owner_of(location, prop)
      Java::limelight.ui.events.panel.MouseDraggedEvent.new(modifiers, location, click_count).dispatch(owner)
    end

    def enter(prop, x = 0, y = 0, modifiers = 0, click_count = 1)
      location = point_for(prop, x, y)
      owner = owner_of(location, prop)
      Java::limelight.ui.events.panel.MouseEnteredEvent.new(modifiers, location, click_count).dispatch(owner)
    end

    def exit(prop, x = 0, y = 0, modifiers = 0, click_count = 1)
      location = point_for(prop, x, y)
      owner = owner_of(location, prop)
      Java::limelight.ui.events.panel.MouseExitedEvent.new(modifiers, location, click_count).dispatch(owner)
    end

    def wheel(prop, scroll_amount = 1, x = 0, y = 0, modifiers = 0, click_count = 0, scroll_type = 0, wheel_rotation = 1)
      location = point_for(prop, x, y)
      owner = owner_of(location, prop)
      Java::limelight.ui.events.panel.MouseWheelEvent.new(modifiers, location, click_count, scroll_type, scroll_amount, wheel_rotation).dispatch(owner)
    end

    def push(prop)
      Java::limelight.ui.events.panel.ButtonPushedEvent.new().dispatch(prop.peer)
    end

    private

    def point_for(prop, x, y)
      absolute_location = prop.peer.absolute_location
      local_x = absolute_location.x + x
      local_y = absolute_location.y + y
      return Java::java.awt.Point.new(local_x, local_y)
    end

    def owner_of(location, prop)
      owner = prop.peer.get_owner_of_point(location)
      return owner
    end

  end

end