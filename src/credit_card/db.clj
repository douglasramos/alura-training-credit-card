(ns credit-card.db)

; GLOBAL STATE (only for mvp purpose)
(def db {:transactions []})

(defn database []
  db)

(defn insert-transaction! [transaction]
  (def db (update db :transactions conj transaction)))

(defn insert-costumer! [costumer]
  (def db (assoc db :costumer costumer)))

(defn insert-credit-card! [credit-card]
  (def db (assoc db :credit-card credit-card)))

(defn transactions []
  (:transactions db))