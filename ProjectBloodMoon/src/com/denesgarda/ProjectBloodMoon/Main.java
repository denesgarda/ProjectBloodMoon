package com.denesgarda.ProjectBloodMoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import static com.denesgarda.ProjectBloodMoon.game.Game.exit;
import static com.denesgarda.ProjectBloodMoon.game.Game.game;

public class Main {
    public static java.sql.Connection conn = null;
    public static Logger logger = Logger.getLogger(Main.class.getName());
    public static BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
    public static String version = "0.2.0";
    public static String fullVersion = "beta0.2.0";

    public static void main(String[] args) {
        logger.info("Project: Blood Moon, by DJHK, " + fullVersion);
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

            System.out.println("Checking version...");
            String query = "SELECT version FROM pbm.versions";
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            rs.last();
            if(!checkVersion(version, rs.getString("version"))) {
                Statement stmt2 = conn.createStatement();
                String query2 = "SELECT link FROM pbm.versions WHERE version = \"" + rs.getString("version") + "\"";
                ResultSet rs2 = stmt2.executeQuery(query2);
                rs2.next();
                System.out.println("You are using an outdated version of the game. Please download the newest version here: " + rs2.getString("link") + "\n\n(Press [ENTER] to continue)");
                consoleInput.readLine();
                exit();
            }

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        Statement stmt = conn.createStatement();
                        String query = "SELECT * FROM pbm.accounts";
                        stmt.executeQuery(query);
                    }
                    catch(Exception e1) {
                        try {
                            conn = DriverManager.getConnection("jdbc:mysql://98.164.253.104:3306/pbm?user=connector&password=dpass");
                        }
                        catch(Exception e2) {
                            System.out.println("You are disconnected from the internet! Please reconnect if you want to save your progress!");
                        }
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 10000, 10000);
            game();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connect to server. Check your connection and try relaunching the game.");
        }
    }

    public static boolean checkVersion(String currentVersion, String latestVersion) {
        int preCurrentVersion = Integer.parseInt(currentVersion.substring(0, currentVersion.indexOf('.')));
        int preLatestVersion = Integer.parseInt(latestVersion.substring(0, latestVersion.indexOf('.')));
        int midCurrentVersion = Integer.parseInt(currentVersion.substring(currentVersion.indexOf('.') + 1, currentVersion.indexOf('.', 2)));
        int midLatestVersion = Integer.parseInt(latestVersion.substring(latestVersion.indexOf('.') + 1, latestVersion.indexOf('.', 2)));
        int postCurrentVersion = Integer.parseInt(currentVersion.substring(currentVersion.indexOf('.', 2) + 1));
        int postLatestVersion = Integer.parseInt(latestVersion.substring(latestVersion.indexOf('.', 2) + 1));

        if(preCurrentVersion > preLatestVersion) {
            return true;
        }
        else if(midCurrentVersion > midLatestVersion) {
            return true;
        }
        else return postCurrentVersion >= postLatestVersion;
    }
}
