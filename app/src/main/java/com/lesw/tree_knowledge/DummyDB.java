package com.lesw.tree_knowledge;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DummyDB {

    private static DummyDB instance;

    private Employee lastFoundEmployee;
    private Employee loggedUser;
    private Context context;

    private static Type listType = new TypeToken<List<Integer>>(){}.getType();

    private static Gson gson = new Gson();

    public static void initializeInstance(Context context) {
        if(instance == null){
            instance = new DummyDB();
            instance.context = context;
            instance.initData();
        }
    }

    public static DummyDB getInstance(){
        return instance;
    }

    private DummyDB() {
        super();
    }

    public Employee findEmployeeByEmail(String email) {
        for (Employee e : RoomDbUtils.getInstance().getAllEmployees()) {
            if (e.getEmail().equalsIgnoreCase(email)) {
                this.lastFoundEmployee = e;
                return e;
            }
        }
        return null;
    }
    public Employee findEmployeeByName(String name) {
        for (Employee e : RoomDbUtils.getInstance().getAllEmployees()) {
            if (e.getName().equalsIgnoreCase(name)) {
                this.lastFoundEmployee = e;
                return e;
            }
        }
        return null;
    }


    public void loginEmployee(String email) {
        if (this.lastFoundEmployee != null && this.lastFoundEmployee.getEmail().equalsIgnoreCase(email)) {
            this.lastFoundEmployee.login();
            this.loggedUser = this.lastFoundEmployee;
        } else {
            Employee e = findEmployeeByEmail(email);
            if (e != null) {
                this.lastFoundEmployee = e;
                e.login();
                this.loggedUser = e;
            }
        }
    }

    public String getLoggedEmployeeName() {
        return loggedUser.getName();
    }

    public Employee getLoggedUser(){
        return loggedUser;
    }

    public Employee findEmployee(String email, String password) {
        for (Employee e : this.getCompanyEmployees()) {
            if (e.getEmail().equalsIgnoreCase(email) && e.getPassword().equals(password)) {
                return e;
            }
        }
        return null;
    }

    public Employee findEmployeeById(int id) {
        for (Employee e : this.getCompanyEmployees()) {
            if (e.getId() == id) {
                return e;
            }
        }

        return null;
    }

    public Knowledge findKnowlegde(String nome) {
        for (Knowledge k : RoomDbUtils.getInstance().getAllKnowledge()) {
            if (k.getName().equals(nome)) {
                return k;
            }
        }

        return null;
    }

    private void initData(){
        Knowledge.ROOT = RoomDbUtils.getInstance().getKnowledgeById(1);

        if (Knowledge.ROOT == null) {
            List<Knowledge> fakeKnowledges = new ArrayList<>();

            Knowledge[] knowledges = {new Knowledge("Árvore do Conhecimento"),
                    new Knowledge("Lógica de Programação", 1, 1),
                    new Knowledge("Microsoft Windows", 1, 1),
                    new Knowledge("Java", 2, 2),
                    new Knowledge("Python", 2, 2),
                    new Knowledge("Word 2013", 3, 2),
                    new Knowledge("Excel 2013", 3, 2),
                    new Knowledge("JavaFX", 4, 3),
                    new Knowledge("JPA", 4, 3),
                    new Knowledge("JAX-RS", 4, 3),
                    new Knowledge("NumPy", 5, 3),
                    new Knowledge("PyTorch", 5, 3)
            };

            Knowledge[] fakeKnowledgeArray = knowledges;

            boolean val = RoomDbUtils.getInstance().insertKnowledgeArray(fakeKnowledgeArray);

            Log.d("TreeKnowledge", "Inserção(Conhecimentos) aconteceu: " + val + "!");

            for (Knowledge knowledge : RoomDbUtils.getInstance().getAllKnowledge()) {
                knowledge.manageUp(context);
            }

            Knowledge.ROOT = RoomDbUtils.getInstance().getKnowledgeById(1);

            Employee[] employees = Employee.populateData();
            val = RoomDbUtils.getInstance().insertEmployees(employees);

            Log.d("TreeKnowledge", "Inserção(Empregados) aconteceu: " + val + "!");

            Log.d("TK", "(I)company_employees.size(): " + RoomDbUtils.
                    getInstance().getAllEmployees().size());

            Log.d("TK", "(I)company_knowledges.size(): " + RoomDbUtils.
                    getInstance().getAllKnowledge().size());

            List<Integer> list = new ArrayList<Integer>();

            Employee e3 = RoomDbUtils.getInstance().getEmployeeById(3);

            Collections.addAll(list, 9, 10, 6);

            e3.addKnowledgeArrayById(list, this.context);

            val = RoomDbUtils.getInstance().updateEmployee(e3);

            Log.d("TreeKnowledge", "Atualização(Empregado: Rodrigo Silva) aconteceu: " + val + "!");

            Employee e1 = RoomDbUtils.getInstance().getEmployeeById(1);

            list.clear();

            Collections.addAll(list, 8, 9, 10, 11, 12);

            e1.addKnowledgeArrayById(list, this.context);

            val = RoomDbUtils.getInstance().updateEmployee(e1);

            Log.d("TreeKnowledge", "Atualização(Empregado: Diego Alves) aconteceu: " + val + "!");

            Employee e2 = RoomDbUtils.getInstance().getEmployeeById(2);

            list.clear();

            Collections.addAll(list, 6, 7);

            e2.addKnowledgeArrayById(list, this.context);

            val = RoomDbUtils.getInstance().updateEmployee(e2);

            Log.d("TreeKnowledge", "Atualização(Empregado: Pedro Santana) aconteceu: " + val + "!");

            Employee e4 = RoomDbUtils.getInstance().getEmployeeById(4);

            list.clear();

            Collections.addAll(list, 6, 7);

            e4.addKnowledgeArrayById(list, this.context);

            val = RoomDbUtils.getInstance().updateEmployee(e4);

            Log.d("TreeKnowledge", "Atualização(Empregado: Márcia Garcia) aconteceu: " + val + "!");

            Employee e5 = RoomDbUtils.getInstance().getEmployeeById(5);

            list.clear();

            Collections.addAll(list, 4,5, 6, 7, 11, 12);

            e5.addKnowledgeArrayById(list, this.context);

            val = RoomDbUtils.getInstance().updateEmployee(e5);

            Log.d("TreeKnowledge", "Atualização(Empregado: Roger Flores) aconteceu: " + val + "!");

            String[][] certificationArray = {
                    {"C#", "Diego Alves", "18/10/2017", "PENDENTE", "C# Certificado XPTO!"},
                    {"C++", "Diego Alves", "31/10/2017", "PENDENTE", "C++ Certificado XPTO!"},
                    {"PMBOK", "Pedro Santana", "07/09/2017", "APROVADO", "PMBOK Certificado XPTO!"},
                    {"Mandarim Básico", "Diego Alves", "01/08/2017", "REPROVADO", "Beginner Chinese Mandarin Certificate!"},
                    {"C++", "Rodrigo Silva", "26/09/2017", "APROVADO", "C++ 2017 Certificate XPTO!"},
                    {"Estruturas de Dados", "Rodrigo Silva", "31/06/2017", "REPROVADO", "Estruturas de Dados Certificado!"},
                    {"SQL Server 2013", "Diego Alves", "03/09/2017", "APROVADO", "Microsoft SQL Server 2013 Certificate XPTO!"},
                    {"Machine Learning", "Mariana Garcia", "02/10/2017", "PENDENTE", "Machine Learning Certificado XPTO!"},
                    {"JavaFX", "Mariana Garcia", "18/07/2017", "PENDENTE", "JavaFX Certificado XPTO!"},
            };

            int index = 0;

            Certification[] certifications = new Certification[certificationArray.length];

            for (String[] aCertificationList : certificationArray) {
                Certification certification = new Certification(aCertificationList[0], aCertificationList[1],
                        aCertificationList[2], aCertificationList[3], aCertificationList[4]);

                certifications[index++] = certification;
            }

            val = RoomDbUtils.getInstance().insertCertificationArray(certifications);

            Log.d("TreeKnowledge", "Inserção(Certificados) aconteceu: " + val + "!");
        }

        Log.d("TK", "company_employees.size(): " + RoomDbUtils.getInstance().getAllEmployees().size());

        Log.d("TK", "company_knowledges.size(): " +RoomDbUtils.getInstance().getAllKnowledge().size());

        Log.d("TK", "company_certifications.size(): " +RoomDbUtils.getInstance().getAllCertifications().size());
    }

    public List<Employee> getCompanyEmployees() {
        return RoomDbUtils.getInstance().getAllEmployees();
    }

    public List<Knowledge> getCompanyKnowledgeList() {
        return RoomDbUtils.getInstance().getAllKnowledge();
    }

    public List<Certification> getCompanyCertifications() {
        return RoomDbUtils.getInstance().getAllCertifications();
    }

    public Context getContext() {
        return context;
    }

    public Knowledge getCompanyRoot() {
        return Knowledge.ROOT;
    }

    public boolean handleApprovedCertificationInCommonCase(String knowledge, String userName) {
        Knowledge k = findKnowlegde(knowledge);

        if (k != null) {
            Employee the_emp = null;
            for (Employee e : this.getCompanyEmployees()) {
                if (e.getName().equalsIgnoreCase(userName)) {
                    the_emp = e;
                    break;
                }
            }

            if (the_emp != null) {
                the_emp.addKnowledge(k, context);
            }

            return true;
        }

        return false;
    }

    public boolean approveCertification(String knowledge, String userName, String date, String certification) {
        for (Certification cert : this.getCompanyCertifications()) {
            if (cert.getKnowledge().equals(knowledge) && cert.getUserName().equals(userName) &&
                    cert.getDate().equals(date) && cert.getCertification().equals(certification)) {
                RoomDbUtils.getInstance().updateCertificationByStatus(cert.getId(), "APROVADO");
                return true;
            }
        }

        return false;
    }

    public boolean disapproveCertification(String knowledge, String userName, String date, String certification) {
        for (Certification cert : this.getCompanyCertifications()) {
            if (cert.getKnowledge().equals(knowledge) && cert.getUserName().equals(userName) &&
                    cert.getDate().equals(date) && cert.getCertification().equals(certification)) {
                RoomDbUtils.getInstance().updateCertificationByStatus(cert.getId(), "REPROVADO");
                return true;
            }
        }

        return false;
    }

    public List<String[]> getEmployeeList() {
        List<String[]> employeeList = new ArrayList<>();
        for (Employee e : this.getCompanyEmployees()) {
            String[] stringArray = {e.getName(), e.getRole()};
            employeeList.add(stringArray);
        }

        return employeeList;
    }

    public List<String[]> getEmployeeListByKnowledge(String knowledge) {
        List<String[]> employeeList = new ArrayList<>();
        for (Employee e : this.getCompanyEmployees()) {
            if (e.hasAKnowledge(knowledge)) {
                String[] stringArray = {e.getName(), e.getRole()};
                employeeList.add(stringArray);
            }
        }

        return employeeList;
    }
}
