(ns credit-card.service
  (:require [credit-card.controller :as controller]
            [credit-card.db :as db]
            [clojure.pprint :as pp]))

(defn set-initial-state []
  (controller/create-account! controller/random-costumer)
  (run! #(controller/transact! (controller/random-transaction %)) [:food :food :entertainment :transport]))

(defn print-costumer []
  (pp/pprint "************ Costumer Data ************")
  (pp/print-table [(db/costumer)]))

(defn print-credit-card []
  (pp/pprint "************ Credit Card ************")
  (pp/print-table [(db/credit-card)]))

(defn -main []
  "Service Entrypoint"
  (println "Starting Service")
  (set-initial-state)
  (print-costumer)
  (newline)
  (print-credit-card))

(-main)

; Operations
(comment (controller/list-transactions!)
         (controller/calculate-available-limit!)
         (controller/current-bill)
         (controller/transactions-by-category :food))


