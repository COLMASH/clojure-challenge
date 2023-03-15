(ns clojure-dataico.core-test
  (:require [clojure.test :refer :all] [clojure-dataico.core :refer :all])
  (:require [clojure-dataico.invoice-item :as invoice-item]))

(deftest test-subtotal-1
  (testing "Tests the subtotal function"

    (def case-1 {:invoice-item/precise-quantity 3.0
                 :invoice-item/precise-price 100.0
                 :invoice-item/discount-rate 100.0})

    (is (= (invoice-item/subtotal case-1) 0.0))))

(deftest test-subtotal-2
  (testing "Tests the subtotal function"

    (def case-2 {:invoice-item/precise-quantity 3.0
                 :invoice-item/precise-price 100.0
                 :invoice-item/discount-rate 100.0})

    (is (= (invoice-item/subtotal case-2) 0.0))))

(deftest test-subtotal-3
  (testing "Tests the subtotal function"
    
    (def case-3 {:invoice-item/precise-quantity 3.0
                 :invoice-item/precise-price 100.0
                 :invoice-item/discount-rate 100.0})
    
    (is (= (invoice-item/subtotal case-3) 0.0))))

(deftest test-subtotal-4 
  (testing "Tests the subtotal function"
    
    (def case-4 {:invoice-item/precise-quantity 3.0
                 :invoice-item/precise-price 100.0
                 :invoice-item/discount-rate 100.0})
    
    (is (= (invoice-item/subtotal case-4) 0.0))))

(deftest test-subtotal-5
  (testing "Tests the subtotal function"
    
    (def case-5 {:invoice-item/precise-quantity 3.0
                 :invoice-item/precise-price 100.0
                 :invoice-item/discount-rate 100.0})
    
    (is (= (invoice-item/subtotal case-5) 0.0))))