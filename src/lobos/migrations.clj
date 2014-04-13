(ns lobos.migrations
  ;; exclude some clojure built-in symbols so we can use the lobos' symbols
  (:refer-clojure :exclude [alter drop complement
                            bigint boolean char double float time])
  ;; use only defmigration macro from lobos
  (:use (lobos [migration :only [defmigration]]
               core
               schema)
        [kaleidocs.models :only [db-spec]]))

(defmigration add-document-table
  ;; code be executed when migrating the schema "up" using "migrate"
  (up [] (create db-spec
                 (table :document
                        (integer :id :primary-key :auto-inc)
                        (varchar :filename 100 :unique
                                 [:collate :utf8-general-ci])
                        (varchar :fields 100
                                 [:collate :utf8-general-ci]))))
  (down [] (drop (table :document ))))

(defmigration add-docgroup-table
  (up [] (create db-spec
                 (table :docgroup
                        (integer :id :primary-key :auto-inc)
                        (varchar :name 100 :unique
                                 [:collate :utf8-general-ci])
                        (varchar :documents 100
                                 [:collate :utf8-general-ci]))))
  (down [] (drop (table :docgroup))))

(defmigration add-profile-table
  (up [] (create db-spec
                 (table :profile
                        (integer :id :primary-key :auto-inc)
                        (varchar :company 100 :unique
                                 [:collate :utf8-general-ci])
                        (varchar :bank 100
                                 [:collate :utf8-general-ci])
                        (varchar :account 100
                                 [:collate :utf8-general-ci])
                        (varchar :city 100
                                 [:collate :utf8-general-ci]))))
  (down [] (drop (table :profile))))

(defmigration add-contract-table
  (up [] (create db-spec
                 (table :contract
                        (integer :id :primary-key :auto-inc)
                        (varchar :records 255
                                 [:collate :utf8-general-ci])
                        (varchar :date 100
                                 [:collate :utf8-general-ci])
                        (integer :sum))))
  (down [] (drop (table :contract))))

(defmigration add-record-table
  (up [] (create db-spec
                 (table :record
                        (integer :id :primary-key :auto-inc)
                        (varchar :date 100
                                 [:collate :utf8-general-ci])

                        (integer :money)
                        (varchar :remarks 100
                                 [:collate :utf8-general-ci])
                        ;; (integer :contract_id [:refer :contract :id])
                        (integer :docgroup_id [:refer :docgroup :id])
                        (integer :profile_id  [:refer :profile :id]))))
  (down [] (drop (table :record))))
