package xyz.pary.it_one.cup2022.util;

import java.util.HashMap;
import java.util.Map;

public class Types {

    private static final Types INSTANCE = new Types();

    private final Map<String, String> types = new HashMap<>();

    private Types() {
        types.put("int1", "TINYINT");
        types.put("int2", "SMALLINT");
        types.put("int4", "INTEGER");
        types.put("int", "INTEGER");
        types.put("int8", "BIGINT");
        types.put("char", "CHARACTER");
        types.put("varchar", "CHARACTER VARYING");
    }

    private String get(String type) {
        return types.get(type);
    }

    public static String resolveType(String t) {
        int i = t.lastIndexOf("(");
        t = i == -1 ? t : t.substring(0, i);
        String rt = INSTANCE.get(t);
        return rt == null ? t.toUpperCase() : rt;
    }
}
