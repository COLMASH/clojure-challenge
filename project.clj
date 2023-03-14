(defproject clojure-dataico "0.1.0-SNAPSHOT"
  :description "Getting started with Clojure"
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main ^:skip-aot clojure-dataico.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
