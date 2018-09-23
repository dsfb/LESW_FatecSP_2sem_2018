package com.lesw.tree_knowledge;

import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;
import com.reactiveandroid.query.Select;

import java.util.HashSet;
import java.util.Set;

@Table(name = "Employee", database = AppDatabase.class)
class Employee {

    @PrimaryKey
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "email")
    private String email;

    @Column(name = "pin")
    private String pin;

    @Column(name = "password")
    private String password;

    @Column(name = "function")
    private RoleEnum function;

    private Boolean logged = false;

    private Set<Knowledge> knowledgeSet;

    public Employee() {
        knowledgeSet = new HashSet<>();
        knowledgeSet.add(Knowledge.ROOT);
    }

    public Employee(String name, String role) {
        this();

        this.name = name;
        this.role = role;
    }

    public Employee(int id, String name, String role, String email, String pin, String password,
                    RoleEnum function) {
        this();

        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.pin = pin;
        this.password = password;
        this.function = function;
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

    public Set<Knowledge> getKnowledgeSet() {
        return knowledgeSet;
    }

    public void addKnowledge(Knowledge knowledge){
        if(knowledge != null){
            knowledgeSet.add(knowledge);
            knowledge.count(this);
            Knowledge up = Select.from(Knowledge.class).where("id=", knowledge.getUp()).fetchSingle();
            addKnowledge(up);
        }
    }

    public Knowledge getRootKnowledge(){
        for (Knowledge knowledge : knowledgeSet) {
            Knowledge up =  Select.from(Knowledge.class).where("id=", knowledge.getUp()).fetchSingle();
            if(up == null) return knowledge;
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
