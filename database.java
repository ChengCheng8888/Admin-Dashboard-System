package application;
import java.sql.*;

public class database {
    public static Connection connectDb(){
        try{
            Connection connect = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/testconnect", "root", "");
            return connect;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
