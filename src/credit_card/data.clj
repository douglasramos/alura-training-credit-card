(ns credit-card.data
  (:require [credit-card.adapter :as adapter]))

;; Random Data
(defn arbitrary-transaction
  ([]
   (arbitrary-transaction :food))
  ([category]
   (arbitrary-transaction category "2021-05-02T16:40:00"))
  ([category date]
   (arbitrary-transaction category date 24.5))
  ([category date amount]
   (arbitrary-transaction category date amount "Burger king"))
  ([category date amount establishment]
   {:transaction/date          (adapter/string->datetime date)
    :transaction/category      category
    :transaction/amount        amount
    :transaction/establishment establishment}))

(def arbitrary-costumer
  {:costumer/name  "Douglas Ramos"
   :costumer/cpf   "051.342.3452-44"
   :costumer/email "douglas.ramos@nubank.com.br"})

(def arbitrary-credit-card
  {:credit-card/number   5492316632645226
   :credit-card/cvv      534
   :credit-card/due-date (adapter/string->datetime "2029-09-30T23:59:00")
   :credit-card/limit    100.0})
