(defproject test-frame "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3211"]
                 [reagent "0.5.0"]
                 [cljs-ajax "0.3.14"]
                 [patisserie "0.1.1"]
                 [binaryage/devtools "0.3.0"]
                 [org.clojars.frozenlock/reagent-modals "0.2.3"]
                 [re-frame "0.4.1"]]

  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.0.6"]
            [lein-figwheel "0.3.3" :exclusions [cider/cider-nrepl]]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target" "test/js"]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]

                        :figwheel {:on-jsload "test-frame.core/mount-root"
                                   ;;:websocket-host ~(.getHostAddress (java.net.InetAddress/getLocalHost))
;;                                   :websocket-host "10.0.0.5"
                                   }

                        :compiler {:main test-frame.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main test-frame.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :pretty-print false}}]})
