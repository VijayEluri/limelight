;- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
;- Limelight and all included source files are distributed under terms of the MIT License.

(ns limelight.clojure.prop-building
  (:use
    [limelight.clojure.core]
    [limelight.clojure.prop :only (new-prop)]
    [limelight.clojure.util :only (read-src ->options)])
  (:require
    [limelight.clojure.scene]))

(declare ->prop)
(declare *context*)

(defn- named? [x]
  (or
    (instance? clojure.lang.Named x)
    (string? x)))

(defn ->props [coll]
  (cond
    (or (not (coll? coll)) (empty? coll)) ()
    (named? (first coll)) (list (->prop coll))
    (coll? (first coll)) (reduce #(into %1 (->props %2)) [] coll)
    :else (throw (Exception. (str "Don't know how to create props from:" coll)))))

(defn- ->prop [data]
  (let [name (name (first data))
        options (if (map? (second data)) (second data) nil)
        child-data (if options (rest (rest data)) (rest data))
        options (assoc options :name name)
        prop (new-prop (limelight.util.Opts. options))]
    (when (seq child-data)
      (add prop (->props child-data)))
    prop))

(defn- src->data [src context root]
  (let [scene (scene root)
        props-ns (._ns scene)]
    (binding [*ns* props-ns
              *context* context]
      (if (string? src)
        (let [src-file (or (:source-file *context*) "[CODE]")]
          (read-src src-file (str "[" src "]")))
        (eval src)))))

(defn- establish-root-path [root context]
  (if-let [prod (production root)]
    (if (nil? (:root-path context))
      (assoc context :root-path (path prod))
      context)
    context))

(defn do-prop-building [root src context]
  (let [context (establish-root-path root context)
        context (assoc context :root root)
        prop-data (src->data src context root)
        props (->props prop-data)]
    (add root props)
    root))

(defn- extract-root-path []
  (if-let [path (:root-path *context*)]
    path
    (throw (limelight.LimelightException. "Can't install props without a :root-path"))))

(defn install [path & context]
  (let [fs (limelight.Context/fs)
        root-path (extract-root-path)
        include-path (.pathTo fs root-path path)
        src (.readTextFile fs include-path)
        extra-context (->options context)
        new-context (merge *context* extra-context)]
    (binding [*context* new-context]
      (read-src include-path (str "(list " src ")")))))

(extend-type limelight.clojure.scene.Scene
  limelight.clojure.core/Buildable
  (build-props [this src context]
    (do-prop-building this src context)))

(extend-type limelight.clojure.prop.Prop
  limelight.clojure.core/Buildable
  (build-props [this src context]
    (do-prop-building this src context)))
