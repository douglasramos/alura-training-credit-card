(ns credit-card.adapter
  (:import [java.time LocalDateTime]))

(defn string->datetime [date-string]
  (LocalDateTime/parse date-string))