package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by George Stratulat on 30/03/2017.
 */
public class DBConnection {
    private final static String URL = "jdbc:mysql://5.196.26.114:3306/";
    private final static String DB_NAME = "foosball";
    private final static String USER = "dat16j";
    private final static String PASS = "123";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    URL + DB_NAME,
                    USER,
                    PASS);
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

