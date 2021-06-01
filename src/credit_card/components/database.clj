(ns credit-card.components.database
  (:require [datomic.api :as d]
            [com.stuartsierra.component :as component]
            [clojure.pprint :as pp]))

(defn db-uri [host port database]
  (str "datomic:dev://" host ":" port "/" database))

(def costumer-schema [{:db/ident       :costumer/cpf
                       :db/valueType   :db.type/string
                       :db/cardinality :db.cardinality/one
                       :db/unique      :db.unique/identity}
                      {:db/ident       :costumer/name
                       :db/valueType   :db.type/string
                       :db/cardinality :db.cardinality/one}
                      {:db/ident       :costumer/email
                       :db/valueType   :db.type/string
                       :db/cardinality :db.cardinality/one}])

(def transaction-schema [{:db/ident       :transaction/category
                          :db/valueType   :db.type/keyword
                          :db/cardinality :db.cardinality/one}
                         {:db/ident       :transaction/amount
                          :db/valueType   :db.type/double
                          :db/cardinality :db.cardinality/one}
                         {:db/ident       :transaction/date
                          :db/valueType   :db.type/string
                          :db/cardinality :db.cardinality/one}
                         {:db/ident       :transaction/establishment
                          :db/valueType   :db.type/string
                          :db/cardinality :db.cardinality/one}])

(def credit-card-schema [{:db/ident       :credit-card/number
                          :db/valueType   :db.type/long
                          :db/cardinality :db.cardinality/one
                          :db/unique      :db.unique/identity}
                         {:db/ident       :credit-card/cvv
                          :db/valueType   :db.type/long
                          :db/cardinality :db.cardinality/one}
                         {:db/ident       :credit-card/due-date
                          :db/valueType   :db.type/string
                          :db/cardinality :db.cardinality/one}
                         {:db/ident       :credit-card/limit
                          :db/valueType   :db.type/double
                          :db/cardinality :db.cardinality/one}])

(defn connect-to-database [host port database]
  (let [db-uri (db-uri host port database)]
    (d/create-database db-uri)
    (d/connect db-uri)))

(defrecord Database [host port database conn]
  component/Lifecycle

  (start [this]
    (println " Starting database ")

    (let [conn (connect-to-database host port database)]
      (run! #(d/transact conn %) [costumer-schema credit-card-schema transaction-schema])
      (pp/pprint conn)
      (assoc this :conn conn)))

  (stop [this]
    (println " Stopping database ")
    (d/delete-database (db-uri host port database))
    (assoc this :conn nil)))

(defn new-database []
  (map->Database {:host     "localhost"
                  :port     "4334"
                  :database "credit-card"}))