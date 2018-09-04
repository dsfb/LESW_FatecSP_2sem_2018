package com.ihc.tree_knowledge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Employee {

    private Integer id;
    private String name;
    private String role;
    private String email;
    private String pin;
    private String password;
    private RoleEnum function;
    private Boolean logged = false;

    private Set<Knowledge> knowledgeList;

    public Employee() {
        knowledgeList = new HashSet<>();
        knowledgeList.add(Knowledge.ROOT);
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
        return knowledgeList;
    }

    public void addKnowledge(Knowledge knowledge){
        if(knowledge != null){
            knowledgeList.add(knowledge);
            knowledge.count(this);
            if(knowledge.getUp() != null){
                addKnowledge(knowledge.getUp());
            }
        }
    }

    public Knowledge getRootKnowledge(){
        for (Knowledge knowledge : knowledgeList) {
            if(knowledge.getUp() == null) return knowledge;
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
        for (Knowledge knowledge : knowledgeList) {
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
