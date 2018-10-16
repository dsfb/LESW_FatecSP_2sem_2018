package com.lesw.tree_knowledge;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RoomDbUtils {
    public static boolean insertKnowledgeByNameAndUp(String name, int up, Context context) {
        Knowledge[] knowledgeArray = {new Knowledge(name, up)};
        return RoomDbUtils.insertKnowledgeArray(knowledgeArray, context);
    }

    public static boolean insertKnowledgeArray(Knowledge[] knowledgeArray, Context context) {
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

    public static Knowledge getKnowledgeById(int id, Context context, List<Knowledge> knowledgeList) {
        try {
            Knowledge k = AppDatabase.
                    getInstance(context).
                    knowledgeDao().findById(id);

            if (k != null) {
                k.populateChildren(knowledgeList);
            }

            return k;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getKnowledgeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public static List<Knowledge> getAllKnowledge(Context context) {
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

    public static List<Employee> getAllEmployees(Context context) {
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

    public static boolean insertEmployee(Employee employee, Context context) {
        Employee[] employees = { employee };
        return RoomDbUtils.insertEmployees(employees, context);
    }

    public static boolean updateEmployee(Employee employee, Context context) {
        try {
            AppDatabase.getInstance(context).employeeDao().updateEmployeeByKnowledgeSet(employee.getId(),
                    employee.getKnowledgeSetStr());
            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateEmployee):\n" + e.getMessage(), e);
            return false;
        }
    }

    public static boolean insertEmployees(Employee[] employees, Context context) {
        try {
            AppDatabase.getInstance(context).employeeDao().insertAll(employees);
            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(insertEmployees):\n" + e.getMessage(), e);
            return false;
        }
    }

    public static Employee getEmployeeById(int id, Context context) {
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

    public static boolean updateKnowledgeByChildren(Knowledge knowledge, Context context) {
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


