(ns kaleidocs.core
  (:require [noir.io :as io]
            [kaleidocs.merge :refer [merge-doc]]
            [kaleidocs.models :refer :all]
            [noir.response :as response]
            [cheshire.core :refer [generate-string parse-string]]
            [clojure.string :as str]
            [taoensso.timbre :as timbre]
            [kaleidocs.convert :refer :all]
            [compojure.core :refer [GET POST DELETE defroutes]]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]])
  (:gen-class))
  )

(def templates-dir "templates")
(def output-dir "output")

(declare broadcast)

(defn odf-template [s]
  (str templates-dir "/" (odf-filename s)))

(defn dev? [args] (some #{"-dev"} args))

(defn port [args]
  (if-let [port (first (remove #{"-dev"} args))]
    (Integer/parseInt port)
    3000))

(defn find-order-key
  [s]
  (second (re-find #"sorting\[([a-zA-Z]+)\]" s)))

(defn find-order-kv
  [params]
  (let [order-key (some find-order-key (keys params))
        order-value (when order-key
                      (case (get params (format "sorting[%s]" order-key))
                        "asc" :ASC
                        "desc" :DESC
                        nil))]
    [(keyword order-key) order-value]))

(defn find-filter-key
  [s]
  (second (re-find #"filter\[([a-zA-Z]+)\]" s)))

(defn find-filter-kvs
  [params]
  (let [filter-keys (remove nil? (map find-filter-key (keys params)))]
    (map (fn [k]
           [(keyword k)
            (get params (format "filter[%s]" k))])
         filter-keys)))

(defn parse-int-or [s & [default]]
  (try (Integer/parseInt s)
       (catch Throwable e default)))

(defn to-list [s]
  (->> #","
       (clojure.string/split s)
       (map parse-int-or )
       (remove nil?)))

(defroutes my-routes
  (GET "/records" [ids]
       (when (seq ids)
         (generate-string
          (query-records-by-ids (to-list ids)))))
  (GET "/api/:entity-type" [entity-type page count & other-params]
       (let [[order-key order-value] (find-order-kv other-params)
             filter-kvs (find-filter-kvs other-params)
             page (parse-int-or page 1)
             count (parse-int-or count 10)]
         (do (timbre/info
              (format "type: %s; page %s; count %s"
                      entity-type page count))
             (timbre/info
              (format "order-key %s and order-value %s"
                      order-key order-value))
             (timbre/info
              (format "filter-kvs %s"
                      (pr-str filter-kvs))))
         (generate-string
          (fetch-entities
           entity-type page count
           order-key order-value
           filter-kvs))))
  (POST "/api/:entity-type" [entity-type :as {data :body}]
        (let []
          (timbre/info "Got a post to" entity-type (pr-str data))
          (if (integer? (:id data))
            (update-entity entity-type (:id data) data)
            (add-entity entity-type data))
          {:status 200}))
  (DELETE ["/api/:entity-type/:id" :id #"[0-9]+"] [entity-type id]
          (timbre/info "Must delete this" entity-type id)
          (delete-entity entity-type id)
          {:status 200})
  (POST "/upload" [file]
        ;; if file exists, just overwrite with new one
        ;; else, inform clients about new template
        (let [filename (:filename file)
              odf-template (str templates-dir "/"
                                (odf-filename filename))]
          (if-not (or (mso-file? filename)
                      (odf-file? filename))
            (broadcast [:status "Error: Unknown file format"])
            (do
              (if (.exists (clojure.java.io/file odf-template))
                (broadcast [:status (str "Updating template " filename)])
                (broadcast [:new-template {:filename filename}]))
              ;; copy to templates dir

              (io/upload-file templates-dir file :create-path? true)

              (when (mso-file? filename)
                (broadcast [:status (str "Importing template " filename)])
                (let [mso-template (str templates-dir "/" filename)]
                  ;; convert to odf
                  (convert-doc (filename->target-format filename)
                               mso-template
                               templates-dir)
                  (.delete (clojure.java.io/file mso-template)))
                (broadcast [:status
                            (str "Importing template " filename " finished")]))))
          filename)))

(def app
  (-> my-routes
      (wrap-resource "public")
      wrap-params
      (wrap-json-body {:keywords? true :bigdecimals? true})
      wrap-multipart-params))

(defn -main [& args]
  (run-server
   (if (dev? args) (wrap-reload app) app)
   {:port (port args)})
  (timbre/info "server started on port" (port args)))
