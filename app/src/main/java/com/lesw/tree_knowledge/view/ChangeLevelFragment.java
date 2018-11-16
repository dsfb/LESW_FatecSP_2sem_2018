package com.lesw.tree_knowledge.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.lesw.tree_knowledge.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeLevelFragment extends Fragment {

    @BindView(R.id.spinner_change_level_employee) Spinner spinner;
    @BindView(R.id.rdo_level_user) RadioButton userChoice;
    @BindView(R.id.rdo_level_hr) RadioButton hrChoice;
    @BindView(R.id.rdo_level_manager) RadioButton managerChoice;

    Button btnChange;

    public ChangeLevelFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hr_change_level, container, false);

        ButterKnife.bind(this, view);


        btnChange = (Button) view.findViewById(R.id.btn_hr_change_level);

        return view;
    }
}
