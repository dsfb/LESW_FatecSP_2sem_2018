package com.lesw.tree_knowledge;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

public class Converters {
    @TypeConverter
    public static RoleEnum deserialize(String reString) {
        return reString == null ? null : RoleEnum.valueOf(reString);
    }

    @TypeConverter
    public static String roleEnumToString(RoleEnum re) {
        return re == null ? null : re.toString();
    }


    private static Type setType = new TypeToken<Set<Integer>>(){}.getType();

    private static Gson gson = new Gson();

    @TypeConverter
    public static Set<Integer> deserializeSetInteger(String siString) {
        return siString == null ? new TreeSet<>() : gson.fromJson(siString, setType);
    }

    @TypeConverter
    public static String setIntegerToString(Set<Integer> si) {
        return si == null ? null : gson.toJson(si, setType);
    }
}
