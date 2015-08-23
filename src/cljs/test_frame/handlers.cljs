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
