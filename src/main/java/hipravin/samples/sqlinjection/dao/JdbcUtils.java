package hipravin.samples.sqlinjection.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class JdbcUtils {
    private JdbcUtils() {
    }

    public static Long getLongNullable(ResultSet rs, String columnName) throws SQLException {
        long value = rs.getLong(columnName);
        return (rs.wasNull()) ? null : value;
    }
}
