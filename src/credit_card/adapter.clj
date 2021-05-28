(ns credit-card.adapter
  (:import [java.time LocalDateTime]))

(defn string->datetime [date-string]
  (LocalDateTime/parse date-string))

(defn datetime->string [datetime]
  (.toString datetime))


; TODO This two methods could be only one that converts any field with date object

(defn credit-card-model->credit-card-schema [credit-card]
  (let [{due-date :credit-card/due-date} credit-card
        due-date-string (datetime->string due-date)]
    (merge credit-card {:credit-card/due-date due-date-string})))

(defn transaction-model->transaction-schema [transaction]
  (let [{date :transaction/date} transaction
        date-string (datetime->string date)]
    (merge transaction {:transaction/date date-string})))


(defn transaction-schema->transaction-model [transaction]
  (let [{date-string :transaction/date} transaction
        date (string->datetime date-string)]
    (merge transaction {:transaction/date date})))