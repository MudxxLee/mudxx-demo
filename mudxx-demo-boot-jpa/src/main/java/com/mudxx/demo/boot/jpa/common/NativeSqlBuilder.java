package com.mudxx.demo.boot.jpa.common;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author laiw
 * @date 2023/11/14 15:20
 */
public class NativeSqlBuilder {

    public static String generateFullSql(String databaseUser, String tableNamePrefix, String tableNameSuffix,
                                         String[] selectFields, String whereAppends, String[] groupFields) {
        String tableName = databaseUser + "." + tableNamePrefix + tableNameSuffix;
        String tableSelectFields = String.join(", ", selectFields);
        String tableGroupFields = String.join(", ", groupFields);
        return "select " + tableSelectFields + " " +
                "from " + tableName + " " +
                "where " + whereAppends + " " +
                "group by " + tableGroupFields;
    }

    public static String generateFullSql(String querySql, String[] selectFields, String[] groupFields) {
        List<String> querySqlList = Stream.of(querySql).collect(Collectors.toList());
        return generateFullSql(querySqlList, selectFields, groupFields);
    }

    public static String generateFullSql(List<String> querySqlList, String[] selectFields, String[] groupFields) {
        return "select " + String.join(", ", selectFields) + " " +
                "from ( " + String.join(" union all ", querySqlList) + " ) t " +
                "group by " + String.join(", ", groupFields);
    }

    public static String generateSimpleSql(String databaseUser, String tableFullName, String[] selectFields, String whereAppends) {
        String tableName = databaseUser + "." + tableFullName;
        String tableSelectFields = String.join(", ", selectFields);
        return "select " + tableSelectFields + " " +
                "from " + tableName + " " +
                "where " + whereAppends;
    }

    public static String generateNativeSql(String querySql, String[] orderFields, boolean isCount) {
        List<String> querySqlList = Stream.of(querySql).collect(Collectors.toList());
        return generateNativeSql(querySqlList, orderFields, isCount);
    }

    public static String generateNativeSql(List<String> querySqlList, String[] orderFields, boolean isCount) {
        StringBuilder sql = new StringBuilder();
        if (isCount) {
            sql.append("select count(1) from ( ");
        } else {
            sql.append("select t.* from ( ");
        }
        sql.append(String.join(" union all ", querySqlList));
        sql.append(" ) t ");
        if (isCount || ObjectUtils.isEmpty(orderFields)) {
            return sql.toString();
        }
        sql.append("order by ");
        sql.append(Arrays.stream(orderFields).map(e -> {
            if (!e.startsWith("t.")) {
                return "t." + e;
            }
            return e;
        }).collect(Collectors.joining(", ")));
        return sql.toString();
    }

    public static void main(String[] args) {
        String[] selectFields = new String[]{
                "currency_code",
                "count(1) count_",
                "sum(sum_unblended_cost) sum_"
        };
        String[] groupFields = new String[]{
                "currency_code",
        };
        List<String> nativeSqlList = new ArrayList<>();
        nativeSqlList.add("select * from dual");
        nativeSqlList.add("select * from dual");
        System.out.println(generateFullSql(nativeSqlList, selectFields, groupFields));
        System.out.println(generateFullSql("select * from dual", selectFields, groupFields));
    }

}
