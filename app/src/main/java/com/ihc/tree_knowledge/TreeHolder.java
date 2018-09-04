package com.ihc.tree_knowledge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;

public class TreeHolder extends TreeNode.BaseNodeViewHolder<IconTreeItem> {

    private PrintView arrowView;

    @SuppressWarnings("WeakerAccess")
    public TreeHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, IconTreeItem nodeValue) {
        final LayoutInflater inflater = LayoutInflater.from(context);

        @SuppressLint("InflateParams")
        final View view = inflater.inflate(R.layout.layout_tree_item, null, false);

        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(nodeValue.text);

        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(nodeValue.icon));

        switch (nodeValue.color){
            case RED:
                iconView.setIconColor(ContextCompat.getColor(context, R.color.red_leaf));
                break;
            case BLACK:
                iconView.setIconColor(ContextCompat.getColor(context, R.color.black));
                break;
            case GREEN:
                iconView.setIconColor(ContextCompat.getColor(context, R.color.accent));
                break;
        }


        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        arrowView.setPadding(20,10,10,10);
        if (node.isLeaf()) {
            arrowView.setVisibility(View.INVISIBLE);
        }
        arrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tView.toggleNode(node);
            }
        });

        TextView txtBadgeCounter = (TextView) view.findViewById(R.id.badge_count);
        if(nodeValue.counter != null){
            txtBadgeCounter.setVisibility(View.VISIBLE);
            txtBadgeCounter.setText(String.valueOf(nodeValue.counter));
        } else {
            txtBadgeCounter.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {

    }
}
