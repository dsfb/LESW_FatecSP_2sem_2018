package com.lesw.tree_knowledge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeListAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Certification> certificationList;
    private LayoutInflater inflater;
    private List<Certification> filteredList;
    private ValueFilter valueFilter;

    KnowledgeListAdapter(Context context, List<Certification> certificationList) {

        this.context = context;
        this.certificationList = certificationList;
        filteredList = this.certificationList;

        getFilter();
    }

    @Override
    public int getCount() {
        if(certificationList == null) return 0;
        return certificationList.size();
    }

    @Override
    public Object getItem(int i) {
        return certificationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            //noinspection ConstantConditions
            view = inflater.inflate(R.layout.knowledge_list_item, null);
            view.setMinimumHeight(75);
        }

        TextView txtKnowledge = (TextView) view.findViewById(R.id.item_knowledge);
        TextView txtOwner = (TextView) view.findViewById(R.id.item_owner);
        TextView txtDate = (TextView) view.findViewById(R.id.item_date);
        TextView txtStatus = (TextView) view.findViewById(R.id.item_status);

        Certification bean = certificationList.get(i);
        txtKnowledge.setText(bean.getKnowledge());
        txtOwner.setText(bean.getUserName());
        txtDate.setText(bean.getDate());
        txtStatus.setText(bean.getStatus());

        if(bean.getStatus() != null){
            switch (bean.getStatus()){
                case "PENDENTE":
                    txtStatus.setTextColor(ContextCompat.getColor(context, R.color.cyan));
                    break;
                case "APROVADO":
                    txtStatus.setTextColor(ContextCompat.getColor(context, R.color.accent));
                    break;
                case "REPROVADO":
                    txtStatus.setTextColor(ContextCompat.getColor(context, R.color.red_leaf));
                    break;
            }
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Certification> filterList = new ArrayList<>();
                for (int i = 0; i < KnowledgeListAdapter.this.filteredList.size(); i++) {
                    if ((KnowledgeListAdapter.this.filteredList.get(i).getKnowledge().toUpperCase())
                            .contains(constraint.toString().toUpperCase()) ||
                            (KnowledgeListAdapter.this.filteredList.get(i).getUserName().toUpperCase())
                                    .contains(constraint.toString().toUpperCase()) ||
                            (KnowledgeListAdapter.this.filteredList.get(i).getStatus().toUpperCase())
                                    .contains(constraint.toString().toUpperCase()) ) {

                        Certification bean = new Certification(
                                KnowledgeListAdapter.this.filteredList.get(i).getKnowledge(),
                                KnowledgeListAdapter.this.filteredList.get(i).getUserName(),
                                KnowledgeListAdapter.this.filteredList.get(i).getDate(),
                                KnowledgeListAdapter.this.filteredList.get(i).getStatus(),
                                KnowledgeListAdapter.this.filteredList.get(i).getCertification(),
                                KnowledgeListAdapter.this.filteredList.get(i).getLevelParentKnowledge(),
                                KnowledgeListAdapter.this.filteredList.get(i).getParentKnowledge());
                        filterList.add(bean);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //noinspection unchecked
            certificationList = (ArrayList<Certification>) results.values;
            notifyDataSetChanged();
        }

    }

}