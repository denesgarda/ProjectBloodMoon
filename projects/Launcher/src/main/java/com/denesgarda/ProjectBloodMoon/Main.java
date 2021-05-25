package com.denesgarda.ProjectBloodMoon;

import com.denesgarda.ProjectBloodMoon.utility.Utility;

import java.io.*;
import java.sql.*;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static Connection conn;
    public static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException, IOException {
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

        //Check version
        System.out.println("Checking version...");
        Statement stmt3 = conn.createStatement();
        String query3 = "SELECT version FROM pbm.launcher";
        ResultSet rs3 = stmt3.executeQuery(query3);
        rs3.next();
        double currentVersion = Double.parseDouble(Utility.getProperty("launcherversion"));
        double latestVersion = Double.parseDouble(rs3.getString("version"));
        if(currentVersion < latestVersion) {
            Statement stmt = conn.createStatement();
            String query = "SELECT link FROM pbm.launcher WHERE version = \"" + latestVersion + "\"";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String link = rs.getString("link");
            System.out.println("You are using an outdated package. Please download the newest one here: " + link);
            System.exit(0);
        }

        //Check if game exists
        File file = new File("ProjectBloodMoon");
        if(file.exists()) {
            String query = "SELECT version FROM pbm.versions";
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            rs.last();

            String version = Utility.getPropertyDown("version");
            if(Double.parseDouble(version) < Double.parseDouble(rs.getString("version"))) {
                System.out.println("Updating...");
                String[] entries = file.list();
                try {
                    for (String s : entries) {
                        File currentFile = new File(file.getPath(), s);
                        currentFile.delete();
                    }
                }
                catch(Exception ignore) {}

                String query2 = "SELECT link FROM pbm.versions WHERE version = \"" + rs.getString("version") + "\"";
                PreparedStatement stmt2 = conn.prepareStatement(query2, ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);
                ResultSet rs2 = stmt2.executeQuery();
                rs2.last();
                String pkg = rs2.getString("link");

                new File("ProjectBloodMoon/package.zip");
                Utility.download(pkg, "ProjectBloodMoon/package.zip");

                System.out.println("Installing update...");

                String zipFileName = "ProjectBloodMoon/package.zip";
                String destDirectory = "ProjectBloodMoon";
                File destDirectoryFolder = new File(destDirectory);
                if (!destDirectoryFolder.exists()) {
                    destDirectoryFolder.mkdir();
                }
                byte[] buffer = new byte[1024];
                ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
                ZipEntry zipEntry = zis.getNextEntry();
                while (zipEntry != null) {
                    String filePath = destDirectory + File.separator + zipEntry.getName();
                    if (!zipEntry.isDirectory()) {
                        FileOutputStream fos = new FileOutputStream(filePath);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    } else {
                        File dir = new File(filePath);
                        dir.mkdir();
                    }
                    zis.closeEntry();
                    zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
                zis.close();

                File packageZip = new File("ProjectBloodMoon/package.zip");
                packageZip.delete();
                Utility.setPropertyDown("vwu", "true");
            }

            System.exit(100);
        }
        else {
            System.out.println("Downloading game...");
            file.mkdirs();

            String query = "SELECT version FROM pbm.versions";
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            rs.last();

            String query2 = "SELECT link FROM pbm.versions WHERE version = \"" + rs.getString("version") + "\"";
            PreparedStatement stmt2 = conn.prepareStatement(query2, ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs2 = stmt2.executeQuery();
            rs2.last();
            String pkg = rs2.getString("link");

            try {
                new File("ProjectBloodMoon/package.zip");
                Utility.download(pkg, "ProjectBloodMoon/package.zip");

                System.out.println("Installing game...");

                String zipFileName = "ProjectBloodMoon/package.zip";
                String destDirectory = "ProjectBloodMoon";
                File destDirectoryFolder = new File(destDirectory);
                if (!destDirectoryFolder.exists()) {
                    destDirectoryFolder.mkdir();
                }
                byte[] buffer = new byte[1024];
                ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
                ZipEntry zipEntry = zis.getNextEntry();
                while (zipEntry != null) {
                    String filePath = destDirectory + File.separator + zipEntry.getName();
                    if (!zipEntry.isDirectory()) {
                        FileOutputStream fos = new FileOutputStream(filePath);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    } else {
                        File dir = new File(filePath);
                        dir.mkdir();
                    }
                    zis.closeEntry();
                    zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
                zis.close();

                File packageZip = new File("ProjectBloodMoon/package.zip");
                packageZip.delete();

                System.exit(100);
            }
            catch(Exception e) {
                System.out.println("Download failed.");
                String[] entries = file.list();
                try {
                    for (String s : entries) {
                        File currentFile = new File(file.getPath(), s);
                        currentFile.delete();
                    }
                }
                catch(Exception ignore) {}
                file.delete();
                System.exit(0);
            }
        }
    }
}
