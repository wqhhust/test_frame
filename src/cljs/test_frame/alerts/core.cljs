(ns test-frame.alerts.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [test-frame.alerts.handlers]
              [test-frame.alerts.subs]
              [test-frame.alerts.views :as views]))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
alerts
