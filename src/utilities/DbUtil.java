package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbUtil {
    private static String connectionString;
    private static Connection con;

    static {
        Properties p = new Properties();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties");

        try {
            p.load(input);
            input.close();
            connectionString = new StringBuilder()
                    .append(p.getProperty("dburl")).append("?user=")
                    .append(p.getProperty("user")).append("&login=")
                    .append(p.getProperty("login")).append("&useUnicode=")
                    .append(p.getProperty("useUnicode"))
                    .append("&characterEncoding=")
                    .append(p.getProperty("characterEncoding")).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(connectionString);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (con == null) createConnection();
        return con;
    }

    public static void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
