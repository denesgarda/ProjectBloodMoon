package com.denesgarda.ProjectBloodMoon;

import com.denesgarda.ProjectBloodMoon.utility.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {
    public static Connection conn;
    public static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        //Log
        logger.info("Project: Blood Moon Launcher");

        //Connect
        try {
            System.out.println("Connecting to server...");
            conn = DriverManager.getConnection("jdbc:mysql://98.164.253.104:3306/pbm?user=pbm&password=" + Utility.decrypt(Utility.getProperty("enc")));
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
        }
        catch(Exception e) {
            System.out.println("Failed to connect to server.");
            System.exit(0);
        }

        //Check if game exists
        File file = new File("ProjectBloodMoon");
        if(file.exists()) {
            System.out.println("EXISTS");
        }
        else {
            System.out.println("DOESN'T EXIST");
        }
    }
}
