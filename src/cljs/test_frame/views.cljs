(ns test-frame.views
    (:require [re-frame.core :as re-frame]
              [re-com.core :as re-com]))

(def feedback-opitons
  [{:id 1 :label "valid alert"}
   {:id 2 :label "invalid alert"}
   {:id 3 :label "unknown alert"}])

(defn dialog-markup [alert-id]
  [re-com/v-box
   :padding  "10px"
   :style    {:background-color "cornsilk"}
   :children [[re-com/title :label (str "Welcome to MI6. Please log in " alert-id) :level :level2]]
   ]
  )

(defn modal-dialog []
  (let [current-alert (re-frame/subscribe [:alert])]
    (fn[]
      (js/console.log (pr-str @current-alert))
      (when (not (nil? @current-alert))
           [re-com/modal-panel
            :backdrop-color   "grey"
            :backdrop-opacity 1.0
            :style            {:font-family "Consolas"}
            :child            [show-detail @current-alert]]
           )
      )))

(defn title []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      (js/console.log (pr-str @name))
      [re-com/title
       :label (str "Hello from " @name)
       :level :level1])))

;#(re-frame/dispatch [:alert-detail ])
(defn show-alert [alert]
  [:li.list-group-item.alert-item {:on-click #(re-frame/dispatch [:alert-detail alert])}
   [:p.list-group-item-text (:desc alert)]
   [:i.fa.fa-chevron-right.pull-right]
   [:p.list-group-item-text (:amount alert)]
   [:p.list-group-item-text (:alert-id alert)]
   ])

(defn show-detail [alert]
  [:li.list-group-item.alert-item
   [:p.list-group-item-text (:desc alert)]
   [:p.list-group-item-text (:amount alert)]
   [:p.list-group-item-text (:alert-id alert)]
   [re-com/single-dropdown
    :choices feedback-opitons
    :model (:feedback-id alert)
    :width "300px"
    :placeholder "please choose a feedback"
    :on-change #(1)]]
  )

(defn show-alerts []
  (let [alerts (re-frame/subscribe [:alerts])]
    (fn []
      (js/console.log (pr-str @alerts))
      [:div#alerts.list-group
        (map show-alert @alerts)
       ])))

(defn clock
  []
  (let [timer (re-frame/subscribe [:timer])]
    (fn clock-render
      []
      [:div.example-clock @timer]
      )))

(defn main-panel []
  (fn []
    [re-com/v-box
     :height "100%"
     :children [[title] [modal-dialog] [show-alerts] [clock] ]]))

(defonce time-updater (js/setInterval
                       #(re-frame/dispatch [:timer (js/Date.)]) 1000))

