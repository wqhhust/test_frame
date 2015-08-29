(ns test-frame.handlers
    (:require [re-frame.core :as re-frame]
              [test-frame.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
 :alert-detail
 (fn  [db [_ alert]]
   (js/console.log (pr-str alert))
   (js/console.log (pr-str db))
   (assoc db :current-alert alert)))

(re-frame/register-handler
 :timer
 (fn
   ;; the first item in the second argument is :timer the second is the
   ;; new value
   [db [_ value]]
   (assoc db :timer value)))    ;; return the new version of db



(re-frame/register-handler
 :feedback
 (fn  [db [_ feedback-desc]]
   (js/console.log "inside handle")
   (js/console.log (pr-str feedback-desc))
   (js/console.log (pr-str db))
   (assoc db :current-alert feedback-desc)))

(re-frame/register-handler
 :navigate
 (fn  [db [_ value]]
   (js/console.log "inside handle")
   (js/console.log (pr-str db))
   (assoc db :current-page (+ value (:current-page db)))))
