(ns credit-card.service
  (:require [credit-card.controller :as controller]
            [credit-card.db :as db]
            [clojure.pprint :as pp]
            [credit-card.components.system-utils :as system-utils]))

; necessary to be a function because it is evaluated before
; systems-map initialized all components. delay function could be used
(defn storage [] (system-utils/get-component :storage))

(defn set-initial-state [storage]
  (controller/create-account! controller/random-costumer storage)
  (run!
    #(controller/transact! (controller/random-transaction %) storage)
    [:food :food :entertainment :transport]))

(defn print-costumer [storage]
  (pp/pprint "************ Costumer Data ************")
  (pp/print-table [(db/costumer storage)]))

(defn print-credit-card [storage]
  (pp/pprint "************ Credit Card ************")
  (pp/print-table [(db/credit-card storage)]))

(comment (set-initial-state (storage))
         (print-costumer (storage))
         (print-credit-card (storage))
         (controller/list-transactions! (storage))
         (controller/calculate-available-limit! (storage))
         (controller/current-bill (storage))
         (controller/transactions-by-category :food (storage)))