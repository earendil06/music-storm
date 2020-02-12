(defproject music-storm-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-antlr "0.2.5"]
                 [org.craigandera/dynne "0.4.1"]
                 [org.clojure/core.async "0.7.559"]]
  :plugins [[lein-antlr "0.3.0"]]
  :repl-options {:init-ns music-storm-clj.core}

  :main music-storm-clj.core

  :antlr-src-dir "src/antlr"
  :antlr-dest-dir "gen-src/com.pastorm.antlr"
  :antlr-options {:Werror true :package "com.pastorm.antlr"}

  :java-source-paths ["gen-src"])
