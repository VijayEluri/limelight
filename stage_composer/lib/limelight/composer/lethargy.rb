module Limelight
  module Composer
    
    module Lethargy
      
      def self.null_event(*args)
        args.each do |sym|
          define_method(sym) { |event| } # do nothing
        end
      end
      
      null_event *Limelight::Prop::EVENTS
      
      def mouse_clicked(e)
        scene.production.controller.prop_clicked(self)
      end
      
    end
    
  end
end