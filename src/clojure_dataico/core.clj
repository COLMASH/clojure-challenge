(ns clojure-dataico.core 
(:require [clojure.data.json :as json]) 
(:require [clojure.spec.alpha :as s] [clojure-dataico.invoice-spec :as cds])
(:require [clj-time.coerce :as c] [clj-time.format :as f])
(:gen-class))

;;>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 1st PROBLEM <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

(def invoice (clojure.edn/read-string (slurp "src/clojure_dataico/invoice.edn")))

(defn find-invoice-items [invoice]
  (println ">>> PROBLEM 1 <<<")
  (println "✅ Solution: Those are the items that satisfy the conditions asked (see find-invoice-items function inside core.clj)")
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

(defn instant-date-formatter [value]
  (c/to-date (f/parse (f/formatter "dd/MM/yyyy") value)))

(defn number-as-double-formatter [key value]
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
  (println (str "✅ Solution: Parsing and transforming JSON file into a Spec compliant Clojure map (see invoice-generator function inside core.clj)..."))
  (def spec-compliant-invoice (postwalk-replace valid-invoice-keys (json/read-str json-file :value-fn number-as-double-formatter :key-fn keyword)))
  (println "Checking if it is Spec compliant:")
  (println "(s/valid? ::invoice invoice) =>" (s/valid? ::cds/invoice (:invoice spec-compliant-invoice))))

;;>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 3rd PROBLEM <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


;;>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> MAIN FUNCTION <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

(defn -main
  [& args]
  (println "Hello Daniel, here are my solutions 📝")
  (find-invoice-items invoice)
  (invoice-generator json-file))