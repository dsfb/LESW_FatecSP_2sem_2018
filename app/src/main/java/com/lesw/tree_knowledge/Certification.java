package com.lesw.tree_knowledge;

import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;

@Table(name = "Certification", database = AppDatabase.class)
public class Certification {

    @PrimaryKey
    private Integer id;

    @Column(name = "title")
    private String knowledge;

    @Column(name = "title")
    private String userName;

    @Column(name = "title")
    private String date;

    @Column(name = "title")
    private String status;

    @Column(name = "title")
    private String certification;

    public Certification(String knowledge, String userName, String date, String status, String certification) {
        this.knowledge = knowledge;
        this.userName = userName;
        this.date = date;
        this.status = status;
        this.certification = certification;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCertification() { return certification; }

    public void setCertification(String certification) {
        this.certification = certification;
    }
}
