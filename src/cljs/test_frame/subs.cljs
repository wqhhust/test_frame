(ns test-frame.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :name
 (fn [db _]
   (reaction (:name @db))))

(re-frame/register-sub
 :alert
 (fn [db _]
   (js/console.log (pr-str @db))
   (reaction (:current-alert @db))))

(re-frame/register-sub
 :alerts
 (fn [db _]
   (reaction (:alerts @db))))

(re-frame/register-sub
 :timer
 (fn
   [db _]                       ;; db is the app-db atom
   db))    ;; wrap the computation in a reaction
