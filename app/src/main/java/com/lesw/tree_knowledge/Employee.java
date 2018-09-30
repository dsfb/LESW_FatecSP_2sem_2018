package com.lesw.tree_knowledge;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;

@Entity
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

    public static Employee[] populateData() {
        return new Employee[] {
                new Employee("Diego Alves", "Desenvolvedor Java", "diego.alves@acme.com", "1234", "123456", RoleEnum.USER),
                new Employee("Pedro Santana", "Gerente de projetos", "pedro.santana@acme.com", "1234", "123456", RoleEnum.MANAGER),
                new Employee("Rodrigo Silva", "Desenvolvedor Java", "rodrigo.silva@acme.com", "1234", "123456", RoleEnum.USER),
                new Employee("Mariana Garcia", "Coordenadora", "mariana.garcia@acme.com", "1234", "123456", RoleEnum.HR),
                new Employee("Roger Flores", "Desenvolvedor Python", "roger.flores@acme.com", "1234", "123456", RoleEnum.USER)
        };
    }

    public Employee() {
        knowledgeSet = new HashSet<>();
        knowledgeSet.add(Knowledge.ROOT);
    }

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

    public Set<Knowledge> getKnowledgeSet() {
        return knowledgeSet;
    }

    public void addKnowledge(Knowledge knowledge, Context context){
        if(knowledge != null){
            knowledgeSet.add(knowledge);
            knowledge.count(this, context);
            Knowledge up = RoomDbUtils.getKnowledgeById(knowledge.getUp(), context);
            addKnowledge(up, context);
        }
    }

    public Knowledge getRootKnowledge(Context context){
        for (Knowledge knowledge : knowledgeSet) {
            Knowledge up = RoomDbUtils.getKnowledgeById(knowledge.getUp(), context);
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
