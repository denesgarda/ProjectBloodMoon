package com.denesgarda.ProjectBloodMoon;

import com.denesgarda.ProjectBloodMoon.game.Game;
import com.denesgarda.ProjectBloodMoon.utility.Utility;
import com.denesgarda.Prop4j.data.PropertiesFile;

import java.io.*;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class Main {
    public static Connection conn;
    public static Logger logger = Logger.getLogger(Main.class.getName());
    public static BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
    public static double version;
    public static PropertiesFile properties = new PropertiesFile("properties.properties");

    public static void main(String[] args) throws IOException {
        try {
            //Set version
            version = Double.parseDouble(properties.getProperty("version"));
            logger.info("Project: Blood Moon, by DJHK, beta" + version);

            //Connect
            System.out.println("Connecting to server...");
            conn = DriverManager.getConnection("jdbc:mysql://98.164.253.104:3306/pbm?user=pbm&password=" + Utility.decrypt(properties.getProperty("enc")));
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

            //Check version
            System.out.println("Checking version...");
            String query = "SELECT version FROM pbm.versions";
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            rs.last();
            if(!(version >= Double.parseDouble(rs.getString("version")))) {
                System.out.println("Outdated version detected. Please update!");
                System.exit(0);
            }

            //Check connection
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
                            System.out.println("You are disconnected from the internet! Please reconnect if you don't want to lose progress!");
                        }
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 10000, 10000);

            //Show news
            if(Boolean.parseBoolean(properties.getProperty("vwu"))) {
                System.out.println("\nUpdate complete! Updated to beta" + properties.getProperty("version"));
                System.out.println("""
                        What's new since beta0.83?
                            - File reading and writing changes
                            - Bug fixes
                            - Changed the note on the main menu
                        (Press [ENTER] to continue)""");
                consoleInput.readLine();
                properties.setProperty("vwu", "false");
            }

            //Launch game
            Game.game();
        }
        catch(FileNotFoundException e) {
            System.out.println("Required files are missing. Cannot run game.");
            System.exit(0);
        }
        catch(Exception e) {
            System.out.println("An error occurred. Press [ENTER] to exit or type \"/debug\" to see the error.");
            String input = consoleInput.readLine();
            if(input.equalsIgnoreCase("/debug")) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}
