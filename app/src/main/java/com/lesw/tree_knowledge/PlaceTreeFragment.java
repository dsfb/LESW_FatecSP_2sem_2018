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
import java.util.Set;


public class PlaceTreeFragment extends Fragment implements TreeNode.TreeNodeClickListener {
    ViewGroup containerView;
    private static final String NAME = "Adição de Conhecimento";
    private AndroidTreeView tView;

    private String knowledgeName;
    private String userName;
    private String certification;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_tree, container, false);
        containerView = (ViewGroup) view.findViewById(R.id.container);

        knowledgeName = getArguments().getString("knowledgeName");
        userName = getArguments().getString("userName");
        certification = getArguments().getString("certification");

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
        Knowledge parent = RoomDbManager.getInstance().getKnowledgeById(parentKnowledgeId);
        Knowledge k = new Knowledge(knowledgeName, parentKnowledgeId, parent.getLevel() + 1);
        Employee e = RoomDbManager.getInstance().getEmployeeByName(userName);
        Set<Integer> s = k.getEmployeeSet();
        s.add(e.getId());
        k.setEmployeeSet(s);
        Knowledge[] k_a = new Knowledge[1];
        k_a[0] = k;
        RoomDbManager.getInstance().insertKnowledgeArray(k_a);
        k = RoomDbManager.getInstance().getKnowledgeByName(knowledgeName);
        e.addKnowledge(k, this.getActivity());
        RoomDbManager.getInstance().updateEmployee(e);
        String aprovadoStatus = "APROVADO";
        Certification c = RoomDbManager.getInstance().getSingleCertification(userName, certification, knowledgeName);
        c.setStatus(aprovadoStatus);
        RoomDbManager.getInstance().updateCertificationByStatus(c);
        Toast.makeText(getActivity(), "A competência foi aprovada com sucesso!", Toast.LENGTH_SHORT).show();
        this.getActivity().finish();
    }
}
