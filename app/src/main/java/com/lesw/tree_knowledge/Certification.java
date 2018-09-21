package com.lesw.tree_knowledge;

public class Certification {

    private Integer id;
    private String knowledge;
    private String userName;
    private String date;
    private String status;
    private String certification;

    public Certification() {
    }

    public Certification(String knowledge, String userName, String date, String status, String certification) {
//        this.id = id;
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
