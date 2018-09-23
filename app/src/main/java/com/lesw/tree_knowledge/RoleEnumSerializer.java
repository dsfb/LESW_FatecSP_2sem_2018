package com.lesw.tree_knowledge;

import com.reactiveandroid.internal.serializer.TypeSerializer;

import io.reactivex.annotations.NonNull;

public class RoleEnumSerializer extends TypeSerializer<RoleEnum, String> {
    @NonNull
    public String serialize(@NonNull RoleEnum re) {
        return re.toString();
    }

    @NonNull
    public RoleEnum deserialize(@NonNull String reString) {
        return RoleEnum.valueOf(reString);
    }
}
