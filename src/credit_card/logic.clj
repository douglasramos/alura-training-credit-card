(ns credit-card.logic
  (:require [credit-card.db :as db]))

(def random-transaction {:date "20210121"
                         :value 24.5
                         :establishment "Burguer king"
                         :category "Food"})


(def random-costumer {:name "Douglas Ramos"
                     :cpf "051.342.3452-44"
                     :email "douglas.ramos@nubank.com.br"})

(defn transact! [transaction]
  (db/insert-transaction! transaction))

(defn create-costumer! [costumer]
  (db/insert-costumer! costumer))

;; manual tests
(create-costumer! random-costumer)

(transact! random-transaction)

(println db/db)