(ns test-frame.core
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [patisserie.core :as cookie]
              [test-frame.alerts.handlers]
              [test-frame.login.handlers]
              [test-frame.alerts.subs]
              [test-frame.login.subs]
              [test-frame.login.views :as login-views]
              [test-frame.alerts.views :as alerts-views]))

(defn high-level-view []
  (let [panel (re-frame/subscribe [:login])]
    (fn []
      [:div
       [:div "panel is " (pr-str @panel)]
       [:div
        (condp = @panel
          :login  [login-views/main-panel]
          :alerts [alerts-views/main-panel]
          [login-views/main-panel])
                                        ;(if (cookie/cookie "token") [alerts-views/main-panel] [login-views/main-panel])
        ]
       ]
      )))

(defn mount-root []
  (reagent/render [high-level-view]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))


