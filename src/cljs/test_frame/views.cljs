(ns test-frame.views
    (:require [re-frame.core :as re-frame]
              [reagent-modals.modals :as reagent-modals]
              ))

(def feedback-opitons
  [{:id 1 :label "valid alert"}
   {:id 2 :label "invalid alert"}
   {:id 3 :label "unknown alert"}])

(defn modal-dialog []
  (let [current-alert (re-frame/subscribe [:alert])]
    (fn[]
      (js/console.log (pr-str @current-alert))
;      (when (not (nil? @current-alert))
       (when true
        (js/console.log (pr-str "print modal"))
        [:div.modal.fade
         [:div.modal-dialog
          [:div.modal-content
           [:div.modal-header
            [:p "modal title"]
            ]
           [:div.modal-body
            [:p "One fine body"]
            ]
           [:div.modal-footer
            [:button.btn.btn-default "close"]
            [:button.btn.btn-primary "save change"]]]]]))))

;#(re-frame/dispatch [:alert-detail ])
(defn show-alert [alert]
                                        ;(re-frame/dispatch [:alert-detail alert])
  [:li.list-group-item.alert-item {:on-click #(reagent-modals/modal! [:div "some message to the user!" [:button.btn.btn-default "test"]])}
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
   ]
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

(defn modal-window-button []
  [:div.btn.btn-primary {:on-click #(reagent-modals/modal! [:div "some message to the user!"])}
   "My Modal"])

(defn main-panel []
  (fn []
    [:div
     [:div [show-alerts]]
     [:div [:p "modal begin"]]
;     [modal-dialog]
     [:div [:p "modal end"]]
     [reagent-modals/modal-window]
     [modal-window-button]
     ]
    ))

(defonce time-updater (js/setInterval
                       #(re-frame/dispatch [:timer (js/Date.)]) 1000))

