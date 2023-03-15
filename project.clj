(defproject clojure-dataico "0.1.0-SNAPSHOT"
  :description "Getting started with Clojure"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/data.json "2.4.0"]
                 [clj-time "0.15.2"]]
  :main ^:skip-aot clojure-dataico.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
