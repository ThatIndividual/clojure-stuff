(ns clojure-noob.core
  (:gen-class))

(def asym-alien-body-parts [{:name "head" :size 5}
                             {:name "mouth" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-eye" :size 1}
                             {:name "first-upper-tentacle" :size 3}
                             {:name "first-lower-tentacle" :size 3}
                             {:name "thorax" :size 10}
                             {:name "abdomen" :size 6}
                             {:name "first-knee" :size 2}
                             {:name "first-thigh" :size 4}
                             {:name "first-lower-leg" :size 3}
                             {:name "first-achilles" :size 1}
                             {:name "first-foot" :size 2}])

(defn matching-parts
  [{:keys [name size] :as part}]
    (if (clojure.string/starts-with? name "first-")
      (let [part-name-root (clojure.string/replace name #"first-" "")]
        [part,
         {:name (str "second-" part-name-root)
          :size size}
         {:name (str "third-" part-name-root)
          :size size}
         {:name (str "fourth-" part-name-root)
          :size size}
         {:name (str "fifth-" part-name-root)
          :size size}])
      [part]))

(defn symmetrize-body-parts
  [parts]
  (reduce (fn [final-parts part]
            (into final-parts (matching-parts part)))
          []
          parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (hit asym-alien-body-parts)))
