package com.lesw.tree_knowledge;

import com.activeandroid.serializer.TypeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public abstract class BaseGsonSerializer extends TypeSerializer {
    private Gson gson;

    public BaseGsonSerializer() {
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public Class<?> getDeserializedType() {
        return TypeToken.get(getTypeDeserialize()).getRawType();
    }

    @Override
    public Object serialize(Object data) {
        if (data != null) {
            return gson.toJson(data);
        }
        return null;
    }

    @Override
    public Object deserialize(Object data) {
        if (data != null) {
            return gson.fromJson(data.toString(), getTypeDeserialize());
        }
        return null;
    }

    protected abstract Type getTypeDeserialize();
}
