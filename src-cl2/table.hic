[:table.table
 {:show-filter "true", :ng-table "tableParams"}
 [:thead
  [:tr
   [:th.text-center.sortable
    {:ng-click
     "tableParams.sorting(key, tableParams.isSortBy(key, 'asc') ? 'desc' : 'asc')",
     :ng-class
     "{'sort-asc':  tableParams.isSortBy(key, 'asc'),
       'sort-desc': tableParams.isSortBy(key, 'desc')}",
     :ng-repeat "key in itemKeys"}
    "{{key}}"]]
  [:tr
   [:th.text-center.sortable
    {:ng-repeat "key in itemKeys"}
    [:input.form-control
     {:ng-model "filterDict[key]",
      :type "text"}]]]]

 [:tbody
  {:ng-repeat "p in $data"}
  [:tr
   {:id "tr{{p.id}}"
    :ng-class-even "'even'", :ng-class-odd "'odd'"}
   [:td.rowTd
    {:ng-repeat "key in itemKeys"
     ;;:filter "{ 'fn': 'text' }",
     :sortable "key",
     :data-title "key"}
    [:p {:ng-class "key|keyBoxClass"} "{{p[key]}}"]]
   [:td.rowTd
    [:input
     {:ng-if "(entityType=='record')||(entityType=='contract')"
      :ng-click "generateItem(p.id)" :value "generate" :type "button"}]
    [:input
     {:ng-click "setEditId(p.id)", :value "edit", :type "button"
      :id "editRowBtn{{p.id}}"}]
    [:input
     {:ng-click "deleteItem(p.id)", :value "delete", :type "button"}]]]
  [:tr
   {:ng-if "editId===p.id", :ng-show "editId===p.id"}
   [:td
    {:ng-include "'edit-row.html'",
     :colspan "7"}]]]]
