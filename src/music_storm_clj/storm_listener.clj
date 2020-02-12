(ns music-storm-clj.storm-listener
  (:import (com.pastorm.antlr StormMusicListener StormMusicParser$AuthorContext StormMusicParser$TitleContext StormMusicParser$MelodyContext StormMusicParser$KeyContext StormMusicParser$TempoContext StormMusicParser$KeyChoiceContext)
           (org.antlr.v4.runtime.tree ParseTreeListener TerminalNode)))

(defn init-model [] {})

(defn record! [model path value]
  (swap! model assoc-in path value))

(defn reset-model! [model]
  (reset! model (init-model)))

(def model (atom (init-model)))

(defn get-model [] @model)

(defn extract-author [^StormMusicParser$AuthorContext ctx]
  (clojure.string/join " " (.TEXT ctx)))

(defn extract-title [^StormMusicParser$TitleContext ctx]
  (clojure.string/join " " (.TEXT ctx)))

(defn extract-key [^StormMusicParser$KeyChoiceContext ctx]
  (.getText ctx))

(defn extract-melody [^StormMusicParser$MelodyContext ctx]
  (map (fn [x]
         (let [decimal (= "-" (subs x 0 1))
               offset (if decimal 1 0)]

           {:n    (Integer/parseInt (subs x (+ offset 2) (+ offset 3)))
            :note (subs x (+ offset 1) (+ offset 2))
            :type (if decimal
                    (/ 1 (Integer/parseInt (subs x (+ offset 0) (+ offset 1))))
                    (Integer/parseInt (subs x (+ offset 0) (+ offset 1))))}))
       (filter
         #(not (= " " %))
         (map
           #(.getText ^TerminalNode %)
           (rest (rest (.children ctx)))))))



(defn extract-tempo [^StormMusicParser$TempoContext ctx]
  (Integer/parseInt (str (.INT ctx))))

(deftype StormListener []
  StormMusicListener
  (enterMusic [this ctx]
    (reset-model! model))
  (exitMusic [this ctx]
    )
  (enterTitle [this ctx]
    (record! model [:meta :title] (extract-title ctx))
    )
  (exitTitle [this ctx]
    )
  (enterAuthor [this ctx]
    (record! model [:meta :author] (extract-author ctx))
    )
  (exitAuthor [this ctx]
    )
  (enterMelody [this ctx]
    (record! model [:melody] (extract-melody ctx))
    )
  (exitMelody [this ctx]
    )
  (enterKey [this ctx]
    )
  (exitKey [this ctx]
    )
  (enterExpr [this ctx]
    )
  (exitExpr [this ctx]
    )
  (enterTempo [this ctx]
    (record! model [:meta :tempo] (extract-tempo ctx))
    )

  (exitTempo [this ctx]
    )
  (enterKeyChoice [this ctx]
    (record! model [:meta :key] (extract-key ctx)))
  (exitKeyChoice [this ctx]
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
