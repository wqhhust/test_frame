(ns test-frame.alerts.views
    (:require [re-frame.core :as re-frame]
              [reagent.core  :as reagent :refer [atom]]
              [reagent-modals.modals :as reagent-modals]))

(defn value-of [element]
  (-> element .-target .-value))

(defn show-detail [alert]
  (let [new-feedback (atom (:feedback-desc alert))
        {:keys [feedback-desc desc amount alert-id]} alert]
    (js/console.log "render in show-detail outside of fun ")
    (fn[]
      (js/console.log "render in show-detail inside of fun")
      [:li#alert-detail.list-group-item.alert-item
       [:p.list-group-item-text desc]
       [:p.list-group-item-text amount]
       [:p.list-group-item-text alert-id]
       [:p.list-group-item-text @new-feedback]
       [:div#chart-area]
       [:div
        [:select.select-picker
         {:default-value feedback-desc :on-change #(reset! new-feedback (value-of %))}
         [:option ""]
         [:option "valid"]
         [:option "invalid"]
         [:option "unkown"]]]
       [:div.btn-group
        [:div.btn.btn-default {:data-dismiss "modal"} "cancel"]
        [:div.btn.btn-default {:data-dismiss "modal"
                               :on-click (fn[x]
                                           (re-frame/dispatch [:feedback {:feedback-desc @new-feedback :alert-id alert-id} ])
                                           )
                               } "save" ]
        ]])))

(defn plot-chart []
  (.Donut js/Morris (clj->js {:element "chart-area"
                              :data [{:label "Download Sales" :value 12}
                                     {:label "In-Store Sales" :value 30}
                                     {:label "Mail-Order Sales" :value 20}]})))

(defn show-detail-with-chart [alert]
  (reagent/create-class
   {
   ;; :component-did-mount #(js/console.log "inside component-did-amount")
    :component-did-mount plot-chart
    :display-name "detail-with-chart"
    :reagent-render (fn [alert] (show-detail alert))
    }
   )
  )

(defn show-alert [alert]
  ^{:key (:alert-id alert)} [:li.list-group-item.alert-item.pointer {:on-click #(reagent-modals/modal! [show-detail-with-chart alert])}
   [:p.list-group-item-text (:desc alert)]
   [:i.fa.fa-chevron-right.pull-right]
   [:p.list-group-item-text (:amount alert)]
   [:p.list-group-item-text (:alert-id alert)]
   ])

(defn show-alerts []
  (let [alerts-info (re-frame/subscribe [:alerts])]
    (js/console.log "render in show-alerts outside of fun")
    (fn []
      (js/console.log "render in show-alerts inside of fun")
      (js/console.log (pr-str @alerts-info))
      (let [{:keys [current-page page-size alerts]} @alerts-info
            alerts-to-show (->> alerts (drop (* page-size current-page)) (take page-size))]
        [:div
         [:div#alerts.list-group (map show-alert alerts-to-show)]
         [:div.btn.btn-default {:on-click #(re-frame/dispatch [:load-ajax-data])} "load data"]
         [:div
          [:nav
           [:ul.pager
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
