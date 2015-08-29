(ns test-frame.db)

(def default-db
  {:name "re-frame daniel"
   :current-alert nil
   :current-page 0
   :page-size 10
   :alerts (into {} (map (fn[x] {(keyword (str x))
                                 {:alert-id x
                                  :desc (str "test" x)
                                  :amount x
                                  }})(range 1 3)))
   })

