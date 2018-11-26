package com.lesw.tree_knowledge;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RoomDbManager {
    private static RoomDbManager instance;
    private Context context;

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new RoomDbManager();
            instance.context = context;
        }
    }

    public static RoomDbManager getInstance() {
        return instance;
    }

    public boolean insertKnowledgeArray(Knowledge[] knowledgeArray) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().insertAll(knowledgeArray);

            for (Knowledge knowledge : knowledgeArray) {
                if (knowledge != null) {
                    Knowledge k = this.getKnowledgeByName(knowledge.getName());
                    k.manageUp(context);
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
            Knowledge k = AppDatabase.getInstance(context).knowledgeDao().findById(id);

            return k;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getKnowledgeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public Knowledge getKnowledgeByName(String name) {
        try {
            Knowledge k = AppDatabase.getInstance(context).knowledgeDao().findByName(name);

            return k;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getKnowledgeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    protected List<Knowledge> getAllKnowledge() {
        try {
            List<Knowledge> knowledgeList = AppDatabase.
                    getInstance(context).
                    knowledgeDao().getAll();

            return knowledgeList;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getAllKnowledge):\n" + e.getMessage(), e);
            List<Knowledge> knowledge = new ArrayList<>();
            return knowledge;
        }
    }

    protected List<Knowledge> loadKnowledgeListByLevel(int level) {
        try {
            List<Knowledge> knowledgeList = AppDatabase.
                    getInstance(context).
                    knowledgeDao().loadAllByLevel(level);

            return knowledgeList;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(loadKnowledgeByLevel):\n" + e.getMessage(), e);
            List<Knowledge> knowledge = new ArrayList<>();
            return knowledge;
        }
    }

    protected List<Integer> loadLevelsForKnowledge() {
        try {
            List<Integer> levels = AppDatabase.
                    getInstance(context).
                    knowledgeDao().getLevels();

            return levels;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(loadLevelsForKnowledge):\n" + e.getMessage(), e);
            List<Integer> levels = new ArrayList<>();
            return levels;
        }
    }

    protected List<Employee> getAllEmployees() {
        try {
            List<Employee> employees = AppDatabase.
                    getInstance(context).
                    employeeDao().getAll();

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
            AppDatabase.getInstance(context).employeeDao().updateEmployee(employee);

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
            Employee e = AppDatabase.getInstance(context).employeeDao().findById(id);

            return e;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getEmployeeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public Employee getEmployeeByEmail(String email) {
        try {
            Employee e = AppDatabase.getInstance(context).employeeDao().findByEmail(email);

            return e;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getEmployeeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public Employee getEmployeeByName(String name) {
        try {
            Employee e = AppDatabase.getInstance(context).employeeDao().findByName(name);

            return e;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getEmployeeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public boolean checkEmployeeByEmail(String email) {
        try {
            Employee e = AppDatabase.getInstance(context).employeeDao().findByEmail(email);

            return e != null;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(checkEmployeeByEmail):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateKnowledgeByChildren(Knowledge knowledge) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().updateKnowledge(knowledge);

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateKnowledgeByChildren):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateKnowledgeByCountingAndEmployee(Knowledge knowledge) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().updateKnowledge(knowledge);

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateKnowledgeByCountingAndEmployee):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateKnowledgeByWarningCount(Knowledge knowledge) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().updateKnowledge(knowledge);

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateKnowledgeByWarningCount):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean insertCertification(Certification certification) {
        Certification[] certifications = { certification };

        return this.insertCertificationArray(certifications);
    }

    public boolean insertCertificationArray(Certification[] certificationArray) {
        try {
            AppDatabase.getInstance(context).certificationDao().insertAll(certificationArray);

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(insertCertificationArray):\n" + e.getMessage(), e);
            return false;
        }
    }

    public List<Certification> getAllCertifications() {
        try {
            List<Certification> certifications = AppDatabase.
                    getInstance(context).
                    certificationDao().getAll();

            return certifications;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getAllCertifications):\n" + e.getMessage(), e);
            List<Certification> certifications = new ArrayList<>();
            return certifications;
        }
    }

    protected Certification getSingleCertification(String username, String certification,
                                                   String knowledge) {
        try {
            return AppDatabase.getInstance(context).certificationDao().findByCondition(certification, username, knowledge);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public boolean updateCertificationByStatus(Certification certification) {
        try {
            AppDatabase.getInstance(context).certificationDao().updateCertification(certification);

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateCertificationByStatus):\n" + e.getMessage(), e);
            return false;
        }
    }

    public int getNumberOfKnowledge() {
        try {
            return AppDatabase.getInstance(context).knowledgeDao().getNumberOfRows();
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getNumberOfKnowledge):\n" + e.getMessage(), e);
            return -1;
        }
    }

    public int getNumberOfEmployee() {
        try {
            return AppDatabase.getInstance(context).employeeDao().getNumberOfRows();
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getNumberOfEmployee):\n" + e.getMessage(), e);
            return -1;
        }
    }

    public int getNumberOfCertification() {
        try {
            return AppDatabase.getInstance(context).certificationDao().getNumberOfRows();
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getNumberOfCertification):\n" + e.getMessage(), e);
            return -1;
        }
    }
}


