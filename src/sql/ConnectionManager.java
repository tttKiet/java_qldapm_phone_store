package sql;
import java.sql.*;

public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/phoneStore";    
    private static String username = "root";   
    private static String password = "";
    private static Connection con;

    public static Connection getConnection() {
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                ex.printStackTrace();
                System.out.println("Failed to create the database connection."); 
            }
        return con;
    }
}