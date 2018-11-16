package com.lesw.tree_knowledge.dao;

import android.arch.persistence.room.TypeConverter;

import com.lesw.tree_knowledge.model.RoleEnum;

public class Converters {
    @TypeConverter
    public static RoleEnum deserialize(String reString) {
        return reString == null ? null : RoleEnum.valueOf(reString);
    }

    @TypeConverter
    public static String roleEnumToString(RoleEnum re) {
        return re == null ? null : re.toString();
    }
}
