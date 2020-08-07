(ns leiningen.new.fwm-spa
  (:require [leiningen.new.templates :refer [renderer project-name
                                             name-to-path ->files
                                             sanitize-ns multi-segment]]
            [leiningen.core.main :as main]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def render (renderer "fwm-spa"))

(def supported-attributes #{"lein" "deps"})

(def attribute-opts (set (map #(str "+" %) supported-attributes)))

(defn parse-opts [opts]
  (reduce (fn [accum opt]
            (cond
              (attribute-opts opt) (update accum :attributes
                                           (fnil conj #{})
                                           (keyword (subs opt 1)))
              :else (throw
                      (ex-info (format "Unknown option '%s'"
                                       opt)
                               {:opts   opts
                                ::error true}))))
          {} opts))

(defn in-clj? []
  (resolve 'clj-new.helpers/create))

(defn test-runner-ns [main-ns]
  (string/join "." [(first (string/split main-ns #"\.")) "test-runner"]))

(defn windows? []
  (.contains (string/lower-case (System/getProperty "os.name")) "windows"))

(defn opts-data [n {:keys [attributes]}]
  (let [to-att #(keyword (str (name %) "?"))
        main-ns (multi-segment (sanitize-ns n))
        test-run-ns (test-runner-ns main-ns)]
    (cond-> {:raw-name         n
             :name             (project-name n)
             :namespace        main-ns
             :test-runner-ns   test-run-ns
             :test-runner-dirs (name-to-path test-run-ns)
             :lein?            (not (in-clj?))
             :deps?            (in-clj?)
             :windows?         (windows?)
             :main-file-path   (-> (name-to-path main-ns)
                                   (string/replace "\\" "/"))
             :nested-dirs      (name-to-path main-ns)}
            (not-empty attributes) (#(reduce
                                       (fn [accum att]
                                         (assoc accum (to-att att) true))
                                       % attributes)))))

(defn fwm-spa
  "Takes a name and any number of attributes of the form +attribute and produces
  a minimal ClojureScript project that incudes Figwheel Main tooling.

  The attribute options are:
     +deps        which generates a deps.edn (a default when used with clj-new)
     +lein        which generates a project.clj (a default with used with lein)"
  [name & opts]
  (when (#{"figwheel" "cljs"} name)
    (main/abort
      (format
        (str "Cannot name a figwheel project %s. The namespace will clash.\n"
             "Please choose a different name, maybe \"tryfig\"?")
        (pr-str name))))
  (try
    (let [parsed-opts (parse-opts opts)
          data (opts-data name parsed-opts)
          _ (println "data: " data)
          _ (println "nested-dirs: " (:nested-dirs data))
          _ (println "raw-name: " (:raw-name data))
          _ (println "test-runner-dirs: " (:test-runner-dirs data))
          base-files [["README.md" (render "README.md" data)]
                      ["figwheel-main.edn" (render "figwheel-main.edn" data)]
                      ["dev.cljs.edn" (render "dev.cljs.edn" data)]
                      ["test.cljs.edn" (render "test.cljs.edn" data)]
                      ["src/{{nested-dirs}}.cljs" (render "core.cljs" data)]
                      ["resources/public/css/style.css" (render "style.css" data)]
                      ["resources/public/index.html" (render "index.html" data)]
                      ["resources/public/test.html" (render "test.html" data)]
                      [".gitignore" (render "gitignore" data)]
                      ["LICENSE.txt" (render "LICENSE" data)]
                      ["test/{{nested-dirs}}_test.cljs" (render "core_test.cljs" data)]
                      ["test/{{test-runner-dirs}}.cljs" (render "test_runner.cljs" data)]
                      ["scripts/clean.clj" (render "clean.clj" data)]
                      ["scripts/dev.clj" (render "dev.clj" data)]
                      ]
          files (cond-> base-files
                        (:lein? data)
                        (conj ["project.clj" (render "project.clj" data)])
                        (:deps? data)
                        (conj ["deps.edn" (render "deps.edn" data)]))
          ]
      (main/info (format (str "Generating fresh figwheel-main project.\n"
                              "   -->  To get started: Change into the '%s' directory and run '%s'\n")
                         (:name data)
                         (if (:deps? data)
                           "clojure -A:fig:build"
                           "lein fig:build")))
      (apply ->files data files)
      ;; ensure target directory
      (.mkdirs (io/file (:name data) "target" "public")))
    (catch clojure.lang.ExceptionInfo t
      (if (-> t ex-data ::error)
        (main/warn (.getMessage t))
        (throw t)))))
