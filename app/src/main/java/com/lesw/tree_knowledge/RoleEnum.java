package com.lesw.tree_knowledge;

public enum RoleEnum {

    MANAGER("MANAGER"), HR("HR"), USER("USER");

    private final String value;

    private RoleEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
