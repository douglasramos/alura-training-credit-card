(ns credit-card.db)

; GLOBAL STATE (only for mvp purpose)
(def db {:transactions []})


(defn insert-transaction! [transaction]
  (def db (update db :transactions conj transaction)))


(defn insert-costumer! [costumer]
  (println "Imrpimindo costumer" costumer)
  (def db (assoc db :costumer costumer)))
