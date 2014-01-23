[:form.form-horizontal {:role "form"}
 [:div.form-group
  [:label.col-sm-4.control-label "Suffixes for amount of money"]
  [:div.col-sm-8
   [:tags-input {:ng-model "config.amountSuffixes"
                 :add-on-space "true"
                 :min-length "1"
                 :allowed-tags-pattern "^[a-zA-Z0-9\\_]*$"}]]]
 [:div.form-group
  [:label.col-sm-4.control-label "Suffix for amounts in words"]
  [:div.col-sm-8
   [:input.form-control
    {:type "text"
     :ng-model "config.amountIwSuffixes"
     :ng-trim "true"
     :ng-pattern "/^[a-zA-Z0-9\\_]*$/"}]]]
 [:div.form-group
  [:label.col-sm-4.control-label "Column to calculate sum"]
  [:div.col-sm-8
   [:input.form-control
    {:type "text"
     :ng-model "config.sumColumn"
     :ng-trim "true"
     :ng-pattern "/^[a-zA-Z0-9\\_]*$/"}]]]
 [:div.form-group
  [:label.col-sm-4.control-label "Default profile keys"]
  [:div.col-sm-8
   [:tags-input {:ng-model "config.profileKeys"
                 :add-on-space "true"
                 :min-length "1"
                 :allowed-tags-pattern "^[a-zA-Z0-9\\_]*$"}]]]
 [:div.form-group
  [:label.col-sm-4.control-label "Default produce keys"]
  [:div.col-sm-8
   [:tags-input {:ng-model "config.produceKeys"
                 :add-on-space "true"
                 :min-length "1"
                 :allowed-tags-pattern "^[a-zA-Z0-9\\_]*$"}]]]
 [:div.form-group
  [:label.col-sm-4.control-label "Default table keys"]
  [:div.col-sm-8
   [:tags-input {:ng-model "config.tableKeys"
                 :add-on-space "true"
                 :min-length "1"
                 :allowed-tags-pattern "^[a-zA-Z0-9\\_]*$"}]]]
 [:div.form-group
  [:label.col-sm-4.control-label "Search columns"]
  [:div.col-sm-8
   [:tags-input {:ng-model "config.searchColumns"
                 :add-on-space "true"
                 :min-length "1"
                 :allowed-tags-pattern "^[a-zA-Z0-9\\_]*$"}]]]]
