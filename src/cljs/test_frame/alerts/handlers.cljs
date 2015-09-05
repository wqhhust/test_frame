(ns test-frame.alerts.handlers
    (:require [re-frame.core :as re-frame]
              [ajax.core :refer [GET POST]]
              [test-frame.alerts.db :as db]))

(re-frame/register-handler
 :initialize-login-db
 (fn  [_ _]
   db/default-db))

(defn handler [response]
  (.log js/console (str response)))

(def host "http://10.0.0.10:6003/")

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(re-frame/register-handler
 :feedback
 (fn  [db [_ feedback]]
   (let [path [:alerts (keyword (str (:alert-id feedback))) :feedback-desc]
         feedback-desc (:feedback-desc feedback)]
     (js/console.log "inside handle feedback")
     (ajax.core/POST (str host "alert/" (:alert-id feedback))
                     {:params feedback
                      :format :edn
                      :handler handler
                      :error-handler error-handler})
     (assoc-in db path feedback-desc))))

(re-frame/register-handler
 :navigate
 (fn  [db [_ value]]
   (js/console.log "inside handle navigate")
   (js/console.log (pr-str (:current-alert db)))
   (assoc db :current-page (+ value (:current-page db)))))

(re-frame/register-handler
 :load-ajax-data
 (fn  [db [_ value]]
   (ajax.core/GET (str host "alerts")
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
   db))
