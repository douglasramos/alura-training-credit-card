(ns credit-card.logic)

(defn total-amount-spent [transactions]
  (->> transactions
       (map :value)
       (reduce +)))

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

(defn month-transactions [month all-transactions]
  (filter
    #(= month (.getMonth (:date %)))
    all-transactions))

(defn bill [transactions]
  (assoc
    (category-bill transactions)
    :total
    (reduce + (map :value transactions))))


(defn available-limit [transactions limit]
  (- limit (total-amount-spent transactions)))







