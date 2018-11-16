package com.lesw.tree_knowledge.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lesw.tree_knowledge.R;
import com.lesw.tree_knowledge.dao.DummyDB;
import com.lesw.tree_knowledge.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private ListView lv;
    private SearchView sv;
    private List<Employee> employeeList;
    EmployeeListAdapter adapter;
    private static SearchFragment instance;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        instance = new SearchFragment();
        return instance;
    }

    public static SearchFragment getInstance() {
        if (instance == null) {
            instance = newInstance();
        }

        return instance;
    }

    public void refreshEmployeeList() {
        employeeList = new ArrayList<>();

        for (String[] anEmployeeList : DummyDB.getInstance().getEmployeeList()) {
            Employee employee = new Employee(anEmployeeList[0], anEmployeeList[1]);
            employeeList.add(employee);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.refreshEmployeeList();
    }

    public void handleMyView() {
        handleView(getView());
    }

    public void handleView(View view) {
        sv = (SearchView) view.findViewById(R.id.search_view);
        lv = (ListView) view.findViewById(R.id.lv);

        EditText searchEditText = (EditText) sv.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.black));

        lv.setOnItemClickListener(this);

        adapter = new EmployeeListAdapter(getContext(), employeeList);
        lv.setAdapter(adapter);
        sv.setOnQueryTextListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        handleView(view);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        adapter.getFilter().filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s != null && s.equals("")) {
            adapter.getFilter().filter(s);
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Employee employee = (Employee) adapter.getItem(i);
        if(employee != null) {
            Intent intent = new Intent(getActivity(), EmployeeActivity.class);
            intent.putExtra("NAME", employee.getName());
            intent.putExtra("ROLE", employee.getRole());
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Erro ao selecionar funcionário. Favor reportar.", Toast.LENGTH_LONG).show();
        }
    }
}
