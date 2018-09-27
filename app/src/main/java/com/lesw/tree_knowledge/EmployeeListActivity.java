package com.lesw.tree_knowledge;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeListActivity extends ListActivity {

    private List<String[]> employeeList;

    Knowledge knowledge;
    NumberPicker numberPicker;
    LinearLayout warningContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);

        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        int id = intent.getIntExtra("ID", 0);

        DummyDB db = DummyDB.getInstance(getApplicationContext());
        knowledge = Knowledge.getById(id, db.getCompanyRoot());

        if(db.getLoggedUser().getFunction().equals(RoleEnum.HR)){

            warningContainer = (LinearLayout) findViewById(R.id.warning_ll);
            warningContainer.setVisibility(View.GONE);

        } else {
            numberPicker = (NumberPicker) findViewById(R.id.number_picker);

            if(knowledge != null) {
                numberPicker.setValue(knowledge.getWarningCount());
            } else {
                numberPicker.setValue(1);
            }
        }

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        if(title != null){
            mTitle.setText(title);
            employeeList = db.getEmployeeListByKnowledge(title);
        } else {
            mTitle.setText("Detalhes da competência");
            employeeList = new ArrayList<>();
        }

        List<HashMap<String,String>> employeeHashMapList = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> item;
        for (String[] anEmployeeList : employeeList) {
            item = new HashMap<String, String>();
            item.put("line1", anEmployeeList[0]);
            item.put("line2", anEmployeeList[1]);
            employeeHashMapList.add(item);
        }

        SimpleAdapter sa = new SimpleAdapter(this, employeeHashMapList,
                R.layout.employee_list_item,
                new String[] { "line1", "line2" },
                new int[] {R.id.item_title, R.id.item_desc});
        ((ListView)findViewById(android.R.id.list)).setAdapter(sa);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(numberPicker != null) {
            knowledge.setWarningCount(numberPicker.getValue());
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String[] employee = employeeList.get(position);
        if(employee.length == 2) {
            String employeeName = employee[0];
            String employeeRole = employee[1];
            Intent intent = new Intent(this, EmployeeActivity.class);
            intent.putExtra("NAME", employeeName);
            intent.putExtra("ROLE", employeeRole);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Erro ao selecionar funcionário. Favor reportar.", Toast.LENGTH_LONG).show();
        }
    }
}
