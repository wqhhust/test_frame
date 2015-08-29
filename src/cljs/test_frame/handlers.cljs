(ns test-frame.handlers
    (:require [re-frame.core :as re-frame]
              [test-frame.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
 :feedback
 (fn  [db [_ feedback]]
   (js/console.log "inside handle")
   (js/console.log (pr-str feedback-desc))
   (js/console.log (pr-str db))
   (assoc-in db [:alerts (keyword (str (:alert-id feedback))) :feedback-desc] (:feedback-desc feedback))))

(re-frame/register-handler
 :navigate
 (fn  [db [_ value]]
   (js/console.log "inside handle")
   (js/console.log (pr-str (:current-alert db)))
   (assoc db :current-page (+ value (:current-page db)))))
