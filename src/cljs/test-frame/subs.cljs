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
   (reaction {:current-page (:current-page @db)
              :alerts (sort-by :alert-id (mapv (fn[[k v]] v) (:alerts @db)))
              :page-size (:page-size @db)
              })))
