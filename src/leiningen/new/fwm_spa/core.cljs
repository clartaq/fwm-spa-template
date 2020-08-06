(ns ^:figwheel-hooks {{namespace}}
  (:require [goog.dom :as gdom]
            [reagent.core :as r :refer [atom]]
            [reagent.dom :as rdom]))

;; Just used to demonstrate testing.
(defn square [a] (* a a))

(println " This text is printed from src/fwm_spa/core.cljs.\n"
         "Go ahead and edit it and see reloading in action.")

;; Define your app data so that it doesn't get over-written on reload.
(defonce app-state (r/atom {:update-num     0
                            :h4-line-height "1.75rem"
                            :time-str       "00:00:00 PM"
                            :time-color     "green"}))

(def colors ["cyan" "darkorange" "red" "green" "blue" "black" "brown" "orange"
             "darkorchid" "chartreuse" "lightblue" "darkpink" "plum" "indigo"])

(defn random-color
  "Return a random color from the vector of available colors."
  []
  (get colors (rand-int (count colors))))

(defn format-time-str
  "Get the current time and return it formatted as a nice pretty string."
  []
  (let [now (js/Date.)
        hours (.getHours now)
        pm? (> hours 11)
        hour (if pm? (- hours 12) #_otherwise hours)
        suffix (if pm? "PM" #_otherwise "AM")
        minutes (.getMinutes now)
        seconds (.getSeconds now)]
    (str hour ":" minutes ":" seconds " " suffix)))

(defn start-time-updater [state]
  (js/setInterval (fn my-updater []
                    (swap! state update :update-num inc)
                    (swap! state update :time-str format-time-str)
                    (when (zero? (mod (:update-num @state) 3))
                      (swap! state update :time-color random-color))) 1000))

(defn home [state]
  "Return a function that will render our home page."
  (start-time-updater state)
  (fn [state]
    [:div {:style {:margin (:body-margin @state)}}
     [:h4 {:style {:line-height (:h4-line-height @state)}}
      "This system says the time is:"
      [:br]
      [:span {:style {:color (:time-color @state)}} (str (:time-str @state))]]
     [:p "This is update: " (:update-num @state)]]))


(defn increment-update-num []
  (swap! app-state #(update % :update-num inc)))

(defn reload [el]
  (rdom/render [home app-state] el))

(defn mount-app-element []
  (when-let [el (gdom/getElement "app")]
    (reload el)))

;; Conditionally start your application based on the presence of an "app"
;; element. This is particularly helpful for testing this ns without launching
;; the app.
(mount-app-element)

;; Specify reload hook with ^;after-load metadata.
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; Optionally touch your app-state to force re-rendering depending on
  ;; your application.
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
