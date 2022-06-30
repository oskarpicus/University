package repository.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.sqlite.SQLiteConfig;

public class JdbcUtils {

    private Properties jdbcProps;

    public JdbcUtils(Properties props) {
        jdbcProps = props;
    }

    private Connection instance = null;

    private Connection getNewConnection() {
        String url = jdbcProps.getProperty("jdbc.url");
        String user = jdbcProps.getProperty("jdbc.user");
        String pass = jdbcProps.getProperty("jdbc.pass");
        Connection con = null;
        try {
            if (user != null && pass != null)
                con = DriverManager.getConnection(url, user, pass);
            else {
                SQLiteConfig config = new SQLiteConfig();
                config.enforceForeignKeys(true);
                con = DriverManager.getConnection(url, config.toProperties());
            }
        } catch (SQLException e) {
            System.out.println("Error getting connection " + e);
        }
        return con;
    }

    public Connection getConnection() {
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return instance;
    }
}