package com.lesw.tree_knowledge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.io.Serializable;

public class UserTreeFragment extends Fragment implements TreeNode.TreeNodeClickListener {

    ViewGroup containerView;
    private static final String NAME = "Conhecimento";
    private AndroidTreeView tView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree, container, false);
        containerView = (ViewGroup) view.findViewById(R.id.container);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TreeNode root = TreeNode.root();

        DummyDB db = DummyDB.getInstance(getContext());

        Employee employee = db.findEmployeeByEmail(this.getActivity().getIntent().getStringExtra("EMAIL"));

        TreeNode tn = Knowledge.generateUserTree(employee, getActivity());

        root.addChildren(tn);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultNodeClickListener(UserTreeFragment.this);
        tView.setDefaultViewHolder(TreeHolder.class);
        containerView.addView(tView.getView());
        tView.setUseAutoToggle(false);

        tView.expandAll();

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
    }


    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tState", (Serializable) tView.getSaveState());
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        Toast toast = Toast.makeText(getActivity(), ((IconTreeItem)value).text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
