(ns web-scrapper.core
  (:require
   [web-scrapper.info :as info]
   [web-scrapper.storage :as storage]
   [web-scrapper.http :refer [request-multi]]
   [web-scrapper.urls :refer [news gen-urls]])
  (:gen-class))

(def p clojure.pprint/pprint)

(defn- date []
  (.format
   (java.text.SimpleDateFormat. "yyyy-MM-dd")
   (new java.util.Date)))

(defn crawl [entry]
  (let [query (get-in entry [1 :query])
        urls (gen-urls entry)]
    (map #(info/extract % query) (request-multi urls))))

(defn -main [& args]
  (let [rows (flatten (map crawl news))]
    (->> rows
         (map #(assoc % :created_at (date)))
         (storage/write! :test))))
