(ns credit-card.logic)

(defn total-amount-spent [transactions]
  (->> transactions
       (map :transaction/value)
       (reduce +)))

(defn transactions-by-category [transactions]
  (group-by :transaction/category transactions))

(defn category-bill [transactions]
  (reduce
    (fn [new-map [key value]]
      (assoc
        new-map
        key
        (->> value
             (map :transaction/value)
             (reduce +))))
    {}
    (transactions-by-category transactions)))

(defn month-transactions [month all-transactions]
  (filter
    #(= month (.getMonth (:transaction/date %)))
    all-transactions))

(defn bill [transactions]
  (assoc
    (category-bill transactions)
    :transaction/total
    (reduce + (map :transaction/value transactions))))

(defn available-limit [transactions limit]
  (- limit (total-amount-spent transactions)))







