(ns test-frame.views
    (:require [re-frame.core :as re-frame]
              [reagent.core  :as reagent :refer [atom]]
              [reagent-modals.modals :as reagent-modals]
              ))

(defn value-of [element]
  (-> element .-target .-value))

(defn show-alert [alert]
  [:li.list-group-item.alert-item.pointer {:on-click #(reagent-modals/modal! [show-detail alert])}
   [:p.list-group-item-text (:desc alert)]
   [:i.fa.fa-chevron-right.pull-right]
   [:p.list-group-item-text (:amount alert)]
   [:p.list-group-item-text (:alert-id alert)]
   ])

(defn show-detail [alert]
  (let [new-feedback (atom (:feedback-desc alert))
        {:keys [feedback-desc desc amount alert-id]} alert
        ]
    [:li#alert-detail.list-group-item.alert-item
     [:p.list-group-item-text desc]
     [:p.list-group-item-text amount]
     [:p.list-group-item-text alert-id]
     [:div
      [:select.select-picker
       {:default-value feedback-desc :on-click #(reset! new-feedback (value-of %))}
       [:option ""]
       [:option "valid"]
       [:option "invalid"]
       [:option "unkown"]]]
     [:div.btn-group
      [:div.btn.btn-default {:data-dismiss "modal"} "cancel"]
      [:div.btn.btn-default {:data-dismiss "modal"
                             :on-click #(re-frame/dispatch [:feedback {:feedback-desc @new-feedback :alert-id alert-id} ])
                             } "save" ]
      ]]))

(defn show-alerts []
  (let [alerts-info (re-frame/subscribe [:alerts])]
    (fn []
      (js/console.log (pr-str @alerts-info))
      (let [{:keys [current-page page-size alerts]} @alerts-info
            alerts-to-show (->> alerts (drop (* page-size current-page)) (take page-size))]
        [:div
         [:div#alerts.list-group (map show-alert alerts-to-show)]
         [:div
          [:nav
           [:ul.pager
            (js/console.log "current page *****" (pr-str @alerts-info))
            (js/console.log "current page *****" current-page)
            (when (> current-page 0)
              [:li [:a {:href "#" :on-click #(re-frame/dispatch [:navigate -1])} "Previous"]])
            (when (< (* (inc current-page) page-size) (count alerts))
              [:li [:a {:href "#" :on-click #(re-frame/dispatch [:navigate 1])} "Next"]])]]]]))))

(defn main-panel []
  (fn []
    [:div
     [:div [show-alerts]]
     [reagent-modals/modal-window]
     ]))
