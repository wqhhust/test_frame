(ns test-frame.login.handlers
    (:require [re-frame.core :as re-frame]
              [ajax.core :refer [GET POST]]
              [patisserie.core :as cookie]
              [test-frame.login.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(def host "http://10.0.0.3:6001/")

(re-frame/register-handler
 :login
 (fn  [db [_ value]]
   (js/console.log (pr-str value))
   (ajax.core/POST (str host "create-auth-token")
                   {:params value
                    :format :json
                    :handler #(re-frame/dispatch [:process-login-response %1])
                    :error-handler #(re-frame/dispatch [:bad-login-response %1])})
   ))

(re-frame/register-handler
 :process-login-response
 (fn [db [_ response]]
   (cookie/set-cookie! "token" response)
   db
   ))

(re-frame/register-handler
 :bad-login-response
 (fn [db [_ response]]
   (js/console.log "bad response, can't get the correct data" (pr-str response))
   db
   )
 )
