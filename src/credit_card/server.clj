(ns credit-card.server
  (:gen-class)
  (:require [credit-card.components :as components]))

(defn run-dev []
  (println "Starting Service in DEV")
  (components/create-and-start-system! :base-system))

(defn -main []
  (println "Starting Service")
  (components/create-and-start-system! :base-system))
