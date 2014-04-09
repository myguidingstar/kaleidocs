[:html {:lang "en" :ng-app "myApp"}
 [:head
  [:meta  {:charset "utf-8"}]
  [:meta {:content "IE=edge", :http-equiv "X-UA-Compatible"}]
  [:meta
   {:content "width=device-width, initial-scale=1.0",
    :name "viewport"}]
  [:title "Kaleidocs"]
  [:link {:rel "stylesheet"
          :href "vendor/bootstrap/dist/css/bootstrap.min.css"}]
  [:link {:rel "stylesheet"
          :href "vendor/ng-table/ng-table.css"}]
  [:link
   {:href
    "http://fonts.googleapis.com/css?family=Arimo|Open+Sans+Condensed:300|Noto+Serif:700italic&subset=latin"
    :rel "stylesheet"}]
  [:script
   "document.write('<base href=\"' + document.location + '\" />')"]
  [:script {:src "vendor/sockjs/sockjs.min.js"}]
  [:script {:src "vendor/angular/angular.js"}]
  [:script {:src "vendor/angular-i18n/angular-locale_vi-vn.js"}]
  [:script {:src "vendor/angular-route/angular-route.js"}]
  [:script {:src "vendor/angular-resource/angular-resource.js"}]
  [:script {:src "vendor/ng-table/ng-table.js"}]
  [:script {:src "vendor/angular-strap/dist/angular-strap.min.js"}]
  [:script {:src "vendor/angular-strap/dist/angular-strap.tpl.min.js"}]
  [:script {:src "vendor/ng-file-upload/angular-file-upload.min.js"}]
  [:script {:src "vendor/angular-sanitize/angular-sanitize.min.js"}]
  [:script {:src "vendor/angular-animate/angular-animate.min.js"}]
  [:script {:src "app.js"}]]
 [:body
  [:div.container
   [:div.navbar.navbar-default
    {:role "navigation"}
    [:div.navbar-header
     [:button.navbar-toggle
      {:data-target ".navbar-collapse",
       :data-toggle "collapse",
       :type "button"}
      [:span.sr-only "Toggle navigation"]
      [:span.icon-bar]
      [:span.icon-bar]
      [:span.icon-bar]]
     [:a.navbar-brand {:href "/"} "Kaleidocs"]]
    [:div.navbar-collapse.collapse
     [:ul.nav.navbar-nav
      [:li [:a {:href "#/config"} "Config"]]
      [:li [:a {:href "#/templates"} "Templates"]]
      [:li [:a {:href "#/profiles"} "Profiles"]]
      [:li [:a {:href "#/records"} "Records"]]
      [:li [:a {:href "#/contracts"} "Contracts"]]
      ]
     ]]
   [:div.container.row.span12
    [:div.span8.offset2
     [:ng-view]]]]]]