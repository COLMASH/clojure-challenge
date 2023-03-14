(ns clojure-dataico.core
  (:gen-class))

(use 'clojure-dataico.invoice)

(defn find-invoice-items [invoice]
  (println "Solution to Problem 1: Those are the items 📦 that satisfy the conditions asked")
  (println (->> invoice
       :invoice/items
       (filter (fn [item]
                 (or (some #(= (:tax/rate %) 19) (:taxable/taxes item))
                     (some #(= (:retention/rate %) 1) (:retentionable/retentions item))))) 
                (remove (fn [item] 
                          (and (some #(= (:tax/rate %) 19) (:taxable/taxes item))
                               (some #(= (:retention/rate %) 1) (:retentionable/retentions item))))))))

(defn -main
  [& args]
  (println "Hello Daniel, here are my solutions 📝")
  (find-invoice-items invoice))