(ns music-storm-clj.storm-listener
  (:import (com.pastorm.antlr StormMusicListener)
           (org.antlr.v4.runtime.tree ParseTreeListener)))

(defn init-model [] {})

(defn record-model! [model key value]
  (swap! model assoc key value))

(defn reset-model! [model]
  (reset! model (init-model)))

(def model (atom (init-model)))
(defn get-model [] @model)

(deftype StormListener []
  StormMusicListener
  (enterMusic [this ctx]
    (reset-model! model))
  (exitMusic [this ctx]
    )
  (enterTitle [this ctx]
    (record-model! model :title (str (.TEXT ctx))))
  (exitTitle [this ctx]
    )
  (enterAuthor [this ctx]
    (record-model! model :author (str (.TEXT ctx))))
  (exitAuthor [this ctx]
    )
  (enterMelody [this ctx]
    (record-model! model :melody (filter
                                   #(not (= " " %))
                                   (map
                                     #(.getText %)
                                     (rest (rest (.children ctx)))))))
  (exitMelody [this ctx]
    )
  ;(enterKey [this ctx]
  ;  (record-model! model :key (str (.TEXT ctx))))
  ;(exitKey [this ctx]
  ;  )

  ParseTreeListener
  (visitTerminal [this terminalNode]
    )
  (visitErrorNode [this errorNode]
    )
  (enterEveryRule [this parserRuleContext]
    )
  (exitEveryRule [this parserRuleContext]
    ))
