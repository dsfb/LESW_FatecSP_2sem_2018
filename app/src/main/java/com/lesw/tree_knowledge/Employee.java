package com.lesw.tree_knowledge;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Entity(tableName = "employee")
class Employee {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "role")
    private String role;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "pin")
    private String pin;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "function")
    private RoleEnum function;

    @Ignore
    private Boolean logged = false;

    @Ignore
    private Set<Knowledge> knowledgeSet;

    @ColumnInfo(name = "knowledge_set")
    private String knowledgeSetStr;

    @Ignore
    private static Type setType = new TypeToken<TreeSet<Integer>>(){}.getType();

    @Ignore
    private static Gson gson = new Gson();

    public static Employee[] populateData() {
        return new Employee[] {
                new Employee("Diego Alves", "Desenvolvedor Java", "diego.alves@acme.com", "1234", "123456", RoleEnum.USER),
                new Employee("Pedro Santana", "Gerente de projetos", "pedro.santana@acme.com", "1234", "123456", RoleEnum.MANAGER),
                new Employee("Rodrigo Silva", "Desenvolvedor Java", "rodrigo.silva@acme.com", "1234", "123456", RoleEnum.USER),
                new Employee("Mariana Garcia", "Coordenadora", "mariana.garcia@acme.com", "1234", "123456", RoleEnum.HR),
                new Employee("Roger Flores", "Desenvolvedor Python", "roger.flores@acme.com", "1234", "123456", RoleEnum.USER)
        };
    }

    @Ignore
    public Employee() {
        knowledgeSet = new HashSet<>();
        knowledgeSet.add(Knowledge.ROOT);

        Set<Integer> set = new TreeSet<>();
        for (Knowledge k : knowledgeSet) {
            set.add(k.getId());
        }
        this.knowledgeSetStr = gson.toJson(set, setType);
    }

    @Ignore
    public Employee(String name, String role) {
        this();

        this.name = name;
        this.role = role;
    }

    public Employee(String name, String role, String email, String pin, String password,
                    RoleEnum function) {
        this();

        this.name = name;
        this.role = role;
        this.email = email;
        this.pin = pin;
        this.password = password;
        this.function = function;
    }

    public void setKnowledgeSet() {
        knowledgeSet = new HashSet<>();
        Set<Integer> set;
        set = gson.fromJson(this.knowledgeSetStr, setType);
        for (int i : set) {
            knowledgeSet.add(Knowledge.getKnowledgeFromList(i));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getFunction() {
        return function;
    }

    public void setFunction(RoleEnum function) { this.function = function; }

    public String getKnowledgeSetStr() {
        return knowledgeSetStr;
    }

    public void setKnowledgeSetStr(String knowledgeSetStr) {
        this.knowledgeSetStr = knowledgeSetStr;
    }

    public Set<Knowledge> getKnowledgeSet() {
        return knowledgeSet;
    }

    public void addKnowledgeArrayById(List<Integer> ids, Context context) {
        for (int id : ids) {
            Knowledge k = RoomDbUtils.getInstance().getKnowledgeById(id);

            this.addKnowledge(k, context);
        }
    }

    public void addKnowledge(Knowledge knowledge, Context context){
        if(knowledge != null){
            knowledgeSet.add(knowledge);

            Set<Integer> set = new TreeSet<>();
            for (Knowledge k : knowledgeSet) {
                set.add(k.getId());
            }

            this.knowledgeSetStr = gson.toJson(set, setType);

            knowledge.count(this);
            if (knowledge.getUp() > 0) {
                Knowledge up = Knowledge.getKnowledgeFromList(knowledge.getUp());
                addKnowledge(up, context);
            }
        }
    }

    public Knowledge getRootKnowledge(){
        for (Knowledge knowledge : knowledgeSet) {
            if (knowledge.getUp() == 0) {
                return knowledge;
            }

            Knowledge up = Knowledge.getKnowledgeFromList(knowledge.getUp());
            if(up.getUp() == 0) return up;
        }

        return null;
    }

    public void login() {
        this.logged = true;
    }

    public void logout() {
        this.logged = false;
    }

    public boolean isLogged() {
        return this.logged;
    }

    public boolean hasAKnowledge(String knowledgeName) {
        for (Knowledge knowledge : knowledgeSet) {
            if (knowledge.getName().equals(knowledgeName)) {
                return true;
            }
        }

        return false;
    }

    public boolean isPrimeiroAcesso() {
        return this.password == null;
    }
}
