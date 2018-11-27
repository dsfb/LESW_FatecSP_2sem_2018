package com.lesw.tree_knowledge;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InsertNewKnowledgeFragment extends Fragment {
    @BindView(R.id.spinner_select_level_tree)
    Spinner spinnerLevel;

    @BindView(R.id.spinner_select_parent_knowledge_tree)
    Spinner spinnerParent;

    @BindView(R.id.edit_text_new_knowledge)
    EditText editTextNewKnowledge;

    Button btnAdd;

    ArrayAdapter<String> spAdapter;

    ArrayAdapter<String> spParentAdapter;

    private String selectedLevel;

    private String selectedParentKnowledge;

    private String insertedKnowledge;

    public InsertNewKnowledgeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_new_knowledge, container, false);

        ButterKnife.bind(this, view);

        List<Integer> levels = RoomDbManager.getInstance().loadLevelsForKnowledge();
        int size = levels.size();

        String[] levelArray = new String[size];

        for (int i = 0; i < size; i++) {
            levelArray[i] = Integer.toString(levels.get(i));
        }

        spAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, levelArray);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(spAdapter);

        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
                selectedLevel = (String) parent.getSelectedItem();
                InsertNewKnowledgeFragment.this.refreshParentSpinner(selectedLevel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd = (Button) view.findViewById(R.id.btn_add_knowledge);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLevel != null && !selectedLevel.isEmpty()) {
                    if (selectedParentKnowledge != null && !selectedParentKnowledge.isEmpty()) {
                        insertedKnowledge = editTextNewKnowledge.getText().toString();

                        if (insertedKnowledge != null && !insertedKnowledge.isEmpty()) {
                            Knowledge parent = RoomDbManager.getInstance().getKnowledgeByNameAndLevel(selectedParentKnowledge,
                                    selectedLevel);
                            Knowledge k = new Knowledge(insertedKnowledge, parent.getId(), parent.getLevel() + 1);
                            Knowledge[] k_a = new Knowledge[1];
                            k_a[0] = k;
                            RoomDbManager.getInstance().insertKnowledgeArray(k_a);
                            Toast.makeText(getActivity(), "A competência foi inserida com sucesso!", Toast.LENGTH_SHORT).show();
                            editTextNewKnowledge.setText("");
                            spinnerLevel.setSelection(0);
                            spinnerParent.setSelection(0);
                        }
                    }
                }
            }
        });

        return view;
    }

    private void refreshParentSpinner(String level) {
        try {
            List<Knowledge> dados = RoomDbManager.getInstance().loadKnowledgeListByLevel(Integer.parseInt(level));
            String[] strArr = new String[dados.size()];

            for (int i = 0; i < dados.size(); i++) {
                strArr[i] = dados.get(i).getName();
            }

            spParentAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, strArr);
            spParentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerParent.setAdapter(spParentAdapter);

            spinnerParent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) view).setTextColor(Color.BLACK);
                    selectedParentKnowledge = (String) parent.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (NumberFormatException nfe) {

        }

    }
}
