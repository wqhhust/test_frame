(ns test-frame.login.views
    (:require [re-frame.core :as re-frame]
              [reagent.core  :as reagent :refer [atom]]
              [reagent-modals.modals :as reagent-modals]))

(defn value-of [element]
  (-> element .-target .-value))

(def user-pwd (atom {}))

(defn input [attrs field]
  [:div
   [:label {:for (:field attrs) :class "control-label"} (:label attrs)]
   [:input {:type (or (:type attrs) "text")
            :class "form-control"
            :id (:field attrs)
            :name (:field attrs)
            :on-change (fn[x] (swap! user-pwd assoc field (value-of x)))
            :placeholder (:label attrs)}]])

(defn login []
  [:div {:class "row"}
   [:div {:class "col-sm-9 col-lg-10"} [:p {} "Login to acme store to get all the benefits..."]]
   [:div {:class "col-sm-3 col-lg-2"}
     [:div {:class "form-group"} (input {:field "username" :label "Username"} :username)]
     [:div {:class "form-group"} (input {:field "password" :label "Password" :type "password"} :password)]
    [:div {:class "form-group"} [:button {:class "btn btn-default" :on-click (fn[x]
                                                                               (js/console.log "inside submit")
                                                                               (js/console.log (pr-str @re-frame.db/app-db))
                                                                               (re-frame/dispatch [:login @user-pwd])
                                                                               )} "Login"]]]]
  )

(defn main-panel []
  (js/console.log (pr-str @re-frame.db/app-db))
  (login)
  )
