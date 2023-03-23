(ns clojure-dataico.core
  (:require [clojure.data.json :as json])
  (:require [clojure.spec.alpha :as s] [clojure-dataico.invoice-spec :as cds])
  (:require [clj-time.coerce :as c] [clj-time.format :as f])
  (:require [clojure-dataico.invoice-item :as invoice-item])
  (:require [clojure.test :refer :all]
            [clojure-dataico.core :refer :all])
  (:gen-class))

;;>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 1st PROBLEM <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

(def invoice (clojure.edn/read-string (slurp "src/clojure_dataico/invoice.edn")))

(defn find-invoice-items [invoice]
  (println ">>> PROBLEM 1 <<<")
  (println "✅ Solution: Those are the items that satisfy the conditions asked")
  (println "(see find-invoice-items function inside src/clojure_dataico/core.clj)")
  (println (->> invoice
                :invoice/items
                (filter (fn [item]
                          (or (some #(= (:tax/rate %) 19) (:taxable/taxes item))
                              (some #(= (:retention/rate %) 1) (:retentionable/retentions item)))))
                (remove (fn [item]
                          (and (some #(= (:tax/rate %) 19) (:taxable/taxes item))
                               (some #(= (:retention/rate %) 1) (:retentionable/retentions item))))))))

;;>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 2nd PROBLEM <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

(use 'clojure.walk)

(def json-file (slurp "src/clojure_dataico/invoice.json"))

(defn number-and-date-formatter [key value]
  (if (= key :tax_rate)
    (double value)
    (if (or (= key :issue_date) (= key :payment_date))
      (c/to-date (f/parse (f/formatter "dd/MM/yyyy") value))
      value)))

(def valid-invoice-keys {:issue_date :invoice/issue-date
                         :customer :invoice/customer
                         :company_name :customer/name
                         :email :customer/email
                         :items :invoice/items
                         :price :invoice-item/price
                         :quantity :invoice-item/quantity
                         :sku :invoice-item/sku
                         :taxes :invoice-item/taxes
                         :tax_category :tax/category
                         :tax_rate :tax/rate
                         "IVA" :iva})

(defn invoice-generator [json-file]
  (println ">>> PROBLEM 2 <<<")
  (println "✅ Solution: Parsing and transforming JSON file into a Spec compliant Clojure map...")
  (println "(see invoice-generator function inside src/clojure_dataico/core.clj)")
  (def spec-compliant-invoice (postwalk-replace valid-invoice-keys (json/read-str json-file :value-fn number-and-date-formatter :key-fn keyword)))
  (println "Checking if the resulting invoice map is Spec compliant:")
  (println "(s/valid? ::invoice invoice) =>" (s/valid? ::cds/invoice (:invoice spec-compliant-invoice))))

;;>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 3rd PROBLEM <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

(defn tests-description []
  (println ">>> PROBLEM 3 <<<")
  (println "✅ Please run the \"lein test\" command in order to see the tests results")
  (println "(see deftest test-subtotal inside test/clojure_dataico/core_test.clj)")
  (println "Those are the edge cases that I identified:")
  (println "✓-1- Tests subtotal function when precise-quantity = 0")
  (println "✓-2- Tests subtotal function when discount-rate = 0%")
  (println "✓-3- Tests subtotal function when discount-rate = 100%")
  (println "✓-4- Tests subtotal function when precise-price = 0")
  (println "✓-5- Tests subtotal function when precise-price :key is missing (default must be 0)")
  )

;;>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> MAIN FUNCTION <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

(defn -main
  [& args]
  (println "Hello there, here are my solutions 📝")
  (find-invoice-items invoice)
  (invoice-generator json-file)
  (tests-description)
  )