(ns credit-card.logic
  (:import [java.time LocalDateTime]))

(defn total-amount-spent [transactions]
  (->> transactions
       (map :value)
       (reduce +)))

(defn available-limit [transactions limit]
  (- limit (total-amount-spent transactions)))

(defn string->datetime [date-string]
  (LocalDateTime/parse date-string))

(defn month-transactions [month all-transactions]
  (filter
    #(= month (.getMonth (:date %)))
    all-transactions))

(defn transactions-by-category [transactions]
  (group-by :category transactions))

(defn category-bill [transactions]
  (reduce
    (fn [new-map [key value]]
      (assoc
        new-map
        key
        (->> value
             (map :value)
             (reduce +))))
    {}
    (transactions-by-category transactions)))

(defn bill [transactions]
  (assoc (category-bill transactions) :total (reduce + (map :value transactions))))









