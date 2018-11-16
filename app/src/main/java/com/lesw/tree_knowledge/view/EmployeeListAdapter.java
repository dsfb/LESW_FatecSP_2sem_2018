package com.lesw.tree_knowledge.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.lesw.tree_knowledge.R;
import com.lesw.tree_knowledge.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Employee> employeeList;
    private LayoutInflater inflater;
    private List<Employee> filteredList;
    private ValueFilter valueFilter;

    EmployeeListAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
        filteredList = this.employeeList;

        getFilter();
    }

    @Override
    public int getCount() {
        if(employeeList == null) return 0;
        return employeeList.size();
    }

    @Override
    public Object getItem(int i) {
        return employeeList.get(i);
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
            view = inflater.inflate(R.layout.employee_list_item, null);
            view.setMinimumHeight(75);
        }

        TextView txtName = (TextView) view.findViewById(R.id.item_title);
        TextView txtRole = (TextView) view.findViewById(R.id.item_desc);

        Employee bean = employeeList.get(i);
        txtName.setText(bean.getName());
        txtRole.setText(bean.getRole());

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
                ArrayList<Employee> filterList = new ArrayList<>();
                for (int i = 0; i < EmployeeListAdapter.this.filteredList.size(); i++) {
                    if ((EmployeeListAdapter.this.filteredList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        Employee bean = new Employee(EmployeeListAdapter.this.filteredList.get(i)
                                .getName(), EmployeeListAdapter.this.filteredList.get(i)
                                .getRole());
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
            employeeList = (ArrayList<Employee>) results.values;
            notifyDataSetChanged();
        }

    }

}