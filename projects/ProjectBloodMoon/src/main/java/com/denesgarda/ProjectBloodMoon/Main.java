package com.denesgarda.ProjectBloodMoon;

import com.denesgarda.ProjectBloodMoon.utility.Utility;

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

    public static void main(String[] args) throws IOException {
        try {
            //Set version
            version = Double.parseDouble(Utility.getProperty("version"));
            logger.info("Project: Blood Moon, by DJHK, beta" + version);

            //Finalize update
            File file = new File("Updater.jar");
            if(file.exists()) {
                System.out.println("Finalizing update...");
                file.delete();
                File bat = new File("update.bat");
                bat.delete();
                File command = new File("update.command");
                command.delete();
                File sh = new File("update.sh");
                sh.delete();

                Utility.setProperty("vwu", "true");
            }

            //Connect
            System.out.println("Connecting to server...");
            conn = DriverManager.getConnection("jdbc:mysql://98.164.253.104:3306/pbm?user=pbm&password=" + Utility.decrypt(Utility.getProperty("enc")));
            java.sql.Connection finalConn = conn;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                File pkg = new File("package.zip");
                File bat = new File("update.bat");
                if(!bat.exists()) {
                    if (pkg.exists()) {
                        pkg.delete();
                    }
                }
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
                System.out.println("Outdated version detected.");
                System.out.println("Preparing update...");

                String query2 = "SELECT link FROM pbm.versions WHERE version = \"" + rs.getString("version") + "\"";
                PreparedStatement stmt2 = conn.prepareStatement(query2, ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);
                ResultSet rs2 = stmt2.executeQuery();
                rs2.last();
                String pkg = rs2.getString("link");

                String query3 = "SELECT * FROM pbm.updater";
                PreparedStatement stmt3 = conn.prepareStatement(query3);
                ResultSet rs3 = stmt3.executeQuery();
                rs3.next();

                String[] queue = {rs3.getString("jar"), rs3.getString("bat"), rs3.getString("command"), rs3.getString("sh"), pkg};

                System.out.println("Updating...");

                new File("package.zip").createNewFile();

                Utility.download(queue[0], "Updater.jar");
                Utility.download(queue[1], "update.bat");
                Utility.download(queue[2], "update.command");
                Utility.download(queue[3], "update.sh");
                Utility.download(queue[4], "package.zip");

                System.exit(100);
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
            if(Boolean.parseBoolean(Utility.getProperty("vwu"))) {
                System.out.println("\nUpdate complete! Updated to beta" + Utility.getProperty("version"));
                System.out.println("""
                        What's new?
                            - Renewed everything
                        (Press [ENTER] to continue)""");
                consoleInput.readLine();
                Utility.setProperty("vwu", "false");
            }

            //Launch game
            System.out.println("LAUNCHING GAME!");
            System.exit(0);
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
