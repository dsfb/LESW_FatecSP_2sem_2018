package com.lesw.tree_knowledge;


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

public class ApprovalsFragment extends Fragment implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private ListView lv;
    private SearchView sv;
    KnowledgeListAdapter adapter;

    public ApprovalsFragment() {
        // Required empty public constructor
    }

    public static ApprovalsFragment newInstance() {
        return new ApprovalsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        handleView(view);

        return view;
    }

    public void handleView(View view) {
        sv = (SearchView) view.findViewById(R.id.search_view);
        lv = (ListView) view.findViewById(R.id.lv);

        EditText searchEditText = (EditText) sv.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.black));

        lv.setOnItemClickListener(this);

        adapter = new KnowledgeListAdapter(getContext(), DummyDB.getInstance().getCompanyCertifications());
        lv.setAdapter(adapter);
        sv.setOnQueryTextListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        handleView(getView());
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
        Certification item = (Certification) adapterView.getAdapter().getItem(i);
        if (item.getStatus().equals("PENDENTE")) {
            Intent intent = new Intent(getActivity(), ApprovalActivity.class);
            intent.putExtra("Knowledge", item.getKnowledge());
            intent.putExtra("Username", item.getUserName());
            intent.putExtra("Date", item.getDate());
            intent.putExtra("Certification", item.getCertification());
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Esta certificação já foi aprovada ou reprovada!", Toast.LENGTH_SHORT).show();
        }
    }
}
