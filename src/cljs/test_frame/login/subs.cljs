(ns test-frame.login.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))


(re-frame/register-sub
 :login
 (fn [db _]
   (reaction (:active-panel @db))
   ))
