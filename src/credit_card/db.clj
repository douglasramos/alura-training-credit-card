(ns credit-card.db
  (:require [datomic.api :as d]
            [clojure.pprint :as pp]))

; Transactions
(defn insert! [entity storage]
  (pp/pprint @(d/transact (:conn storage) [entity])))

; Queries
; TODO makes all queries receives the db as an input
(defn transactions [storage]
  (d/q '[:find [(pull ?e [*]) ...]
         :where [?e :transaction/category]] (d/db (:conn storage))))

(defn costumer [storage]
  (d/q '[:find (pull ?e [*]) .
         :where [?e :costumer/cpf]] (d/db (:conn storage))))

(defn credit-card [storage]
  (d/q '[:find (pull ?e [*]) .
         :where [?e :credit-card/number]] (d/db (:conn storage))))





