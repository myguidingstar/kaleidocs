[:h3 "Records:"]

[:div.form-group
  [:label.col-sm-4.control-label "Search columns"]
  [:div.col-sm-8
   [:tags-input {:ng-model "produce.recordIds"
                 :add-on-space "true"
                 :min-length "1"
                 :allowed-tags-pattern "^[0-9]*$"}]]]
[:div ;;{:ng-show "false"}
 "{{produce.records = getRecords(produce.recordIds)}}"]
[:div ;;{:ng-show "false"}
 "{{produce.profile = getProfile(produce.records)}}"]
[:h3 "Profile:"]
[:table.table.table-bordered.table-hover.table-condensed
  [:tr {:style "font-weight: bold"}
   [:td {:style "width:35%"} "Name"]
   [:td {:style "width:55%"} "Value"]]
  [:tr {:ng-repeat "field in produce.profile.fields"}
   [:td
    [:span
     "{{ field.name || 'empty' }}"]]
   [:td
    [:span
     "{{ field.value || 'empty' }}"]]
   ]]
[:div {:ng-controller "generatedCtrl"
       :ng-include "'partials/generated.html'"}]
