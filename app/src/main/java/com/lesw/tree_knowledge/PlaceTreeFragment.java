package com.lesw.tree_knowledge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.io.Serializable;


public class PlaceTreeFragment extends Fragment implements TreeNode.TreeNodeClickListener {
    ViewGroup containerView;
    private static final String NAME = "Adição de Conhecimento";
    private AndroidTreeView tView;

    private String knowledgeName;
    private String userName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_tree, container, false);
        containerView = (ViewGroup) view.findViewById(R.id.container);

        knowledgeName = getArguments().getString("knowledgeName");
        userName = getArguments().getString("userName");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DummyDB db = DummyDB.getInstance();
        TreeNode tn = Knowledge.generateHRTree(db.getCompanyRoot(), getActivity());

        TreeNode root = TreeNode.root();

        root.addChildren(tn);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultNodeClickListener(PlaceTreeFragment.this);
        tView.setDefaultViewHolder(TreeHolder.class);
        containerView.removeAllViews();
        containerView.addView(tView.getView());
        tView.setUseAutoToggle(false);

        tView.expandAll();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tState", (Serializable) tView.getSaveState());
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        this.registerParentKnowledge(((IconTreeItem)value).knowledge.getId());
    }

    public void registerParentKnowledge(int parentKnowledgeId) {
        // TODO: implementar o registro de conhecimento inédito filho para um conhecimento pai fornecido!
        Toast.makeText(getActivity(), "A competência foi aprovada com sucesso!", Toast.LENGTH_SHORT).show();
    }
}
