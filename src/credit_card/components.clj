(ns credit-card.components
  (:refer-clojure :exclude [test])
  (:require [com.stuartsierra.component :as component]
            [credit-card.components.database :as database]
            [credit-card.components.system-utils :as system-utils]))


; TODO use config based on environment
;(def base-config-map {:environment :prod
;                      :dev-port    8080})

;(def local-config-map {:environment :dev
;                       :dev-port    8080})

(defn base []
  (component/system-map
    :storage (database/new-database)))

(def systems-map
  {:base-system base})

(defn create-and-start-system!
  ([] (create-and-start-system! :base-system))
  ([env]
   (system-utils/bootstrap! systems-map env)))

(defn stop-system! [] (system-utils/stop-components!))