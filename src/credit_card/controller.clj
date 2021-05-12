(ns credit-card.controller
  (:require [credit-card.db :as db]
            [credit-card.logic :as logic])
  (:import [java.time LocalDateTime]))

;; Random Data
(defn random-transaction
  ([]
   (random-transaction :food))
  ([category]
   (random-transaction category (logic/string->datetime "2021-05-02T16:40:00")))
  ([category date]
   (random-transaction category date 24.5))
  ([category date value]
   (random-transaction category date value "Burger king"))
  ([category date value establishment]
   {:date date, :category category, :value value, :establishment establishment}))

(def random-costumer
  {:name  "Douglas Ramos"
   :cpf   "051.342.3452-44"
   :email "douglas.ramos@nubank.com.br"})

(def random-credit-card
  {:number   5492316632645226
   :cvv      534
   :due-date (logic/string->datetime "2029-09-30T23:59:00")
   :limit    100})

;; Use Cases
(defn transact! [transaction]
  (db/insert-transaction! transaction))

(defn create-account! [costumer]
  (db/insert-costumer! costumer)
  (db/insert-credit-card! random-credit-card))

(defn list-transactions! []
  (db/transactions))

(defn current-bill []
  (let [current-month (.getMonth (LocalDateTime/now))]
    (logic/bill
      (logic/month-transactions current-month (db/transactions)))))

(defn calculate-available-limit! []
  (logic/available-limit (db/transactions) (get-in db/db [:credit-card :limit])))

;; Manual tests
(create-account! random-costumer)

(transact! (random-transaction :car))

(list-transactions!)

(calculate-available-limit!)

(current-bill)

(db/database)
