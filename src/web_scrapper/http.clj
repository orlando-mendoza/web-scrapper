(ns web-scrapper.http
  (:require
   [clj-http.client :as client]
   [net.cgrand.enlive-html :as html])
  (:gen-class))

(defn request [url]
  (-> url client/get :body html/html-snippet))

(defn grab [content query]
  (html/select content query))

(defn request-multi [urls]
  (pmap request urls))