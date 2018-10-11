package com.lesw.tree_knowledge;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;

public class DummyDB {

    private static DummyDB instance;

    private List<Employee> companyEmployees;
    private List<Knowledge> companyKnowledges;
    private Employee lastFoundEmployee;
    private List<Certification> companyCertifications;
    private Employee loggedUser;
    private Context context;

    public static DummyDB getInstance(Context context){
        if(instance == null){
            instance = new DummyDB();
            instance.context = context;
            instance.initData();
        }

        instance.context = context;
        return instance;
    }

    private DummyDB() {
        this.companyEmployees = new ArrayList<>();
        this.companyKnowledges = new ArrayList<>();
        this.companyCertifications = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        companyEmployees.add(employee);
    }

    public Employee findEmployeeByEmail(String email) {
        for (Employee e : companyEmployees) {
            if (e.getEmail().equalsIgnoreCase(email)) {
                this.lastFoundEmployee = e;
                return e;
            }
        }
        return null;
    }
    public Employee findEmployeeByName(String name) {
        for (Employee e : companyEmployees) {
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
            for (Employee e : companyEmployees) {
                if (e.getEmail().equalsIgnoreCase(email)) {
                    this.lastFoundEmployee = e;
                    e.login();
                    this.loggedUser = e;
                }
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
        for (Employee e : companyEmployees) {
            if (e.getEmail().equalsIgnoreCase(email) && e.getPassword().equals(password)) {
                return e;
            }
        }
        return null;
    }

    public void addKnowledgeToRoot(String nome) {
        Knowledge k = new Knowledge(nome, Knowledge.ROOT.getId());
        this.companyKnowledges.add(k);
    }

    public Knowledge findKnowlegde(String nome) {
        for (Knowledge k : companyKnowledges) {
            if (k.getName().equals(nome)) {
                return k;
            }
        }

        return null;
    }

    public void addCertification(Certification certification) {
        companyCertifications.add(certification);
    }

    private void initData(){
        Knowledge.ROOT = RoomDbUtils.getKnowledgeById(1, this.context);

        if (Knowledge.ROOT == null) {
            List<Knowledge> fakeKnowledges = new ArrayList<>();

            Knowledge[] knowledges = {new Knowledge("Árvore do Conhecimento"),
                    new Knowledge("Lógica de Programação", 1),
                    new Knowledge("Microsoft Windows", 1),
                    new Knowledge("Java", 2),
                    new Knowledge("Python", 2),
                    new Knowledge("Word 2013", 3),
                    new Knowledge("Excel 2013", 3),
                    new Knowledge("JavaFX", 4),
                    new Knowledge("JPA", 4),
                    new Knowledge("JAX-RS", 4),
                    new Knowledge("NumPy", 5),
                    new Knowledge("PyTorch", 5)
            };

            Knowledge[] fakeKnowledgeArray = knowledges;

            boolean val = RoomDbUtils.insertKnowledgeArray(fakeKnowledgeArray, this.context);

            Log.d("TreeKnowledge", "Inserção(Conhecimentos) aconteceu: " + val + "!");

            Knowledge.ROOT = RoomDbUtils.getKnowledgeById(1, this.context);

            Employee[] employees = Employee.populateData();
            val = RoomDbUtils.insertEmployees(employees, this.context);

            Log.d("TreeKnowledge", "Inserção(Empregados) aconteceu: " + val + "!");

            //getting all table records
            companyEmployees = RoomDbUtils.getAllEmployees(this.context);

            Log.d("TK", "(I)company_employees.size(): " + companyEmployees.size());

            companyKnowledges = RoomDbUtils.getAllKnowledge(this.context);

            Log.d("TK", "(I)company_knowledges.size(): " + companyKnowledges.size());

            Employee e3 = RoomDbUtils.getEmployeeById(3, this.context);

            if (e3 == null) {
                Log.d("TK", "(I)empregado(Rodrigo Silva): null!");
            }

            e3.addKnowledge(companyKnowledges.get(8), this.context);
            e3.addKnowledge(companyKnowledges.get(10), this.context);
            e3.addKnowledge(companyKnowledges.get(5), this.context);

            val = RoomDbUtils.updateEmployee(e3, this.context);

            Log.d("TreeKnowledge", "Atualização(Empregado: Rodrigo Silva) aconteceu: " + val + "!");
        }

        //getting all table records
        companyEmployees = RoomDbUtils.getAllEmployees(this.context);

        Log.d("TK", "company_employees.size(): " + companyEmployees.size());

        companyKnowledges = RoomDbUtils.getAllKnowledge(this.context);

        Log.d("TK", "company_knowledges.size(): " + companyKnowledges.size());

        for (Knowledge k : companyKnowledges) {

            k.manageUp(this.context);
        }



        /*
        e1.addKnowledge(k8);
        e1.addKnowledge(k9);
        e1.addKnowledge(k10);
        e1.addKnowledge(k11);
        e1.addKnowledge(k12);


        e2.addKnowledge(k6);
        e2.addKnowledge(k7);
        */


        /*
        e4.addKnowledge(k6);
        e4.addKnowledge(k7);

        e5.addKnowledge(k4);
        e5.addKnowledge(k5);
        e5.addKnowledge(k11);
        e5.addKnowledge(k12);
        e5.addKnowledge(k6);
        e5.addKnowledge(k7);
        */
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

        for (String[] aCertificationList : certificationArray) {
            Certification certification = new Certification(aCertificationList[0], aCertificationList[1],
                    aCertificationList[2], aCertificationList[3], aCertificationList[4]);
            this.companyCertifications.add(certification);
        }
    }

    public List<Employee> getCompanyEmployees() {
        return companyEmployees;
    }

    public List<Certification> getCompanyCertifications() { return companyCertifications; }

    public Knowledge getCompanyRoot() {
        return Knowledge.ROOT;
    }

    public boolean handleApprovedCertificationInCommonCase(String knowledge, String userName) {
        Knowledge k = findKnowlegde(knowledge);

        if (k != null) {
            Employee the_emp = null;
            for (Employee e : companyEmployees) {
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
        Certification the_certification = null;

        for (Certification cert : companyCertifications) {
            if (cert.getKnowledge().equals(knowledge) && cert.getUserName().equals(userName) &&
                    cert.getDate().equals(date) && cert.getCertification().equals(certification)) {
                the_certification = cert;
                break;
            }
        }

        if (the_certification != null) {
            the_certification.setStatus("APROVADO");
            return true;
        }

        return false;
    }

    public boolean disapproveCertification(String knowledge, String userName, String date, String certification) {
        Certification the_certification = null;

        for (Certification cert : companyCertifications) {
            if (cert.getKnowledge().equals(knowledge) && cert.getUserName().equals(userName) &&
                    cert.getDate().equals(date) && cert.getCertification().equals(certification)) {
                the_certification = cert;
                break;
            }
        }

        if (the_certification != null) {
            the_certification.setStatus("REPROVADO");
            return true;
        }

        return false;
    }

    public List<String[]> getEmployeeList() {
        List<String[]> employeeList = new ArrayList<>();
        for (Employee e : companyEmployees) {
            String[] stringArray = {e.getName(), e.getRole()};
            employeeList.add(stringArray);
        }

        return employeeList;
    }

    public List<String[]> getEmployeeListByKnowledge(String knowledge) {
        List<String[]> employeeList = new ArrayList<>();
        for (Employee e : companyEmployees) {
            if (e.hasAKnowledge(knowledge)) {
                String[] stringArray = {e.getName(), e.getRole()};
                employeeList.add(stringArray);
            }
        }

        return employeeList;
    }
}
