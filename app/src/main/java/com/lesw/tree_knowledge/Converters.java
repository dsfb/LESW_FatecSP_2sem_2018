package com.lesw.tree_knowledge;

public class Converters {
    public static RoleEnum stringToRoleEnum(String reString) {
        return reString == null ? null : RoleEnum.valueOf(reString);
    }

    public static String roleEnumToString(RoleEnum re) {
        return re == null ? null : re.toString();
    }
}
