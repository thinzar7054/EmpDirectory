package Config;

import java.sql.SQLException;

public class AutoID {
    private final DBConfig dbConfig;

    public AutoID() {
        this.dbConfig = DBConfig.getInstance();
    }

    public String getAdminID() throws SQLException {
        return generateID("adm_id", "admin", "AD-", 5);
    }

    private String generateID(String field, String table, String prefix, int digits) throws SQLException {
        // ORDER BY the numeric part after the prefix
        String query = String.format(
            "SELECT %s FROM %s ORDER BY CAST(SUBSTRING(%s, %d) AS UNSIGNED) DESC LIMIT 1",
            field, table, field, prefix.length() + 1
        );
 
        try (var rs = dbConfig.executeQuery(query)) {
            if (rs.next()) {
                String lastId = rs.getString(field);
                int num = Integer.parseInt(lastId.substring(prefix.length())) + 1;
                return String.format("%s%0" + digits + "d", prefix, num);
            }
            return prefix + String.format("%0" + digits + "d", 1);
        }
    }
}