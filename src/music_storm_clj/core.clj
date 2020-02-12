(ns music-storm-clj.core
  (:require [clojure.java.io :as io]
            music-storm-clj.storm-listener
            [dynne.sampled-sound :refer :all]
            [clojure.core.async :as async])
  (:use [music-storm-clj.storm-listener :only (get-model)])
  (:import (com.pastorm.antlr StormMusicLexer StormMusicParser)
           (org.antlr.v4.runtime CharStreams CommonTokenStream)
           (org.antlr.v4.runtime.tree ParseTreeWalker)
           (music_storm_clj.storm_listener StormListener)))

(def data-file (io/resource "test.txt"))

(def char-stream (. CharStreams (fromString (slurp data-file))))
(def lexer (StormMusicLexer. char-stream))
(def tokens (CommonTokenStream. lexer))
(def parser (StormMusicParser. tokens))
(def walker (ParseTreeWalker.))
(def listener (StormListener.))

(def music-channel (async/chan))
(defn note->freq [note n]
  (let [note-lower (.toLowerCase note)
        f0 (cond
             (= note-lower "c") 261.63
             (= note-lower "d") 293.66
             (= note-lower "e") 329.63
             (= note-lower "f") 349.23
             (= note-lower "g") 392.00
             (= note-lower "a") 440
             (= note-lower "b") 493.88
             :else 0)]
    (* f0 (Math/pow 2 (- n 4)))))

(defn process [elt]
  (play (sinusoid (:duration elt) (:frequency elt))))

(defn start-consumers [n-consumers]
  (dotimes [_ n-consumers]
    (async/thread
      (while true
        (let [elt (async/<!! music-channel)]
          (process elt))))))

(defn play-model [model]
  (let [{:keys [meta melody]} model]
    (doseq [elt melody]
      (let [link false
            dot false
            factor (cond link 1 dot 1.5 :else 2)
            duration (/ (* (:type elt) (/ 60 (:tempo meta))) factor)
            time-sleep (* factor duration)]
        (async/>!! music-channel {
                                  :duration  duration
                                  :frequency (note->freq (:note elt) (:n elt))
                                  })
        (Thread/sleep (* 1000 time-sleep))))))

(defn -main []
  (println "")
  (.walk walker listener (.music parser))
  (start-consumers 1)
  (println (get-model))
  (play-model (get-model)))
