package com.denesgarda.ProjectBloodMoon.connection;

import com.denesgarda.ProjectBloodMoon.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection connect(Connection conn) {
        System.out.println("Connecting to server...");
        try {
            conn = DriverManager.getConnection("jdbc:mysql://98.164.253.104:3306/pbm?user=connector&password=dpass");
            java.sql.Connection finalConn = conn;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Closing connection...");
                try {
                    finalConn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException ex) {
                    System.out.println("Failed to close the connection.");
                }
            }));
            System.out.println("Connection established.");
            return conn;
        }
        catch(Exception e) {
            System.out.println("Failed to connect to server. Check your connection and try relaunching the game.");
            return null;
        }
    }
}
