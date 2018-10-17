package com.lesw.tree_knowledge;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "certification")
public class Certification {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "knowledge")
    private String knowledge;

    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "certification")
    private String certification;

    public Certification(String knowledge, String userName, String date, String status, String certification) {
        this.knowledge = knowledge;
        this.userName = userName;
        this.date = date;
        this.status = status;
        this.certification = certification;
    }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

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
