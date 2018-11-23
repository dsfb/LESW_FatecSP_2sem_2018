package com.lesw.tree_knowledge;

import java.io.Serializable;

public class KnowledgeLevelSpinner implements Serializable {
    private String knowledgeLevelId;
    private String knowledgeLevel;

    public KnowledgeLevelSpinner(String knowledgeLevelId, String knowledgeLevel) {
        this.knowledgeLevelId = knowledgeLevelId;
        this.knowledgeLevel = knowledgeLevel;
    }

    public String getKnowledgeLevelId() {
        return knowledgeLevelId;
    }

    public void setKnowledgeLevelId(String knowledgeLevelId) {
        this.knowledgeLevelId = knowledgeLevelId;
    }

    public String getKnowledgeLevel() {
        return knowledgeLevel;
    }

    public void setKnowledgeLevel(String knowledgeLevel) {
        this.knowledgeLevel = knowledgeLevel;
    }

    @Override
    public  String toString() {
        return this.knowledgeLevel;
    }
}
