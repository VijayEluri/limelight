;- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
;- Limelight and all included source files are distributed under terms of the MIT License.

(ns limelight.clojure.prop-spec
  (:use
    [speclj.core]
    [limelight.clojure.spec-helper]
    [limelight.clojure.prop])
  (:import
    [limelight.clojure.prop Prop]))

(describe "Prop"

  (it "has limelight lineage"
    (should (isa? Prop limelight.model.api.PropProxy)))

  (it "can be created"
    (let [prop (new-prop (hash-map :name "Bill"))]
      (should-not= nil @(.peer prop))
      (should= limelight.ui.model.PropPanel (type @(.peer prop)))))
  )




