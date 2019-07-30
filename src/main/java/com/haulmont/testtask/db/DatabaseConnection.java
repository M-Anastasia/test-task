package com.haulmont.testtask.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by fishn on 23.07.2019.
 */
public class DatabaseConnection {

    public static Connection getConnection() {

        Connection con = null;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:file:testtaskdb", "SA", "");
            if (con!= null){
                System.out.println("Connection created successfully");
            }else{
                System.out.println("Problem with creating connection");
            }
        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return con;
    }
}
