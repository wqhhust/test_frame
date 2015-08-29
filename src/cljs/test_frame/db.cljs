(ns test-frame.db)

(def default-db
  {:name "re-frame daniel"
   :current-alert nil
   :current-page 0
   :page-size 4
   :alerts [{:alert-id 1 :desc "test1" :amount 1}
            {:alert-id 2 :desc "test2" :amount 2 :feedback-desc "invalid"}
            {:alert-id 3 :desc "test3" :amount 3}
            {:alert-id 4 :desc "test5" :amount 4}]
   })


