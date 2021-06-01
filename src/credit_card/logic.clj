(ns credit-card.logic)

(defn ^:private total-amount-spent [transactions]
  (->> transactions
       (map :transaction/amount)
       (reduce +)))

(defn ^:private transactions-by-category [transactions]
  (group-by :transaction/category transactions))

(defn ^:private category-total-amount [category-transactions]
  (->> category-transactions
       (map :transaction/amount)
       (reduce +)))

(defn ^:private category-billing-statement [transactions]
  (reduce
    (fn [category-bill [category category-transactions]]
      (assoc category-bill category (category-total-amount category-transactions)))
    {}
    (transactions-by-category transactions)))

(defn month-transactions [month all-transactions]
  (filter
    #(= month (.getMonth (:transaction/date %)))
    all-transactions))

(defn billing-statement [transactions]
  (assoc
    (category-billing-statement transactions)
    :total
    (reduce + (map :transaction/amount transactions))))

(defn available-limit [transactions limit]
  (- limit (total-amount-spent transactions)))





