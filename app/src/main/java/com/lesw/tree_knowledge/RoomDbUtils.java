package com.lesw.tree_knowledge;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomDbUtils {
    public static boolean insertKnowledgeByNameAndUp(String name, int up, Context context) {
        Knowledge[] knowledgeArray = {new Knowledge(name, up)};
        return RoomDbUtils.insertKnowledgeArray(knowledgeArray, context);
    }

    public static boolean insertKnowledgeArray(Knowledge[] knowledgeArray, Context context) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().insertAll(knowledgeArray);
            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(insertKnowledgeArray):\n" + e.getMessage());
            return false;
        }
    }

    public static Knowledge getKnowledgeById(int id, Context context) {
        try {
            return AppDatabase.
                    getInstance(context).
                    knowledgeDao().findById(id);
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getKnowledgeById):\n" + e.getMessage());
            return null;
        }
    }

    public static List<Knowledge> getAllKnowledge(Context context) {
        try {
            return AppDatabase.
                    getInstance(context).
                    knowledgeDao().getAll();
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getAllKnowledge):\n" + e.getMessage());
            List<Knowledge> knowledge = new ArrayList<>();
            return knowledge;
        }
    }

    public static List<Employee> getAllEmployees(Context context) {
        try {
            return AppDatabase.
                    getInstance(context).
                    employeeDao().getAll();
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getAllEmployees):\n" + e.getMessage());
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
            Log.e("TreeKnowledge", "Error(updateEmployees):\n" + e.getMessage());
            return false;
        }
    }

    public static boolean insertEmployees(Employee[] employees, Context context) {
        try {
            AppDatabase.getInstance(context).employeeDao().insertAll(employees);
            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(insertEmployees):\n" + e.getMessage());
            return false;
        }
    }

    public static Employee getEmployeeById(int id, Context context) {
        try {
            return AppDatabase.
                    getInstance(context).
                    employeeDao().findById(id);
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getEmployeeById):\n" + e.getMessage());
            return null;
        }
    }
}


