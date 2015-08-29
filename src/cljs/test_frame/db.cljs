(ns test-frame.db)

;; to check the db status use:  @re-frame.db/app-db
(def default-db
  {:name "re-frame daniel"
   :current-page 0
   :page-size 10
   :alerts (into {} (map (fn[x] {(keyword (str x))
                                 {:alert-id x
                                  :desc (str "test" x)
                                  :amount x
                                  }})(range 1 40)))
   })


