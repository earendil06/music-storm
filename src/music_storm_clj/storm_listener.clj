(ns music-storm-clj.storm-listener
  (:import (com.pastorm.antlr StormMusicListener)
           (org.antlr.v4.runtime.tree ParseTreeListener)))

(deftype StormListener []
  StormMusicListener
  (enterMusic [this ctx]
    (println "hello world"))
  (exitMusic [this ctx]
    )
  (enterTitle [this ctx]
    )
  (exitTitle [this ctx]
    )
  (enterAuthor [this ctx]
    )
  (exitAuthor [this ctx]
    )
  (enterMelody [this ctx]
    )
  (exitMelody [this ctx]
    )

  ParseTreeListener
  (visitTerminal [this terminalNode]
    )
  (visitErrorNode [this errorNode]
    )
  (enterEveryRule [this parserRuleContext]
    )
  (exitEveryRule [this parserRuleContext]
    ))
