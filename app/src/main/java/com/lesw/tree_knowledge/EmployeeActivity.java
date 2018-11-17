package com.lesw.tree_knowledge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.io.Serializable;

public class EmployeeActivity extends AppCompatActivity {

    ViewGroup containerView;
    private AndroidTreeView tView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        containerView = (ViewGroup) findViewById(R.id.container);

        Intent intent = getIntent();
        String employeeName = intent.getStringExtra("NAME");
        String employeeRole = intent.getStringExtra("ROLE");

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);

        if(employeeName == null && employeeRole == null) return;

        mTitle.setText(employeeName + ", " + employeeRole);

        Employee employee = DummyDB.getInstance().findEmployeeByName(employeeName);
        if(employee == null) return;

        TreeNode tn = Knowledge.generateUserTree(employee, getApplicationContext());

        TreeNode root = TreeNode.root();

        root.addChildren(tn);

        tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        //tView.setDefaultNodeClickListener(this);
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

//    @Override
//    public void onClick(TreeNode node, Object value) {
//        Toast toast = Toast.makeText(this, ((IconTreeItem)value).text, Toast.LENGTH_SHORT);
//        toast.show();
//    }
}
