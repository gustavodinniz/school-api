package br.com.alura.school.schoolapi.config.utils;

import lombok.extern.java.Log;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.quirks.PostgresQuirks;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
public abstract class NativeBaseRepository {

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


}
