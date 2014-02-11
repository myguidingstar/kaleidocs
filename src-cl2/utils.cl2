(defn end-with? [x s]
  (== s (.match x (+ s "$"))))

(defn format-number [x]
  ;; TODO: bug in core-cl2's partition
  (.join (remove nil? (partition 3 x)) \.))

(defn format-amount&add-iw! [obj]
  (doseq [[k v] obj]
    (when (amount-field? k)
      (set! (get obj k) (format-number v))
      (set! (get obj
                 (+ k (:amount-iw-suffix @config)))
            (amount-in-words v))))
  obj)

(defn index->id! [coll]
  (doseq [[k v] coll]
    (set! (get v "ID") (inc (parseInt k))))
  coll)

(defn export-records [records]
  (index->id!
   (map #(merge (format-amount&add-iw! (fields->map (:fields %)))
                {:id (:id %)})
        records)))

(defn get-single-templates []
  (map #(:filename %) (find-entities templates {:type "single"})))

(defn get-multiple-templates []
  (map #(:filename %) (find-entities templates {:type "multiple"})))
