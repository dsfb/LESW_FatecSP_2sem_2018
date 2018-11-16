package com.lesw.tree_knowledge.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.lesw.tree_knowledge.R;

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

    public InsertNewKnowledgeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_new_knowledge, container, false);

        ButterKnife.bind(this, view);


        btnAdd = (Button) view.findViewById(R.id.btn_add_knowledge);

        return view;
    }
}
