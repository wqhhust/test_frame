(ns test-frame.handlers
    (:require [re-frame.core :as re-frame]
              [ajax.core :refer [GET POST]]
              [test-frame.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
 :feedback
 (fn  [db [_ feedback]]
   (js/console.log "inside handle")
   (ajax.core/POST (str "http://localhost:6003/alert/" (:alert-id feedback))
                   {:params {:a 1 :b 2}
                    :handle #(js/console.log (pr-str %))
                    :error-handler #(js/console.log (pr-str %))})
   (assoc-in db [:alerts (keyword (str (:alert-id feedback))) :feedback-desc] (:feedback-desc feedback))))

(re-frame/register-handler
 :navigate
 (fn  [db [_ value]]
   (js/console.log "inside handle")
   (js/console.log (pr-str (:current-alert db)))
   (assoc db :current-page (+ value (:current-page db)))))

(re-frame/register-handler
 :load-ajax-data
 (fn  [db [_ value]]
   (ajax.core/GET "http://localhost:6003/alerts"
                  {:handler #(re-frame/dispatch [:process-response %1])
                   :error-handler #(re-frame/dispatch [:bad-response %1])})
   (assoc db :loading? true)
   ))

(re-frame/register-handler
 :process-response
 (fn [db [_ response]]
   (js/console.log "good response, the response is:" (pr-str response))
   (-> db
       (assoc :loading? false)
       (assoc :alerts (into {} (map (fn[x] {(->> x :alert-id str keyword) x}) response)))
       )))

(re-frame/register-handler
 :bad-response
 (fn [db [_ response]]
   (js/console.log "bad response, can't get the correct data" (pr-str response))
   db
   )
 )
