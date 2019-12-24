(ns web-scrapper.info
  (:require
   [web-scrapper.http :refer [grab]]))

(defn extract-title [post q]
  (let [ancher (first (grab post q))
        ret {:title (-> ancher :content first)
             :href (-> ancher :attrs :href)}]
    ret))

(defn extract [body {:keys [root title]}]
  (map #(extract-title % title) (grab body root)))

