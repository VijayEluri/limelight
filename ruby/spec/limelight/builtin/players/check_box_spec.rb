##- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
##- Limelight and all included source files are distributed under terms of the MIT License.
#
#require File.expand_path(File.dirname(__FILE__) + "/../../../spec_helper")
#require 'limelight/prop'
#require 'limelight/builtin/players'
#
#describe Limelight::Builtin::Players::CheckBox do
#
#  before(:each) do
#    @prop = Limelight::Prop.new
#    Limelight::Player.cast(Limelight::Builtin::Players::CheckBox, @prop)
#  end
#
#  it "should have a TextField" do
#    @prop.peer.children[0].class.should == Java::limelight.ui.model.inputs.CheckBoxPanel
#  end
#
#  it "should handled checked state" do
#    @prop.checked?.should == false
#    @prop.checked.should == false
#
#    @prop.checked = true
#
#    @prop.checked?.should == true
#    @prop.checked.should == true
#  end
#
#end