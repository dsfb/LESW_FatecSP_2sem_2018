package com.lesw.tree_knowledge;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LevelKnowledgeFragment extends Fragment {
    @BindView(R.id.lv_level_tree)
    ListView scrollView;

    Spinner spinner;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> spAdapter;

    public LevelKnowledgeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree_level, container, false);

        ButterKnife.bind(this, view);

        spinner = view.findViewById(R.id.sp_select_level_tree);

        List<Integer> levels = RoomDbManager.getInstance().loadLevelsForKnowledge();
        int size = levels.size();

        String[] levelArray = new String[size];

        for (int i = 0; i < size; i++) {
            levelArray[i] = Integer.toString(levels.get(i));
        }

        spAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, levelArray);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
                String selectedItemText = (String) parent.getItemAtPosition(position);
                try {
                    int level = Integer.parseInt(selectedItemText);
                    LevelKnowledgeFragment.this.refreshListView(level);
                } catch (NumberFormatException nfe) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void refreshListView(int level) {
        List<Knowledge> conhecimentos = RoomDbManager.getInstance().loadKnowledgeListByLevel(level);

        String[] dados = new String[conhecimentos.size()];
        for (int i = 0; i < conhecimentos.size(); i++) {
            dados[i] = conhecimentos.get(i).getName();
        }

        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, dados);
        scrollView.setAdapter(adapter);
    }
}
