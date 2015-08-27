(ns test-frame.db)

(def default-db
  {:name "re-frame daniel"
   :current-alert nil
   :alerts [{:alert-id 1 :desc "test1" :amount 1}
            {:alert-id 2 :desc "test2" :amount 2 :feedback-desc "invalid"}
            {:alert-id 3 :desc "test3" :amount 3}
            {:alert-id 4 :desc "test4" :amount 4}]
   })


