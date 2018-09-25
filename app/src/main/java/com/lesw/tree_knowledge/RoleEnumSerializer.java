package com.lesw.tree_knowledge;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RoleEnumSerializer extends BaseGsonSerializer {
    @Override
    protected Type getTypeDeserialize() {
        return new TypeToken<RoleEnum>() {
        }.getType();
    }
}
