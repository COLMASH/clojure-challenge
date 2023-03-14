(ns clojure-dataico.core
  (:gen-class))

(use 'clojure-dataico.invoice)

(defn -main
  [& args]
  (println "Hello Daniel, here are my solutions, thank you 🤝🏻"))

(defn find-invoice-items [invoice]
  (println (->> invoice
       :invoice/items
       (filter (fn [item]
                 (some #(= (:tax/category %) :iva) (:taxable/taxes item))))
       (filter (fn [item]
                 (not (some #(= (:retention/category %) :ret_fuente) (:retentionable/retentions item)))))
       (filter (fn [item]
                 (or (some #(= (:tax/rate %) 19) (:taxable/taxes item))
                     (some #(= (:retention/rate %) 1) (:retentionable/retentions item))))))))

(find-invoice-items invoice)