package com.lesw.tree_knowledge;

import android.arch.persistence.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static RoleEnum deserialize(String reString) {
        return reString == null ? null : RoleEnum.valueOf(reString);
    }

    @TypeConverter
    public static String dateToTimestamp(RoleEnum re) {
        return re == null ? null : re.toString();
    }
}
