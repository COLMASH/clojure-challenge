(ns clojure-dataico.core-test
  (:require [clojure.test :refer :all] [clojure-dataico.core :refer :all])
  (:require [clojure-dataico.invoice-item :as invoice-item]))

(deftest test-subtotal 
  (testing "-1- Tests subtotal function when precise-quantity = 0" 
    (def case-5 {:invoice-item/precise-quantity 0.0
                 :invoice-item/precise-price 100.0
                 :invoice-item/discount-rate 50.0}) 
    (is (= (invoice-item/subtotal case-5) 0.0)))
  
  (testing "-2- Tests subtotal function when discount-rate = 0%"
    (def case-1 {:invoice-item/precise-quantity 5.0
                 :invoice-item/precise-price 100.0
                 :invoice-item/discount-rate 0.0})
    (is (= (invoice-item/subtotal case-1) 500.0)))
  
  (testing "-3- Tests subtotal function when discount-rate = 100%"
    (def case-2 {:invoice-item/precise-quantity 5.0
                 :invoice-item/precise-price 100.0
                 :invoice-item/discount-rate 100.0})
    (is (= (invoice-item/subtotal case-2) 0.0)))
  
  (testing "-4- Tests subtotal function when precise-price = 0" 
    (def case-3 {:invoice-item/precise-quantity 5.0
                 :invoice-item/precise-price 0.0
                 :invoice-item/discount-rate 50.0}) 
    (is (= (invoice-item/subtotal case-3) 0.0)))
  
   (testing "-5- Tests subtotal function when precise-price :key is missing (default must be 0)"
    (def case-4 {:invoice-item/precise-quantity 5.0
                 :invoice-item/precise-price 100.0}) 
    (is (= (invoice-item/subtotal case-4) 500.0))))

