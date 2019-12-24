(ns web-scrapper.urls)

(defn domain [entry-index]
  (str "https://" (name entry-index)))

(defn query-url [[index {{:keys [args]} :nav}]]
  (str (domain index) "/" (first args)))

(defn gen-urls [[_ {{:keys [args clb]} :nav} :as entry]]
  (let [[_ start end] args
        root-url (domain (first entry))
        pagination (for [page (range start (+ end 1))]
                     (if (nil? clb)
                       (str (query-url entry) page)
                       (clb (query-url entry) page)))]
    (cons root-url pagination)))



(def news
  {:news.ycombinator.com
   {:nav {:args ["news?p=" 2 10]}
          ;; :clb #(str %1 %2 "/")
    :query {:root [:.athing]
            :title [:.storylink]
            :size nil}}})

    
            



