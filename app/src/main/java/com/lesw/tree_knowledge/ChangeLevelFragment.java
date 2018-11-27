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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// Source adapted: https://android--examples.blogspot.com/2016/10/android-change-spinner-selected-item.html

public class ChangeLevelFragment extends Fragment {

    @BindView(R.id.spinner_change_level_employee) Spinner spEmployee;
    @BindView(R.id.spinner_set_first_level_employee) Spinner spFirstLevel;
    @BindView(R.id.spinner_set_last_level_employee) Spinner spLastLevel;

    private ArrayAdapter<String> aaEmployee, aaFirstLevel, aaLastLevel;
    private String firstLevel, employee, lastLevel;

    private List<Employee> employees = new ArrayList<>();

    private Button btnChange;

    public ChangeLevelFragment() {
    }

    private void prepareView() {
        List<String> lista = new ArrayList<>();

        for (RoleEnum re : RoleEnum.values()) {
            lista.add(re.toString());
        }

        String[] dados = lista.toArray(new String[lista.size()]);

        aaFirstLevel = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, dados) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.BLACK);

                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position,convertView,parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);

                // Return the modified view
                return tv;
            }
        };

        aaFirstLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFirstLevel.setAdapter(aaFirstLevel);

        spFirstLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                firstLevel = (String) parent.getSelectedItem();
                ChangeLevelFragment.this.processFirstLevel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hr_change_level, container, false);

        ButterKnife.bind(this, view);

        btnChange = (Button) view.findViewById(R.id.btn_hr_change_level);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Employee emp : employees) {
                    if (employee.equals(emp.getName())) {
                        RoleEnum re = null;
                        switch (lastLevel) {
                            case "MANAGER":
                                re = RoleEnum.MANAGER;
                                break;
                            case "HR":
                                re = RoleEnum.HR;
                                break;
                            case "USER":
                                re = RoleEnum.USER;
                                break;
                            default:
                                break;
                        }

                        if (re != null) {
                            emp.setFunction(re);
                            RoomDbManager.getInstance().updateEmployee(emp);
                            Toast.makeText(ChangeLevelFragment.this.getActivity(), "Funcionário teve o nível de perfil alterado com sucesso!", Toast.LENGTH_SHORT).show();
                            spFirstLevel.setSelection(0);
                            ChangeLevelFragment.this.processFirstLevel();
                        }

                        break;
                    }
                }
            }
        });

        this.prepareView();

        return view;
    }

    private void processFirstLevel() {
        List<String> lista = new ArrayList<>();
        String[] dados;

        switch(firstLevel) {
            case "MANAGER":
                employees = RoomDbManager.getInstance().getEmployeesByFunction(RoleEnum.MANAGER);

                for (RoleEnum re : RoleEnum.values()) {
                    if (!re.equals(RoleEnum.MANAGER)) {
                        lista.add(re.toString());
                    }
                }

                break;
            case "HR":
                employees = RoomDbManager.getInstance().getEmployeesByFunction(RoleEnum.HR);

                for (RoleEnum re : RoleEnum.values()) {
                    if (!re.equals(RoleEnum.HR)) {
                        lista.add(re.toString());
                    }
                }

                break;
            case "USER":
                employees = RoomDbManager.getInstance().getEmployeesByFunction(RoleEnum.USER);

                for (RoleEnum re : RoleEnum.values()) {
                    if (!re.equals(RoleEnum.USER)) {
                        lista.add(re.toString());
                    }
                }

                break;
            default:
                break;
        }

        dados = lista.toArray(new String[lista.size()]);

        aaLastLevel = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, dados) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.BLACK);

                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position,convertView,parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);

                // Return the modified view
                return tv;
            }
        };
        aaLastLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLastLevel.setAdapter(aaLastLevel);

        spLastLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lastLevel = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> strEmployees = new ArrayList<>();

        for (Employee e : employees) {
            strEmployees.add(e.getName());
        }

        String[] dataEmployee = strEmployees.toArray(new String[strEmployees.size()]);

        aaEmployee = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, dataEmployee) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.BLACK);

                // Return the view
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position,convertView,parent);

                // Set the text color of drop down items
                tv.setTextColor(Color.BLACK);

                // Return the modified view
                return tv;
            }
        };
        aaEmployee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmployee.setAdapter(aaEmployee);

        spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employee = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
