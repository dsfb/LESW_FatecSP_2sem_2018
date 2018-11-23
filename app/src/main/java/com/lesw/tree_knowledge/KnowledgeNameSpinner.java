package com.lesw.tree_knowledge;

import java.io.Serializable;

public class KnowledgeNameSpinner implements Serializable {
    private String knowledgeNameId;
    private String knowledgeName;

    public KnowledgeNameSpinner(String knowledgeNameId, String knowledgeName) {
        this.knowledgeNameId = knowledgeNameId;
        this.knowledgeName = knowledgeName;
    }

    public String getKnowledgeNameId() {
        return knowledgeNameId;
    }

    public void setKnowledgeNameId(String knowledgeNameId) {
        this.knowledgeNameId = knowledgeNameId;
    }

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }

    @Override
    public String toString() {
        return this.knowledgeName;
    }
}
