(ns credit-card.logic-test
  (:require [clojure.test :refer [deftest is are testing]]
            [credit-card.logic :as logic]
            [credit-card.data :as data])
  (:import [java.time Month]))

(def transactions-input
  [(data/arbitrary-transaction :food "2021-05-02T16:40:00" 20.0)
   (data/arbitrary-transaction :entertainment "2021-12-02T16:40:00" 20.0)
   (data/arbitrary-transaction :food "2021-12-02T20:45:00" 10.0)
   (data/arbitrary-transaction :entertainment "2021-12-02T13:40:00" 30.0)])

(deftest test-month-transactions
  (testing "filters transactions by month input"
    (are [expected-output output]
      (= expected-output output)
      1 (count (logic/month-transactions Month/MAY transactions-input))
      3 (count (logic/month-transactions Month/DECEMBER transactions-input))
      0 (count (logic/month-transactions Month/JANUARY transactions-input)))))

(deftest test-billing-statement
  (testing "returns a map that represents the complete billing statement given a list of transactions"
    (is (= {:food 30.0 :entertainment 50.0 :total 80.0} (logic/billing-statement transactions-input)))))

(deftest test-available-limit
  (testing "returns the available limit based on the credit card limit and the transactions"
    (are [expected-output output]
      (= expected-output output)
      20.0 (logic/available-limit transactions-input 100.0)
      0.0 (logic/available-limit transactions-input 80.0)
      100.0 (logic/available-limit transactions-input 180.0))))