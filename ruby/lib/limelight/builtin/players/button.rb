#- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
#- Limelight and all included source files are distributed under terms of the MIT License.

on_cast do
  button = Java::limelight.ui.model.inputs.ButtonPanel.new
  peer.add(button)
end
