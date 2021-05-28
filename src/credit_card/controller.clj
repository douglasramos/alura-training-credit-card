(ns credit-card.controller
  (:require [credit-card.db :as db]
            [credit-card.logic :as logic]
            [credit-card.adapter :as adapter])
  (:import [java.time LocalDateTime]))

;; Random Data
(defn random-transaction
  ([]
   (random-transaction :food))
  ([category]
   (random-transaction category (adapter/string->datetime "2021-05-02T16:40:00")))
  ([category date]
   (random-transaction category date 24.5))
  ([category date value]
   (random-transaction category date value "Burger king"))
  ([category date value establishment]
   {:transaction/date          date
    :transaction/category      category
    :transaction/value         value
    :transaction/establishment establishment}))

(def random-costumer
  {:costumer/name  "Douglas Ramos"
   :costumer/cpf   "051.342.3452-44"
   :costumer/email "douglas.ramos@nubank.com.br"})

(def random-credit-card
  {:credit-card/number   5492316632645226
   :credit-card/cvv      534
   :credit-card/due-date (adapter/string->datetime "2029-09-30T23:59:00")
   :credit-card/limit    100.0})

;; Use Cases
(defn transact! [transaction storage]
  (db/insert! (adapter/transaction-model->transaction-schema transaction) storage))

(defn create-account! [costumer storage]
  (db/insert! costumer storage)
  (db/insert! (adapter/credit-card-model->credit-card-schema random-credit-card) storage))

(defn list-transactions! [storage]
  (db/transactions storage))

(defn current-bill [storage]
  (let [current-month (.getMonth (LocalDateTime/now))
        all-transactions (map adapter/transaction-schema->transaction-model (db/transactions storage))
        month-transactions (logic/month-transactions current-month all-transactions)]
    (logic/bill month-transactions)))

(defn calculate-available-limit! [storage]
  (logic/available-limit (db/transactions storage) (:credit-card/limit (db/credit-card storage))))

(defn transactions-by-category [category storage]
  (filter #(= category (:transaction/category %)) (db/transactions storage)))
