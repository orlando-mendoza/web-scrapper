(ns web-scrapper.storage
  (:require
   [clojure.edn :as edn]))

(defn- locate-db [db-name]
  (str "./database/" (name db-name) ".edn"))

(defn read-db [db-name]
  (let [data (edn/read-string (slurp (locate-db db-name)))]
    (if (nil? data) '() data)))

(defn- db-has? [db index value]
  (some #(= (index %) value) db))

(defn- unique-rows [db rows]
  (filter #(not (db-has? db :href (:href %))) rows))

(defn- write-db! [db-name data]
  (spit (locate-db db-name) (apply list data)))

(defn write! [db-name rows]
  (let [db (read-db db-name)
        urows (unique-rows db rows)]
    (if (empty? urows)
      nil
      (write-db! db-name (concat urows db)))))