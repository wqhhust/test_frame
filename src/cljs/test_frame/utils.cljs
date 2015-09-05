(ns test-frame.utils
    (:require [patisserie.core :as cookie]))

(defn get-auth-token []
  (let [login-token (cljs.reader/read-string (cookie/cookie "token"))
        token-pair (get login-token "token-pair" {:msg "not a valid token-pair"})
        auth-token (get token-pair "auth-token" nil)]
    auth-token
    )
  )






