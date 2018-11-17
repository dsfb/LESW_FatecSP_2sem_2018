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

    private Map<Integer, Employee> employeeByIdMap = new TreeMap<>();

    private Map<String, Employee> employeeByNameMap = new TreeMap<>();

    private Map<String, Employee> employeeByEmailMap = new TreeMap<>();

    private Map<Integer, Knowledge> knowledgeByIdMap = new TreeMap<>();

    private Map<String, Knowledge> knowledgeByNameMap = new TreeMap<>();

    private Map<Integer, Certification> certificationMap = new TreeMap<>();

    private void populateKnowledgeMap() {
        List<Knowledge> knowledgeList = this.getAllKnowledge();

        knowledgeByIdMap.clear();
        knowledgeByNameMap.clear();

        for (Knowledge knowledge : knowledgeList) {
            knowledgeByIdMap.put(knowledge.getId(), knowledge);
            knowledgeByNameMap.put(knowledge.getName(), knowledge);
            knowledge.populateChildren(this.getAllKnowledge());
        }
    }

    private void populateEmployeeMaps() {
        List<Employee> employeeList = this.getAllEmployees();

        employeeByIdMap.clear();
        employeeByEmailMap.clear();
        employeeByNameMap.clear();

        for (Employee employee : employeeList) {
            employeeByIdMap.put(employee.getId(), employee);
            employeeByEmailMap.put(employee.getEmail(), employee);
            employeeByNameMap.put(employee.getName(), employee);
        }
    }

    private void populateCertificationMap() {
        List<Certification> certificationList = this.getAllCertifications();

        certificationMap.clear();

        for (Certification certification : certificationList) {
            certificationMap.put(certification.getId(), certification);
        }
    }

    public void restoreDB() {
        this.populateKnowledgeMap();
        this.populateCertificationMap();
        this.populateEmployeeMaps();
    }

    public boolean insertKnowledgeArray(Knowledge[] knowledgeArray) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().insertAll(knowledgeArray);

            for (Knowledge knowledge : knowledgeArray) {
                if (knowledge != null) {
                    knowledge.manageUp(context);
                }
            }

            this.populateKnowledgeMap();

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(insertKnowledgeArray):\n" + e.getMessage(), e);
            return false;
        }
    }

    public Knowledge getKnowledgeById(int id) {
        try {
            Knowledge k = this.knowledgeByIdMap.get(id);

            return k;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getKnowledgeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public Knowledge getKnowledgeByName(String name) {
        try {
            Knowledge k = this.knowledgeByNameMap.get(name);

            if (k != null) {
                k.populateChildren(this.getAllKnowledge());
            }

            return k;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getKnowledgeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public Map<Integer, Knowledge> getKnowledgeByIdMap() {
        return this.knowledgeByIdMap;
    }

    private List<Knowledge> getAllKnowledge() {
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

    public Map<Integer, Employee> getEmployeeByIdMap() {
        return this.employeeByIdMap;
    }

    public Map<String, Employee> getEmployeeByNameMap() {
        return employeeByNameMap;
    }

    public Map<String, Employee> getEmployeeByEmailMap() {
        return employeeByEmailMap;
    }

    private List<Employee> getAllEmployees() {
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

            this.employeeByIdMap.put(employee.getId(), employee);
            this.employeeByEmailMap.put(employee.getEmail(), employee);
            this.employeeByNameMap.put(employee.getName(), employee);

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateEmployee):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean insertEmployees(Employee[] employees) {
        try {
            AppDatabase.getInstance(context).employeeDao().insertAll(employees);

            this.populateEmployeeMaps();

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(insertEmployees):\n" + e.getMessage(), e);
            return false;
        }
    }

    public Employee getEmployeeById(int id) {
        try {
            Employee e = this.employeeByIdMap.get(id);

            if (e != null) {
                e.setKnowledgeSet();
            }

            return e;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getEmployeeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public Employee getEmployeeByEmail(String email) {
        try {
            Employee e = this.employeeByEmailMap.get(email);

            if (e != null) {
                e.setKnowledgeSet();
            }

            return e;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getEmployeeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public Employee getEmployeeByName(String name) {
        try {
            Employee e = this.employeeByNameMap.get(name);

            if (e != null) {
                e.setKnowledgeSet();
            }

            return e;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(getEmployeeById):\n" + e.getMessage(), e);
            return null;
        }
    }

    public boolean checkEmployeeByEmail(String email) {
        try {
            Employee e = this.employeeByEmailMap.get(email);

            return e != null;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(checkEmployeeByEmail):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateKnowledgeByChildren(Knowledge knowledge) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().updateKnowledgeByChildren(knowledge.getId(),
                    knowledge.getChildrenSetStr());

            this.knowledgeByIdMap.put(knowledge.getId(), knowledge);

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateKnowledgeByChildren):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateKnowledgeByCounting(Knowledge knowledge) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().updateKnowledgeByCounting(knowledge.getId(),
                    knowledge.getCount());

            this.knowledgeByIdMap.put(knowledge.getId(), knowledge);

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateKnowledgeByCounting):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateKnowledgeByEmployee(Knowledge knowledge) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().updateKnowledgeByEmployee(knowledge.getId(),
                    knowledge.getEmployeeSetStr());

            this.knowledgeByIdMap.put(knowledge.getId(), knowledge);

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateKnowledgeByEmployee):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateKnowledgeByCountingAndEmployee(Knowledge knowledge) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().updateKnowledgeByCountingAndEmployee(knowledge.getId(),
                    knowledge.getCount(), knowledge.getEmployeeSetStr());

            this.knowledgeByIdMap.put(knowledge.getId(), knowledge);

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(updateKnowledgeByCountingAndEmployee):\n" + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateKnowledgeByWarningCount(Knowledge knowledge) {
        try {
            AppDatabase.getInstance(context).knowledgeDao().updateKnowledgeByWarningCount(knowledge.getId(),
                    knowledge.getWarningCount());

            this.knowledgeByIdMap.put(knowledge.getId(), knowledge);

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

            this.populateCertificationMap();

            return true;
        } catch (Exception e) {
            Log.e("TreeKnowledge", "Error(insertCertificationArray):\n" + e.getMessage(), e);
            return false;
        }
    }

    public Map<Integer, Certification> getCertificationMap() {
        return this.certificationMap;
    }

    private List<Certification> getAllCertifications() {
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

    public boolean updateCertificationByStatus(Certification certification) {
        try {
            AppDatabase.getInstance(context).certificationDao().updateCertificationByStatus(certification.getId(),
                    certification.getStatus());

            this.certificationMap.put(certification.getId(), certification);

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


