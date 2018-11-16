package com.lesw.tree_knowledge.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lesw.tree_knowledge.R;

import butterknife.ButterKnife;

public class LevelKnowledgeFragment extends Fragment {
    public LevelKnowledgeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree_level, container, false);

        ButterKnife.bind(this, view);

        return view;
    }
}
