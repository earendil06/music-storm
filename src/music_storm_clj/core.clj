(ns music-storm-clj.core
  (:require [clojure.java.io :as io] music-storm-clj.storm-listener)
  (:import (com.pastorm.antlr StormMusicLexer StormMusicParser)
           (org.antlr.v4.runtime CharStreams CommonTokenStream)
           (org.antlr.v4.runtime.tree ParseTreeWalker)
           (music_storm_clj.storm_listener StormListener)))

(def data-file (io/resource
                 "test.txt"))

(def char-stream (. CharStreams (fromString (slurp data-file))))
(def lexer (StormMusicLexer. char-stream))
(def tokens (CommonTokenStream. lexer))
(def parser (StormMusicParser. tokens))
(def walker (ParseTreeWalker.))
(def listener (StormListener.))

(defn -main []
  (.walk walker listener (.music parser)))
