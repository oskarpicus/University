package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private final Properties properties;
    private final static Logger logger = LogManager.getLogger();
    private Connection connection = null;

    public JdbcUtils(Properties properties) {
        this.properties = properties;
    }

    private Connection getNewConnection(){
        logger.traceEntry();
        String url = properties.getProperty("jdbc.url");
        String password = properties.getProperty("jdbc.password");
        String user = properties.getProperty("jdbc.user");
        try{
            if(password!=null && user !=null)
                connection = DriverManager.getConnection(url, user, password);
            else
                connection = DriverManager.getConnection(url);
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit();
        return connection;
    }

    /**
     * Method for getting a connection to the database
     * @return connection: Connection
     */
    public Connection getConnection(){
        logger.traceEntry();
        try{
            if(connection==null || connection.isClosed())
                connection = this.getNewConnection();
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit();
        return connection;
    }
}
