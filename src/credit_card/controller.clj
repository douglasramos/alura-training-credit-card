(ns credit-card.controller
  (:require [credit-card.db :as db]
            [credit-card.logic :as logic]
            [credit-card.adapter :as adapter]
            [credit-card.data :as data])
  (:import [java.time LocalDateTime]))

;; Use Cases
(defn transact! [transaction storage]
  (db/insert! (adapter/transaction-model->transaction-schema transaction) storage))

(defn create-account! [costumer storage]
  (db/insert! costumer storage)
  (db/insert! (adapter/credit-card-model->credit-card-schema data/arbitrary-credit-card) storage))

(defn list-transactions! [storage]
  (db/transactions storage))

(defn current-bill [storage]
  (let [current-month (.getMonth (LocalDateTime/now))
        all-transactions (map adapter/transaction-schema->transaction-model (db/transactions storage))
        month-transactions (logic/month-transactions current-month all-transactions)]
    (logic/billing-statement month-transactions)))

(defn calculate-available-limit! [storage]
  (logic/available-limit (db/transactions storage) (:credit-card/limit (db/credit-card storage))))

(defn transactions-by-category [category storage]
  (filter #(= category (:transaction/category %)) (db/transactions storage)))
