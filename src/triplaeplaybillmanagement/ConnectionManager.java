/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package triplaeplaybillmanagement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jaideep
 */
public class ConnectionManager {
    
    private static String userName = "root";
    private static String password = "root";
    private static String hostName = "127.0.0.1";
    private static String dbName = "CBM";
    private static String url = "jdbc:mysql://" +hostName +":3306/" + dbName+"?autoReconnect=true&useSSL=false";
    private static Connection connection;
    private static String urlString;
    private static String driverName = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection(){
        try{
            Class.forName(driverName);
            try{
             connection = DriverManager.getConnection(url, userName, password);
             System.out.println(" Connection Successful.");
            }
             catch( SQLException ex){
            //Log an exception
                System.out.println(" Failed to create the database connection. ");
                ex.printStackTrace();
            }
        }catch( ClassNotFoundException ex){
            //logging exception
            System.out.println(" Driver Not Found");            

        }
        return connection;
    }
    
    public static void main(String [] args){
        getConnection();
        System.out.println(url);
    }
}
