package br.com.alura.school.schoolapi.config.utils;

import lombok.extern.java.Log;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.quirks.PostgresQuirks;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.*;

@Log
public abstract class NativeBaseRepository {

    protected void executeNativeScript(String sql) {
        executeNativeScript(sql, new HashMap<>());
    }

    protected void executeNativeScript(String sql, Map<String, Object> params) {
        Sql2o sql2o = new Sql2o(getDataSource(), new PostgresQuirks());
        Connection con = null;

        try {

            con = sql2o.open();
            Query query = con.createQuery(sql);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.addParameter(entry.getKey(), entry.getValue());
            }

            query.executeUpdate();
            con.commit();

        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Throwable e) {
                    log.warning("Error on closing database connection");
                }
            }
        }
    }

    public abstract DataSource getDataSource();

    protected <X> List<X> parseNativeQuery(String sqlQuery, Class<X> returnType) {
        return parseNativeQuery(sqlQuery, returnType, new HashMap<>());
    }

    protected <X> List<X> parseNativeQuery(String sqlQuery, Class<X> returnType, Map<String, Object> params) {
        Sql2o sql2o = new Sql2o(getDataSource(), new PostgresQuirks());

        try (Connection con = sql2o.open()) {
            org.sql2o.Query query = con.createQuery(sqlQuery);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.addParameter(entry.getKey(), entry.getValue());
            }
            return query.executeAndFetch(returnType);
        }
    }

    protected <X> X parseNativeQueryToSingleResult(String sqlQuery, Class<X> returnType) {
        return parseNativeQueryToSingleResult(sqlQuery, returnType, new HashMap<>());
    }

    protected <X> X parseNativeQueryToSingleResult(String sqlQuery, Class<X> returnType, Map<String, Object> params) {
        Sql2o sql2o = new Sql2o(getDataSource(), new PostgresQuirks());

        try (Connection con = sql2o.open()) {
            org.sql2o.Query query = con.createQuery(sqlQuery);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.addParameter(entry.getKey(), entry.getValue());
            }
            return query.executeAndFetchFirst(returnType);
        }
    }

    public String replaceParams(String sqlQuery, Map<String, Object> params) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Map<String, Object> treeMap = new TreeMap<>(
                (current, next) -> {
                    if (current.length() > next.length()) {
                        return -1;
                    }

                    if (current.length() < next.length()) {
                        return 1;
                    }
                    return current.compareTo(next);
                }
        );
        treeMap.putAll(params);

        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            if (entry.getValue() instanceof Date) {
                sqlQuery = sqlQuery.replaceAll(String.format(":%s", entry.getKey()), String.format("'%s'", dateFormat.format(entry.getValue())));
                continue;
            }

            if (entry.getValue() instanceof Number) {
                sqlQuery = sqlQuery.replaceAll(String.format(":%s", entry.getKey()), entry.getValue() + "");
                continue;
            }

            if (entry.getValue() instanceof String) {
                sqlQuery = sqlQuery.replaceAll(String.format(":%s", entry.getKey()), String.format("'%s'", entry.getValue()));
                continue;
            }

            sqlQuery = sqlQuery.replaceAll(String.format(":%s", entry.getKey()), String.valueOf(entry.getValue()));
        }

        return sqlQuery;
    }



}
