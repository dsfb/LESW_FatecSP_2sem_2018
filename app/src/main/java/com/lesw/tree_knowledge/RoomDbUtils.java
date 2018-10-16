package com.lesw.tree_knowledge;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RoomDbUtils {
    private static RoomDbUtils instance;
    private Context context;

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new RoomDbUtils();
            instance.context = context;
        }
    }

    public static RoomDbUtils getInstance() {
        return instance;
    }

    public boolean insertKnowledgeByNameAndUp(String name, int up) {
        Knowledge[] knowledgeArray = {new Knowledge(name, up)};
        return this.insertKnowledgeArray(knowledgeArray);
    }

    public boolean insertKnowledgeArray(Knowledge[] knowledgeArray) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().insertAll(knowledgeArray);

            for (Knowledge knowledge : knowledgeArray) {
                if (knowledge != null) {
                    knowledge.manageUp(context);
                }
            }

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(insertKnowledgeArray):\n" + e.getMessage(), e);
            return false;
        }
    }

    public Knowledge getKnowledgeById(int id) {
        try {
            Knowledge k = AppDatabase.
                    getInstance(context).
                    knowledgeDao().findById(id);

            if (k != null) {
                k.populateChildren(this.getAllKnowledge());
            }

            return k;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getKnowledgeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public List<Knowledge> getAllKnowledge() {
        try {
            List<Knowledge> knowledgeList = AppDatabase.
                    getInstance(context).
                    knowledgeDao().getAll();

            for (Knowledge knowledge : knowledgeList) {
                knowledge.populateChildren(knowledgeList);
            }

            return knowledgeList;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getAllKnowledge):\n" + e.getMessage(), e);
            List<Knowledge> knowledge = new ArrayList<>();
            return knowledge;
        }
    }

    public List<Employee> getAllEmployees() {
        try {
            List<Employee> employees = AppDatabase.
                    getInstance(context).
                    employeeDao().getAll();

            for (Employee e : employees) {
                e.setKnowledgeSet();
            }

            return employees;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getAllEmployees):\n" + e.getMessage(), e);
            List<Employee> employees = new ArrayList<>();
            return employees;
        }
    }

    public boolean insertEmployee(Employee employee) {
        Employee[] employees = { employee };
        return this.insertEmployees(employees);
    }

    public boolean updateEmployee(Employee employee) {
        try {
            AppDatabase.getInstance(context).employeeDao().updateEmployeeByKnowledgeSet(employee.getId(),
                    employee.getKnowledgeSetStr());
            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateEmployee):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean insertEmployees(Employee[] employees) {
        try {
            AppDatabase.getInstance(context).employeeDao().insertAll(employees);
            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(insertEmployees):\n" + e.getMessage(), e);
            return false;
        }
    }

    public Employee getEmployeeById(int id) {
        try {
            Employee e = AppDatabase.
                    getInstance(context).
                    employeeDao().findById(id);

            e.setKnowledgeSet();

            return e;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getEmployeeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public boolean updateKnowledgeByChildren(Knowledge knowledge) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().updateKnowledgeByChildren(knowledge.getId(),
                    knowledge.getChildrenSetStr());
            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateKnowledgeByChildren):\n" + e.getMessage(), e);
            return false;
        }
    }
}


