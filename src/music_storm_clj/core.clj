(ns music-storm-clj.core
  (:require [clojure.java.io :as io]
            music-storm-clj.storm-listener
            [dynne.sampled-sound :refer :all])
  (:use [music-storm-clj.storm-listener :only (get-model)])
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

(defn note->freq [note]
  (cond
    (= note "c") 261.63
    (= note "d") 293.66
    (= note "e") 329.63
    (= note "f") 349.23
    (= note "g") 392.00
    (= note "a") 440
    (= note "b") 493.88))

(defn play-melody [melody]
  (doseq [note melody]
    (play (sinusoid 0.25 (note->freq note)))
    (Thread/sleep 250)))

(defn -main []
  (.walk walker listener (.music parser))
  (let [melody (:melody (get-model))]
    (println melody)
    (play-melody melody)))
