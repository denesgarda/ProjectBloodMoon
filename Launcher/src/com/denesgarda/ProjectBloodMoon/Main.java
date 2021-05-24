package com.denesgarda.ProjectBloodMoon;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.*;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class Main {
    public static Connection conn;
    public static Logger logger = Logger.getLogger(Main.class.getName());
    public static BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
    public static double version = 0;
    public static double launcherVersion = 1.2;

    public static void main(String[] args) {
        logger.info("Project: Blood Moon, by DJHK");
        System.out.println("Connecting to server...");
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("properties.properties"));
            version = Double.parseDouble(properties.getProperty("version"));
            conn = DriverManager.getConnection("jdbc:mysql://98.164.253.104:3306/pbm?user=pbm&password=" + decrypt(properties.getProperty("enc")));
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

            String query3 = "SELECT version FROM pbm.launchers";
            PreparedStatement stmt3 = conn.prepareStatement(query3, ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs3 = stmt3.executeQuery(query3);
            rs3.last();
            if(!(launcherVersion >= Double.parseDouble(rs3.getString("version")))) {
                Statement stmt2 = conn.createStatement();
                String query2 = "SELECT link FROM pbm.launchers WHERE version = \"" + rs3.getString("version") + "\"";
                ResultSet rs2 = stmt2.executeQuery(query2);
                rs2.next();
                System.out.println("You are using an expired package. Please download the newest version here: " + rs2.getString("link"));
                System.exit(0);
            }

            if(!(version >= Double.parseDouble(rs.getString("version")))) {
                System.out.println("You are using an outdated version of the game. Downloading new version...");
                Statement stmt2 = conn.createStatement();
                String query2 = "SELECT jarlink FROM pbm.versions WHERE version = \"" + rs.getString("version") + "\"";
                ResultSet rs2 = stmt2.executeQuery(query2);
                rs2.next();
                System.out.println("Downloading...");
                System.out.println("DO NOT EXIT THE LAUNCHER!");

                File oldJar = new File("ProjectBloodMoon.jar");
                oldJar.delete();
                URL website = new URL(rs2.getString("jarlink"));
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream("ProjectBloodMoon.jar");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

                properties.setProperty("version", rs.getString("version"));
                properties.setProperty("vwu", "true");
                properties.save(new ObjectOutputStream(new FileOutputStream("properties.properties")), "");

                System.out.println("Update finished.");
            }

            System.out.println("Launching game...");
            System.exit(3000);
        }
        catch(FileNotFoundException e) {
            System.out.println("Required files are missing. Cannot start game.");
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connect to server. Check your connection and try relaunching the game.");
        }
    }

    private static String decrypt(String text) {
        char[] characters = text.toCharArray();
        for(int i = 0; i < characters.length; i++) {
            characters[i] -= 3;
        }
        return String.valueOf(characters);
    }
    private static String encrypt(String text) {
        char[] characters = text.toCharArray();
        for(int i = 0; i < characters.length; i++) {
            characters[i] += 3;
        }
        return String.valueOf(characters);
    }
}
