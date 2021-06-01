(defproject credit-card "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clojure.java-time "0.3.1"]
                 [com.datomic/datomic-pro "1.0.6269"]
                 [com.stuartsierra/component "1.0.0"]
                 [nubank/matcher-combinators "3.1.4"]]

  :profiles {:repl-start {:injections   [(require '[credit-card.server :as s] '[credit-card.service])
                                         (s/run-dev)]
                          :repl-options {:prompt  #(str "[credit-card] " % "=> ")
                                         :init-ns user}
                          :test-paths   ^:replace []}}

  :main ^{:skip-aot true} credit-card.server)