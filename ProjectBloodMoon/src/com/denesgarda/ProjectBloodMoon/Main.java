package com.denesgarda.ProjectBloodMoon;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import static com.denesgarda.ProjectBloodMoon.game.Game.exit;
import static com.denesgarda.ProjectBloodMoon.game.Game.game;

public class Main {
    public static java.sql.Connection conn = null;
    public static Logger logger = Logger.getLogger(Main.class.getName());
    public static BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
    public static double version = 0;

    public static void main(String[] args) throws IOException {
        logger.info("Project: Blood Moon, by DJHK");
        System.out.println("Connecting to server...");
        try {
            Properties gameProperties = new Properties();
            gameProperties.load(new FileInputStream("game.properties"));
            Properties properties = new Properties();
            properties.load(new FileInputStream("properties.properties"));
            version = Double.parseDouble(gameProperties.getProperty("version"));
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
            if(!(version >= Double.parseDouble(rs.getString("version")))) {
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
                            System.out.println("You are disconnected from the internet! Please reconnect if you don't want to lose progress!");
                        }
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 10000, 10000);
            game();
        }
        catch(FileNotFoundException e) {
            System.out.println("Required files are missing. Cannot start game.");
        }
        catch(CommunicationsException e) {
            System.out.println("Failed to connect to server. Check your connection and try relaunching the game.");
        }
        catch(Exception e) {
            System.out.println("Something went wrong; and error occurred! :(\nPress enter to exit the game. Type \"/debug\" to view the error.");
            String input = consoleInput.readLine();
            if(input.equalsIgnoreCase("/debug")) {
                e.printStackTrace();
            }
            System.exit(-1);
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
